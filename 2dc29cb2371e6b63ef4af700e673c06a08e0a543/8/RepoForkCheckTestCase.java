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

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * Unit tests for {@link RepoForkCheck}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: b57717682b21390d3de19fc113a102d68a0ad6f8 $
 * @since 1.0.0
 *
 */
public class RepoForkCheckTestCase {

    /**
     * RepoNameCheck can tell if the repo is a fork. 
     */
    @Test
    public void recognizesFork() throws IOException {
        JsonObject repo = Json.createObjectBuilder().add("fork", true).build();
        Command com = Mockito.mock(Command.class);
        CachedRepo cached = Mockito.mock(CachedRepo.class);
        Mockito.when(cached.json()).thenReturn(repo);
        Mockito.when(com.repo()).thenReturn(cached);

        Logger logger = Mockito.mock(Logger.class);
        
        Step onTrue = Mockito.mock(Step.class);
        Mockito.doThrow(new IllegalStateException("This step should not have been executed!")).when(onTrue).perform(com, logger);
        Step onFalse = Mockito.mock(Step.class);
        Mockito.doNothing().when(onFalse).perform(com, logger);
        
        RepoForkCheck rfc = new RepoForkCheck(onTrue, onFalse);
        rfc.perform(com, logger);
    }
    
    /**
     * RepoNameCheck can tell if the repo is NOT a fork. 
     */
    @Test
    public void recognizesNotFork() throws IOException {
        JsonObject repo = Json.createObjectBuilder().add("fork", false).build();
        Command com = Mockito.mock(Command.class);
        CachedRepo cached = Mockito.mock(CachedRepo.class);
        Mockito.when(cached.json()).thenReturn(repo);
        Mockito.when(com.repo()).thenReturn(cached);

        Logger logger = Mockito.mock(Logger.class);
        
        Step onTrue = Mockito.mock(Step.class);
        Mockito.doNothing().when(onTrue).perform(com, logger);
        Step onFalse = Mockito.mock(Step.class);
        Mockito.doThrow(new IllegalStateException("This step should not have been executed!")).when(onFalse).perform(com, logger);

        RepoForkCheck rfc = new RepoForkCheck(onTrue, onFalse);
        rfc.perform(com, logger);
    }
}
