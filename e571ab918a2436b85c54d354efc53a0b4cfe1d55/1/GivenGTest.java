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
import cucumber.api.DataTable;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class GivenGTest {

    @Test
    public void testSaveElementFromVariable() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());

        String baseData = "indicesJSON.conf";
        String envVar = "envVar";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();
        GivenGSpec giveng = new GivenGSpec(commong);

        try {
            giveng.saveElementEnvironment(null, null, jsonString.concat(".$.[0]"), envVar);
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        assertThat(ThreadProperty.get(envVar)).as("Not correctly ordered").isEqualTo("stratiopaaslogs-2016-07-26");
    }


    @Test
    public void testSaveDCOSCookie() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        GivenGSpec giveng = new GivenGSpec(commong);
        String email = "admin@demo.stratio.com";
        String dcosSecret = "04uth_jwt_s3cr3t";

        giveng.setDCOSCookie(dcosSecret,email);
    }

    @Test
    public void testsendRequestDataTableTimeout() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        commong.setRestHost("jenkins.stratio.com");
        commong.setRestPort(":80");
        String endPoint = "endpoint";
        String expectedMsg = "regex:tag";
        String requestType = "POST";
        String baseData = "retrieveDataStringTest.conf";
        String type = "string";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "DELETE", "N/A"));
        DataTable modifications = DataTable.create(rawData);

        GivenGSpec giveng = new GivenGSpec(commong);

        try {
            giveng.sendRequestDataTableTimeout(10,1,requestType,endPoint,null,expectedMsg,baseData,null,type,modifications);
            fail("Expected Exception");
        } catch (NullPointerException e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(NullPointerException.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo(null);
        }

    }

    @Test
    public void testCheckHashMap() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String jsonExample = "[{\"attributes\": {\"dc\": \"dc1\", \"label\": \"all\"}, \"hostname\": \"1\" }, {\"attributes\": {\"dc\": \"dc2\", \"label\": \"all\"}, \"hostname\": \"2\" },{\"attributes\": {\"dc\": \"dc3\", \"label\": \"all\"}, \"hostname\": \"3\" } ]";
        CommonG commong = new CommonG();
        GivenGSpec giveng = new GivenGSpec(commong);
        String result = giveng.obtainsDataCenters(jsonExample);
        Assert.assertEquals(result.split(";").length, 3);
    }
}
