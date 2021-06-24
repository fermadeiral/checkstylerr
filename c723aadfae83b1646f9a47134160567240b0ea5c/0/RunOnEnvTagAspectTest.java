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

import gherkin.formatter.model.Tag;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class RunOnEnvTagAspectTest {

    public RunOnTagAspect runontag = new RunOnTagAspect();

    @Test
    public void testGetParams() throws Exception {
        String[][] expectedResponse = {{"HELLO", "BYE"}, {}};
        assertThat(expectedResponse).as("Params are correctly obtained").isEqualTo(runontag.getParams("@runOnEnv(HELLO,BYE)"));
    }

    @Test
    public void testCheckParams() throws Exception {
        System.setProperty("HELLO_OK","OK");
        assertThat(true).as("Params are correctly checked").isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO_OK)")));
        System.clearProperty("HELLO_OK");
    }

    @Test
    public void testCheckParams_2() throws Exception {
        System.setProperty("HELLO_KO","KO");
        assertThat(false).as("Params are correctly checked 2").isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO_KO,BYE_KO)")));
        System.clearProperty("HELLO_KO");
    }
    @Test
    public void testCheckEmptyParams() throws Exception {
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv()")))
                .withMessage("Error while parsing params. Params must be at least one");
    }
    @Test
    public void testGetEmptyParams() throws Exception {
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.getParams("@runOnEnv"))
                .withMessage("Error while parsing params. Format is: \"runOnEnv(PARAM)\", but found: " + "@runOnEnv");
    }

    @Test
    public void testTagIterationRun() throws Exception {
        System.setProperty("HELLO","OK");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO,BYE)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunArrayNegative() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("BYE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO,BYE)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreRun() throws Exception {
        System.clearProperty("BYE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(BYE)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkip() throws Exception {
        System.clearProperty("HELLO_NO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO_NO)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipArray() throws Exception {
        System.clearProperty("HELLO_NO");
        System.clearProperty("BYE_NO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO_NO,BYE_NO)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreSkip() throws Exception {
        System.setProperty("HELLO","OK");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreSkipArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO,BYE)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreSkipArrayNegative() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO,SEEYOU)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValue() throws Exception {
        System.setProperty("HELLO","OK");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=OK)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThan() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>FIRST)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThan() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<SECOND)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=OK)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>OK)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<OK)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueNegative() throws Exception {
        System.setProperty("HELLO","OK");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=KO)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>SECOND)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>FIRST)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<FIRST)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<SECOND)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=OK,BYE=KO)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArray() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>FIRST,BYE>1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArray() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<SECOND,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayNegative1() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=OK,BYE=OK)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayNegative2() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=KO,BYE=KO)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayNegative3() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=KO,BYE=OK)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>FIRST,BYE>1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>SECOND,BYE>1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO>SECOND,BYE>1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<SECOND,BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<FIRST,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO<FIRST,BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMix() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,BYE,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValue() throws Exception {
        System.setProperty("HELLO","OK");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=OK)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThan() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>FIRST)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThan() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<SECOND)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=OK)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>OK)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<OK)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueNegative() throws Exception {
        System.setProperty("HELLO","OK");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=KO)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>SECOND)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>SECOND)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<FIRST)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<FIRST)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=OK,BYE=KO)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArray() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>FIRST,BYE>1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArray() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<SECOND,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArrayNegative1() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=OK,BYE=OK)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArrayNegative2() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=KO,BYE=KO)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArrayNegative3() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=KO,BYE=OK)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>FIRST,BYE>1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>SECOND,BYE>1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO>SECOND,BYE>1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<SECOND,BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<FIRST,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-abc");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO<FIRST,BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMix() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,BYE,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndPositive() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&BYE&&HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&HELLO<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&BYE&&HELLO>THRID&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&BYE&&HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&BYE&&HELLO>FIRST&&HELLO<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&BYE&&HELLO>THRID&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||BYE||HELLO>THIRD||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||HELLO<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||BYE||HELLO>THRID||BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||BYE||HELLO>THIRD||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||BYE||HELLO>FIRST||HELLO<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||BYE||HELLO>THRID||BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }
    
    @Test
    public void testTagBooleanExpressionRunOrNegative() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }
    
    @Test
    public void testTagBooleanExpressionSkipAndPositive() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&HELLO<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>THRID&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&BYE&&HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&BYE&&HELLO>FIRST&&HELLO<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&BYE&&HELLO>THRID&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||BYE||HELLO>THIRD||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||HELLO<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||BYE||HELLO>THRID||BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||BYE||HELLO>THIRD||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||BYE||HELLO>FIRST||HELLO<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||BYE||HELLO>THRID||BYE<0.0.19)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrNegative() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-abc)", 1));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-abc)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)", 1));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionExceptionNumOperators() throws Exception {
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@skipOnEnv(HELLO=OK&&BYE!KK)")))
                .withMessage("Error in expression. Number of conditional operators plus 1 should be equal to the number of expressions.");
    }

    @Test
    public void testTagBooleanExpressionExceptionWrongOperator() throws Exception {
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@skipOnEnv(HELLO=OK&&BYE,KK)")))
                .withMessage("Error in conditional operators. Operators should be && or ||.");
    }
}
