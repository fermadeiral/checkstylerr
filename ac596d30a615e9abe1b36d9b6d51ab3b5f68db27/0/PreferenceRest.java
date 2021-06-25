package com.mobi.preference.rest;

/*-
 * #%L
 * preference.rest
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 - 2020 iNovex Information Systems, Inc.
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

import static com.mobi.rest.util.RestUtils.checkStringParam;
import static com.mobi.rest.util.RestUtils.getActiveUser;
import static com.mobi.rest.util.RestUtils.jsonldToModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mobi.exception.MobiException;
import com.mobi.jaas.api.engines.EngineManager;
import com.mobi.jaas.api.ontologies.usermanagement.User;
import com.mobi.persistence.utils.api.SesameTransformer;
import com.mobi.preference.api.PreferenceService;
import com.mobi.preference.api.ontologies.Preference;
import com.mobi.rdf.api.Model;
import com.mobi.rdf.api.Resource;
import com.mobi.rdf.api.ValueFactory;
import com.mobi.rdf.orm.OrmFactory;
import com.mobi.rdf.orm.OrmFactoryRegistry;
import com.mobi.rest.util.ErrorUtils;
import com.mobi.rest.util.RestUtils;
import io.swagger.annotations.Api;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component(service = PreferenceRest.class, immediate = true)
@Path("/preference")
@Api( value = "/preference" )
public class PreferenceRest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Reference
    PreferenceService preferenceService;

    @Reference
    SesameTransformer transformer;

    @Reference
    EngineManager engineManager;

    @Reference
    ValueFactory vf;

    @Reference
    OrmFactoryRegistry factoryRegistry;

    /**
     * Returns a JSON array of user preferences for the active user
     *
     * @return A JSON array of user preferences for the active user
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response getUserPreferences(@Context ContainerRequestContext context) {
        User user = getActiveUser(context, engineManager);
        Set<Preference> userPreferences = preferenceService.getUserPreferences(user);
        ObjectNode result = mapper.createObjectNode();
        userPreferences.stream().forEach(pref -> {
            JsonNode jsonNode = getPreferenceAsJsonNode(pref);
            result.set(pref.getResource().stringValue(), jsonNode);
        });
        return Response.ok(result.toString()).build();
    }

    @GET
    @Path("groups/{preferenceGroup}/definitions")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response getPreferenceDefinitions(@Context ContainerRequestContext context,
                                             @PathParam("preferenceGroup") String preferenceGroup) {
        Model preferenceDefinitions = preferenceService.getPreferenceDefinitions(vf.createIRI(preferenceGroup));
        return Response.ok(RestUtils.modelToJsonld(preferenceDefinitions, transformer)).build();
    }

    @GET
    @Path("groups")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response getPreferenceGroups(@Context ContainerRequestContext context) {
        List<Resource> userPreferences = preferenceService.getPreferenceGroups();
        return Response.status(200).entity(userPreferences).build();
    }

    @PUT
    @Path("{preferenceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response updateUserPreference(@Context ContainerRequestContext context,
                                         @PathParam("preferenceId") String preferenceId,
                                         @QueryParam("preferenceType") String preferenceType,
                                         String jsonld) {
        checkStringParam(preferenceType, "Preference Type is required");
        checkStringParam(jsonld, "User Preference JSON-LD is required");
        User user = getActiveUser(context, engineManager);
        try {
            Model newUserPreferenceModel = jsonldToModel(jsonld, transformer);
            Preference preference = getPreferenceFromModel(preferenceId, preferenceType, newUserPreferenceModel);
            preferenceService.updatePreference(user, preference);
            return Response.ok().build();
        } catch (IllegalArgumentException ex) {
            throw ErrorUtils.sendError(ex, ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response createUserPreference(@Context ContainerRequestContext context,
                                         @QueryParam("preferenceType") String preferenceType,
                                         String jsonld) {
        checkStringParam(preferenceType, "Preference Type is required");
        checkStringParam(jsonld, "User Preference JSON is required");
        User user = getActiveUser(context, engineManager);
        try {
            Model newUserPreferenceModel = jsonldToModel(jsonld, transformer);
            Preference preference = getPreferenceFromModel(preferenceType, newUserPreferenceModel);
            preferenceService.addPreference(user, preference);
            return Response.status(201).entity(preference.getResource().stringValue()).build();
        } catch (IllegalArgumentException ex) {
            throw ErrorUtils.sendError(ex, ex.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    public JsonNode getPreferenceAsJsonNode(Preference preference) {
        try {
            return mapper.readTree(RestUtils.modelToString(preference.getModel(), RDFFormat.JSONLD, transformer));
        } catch (IOException e) {
            throw new MobiException(e);
        }
    }

    protected Preference getPreferenceFromModel(String preferenceType, Model preferenceModel) {
        Collection<? extends Preference> preferences = getSpecificPreferenceFactory(preferenceType).getAllExisting(preferenceModel);
        if (preferences.size() > 1) {
            throw ErrorUtils.sendError("More than one preference of type: " + preferenceType + " found in request.", Response.Status.BAD_REQUEST);
        } else if (preferences.isEmpty()) {
            throw ErrorUtils.sendError("No preference of type: " + preferenceType + " was found in request.", Response.Status.BAD_REQUEST);
        } else {
            return preferences.iterator().next();
        }
    }

    protected Preference getPreferenceFromModel(String preferenceId, String preferenceType, Model preferenceModel) {
        return getSpecificPreferenceFactory(preferenceType).getExisting(vf.createIRI(preferenceId),
                preferenceModel).orElseThrow(() -> ErrorUtils.sendError("Could not parse " + preferenceType + " preference with id " +
                preferenceId + " from request.", Response.Status.BAD_REQUEST));
    }

    protected OrmFactory<? extends Preference> getSpecificPreferenceFactory(String preferenceType) {
        return (OrmFactory<? extends Preference>) factoryRegistry.getFactoryOfType(preferenceType).orElseThrow(() ->
                ErrorUtils.sendError("Unknown preference type: " + preferenceType, Response.Status.BAD_REQUEST));
    }
}