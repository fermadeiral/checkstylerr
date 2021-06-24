package org.openmrs.module.sync2.api.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.openmrs.module.sync2.api.SyncAuditService;
import org.openmrs.module.sync2.api.SyncConfigurationService;
import org.openmrs.module.sync2.api.SyncPushService;
import org.openmrs.module.sync2.api.model.audit.AuditMessage;
import org.openmrs.module.sync2.api.sync.SyncClient;
import org.openmrs.module.sync2.api.utils.SyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.openmrs.module.sync2.SyncConstants.ACTION_VOIDED;
import static org.openmrs.module.sync2.SyncConstants.PUSH_OPERATION;
import static org.openmrs.module.sync2.SyncConstants.PUSH_SUCCESS_MESSAGE;
import static org.openmrs.module.sync2.api.utils.SyncAuditUtils.prepareBaseAuditMessage;
import static org.openmrs.module.sync2.api.utils.SyncUtils.getLocalBaseUrl;
import static org.openmrs.module.sync2.api.utils.SyncUtils.getFullUrl;
import static org.openmrs.module.sync2.api.utils.SyncUtils.getParentBaseUrl;
import static org.openmrs.module.sync2.api.utils.SyncUtils.getPushPath;
import static org.openmrs.module.sync2.api.utils.SyncUtils.extractUUIDFromResourceLinks;

@Component("sync2.syncPushService")
public class SyncPushServiceImpl implements SyncPushService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncPushService.class);

    @Autowired
    private SyncAuditService syncAuditService;

    @Autowired
    private SyncConfigurationService configurationService;

    private SyncClient syncClient = new SyncClient();

    @Override
    public AuditMessage readDataAndPushToParent(String category, Map<String, String> resourceLinks,
                                                String action, String clientName) {
        String parentResourceURL = getFullUrl(getParentBaseUrl(configurationService), getPushPath(resourceLinks.get(clientName)));
        String localResourceURL = getFullUrl(getLocalBaseUrl(configurationService), resourceLinks.get(clientName));

        LOGGER.info(String.format("SyncPushService category: %s, address: %s, action: %s", category, parentResourceURL, action));
        
        String uuid = extractUUIDFromResourceLinks(resourceLinks);
    
        AuditMessage auditMessage = prepareBaseAuditMessage(PUSH_OPERATION, configurationService);
        auditMessage.setResourceName(category);
        auditMessage.setUsedResourceUrl(parentResourceURL);
        auditMessage.setLinkType(clientName);
        auditMessage.setAvailableResourceUrls(SyncUtils.serializeMapToPrettyJson(resourceLinks));
        auditMessage.setAction(action);
        try {
            Object data = action.equals(ACTION_VOIDED) ? uuid : syncClient.pullData(category, clientName, localResourceURL);
            syncClient.pushData(data, clientName, parentResourceURL, action);

            auditMessage.setSuccess(true);
            auditMessage.setDetails(PUSH_SUCCESS_MESSAGE);
        } catch (Exception e) {
            LOGGER.error("Problem with pushing to parent", e);
            auditMessage.setSuccess(false);
            auditMessage.setDetails(ExceptionUtils.getFullStackTrace(e));
        } finally {
            auditMessage = syncAuditService.saveAuditMessage(auditMessage);
        }
        return auditMessage;
    }
    
    @Override
    public AuditMessage readDataAndPushToParent(String category, Map<String, String> resourceLinks,
                                                String action) {
        String clientName = SyncUtils.selectAppropriateClientName(resourceLinks);
        return readDataAndPushToParent(category, resourceLinks, action, clientName);
    }
}
