/**
 * Copyright (c) 2020, Self XDSD Contributors
 * All rights reserved.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"),
 * to read the Software only. Permission is hereby NOT GRANTED to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.selfxdsd.selfpm;

import com.selfxdsd.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Each PM will periodically review the assigned tasks from the projects
 * they manage.
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 0.0.2
 */
@Component
public final class ReviewAssignedTasks {

    /**
     * The PMs will review the assigned tasks every 6 hours.
     */
    private static final int EVERY_6_HOURS = 21600000;

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(
        ReviewAssignedTasks.class
    );

    /**
     * Self's core.
     */
    private final Self selfCore;

    /**
     * Ctor.
     * @param selfCode Self Core, injected by Spring automatically.
     */
    @Autowired
    public ReviewAssignedTasks(final Self selfCode) {
        this.selfCore = selfCode;
    }

    /**
     * Every 6 hours the PMs should verify their assigned tasks.
     */
    @Scheduled(fixedRate = EVERY_6_HOURS)
    public void reviewAssignedTasks() {
        LOG.debug("PMs reviewing their assigned tasks...");
        for(final ProjectManager manager : this.selfCore.projectManagers()) {
            LOG.debug(
                "PM @" + manager.username()
                + " reviewing their aassinged tasks..."
            );
            for(final Project project : manager.projects()) {
                LOG.debug(
                    "Reviewing assigned tasks from project "
                    + project.repoFullName() + " at " + project.provider()
                    + "... "
                );
                project.resolve(
                    new Event() {
                        @Override
                        public String type() {
                            return Type.ASSIGNED_TASKS;
                        }

                        @Override
                        public Issue issue() {
                            throw new UnsupportedOperationException(
                                "No Issue in the " + Type.ASSIGNED_TASKS
                                + " event."
                            );
                        }

                        @Override
                        public Comment comment() {
                            throw new UnsupportedOperationException(
                                "No Comment in the " + Type.ASSIGNED_TASKS
                                + " event."
                            );
                        }

                        @Override
                        public Commit commit() {
                            throw new UnsupportedOperationException(
                                "No Commit in the " + Type.ASSIGNED_TASKS
                                + " event."
                            );
                        }

                        @Override
                        public Project project() {
                            return project;
                        }
                    }
                );
            }
        }
        LOG.debug("All PMs finished reviewing their assigned tasks.");
    }
}
