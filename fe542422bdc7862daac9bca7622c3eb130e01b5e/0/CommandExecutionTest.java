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
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CommandExecutionTest {

    @Test
    public void testemptyShellOutput() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        CommandExecutionSpec cmdExec = new CommandExecutionSpec(commong);

        commong.runLocalCommand("echo blabla 2$>1");
        try {
            cmdExec.emptyShellOutput();
         } catch (AssertionError error) {
            fail("No AssertionError should be thrown.");
        }

    }

    @Test
    public void testemptyShellOutputNotEmpty() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        CommandExecutionSpec cmdExec = new CommandExecutionSpec(commong);

        commong.runLocalCommand("echo blabla");
        try {
            cmdExec.emptyShellOutput();
        } catch (AssertionError e) {
            assertThat(e.getClass().toString()).as("Unexpected exception").isEqualTo(AssertionError.class.toString());
        }
    }

}