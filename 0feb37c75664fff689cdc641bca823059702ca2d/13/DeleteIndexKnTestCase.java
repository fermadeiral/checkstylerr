/**
 * Copyright (c) 2016-2017, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1)Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  3)Neither the name of charles-rest nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.charles.github;

import java.io.IOException;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link DeleteIndexKn}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 5d4e97971021562afdccad9bbc2599ce2b6c6d08 $
 * @since 1.0.2
 */
public final class DeleteIndexKnTestCase {

    /**
     * DeleteIndexKn can start an 'deleteindex' command.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void handlesDeleteIndexCommand() throws Exception {
        final Command com = Mockito.mock(Command.class);
        Mockito.when(com.type()).thenReturn("deleteindex");
        Mockito.when(com.authorLogin()).thenReturn("amihaiemil");
        Mockito.when(com.language()).thenReturn(new English());
        final CachedRepo repo = Mockito.mock(CachedRepo.class);
        Mockito.when(repo.name()).thenReturn("testRepo");
        Mockito.when(com.repo()).thenReturn(repo);

        final LogsLocation logs = Mockito.mock(LogsLocation.class);
        Mockito.when(logs.address()).thenReturn("/path/to/logs");
        
        final Knowledge deleteindex = new DeleteIndexKn(
            new Knowledge() {
                @Override
                public Steps start(final Command com, final LogsLocation logs) throws IOException {
                    throw new IllegalStateException(
                        "'deleteindex' command was misunderstood!"
                    );
                }
            }
        );

        Steps steps = deleteindex.start(com, logs);
        MatcherAssert.assertThat(steps, Matchers.notNullValue());
        MatcherAssert.assertThat(steps instanceof StepsTree, Matchers.is(true));
    }
    
    /**
     * DeleteIndexKn can start a command which is not 'deleteindex'.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void handlesNotDeleteIndexCommand() throws Exception {
        final Command com = Mockito.mock(Command.class);
        Mockito.when(com.type()).thenReturn("indexsite");
        
        final Knowledge deleteindex = new DeleteIndexKn(
            new Knowledge() {
                @Override
                public Steps start(final Command com, final LogsLocation logs) throws IOException {
                    MatcherAssert.assertThat(
                        com.type(),
                        Matchers.equalTo("indexsite")
                    );
                    return null;
                }
            }
        );
        deleteindex.start(com, Mockito.mock(LogsLocation.class));
    }
}
