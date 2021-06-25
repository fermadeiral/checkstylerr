/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.extension.batch._private;

import javax.batch.operations.JobStartException;
import javax.batch.operations.NoSuchJobException;

import org.jboss.as.controller.PathElement;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

/**
 * Log messages for WildFly batch module
 */
@MessageLogger(projectCode = "WFLYBATCH")
public interface BatchLogger extends BasicLogger {
    /**
     * A logger with the category {@code org.wildfly.extension.batch}.
     */
    BatchLogger LOGGER = Logger.getMessageLogger(BatchLogger.class, "org.wildfly.extension.batch");

    /**
     * Creates an exception indicating there was an error processing the batch jobs directory.
     *
     * @param cause the cause of the error
     *
     * @return a {@link org.jboss.as.server.deployment.DeploymentUnitProcessingException} for the error
     */
    @Message(id = 1, value = "Error processing META-INF/batch-jobs directory.")
    DeploymentUnitProcessingException errorProcessingBatchJobsDir(@Cause Throwable cause);

    /**
     * Creates an exception indicating that the resource of given type can not be removed.
     *
     * @return an {@link UnsupportedOperationException} for the error
     */
    @Message(id = 2, value = "Resources of type %s cannot be removed")
    UnsupportedOperationException cannotRemoveResourceOfType(String childType);

    /**
     * Creates an exception indicating the deployment name could not be found on the address.
     *
     * @return an {@link java.lang.IllegalArgumentException} for the error
     */
    @Message(id = 3, value = "Could not find deployment name: %s")
    IllegalArgumentException couldNotFindDeploymentName(String address);

    /**
     * Creates an exception indicating the {@link org.wildfly.extension.batch.deployment.JobOperatorService
     * JobOperatorService} has stopped.
     *
     * @return an {@link java.lang.IllegalStateException} for the error
     */
    @Message(id = 4, value = "The service JobOperatorService has been stopped and cannot execute operations.")
    IllegalStateException jobOperatorServiceStopped();

    /**
     * Creates an exception indicating the job name was not found for the deployment.
     *
     * @param jobName the invalid job name
     *
     * @return a {@link javax.batch.operations.NoSuchJobException} for the error
     */
    @Message(id = 5, value = "The job name '%s' was not found for the deployment.")
    NoSuchJobException noSuchJobException(String jobName);

    /**
     * Creates an exception indicating the job XML file could not be found in the deployment.
     *
     * @param xmlFile the invalid XML file
     *
     * @return a {@link javax.batch.operations.JobStartException} for the error
     */
    @Message(id = 6, value = "Could not find the job XML file in the deployment: %s")
    JobStartException couldNotFindJobXml(String xmlFile);

    /**
     * Logs a warning message indicating the job XML file failed to be processed and attempting the run the job may
     * result in errors.
     *
     * @param jobXmlFile the invalid job XML file name
     */
    @LogMessage(level = Level.WARN)
    @Message(id = 7, value = "Failed processing the job XML file %s. Attempting to execute this job may result in errors.")
    void invalidJobXmlFile(String jobXmlFile);

    /**
     * Logs an warning message indicating en empty job-repository element was found in the deployment descriptor.
     *
     * @param deploymentName the name of the deployment
     */
    @LogMessage(level = Level.WARN)
    @Message(id = 8, value = "Empty job-repository element found in deployment descriptor. Using the default job repository for deployment %s.")
    void emptyJobRepositoryElement(String deploymentName);

    @Message(id = 9, value = "Indexed child resources can only be registered if the parent resource supports ordered children. The parent of '%s' is not indexed")
    IllegalStateException indexedChildResourceRegistrationNotAvailable(PathElement address);
}
