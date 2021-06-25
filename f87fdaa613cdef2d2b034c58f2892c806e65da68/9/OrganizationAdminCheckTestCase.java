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

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

/**
 * Unit tests for {@link OrganizationAdminCheck}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id: 6166f63bb86417abf0c09b713448ff58c746f809 $
 * @since 1.0.0
 *
 */
public class OrganizationAdminCheckTestCase {

    /**
     * OrganizationAdminCheck can tell when the command author is NOT owner of the repo but is admin
     * of the organization that holds the repo.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void authorOrganizationAdmin() throws Exception {
        JsonObject membership = Json.createObjectBuilder().add("state", "active").add("role", "admin").build();
        Command com  = this.mockCommand("someone", "orgName");
        Mockito.when(com.authorOrgMembership()).thenReturn(membership);

        OrganizationAdminCheck oac = new OrganizationAdminCheck(
            new Step.Fake(true), new Step.Fake(false)
        );
        oac.perform(com, Mockito.mock(Logger.class));
    }
    
    /**
     * OrganizationAdminCheck throws IllegalStateException if the membership endpoint didn't work.
     * @throws Exception If something goes wrong.
     */
    @Test (expected = IllegalStateException.class)
    public void membershipEndpointThrowsIOException() throws Exception {
        Command com  = this.mockCommand("someone", "orgName");
        Mockito.when(com.authorOrgMembership()).thenThrow(new IOException());

        OrganizationAdminCheck oac = new OrganizationAdminCheck(
            Mockito.mock(Step.class), Mockito.mock(Step.class)
        );
        oac.perform(com, Mockito.mock(Logger.class));
    }

    /**
     * OrganizationAdminCheck can tell when the command author is neither repo owner nor admin of
     * the organization under which the repo is registered.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void authorNotOrganizationAdmin() throws Exception {
        JsonObject membership = Json.createObjectBuilder().add("state", "active").add("role", "member").build();
        Command com  = this.mockCommand("someone", "orgName");
        Logger logger = Mockito.mock(Logger.class);
        Mockito.when(com.authorOrgMembership()).thenReturn(membership);
        Step onTrue = Mockito.mock(Step.class);
        Mockito.doThrow(new IllegalStateException("This step should not have been executed!")).when(onTrue).perform(com, logger);
        Step onFalse = Mockito.mock(Step.class);
        Mockito.doNothing().when(onFalse).perform(com, logger);

        OrganizationAdminCheck oac = new OrganizationAdminCheck(
            new Step.Fake(false), new Step.Fake(true)
        );
        oac.perform(com, logger);
    }
    /**
     * Mock a command for the unit tests.
     * @param author Author of the command.
     * @param repoOwner Repository owner.
     * @param fork Is the repository a fork or not?
     * @param organization Is the repo under an organization or not?
     * @param port Port on which the organization membership goes.
     * @return Command mock. 
     * @throws IOException If something goes wrong.
     */
    private Command mockCommand(String author, String repoOwner) throws IOException {
        JsonObject repoJson = Json.createObjectBuilder()
            .add(
                "owner",
                Json.createObjectBuilder()
                .add("login", repoOwner)
                .add("type", "Organization")
                .build()
            )
            .build();
        Command command = Mockito.mock(Command.class);
        Mockito.when(command.authorLogin()).thenReturn(author);
        CachedRepo crepo = Mockito.mock(CachedRepo.class);
        Mockito.when(crepo.json()).thenReturn(repoJson);
        
        Mockito.when(command.repo()).thenReturn(crepo);
        return command;
    }
    
}
