package com.mobi.catalog.impl.mergerequest;

/*-
 * #%L
 * com.mobi.catalog.impl
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

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import com.mobi.catalog.api.CatalogUtilsService;
import com.mobi.catalog.api.builder.Conflict;
import com.mobi.catalog.api.mergerequest.MergeRequestConfig;
import com.mobi.catalog.api.mergerequest.MergeRequestFilterParams;
import com.mobi.catalog.api.mergerequest.MergeRequestManager;
import com.mobi.catalog.api.ontologies.mcat.Branch;
import com.mobi.catalog.api.ontologies.mcat.BranchFactory;
import com.mobi.catalog.api.ontologies.mcat.CommitFactory;
import com.mobi.catalog.api.ontologies.mcat.VersionedRDFRecordFactory;
import com.mobi.catalog.api.ontologies.mergerequests.AcceptedMergeRequest;
import com.mobi.catalog.api.ontologies.mergerequests.AcceptedMergeRequestFactory;
import com.mobi.catalog.api.ontologies.mergerequests.MergeRequest;
import com.mobi.catalog.api.ontologies.mergerequests.MergeRequestFactory;
import com.mobi.catalog.api.versioning.VersioningManager;
import com.mobi.catalog.config.CatalogConfigProvider;
import com.mobi.exception.MobiException;
import com.mobi.jaas.api.ontologies.usermanagement.User;
import com.mobi.ontologies.dcterms._Thing;
import com.mobi.persistence.utils.Bindings;
import com.mobi.query.api.TupleQuery;
import com.mobi.rdf.api.IRI;
import com.mobi.rdf.api.Resource;
import com.mobi.rdf.api.ValueFactory;
import com.mobi.repository.api.RepositoryConnection;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component(name = SimpleMergeRequestManager.COMPONENT_NAME)
public class SimpleMergeRequestManager implements MergeRequestManager {

    static final String MERGE_REQUEST_NAMESPACE = "https://mobi.com/merge-requests#";
    static final String COMPONENT_NAME = "com.mobi.catalog.api.mergerequest.MergeRequestManager";

    private ValueFactory vf;
    private CatalogConfigProvider configProvider;
    private CatalogUtilsService catalogUtils;
    private VersioningManager versioningManager;
    private MergeRequestFactory mergeRequestFactory;
    private AcceptedMergeRequestFactory acceptedMergeRequestFactory;
    private VersionedRDFRecordFactory recordFactory;
    private BranchFactory branchFactory;
    private CommitFactory commitFactory;

    @Reference
    void setVf(ValueFactory vf) {
        this.vf = vf;
    }

    @Reference
    void setConfigProvider(CatalogConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Reference
    void setCatalogUtils(CatalogUtilsService catalogUtils) {
        this.catalogUtils = catalogUtils;
    }

    @Reference
    void setVersioningManager(VersioningManager versioningManager) {
        this.versioningManager = versioningManager;
    }

    @Reference
    void setMergeRequestFactory(MergeRequestFactory mergeRequestFactory) {
        this.mergeRequestFactory = mergeRequestFactory;
    }

    @Reference
    void setAcceptedMergeRequestFactory(AcceptedMergeRequestFactory acceptedMergeRequestFactory) {
        this.acceptedMergeRequestFactory = acceptedMergeRequestFactory;
    }

    @Reference
    void setRecordFactory(VersionedRDFRecordFactory recordFactory) {
        this.recordFactory = recordFactory;
    }

    @Reference
    void setBranchFactory(BranchFactory branchFactory) {
        this.branchFactory = branchFactory;
    }

    @Reference
    void setCommitFactory(CommitFactory commitFactory) {
        this.commitFactory = commitFactory;
    }

    private static final String GET_MERGE_REQUESTS_QUERY;
    private static final String FILTERS = "%FILTERS%";
    private static final String REQUEST_ID_BINDING = "requestId";
    private static final String ASSIGNEE_BINDING = "assignee";
    private static final String ON_RECORD_BINDING = "onRecord";
    private static final String SOURCE_BRANCH_BINDING = "sourceBranch";
    private static final String TARGET_BRANCH_BINDING = "targetBranch";
    private static final String SOURCE_COMMIT_BINDING = "sourceCommit";
    private static final String TARGET_COMMIT_BINDING = "targetCommit";
    private static final String REMOVE_SOURCE_BINDING = "removeSource";
    private static final String SORT_PRED_BINDING = "sortPred";

    static {
        try {
            GET_MERGE_REQUESTS_QUERY = IOUtils.toString(SimpleMergeRequestManager.class
                    .getResourceAsStream("/get-merge-requests.rq"), "UTF-8"
            );
        } catch (IOException e) {
            throw new MobiException(e);
        }
    }

    @Override
    public List<MergeRequest> getMergeRequests(MergeRequestFilterParams params) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            return getMergeRequests(params, conn);
        }
    }

    @Override
    public List<MergeRequest> getMergeRequests(MergeRequestFilterParams params, RepositoryConnection conn) {
        StringBuilder filters = new StringBuilder("FILTER ");
        if (!params.getAccepted()) {
            filters.append("NOT ");
        }
        filters.append("EXISTS { ?").append(REQUEST_ID_BINDING).append(" a mq:AcceptedMergeRequest . } ");
        Resource sortBy = params.getSortBy().orElseGet(() -> vf.createIRI(_Thing.issued_IRI));
        filters.append("?").append(REQUEST_ID_BINDING).append(" <").append(sortBy).append("> ?")
                .append(SORT_PRED_BINDING).append(". ");

        if (params.hasFilters()) {
            filters.append("FILTER (");
            params.getAssignee().ifPresent(assignee -> filters.append("?").append(ASSIGNEE_BINDING).append(" = <")
                    .append(assignee).append("> && "));
            params.getOnRecord().ifPresent(onRecord -> filters.append("?").append(ON_RECORD_BINDING).append(" = <")
                    .append(onRecord).append("> && "));
            params.getSourceBranch().ifPresent(sourceBranch -> filters.append("?").append(SOURCE_BRANCH_BINDING)
                    .append(" = <").append(sourceBranch).append("> && "));
            params.getTargetBranch().ifPresent(targetBranch -> filters.append("?").append(TARGET_BRANCH_BINDING)
                    .append(" = <").append(targetBranch).append("> && "));
            params.getSourceCommit().ifPresent(sourceCommit -> filters.append("?").append(SOURCE_COMMIT_BINDING)
                    .append(" = <").append(sourceCommit).append("> && "));
            params.getTargetCommit().ifPresent(targetCommit -> filters.append("?").append(TARGET_COMMIT_BINDING)
                    .append(" = <").append(targetCommit).append("> && "));
            params.getRemoveSource().ifPresent(removeSource -> filters.append("?").append(REMOVE_SOURCE_BINDING)
                    .append(" = ").append(removeSource).append(" && "));
            filters.delete(filters.lastIndexOf(" && "), filters.length());
            filters.append(")");
        }

        StringBuilder queryBuilder = new StringBuilder(GET_MERGE_REQUESTS_QUERY.replace(FILTERS, filters.toString()));
        queryBuilder.append(" ORDER BY ");
        if (params.sortAscending()) {
            queryBuilder.append("?").append(SORT_PRED_BINDING);
        } else {
            queryBuilder.append("DESC(?").append(SORT_PRED_BINDING).append(")");
        }

        TupleQuery query = conn.prepareTupleQuery(queryBuilder.toString());
        return StreamSupport.stream(query.evaluate().spliterator(), false)
                .map(bindings -> Bindings.requiredResource(bindings, REQUEST_ID_BINDING))
                .map(resource -> catalogUtils.getExpectedObject(resource, mergeRequestFactory, conn))
                .collect(Collectors.toList());
    }

    @Override
    public MergeRequest createMergeRequest(MergeRequestConfig config, Resource localCatalog) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            return createMergeRequest(config, localCatalog, conn);
        }
    }

    @Override
    public MergeRequest createMergeRequest(MergeRequestConfig config, Resource localCatalog,
                                           RepositoryConnection conn) {
        catalogUtils.validateBranch(localCatalog, config.getRecordId(), config.getSourceBranchId(), conn);
        catalogUtils.validateBranch(localCatalog, config.getRecordId(), config.getTargetBranchId(), conn);

        OffsetDateTime now = OffsetDateTime.now();
        MergeRequest request = mergeRequestFactory.createNew(vf.createIRI(MERGE_REQUEST_NAMESPACE + UUID.randomUUID()));
        request.setProperty(vf.createLiteral(now), vf.createIRI(_Thing.issued_IRI));
        request.setProperty(vf.createLiteral(now), vf.createIRI(_Thing.modified_IRI));
        request.setOnRecord(recordFactory.createNew(config.getRecordId()));
        request.setSourceBranch(branchFactory.createNew(config.getSourceBranchId()));
        request.setTargetBranch(branchFactory.createNew(config.getTargetBranchId()));
        request.setProperty(vf.createLiteral(config.getTitle()), vf.createIRI(_Thing.title_IRI));
        request.setRemoveSource(config.getRemoveSource());
        config.getDescription().ifPresent(description ->
                request.setProperty(vf.createLiteral(description), vf.createIRI(_Thing.description_IRI)));
        request.setProperty(config.getCreator().getResource(), vf.createIRI(_Thing.creator_IRI));
        config.getAssignees().forEach(request::addAssignee);
        return request;
    }

    @Override
    public void addMergeRequest(MergeRequest request) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            addMergeRequest(request, conn);
        }
    }

    @Override
    public void addMergeRequest(MergeRequest request, RepositoryConnection conn) {
        if (conn.containsContext(request.getResource())) {
            throw catalogUtils.throwAlreadyExists(request.getResource(), recordFactory);
        }
        conn.add(request.getModel(), request.getResource());
    }

    @Override
    public Optional<MergeRequest> getMergeRequest(Resource requestId) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            return getMergeRequest(requestId, conn);
        }
    }

    @Override
    public Optional<MergeRequest> getMergeRequest(Resource requestId, RepositoryConnection conn) {
        return catalogUtils.optObject(requestId, mergeRequestFactory, conn);
    }

    @Override
    public void updateMergeRequest(Resource requestId, MergeRequest request) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            updateMergeRequest(requestId, request, conn);
        }
    }

    @Override
    public void updateMergeRequest(Resource requestId, MergeRequest request, RepositoryConnection conn) {
        catalogUtils.validateResource(requestId, mergeRequestFactory.getTypeIRI(), conn);
        catalogUtils.updateObject(request, conn);
    }

    @Override
    public void deleteMergeRequest(Resource requestId) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            deleteMergeRequest(requestId, conn);
        }
    }

    @Override
    public void deleteMergeRequest(Resource requestId, RepositoryConnection conn) {
        catalogUtils.validateResource(requestId, mergeRequestFactory.getTypeIRI(), conn);
        catalogUtils.remove(requestId, conn);
    }

    @Override
    public void acceptMergeRequest(Resource requestId, User user) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            acceptMergeRequest(requestId, user, conn);
        }
    }

    @Override
    public void acceptMergeRequest(Resource requestId, User user,  RepositoryConnection conn) {
        // Validate MergeRequest
        MergeRequest request = catalogUtils.getExpectedObject(requestId, mergeRequestFactory, conn);
        if (request.getModel().contains(requestId, vf.createIRI(com.mobi.ontologies.rdfs.Resource.type_IRI),
                vf.createIRI(AcceptedMergeRequest.TYPE))) {
            throw new IllegalArgumentException("Request " + requestId + " has already been accepted");
        }

        // Collect information about the VersionedRDFRecord, Branches, and Commits
        Resource recordId = request.getOnRecord_resource().orElseThrow(() ->
                new IllegalStateException("Request " + requestId + " does not have a VersionedRDFRecord"));
        Resource targetId = request.getTargetBranch_resource().orElseThrow(() ->
                new IllegalArgumentException("Request " + requestId + " does not have a target Branch"));
        Branch target = catalogUtils.getExpectedObject(targetId, branchFactory, conn);
        Resource sourceId = request.getSourceBranch_resource().orElseThrow(() ->
                new IllegalStateException("Request " + requestId + " does not have a source Branch"));
        Branch source = catalogUtils.getExpectedObject(sourceId, branchFactory, conn);
        Resource sourceCommitId = getBranchHead(source);
        Resource targetCommitId = getBranchHead(target);
        String sourceTitle = getBranchTitle(source);
        String targetTitle = getBranchTitle(target);

        // Check conflicts and perform merge
        Set<Conflict> conflicts = catalogUtils.getConflicts(sourceCommitId, targetCommitId, conn);
        if (conflicts.size() > 0) {
            throw new IllegalArgumentException("Branch " + sourceId + " and " + targetId
                    + " have conflicts and cannot be merged");
        }
        versioningManager.merge(configProvider.getLocalCatalogIRI(), recordId, sourceId, targetId, user, null, null);

        // Turn MergeRequest into an AcceptedMergeRequest
        AcceptedMergeRequest acceptedRequest = acceptedMergeRequestFactory.createNew(request.getResource(),
                request.getModel());
        acceptedRequest.removeProperty(targetId, vf.createIRI(MergeRequest.targetBranch_IRI));
        acceptedRequest.removeProperty(sourceId, vf.createIRI(MergeRequest.sourceBranch_IRI));
        IRI removeSourceIRI = vf.createIRI(MergeRequest.removeSource_IRI);
        request.getProperty(removeSourceIRI).ifPresent(removeSource -> acceptedRequest.removeProperty(removeSource,
                removeSourceIRI));
        acceptedRequest.setTargetBranchTitle(targetTitle);
        acceptedRequest.setSourceBranchTitle(sourceTitle);
        acceptedRequest.setTargetCommit(commitFactory.createNew(targetCommitId));
        acceptedRequest.setSourceCommit(commitFactory.createNew(sourceCommitId));
        catalogUtils.updateObject(acceptedRequest, conn);
    }

    @Override
    public void deleteMergeRequestsWithRecordId(Resource recordId) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            deleteMergeRequestsWithRecordId(recordId, conn);
        }
    }

    @Override
    public void deleteMergeRequestsWithRecordId(Resource recordId, RepositoryConnection conn) {
        MergeRequestFilterParams.Builder builder = new MergeRequestFilterParams.Builder();
        builder.setOnRecord(recordId);

        List<MergeRequest> mergeRequests = getMergeRequests(builder.build(), conn);
        mergeRequests.forEach(mergeRequest -> deleteMergeRequest(mergeRequest.getResource(), conn));

        builder.setAccepted(true);
        mergeRequests = getMergeRequests(builder.build(), conn);
        mergeRequests.forEach(mergeRequest -> deleteMergeRequest(mergeRequest.getResource(), conn));
    }

    @Override
    public void cleanMergeRequests(Resource recordId, Resource branchId) {
        try (RepositoryConnection conn = configProvider.getRepository().getConnection()) {
            cleanMergeRequests(recordId, branchId, conn);
        }
    }

    @Override
    public void cleanMergeRequests(Resource recordId, Resource branchId, RepositoryConnection conn) {
        MergeRequestFilterParams.Builder builder = new MergeRequestFilterParams.Builder();
        builder.setOnRecord(recordId);

        List<MergeRequest> mergeRequests = getMergeRequests(builder.build(), conn);
        mergeRequests.forEach(mergeRequest -> {
            mergeRequest.getTargetBranch_resource().ifPresent(targetResource -> {
                if (targetResource.equals(branchId)) {
                    mergeRequest.getModel().remove(mergeRequest.getResource(),
                            vf.createIRI(MergeRequest.targetBranch_IRI), targetResource);
                    updateMergeRequest(mergeRequest.getResource(), mergeRequest, conn);
                }
            });
            mergeRequest.getSourceBranch_resource().ifPresent(sourceResource -> {
                if (sourceResource.equals(branchId)) {
                    deleteMergeRequest(mergeRequest.getResource(), conn);
                }
            });
        });
    }

    private String getBranchTitle(Branch branch) {
        return branch.getProperty(vf.createIRI(_Thing.title_IRI)).orElseThrow(() ->
                new IllegalStateException("Branch " + branch.getResource() + " does not have a title")).stringValue();
    }

    private Resource getBranchHead(Branch branch) {
        return branch.getHead_resource().orElseThrow(() ->
                new IllegalStateException("Branch " + branch.getResource() + " does not have a head Commit"));
    }
}
