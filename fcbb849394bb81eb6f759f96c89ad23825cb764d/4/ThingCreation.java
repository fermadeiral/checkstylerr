package uk.co.compendiumdev.thingifier.api.restapihandlers;

import uk.co.compendiumdev.thingifier.Thing;
import uk.co.compendiumdev.thingifier.Thingifier;
import uk.co.compendiumdev.thingifier.api.ValidationReport;
import uk.co.compendiumdev.thingifier.api.http.bodyparser.BodyParser;
import uk.co.compendiumdev.thingifier.api.response.ApiResponse;
import uk.co.compendiumdev.thingifier.generic.definitions.RelationshipVector;
import uk.co.compendiumdev.thingifier.generic.instances.ThingInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ThingCreation {

    private final Thingifier thingifier;

    public ThingCreation(final Thingifier thingifier) {
        this.thingifier = thingifier;
    }

    public ApiResponse with(final BodyParser bodyargs, final Thing thing) {

        ValidationReport validated = new BodyRelationshipValidator(thingifier).validate(bodyargs, thing);

        if(!validated.isValid()){
            return ApiResponse.error(400, String.format("Invalid relationships.%n%s",validated.getCombinedErrorMessages()));
        }

        return addNewThingWithFields(bodyargs, thing.createInstance(), thing);
    }

    public ApiResponse withGuid(final String instanceGuid, final BodyParser bodyargs, final Thing thing) {

        final Map<String, String> args = bodyargs.getStringMap();

        ThingInstance instance;

        try {
            String aGUID = UUID.fromString(instanceGuid).toString();
            instance = thing.createInstance(aGUID);

        } catch (Exception e) {
            // that is not a valid guid
            System.out.println(e.getMessage());
            return ApiResponse.error404(String.format("Invalid GUID for %s entity %s", instanceGuid, thing.definition().getName()));
        }

        return addNewThingWithFields(bodyargs, instance, thing);
    }


    private ApiResponse addNewThingWithFields(final BodyParser bodyargs, final ThingInstance instance, final Thing thing) {

        final Map<String, String> args = bodyargs.getStringMap();

        try {
            instance.setFieldValuesFrom(args);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }

        ValidationReport validation = instance.validateFields();

        if (validation.isValid()) {
            thing.addInstance(instance);

            try {
                List<RelationshipDetails> relationships = getRelationshipsFromArgs(bodyargs);
                for (RelationshipDetails relationship : relationships) {
                    instance.connects(relationship.relationshipName,
                            thingifier.getThingNamedSingularOrPlural(relationship.toType).
                                    findInstanceByGUID(relationship.guidValue));
                }
            }catch(Exception e){
                return ApiResponse.error(400, "Error creating relationships " + e.getMessage());
            }

            return ApiResponse.created(instance);
        } else {
            // do not add it, report the errors
            return ApiResponse.error(400, validation.getErrorMessages());
        }
    }

    private List<RelationshipDetails> getRelationshipsFromArgs(final BodyParser bodyargs) {

        Map<String,String> fullargs = bodyargs.getFlattenedStringMap();
        List<RelationshipDetails>relationships = new ArrayList<>();

        // assume any relationships errors already reported

        for(Map.Entry<String, String> complexKeyValue : fullargs.entrySet()) {
            //is it a relationship?
            String complexKey = complexKeyValue.getKey();
            if (complexKey.startsWith("relationships.")) {
                String[] parts = complexKey.split("\\.");
                if (parts.length == 4) {
                    relationships.add(
                            new RelationshipDetails(
                                    parts[1], parts[2], parts[3],
                                    complexKeyValue.getValue()));
                }
            }
        }
        return relationships;
    }

    private class RelationshipDetails {
        public final String relationshipName;
        public final String toType;
        public final String guidName;
        public final String guidValue;

        public RelationshipDetails(final String relationshipName, final String toType, final String keyName, final String keyValue) {
            this.relationshipName = relationshipName;
            this.toType = toType;
            this.guidName = keyName;
            this.guidValue = keyValue;
        }
    }
}

