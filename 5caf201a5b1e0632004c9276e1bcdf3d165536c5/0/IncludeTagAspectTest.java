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
package com.stratio.qa.aspects;


import com.stratio.qa.exceptions.IncludeException;
import org.testng.annotations.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


public class IncludeTagAspectTest {
    public LoopIncludeTagAspect inctag = new LoopIncludeTagAspect();

    @Test
    public void testGetFeature() {
        assertThat("test.feature").as("Test feature name is extracted correctly").isEqualTo(inctag.getFeatureName("@include(feature: test.feature,scenario: To copy)"));
    }

    @Test
    public void testGetScenario() {
        assertThat("To copy").as("Test scenario name is extracted correctly").isEqualTo(inctag.getScenName("@include(feature: test.feature,scenario: To copy)"));
    }

    @Test
    public void testGetScenarioParams() {
        assertThat("To copy").as("Test scenario name is extracted correctly").isEqualTo(inctag.getScenName("@include(feature: test.feature,scenario: To copy,params:param1=1)"));
    }

    @Test
    public void testGetParams() {
        assertThat(4).as("Test that the number of keys and values are correctly calculated for params").isEqualTo(inctag.getParams("@include(feature: test.feature,scenario: To copy,params: [time1:9, time2:9])").length);
    }

    @Test
    public void testDoReplaceKeys() throws IncludeException {
        String keysNotReplaced = "Given that <time1> is not equal to <time2> into a step";
        String[] keys = {"<time1>", "9", "<time2>", "8"};
        assertThat("Given that 9 is not equal to 8 into a step").as("Test that keys are correctly replaced at scenario outlines").isEqualTo(inctag.doReplaceKeys(keysNotReplaced, keys));
    }

    @Test
    public void testDoReplaceKeysException() {
        String keysNotReplaced = "Given that <time1> is not equal to <time3> into a step";
        String[] keys = {"<time1>", "9", "<time2>", "8"};
        assertThatExceptionOfType(IncludeException.class).isThrownBy(() -> inctag.doReplaceKeys(keysNotReplaced, keys));
    }

    @Test
    public void testCheckParams() throws IncludeException {
        String lineOfParams = "| hey | ho |";
        String[] keys = {"<time1>", "9", "<time2>", "8"};
        String[] tonsOfKeys = {"<time1>", "9", "<time2>", "23", "33", "32", "10"};
        assertThat(inctag.checkParams(lineOfParams, keys)).as("Test that include parameters match the number of them at the scenario outline included").isTrue();
        assertThat(inctag.checkParams(lineOfParams, tonsOfKeys)).as("Test that include parameters match the number of them at the scenario outline included").isFalse();
    }

    @Test
    public void testTagIterationSkip() throws Exception {
        String path = "";
        List<String> lines = new ArrayList<>();
        lines.add("@include(testCheckParams)");

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> inctag.parseLines(lines, path));
    }

    @Test
    public void testParseLines() throws IncludeException {
        String[] originalFeature = new String[]
                {"Feature: Test",
                 "@include(feature:logger.feature,scenario:Some simple request)",
                 "Scenario: Scenario with include",
                 "Given I run 'echo 1' locally"};
        List<String> lines = new ArrayList<>(Arrays.asList(originalFeature));
        inctag.parseLines(lines, "src/test/resources/features/");
        assertThat(lines.get(1)).as("Test that Scenario line was moved to include line").isEqualTo("Scenario: Scenario with include");
        assertThat(lines).as("Test that array doesn't contains @include tag").doesNotContain("@include");
    }

    @Test
    public void testParseLinesMultipleIncludes() throws IncludeException {
        String[] originalFeature = new String[]
                {"Feature: Test",
                 "@include(feature:logger.feature,scenario:Some simple request)",
                 "@include(feature:logger.feature,scenario:Some simple request)",
                 "Scenario: Scenario with include",
                 "Given I run 'echo 1' locally"};
        List<String> lines = new ArrayList<>(Arrays.asList(originalFeature));
        inctag.parseLines(lines, "src/test/resources/features/");
        assertThat(lines.get(1)).as("Test that Scenario line was moved to first include line").isEqualTo("Scenario: Scenario with include");
        assertThat(lines).as("Test that array doesn't contains @include tag").doesNotContain("@include");
    }

    @Test
    public void testIncludeWithRunOnEnv() throws IncludeException {
        String[] originalFeature = new String[]
                {"Feature: Test",
                 "@include(feature:runOnEnvTag.feature,scenario:RunOnEnv with param defined.)",
                 "Scenario: Scenario with include",
                 "Given I run 'echo 1' locally"};
        List<String> lines = new ArrayList<>(Arrays.asList(originalFeature));
        inctag.parseLines(lines, "src/test/resources/features/");
        assertThat(lines.get(1)).as("Test that Scenario line was moved to include line").isEqualTo("Scenario: Scenario with include");
        assertThat(lines.get(2)).as("Test that array doesn't contains @runOnEnv tag").doesNotContain("@runOnEnv");
        assertThat(lines).as("Test that array doesn't contains @include tag").doesNotContain("@include");
    }

    @Test
    public void testIncludeBeforeOtherTags() throws IncludeException {
        String[] originalFeature = new String[]
                {"Feature: Test",
                 "@include(feature:logger.feature,scenario:Some simple request)",
                 "@runOnEnv(TEST)",
                 "Scenario: Scenario with include",
                 "Given I run 'echo 1' locally"};
        List<String> lines = new ArrayList<>(Arrays.asList(originalFeature));
        inctag.parseLines(lines, "src/test/resources/features/");
        assertThat(lines.get(1)).as("Test that runOnEnv tag was moved before Scenario").isEqualTo("@runOnEnv(TEST)");
        assertThat(lines.get(2)).as("Test that Scenario line was moved after other scenario tags").isEqualTo("Scenario: Scenario with include");
        assertThat(lines).as("Test that array doesn't contains @include tag").doesNotContain("@include");
    }

    @Test
    public void testIncludeBeforeOtherTagsMultiple() throws IncludeException {
        String[] originalFeature = new String[]
                {"Feature: Test",
                 "@include(feature:logger.feature,scenario:Some simple request)",
                 "@runOnEnv(TEST)",
                 "@ignore @manual",
                 "Scenario: Scenario with include",
                 "Given I run 'echo 1' locally"};
        List<String> lines = new ArrayList<>(Arrays.asList(originalFeature));
        inctag.parseLines(lines, "src/test/resources/features/");
        assertThat(lines.get(1)).as("Test that runOnEnv tag was moved before Scenario").isEqualTo("@runOnEnv(TEST)");
        assertThat(lines.get(2)).as("Test that ignore tag was moved before Scenario").isEqualTo("@ignore @manual");
        assertThat(lines.get(3)).as("Test that Scenario line was moved after other scenario tags").isEqualTo("Scenario: Scenario with include");
        assertThat(lines).as("Test that array doesn't contains @include tag").doesNotContain("@include");
    }
}