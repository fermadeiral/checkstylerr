/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.qa.specs;

import com.stratio.qa.utils.ThreadProperty;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DcosTest {

    @Test
    public void testSaveDCOSCookie() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        DcosSpec dcos = new DcosSpec(commong);
        String email = "admin@demo.stratio.com";
        String dcosSecret = "04uth_jwt_s3cr3t";

        dcos.setDCOSCookie(dcosSecret,email);
    }

    @Test
    public void testCheckHashMap() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String jsonExample = "[{\"attributes\": {\"dc\": \"dc1\", \"label\": \"all\"}, \"hostname\": \"1\" }, {\"attributes\": {\"dc\": \"dc2\", \"label\": \"all\"}, \"hostname\": \"2\" },{\"attributes\": {\"dc\": \"dc3\", \"label\": \"all\"}, \"hostname\": \"3\" } ]";
        CommonG commong = new CommonG();
        DcosSpec dcos = new DcosSpec(commong);
        String result = dcos.obtainsDataCenters(jsonExample);
        Assert.assertEquals(result.split(";").length, 3);
    }

    @Test
    public void testSelectElements() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String exhibitorJsonExample = "{\"phases\":[{\"0001\":{\"steps\":[{\"0\":{\"id\":\"6db1fbc7-5fa8-4d38-9334-a06cd861a216\",\"name\":\"arango-agent-0001\",\"role\":\"arango-agent\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.183\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S15\",\"placements\":\"hostname:LIKE:10.200.0.18[2-4]\"}},{\"1\":{\"id\":\"aeb40b93-1b5d-4f6b-a931-2cbda14cc7ea\",\"name\":\"arango-agent-0002\",\"role\":\"arango-agent\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.182\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S12\",\"placements\":\"hostname:LIKE:10.200.0.18[2-4]\"}},{\"2\":{\"id\":\"460131ac-3802-45f6-a6d4-8b5ddeb88034\",\"name\":\"arango-agent-0003\",\"role\":\"arango-agent\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.184\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S14\",\"placements\":\"hostname:LIKE:10.200.0.18[2-4]\"}}]}},{\"0002\":{\"steps\":[{\"0\":{\"id\":\"7f0e960f-9735-4ab5-92e9-b52208c92b00\",\"name\":\"arango-server-0001\",\"role\":\"arango-server\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.181\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S17\",\"placements\":\"hostname:LIKE:10.200.0.1(61|62|81)\"}},{\"1\":{\"id\":\"b624ab95-fbc0-4bc3-9e1d-e775aa810196\",\"name\":\"arango-server-0002\",\"role\":\"arango-server\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.161\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S11\",\"placements\":\"hostname:LIKE:10.200.0.1(61|62|81)\"}},{\"2\":{\"id\":\"0c842c15-5c63-45ac-b942-02540a3b7a59\",\"name\":\"arango-server-0003\",\"role\":\"arango-server\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.162\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S16\",\"placements\":\"hostname:LIKE:10.200.0.1(61|62|81)\"}}]}},{\"0003\":{\"steps\":[{\"0\":{\"id\":\"21805cd6-80a8-4076-8a76-7b51a130ebb4\",\"name\":\"arango-coordinator-0001\",\"role\":\"arango-coordinator\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.194\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S13\",\"placements\":\"hostname:LIKE:10.200.0.1(85|94)\"}},{\"1\":{\"id\":\"7bf761f2-b19e-46fe-9c7c-07761880106d\",\"name\":\"arango-coordinator-0002\",\"role\":\"arango-coordinator\",\"status\":\"RUNNING\",\"agent_hostname\":\"10.200.0.185\",\"slaveid\":\"67cb99d4-62fe-4da5-a9a4-79786e28780a-S10\",\"placements\":\"hostname:LIKE:10.200.0.1(85|94)\"}}]}}]}";
        ThreadProperty.set("exhibitor_answer", exhibitorJsonExample);
        CommonG commong = new CommonG();
        DcosSpec dcos = new DcosSpec(commong);
        dcos.selectElements("arango-agent", "arangodb", "agent_hostname");
        String[] hostnames = ThreadProperty.get("elementsConstraint").split("\n");
        Assert.assertEquals(hostnames.length, 3, "Hostnames returned from select elements should be 3");
        ThreadProperty.remove("exhibitor_answer");
    }

    @Test
    public void testCheckConstraintType() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        DcosSpec dcos = new DcosSpec(commong);
        String[] hostnames = {"\"10.200.0.183\"", "\"10.200.0.182\"", "\"10.200.0.184\""};
        dcos.checkConstraintType("arango-agent", "arango", "hostname", "LIKE", "10.200.0.18[2-4]", hostnames);
    }
}
