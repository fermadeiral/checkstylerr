/*
 * Copyright 2012-2013 inBloom, Inc. and its affiliates.
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
package org.slc.sli.api.security.context.validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import org.slc.sli.api.util.SecurityUtil;
import org.slc.sli.common.constants.EntityNames;
import org.slc.sli.common.util.datetime.DateHelper;
import org.slc.sli.domain.Entity;

/**
 * Validator for teacher/staff
 *
 * @author nbrown
 *
 */
@Component
public class TransitiveStudentToStaffValidator extends BasicValidator {

    public TransitiveStudentToStaffValidator() {
        super(true, EntityNames.STUDENT, Arrays.asList(EntityNames.STAFF, EntityNames.TEACHER));
    }

    @Override
    protected boolean doValidate(Set<String> ids) {
        Entity me = SecurityUtil.getSLIPrincipal().getEntity();
        Set<String> idsToCheck = new HashSet<String>(ids);
        idsToCheck.removeAll(filterConnectedViaEdOrg(idsToCheck, me));
        idsToCheck.removeAll(filterConnectedViaSection(idsToCheck, me));
        idsToCheck.removeAll(filterConnectedViaProgram(idsToCheck, me));
        idsToCheck.removeAll(filterConnectedViaCohort(idsToCheck, me));
        return idsToCheck.isEmpty();
    }

    protected Set<String> filterConnectedViaProgram(Set<String> ids, Entity me) {
        if (ids.isEmpty()) {
            return ids;
        }
        return filterThroughSubdocs(ids, me, "studentProgramAssociation", "programId", EntityNames.STAFF_PROGRAM_ASSOCIATION);
    }

    private Set<String> filterThroughSubdocs(Set<String> ids, Entity me, String subDocType, String idKey, String staffAssocType) {
        Set<String> subDocIds = getSubDocIds(me, subDocType, idKey);
        if(subDocIds.isEmpty()) {
            return ids;
        }
        Query q = Query.query(Criteria.where("body."+idKey).in(subDocIds).and("body.staffId").in(ids).andOperator(DateHelper.getExpiredCriteria()));
        Iterator<Entity> spas = getRepo().findEach(staffAssocType, q);
        Set<String> filtered = new HashSet<String>();
        while(spas.hasNext()) {
            Entity spa = spas.next();
            String staff = (String) spa.getBody().get("staffId");
            if (staff != null) {
                filtered.add(staff);
            }
        }
        return filtered;
    }

    protected Set<String> filterConnectedViaCohort(Set<String> ids, Entity me) {
        if (ids.isEmpty()) {
            return ids;
        }
        return filterThroughSubdocs(ids, me, "studentCohortAssociation", "cohortId", EntityNames.STAFF_COHORT_ASSOCIATION);
    }

    protected Set<String> filterConnectedViaSection(Set<String> ids, Entity me) {
        return Collections.emptySet();
    }

    protected Set<String> filterConnectedViaEdOrg(Set<String> ids, Entity me) {
        return Collections.emptySet();
    }

    private Set<String> getSubDocIds(Entity me, String subDocType, String idKey) {
        List<Entity> subDocs = me.getEmbeddedData().get(subDocType);
        if(subDocs == null) {
            return Collections.emptySet();
        }
        Set<String> assocIds = new HashSet<String>();
        for(Entity subDoc: subDocs) {
            assocIds.add((String) subDoc.getBody().get(idKey));
        }
        return assocIds;
    }
}
