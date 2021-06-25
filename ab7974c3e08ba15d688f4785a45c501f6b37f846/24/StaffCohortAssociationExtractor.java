/*
 * Copyright 2012 Shared Learning Collaborative, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.slc.sli.bulk.extract.lea;

import java.util.Iterator;
import java.util.Set;

import org.slc.sli.bulk.extract.extractor.EntityExtractor;
import org.slc.sli.bulk.extract.util.EdOrgExtractHelper;
import org.slc.sli.common.constants.EntityNames;
import org.slc.sli.common.constants.ParameterConstants;
import org.slc.sli.domain.Entity;
import org.slc.sli.domain.NeutralQuery;
import org.slc.sli.domain.Repository;

public class StaffCohortAssociationExtractor implements EntityExtract {
    private EntityExtractor extractor;
    private ExtractFileMap map;
    private Repository<Entity> repo;
    private EdOrgExtractHelper edOrgExtractHelper;
    
    public StaffCohortAssociationExtractor(EntityExtractor extractor, ExtractFileMap map, Repository<Entity> repo, EdOrgExtractHelper edOrgExtractHelper) {
        this.extractor = extractor;
        this.map = map;
        this.repo = repo;
        this.edOrgExtractHelper = edOrgExtractHelper;
    }

    @Override
    public void extractEntities(EntityToEdOrgCache staffToEdorgCache) {
        edOrgExtractHelper.logSecurityEvent(map.getEdOrgs(), EntityNames.STAFF_COHORT_ASSOCIATION, this.getClass().getName());
        Iterator<Entity> scas = repo.findEach(EntityNames.STAFF_COHORT_ASSOCIATION, new NeutralQuery());
        while (scas.hasNext()) {
            Entity sca = scas.next();
            Set<String> edOrgs = staffToEdorgCache.getEntriesById(sca.getBody().get(ParameterConstants.STAFF_ID).toString());
            if (edOrgs == null || edOrgs.size() == 0) {
                continue;
            }
            for (String edOrg : edOrgs) {
                extractor.extractEntity(sca, map.getExtractFileForEdOrg(edOrg), EntityNames.STAFF_COHORT_ASSOCIATION);
            }
            
        }
    }
    
}
