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
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

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

    @Test
    public void testassertCommandExistsOnTimeOutLocalDoesNotContain() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        CommandExecutionSpec cmdExec = new CommandExecutionSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> cmdExec.assertCommandExistsOnTimeOutLocal(5,1,"echo test | grep check", "check", null)).withMessageContaining("Local command output don't found yet after 5 seconds");
    }

    @Test
    public void testassertCommandExistsOnTimeOutLocalIncorrectExitStatus() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        CommandExecutionSpec cmdExec = new CommandExecutionSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> cmdExec.assertCommandExistsOnTimeOutLocal(5,1,"echo test | grep check", "check", "2")).withMessageContaining("Local command output don't found yet after 5 seconds");
    }

    @Test
    public void testassertCommandExistsOnTimeOutLocalCorrectExitStatus() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        CommandExecutionSpec cmdExec = new CommandExecutionSpec(commong);

        assertThatExceptionOfType(Exception.class).isThrownBy(() -> cmdExec.assertCommandExistsOnTimeOutLocal(5,1,"echo test | grep test", "check", "0")).withMessageContaining("Local command output don't found yet after 5 seconds");
    }

    @Test
    public void testassertCommandExistsOnTimeOutLocalSuccess() throws Exception {
        ThreadProperty.set("class", this.getClass().getCanonicalName());
        CommonG commong = new CommonG();
        CommandExecutionSpec cmdExec = new CommandExecutionSpec(commong);

        Assertions.assertThatCode(() -> cmdExec.assertCommandExistsOnTimeOutLocal(5,1,"echo test | grep test", "test", null)).doesNotThrowAnyException();
    }
}