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

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.stratio.qa.utils.ThreadProperty;
import io.cucumber.datatable.DataTable;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.everit.json.schema.ValidationException;

public class CommonGTest {

    private JSONObject jsonObject1_1, jsonObject1;
    private JSONObject jsonObject2, jsonObject3;
    private JSONObject jsonObject4_1, jsonObject4;
    private JSONObject jsonObject5, jsonObject6, jsonObject6_1;
    private JSONObject jsonObject7, jsonObject7_1;
    private JSONObject jsonObject8, jsonObject8_1;
    private JSONObject jsonObject9, jsonObject9_1;
    private JSONObject jsonObject10, jsonObject10_1;
    private JSONObject jsonObject11, jsonObject11_1;
    private JSONObject jsonObject12, jsonObject12_1;
    private JSONObject jsonObject13, jsonObject13_1, jsonObject13_2;
    private JSONObject jsonObject14, jsonObject14_1;
    private JSONObject jsonObject15, jsonObject15_1;
    private JSONObject jsonObject16, jsonObject16_1;
    private JSONObject jsonObject17, jsonObject17_2;
    private JSONObject jsonObject18, jsonObject18_3;
    private JSONObject jsonObject19, jsonObject19_2, jsonObject19_3;
    private JSONArray jsonObject3_1, jsonObject17_1, jsonObject18_1;
    private JsonObject jsonObject20, jsonObject21;

    @BeforeClass
    public void prepareJson() {
        jsonObject1_1 = new JSONObject();
        jsonObject1_1.put("key3", "value3");
        jsonObject1 = new JSONObject();
        jsonObject1.put("key1", "value1").put("key2", jsonObject1_1);

        jsonObject2 = new JSONObject();
        jsonObject2.put("type", "record");

        String[] array1 = {"a", "b", "c"};
        jsonObject3_1 = new JSONArray(Arrays.asList(array1));
        jsonObject3 = new JSONObject();
        jsonObject3.put("type", jsonObject3_1);

        jsonObject4_1 = new JSONObject();
        jsonObject4_1.put("key3", JSONObject.NULL);
        jsonObject4 = new JSONObject();
        jsonObject4.put("key1", "value1").put("key2", jsonObject4_1);

        jsonObject5 = new JSONObject();
        jsonObject5.put("key2", jsonObject1_1);

        jsonObject6_1 = new JSONObject();
        jsonObject6_1 = new JSONObject();
        jsonObject6_1.put("key4", "value4").put("key3", "value3");
        jsonObject6 = new JSONObject();
        jsonObject6.put("key1", "value1").put("key2", jsonObject6_1);

        jsonObject7_1 = new JSONObject();
        jsonObject7_1.put("key3", "NEWvalue3");
        jsonObject7 = new JSONObject();
        jsonObject7.put("key2", jsonObject7_1).put("key1", "value1");

        jsonObject8_1 = new JSONObject();
        jsonObject8_1.put("key3", "value3Append");
        jsonObject8 = new JSONObject();
        jsonObject8.put("key2", jsonObject8_1).put("key1", "value1");

        jsonObject9_1 = new JSONObject();
        jsonObject9_1.put("key3", "Prependvalue3");
        jsonObject9 = new JSONObject();
        jsonObject9.put("key2", jsonObject9_1).put("key1", "value1");

        jsonObject10_1 = new JSONObject();
        jsonObject10_1.put("key3", "vaREPLACEe3");
        jsonObject10 = new JSONObject();
        jsonObject10.put("key2", jsonObject10_1).put("key1", "value1");

        jsonObject11_1 = new JSONObject();
        jsonObject11_1.put("key3", true);
        jsonObject11 = new JSONObject();
        jsonObject11.put("key2", jsonObject11_1).put("key1", "value1");

        jsonObject12_1 = new JSONObject();
        jsonObject12_1.put("key3", false);
        jsonObject12 = new JSONObject();
        jsonObject12.put("key2", jsonObject12_1).put("key1", "value1");

        jsonObject13_1 = new JSONObject();
        jsonObject13_2 = new JSONObject();
        jsonObject13_1.put("key3", jsonObject13_2);
        jsonObject13 = new JSONObject();
        jsonObject13.put("key2", jsonObject13_1).put("key1", "value1");

        jsonObject14_1 = new JSONObject();
        jsonObject14_1.put("key3", 5);
        jsonObject14 = new JSONObject();
        jsonObject14.put("key2", jsonObject14_1).put("key1", "value1");

        jsonObject15_1 = new JSONObject();
        jsonObject15_1.put("key3", 5.0);
        jsonObject15 = new JSONObject();
        jsonObject15.put("key2", jsonObject15_1).put("key1", "value1");

        jsonObject16_1 = new JSONObject();
        jsonObject16_1.put("key3", 0);
        jsonObject16 = new JSONObject();
        jsonObject16.put("key2", jsonObject16_1).put("key1", "value1");

        String[] array2 = {"a", "b", "c"};
        jsonObject17_1 = new JSONArray(Arrays.asList(array2));
        jsonObject17_2 = new JSONObject();
        jsonObject17_2.put("key3", jsonObject17_1);
        jsonObject17 = new JSONObject();
        jsonObject17.put("key2", jsonObject17_2).put("key1", "value1");

        String[] array3_1 = {};
        Object[] array3 = {array3_1};
        jsonObject18_1 = new JSONArray(Arrays.asList(array3));
        jsonObject18_3 = new JSONObject();
        jsonObject18_3.put("key3", jsonObject18_1);
        jsonObject18 = new JSONObject();
        jsonObject18.put("key2", jsonObject18_3).put("key1", "value1");

        jsonObject19_3 = new JSONObject();
        jsonObject19_3.put("a", "1").put("b", "1").put("c", "1");

        jsonObject19_2 = new JSONObject();
        jsonObject19_2.put("key3", jsonObject19_3);
        jsonObject19 = new JSONObject();
        jsonObject19.put("key2", jsonObject19_2).put("key1", "value1");

        String test20 = "{\"key1\":null,\"key2\":{\"key3\":null}}";
        jsonObject20 = JsonValue.readHjson(test20).asObject();

        String test21 = "{\"key1\":\"value1\",\"key2\":{\"key3\":\"value3\"}}";
        jsonObject21 = JsonValue.readHjson(test21).asObject();
    }

    @Test
    public void retrieveDataExceptionTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String baseData = "invalid.conf";
        String type = "string";

