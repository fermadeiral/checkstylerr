package com.ctrip.framework.apollo.biz.service;

import com.ctrip.framework.apollo.biz.AbstractIntegrationTest;
import com.ctrip.framework.apollo.biz.entity.Instance;
import com.ctrip.framework.apollo.biz.entity.InstanceConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.*;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class InstanceServiceTest extends AbstractIntegrationTest {
  @Autowired
  private InstanceService instanceService;

  @Test
  @Rollback
  public void testInstance() throws Exception {
    String someAppId = "someAppId";
    String someClusterName = "someClusterName";
    String someDataCenter = "someDataCenter";
    String someIp = "someIp";

    Instance instance = instanceService.findInstance(someAppId, someClusterName, someDataCenter,
        someIp);

    assertNull(instance);

    instanceService.createInstance(assembleInstance(someAppId, someClusterName, someDataCenter,
        someIp));

    instance = instanceService.findInstance(someAppId, someClusterName, someDataCenter,
        someIp);

    assertNotEquals(0, instance.getId());
  }

  @Test
  @Rollback
  public void testInstanceConfig() throws Exception {
    long someInstanceId = 1;
    String someConfigAppId = "someConfigAppId";
    String someConfigNamespaceName = "someConfigNamespaceName";
    String someReleaseKey = "someReleaseKey";
    String anotherReleaseKey = "anotherReleaseKey";

    InstanceConfig instanceConfig = instanceService.findInstanceConfig(someInstanceId,
        someConfigAppId, someConfigNamespaceName);

    assertNull(instanceConfig);

    instanceService.createInstanceConfig(assembleInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespaceName, someReleaseKey));

    instanceConfig = instanceService.findInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespaceName);

    assertNotEquals(0, instanceConfig.getId());
    assertEquals(someReleaseKey, instanceConfig.getReleaseKey());

    instanceConfig.setReleaseKey(anotherReleaseKey);

    instanceService.updateInstanceConfig(instanceConfig);

    InstanceConfig updated = instanceService.findInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespaceName);

    assertEquals(instanceConfig.getId(), updated.getId());
    assertEquals(anotherReleaseKey, updated.getReleaseKey());
  }

  private Instance assembleInstance(String appId, String clusterName, String dataCenter, String ip) {
    Instance instance = new Instance();
    instance.setAppId(appId);
    instance.setIp(ip);
    instance.setClusterName(clusterName);
    instance.setDataCenter(dataCenter);

    return instance;
  }

  private InstanceConfig assembleInstanceConfig(long instanceId, String configAppId, String
      configNamespaceName, String releaseKey) {
    InstanceConfig instanceConfig = new InstanceConfig();
    instanceConfig.setInstanceId(instanceId);
    instanceConfig.setConfigAppId(configAppId);
    instanceConfig.setConfigNamespaceName(configNamespaceName);
    instanceConfig.setReleaseKey(releaseKey);
    return instanceConfig;
  }
}