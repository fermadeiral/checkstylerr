package uk.co.compendiumdev.thingifier.domain.instances;

import uk.co.compendiumdev.thingifier.api.ValidationReport;
import uk.co.compendiumdev.thingifier.api.http.bodyparser.BodyParser;
import uk.co.compendiumdev.thingifier.domain.FieldType;
import uk.co.compendiumdev.thingifier.domain.definitions.Field;
import uk.co.compendiumdev.thingifier.domain.definitions.RelationshipVector;
import uk.co.compendiumdev.thingifier.domain.definitions.ThingDefinition;

import java.util.*;

import static uk.co.compendiumdev.thingifier.domain.definitions.Optionality.MANDATORY_RELATIONSHIP;

public class ThingInstance {

    // TODO: this is messy because of cloning and documentation - find a way to simplify
    // TODO split 'objectInstance' from ThingInstance i.e. without relationships and without a GUID

    private final Relationships relationships;
    private final ThingDefinition entityDefinition;
    private final InstanceFields instanceFields;
    private Map<String, ThingInstance> objectFields;

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
        this.instanceFields = new InstanceFields();
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

        if (this.entityDefinition.hasFieldNameDefined(fieldName)) {

            Field field = entityDefinition.getField(fieldName);
            return setFieldValue(field, fieldName, value);

        } else {

            if(fieldName.contains(".")){
                return processFieldNameAsObject(fieldName, value);

            }else {
                reportCannotFindFieldError(fieldName);
            }
        }
        return this;
    }

    private ThingInstance processFieldNameAsObject(final String fieldName, final String value) {
        // processing a complex set of fields
        final String[] fields = fieldName.split("\\.");
        String toplevelFieldName = fields[0];
        if (this.entityDefinition.hasFieldNameDefined(toplevelFieldName)){

            // process a setField on an Object field
            Field field = entityDefinition.getField(toplevelFieldName);
            if(field.getType()!= FieldType.OBJECT){
                throw new RuntimeException(
                        "Cannot reference fields on non object fields: " + fieldName + " on Entity " + this.entityDefinition.getName());
            }
            final List<String> fieldNames = new ArrayList();
            fieldNames.addAll(Arrays.asList(fields));
            fieldNames.remove(0);
            // need to track the instance with it
            final ThingInstance fieldInstance = getObjectInstance(toplevelFieldName);

            return setFieldValue(fieldInstance, fieldNames, value);
        }else{
            // if it is not a relationship then we should throw an error because we don't recognise it as an object
            if(!this.entityDefinition.hasRelationship(toplevelFieldName)) {
                reportCannotFindFieldError(fieldName);
            }
        }
        return this;
    }

    private ThingInstance setFieldValue(final ThingInstance fieldInstance, final List<String> fieldNames, final String value) {

        String fieldName = fieldNames.get(0);
        final ThingDefinition defn = fieldInstance.entityDefinition;
        if(defn==null || !defn.hasFieldNameDefined(fieldName)){
            reportCannotFindFieldError(fieldName);
        }

        final Field nextField = defn.getField(fieldName);

        if(fieldNames.size()==1){
            return fieldInstance.setFieldValue(nextField, fieldName, value);
        }else{

            if(nextField.getType()!= FieldType.OBJECT){
                throw new RuntimeException(
                        "Cannot reference fields on non object fields: " + fieldName + " on Entity " + this.entityDefinition.getName());
            }
            fieldNames.remove(0);

            final ThingInstance nextFieldInstance = fieldInstance.getObjectInstance(fieldName);
            return setFieldValue(nextFieldInstance, fieldNames, value);
        }

    }

    /* for a given field in this instance, set the value */
    private ThingInstance setFieldValue(final Field field, final String fieldName, final String value) {
        final ValidationReport validationReport = field.validate(value);
        if (validationReport.isValid()) {
            this.instanceFields.addValue(
                                    fieldName,
                                    field.getValueToAdd(value));

        } else {
            throw new IllegalArgumentException(
                    validationReport.getCombinedErrorMessages());
        }

        return this;
    }

    public ThingInstance setFieldValuesFrom(final BodyParser args) {

        // protected fields
        List<String> idOrGuidFields = entityDefinition.getProtectedFieldNamesList();

        for (Map.Entry<String, String> entry : args.getFlattenedStringMap()) {

            // Handle attempt to amend a protected field
            if (!idOrGuidFields.contains(entry.getKey())) {
                // set the value because it is not protected
                setValue(entry.getKey(), entry.getValue());
            } else {
                // if editing it then throw error, ignore if same value
                String existingValue = instanceFields.getValue(entry.getKey());
                if (existingValue != null && existingValue.trim().length() > 0) {

                    // if value is different then it is an attempt to amend it
                    if (!existingValue.equalsIgnoreCase(entry.getValue())) {
                        throw new RuntimeException(
                                String.format("Can not amend %s on Entity %s from %s to %s",
                                        entry.getKey(),
                                        this.entityDefinition.getName(),
                                        existingValue,
                                        entry.getValue()));
                    }
                }
            }
        }
        return this;
    }



    public void overrideValue(final String key, final String value) {
        // bypass all validation
        this.instanceFields.addValue(key, value);
    }

    private void reportCannotFindFieldError(final String fieldName) {
        throw new RuntimeException("Could not find field: " + fieldName + " on Entity " + this.entityDefinition.getName());
    }

    public String getValue(String fieldName) {
        if (this.entityDefinition.hasFieldNameDefined(fieldName)) {
            // if an object, just return "", we should be using this to get the value
            if(this.entityDefinition.getField(fieldName).getType()==FieldType.OBJECT){
                return "";
            }

            String assignedValue = this.instanceFields.getValue(fieldName);
            if (assignedValue == null) {
                // does definition have a default value?
                if (this.entityDefinition.getField(fieldName).hasDefaultValue()) {
                    return getDefaultValue(fieldName);
                } else {
                    // return the field type default value
                    String defaultVal = this.entityDefinition.getField(fieldName).getType().getDefault();
                    if (defaultVal != null) {
                        return defaultVal;
                    }
                }
            } else {
                return assignedValue;
            }
        }

        reportCannotFindFieldError(fieldName);
        return "";
    }

    public ThingDefinition getEntity() {
        return this.entityDefinition;
    }


    public String getDefaultValue(String defaultFieldValue) {
        return entityDefinition.getField(defaultFieldValue).getDefaultValue();
    }

    public boolean hasIDField() {
        return entityDefinition.hasIDField();
    }

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
        Validation - suspect this could be a separate class for a ThingInstanceValidator
     */

    public ValidationReport validateFields(){
        return validateFields(new ArrayList<>());
    }

    public ValidationReport validateFields(List<String> excluding){
        ValidationReport report = new ValidationReport();

        // Field validation

        for (String fieldName : entityDefinition.getFieldNames()) {
            if(!excluding.contains(fieldName)) {
                Field field = entityDefinition.getField(fieldName);
                ValidationReport validity = field.validate(instanceFields.getValue(fieldName));
                report.combine(validity);
            }
        }

        return report;
    }
    public ValidationReport validateNonProtectedFields() {
        List<String> excluding = getProtectedFieldNamesList(); // guid and ids
        return validateFields(excluding);
    }

    private List<String> getProtectedFieldNamesList() {
        return entityDefinition.getProtectedFieldNamesList();
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

    public ThingInstance getObjectInstance(final String objectFieldName) {
        Field objectField = entityDefinition.getField(objectFieldName);
        if(objectField.getType()==FieldType.OBJECT){
            if(objectFields==null){
                objectFields = new HashMap<>();
            }
            ThingInstance instance = objectFields.get(objectFieldName);
            if(instance==null){
                instance = new ThingInstance(objectField.getObjectDefinition());
                addObjectInstance(objectFieldName, instance);
            }
            return instance;
        }
        return null;
    }

    private void addObjectInstance(final String objectFieldName, final ThingInstance thingInstance) {
        if(objectFields==null){
            objectFields = new HashMap<>();
        }
        objectFields.put(objectFieldName, thingInstance);
    }
}