        assertThat(commong.retrieveData(baseData, type)).as("File not found exception").isEqualTo("ERR! File not found: invalid.conf");
    }

    @Test
    public void retrieveDataStringTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String baseData = "retrieveDataStringTest.conf";
        String type = "string";

        String returnedData = commong.retrieveData(baseData, type);
        assertThat(returnedData).as("Invalid information read").isEqualTo("username=username&password=password");
    }

    @Test
    public void retrieveDataInvalidJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String baseData = "retrieveDataInvalidJsonTest.conf";
        String type = "json";

        try {
            commong.retrieveData(baseData, type);
            org.testng.Assert.fail("Expected ParseException");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(ParseException.class.toString());
        }
    }

    @Test
    public void retrieveDataValidJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String baseData = "retrieveDataValidJsonTest.conf";
        String type = "json";

        String returnedData = commong.retrieveData(baseData, type);
        assertThat(returnedData).as("Invalid information read").isEqualTo(jsonObject1.toString());
    }

    @Test
    public void modifyDataNullValueJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject4.toString();
        String expectedData = "{\"key2\":{\"key3\":null}}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "DELETE", "N/A"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void removeNullsTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String expectedData = "{\"key1\":\"TO_BE_NULL\",\"key2\":{\"key3\":\"TO_BE_NULL\"}}";

        JsonObject nullsRemoved = commong.removeNulls(jsonObject20);
        JSONAssert.assertEquals(expectedData, nullsRemoved.toString(), false);

    }

    @Test
    public void removeNullsNoNullsTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String expectedData = "{\"key1\":\"value1\",\"key2\":{\"key3\":\"value3\"}}";

        JsonObject nullsRemoved = commong.removeNulls(jsonObject21);
        JSONAssert.assertEquals(expectedData, nullsRemoved.toString(), false);

    }

    @Test
    public void modifyDataInvalidModificationTypeStringTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = "username=username&password=password";
        String type = "string";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("username=username", "REMOVE", "N/A"));
        DataTable modifications = DataTable.create(rawData);

        try {
            commong.modifyData(data, type, modifications);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("Modification type does not exist: REMOVE");
        }
    }

    @Test
    public void modifyDataInvalidModificationTypeJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("username=username", "REMOVE", "N/A"));
        DataTable modifications = DataTable.create(rawData);

        try {
            commong.modifyData(data, type, modifications);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("Modification type does not exist: REMOVE");
        }
    }

    @Test
    public void modifyDataDeleteStringTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = "username=username&password=password";
        String expectedData = "password=password";
        String type = "string";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("username=username&", "DELETE", "N/A"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        assertThat(modifiedData).as("Unexpected modified data").isEqualTo(expectedData);
    }

    @Test
    public void modifyDataAddStringTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = "username=username&password=password";
        String expectedData = "username=username&password=password&config=config";
        String type = "string";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("N/A", "ADD", "&config=config"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        assertThat(modifiedData).as("Unexpected modified data").isEqualTo(expectedData);
    }

    @Test
    public void modifyDataUpdateStringTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = "username=username&password=password";
        String expectedData = "username=NEWusername&password=password";
        String type = "string";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("username=username", "UPDATE", "username=NEWusername"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        assertThat(modifiedData).as("Unexpected modified data").isEqualTo(expectedData);
    }

    @Test
    public void modifyDataPrependStringTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = "username=username&password=password";
        String expectedData = "key1=value1&username=username&password=password";
        String type = "string";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("username=username", "PREPEND", "key1=value1&"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        assertThat(modifiedData).as("Unexpected modified data").isEqualTo(expectedData);
    }

    @Test
    public void modifyDataDeleteJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = "{\"key2\":{\"key3\":\"value3\"}}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "DELETE", "N/A"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject6.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.key2.key4", "ADD", "value4", "string"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataUpdateJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject7.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "UPDATE", "NEWvalue3"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAppendJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject8.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "APPEND", "Append"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataPrependJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject9.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "PREPEND", "Prepend"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject10.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "lu->REPLACE"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonArrayTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject2.toString();
        String expectedData = "{\"type\":[]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("type", "REPLACE", "[]", "array"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonArrayTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject2.toString();
        String expectedData = jsonObject3.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("type", "REPLACE", "[\"a\", \"b\", \"c\"]", "array"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonArrayTest_3() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject17.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "[\"a\", \"b\", \"c\"]", "array"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonArrayTest_4() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject18.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "[[]]", "array"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonObjectTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject19.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "{\"a\":\"1\", \"b\":\"1\", \"c\":\"1\"}", "object"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonObjectTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject13.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "{}", "object"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonNumberTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject14.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "5", "number"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonNumberTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject15.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "5.0", "number"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonNumberTest_3() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject16.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "0", "number"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonBooleanTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject11.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "true", "boolean"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonBooleanTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject12.toString();
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "false", "boolean"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataReplaceJsonNull() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String data = jsonObject1.toString();
        String expectedData = jsonObject4.toString();
        ;
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key2.key3", "REPLACE", "null", "null"));
        DataTable modifications = DataTable.create(rawData);
        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonArrayTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",[\"value2\"]]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "[\"value2\"]", "array"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonArrayTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",[]]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "[]", "array"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonArrayTest_3() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",[[]]]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "[[]]", "array"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonObjectTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",{\"key2\": \"value2\"}]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "{\"key2\": \"value2\"}", "object"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonObjectTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",{}]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "{}", "object"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonStringTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",\"value2\"]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "value2", "string"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonStringTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",\"\"]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "", "string"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonNumberTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",666]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "666", "number"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonNumberTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",66.6]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "66.6", "number"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonNumberTest_3() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",0]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "0", "number"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonBooleanTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",true]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "true", "boolean"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonBooleanTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",false]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "false", "boolean"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonBooleanTest_3() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",false]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "", "boolean"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonNullTest_1() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",null]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "null", "null"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataAddToJsonNullTest_2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", new JSONArray(Arrays.asList("value1")));
        String data = jsonObject.toString();
        String expectedData = "{\"key1\":[\"value1\",null]}";
        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("key1", "ADDTO", "", "null"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void generateRequestNoAppURLTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String requestType = "MYREQUEST";
        String endPoint = "endpoint";
        String data = "data";
        String type = "string";

        try {
            commong.generateRequest(requestType, false, null, null, endPoint, data, type);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("Rest host has not been set");
        }
    }

    @Test
    public void generateRequestInvalidRequestTypeTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String requestType = "MYREQUEST";
        String endPoint = "endpoint";
        String data = "data";
        String type = "string";

        try {
            commong.setRestHost("localhost");
            commong.setRestPort("80");

            commong.generateRequest(requestType, false, null, null, endPoint, data, type);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("Operation not valid: MYREQUEST");
        }
    }

    @Test
    public void generateRequestNotImplementedRequestTypeTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String requestType = "TRACE";
        String endPoint = "endpoint";
        String data = "data";
        String type = "string";

        try {
            commong.setRestHost("localhost");
            commong.setRestPort("80");
            commong.generateRequest(requestType, false, null, null, endPoint, data, type);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("Operation not implemented: TRACE");
        }
    }

    @Test
    public void generateRequestDataNullPUTTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String requestType = "PUT";
        String endPoint = "endpoint";
        String type = "string";

        try {
            commong.setRestHost("localhost");
            commong.setRestPort("80");
            commong.generateRequest(requestType, false, null, null, endPoint, null, type);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("Missing fields in request.");
        }
    }

    @Test
    public void generateRequestDataNullPOSTTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String requestType = "POST";
        String endPoint = "endpoint";
        String type = "string";

        try {
            commong.setRestHost("localhost");
            commong.setRestPort("80");
            commong.generateRequest(requestType, false, null, null, endPoint, null, type);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("Missing fields in request.");
        }
    }

    @Test
    public void testRunLocalCommand() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String command = "echo hey";
        commong.runLocalCommand(command);
        int exitstatus = commong.getCommandExitStatus();
        String response = commong.getCommandResult();

        assertThat(exitstatus).as("Running command echo locally").isEqualTo(0);
        assertThat(response).as("Running command echo locally").isEqualTo("hey");

    }

    @Test
    public void testNonexistentLocalCommandExitStatus() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String command = "nonexistscommand";
        commong.runLocalCommand(command);
        int exitstatus = commong.getCommandExitStatus();

        assertThat(exitstatus).as("Running nonexistent command 'shur' locally").isEqualTo(127);
    }

    @Test
    public void testGetSSHConnection() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        commong.getRemoteSSHConnection();
    }

    @Test
    public void testGetWebHostPort() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        commong.getWebHost();
        commong.getWebPort();
    }

    @Test
    public void testGetExceptionList() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        commong.getExceptions();
        commong.getTextFieldCondition();
    }

    @Test
    public void testGetClients() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        commong.getCassandraClient();
        commong.getElasticSearchClient();
        commong.getKafkaUtils();
        commong.getMongoDBClient();
        commong.getZookeeperSecClient();
    }

    @Test
    public void testRegexMatcher() throws Exception {
        String expectedMsg = "regex:tag";
        ThreadProperty.set("class", this.getClass().getCanonicalName());

        assertThat("tag").as("Regex matcher").isEqualTo(CommonG.matchesOrContains(expectedMsg).toString());
    }

    @Test
    public void testRegexContains() throws Exception {
        String expectedMsg = "tag";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String exit = Pattern.compile(Pattern.quote(expectedMsg)).toString();

        assertThat(exit).as("Regex matcher").isEqualTo(CommonG.matchesOrContains(expectedMsg).toString());
    }


    @Test
    public void testGETRequest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        commong.setClient(new AsyncHttpClient());
        commong.setRestHost("jenkins.stratio.com");
        commong.setRestPort(":80");
        Future<Response> response = commong.generateRequest("GET", false, "bdt", "bdt", "/monitoring", "", "");

        assertThat(401).as("GET Request with exit code status 401").isEqualTo(response.get().getStatusCode());
    }

    @Test
    public void testPOSTRequest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        commong.setClient(new AsyncHttpClient());
        commong.setRestHost("jenkins.stratio.com");
        commong.setRestPort(":80");
        String data = "{j_username=user&j_password=pass";

        Future<Response> response = commong.generateRequest("POST", false, "bdt", "bdt", "/j_acegi_security_check", data, "");

        assertThat(401).as("GET Request with exit code status 401").isEqualTo(response.get().getStatusCode());
    }

    @Test
    public void testDELETERequest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        commong.setClient(new AsyncHttpClient());
        commong.setRestHost("jenkins.stratio.com");
        commong.setRestPort(":80");
        String data = "{j_username=user&j_password=pass";

        Future<Response> response = commong.generateRequest("DELETE", false, "bdt", "bdt", "/j_acegi_security_check", data, "");

        assertThat(401).as("GET Request with exit code status 401").isEqualTo(response.get().getStatusCode());
    }

    @Test
    public void testPUTRequest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        commong.setClient(new AsyncHttpClient());
        commong.setRestHost("jenkins.stratio.com");
        commong.setRestPort(":80");
        String data = "{j_username=user&j_password=pass";

        Future<Response> response = commong.generateRequest("PUT", false, "bdt", "bdt", "/j_acegi_security_check", data, "");

        assertThat(401).as("GET Request with exit code status 401").isEqualTo(response.get().getStatusCode());
    }

    @Test
    public void testGetClientsResults() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        commong.getCassandraResults();
        commong.getElasticsearchResults();
        commong.getCSVResults();
        commong.getMongoResults();
        commong.getResultsType();
        commong.getSeleniumCookies();
        commong.getHeaders();
    }


    @Test
    public void testParseJSONFragments() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String baseData = "fragmentsJSON.conf";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();

        String value1 = null;
        String value2 = null;

        try {
            value1 = commong.getJSONPathString(jsonString, "$.id", null);
            value2 = commong.getJSONPathString(jsonString, "$.element.name", null);
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        assertThat(value1).as("Value for id key does not match").isEqualTo("id");
        assertThat(value2).as("Value for element.name key does not match").isEqualTo("elementName");
    }

    @Test
    public void testParseJSONElasticseach() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String baseData = "elasticJSON.conf";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();

        String value1 = null;

        try {
            value1 = commong.getJSONPathString(jsonString, "$.product", null);
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        assertThat(value1).as("Value for product key does not match").isEqualTo("productValue");
    }


    @Test
    public void testParseJSONWhere() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String baseData = "whereJSON.conf";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();

        String value1 = null;

        try {
            value1 = commong.getJSONPathString(jsonString, "$", null);
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        assertThat(value1).as("Value does not match").isEqualTo("column = 'value'");
    }

    @Test
    public void testParseJSONConsulMesos() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String baseData = "consulMesosJSON.conf";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();

        String value1 = null;
        String value2 = null;

        try {
            value1 = commong.getJSONPathString(jsonString, "$.[0].Node", null);
            value2 = commong.getJSONPathString(jsonString, "$.[1].Node", null);
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        assertThat(value1).as("Value for [0].Node key does not match").isEqualTo("paaslab31.stratio.com");
        assertThat(value2).as("Value for [1].Node key does not match").isEqualTo("paaslab33.stratio.com");
    }


    @Test
    public void testParseJSONConsulServices() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String baseData = "consulServicesJSON.conf";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();

        String value1 = null;
        String value2 = null;

        try {
            value1 = commong.getJSONPathString(jsonString, "$.~[0]", null);
            value2 = commong.getJSONPathString(jsonString, "$.~[2]", null);
        } catch (Exception e) {
            fail("Error parsing JSON String: " + e.toString());
        }

        assertThat(value1).as("key in 0 does not match").isEqualTo("mesos");
        assertThat(value2).as("key in 2 does not match").isEqualTo("consul");

    }

    @Test
    public void testParseJSONConsulMesosFrameworks() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        String baseData = "mesosFrameworks.conf";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();

        String value1 = null;

        try {
            value1 = commong.getJSONPathString(jsonString, "$.frameworks[?(@.name == \"arangodb3\")].hostname", "0");
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        assertThat(value1).as("Value for search does not match").isEqualTo("paaslab34.stratio.com");
    }

    @Test
    public void retrieveDataJsonASYaml() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String inputData = "schemas/testCreateFile.json";

        String returnedData = commong.asYaml(inputData);
       //Checking the output file follows yaml structure, starting with --
        assertThat(returnedData).as("Yaml file is not correct").contains("---\n" +
                "key1: \"value1\"\n");
    }

    @Test
    public void testparseJSONSchemaBasicProperties() throws Exception{
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String jsonSchemaHC = "{\"type\": \"object\",\"additionalProperties\": false,\"properties\": {\"serviceId\": {\"description\": \"Service ID of the Service\",\"type\": \"string\",\"readOnly\": false,\"pattern\": \"(.*)(jumanji-)(.+)+$\",\"examples\": [\"jumanji-\"],\"title\": \"Service ID of the Service\",\"default\": \"jumanji-\"}}}";
        String expected = "{\"serviceId\":\"jumanji-\"}";

        String result = commong.parseJSONSchema(new JSONObject(jsonSchemaHC)).toString();

        assertThat(result).as("Result: " + result + " is not equal to expected one: " + expected).isEqualToIgnoringCase(expected);
    }

    @Test
    public void testparseJSONSchemaBasicDoubleProperties() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String jsonSchemaHC = "{\"type\": \"object\",\"additionalProperties\": false,\"properties\": {\"general\": {\"type\": \"object\",\"additionalProperties\": false,\"ui\": null,\"name\": \"general\",\"title\": \"General\",\"description\": \"General\",\"properties\": {\"serviceId\": {\"description\": \"Service ID of the Service\",\"type\": \"string\",\"readOnly\": false,\"pattern\": \"(.*)(jumanji-)(.+)+$\",\"examples\": [\"jumanji-\"],\"title\": \"Service ID of the Service\",\"default\": \"jumanji-\"},\"mesosPrincipal\": {\"type\": \"string\",\"maxLength\": 100,\"minLength\": 3,\"readOnly\": false,\"level\": 1,\"title\": \"Mesos principal\",\"default\": \"open\"},\"kafkaZookeeper\": {\"description\": \"Zookeper used by kafka cluster.\",\"type\": \"string\",\"readOnly\": false,\"level\": 1,\"title\": \"Kafka zookeeper\",\"default\": \"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\"},\"resources\": {\"type\": \"object\",\"additionalProperties\": false,\"ui\": {\"component\": \"standard\"},\"name\": \"resources\",\"title\": \"Resources\",\"description\": \"\",\"properties\": {\"INSTANCES\": {\"description\": \"Instances of the service will be raised.\",\"type\": \"number\",\"minimum\": 1,\"readOnly\": false,\"level\": 1,\"title\": \"Instances\",\"default\": 1},\"CPUs\": {\"description\": \"CPUs allocated for the service.\",\"type\": \"number\",\"minimum\": 1,\"readOnly\": false,\"level\": 1,\"title\": \"CPUs\",\"default\": 1},\"MEM\": {\"description\": \"Memory allocated in MB for the service.\",\"type\": \"number\",\"minimum\": 1230,\"readOnly\": false,\"level\": 1,\"title\": \"Memory (MB)\",\"default\": 1230}}}}}}}";
        String expected = "{\"general\":{\"kafkaZookeeper\":\"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\",\"resources\":{\"CPUs\":1,\"MEM\":1230,\"INSTANCES\":1},\"mesosPrincipal\":\"open\",\"serviceId\":\"jumanji-\"}}";

        String result = commong.parseJSONSchema(new JSONObject(jsonSchemaHC)).toString();

        assertThat(result).as("Result: " + result + " is not equal to expected one: " + expected).isEqualToIgnoringCase(expected);
    }

    @Test
    public void testparseJSONSchemaSaaS() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String jsonSchemaHC = "{\"type\":\"object\",\"additionalProperties\": false,\"properties\": {\"general\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": null,\"name\":\"general\",\"title\":\"General\",\"description\":\"General\",\"properties\": {\"serviceId\": {\"description\":\"Service ID of the Service\",\"type\":\"string\",\"readOnly\": false,\"pattern\":\"(.*)(jumanji-)(.+)+$\",\"examples\": [\"jumanji-\"],\"title\":\"Service ID of the Service\",\"default\":\"jumanji-\"},\"mesosPrincipal\": {\"type\":\"string\",\"maxLength\": 100,\"minLength\": 3,\"readOnly\": false,\"level\": 1,\"title\":\"Mesos principal\",\"default\":\"open\"},\"kafkaZookeeper\": {\"description\":\"Zookeper used by kafka cluster.\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Kafka zookeeper\",\"default\":\"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\"},\"resources\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"standard\"},\"name\":\"resources\",\"title\":\"Resources\",\"description\":\"\",\"properties\": {\"INSTANCES\": {\"description\":\"Instances of the service will be raised.\",\"type\":\"number\",\"minimum\": 1,\"readOnly\": false,\"level\": 1,\"title\":\"Instances\",\"default\": 1},\"CPUs\": {\"description\":\"CPUs allocated for the service.\",\"type\":\"number\",\"minimum\": 1,\"readOnly\": false,\"level\": 1,\"title\":\"CPUs\",\"default\": 1},\"MEM\": {\"description\":\"Memory allocated in MB for the service.\",\"type\":\"number\",\"minimum\": 1230,\"readOnly\": false,\"level\": 1,\"title\":\"Memory (MB)\",\"default\": 1230}}}}},\"settings\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": null,\"name\":\"settings\",\"title\":\"Settings\",\"description\":\"\",\"properties\": {\"frameworkUser\": {\"description\":\"The user of the SO that the service will run as.\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Framework user\",\"default\":\"root\"},\"taskcfgAllEnableAvailabilityZones\": {\"description\":\"Enable availability zones\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Enable availability zones\",\"default\": false},\"moreSettings\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreSettings\",\"title\":\"\",\"description\":\"\",\"properties\": {\"mesosApiVersion\": {\"description\":\"Configures the Mesos API version to use. Possible values: V0 (non-HTTP), V1 (HTTP)\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Mesos API version\",\"enum\": [\"V0\",\"V1\"],\"default\":\"V0\"},\"taskcfgAllLeaderMesos\": {\"description\":\"Leader mesos URI\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Leader mesos\",\"default\":\"http://leader.mesos:5050/slaves\"}}},\"healthcheck\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"healthcheck\",\"title\":\"Healthcheck\",\"description\":\"\",\"properties\": {\"healthCheckEnable\": {\"description\":\"Enable/disable health check\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Health check enable\",\"default\": true},\"healthcheckMaxFailures\": {\"description\":\"Specifies the number of consecutive health check failures that can occur before a task is killed\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Max failures\",\"default\": 20},\"healthcheckDelay\": {\"description\":\"The period of time (in seconds) waited before the health-check begins execution\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Delay (seconds)\",\"default\": 20},\"healthcheckGracePeriod\": {\"description\":\"Specifies the amount of time (in seconds) to ignore health checks immediately after a task is started; or until the task becomes healthy for the first time\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Grace period (seconds)\",\"default\": 180},\"healthcheckInterval\": {\"description\":\"Specifies the amount of time (in seconds) to wait between health checks\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Interval (seconds)\",\"default\": 30},\"taskcfgAllTimestampThreshold\": {\"description\":\"Specifies the amount of time (in miliseconds) for considering healthcheck is KO\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Timestamp threshold (ms)\",\"default\": 60000},\"healthcheckTimeout\": {\"description\":\"Specifies the amount of time (in seconds) before a health check fails, regardless of the response\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Timeout (seconds)\",\"default\": 20}}},\"readiness\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"readiness\",\"title\":\"Readiness\",\"description\":\"\",\"properties\": {\"readinessCheckEnable\": {\"description\":\"Enable readiness-check\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Readiness-check\",\"default\": true},\"readinessDelay\": {\"description\":\"Specifies the amount of time (in seconds) from a readiness check and the next one\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Delay\",\"default\": 0},\"readinessInterval\": {\"description\":\"Specifies the amount of time (in seconds) to wait between readiness checks\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Interval\",\"default\": 30},\"readinessTimeout\": {\"description\":\"Specifies the amount of time (in seconds) before a readiness check fails, regardless of the response\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Timeout\",\"default\": 10}}},\"framework\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"framework\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"mapping\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"mapping\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"commonscli\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"commonscli\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"manifest\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"manifest\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"Statsd\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"Statsd\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"brokers\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"brokers\",\"title\":\"Brokers\",\"description\":\"\",\"properties\": {\"brokerCount\": {\"description\":\"Number of brokers to run\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Number of brokers\",\"default\": 2},\"brokerCpus\": {\"description\":\"Broker cpu requirements\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"CPUs\",\"default\": 1},\"brokerMem\": {\"description\":\"Broker memory requirements in MB.\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Memory (MB)\",\"default\": 4096},\"brokerDiskSize\": {\"description\":\"Broker disk requirements (only respected with persistent volumes)\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Disk size (MB)\",\"default\": 5000},\"brokerDiskType\": {\"description\":\"Disk type to be used for storing broker data. See documentation. [ROOT, MOUNT]\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Broker disk type\",\"enum\": [\"ROOT\",\"MOUNT\"],\"default\":\"ROOT\"},\"brokerDiskPath\": {\"description\":\"Relative path of consistent disk\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Disk path\",\"default\":\"kafka-broker-data\"},\"jmxAll\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"jmxAll\",\"title\":\"JMX\",\"description\":\"\",\"properties\": {\"taskcfgAllJmxEnabled\": {\"description\":\"Enable JMX service\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX\",\"default\": true},\"jmxEnabled\": {\"description\":\"Enable JMX service\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX\",\"default\": true},\"taskcfgAllJmxAuthenticate\": {\"description\":\"Enable JMX authentication\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Authenticate\",\"default\": true},\"taskcfgAllJmxPort\": {\"description\":\"Port for setting JMX service\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX Port\",\"default\": 9102},\"jmxPort\": {\"description\":\"Port for setting JMX service\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX Port\",\"default\": 9102},\"taskcfgAllJmxReporterEnabled\": {\"description\":\"Reporter enabled for JMX service\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX Reporter enabled\",\"default\": true},\"taskcfgAllJmxReporterPath\": {\"description\":\"Reporter path for setting JMX service\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX Reporter path\",\"default\":\"jmxexporter\"},\"taskcfgAllJmxReporterPort\": {\"description\":\"Reporter port for setting JMX service\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX Reporter port\",\"default\": 8085},\"taskcfgAllJmxReporterVersion\": {\"description\":\"Reporter version for setting JMX service\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"JMX Reporter version\",\"default\":\"0.10\"},\"exporter\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"exporter\",\"title\":\"\",\"description\":\"\",\"properties\": {}}}},\"moreBrokers\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreBrokers\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllBrokerPort\": {\"description\":\"Port for broker to listen on\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Broker port\",\"default\": 9093},\"taskcfgAllJavaOpts\": {\"description\":\"Broker JAVA_OPTS\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Java opts\",\"default\":\"\"},\"brokerJavaHeap\": {\"description\":\"The amount of JVM heap, in MB, allocated to the Kafka broker process\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Java heap for the broker (MB)\",\"default\": 2048}}}}},\"producers\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"producers\",\"title\":\"Producers\",\"description\":\"\",\"properties\": {\"producerCpus\": {\"description\":\"Producer cpu requirements\",\"type\":\"number\",\"minimum\": 0.5,\"readOnly\": false,\"level\": 1,\"title\":\"CPUs\",\"default\": 0.5},\"producerMem\": {\"description\":\"Producer memory allocated in MB.\",\"type\":\"number\",\"minimum\": 512,\"readOnly\": false,\"level\": 1,\"title\":\"Memory (MB)\",\"default\": 512}}},\"consumer\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"consumer\",\"title\":\"Consumer\",\"description\":\"\",\"properties\": {\"consumerCpus\": {\"description\":\"Consumer cpu requirements\",\"type\":\"number\",\"minimum\": 0.5,\"readOnly\": false,\"level\": 1,\"title\":\"CPUs\",\"default\": 0.5},\"consumerMem\": {\"description\":\"Consumer memory allocation requirements in MB.\",\"type\":\"number\",\"minimum\": 512,\"readOnly\": false,\"level\": 1,\"title\":\"Memory (MB)\",\"default\": 512}}},\"package\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"package\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"uris\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"uris\",\"title\":\"\",\"description\":\"\",\"properties\": {\"executorUri\": {\"description\":\"{{resource.assets.uris.executor-zip}}\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Executor URI\",\"default\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/stratio-framework-dcos-commons-executor/1.0.1-9973f3b/stratio-framework-dcos-commons-executor-1.0.1-9973f3b.zip\"},\"libmesosUri\": {\"description\":\"{{resource.assets.uris.libmesos-bundle-tar-gz}}\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Libmesos URI\",\"default\":\"${eos.artifactRepository}/repository/public/com/mesosphere/libmesos-bundle/1.9.0-rc2-1.2.0-rc2/libmesos-bundle-1.9.0-rc2-1.2.0-rc2-1.tar.gz\"},\"bootstrapUri\": {\"description\":\"{{resource.assets.uris.bootstrap-uri}}\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Bootstrap URI\",\"default\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/stratio-framework-dcos-commons-cli/1.0.1-9973f3b/stratio-framework-dcos-commons-cli-1.0.1-9973f3b-bootstrap.zip\"},\"svcymlUri\": {\"description\":\"{{resource.assets.uris.svcyml-uri}}\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"svc.yml URI\",\"default\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/kafka/eos-framework-kafka/2.1.1-4e72fef/eos-framework-kafka-2.1.1-4e72fef-svc.yml\"},\"zookeeperUri\": {\"description\":\"{{resource.assets.uris.zookeeper353beta-uri}}\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Zookeeper URI\",\"default\":\"${eos.artifactRepository}/repository/public/org/apache/zookeeper/zookeeper/3.5.3-beta/zookeeper-3.5.3-beta.jar\"},\"zkconnectUri\": {\"description\":\"{{resource.assets.uris.zkconnect-uri}}\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"zkconnect URI\",\"default\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/kafka/eos-framework-kafka-zkconnect/2.1.1-4e72fef/eos-framework-kafka-zkconnect-2.1.1-4e72fef.zip\"}}},\"logs\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"logs\",\"title\":\"Logs\",\"description\":\"\",\"properties\": {\"taskcfgAllLoglevelRoot\": {\"description\":\"Log level for root logs (DEBUG, INFO, ERROR, WARN)\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Log level root\",\"enum\": [\"DEBUG\",\"INFO\",\"WARN\",\"ERROR\"],\"default\":\"INFO\"},\"taskcfgAllLoglevelOrgKafka\": {\"description\":\"Log level for org kafka logs (DEBUG, INFO, ERROR, WARN)\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Log level kafka\",\"enum\": [\"DEBUG\",\"INFO\",\"WARN\",\"ERROR\"],\"default\":\"WARN\"},\"taskcfgAllLoglevelComMesosphere\": {\"description\":\"Log level for com mesosphere logs (DEBUG, INFO, ERROR, WARN)\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Log level COM mesosphere\",\"enum\": [\"DEBUG\",\"INFO\",\"WARN\",\"ERROR\"],\"default\":\"WARN\"},\"taskcfgAllLoglevelComMesosphereSdkExecutor\": {\"description\":\"Log level for com mesosphere sdk executor logs (DEBUG, INFO, ERROR, WARN, FATAL)\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Log level COM Mesosphere SDK executor\",\"enum\": [\"DEBUG\",\"INFO\",\"WARN\",\"ERROR\",\"FATAL\"],\"default\":\"FATAL\"},\"taskcfgAllLoglevelComStratio\": {\"description\":\"Log level for com stratio logs (DEBUG, INFO, ERROR, WARN)\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Log level COM Stratio\",\"enum\": [\"DEBUG\",\"INFO\",\"WARN\",\"ERROR\"],\"default\":\"WARN\"},\"moreLogs\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreLogs\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllLogSize\": {\"description\":\"Log size\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Log size (bytes)\",\"default\": 20930560}}}}}}},\"security\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": null,\"name\":\"security\",\"title\":\"Security\",\"description\":\"\",\"properties\": {\"taskcfgAllMesosSecurized\": {\"description\":\"Whether or not to deploy in a securized Mesos\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Mesos securized\",\"default\": true},\"taskcfgAllDynamicAutentication\": {\"description\":\"Enable/disable dynamic authentication\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Dynamic autentication\",\"default\": true},\"taskcfgAllTlsClientEnabled\": {\"description\":\"Whether or not to enable the TLS\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"TLS client (All tasks)\",\"default\": true},\"taskcfgAllAuthpluginEnabled\": {\"description\":\"Whether or not (true/false) to enableAuthorization plugin\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Authorization plugin\",\"default\": true},\"moreSecurity\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreSecurity\",\"title\":\"\",\"description\":\"\",\"properties\": {\"certificatesName\": {\"description\":\"certificates\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Certificates name\",\"default\":\"certificates\"},\"keystoreName\": {\"description\":\"broker\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Keystore name\",\"default\":\"broker\"},\"truststoreName\": {\"description\":\"ca-bundle\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Truststore name\",\"default\":\"ca-bundle\"}}},\"kafkaSecurity\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"kafkaSecurity\",\"title\":\"Kafka\",\"description\":\"\",\"properties\": {\"taskcfgAllEnableKafkaTls\": {\"description\":\"Whether or not to enable the TLS\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"TLS\",\"default\": true},\"taskcfgAllKafkaCertificatesDomain\": {\"description\":\"Internal certificates domain name\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Certificates domain\",\"default\":\"mesos\"},\"taskcfgAllKafkaExternalCertificatesDomain\": {\"description\":\"External certificates domain name. Only if Marathon-LB is activated.\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"External certificates domain\",\"default\":\"test\"},\"taskcfgAllIsZookeeperKafkaKerberized\": {\"description\":\"Whether or not zookeeper kafka is kerberized\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Zookeeper kerberized\",\"default\": true}}},\"gosec\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"gosec\",\"title\":\"GoSec\",\"description\":\"\",\"properties\": {\"taskcfgAllZookeeperHosts\": {\"description\":\"Zookeeper hosts\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Hosts\",\"default\":\"gosec1.node.paas.labs.stratio.com:2181,gosec2.node.paas.labs.stratio.com:2181,gosec3.node.paas.labs.stratio.com:2181\"},\"taskcfgAllZookeeperRootPath\": {\"description\":\"Zookeeper root path\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Root path\",\"default\":\"/stratio/gosec\"},\"taskcfgAllZookeeperWatchersEnabled\": {\"description\":\"Zookeeper watchers enabled\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Watchers\",\"default\": false},\"dyplon\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"dyplon\",\"title\":\"Dyplon\",\"description\":\"\",\"properties\": {\"taskcfgAllDyplonCacheEnabled\": {\"description\":\"Dyplon cache enabled\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Dyplon cache\",\"default\": true},\"taskcfgAllDyplonCacheTtl\": {\"description\":\"Dyplon cache TTL.\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cache TTL\",\"default\": 6000},\"dyplonScheduler\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"dyplonScheduler\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"allTasks\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"allTasks\",\"title\":\"\",\"description\":\"\",\"properties\": {}}}},\"audit\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"audit\",\"title\":\"Audit\",\"description\":\"\",\"properties\": {\"kafkaAuditEnabled\": {\"description\":\"Enable audit for kafka\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Enable audit for kafka\",\"default\": false},\"taskcfgAllKafkaHosts\": {\"description\":\"Hotsname of the kafka server to audit\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Hosts\",\"default\":\"gosec1.node.paas.labs.stratio.com:9092,gosec2.node.paas.labs.stratio.com:9092,gosec3.node.paas.labs.stratio.com:9092\"},\"taskcfgAllKafkaAuditTopic\": {\"description\":\"Name of the topic where write messages of audit\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Audit topic\",\"default\":\"audit\"}}},\"moreGoSec\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreGoSec\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllZookeeperConnectionTimeout\": {\"description\":\"Zookeeper connection timeout\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Connection timeout (ms)\",\"default\": 60000},\"taskcfgAllZookeeperSessionTimeout\": {\"description\":\"Zookeeper session timeout\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Session timeout (ms)\",\"default\": 60000}}}}},\"kerberos\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"kerberos\",\"title\":\"Kerberos\",\"description\":\"\",\"properties\": {\"kdcHost\": {\"description\":\"\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"KDC Host\",\"default\":\"kerberos.labs.stratio.com\"},\"kdcPort\": {\"description\":\"\",\"type\":\"number\",\"readOnly\": true,\"level\": 1,\"title\":\"KDC Port\",\"default\": 88},\"taskcfgAllKrbAdminHost\": {\"description\":\"KRB ADMIN HOST for generating krb5.conf\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"KAdmin Host\",\"default\":\"kerberos.labs.stratio.com\"},\"taskcfgAllKrbAdminPort\": {\"description\":\"KRB ADMIN PORT for generating krb5.conf\",\"type\":\"number\",\"readOnly\": true,\"level\": 1,\"title\":\"KAdmin Port\",\"default\": 749},\"taskcfgAllKrbRealm\": {\"description\":\"KRB USER for generating krb5.conf\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Realm\",\"default\":\"PRUEBA.STRATIO.COM\"},\"taskcfgAllEnableKrb5Debug\": {\"description\":\"Enable krb5 debug option\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"KRB5 debug\",\"default\": true}}}}},\"streaming\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": null,\"name\":\"streaming\",\"title\":\"Streaming\",\"description\":\"\",\"properties\": {\"kafkaGeneral\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"kafkaGeneral\",\"title\":\"\",\"description\":\"\",\"properties\": {}},\"taskcfgAllKafkaDeleteTopicEnable\": {\"description\":\"Enables delete topic. Delete topic through the admin tool will have no effect if this config is turned off\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Delete topic\",\"default\": false},\"taskcfgAllKafkaAutoCreateTopicsEnable\": {\"description\":\"Enables auto creation of topic on the server\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Auto create topics\",\"default\": true},\"taskcfgAllKafkaNumPartitions\": {\"description\":\"The default number of log partitions per topic\",\"type\":\"number\",\"minimum\": 1,\"readOnly\": false,\"level\": 1,\"title\":\"Partitions\",\"default\": 1},\"taskcfgAllKafkaDefaultReplicationFactor\": {\"description\":\"Default replication factors for automatically created topics\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Default replication factor\",\"default\": 1},\"moreStreaming\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreStreaming\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaSuperuserRegex\": {\"description\":\"Super user regex for server properties\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Superuser regex\",\"default\":\"kafka-[0-9]+-broker\"},\"taskcfgAllKafkaCompressionType\": {\"description\":\"Specify the final compression type for a given topic. This configuration accepts the standard compression codecs ('gzip','snappy', lz4). It additionally accepts'uncompressed' which is equivalent to no compression; and'producer' which means retain the original compression codec set by the producer\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Compression type\",\"enum\": [\"gzip\",\"snappy\",\"lz4\",\"producer\",\"uncompressed\"],\"default\":\"producer\"},\"taskcfgAllKafkaBackgroundThreads\": {\"description\":\"The number of threads to use for various background processing tasks\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Kafka background threads\",\"default\": 10},\"taskcfgAllKafkaMaxIncrementalFetchSessionCacheSlots\": {\"description\":\"The maximum number of incremental fetch sessions that we will maintain\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Max incremental fetch session cache slots\",\"default\": 1000},\"taskcfgAllKafkaControllerSocketTimeoutMs\": {\"description\":\"The socket timeout for controller-to-broker channels\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Controller socket timeout (ms)\",\"default\": 30000},\"taskcfgAllKafkaQuotaConsumerDefault\": {\"description\":\"Any consumer distinguished by clientId/consumer group will get throttled if it fetches more bytes than this value per-second\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Quota consumer default (bytes)\",\"default\": 9223372000000000000},\"taskcfgAllKafkaQuotaProducerDefault\": {\"description\":\"Any producer distinguished by clientId will get throttled if it produces more bytes than this value per-second\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Quota producer default (bytes)\",\"default\": 9223372000000000000},\"taskcfgAllKafkaProducerPurgatoryPurgeIntervalRequests\": {\"description\":\"The purge interval (in number of requests) of the producer request purgatory\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Producer purgatory purge interval requests\",\"default\": 1000},\"taskcfgAllKafkaFetchPurgatoryPurgeIntervalRequests\": {\"description\":\"The purge interval (in number of requests) of the fetch request purgatory\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Fetch purgatory purge interval requests\",\"default\": 1000},\"taskcfgAllKafkaQuotaWindowNum\": {\"description\":\"The number of samples to retain in memory\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Number of samples to retain in memory\",\"default\": 11},\"taskcfgAllKafkaQuotaWindowSizeSeconds\": {\"description\":\"The time span of each sample\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Time span of each sample (seconds)\",\"default\": 1},\"taskcfgAllKafkaGroupMinSessionTimeoutMs\": {\"description\":\"The minimum allowed session timeout for registered consumers\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Kafka group min session timeout (ms)\",\"default\": 6000},\"taskcfgAllKafkaGroupMaxSessionTimeoutMs\": {\"description\":\"The maximum allowed session timeout for registered consumers\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Kafka group max session timeout (ms)\",\"default\": 300000},\"taskcfgAllKafkaInterBrokerProtocolVersion\": {\"description\":\"Specify which version of the inter-broker protocol will be used, which must align with log.message.format.version. This is typically bumped after all brokers were upgraded to a new version. Example of some valid values are: 0.8.0, 0.8.1, 0.8.1.1, 0.8.2, 0.8.2.0, 0.8.2.1, 0.9.0.0, 0.9.0.1, 0.10.0.0. Check ApiVersion for the full list\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"inter-broker protocol version\",\"default\":\"1.0\"},\"taskcfgAllKafkaNumIoThreads\": {\"description\":\"The number of io threads that the server uses for carrying out network requests\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Num of IO threads\",\"default\": 8},\"taskcfgAllKafkaNumNetworkThreads\": {\"description\":\"The number of network threads that the server uses for handling network requests\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Num of network threads\",\"default\": 3},\"taskcfgAllKafkaNumRecoveryThreadsPerDataDir\": {\"description\":\"The number of threads per data directory to be used for log recovery at startup and flushing at shutdown\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Num of recovery threads per data dir\",\"default\": 1},\"taskcfgAllKafkaQueuedMaxRequests\": {\"description\":\"The number of queued requests allowed before blocking the network threads\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Kafka queued max requests\",\"default\": 500},\"taskcfgAllKafkaReservedBrokerMaxId\": {\"description\":\"Max number that can be used for a broker.id\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Broker max ID\",\"default\": 1000},\"taskcfgAllKafkaSocketReceiveBufferBytes\": {\"description\":\"The SO_RCVBUF buffer of the socket sever sockets\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Socket receive buffer (bytes)\",\"default\": 102400},\"taskcfgAllKafkaSocketRequestMaxBytes\": {\"description\":\"The maximum number of bytes in a socket request\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Socket request max (bytes)\",\"default\": 104857600},\"taskcfgAllKafkaSocketSendBufferBytes\": {\"description\":\"The SO_SNDBUF buffer of the socket sever sockets\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Socket send buffer (bytes)\",\"default\": 102400}}},\"controlledShutdown\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"controlledShutdown\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaControlledShutdownEnable\": {\"description\":\"Enable controlled shutdown of the server\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Controlled shutdown\",\"default\": true},\"taskcfgAllKafkaControlledShutdownMaxRetries\": {\"description\":\"Controlled shutdown can fail for multiple reasons. This determines the number of retries when such failure happens\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Max retries\",\"default\": 3},\"taskcfgAllKafkaControlledShutdownRetryBackoffMs\": {\"description\":\"Before each retry, the system needs time to recover from the state that caused the previous failure (Controller fail over, replica lag etc). This config determines the amount of time to wait before retrying\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Retry backoff (ms)\",\"default\": 5000}}},\"leaderRebalance\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"leaderRebalance\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaAutoLeaderRebalanceEnable\": {\"description\":\"Enables auto leader balancing. A background thread checks and triggers leader balance if required at regular intervals\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Auto leader rebalance\",\"default\": true},\"taskcfgAllKafkaLeaderImbalanceCheckIntervalSeconds\": {\"description\":\"The frequency with which the partition rebalance check is triggered by the controller\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Leader imbalance check interval (seconds)\",\"default\": 300},\"taskcfgAllKafkaLeaderImbalancePerBrokerPercentage\": {\"description\":\"The ratio of leader imbalance allowed per broker. The controller would trigger a leader balance if it goes above this value per broker. The value is specified in percentage\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Leader imbalance per broker (percentage)\",\"default\": 10}}},\"replicas\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"replicas\",\"title\":\"Replicas\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaUncleanLeaderElectionEnable\": {\"description\":\"Indicates whether to enable replicas or not in the ISR set to be elected as leader as a last resort, even though doing so may result in data loss\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Replicas\",\"default\": true},\"taskcfgAllKafkaNumReplicaFetchers\": {\"description\":\"Number of fetcher threads used to replicate messages from a source broker. Increasing this value can increase the degree of I/O parallelism in the follower broker\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Num of replica fetchers\",\"default\": 1},\"taskcfgAllKafkaMinInsyncReplicas\": {\"description\":\"Define the minimum number of replicas in ISR needed to satisfy a produce request with required.acks=-1 (or all)\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Min InSync replicas\",\"default\": 1},\"moreReplicas\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreReplicas\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaReplicaFetchBackoffMs\": {\"description\":\"The amount of time to sleep when fetch partition error occurs\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Replica fetch backoff (ms)\",\"default\": 1000},\"taskcfgAllKafkaReplicaFetchMaxBytes\": {\"description\":\"The number of byes of messages to attempt to fetch\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Replica fetch max (bytes)\",\"default\": 1048576},\"taskcfgAllKafkaReplicaFetchMinBytes\": {\"description\":\"Minimum bytes expected for each fetch response. If not enough bytes, wait up to replicaMaxWaitTimeMs\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Replica fetch min (bytes)\",\"default\": 1},\"taskcfgAllKafkaReplicaFetchWaitMaxMs\": {\"description\":\"Max wait time for each fetcher request issued by follower replicas. This value should always be less than the replica.lag.time.max.ms at all times to prevent frequent shrinking of ISR for low throughput topics\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Replica fetch wait max time (ms)\",\"default\": 500},\"taskcfgAllKafkaReplicaHighWatermarkCheckpointIntervalMs\": {\"description\":\"The frequency with which the high watermark is saved out to disk\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"High watermark checkpoint interval (ms)\",\"default\": 5000},\"taskcfgAllKafkaReplicaLagTimeMaxMs\": {\"description\":\"If a follower hasn't sent any fetch requests or hasn't consumed up to the leaders log end offset for at least this time, the leader will remove the follower from isr\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Lag time max (ms)\",\"default\": 10000},\"taskcfgAllKafkaReplicaSocketReceiveBufferBytes\": {\"description\":\"The socket receive buffer for network requests\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Socket receive buffer (bytes)\",\"default\": 65536},\"taskcfgAllKafkaReplicaSocketTimeoutMs\": {\"description\":\"The socket timeout for network requests. Its value should be at least replica.fetch.wait.max.ms\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Socket timeout (ms)\",\"default\": 30000},\"taskcfgAllKafkaRequestTimeoutMs\": {\"description\":\"The configuration controls the maximum amount of time the client will wait for the response of a request. If the response is not received before the timeout elapses the client will resend the request if necessary or fail the request if retries are exhausted\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Request timeout (ms)\",\"default\": 30000}}}}},\"offsets\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"offsets\",\"title\":\"Offsets\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaOffsetMetadataMaxBytes\": {\"description\":\"The maximum size for a metadata entry associated with an offset commit\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Metadata max (bytes)\",\"default\": 4096},\"taskcfgAllKafkaOffsetsLoadBufferSize\": {\"description\":\"Batch size for reading from the offsets segments when loading offsets into the cache\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Load buffer size (bytes)\",\"default\": 5242880},\"taskcfgAllKafkaOffsetsCommitRequiredAcks\": {\"description\":\"The required acks before the commit can be accepted. In general, the default (-1) should not be overridden\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Commit required ACKs\",\"default\": -1},\"taskcfgAllKafkaOffsetsCommitTimeoutMs\": {\"description\":\"Offset commit will be delayed until all replicas for the offsets topic receive the commit or this timeout is reached. This is similar to the producer request timeout\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Commit timeout (ms)\",\"default\": 5000},\"taskcfgAllKafkaOffsetsRetentionCheckIntervalMs\": {\"description\":\"Frequency at which to check for stale offsets\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Retention check interval (ms)\",\"default\": 600000},\"taskcfgAllKafkaOffsetsRetentionMinutes\": {\"description\":\"Log retention window in minutes for offsets topic\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Retention (minutes)\",\"default\": 1440},\"taskcfgAllKafkaOffsetsTopicNumPartitions\": {\"description\":\"The number of partitions for the offset commit topic (should not change after deployment)\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Number of Topic partitions\",\"default\": 50},\"taskcfgAllKafkaOffsetsTopicCompressionCodec\": {\"description\":\"Compression codec for the offsets topic - compression may be used to achieve'atomic' commits\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Topic compression codec\",\"default\": 0},\"taskcfgAllKafkaOffsetsTopicReplicationFactor\": {\"description\":\"The replication factor for the offsets topic (set higher to ensure availability). To ensure that the effective replication factor of the offsets topic is the configured value, the number of alive brokers has to be at least the replication factor at the time of the first request for the offsets topic. If not, either the offsets topic creation will fail or it will get a replication factor of min(alive brokers, configured replication factor)\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Topic replication factor\",\"default\": 1},\"taskcfgAllKafkaOffsetsTopicSegmentBytes\": {\"description\":\"The offsets topic segment bytes should be kept relatively small in order to facilitate faster log compaction and cache loads\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Topic segment (bytes)\",\"default\": 104857600}}},\"metrics\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"metrics\",\"title\":\"Metrics\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaMetricsNumSamples\": {\"description\":\"The number of samples maintained to compute metrics\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Num samples\",\"default\": 2},\"taskcfgAllKafkaMetricsSampleWindowMs\": {\"description\":\"The number of samples maintained to compute metrics\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Sample window (ms)\",\"default\": 30000}}},\"connections\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"connections\",\"title\":\"Connections\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaMaxConnectionsPerIp\": {\"description\":\"mum number of connections we allow from each ip address\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Max connections per IP\",\"default\": 2147483647},\"taskcfgAllKafkaConnectionsMaxIdleMs\": {\"description\":\"Idle connections timeout: the server socket processor threads close the connections that idle more than this\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Max idle connections timeout (ms)\",\"default\": 600000},\"taskcfgAllKafkaMaxConnectionsPerIpOverrides\": {\"description\":\"Per-ip or hostname overrides to the default maximum number of connections\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Max connections per IP overrides\",\"default\":\"\"}}},\"kafkaZookeeper\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"kafkaZookeeper\",\"title\":\"Zookeeper of the Kafka cluster\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaZookeeper\": {\"description\":\"\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Hosts\",\"default\":\"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\"},\"taskcfgAllKafkaZookeeperMaxInFlightRequests\": {\"description\":\"The maximum number of unacknowledged requests the client will send to Zookeeper before blocking\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Max unacknowledged requests\",\"default\": 10},\"taskcfgAllKafkaZookeeperSyncTimeMs\": {\"description\":\"How far a ZK follower can be behind a ZK leader\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Sync time (ms)\",\"default\": 2000},\"taskcfgAllKafkaZookeeperSessionTimeoutMs\": {\"description\":\"Zookeeper session timeout\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Session timeout (ms)\",\"default\": 6000}}},\"kafkaLog\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"kafkaLog\",\"title\":\"Log\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaLogPreallocate\": {\"description\":\"Should pre allocate file when create new segment? If you are using Kafka on Windows, you probably need to set it to true\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Preallocate Kafka log\",\"default\": false},\"taskcfgAllKafkaLogMessageFormatVersion\": {\"description\":\"Specify which version of the log message format will be used, which must align with inter.broker.protocol.version. This is a new setting as of 0.10.0.0, and should be left at 0.9.0 until clients are updated to 0.10.0.x. Clients on earlier versions may see a performance penalty if this is increased before they've upgraded. See the latest Kafka documentation for details\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Kafka log message format version\",\"default\":\"1.0\"},\"taskcfgAllKafkaMessageMaxBytes\": {\"description\":\"The maximum size of message that the server can receive\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Message max size (bytes)\",\"default\": 1000012},\"cleaner\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"cleaner\",\"title\":\"Cleaner\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaLogCleanerEnable\": {\"description\":\"Enable the log cleaner process to run on the server? Should be enabled if using any topics with a cleanup.policy=compact including the internal offsets topic. If disabled those topics will not be compacted and continually grow in size\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner\",\"default\": true},\"taskcfgAllKafkaLogCleanupPolicy\": {\"description\":\"The default cleanup policy for segments beyond the retention window, must be either'delete' or'compact'\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Log cleanup policy\",\"default\":\"delete\"},\"moreCleaner\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreCleaner\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaLogCleanerBackoffMs\": {\"description\":\"The amount of time to sleep when there are no logs to clean\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner backoff (ms)\",\"default\": 15000},\"taskcfgAllKafkaLogCleanerDedupeBufferSize\": {\"description\":\"The total memory used for log deduplication across all cleaner threads\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner dedupe buffer size (bytes)\",\"default\": 134217728},\"taskcfgAllKafkaLogCleanerDeleteRetentionMs\": {\"description\":\"How long are delete records retained?\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner delete retention (ms)\",\"default\": 86400000},\"taskcfgAllKafkaLogCleanerIoBufferLoadFactor\": {\"description\":\"Log cleaner dedupe buffer load factor. The percentage full the dedupe buffer can become. A higher value will allow more log to be cleaned at once but will lead to more hash collisions\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner IO buffer load factor\",\"default\": 0.9},\"taskcfgAllKafkaLogCleanerIoBufferSize\": {\"description\":\"The total memory used for log cleaner I/O buffers across all cleaner threads\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner IO buffer size (bytes)\",\"default\": 524288},\"taskcfgAllKafkaLogCleanerIoMaxBytesPerSecond\": {\"description\":\"The log cleaner will be throttled so that the sum of its read and write i/o will be less than this value on average\",\"type\":\"string\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner IO max bytes per second\",\"default\":\"Infinity\"},\"taskcfgAllKafkaLogCleanerMinCleanableRatio\": {\"description\":\"The minimum ratio of dirty log to total log for a log to eligible for cleaning\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner min cleanable ratio\",\"default\": 0.5},\"taskcfgAllKafkaLogCleanerThreads\": {\"description\":\"The number of background threads to use for log cleaning\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Cleaner threads\",\"default\": 1}}}}},\"moreKafkaLog\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"moreKafkaLog\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllKafkaLogFlushIntervalMessages\": {\"description\":\"The number of messages accumulated on a log partition before messages are flushed to disk\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Flush interval messages\",\"default\": 9223372000000000000},\"taskcfgAllKafkaLogFlushOffsetCheckpointIntervalMs\": {\"description\":\"The frequency with which we update the persistent record of the last flush which acts as the log recovery point\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Flush offset checkpoint interval (ms)\",\"default\": 60000},\"taskcfgAllKafkaLogFlushSchedulerIntervalMs\": {\"description\":\"The frequency in ms that the log flusher checks whether any log needs to be flushed to disk\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Flush scheduler interval (ms)\",\"default\": 9223372000000000000},\"taskcfgAllKafkaLogIndexIntervalBytes\": {\"description\":\"The interval with which we add an entry to the offset index\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Index interval (bytes)\",\"default\": 4096},\"taskcfgAllKafkaLogIndexSizeMaxBytes\": {\"description\":\"The maximum size in bytes of the offset index\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Index size max (bytes)\",\"default\": 10485760},\"taskcfgAllKafkaLogRetentionHours\": {\"description\":\"The number of hours to keep a log file before deleting it (in hours), tertiary to log.retention.ms property\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Log retention (hours)\",\"default\": 168},\"taskcfgAllKafkaLogRetentionBytes\": {\"description\":\"The maximum size of the log before deleting it\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Log retention (bytes)\",\"default\": -1},\"taskcfgAllKafkaLogRetentionCheckIntervalMs\": {\"description\":\"The frequency in milliseconds that the log cleaner checks whether any log is eligible for deletion\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Retention check interval (ms)\",\"default\": 300000},\"taskcfgAllKafkaLogRollHours\": {\"description\":\"The maximum time before a new log segment is rolled out (in hours), secondary to log.roll.ms property\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Log roll (hours)\",\"default\": 168},\"taskcfgAllKafkaLogRollJitterHours\": {\"description\":\"The maximum jitter to subtract from logRollTimeMillis (in hours), secondary to log.roll.jitter.ms property\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Log roll jitter (hours)\",\"default\": 0},\"taskcfgAllKafkaLogSegmentBytes\": {\"description\":\"The maximum size of a single log file\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Log segment (bytes)\",\"default\": 1073741824},\"taskcfgAllKafkaLogSegmentDeleteDelayMs\": {\"description\":\"The amount of time to wait before deleting a file from the filesystem\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Segment delete delay (ms)\",\"default\": 60000},\"taskcfgAllKafkaAlterLogDirsReplicationQuotaWindowNum\": {\"description\":\"The number of samples to retain in memory for alter log dirs replication quotas\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Dirs replication quota\",\"default\": 11},\"taskcfgAllKafkaAlterLogDirsReplicationQuotaWindowSizeSeconds\": {\"description\":\"The time span of each sample for alter log dirs replication quotas\",\"type\":\"number\",\"readOnly\": false,\"level\": 1,\"title\":\"Dirs replication quota size (seconds)\",\"default\": 1}}}}}}},\"environment\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": null,\"name\":\"environment\",\"title\":\"\",\"description\":\"\",\"properties\": {\"vault\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"standard\"},\"name\":\"vault\",\"title\":\"Vault\",\"description\":\"\",\"properties\": {\"taskcfgAllVaultHosts\": {\"description\":\"Vault Host for downloading secrets\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Hosts\",\"default\":\"vault.service.paas.labs.stratio.com\"},\"taskcfgAllVaultPort\": {\"description\":\"Vault Port for downloading secrets\",\"type\":\"number\",\"readOnly\": true,\"level\": 1,\"title\":\"Port\",\"default\": 8200}}},\"ldap\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"ldap\",\"title\":\"Ldap\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapHost\": {\"description\":\"Ldap host\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Host\",\"default\":\"ldap.labs.stratio.com\"},\"taskcfgAllLdapPort\": {\"description\":\"Ldap port\",\"type\":\"number\",\"readOnly\": true,\"level\": 1,\"title\":\"Port\",\"default\": 636},\"taskcfgAllLdapAuthentication\": {\"description\":\"Ldap authentication type\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Authentication\",\"default\":\"simple\"},\"taskcfgAllLdapSecureConnection\": {\"description\":\"Ldap secure connection enabled\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Secure connection\",\"default\": true},\"ldapValidation\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"switch\"},\"name\":\"ldapValidation\",\"title\":\"Validation\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapPoolValidateperiodically\": {\"description\":\"validate periodically (parameter included for Active Directory integration)\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Validate periodically\",\"default\": true},\"taskcfgAllLdapPoolValidateperiod\": {\"description\":\"validate period (parameter included for Active Directory integration)\",\"type\":\"number\",\"readOnly\": true,\"level\": 1,\"title\":\"Validate period (seconds)\",\"default\": 30},\"taskcfgAllLdapPoolValidateoncheckin\": {\"description\":\"validate on checkin (parameter included for Active Directory integration)\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Validate on checkin\",\"default\": false},\"taskcfgAllLdapPoolValidateoncheckout\": {\"description\":\"validate on checkout (parameter included for Active Directory integration)\",\"type\":\"boolean\",\"readOnly\": false,\"level\": 1,\"title\":\"Validate on checkout\",\"default\": true}}},\"ldapPoolSize\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"ldapPoolSize\",\"title\":\"Pool size\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapPoolMaxpoolsize\": {\"description\":\"ldap max pool size (parameter included for Active Directory integration)\",\"type\":\"number\",\"readOnly\": true,\"level\": 1,\"title\":\"Max pool size\",\"default\": 10},\"taskcfgAllLdapPoolMinpoolsize\": {\"description\":\"ldap min pool size (parameter included for Active Directory integration)\",\"type\":\"number\",\"readOnly\": true,\"level\": 1,\"title\":\"Min pool size\",\"default\": 1}}},\"ldapQueries\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"ldapQueries\",\"title\":\"Queries\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapUserQueryAll\": {\"description\":\"LDAP filter to retrieve all users\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Query to retrieve all users\",\"default\":\"(&(objectClass=person)%filter)\"},\"taskcfgAllLdapGroupQueryAll\": {\"description\":\"LDAP filter to retrieve all users\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Query to retrieve all groups\",\"default\":\"(&(objectClass=posixGroup)(!(ou=Groups))%filter)\"},\"taskcfgAllLdapGroupQueryByUser\": {\"description\":\"LDAP filter to retrieve all the groups a user is member of\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Query to retrieve groups by user\",\"default\":\"(&(objectClass=posixGroup)(!(ou=Groups))(memberUid=uid=%filter,ou=People,dc=stratio,dc=com))\"}}},\"ldapDomains\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"ldapDomains\",\"title\":\"Domains\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapUserDomain\": {\"description\":\"baseDN to use when performing LDAP User query\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"User domain\",\"default\":\"ou=People,dc=stratio,dc=com\"},\"taskcfgAllLdapGroupDomain\": {\"description\":\"baseDN to use when performing LDAP Group queries\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Group domain\",\"default\":\"ou=Groups,dc=stratio,dc=com\"}}},\"ldapUserAttributes\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"ldapUserAttributes\",\"title\":\"User attributes\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapUserId\": {\"description\":\"LDAP attribute to use as User ID\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"ID\",\"default\":\"uid\"},\"taskcfgAllLdapUserName\": {\"description\":\"LDAP attribute to use as User Name\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Name\",\"default\":\"cn\"},\"taskcfgAllLdapUserDescription\": {\"description\":\"LDAP attribute to use as User Description\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Description\",\"default\":\"description\"},\"taskcfgAllLdapUserMail\": {\"description\":\"LDAP attribute to use as User e-mail\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Mail\",\"default\":\"mail\"}}},\"ldapGroupAttributes\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"accordion\"},\"name\":\"ldapGroupAttributes\",\"title\":\"Group attributes\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapGroupId\": {\"description\":\"LDAP attribute to use as Group ID\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"ID\",\"default\":\"cn\"},\"taskcfgAllLdapGroupDescription\": {\"description\":\"LDAP attribute to use as Group Description\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Description\",\"default\":\"description\"},\"taskcfgAllLdapGroupMember\": {\"description\":\"LDAP attribute to determine if a User is member of a Group\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Group member\",\"default\":\"memberUid\"}}},\"ldapMore\": {\"type\":\"object\",\"additionalProperties\": false,\"ui\": {\"component\":\"show-more\"},\"name\":\"ldapMore\",\"title\":\"\",\"description\":\"\",\"properties\": {\"taskcfgAllLdapPrincipal\": {\"description\":\"ldap principal\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"LDAP principal\",\"default\":\"cn=ldaproot,dc=stratio,dc=com\"},\"taskcfgAllLdapDefaultrealm\": {\"description\":\"ldap defaultRealm (parameter included for Active Directory integration)\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Default Realm\",\"default\":\"stratio.com\"},\"taskcfgAllLdapSchema\": {\"description\":\"LDAP schema used in the LDAP server\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Schema\",\"default\":\"RFC2307bis\"},\"taskcfgAllLdapMatchingGroupLinkuser\": {\"description\":\"Ldap matching group linkuser (parameter included for Active Directory integration)\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Group link user\",\"default\":\"uid=%filter,ou=People,dc=stratio,dc=com\"},\"taskcfgAllLdapMappingDistinguishedName\": {\"description\":\"ldap mapping distinguished name (parameter included for Active Directory integration)\",\"type\":\"string\",\"readOnly\": true,\"level\": 1,\"title\":\"Distinguished name\",\"default\":\"distinguishedname\"}}}}}}}}}";
        String expected = "{\"general\":{\"kafkaZookeeper\":\"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\",\"resources\":{\"CPUs\":1,\"MEM\":1230,\"INSTANCES\":1},\"mesosPrincipal\":\"open\",\"serviceId\":\"jumanji-\"},\"settings\":{\"mapping\":{},\"brokers\":{\"brokerCpus\":1,\"brokerDiskSize\":5000,\"jmxAll\":{\"taskcfgAllJmxReporterPath\":\"jmxexporter\",\"exporter\":{},\"jmxPort\":9102,\"taskcfgAllJmxAuthenticate\":true,\"taskcfgAllJmxReporterEnabled\":true,\"taskcfgAllJmxEnabled\":true,\"jmxEnabled\":true,\"taskcfgAllJmxReporterPort\":8085,\"taskcfgAllJmxPort\":9102,\"taskcfgAllJmxReporterVersion\":\"0.10\"},\"brokerDiskType\":\"ROOT\",\"brokerCount\":2,\"brokerMem\":4096,\"brokerDiskPath\":\"kafka-broker-data\",\"moreBrokers\":{\"taskcfgAllBrokerPort\":9093,\"taskcfgAllJavaOpts\":\"\",\"brokerJavaHeap\":2048}},\"package\":{},\"taskcfgAllEnableAvailabilityZones\":false,\"moreSettings\":{\"taskcfgAllLeaderMesos\":\"http://leader.mesos:5050/slaves\",\"mesosApiVersion\":\"V0\"},\"readiness\":{\"readinessTimeout\":10,\"readinessInterval\":30,\"readinessDelay\":0,\"readinessCheckEnable\":true},\"manifest\":{},\"producers\":{\"producerMem\":512,\"producerCpus\":0.5},\"commonscli\":{},\"healthcheck\":{\"taskcfgAllTimestampThreshold\":60000,\"healthcheckDelay\":20,\"healthCheckEnable\":true,\"healthcheckGracePeriod\":180,\"healthcheckMaxFailures\":20,\"healthcheckInterval\":30,\"healthcheckTimeout\":20},\"uris\":{\"zkconnectUri\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/kafka/eos-framework-kafka-zkconnect/2.1.1-4e72fef/eos-framework-kafka-zkconnect-2.1.1-4e72fef.zip\",\"executorUri\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/stratio-framework-dcos-commons-executor/1.0.1-9973f3b/stratio-framework-dcos-commons-executor-1.0.1-9973f3b.zip\",\"libmesosUri\":\"${eos.artifactRepository}/repository/public/com/mesosphere/libmesos-bundle/1.9.0-rc2-1.2.0-rc2/libmesos-bundle-1.9.0-rc2-1.2.0-rc2-1.tar.gz\",\"zookeeperUri\":\"${eos.artifactRepository}/repository/public/org/apache/zookeeper/zookeeper/3.5.3-beta/zookeeper-3.5.3-beta.jar\",\"svcymlUri\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/kafka/eos-framework-kafka/2.1.1-4e72fef/eos-framework-kafka-2.1.1-4e72fef-svc.yml\",\"bootstrapUri\":\"${eos.artifactRepository}/repository/public/com/stratio/framework/stratio-framework-dcos-commons-cli/1.0.1-9973f3b/stratio-framework-dcos-commons-cli-1.0.1-9973f3b-bootstrap.zip\"},\"framework\":{},\"Statsd\":{},\"frameworkUser\":\"root\",\"logs\":{\"taskcfgAllLoglevelOrgKafka\":\"WARN\",\"taskcfgAllLoglevelComMesosphere\":\"WARN\",\"moreLogs\":{\"taskcfgAllLogSize\":20930560},\"taskcfgAllLoglevelRoot\":\"INFO\",\"taskcfgAllLoglevelComMesosphereSdkExecutor\":\"FATAL\",\"taskcfgAllLoglevelComStratio\":\"WARN\"},\"consumer\":{\"consumerCpus\":0.5,\"consumerMem\":512}},\"security\":{\"taskcfgAllTlsClientEnabled\":true,\"taskcfgAllAuthpluginEnabled\":true,\"taskcfgAllMesosSecurized\":true,\"gosec\":{\"taskcfgAllZookeeperRootPath\":\"/stratio/gosec\",\"taskcfgAllZookeeperHosts\":\"gosec1.node.paas.labs.stratio.com:2181,gosec2.node.paas.labs.stratio.com:2181,gosec3.node.paas.labs.stratio.com:2181\",\"dyplon\":{\"taskcfgAllDyplonCacheEnabled\":true,\"allTasks\":{},\"taskcfgAllDyplonCacheTtl\":6000,\"dyplonScheduler\":{}},\"taskcfgAllZookeeperWatchersEnabled\":false,\"moreGoSec\":{\"taskcfgAllZookeeperConnectionTimeout\":60000,\"taskcfgAllZookeeperSessionTimeout\":60000},\"audit\":{\"kafkaAuditEnabled\":false,\"taskcfgAllKafkaAuditTopic\":\"audit\",\"taskcfgAllKafkaHosts\":\"gosec1.node.paas.labs.stratio.com:9092,gosec2.node.paas.labs.stratio.com:9092,gosec3.node.paas.labs.stratio.com:9092\"}},\"taskcfgAllDynamicAutentication\":true,\"moreSecurity\":{\"certificatesName\":\"certificates\",\"truststoreName\":\"ca-bundle\",\"keystoreName\":\"broker\"},\"kafkaSecurity\":{\"taskcfgAllKafkaExternalCertificatesDomain\":\"test\",\"taskcfgAllKafkaCertificatesDomain\":\"mesos\",\"taskcfgAllIsZookeeperKafkaKerberized\":true,\"taskcfgAllEnableKafkaTls\":true},\"kerberos\":{\"kdcPort\":88,\"kdcHost\":\"kerberos.labs.stratio.com\",\"taskcfgAllKrbAdminHost\":\"kerberos.labs.stratio.com\",\"taskcfgAllEnableKrb5Debug\":true,\"taskcfgAllKrbRealm\":\"PRUEBA.STRATIO.COM\",\"taskcfgAllKrbAdminPort\":749}},\"streaming\":{\"kafkaZookeeper\":{\"taskcfgAllKafkaZookeeperMaxInFlightRequests\":10,\"taskcfgAllKafkaZookeeperSessionTimeoutMs\":6000,\"taskcfgAllKafkaZookeeper\":\"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\",\"taskcfgAllKafkaZookeeperSyncTimeMs\":2000},\"replicas\":{\"taskcfgAllKafkaNumReplicaFetchers\":1,\"taskcfgAllKafkaMinInsyncReplicas\":1,\"moreReplicas\":{\"taskcfgAllKafkaReplicaSocketTimeoutMs\":30000,\"taskcfgAllKafkaReplicaHighWatermarkCheckpointIntervalMs\":5000,\"taskcfgAllKafkaReplicaFetchMaxBytes\":1048576,\"taskcfgAllKafkaReplicaFetchMinBytes\":1,\"taskcfgAllKafkaReplicaFetchWaitMaxMs\":500,\"taskcfgAllKafkaReplicaSocketReceiveBufferBytes\":65536,\"taskcfgAllKafkaRequestTimeoutMs\":30000,\"taskcfgAllKafkaReplicaFetchBackoffMs\":1000,\"taskcfgAllKafkaReplicaLagTimeMaxMs\":10000},\"taskcfgAllKafkaUncleanLeaderElectionEnable\":true},\"controlledShutdown\":{\"taskcfgAllKafkaControlledShutdownEnable\":true,\"taskcfgAllKafkaControlledShutdownMaxRetries\":3,\"taskcfgAllKafkaControlledShutdownRetryBackoffMs\":5000},\"kafkaLog\":{\"taskcfgAllKafkaLogMessageFormatVersion\":\"1.0\",\"moreKafkaLog\":{\"taskcfgAllKafkaLogIndexIntervalBytes\":4096,\"taskcfgAllKafkaLogFlushSchedulerIntervalMs\":9223372000000000000,\"taskcfgAllKafkaLogRollJitterHours\":0,\"taskcfgAllKafkaLogRetentionHours\":168,\"taskcfgAllKafkaLogSegmentDeleteDelayMs\":60000,\"taskcfgAllKafkaAlterLogDirsReplicationQuotaWindowSizeSeconds\":1,\"taskcfgAllKafkaLogRetentionBytes\":-1,\"taskcfgAllKafkaLogRollHours\":168,\"taskcfgAllKafkaLogFlushOffsetCheckpointIntervalMs\":60000,\"taskcfgAllKafkaLogIndexSizeMaxBytes\":10485760,\"taskcfgAllKafkaLogSegmentBytes\":1073741824,\"taskcfgAllKafkaAlterLogDirsReplicationQuotaWindowNum\":11,\"taskcfgAllKafkaLogFlushIntervalMessages\":9223372000000000000,\"taskcfgAllKafkaLogRetentionCheckIntervalMs\":300000},\"cleaner\":{\"taskcfgAllKafkaLogCleanerEnable\":true,\"taskcfgAllKafkaLogCleanupPolicy\":\"delete\",\"moreCleaner\":{\"taskcfgAllKafkaLogCleanerIoBufferLoadFactor\":0.9,\"taskcfgAllKafkaLogCleanerIoBufferSize\":524288,\"taskcfgAllKafkaLogCleanerMinCleanableRatio\":0.5,\"taskcfgAllKafkaLogCleanerThreads\":1,\"taskcfgAllKafkaLogCleanerDeleteRetentionMs\":86400000,\"taskcfgAllKafkaLogCleanerIoMaxBytesPerSecond\":\"Infinity\",\"taskcfgAllKafkaLogCleanerDedupeBufferSize\":134217728,\"taskcfgAllKafkaLogCleanerBackoffMs\":15000}},\"taskcfgAllKafkaMessageMaxBytes\":1000012,\"taskcfgAllKafkaLogPreallocate\":false},\"taskcfgAllKafkaDefaultReplicationFactor\":1,\"kafkaGeneral\":{},\"moreStreaming\":{\"taskcfgAllKafkaGroupMinSessionTimeoutMs\":6000,\"taskcfgAllKafkaReservedBrokerMaxId\":1000,\"taskcfgAllKafkaGroupMaxSessionTimeoutMs\":300000,\"taskcfgAllKafkaNumRecoveryThreadsPerDataDir\":1,\"taskcfgAllKafkaCompressionType\":\"producer\",\"taskcfgAllKafkaQueuedMaxRequests\":500,\"taskcfgAllKafkaQuotaWindowSizeSeconds\":1,\"taskcfgAllKafkaNumIoThreads\":8,\"taskcfgAllKafkaQuotaConsumerDefault\":9223372000000000000,\"taskcfgAllKafkaQuotaProducerDefault\":9223372000000000000,\"taskcfgAllKafkaMaxIncrementalFetchSessionCacheSlots\":1000,\"taskcfgAllKafkaControllerSocketTimeoutMs\":30000,\"taskcfgAllKafkaQuotaWindowNum\":11,\"taskcfgAllKafkaSuperuserRegex\":\"kafka-[0-9]+-broker\",\"taskcfgAllKafkaSocketReceiveBufferBytes\":102400,\"taskcfgAllKafkaBackgroundThreads\":10,\"taskcfgAllKafkaInterBrokerProtocolVersion\":\"1.0\",\"taskcfgAllKafkaProducerPurgatoryPurgeIntervalRequests\":1000,\"taskcfgAllKafkaFetchPurgatoryPurgeIntervalRequests\":1000,\"taskcfgAllKafkaNumNetworkThreads\":3,\"taskcfgAllKafkaSocketSendBufferBytes\":102400,\"taskcfgAllKafkaSocketRequestMaxBytes\":104857600},\"taskcfgAllKafkaDeleteTopicEnable\":false,\"taskcfgAllKafkaNumPartitions\":1,\"leaderRebalance\":{\"taskcfgAllKafkaLeaderImbalanceCheckIntervalSeconds\":300,\"taskcfgAllKafkaLeaderImbalancePerBrokerPercentage\":10,\"taskcfgAllKafkaAutoLeaderRebalanceEnable\":true},\"offsets\":{\"taskcfgAllKafkaOffsetsTopicCompressionCodec\":0,\"taskcfgAllKafkaOffsetMetadataMaxBytes\":4096,\"taskcfgAllKafkaOffsetsCommitRequiredAcks\":-1,\"taskcfgAllKafkaOffsetsRetentionCheckIntervalMs\":600000,\"taskcfgAllKafkaOffsetsTopicSegmentBytes\":104857600,\"taskcfgAllKafkaOffsetsCommitTimeoutMs\":5000,\"taskcfgAllKafkaOffsetsLoadBufferSize\":5242880,\"taskcfgAllKafkaOffsetsTopicReplicationFactor\":1,\"taskcfgAllKafkaOffsetsRetentionMinutes\":1440,\"taskcfgAllKafkaOffsetsTopicNumPartitions\":50},\"taskcfgAllKafkaAutoCreateTopicsEnable\":true,\"metrics\":{\"taskcfgAllKafkaMetricsNumSamples\":2,\"taskcfgAllKafkaMetricsSampleWindowMs\":30000},\"connections\":{\"taskcfgAllKafkaConnectionsMaxIdleMs\":600000,\"taskcfgAllKafkaMaxConnectionsPerIp\":2147483647,\"taskcfgAllKafkaMaxConnectionsPerIpOverrides\":\"\"}},\"environment\":{\"ldap\":{\"ldapPoolSize\":{\"taskcfgAllLdapPoolMinpoolsize\":1,\"taskcfgAllLdapPoolMaxpoolsize\":10},\"ldapDomains\":{\"taskcfgAllLdapGroupDomain\":\"ou=Groups,dc=stratio,dc=com\",\"taskcfgAllLdapUserDomain\":\"ou=People,dc=stratio,dc=com\"},\"taskcfgAllLdapAuthentication\":\"simple\",\"ldapValidation\":{\"taskcfgAllLdapPoolValidateoncheckin\":false,\"taskcfgAllLdapPoolValidateperiod\":30,\"taskcfgAllLdapPoolValidateperiodically\":true,\"taskcfgAllLdapPoolValidateoncheckout\":true},\"ldapGroupAttributes\":{\"taskcfgAllLdapGroupId\":\"cn\",\"taskcfgAllLdapGroupMember\":\"memberUid\",\"taskcfgAllLdapGroupDescription\":\"description\"},\"taskcfgAllLdapPort\":636,\"taskcfgAllLdapHost\":\"ldap.labs.stratio.com\",\"ldapQueries\":{\"taskcfgAllLdapGroupQueryByUser\":\"(&(objectClass=posixGroup)(!(ou=Groups))(memberUid=uid=%filter,ou=People,dc=stratio,dc=com))\",\"taskcfgAllLdapUserQueryAll\":\"(&(objectClass=person)%filter)\",\"taskcfgAllLdapGroupQueryAll\":\"(&(objectClass=posixGroup)(!(ou=Groups))%filter)\"},\"taskcfgAllLdapSecureConnection\":true,\"ldapUserAttributes\":{\"taskcfgAllLdapUserMail\":\"mail\",\"taskcfgAllLdapUserName\":\"cn\",\"taskcfgAllLdapUserId\":\"uid\",\"taskcfgAllLdapUserDescription\":\"description\"},\"ldapMore\":{\"taskcfgAllLdapSchema\":\"RFC2307bis\",\"taskcfgAllLdapMappingDistinguishedName\":\"distinguishedname\",\"taskcfgAllLdapDefaultrealm\":\"stratio.com\",\"taskcfgAllLdapPrincipal\":\"cn=ldaproot,dc=stratio,dc=com\",\"taskcfgAllLdapMatchingGroupLinkuser\":\"uid=%filter,ou=People,dc=stratio,dc=com\"}},\"vault\":{\"taskcfgAllVaultPort\":8200,\"taskcfgAllVaultHosts\":\"vault.service.paas.labs.stratio.com\"}}}";

        String result = commong.parseJSONSchema(new JSONObject(jsonSchemaHC)).toString();

        assertThat(result).as("Result: " + result + " is not equal to expected one: " + expected).isEqualToIgnoringCase(expected);
    }

    @Test
    public void testparseJSONSchemaBasicNoProperties() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String jsonSchemaHC = "{\"serviceId\": {\"description\": \"Service ID of the Service\",\"type\": \"string\",\"readOnly\": false,\"pattern\": \"(.*)(jumanji-)(.+)+$\",\"examples\": [\"jumanji-\"],\"title\": \"Service ID of the Service\",\"default\": \"jumanji-\"}}";
        String expected = "{\"serviceId\":\"jumanji-\"}";

        String result = commong.parseJSONSchema(new JSONObject(jsonSchemaHC)).toString();

        assertThat(result).as("Result: " + result + " is not equal to expected one: " + expected).isEqualToIgnoringCase(expected);
    }

    @Test
    public void testparseJSONSchemaBasicNoPropertiesProperties() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String jsonSchemaHC = "{\"general\": {\"type\": \"object\",\"additionalProperties\": false,\"ui\": null,\"name\": \"general\",\"title\": \"General\",\"description\": \"General\",\"properties\": {\"serviceId\": {\"description\": \"Service ID of the Service\",\"type\": \"string\",\"readOnly\": false,\"pattern\": \"(.*)(jumanji-)(.+)+$\",\"examples\": [\"jumanji-\"],\"title\": \"Service ID of the Service\",\"default\": \"jumanji-\"},\"mesosPrincipal\": {\"type\": \"string\",\"maxLength\": 100,\"minLength\": 3,\"readOnly\": false,\"level\": 1,\"title\": \"Mesos principal\",\"default\": \"open\"},\"kafkaZookeeper\": {\"description\": \"Zookeper used by kafka cluster.\",\"type\": \"string\",\"readOnly\": false,\"level\": 1,\"title\": \"Kafka zookeeper\",\"default\": \"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\"},\"resources\": {\"type\": \"object\",\"additionalProperties\": false,\"ui\": {\"component\": \"standard\"},\"name\": \"resources\",\"title\": \"Resources\",\"description\": \"\",\"properties\": {\"INSTANCES\": {\"description\": \"Instances of the service will be raised.\",\"type\": \"number\",\"minimum\": 1,\"readOnly\": false,\"level\": 1,\"title\": \"Instances\",\"default\": 1},\"CPUs\": {\"description\": \"CPUs allocated for the service.\",\"type\": \"number\",\"minimum\": 1,\"readOnly\": false,\"level\": 1,\"title\": \"CPUs\",\"default\": 1},\"MEM\": {\"description\": \"Memory allocated in MB for the service.\",\"type\": \"number\",\"minimum\": 1230,\"readOnly\": false,\"level\": 1,\"title\": \"Memory (MB)\",\"default\": 1230}}}}}}";
        String expected = "{\"general\":{\"kafkaZookeeper\":\"zk-0001.zkuserland.mesos:2181,zk-0002.zkuserland.mesos:2181,zk-0003.zkuserland.mesos:2181\",\"resources\":{\"CPUs\":1,\"MEM\":1230,\"INSTANCES\":1},\"mesosPrincipal\":\"open\",\"serviceId\":\"jumanji-\"}}";

        String result = commong.parseJSONSchema(new JSONObject(jsonSchemaHC)).toString();

        assertThat(result).as("Result: " + result + " is not equal to expected one: " + expected).isEqualToIgnoringCase(expected);
    }

    @Test
    public void testparseJSONSchemaIncorrectType() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String jsonSchemaHC = "{\"serviceId\": {\"description\": \"Service ID of the Service\",\"type\": \"double\",\"readOnly\": false,\"pattern\": \"(.*)(jumanji-)(.+)+$\",\"examples\": [\"jumanji-\"],\"title\": \"Service ID of the Service\",\"default\": \"jumanji-\"}}";
        String expected = "{\"serviceId\":\"jumanji-\"}";

        try {
            String result = commong.parseJSONSchema(new JSONObject(jsonSchemaHC)).toString();
        } catch (Exception e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(Exception.class.toString());
            assertThat(e.getMessage()).as("Unexpected exception message").isEqualTo("type not expected");
        }
    }

    @Test
    public void testMatchJsonToSchema() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        String schema = "{\"type\": \"object\",\"additionalProperties\": false,\"properties\": {\"serviceId\": {\"description\": \"Service ID of the Service\",\"type\": \"string\",\"readOnly\": false,\"pattern\": \"(.*)(jumanji-)(.+)+$\",\"examples\": [\"jumanji-\"],\"title\": \"Service ID of the Service\",\"default\": \"jumanji-\"}},\"taskcfgAllKafkaReplicaLagTimeMaxMs\": {\"description\": \"If a follower hasn't sent any fetch requests or hasn't consumed up to the leaders log end offset for at least this time, the leader will remove the follower from isr\",\"readOnly\": false,\"default\": 10000,\"type\": \"number\",\"title\": \"Lag time max (ms)\",\"level\": 1}}";
        String json = "{\"serviceId\":\"jumanji-\"}";

        JSONObject jsonschema = new JSONObject(schema);
        JSONObject jsondeploy = new JSONObject(json);

        try {
            boolean matches = commong.matchJsonToSchema(jsonschema,jsondeploy);
            assertThat(matches).as("Unexpected situation, exception should have been triggered").isTrue();
        } catch (ValidationException valEx){
            assertThat(valEx.getClass().toString()).as("Unexpected exception").isEqualTo(ValidationException.class.toString());
        }
    }

    @Test
    public void testCookieExistsTrue() throws Exception{
        String cookieName = "cookieTest";
        org.openqa.selenium.Cookie cookie = new org.openqa.selenium.Cookie("cookieTest", "cookiePath", "cookieValue");
        CommonG commong = new CommonG();
        Set<Cookie> setCookies = new HashSet<Cookie>();
        setCookies.add(cookie);
        commong.setSeleniumCookies(setCookies);
        assertThat(true).isEqualTo(commong.cookieExists(cookieName));
    }

    @Test
    public void testCookieExistsFalse() throws Exception{
        String cookieName = "cookieFalse";
        org.openqa.selenium.Cookie cookie = new org.openqa.selenium.Cookie("cookieTest", "cookiePath", "cookieValue");
        CommonG commong = new CommonG();
        Set<Cookie> setCookies = new HashSet<Cookie>();
        setCookies.add(cookie);
        commong.setSeleniumCookies(setCookies);
        assertThat(false).isEqualTo(commong.cookieExists(cookieName));
    }

    @Test
    public void testCookieExistsSizeZero() throws Exception{
        String cookieName = "cookieFalse";
        org.openqa.selenium.Cookie cookie = new org.openqa.selenium.Cookie("cookieTest", "cookiePath", "cookieValue");
        Set<Cookie> setCookies = new HashSet<Cookie>();
        CommonG commong = new CommonG();
        commong.setSeleniumCookies(setCookies);
        assertThat(false).isEqualTo(commong.cookieExists(cookieName));
    }

    @Test
    public void testCookieExistsNull() throws Exception{
        String cookieName = "cookieFalse";
        org.openqa.selenium.Cookie cookie = new org.openqa.selenium.Cookie("cookieTest", "cookiePath", "cookieValue");
        CommonG commong = new CommonG();
        assertThat(false).isEqualTo(commong.cookieExists(cookieName));
    }

    @Test
    public void modifyDataArrayDeleteTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"},{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[1]", "DELETE", "N/A", "N/A"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddToStringTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"},\"value\"]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$", "ADDTO", "value", "string"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddToNumberTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"},1]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$", "ADDTO", "1", "number"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddToArrayTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"},[]]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$", "ADDTO", "[]", "array"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddToBooleanTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"},true]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$", "ADDTO", "true", "boolean"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddToObjectTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"},{}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$", "ADDTO", "{}", "object"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddToNullTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"},null]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$", "ADDTO", "null", "null"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }


    @Test
    public void modifyDataArrayAddStringTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\",\"key\":\"value\"}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].key", "ADD", "value", "string"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddNumberTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\",\"key\":1}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].key", "ADD", "1", "number"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddArrayTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\",\"key\":[]}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].key", "ADD", "[]", "array"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddBooleanTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\",\"key\":true}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].key", "ADD", "true", "boolean"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddObjectTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\",\"key\":{}}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].key", "ADD", "{}", "object"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAddNullTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\",\"key\":null}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].key", "ADD", "null", "null"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayReplaceStringTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"newname\"}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "REPLACE", "newname", "string"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayReplaceNumberTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":1}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "REPLACE", "1", "number"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayReplaceArrayTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":[]}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "REPLACE", "[]", "array"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayReplaceBooleanTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":true}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "REPLACE", "true", "boolean"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayReplaceObjectTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":{}}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "REPLACE", "{}", "object"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayReplaceNullTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":null}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "REPLACE", "null", "null"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayUpdateTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"\",\"id\":-1,\"name\":\"newname\"}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "UPDATE", "newname"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayAppendTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"description\",\"id\":-1,\"name\":\"name\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"description\",\"id\":-1,\"name\":\"nameappended\"}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "APPEND", "appended"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void modifyDataArrayPrependTest() throws Exception{
        String data = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"description\",\"id\":-1,\"name\":\"name\"}]";
        String expectedData = "[{\"businessAssets\":{\"id\":1},\"metadataPath\":\"\",\"description\":\"description\",\"id\":-1,\"name\":\"prependedname\"}]";
        CommonG commong = new CommonG();

        String type = "json";
        List<List<String>> rawData = Arrays.asList(Arrays.asList("$.[0].name", "PREPEND", "prepended"));
        DataTable modifications = DataTable.create(rawData);

        String modifiedData = commong.modifyData(data, type, modifications);
        JSONAssert.assertEquals(expectedData, modifiedData, false);
    }

    @Test
    public void csvTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        List<Map<String, String>> csvResults = new ArrayList<Map<String, String>>();
        Map<String, String> row = new HashMap<String, String>();
        row.put("id", "1");
        row.put("name", "aaa");
        row.put("value", "test");
        csvResults.add(row);

        List<List<String>> rawData = Arrays.asList(Arrays.asList("id", "name", "value"), Arrays.asList("1", "aaa", "test"));
        DataTable csvList = DataTable.create(rawData);

        commong.setCSVResults(csvResults);
        commong.resultsMustBeCSV(csvList);
    }

    @Test
    public void csvExceptionTest() {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        List<List<String>> rawData = Arrays.asList(Arrays.asList("id", "name", "value"), Arrays.asList("1", "aaa", "test"));
        DataTable csvList = DataTable.create(rawData);

        try {
            commong.resultsMustBeCSV(csvList);
            org.testng.Assert.fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getMessage()).as("CSV Results not setted").isEqualTo("You must execute a query before trying to get results");
        }
    }

    @Test
    public void csvRegexTest() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();

        List<Map<String, String>> csvResults = new ArrayList<Map<String, String>>();
        Map<String, String> row = new HashMap<String, String>();
        row.put("id", "0");
        row.put("uuid", "c83d3a0b-0d4d-474d-aed5-bd407a3e5ae8");
        row.put("date", "2019-04-03 09:23:23.763");
        csvResults.add(row);

        List<List<String>> rawData = Arrays.asList(Arrays.asList("id", "uuid", "date"), Arrays.asList("0", "regex-uuid", "regex-timestamp_yyyy-MM-dd HH:mm:ss.SSS"));
        DataTable csvList = DataTable.create(rawData);

        commong.setCSVResults(csvResults);
        commong.resultsMustBeCSV(csvList);
    }
}
