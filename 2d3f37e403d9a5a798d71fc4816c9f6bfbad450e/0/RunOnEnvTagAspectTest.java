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

import com.stratio.qa.utils.ThreadProperty;
import gherkin.pickles.PickleLocation;
import gherkin.pickles.PickleTag;
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
    public void testCheckLocalParams() throws Exception {
        ThreadProperty.set("HELLO_OK","OK");
        assertThat(true).as("Params are correctly checked").isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO_OK)")));
        ThreadProperty.remove("HELLO_OK");
    }

    @Test
    public void testCheckParams_2() throws Exception {
        System.setProperty("HELLO_KO","KO");
        assertThat(false).as("Params are correctly checked 2").isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO_KO,BYE_KO)")));
        System.clearProperty("HELLO_KO");
    }

    @Test
    public void testCheckLocalParams_2() throws Exception {
        ThreadProperty.set("HELLO_KO","KO");
        assertThat(false).as("Params are correctly checked 2").isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO_KO,BYE_KO)")));
        ThreadProperty.remove("HELLO_KO");
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
    public void testCheckLengthVersion() throws Exception {
        System.setProperty("VERSION","1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0)")))
                .withMessage("Error while parsing params. The versions must have the same number of elements");
        System.clearProperty("VERSION");
    }

    @Test
    public void testCheckLengthVersion_2() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-1.0)")))
                .withMessage("Error while parsing params. The versions must have the same number of elements");
        System.clearProperty("VERSION");
    }

    @Test
    public void testCheckVersion() throws Exception {
        System.setProperty("VERSION","1.0_0-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-1.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
        System.clearProperty("VERSION");
    }

    @Test
    public void testCheckVersion_2() throws Exception {
        System.setProperty("VERSION","1.0.0@1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-1.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
        System.clearProperty("VERSION");
    }

    @Test
    public void testCheckVersion_3() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.*)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
        System.clearProperty("VERSION");
    }

    @Test
    public void testCheckVersion_4() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-0.5.15-/+-)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
        System.clearProperty("VERSION");
    }

    @Test
    public void testCheckVersion_5() throws Exception {
        ThreadProperty.set("VERSION","1.0_0-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-1.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
        ThreadProperty.remove("VERSION");
    }

    @Test
    public void testTagIterationRun() throws Exception {
        System.setProperty("HELLO","OK");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO,BYE)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunArrayNegative() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("BYE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO,BYE)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreRun() throws Exception {
        System.clearProperty("BYE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkip() throws Exception {
        System.clearProperty("HELLO_NO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO_NO)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipArray() throws Exception {
        System.clearProperty("HELLO_NO");
        System.clearProperty("BYE_NO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO_NO,BYE_NO)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreSkip() throws Exception {
        System.setProperty("HELLO","OK");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreSkipArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO,BYE)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationIgnoreSkipArrayNegative() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO,SEEYOU)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValue() throws Exception {
        System.setProperty("HELLO","OK");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=OK)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueVersion() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION=1.0.0-1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueVersion_2() throws Exception {
        System.setProperty("VERSION","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION=1.0.0-1.0.0-1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueVersion_3() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION=1.0.0-1.0.0-1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueVersion_4() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.1-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION=1.0.0-1.0.0-1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueVersion_5() throws Exception {
        System.setProperty("VERSION","1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION=1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThan() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanVersion() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>0.19.8)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanVersion_2() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanVersion_3() throws Exception {
        System.setProperty("VERSION","1.0.0-1.0.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-0.9.10-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanVersion_4() throws Exception {
        System.setProperty("VERSION","1.0.0-0.19.10-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-0.1.10-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThan() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<SECOND)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanVersion() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<SECOND)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=OK)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>OK)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<OK)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueNegative() throws Exception {
        System.setProperty("HELLO","OK");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=KO)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>FIRST)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<FIRST)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=OK,BYE=KO)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArray() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>FIRST,BYE>1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArray() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<SECOND,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayNegative1() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=OK,BYE=OK)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayNegative2() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=KO,BYE=KO)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayNegative3() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=KO,BYE=OK)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>FIRST,BYE>1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>SECOND,BYE>1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueGreatherThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO>SECOND,BYE>1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<SECOND,BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<FIRST,BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueSmallerThanArrayNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO<FIRST,BYE<1.0.0-1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMix1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,BYE,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMix2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<1.0.0-1.1.0,HELLO=SECOND,BYE,HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMix3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,BYE,BYE<1.0.0-1.1.0,HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","0.0.19");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<0.0.5)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative16() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.2.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative17() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.2.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<1.0.0,HELLO=SECOND,SEEYOU,HELLO>SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationRunValueArrayMixNegative18() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.2.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND,BYE<1.0.0,SEEYOU,HELLO>SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValue() throws Exception {
        System.setProperty("HELLO","OK");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=OK)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThan() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>FIRST)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThan() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=OK)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>OK)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanNotDefined() throws Exception {
        System.clearProperty("HELLO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<OK)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueNegative() throws Exception {
        System.setProperty("HELLO","OK");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=KO)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>SECOND)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>SECOND)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArray() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=OK,BYE=KO)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArray() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>FIRST,BYE>1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArray() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<SECOND,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArrayNegative1() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=OK,BYE=OK)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArrayNegative2() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=KO,BYE=KO)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueArrayNegative3() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","KO");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=KO,BYE=OK)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>FIRST,BYE>1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>SECOND,BYE>1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>SECOND,BYE>1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative4() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>SECOND,BYE>1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueGreatherThanArrayNegative5() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO>SECOND,BYE>1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArrayNegative1() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<SECOND,BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArrayNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkipValueSmallerThanArrayNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO<FIRST,BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMix() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,BYE,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMix_2() throws Exception {
        System.setProperty("HELLO","FIRST");
        System.setProperty("BYE","1.0.0-1.1.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST,BYE,BYE>1.0.0,HELLO<SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>THRID,BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>FIRST,BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND,SEEYOU,HELLO>SECOND,BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU", "WAY");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>THRID,BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>FIRST,BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationSkypValueArrayMixNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=WAY,SEEYOU,HELLO>SECOND,BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.5.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<1.10.0&&HELLO=SECOND&&BYE&&HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.5.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&BYE<1.10.0&&BYE&&HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&BYE&&HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&HELLO<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&BYE&&HELLO>THRID&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&BYE&&HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&BYE&&HELLO>FIRST&&HELLO<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&BYE&&HELLO>THRID&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative16() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.19.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<1.5.0&&HELLO=FIRST&&SEEYOU&&HELLO>SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunAndNegative17() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.51.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU&&BYE<1.5.0&&HELLO>SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||BYE||HELLO>THIRD||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||HELLO<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||BYE||HELLO>THRID||BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||BYE||HELLO>THIRD||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||BYE||HELLO>FIRST||HELLO<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||BYE||HELLO>THRID||BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrPositive16() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.15");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<1.0.5||HELLO=FIRST||SEEYOU||HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }
    @Test
    public void testTagBooleanExpressionRunOrPositive17() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.15");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||SEEYOU||BYE<1.0.5||HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","10.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<5.0.0||HELLO=FIRST||SEEYOU||HELLO>SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunOrNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","10.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST||BYE<5.0.0||SEEYOU||HELLO>SECOND)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0-1.15.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<1.0.0-1.20.0&&HELLO=SECOND||SEEYOU||HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixPositive6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||BYE<1.0.0-1.1.0&&HELLO>FIRST)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(BYE<0.0.19&&HELLO=FIRST||SEEYOU&&HELLO>THIRD)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionRunMixNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(HELLO=FIRST&&SEEYOU||BYE<0.0.19&&HELLO>THIRD)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(BYE<1.0.0-1.1.0&&HELLO=SECOND&&BYE&&HELLO>FIRST)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&BYE<1.0.0-1.1.0&&BYE&&HELLO>FIRST)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>FIRST&&HELLO<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&BYE&&HELLO>THRID&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&BYE&&HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&BYE&&HELLO>FIRST&&HELLO<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&BYE&&HELLO>THRID&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>FIRST&&BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipAndNegative15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU&&HELLO>SECOND&&BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||BYE||HELLO>THIRD||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||BYE||HELLO>FIRST||HELLO<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||BYE||HELLO>THRID||BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>FIRST||BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND||SEEYOU||HELLO>SECOND||BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||BYE||HELLO>THIRD||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||BYE||HELLO>FIRST||HELLO<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||BYE||HELLO>THRID||BYE<0.0.19)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive14() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrPositive15() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>FIRST||BYE<1.0.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipOrNegative() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||SEEYOU||HELLO>SECOND||BYE<1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<1.0.0-1.1.0)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(BYE<1.0.0-1.1.0||HELLO=FIRST&&SEEYOU||HELLO>FIRST)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixPositive7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||SEEYOU&&BYE<1.0.0-1.1.0||HELLO>FIRST)"));
        assertThat(true).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative1() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative2() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative3() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative4() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative5() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=SECOND&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative6() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative7() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative8() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.setProperty("SEEYOU","MAYBE");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative9() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<1.0.0-1.1.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative10() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>FIRST&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative11() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST&&SEEYOU||HELLO>THIRD&&BYE<0.0.19)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative12() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(BYE<0.0.19||HELLO=FIRST&&SEEYOU||HELLO>THIRD)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagBooleanExpressionSkipMixNegative13() throws Exception {
        System.setProperty("HELLO","SECOND");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("SEEYOU");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@skipOnEnv(HELLO=FIRST||SEEYOU&&BYE<0.0.19||HELLO>THIRD)"));
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

    @Test
    public void testTagIterationFirstElementNotExist() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(NOTEXIST=1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationFirstElementNotExistGreatherThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(NOTEXIST>1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationFirstElementNotExistLowerThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(NOTEXIST<1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementNotExist() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST=1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementNotExistOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0||NOTEXIST=1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsNotEqual() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST=1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsNotEqualOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0||NOTEXIST=1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementNotExistGreatherThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST>1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementNotExistGreatherThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0||NOTEXIST>1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementNotExistLowerThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST<1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementNotExistLowerThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0||NOTEXIST<1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsLowerThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST<1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsLowerThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0||NOTEXIST<1.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationLastElementNotExist() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST=1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementNotExistOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST=1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementExists() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST=1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementExistsOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST=1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementNotExistGreatherThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST>1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementNotExistGreatherThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST>1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementExistsGreatherThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST>1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementExistsGreatherThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=KO||NOTEXIST>1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementNotExistLowerThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST<1.0.0)")));
    }

    @Test
    public void testTagIterationLastElementNotExistLowerThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST<1.0.0)")));
    }

    @Test
    public void testTagIterationFirstElementNotExistWithoutValue() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(NOTEXIST&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationFirstElementNotExistWithoutValueOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(NOTEXIST||HELLO=OK)")));
    }

    @Test
    public void testTagIterationLastElementNotExistWithoutValue() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST)")));
    }

    @Test
    public void testTagIterationLastElementNotExistWithoutValueOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.clearProperty("NOTEXIST");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST)")));
    }

    @Test
    public void testTagIterationLastElementExistsWithValueNotChecked() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST)")));
    }

    @Test
    public void testTagIterationLastElementExistsWithValueNotCheckedOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsWithDifferentValue() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST=2.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsWithDifferentValueOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0||NOTEXIST=2.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsWithValueLowerThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST>2.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsWithValueLowerThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0||NOTEXIST>2.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsWithValueGreaterThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&NOTEXIST<2.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationIntermediateElementExistsWithValueGreaterThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("BYE","1.0.0");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.1||NOTEXIST<2.0.0&&HELLO=OK)")));
    }

    @Test
    public void testTagIterationLastElementExistsWithValue() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(false).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST=2.0.0)")));
    }

    @Test
    public void testTagIterationLastElementExistsWithValueOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST=2.0.0)")));
    }

    @Test
    public void testTagIterationLastElementExistsWithValueGreaterThan() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK&&NOTEXIST>0.1.0)")));
    }

    @Test
    public void testTagIterationLastElementExistsWithValueGreaterThanOrOperator() throws Exception {
        System.setProperty("HELLO","OK");
        System.setProperty("NOTEXIST","1.0.0");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(HELLO=OK||NOTEXIST>0.1.0)")));
    }

    @Test
    public void testTagIterationWithPrereleaseVersion() throws Exception {
        System.setProperty("VERSION","1.0.1-2xXxAzx");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithPrereleaseVersion2() throws Exception {
        System.setProperty("VERSION","1.0.1-AxXxAzx-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithPrereleaseVersion3() throws Exception {
        System.setProperty("VERSION","1.0.1-cxXxAzx-1.0.0-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithPrereleaseVersion4() throws Exception {
        System.setProperty("VERSION","1.0.1-1.0.0-x3XxAzx");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithPrereleaseVersion5() throws Exception {
        System.setProperty("VERSION","1.0.1-1.0.0-x2XxAzx-1.0.0");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithPrereleaseVersion6() throws Exception {
        System.setProperty("VERSION","1.0.1-1.0.0-1.0.0-xXxAzx1");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithPrereleaseVersion7() throws Exception {
        System.setProperty("VERSION","1.0.1-1234567-1.0.0-11aa22w-1.0.0-xXx2Azx");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0-1.0.0-1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithSnapshotVersion() throws Exception {
        System.setProperty("VERSION","1.0.1-SNAPSHOT");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithRCVersion() throws Exception {
        System.setProperty("VERSION","1.0.1-RC1");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testTagIterationWithMilestoneVersion() throws Exception {
        System.setProperty("VERSION","1.0.1-M3");
        List<PickleTag> tagList = new ArrayList<>();
        tagList.add(new PickleTag(new PickleLocation(1,0),"@runOnEnv(VERSION>1.0.0)"));
        assertThat(false).isEqualTo(runontag.tagsIteration(tagList,1));
    }

    @Test
    public void testPrereleaseNotAllowedInRunOnEnv() throws Exception {
        System.setProperty("VERSION","1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-xxxxxxx)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
    }

    @Test
    public void testInvalidSnapshotVersionInRunOnEnv() throws Exception {
        System.setProperty("VERSION","1.0.0-SNAPSHOT1");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
    }

    @Test
    public void testInvalidSnapshotVersionInRunOnEnv2() throws Exception {
        System.setProperty("VERSION","1.0.0-SNAPSHOT1-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-1.0.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
    }

    @Test
    public void testInvalidRCVersionInRunOnEnv() throws Exception {
        System.setProperty("VERSION","1.0.0-RC14");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
    }

    @Test
    public void testInvalidRCVersionInRunOnEnv2() throws Exception {
        System.setProperty("VERSION","1.0.0-RC14-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-1.0.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
    }

    @Test
    public void testInvalidMilestoneVersionInRunOnEnv() throws Exception {
        System.setProperty("VERSION","1.0.0-M14");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
    }

    @Test
    public void testInvalidMilestoneVersionInRunOnEnv2() throws Exception {
        System.setProperty("VERSION","1.0.0-M14-1.0.0");
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> runontag.checkParams(runontag.getParams("@runOnEnv(VERSION>1.0.0-1.0.0)")))
                .withMessage("Error while parsing params. The versions have some characters that are not numbers, '.' or '-' or an invalid format");
    }

    @Test
    public void testGlobalAndLocalVar() throws Exception {
        System.setProperty("HELLO","OK");
        ThreadProperty.set("BYE","1.0.0");
        System.clearProperty("BYE");
        ThreadProperty.remove("HELLO");
        assertThat(true).isEqualTo(runontag.checkParams(runontag.getParams("@runOnEnv(BYE=1.0.0&&HELLO=OK)")));
        System.clearProperty("HELLO");
        ThreadProperty.remove("BYE");
    }
}
