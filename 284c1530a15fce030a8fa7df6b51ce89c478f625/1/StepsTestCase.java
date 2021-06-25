/*
 * Copyright (c) 2016-2017, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1)Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *  2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  3)Neither the name of charles-rest nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.amihaiemil.charles.github;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.json.Json;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import com.google.common.collect.Lists;
import com.jcabi.github.Comment;
import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Issue;
import com.jcabi.github.Repos.RepoCreate;
import com.jcabi.github.mock.MkGithub;

/**
 * Unit tests for {@link StepsTree}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 083f6b1fb430671da8ce6c44c2e8ed82729d6769 $
 * @since 1.0.0
 */
public class StepsTestCase {

    /**
     * Steps can perform without throwing exceptions.
     */
    @Test
    public void stepsPerformOk() throws IOException {
    	Steps steps = new StepsTree(
            Mockito.mock(Step.class),
            this.mockCommand(),
            Mockito.mock(LogsLocation.class)
        );
        steps.perform( Mockito.mock(Logger.class));
    }
    
    /**
     * Steps' execution fail and the failure comment is sent.
     * @throws Exception if something goes wrong.
     */
    @Test
    @Ignore
    public void stepsFail() throws Exception {
        Command com = this.mockCommand();
        Logger logger = Mockito.mock(Logger.class);
        Reply rep = new TextReply(com, "Error whene executig steps!");
        SendReply sr = new SendReply(
            rep, Mockito.mock(Step.class)
        );
        
        Step s = Mockito.mock(Step.class);
        Mockito.doThrow(new IllegalStateException("for test"))
            .when(s).perform(com, logger);

        Steps steps = new StepsTree(s, com, sr);
        steps.perform(logger);

        List<Comment> comments = Lists.newArrayList(com.issue().comments().iterate());
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).json().getString("body").startsWith("> @charlesmike mock command\n\n@amihaiemil Some steps failed when processing your command. See [logs]"));
    }
    
    /**
     * Mock a command.
     * @return The created Command.
     * @throws IOException If something goes wrong.
     */
    private Command mockCommand() throws IOException {
        Github gh = new MkGithub("amihaiemil");
        RepoCreate repoCreate = new RepoCreate("amihaiemil.github.io", false);
        gh.repos().create(repoCreate);
        Issue issue = gh.repos().get(
                          new Coordinates.Simple("amihaiemil", "amihaiemil.github.io")
                      ).issues().create("Test issue for commands", "test body");
        Command com = Mockito.mock(Command.class);
        Mockito.when(com.language()).thenReturn(new English());
        Mockito.when(com.authorLogin()).thenReturn("amihaiemil");
        Mockito.when(com.issue()).thenReturn(issue);
        Mockito.when(com.json()).thenReturn(Json.createObjectBuilder().add("body", "@charlesmike mock command").build());
        return com;
    }

}
