package uk.co.compendiumdev.thingifier.domain.instances;

import uk.co.compendiumdev.thingifier.api.ValidationReport;
import uk.co.compendiumdev.thingifier.api.http.bodyparser.BodyParser;
import uk.co.compendiumdev.thingifier.domain.definitions.Field;
import uk.co.compendiumdev.thingifier.domain.definitions.ThingDefinition;

import java.util.*;

public class ThingInstance {

    // TODO: this is messy because of cloning and documentation - find a way to simplify

    private final Relationships relationships;
    private final ThingDefinition entityDefinition;
    private final InstanceFields instanceFields;


    public ThingInstance(ThingDefinition eDefn) {
        this(eDefn, true);
    }

    protected static ThingInstance getInstanceWithoutIds(ThingDefinition eDefn){
        return new ThingInstance(eDefn, false);
    }

    /**
     * Without ids this would be a dangerous thing instance since
     * the ids are supposed to be global to the entity
     * without ids should only be used for creating documentation or
     * example instances
     * @param eDefn
     * @param addIds
     */
    private ThingInstance(ThingDefinition eDefn, boolean addIds) {
        this.entityDefinition = eDefn;
        this.instanceFields = new InstanceFields(eDefn.getFieldDefinitions());
        instanceFields.addValue("guid", UUID.randomUUID().toString());
        if(addIds) {
            eDefn.addIdsToInstance(instanceFields);
        }
        this.relationships = new Relationships(this);
    }

    public ThingInstance(ThingDefinition entityTestSession, String guid) {
        this(entityTestSession);
        instanceFields.addValue("guid", guid);
    }

    public String toString() {

        StringBuilder output = new StringBuilder();

        output.append("\t\t\t" + entityDefinition.getName() + "\n");
        //output.append(instance.toString() + "\n");
        for (String fieldName : entityDefinition.getFieldNames()) {
            output.append(String.format("\t\t\t\t %s : %s %n", fieldName, getValue(fieldName)));
        }

        output.append(relationships.toString());

        return output.toString();
    }

    public String getGUID() {
        return instanceFields.getValue("guid");
    }

    public List<String> getFieldNames() {
        return this.entityDefinition.getFieldNames();
    }

    public ThingInstance setValue(String fieldName, String value) {
        instanceFields.setValue(fieldName, value);
        return this;
    }

    public ThingInstance setFieldValuesFrom(final BodyParser args) {

        final List<String> anyErrors = instanceFields.findAnyGuidOrIdDifferences(args);
        if(anyErrors.size()>0){
            throw new RuntimeException(anyErrors.get(0));
        }

        setFieldValuesFromArgsIgnoring(args, entityDefinition.getProtectedFieldNamesList());

        return this;
    }

    public void setFieldValuesFromArgsIgnoring(final BodyParser args,
                                                final List<String> ignoreFields) {

        for (Map.Entry<String, String> entry : args.getFlattenedStringMap()) {

            // Handle attempt to amend a protected field
            if (!ignoreFields.contains(entry.getKey())) {
                // set the value because it is not protected
                setValue(entry.getKey(), entry.getValue());
            }
        }
    }

    public void overrideFieldValuesFromArgsIgnoring(final BodyParser args,
                                               final List<String> ignoreFields) {

        for (Map.Entry<String, String> entry : args.getFlattenedStringMap()) {

            // Handle attempt to amend a protected field
            if (!ignoreFields.contains(entry.getKey())) {
                // set the value because it is not protected
                overrideValue(entry.getKey(), entry.getValue());
            }
        }
    }

    public void overrideValue(final String key, final String value) {
        // bypass all validation - except, field must exist
        this.instanceFields.putFieldValue(key, value);
    }

    public String getValue(String fieldName) {
        return instanceFields.getValue(fieldName);
    }

    public InstanceFields getObjectValue(String fieldName){
        return instanceFields.getObjectInstance(fieldName);
    }

    public ThingDefinition getEntity() {
        return this.entityDefinition;
    }


