package com.mobi.catalog.api.mergerequest;

/*-
 * #%L
 * com.mobi.catalog.api
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2017 iNovex Information Systems, Inc.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.mobi.catalog.api.ontologies.mcat.Branch;
import com.mobi.catalog.api.ontologies.mcat.Catalog;
import com.mobi.catalog.api.ontologies.mcat.VersionedRDFRecord;
import com.mobi.catalog.api.ontologies.mergerequests.MergeRequest;
import com.mobi.jaas.api.ontologies.usermanagement.User;
import com.mobi.rdf.api.Resource;

import java.util.Optional;
import java.util.Set;

public interface MergeRequestManager {

    /**
     * Gets the {@link Set} of all {@link MergeRequest}s in Mobi.
     *
     * @return The {@link Set} of all {@link MergeRequest}s
     * @throws IllegalStateException If the catalog {@link com.mobi.repository.api.Repository} could not be found
     */
    Set<MergeRequest> getMergeRequests();

    /**
     * Creates a {@link MergeRequest} with the metadata within the provided {@link MergeRequestConfig} along with a
     * created and modified date. The title, {@link VersionedRDFRecord} ID, source {@link Branch} ID, target
     * {@link Branch} ID, and creator {@link User} are required. Can optionally include a description and
     * any assigned {@link User}s.
     *
     * @param config A {@link MergeRequestConfig} containing metadata about a {@link MergeRequest}
     * @return A {@link MergeRequest} created with the provided metadata
     * @throws IllegalArgumentException Thrown if the local {@link Catalog} could not be found, the
     *      {@link VersionedRDFRecord} could not be found, the {@link VersionedRDFRecord} does not belong to the local
     *      {@link Catalog}, or the source or target {@link Branch}es do not belong to the {@link VersionedRDFRecord}.
     * @throws IllegalStateException If the catalog {@link com.mobi.repository.api.Repository} could not be found
     */
    MergeRequest createMergeRequest(MergeRequestConfig config);

    /**
     * Stores the provided {@link MergeRequest} in the repository as long as it does not already exist.
     *
     * @param request A {@link MergeRequest} to add to the repository
     * @throws IllegalArgumentException If the provided {@link MergeRequest} already exists in the repository
     * @throws IllegalStateException If the catalog {@link com.mobi.repository.api.Repository} could not be found
     */
    void addMergeRequest(MergeRequest request);

    /**
     * Gets the {@link MergeRequest} identified by the provided {@link Resource}.
     *
     * @param requestId The {@link Resource} identifying a {@link MergeRequest}
     * @return The {@link MergeRequest} if it exists.
     * @throws IllegalStateException If the catalog {@link com.mobi.repository.api.Repository} could not be found
     */
    Optional<MergeRequest> getMergeRequest(Resource requestId);

    /**
     * Replaces the stored {@link MergeRequest} of {@code requestId} with the provided {@link MergeRequest} {@code request}
     * Assumes that {@code request} is properly populated.
     *
     * @param requestId the {@link Resource} identifying a {@link MergeRequest}
     * @param request the updated {@link MergeRequest} referenced by {@code requestId}
     */
    void updateMergeRequest(Resource requestId, MergeRequest request);

}
