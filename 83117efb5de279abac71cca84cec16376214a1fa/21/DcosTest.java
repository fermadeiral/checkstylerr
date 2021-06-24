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
}
