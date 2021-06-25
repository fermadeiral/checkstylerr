package com.mobi.catalog.rest.impl;

/*-
 * #%L
 * com.mobi.catalog.rest
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2018 iNovex Information Systems, Inc.
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
import static com.mobi.catalog.rest.utils.CatalogRestUtils.createCommitJson;
import static com.mobi.catalog.rest.utils.CatalogRestUtils.createCommitResponse;
import static com.mobi.catalog.rest.utils.CatalogRestUtils.getDifferenceJsonString;
import static com.mobi.rest.util.RestUtils.checkStringParam;
import static com.mobi.rest.util.RestUtils.createPaginatedResponseWithJson;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import com.mobi.catalog.api.CatalogManager;
import com.mobi.catalog.api.builder.Difference;
import com.mobi.catalog.api.ontologies.mcat.Commit;
import com.mobi.catalog.rest.CommitRest;
import com.mobi.exception.MobiException;
import com.mobi.jaas.api.engines.EngineManager;
import com.mobi.persistence.utils.api.BNodeService;
import com.mobi.persistence.utils.api.SesameTransformer;
import com.mobi.rdf.api.ValueFactory;
import com.mobi.rest.util.ErrorUtils;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component(immediate = true)
public class CommitRestImpl implements CommitRest {
    private static final Logger logger = LoggerFactory.getLogger(CommitRestImpl.class);

    private BNodeService bNodeService;
    private CatalogManager catalogManager;
    private SesameTransformer transformer;
    private ValueFactory vf;

    protected EngineManager engineManager;

    @Reference
    void setbNodeService(BNodeService bNodeService) {
        this.bNodeService = bNodeService;
    }

    @Reference
    void setCatalogManager(CatalogManager catalogManager) {
        this.catalogManager = catalogManager;
    }

    @Reference
    void setTransformer(SesameTransformer transformer) {
        this.transformer = transformer;
    }

    @Reference
    void setVf(ValueFactory vf) {
        this.vf = vf;
    }

    @Reference
    void setEngineManager(EngineManager engineManager) {
        this.engineManager = engineManager;
    }

    @Override
    public Response getCommit(String commitId, String format) {
        long start = System.currentTimeMillis();
        try {
            Response response = Response.status(Response.Status.NOT_FOUND).build();
            Optional<Commit> optCommit = catalogManager.getCommit(vf.createIRI(commitId));

            if (optCommit.isPresent()) {
                response = createCommitResponse(optCommit.get(),
                        catalogManager.getCommitDifference(optCommit.get().getResource()),
                        format, transformer, bNodeService);
            }
            return response;
        } finally {
            logger.trace("getCommit took {}ms", System.currentTimeMillis() - start);
        }
    }

    @Override
    public Response getCommitHistory(UriInfo uriInfo, String commitId, int offset, int limit) {
        long start = System.currentTimeMillis();
        try {
            Response response = Response.status(Response.Status.NOT_FOUND).build();

            if (offset < 0) {
                throw ErrorUtils.sendError("Offset cannot be negative.", Response.Status.BAD_REQUEST);
            }

            if (limit < 0 || (offset > 0 && limit == 0)) {
                throw ErrorUtils.sendError("Limit must be positive.", Response.Status.BAD_REQUEST);
            }

            try {
                JSONArray commitChain = new JSONArray();

                final List<Commit> commits;

                commits = catalogManager.getCommitChain(vf.createIRI(commitId));

                Stream<Commit> result = commits.stream();
                if (limit > 0) {
                    result = result.skip(offset)
                            .limit(limit);
                }
                result.map(r -> createCommitJson(r, vf, engineManager)).forEach(commitChain::add);
                response = createPaginatedResponseWithJson(uriInfo, commitChain, commits.size(), limit, offset);
            } catch (IllegalArgumentException ex) {
                throw ErrorUtils.sendError(ex, ex.getMessage(), Response.Status.BAD_REQUEST);
            } catch (IllegalStateException | MobiException ex) {
                throw ErrorUtils.sendError(ex, ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
            }
            return response;
        } finally {
            logger.trace("getCommitHistory took {}ms", System.currentTimeMillis() - start);
        }
    }

    @Override
    public Response getDifference(String source, String target, String rdfFormat) {
        long start = System.currentTimeMillis();
        try {
            Response response = Response.status(Response.Status.NOT_FOUND).build();
            checkStringParam(source, "Source commit is required");
            checkStringParam(target, "Target commit is required");

            try {
                Difference diff = catalogManager.getDifference(vf.createIRI(source), vf.createIRI(target));
                response = Response.ok(getDifferenceJsonString(diff, rdfFormat, transformer, bNodeService), MediaType.APPLICATION_JSON).build();
            } catch (IllegalArgumentException ex) {
                throw ErrorUtils.sendError(ex, ex.getMessage(), Response.Status.BAD_REQUEST);
            } catch (IllegalStateException | MobiException ex) {
                throw ErrorUtils.sendError(ex, ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
            }
            return response;
        } finally {
            logger.trace("getDifference took {}ms", System.currentTimeMillis() - start);
        }
    }
}