    public boolean hasIDField() {
        return entityDefinition.hasIDField();
    }

    // todo assumes that there is one id field - should allow more
    public String getID() {
        String value = "";
        final Field field = entityDefinition.getIDField();
        if(field!=null) {
            value = getValue(field.getName());
        }
        return value;
    }

    /**
     * connect this thing to another thing using the relationship relationshipName
     */
    public void connects(String relationshipName, ThingInstance thing) {
        relationships.connect( relationshipName, thing);
    }

    protected void isNowRelatedVia(RelationshipInstance relationship) {
        // if the relationship vector has a parent that is both ways then we need to create a relationship of the reverse type to the thing that called us
        if (relationship.getRelationship().isTwoWay()) {
            relationships.add(relationship);
        }
    }

    public ThingDefinition typeOfConnectedItems(String relationshipName) {
        return relationships.getTypeOfConnectedItems(relationshipName);
    }

    public Collection<ThingInstance> connectedItems(String relationshipName) {
        return relationships.getConnectedItems(relationshipName);
    }

    public void removeRelationshipsTo(ThingInstance thing, String relationshipName) {
        relationships.removeRelationshipsTo(thing, relationshipName);

    }

    public List<ThingInstance> removeAllRelationships() {
        return relationships.removeAllRelationships();
    }

    public void isNoLongerRelatedVia(RelationshipInstance relationship) {
        // delete any relationship to or from
        relationships.remove(relationship);
    }

    public void removeRelationshipsInvolvingMe(ThingInstance thing) {
        relationships.removeAllRelationshipsInvolving(thing);
    }

    public List<ThingInstance> connectedItemsOfType(String type) {
        return relationships.connectedItemsOfType(type);
    }

    public boolean hasAnyRelationshipInstances() {
        return relationships.hasAnyRelationshipInstances();

    }

    /*
        Validation
     */

    private ValidationReport validateFields(){
        return validateFieldValues(new ArrayList<>(), false);
    }

    public ValidationReport validateFieldValues(List<String> excluding, boolean amAllowedToSetIds){
        return instanceFields.validateFields(excluding, amAllowedToSetIds);
    }

    public ValidationReport validateNonProtectedFields() {
        return validateFieldValues(
                    entityDefinition.getProtectedFieldNamesList(),
                    false);
    }

    public ValidationReport validateRelationships(){
        return relationships.validateRelationships();

    }

    public ValidationReport validate() {
        ValidationReport report = new ValidationReport();

        report.combine(validateFields());
        report.combine(validateRelationships());

        return report;
    }






    // Cloning and documentation

    public void clearAllFields() {
        List<String>ignoreFields = new ArrayList<>();

        ignoreFields.add("guid");
        if(hasIDField()){
            ignoreFields.add(getEntity().getIDField().getName());
        }

        instanceFields.deleteAllFieldsExcept(ignoreFields);
    }

    public ThingInstance setCloneFieldValuesFrom(final Map<String, String> args) {

        for (Map.Entry<String, String> entry : args.entrySet()) {
            overrideValue(entry.getKey(), entry.getValue());
        }

        return this;
    }

    public ThingInstance createDuplicateWithoutRelationships() {
        ThingInstance cloneInstance = new ThingInstance(entityDefinition, false);
        cloneInstance.setCloneFieldValuesFrom(instanceFields.asMap());
        return cloneInstance;
    }

    public ThingInstance createDuplicateWithRelationships() {
        ThingInstance cloneInstance = new ThingInstance(entityDefinition, false);
        cloneInstance.setCloneFieldValuesFrom(instanceFields.asMap());
        cloneInstance.setCloneRelationships(relationships.createClonedRelationships());
        return cloneInstance;
    }

    private void setCloneRelationships(final List<RelationshipInstance> clonedRelationships) {
        for(RelationshipInstance relationship : clonedRelationships){
            relationships.add(relationship);
        }
    }

    public InstanceFields getFields() {
        return instanceFields;
    }
}
