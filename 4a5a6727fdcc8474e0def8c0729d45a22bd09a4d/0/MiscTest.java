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

import com.stratio.qa.assertions.Assertions;
import org.assertj.core.api.Fail;
import com.stratio.qa.utils.ThreadProperty;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import org.junit.ComparisonFailure;

public class MiscTest {

    @Test
    public void testSaveElementFromVariable() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());

        String baseData = "indicesJSON.conf";
        String envVar = "envVar";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        try {
            misc.saveElementEnvironment(null, jsonString.concat(".$.[0]"), envVar);
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        assertThat(ThreadProperty.get(envVar)).as("Not correctly ordered").isEqualTo("stratiopaaslogs-2016-07-26");
    }

    @Test
    public void testSortJSONElementsAscending() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());

        String baseData = "indicesJSON.conf";
        String ascendingFile = "indicesJSONAscending.conf";
        String envVar = "envVar";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));
        String jsonStringAscending = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(ascendingFile).getFile())));

        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        ThreadProperty.set(envVar, jsonString);

        try {
            misc.sortElements(envVar, "alphabetical", "ascending");
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        String value = ThreadProperty.get(envVar);

        assertThat(value).as("Not correctly ordered").isEqualTo(jsonStringAscending);
    }

    @Test
    public void testSortJSONElementsDescending() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());

        String baseData = "indicesJSON.conf";
        String descendingFile = "indicesJSONDescending.conf";
        String envVar = "envVar";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));
        String jsonStringDescending = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(descendingFile).getFile())));

        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        ThreadProperty.set(envVar, jsonString);

        try {
            misc.sortElements(envVar, "alphabetical", "descending");
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        String value = ThreadProperty.get(envVar);

        assertThat(value).as("Not correctly ordered").isEqualTo(jsonStringDescending);
    }

    @Test
    public void testSortJSONElementsOrderedByDefault() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());

        String ascendingFile = "indicesJSONAscending.conf";
        String envVar = "envVar";

        String jsonStringAscending = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(ascendingFile).getFile())));

        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        ThreadProperty.set(envVar, jsonStringAscending);

        try {
            misc.sortElements(envVar, "alphabetical", "ascending");
        } catch (Exception e) {
            fail("Error parsing JSON String");
        }

        String value = ThreadProperty.get(envVar);

        assertThat(value).as("Not correctly ordered").isEqualTo(jsonStringAscending);
    }

    @Test
    public void testSortJSONElementsNoCriteria() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());

        String baseData = "indicesJSON.conf";
        String envVar = "envVar";

        String jsonString = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        ThreadProperty.set(envVar, jsonString);

        try {
            misc.sortElements(envVar, "nocriteria", "ascending");
            fail("No exception returned ordering without criteria");
        } catch (Exception e) {

        }
    }
    
    @Test
    public void testValueEqualInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "consulMesos";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.[0].Node", "equal", "paaslab31.stratio.com");
        List<String> row2 = Arrays.asList("[0].Node", "equal", "paaslab31.stratio.com");

        List<List<String>> rawData = Arrays.asList(row1, row2);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);

    }

    @Test
    public void testValueNotEqualInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "consulMesos";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.[1].Node", "not equal", "paaslab31.stratio.com");
        List<String> row2 = Arrays.asList("[2].Node", "not equal", "paaslab32.stratio.com");

        List<List<String>> rawData = Arrays.asList(row1, row2);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);

    }

    @Test
    public void testValueContainsInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "consulMesos";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.[0].ServiceTags", "contains", "leader");
        List<String> row2 = Arrays.asList("[1].ServiceTags", "contains", "master");

        List<List<String>> rawData = Arrays.asList(row1, row2);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);

    }

    @Test
    public void testValueDoesNotContainInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "consulMesos";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.[0].ServiceTags", "does not contain", "adsads");
        List<String> row2 = Arrays.asList("[1].Node", "does not contain", "rgrerg");

        List<List<String>> rawData = Arrays.asList(row1, row2);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);

    }

    @Test(expectedExceptions = AssertionError.class)
    public void testWrongOperatorInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "consulMesos";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.[0].ServiceTags", "&&", "leader");
        List<String> row2 = Arrays.asList("[1].Node", "||", "paaslab32.stratio.com");

        List<List<String>> rawData = Arrays.asList(row1, row2);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);

    }

    @Test
    public void testKeysContainsInJSON() throws Exception {
        String baseData = "exampleJSON.conf";
        String envVar = "exampleEnvVar";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.glossary.~[0]", "contains", "title");
        List<String> row2 = Arrays.asList("$.glossary.GlossDiv.~", "contains", "GlossList");

        List<List<String>> rawData = Arrays.asList(row1, row2);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);

    }

    @Test
    public void testSizeInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "exampleEnvVar";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$", "size", "4");
        List<String> row2 = Arrays.asList("$.[0].ServiceTags", "size", "2");

        List<List<String>> rawData = Arrays.asList(row1, row2);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);

    }

    @Test(expectedExceptions = AssertionError.class)
    public void testNotParsedArraySizeInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "exampleEnvVar";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.[0]", "size", "4");
        List<List<String>> rawData = Arrays.asList(row1);

        DataTable table = DataTable.create(rawData);
        misc.matchWithExpresion(envVar, table);
    }

    @Test(expectedExceptions = AssertionError.class, expectedExceptionsMessageRegExp = ".*?Expected array for size operation.*?")
    public void testNotArraySizeInJSON() throws Exception {
        String baseData = "consulMesosJSON.conf";
        String envVar = "exampleEnvVar";
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        String result = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(baseData).getFile())));

        ThreadProperty.set(envVar, result);

        List<String> row1 = Arrays.asList("$.[0].Node", "size", "4");
        List<List<String>> rawData = Arrays.asList(row1);

        DataTable table = DataTable.create(rawData);

        misc.matchWithExpresion(envVar, table);
    }

    @Test
    public void testCheckValueInvalidComparison() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> misc.checkValue("BlaBlaBla", "not valid comparison", "BleBleBle")).withMessageContaining("Not a valid comparison. Valid ones are: is | matches | is higher than | is higher than or equal to | is lower than | is lower than or equal to | contains | does not contain | is different from");
    }

    @Test
    public void testCheckValueIsFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(ComparisonFailure.class).isThrownBy(() -> misc.checkValue("10", "is", "5")).withMessageContaining("Numbers are not equal.");
    }

    @Test()
    public void testCheckValueIsSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("10", "is", "10");
    }

    @Test
    public void testCheckValueMatchesFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("prueba", "matches", "test")).withMessageContaining("Values are different.");
    }

    @Test
    public void testCheckValueMatchesSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("prueba", "is", "prueba");
    }

    @Test
    public void testCheckValueIsHigherThanException() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> misc.checkValue("prueba", "is higher than", "10")).withMessageContaining("A number should be provided in order to perform a valid comparison.");
    }

    @Test
    public void testCheckValueIsHigherThanFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("5", "is higher than", "10")).withMessageContaining("First value is not higher than second one.");
    }

    @Test
    public void testCheckValueIsHigherThanSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("10", "is higher than", "5");
    }

    @Test
    public void testCheckValueIsHigherThanOrEqualToException() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> misc.checkValue("prueba", "is higher than or equal to", "10")).withMessageContaining("A number should be provided in order to perform a valid comparison.");
    }

    @Test
    public void testCheckValueIsHigherThanOrEqualToFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("5", "is higher than or equal to", "10")).withMessageContaining("First value is not higher than or equal to second one.");
    }

    @Test
    public void testCheckValueIsHigherThanOrEqualToSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("10", "is higher than or equal to", "5");
    }

    @Test
    public void testCheckValueIsHigherThanOrEqualToSuccess2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("5", "is higher than or equal to", "5");
    }

    @Test
    public void testCheckValueIsLowerThanException() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> misc.checkValue("prueba", "is lower than", "10")).withMessageContaining("A number should be provided in order to perform a valid comparison.");
    }

    @Test
    public void testCheckValueIsLowerThanFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("10", "is lower than", "5")).withMessageContaining("First value is not lower than second one.");
    }

    @Test
    public void testCheckValueIsLowerThanSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("5", "is lower than", "10");
    }

    @Test
    public void testCheckValueIsLowerThanOrEqualToException() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> misc.checkValue("prueba", "is lower than or equal to", "10")).withMessageContaining("A number should be provided in order to perform a valid comparison.");
    }

    @Test
    public void testCheckValueIsLowerThanOrEqualToFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("10", "is lower than or equal to", "5")).withMessageContaining("First value is not lower than or equal to second one.");
    }

    @Test
    public void testCheckValueIsLowerThanOrEqualToSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("5", "is lower than or equal to", "10");
    }

    @Test
    public void testCheckValueIsLowerThanOrEqualToSuccess2() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("5", "is lower than or equal to", "5");
    }

    @Test
    public void testCheckValueContainsFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("Prueba", "contains", "test")).withMessageContaining("Second value is not contained in first one.");
    }

    @Test
    public void testCheckValueContainsSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("Prueba", "contains", "rueb");
    }

    @Test
    public void testCheckValueDoesNotContainFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("Prueba", "does not contain", "rueb")).withMessageContaining("Second value is contained in first one.");
    }

    @Test
    public void testCheckValueDoesNotContainSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("Prueba", "does not contain", "test");
    }

    @Test
    public void testCheckValueIsDifferentFromFail() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> misc.checkValue("Prueba", "is different from", "Prueba")).withMessageContaining("Both values are equal.");
    }

    @Test
    public void testCheckValueIsDifferentFromSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        MiscSpec misc = new MiscSpec(commong);

        misc.checkValue("Prueba", "is different from", "test");
    }
}
