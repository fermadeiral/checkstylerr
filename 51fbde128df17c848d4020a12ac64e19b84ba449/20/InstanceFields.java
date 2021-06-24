package uk.co.compendiumdev.thingifier.domain.instances;

import uk.co.compendiumdev.thingifier.api.ValidationReport;
import uk.co.compendiumdev.thingifier.domain.definitions.field.definition.FieldType;
import uk.co.compendiumdev.thingifier.domain.definitions.DefinedFields;
import uk.co.compendiumdev.thingifier.domain.definitions.field.definition.Field;
import uk.co.compendiumdev.thingifier.domain.definitions.field.instance.FieldValue;

import java.util.*;

public class InstanceFields {

    private final DefinedFields objectDefinition;
    private Map<String, FieldValue> values = new HashMap<String, FieldValue>();

    public InstanceFields(final DefinedFields objectDefinition) {
        this.objectDefinition = objectDefinition;
    }

    public InstanceFields addIdsToInstance() {
        List<Field>idfields = objectDefinition.getFieldsOfType(FieldType.ID);
        for(Field aField : idfields){
            if(aField.getType()==FieldType.ID){
                if(!values.containsKey(aField.getName().toLowerCase())) {
                    addValue(FieldValue.is(aField.getName(), aField.getNextIdValue()));
                }
            }
        }
        return this;
    }

    public void addValue(final FieldValue value) {
        values.put(value.getName().toLowerCase(), value);
    }

    public FieldValue getAssignedValue(String fieldName) {
        return values.get(fieldName.toLowerCase());
    }

    public FieldValue getFieldValue(String fieldName) {

        // todo support complex fieldNames e.g. person.firstname

        if(!objectDefinition.hasFieldNameDefined(fieldName)){
            reportCannotFindFieldError(fieldName);
        }

        // bypass default processing for OBJECT, ARRAY - at the moment
        // todo: allow defaults for OBJECT, ARRAY, etc.
        Field field = objectDefinition.getField(fieldName);
        if(field.getType()==FieldType.OBJECT){
            getAssignedValue(fieldName);
        }

        // pass back any defaults setup
        FieldValue assignedValue = getAssignedValue(fieldName);
        if (assignedValue == null) {
            // does definition have a default value?
            if (objectDefinition.getField(fieldName).hasDefaultValue()) {
                return objectDefinition.getField(fieldName).getDefaultValue();
            } else {
                // return the field type default value
                String defaultVal = objectDefinition.getField(fieldName).getType().getDefault();
                if (defaultVal != null) {
                    return FieldValue.is(fieldName, defaultVal);
                }
            }
        }

        return assignedValue;
    }

    public String toString() {

        StringBuilder output = new StringBuilder();

        for (Map.Entry<String, FieldValue> entry : values.entrySet()) {
            output.append("\n\t\t\t\t" + entry.getKey() + " : " + entry.getValue() + "\n");
        }

        return output.toString();
    }


    public void deleteAllFieldsExcept(List fieldNamesToIgnore) {

        Set<String> ignorekeys = new HashSet<>(fieldNamesToIgnore);
        Set<String> keys = new HashSet(values.keySet());

        for (String key : keys) {
            if (!ignorekeys.contains(key)) {
                values.remove(key);
            }
        }
    }

    public InstanceFields cloned(){
        final InstanceFields clone = new InstanceFields(objectDefinition);
        for(FieldValue value : values.values()){
            clone.addValue(value.cloned());
        }
        return clone;
    }


    public DefinedFields getDefinition() {
        return objectDefinition;
    }

    public InstanceFields putValue(final String fieldName, final String value) {
        if(objectDefinition.hasFieldNameDefined(fieldName)){
            addValue(FieldValue.is(fieldName, value));
        } else {
            if (fieldName.contains(".")) {
                // this will throw an error if called with a 'relationship'
                // up to the caller to make sure that doesnt' happen
                setFieldNameAsPath(fieldName, value, false);
            } else {
                reportCannotFindFieldError(fieldName);
            }
        }
        return this;
    }

    public InstanceFields setValue(final String fieldName, final String value) {

        if (objectDefinition.hasFieldNameDefined(fieldName)) {
            setFieldValue(objectDefinition.getField(fieldName), FieldValue.is(fieldName, value));
        } else {
            if(fieldName.contains(".")){
                // this will throw an error if called with a 'relationship'
                // up to the caller to make sure that doesnt' happen
                setFieldNameAsPath(fieldName, value, true);
            }else {
                reportCannotFindFieldError(fieldName);
            }
        }

        return this;
    }

    private InstanceFields setFieldValue(final Field field, final FieldValue value) {

        final ValidationReport validationReport = field.validate(value);
        if (validationReport.isValid()) {
            addValue(FieldValue.is(value.getName(),
                                    getActualValueToAdd(
                                        field,
                                        value.asString())));

        } else {
            throw new IllegalArgumentException(
                    validationReport.getCombinedErrorMessages());
        }

        return this;
    }

    /**
     * When values are recieved from json they might be "0.0" for integers etc.
     * So conver them prior to setting
     * @param field
     * @param value
     * @return
     */
    private String getActualValueToAdd(final Field field, final String value) {

        switch (field.getType()){
            case BOOLEAN:
                return Boolean.valueOf(value).toString();
            case FLOAT:
                return Float.valueOf(value).toString();
            case STRING:
                if(field.shouldTruncate()){
                    return value.substring(0,field.getMaximumAllowedLength());
                }else{
                    return value;
                }
            case INTEGER:
            case ID:
                try {
                    Double dVal = Double.parseDouble(value);
                    return String.valueOf(dVal.intValue());
                }catch(Exception e){
                    return Integer.valueOf(value).toString();
                }
            case GUID:
            case OBJECT:
            case ENUM:
            case DATE:
                return value;
            default:
                System.out.println("Unhandled value to add on set");
                return value;
        }

    }

    /*
        set a value in the object hierarchy and create objects as we go
        note that this can create partial objects which may not actually
        match validation rules
    */
    private void setFieldNameAsPath(final String fieldName, final String value, boolean shouldValidateValue) {
        // processing a complex set of fields

        final String[] fields = fieldName.split("\\.");
        final List<String> fieldNames = new ArrayList();
        fieldNames.addAll(Arrays.asList(fields));

        // start recursive call to work through list
        setFieldValue(fieldNames, value, shouldValidateValue);
    }

    /*
        recursive setFieldValue to handle 'objects'
     */
    protected void setFieldValue(final List<String> fieldNames, final String value, boolean shouldValidateValue) {

        String fieldName = fieldNames.get(0);
        if(!objectDefinition.hasFieldNameDefined(fieldName)){
            reportCannotFindFieldError(fieldName);
        }

        final Field field = objectDefinition.getField(fieldName);

        if(fieldNames.size()==1){
            // set the primitive value
            if(shouldValidateValue) {
                setFieldValue(field, FieldValue.is(fieldName, value));
            }else{
                addValue(FieldValue.is(fieldName, value));
            }
            return;
        }else{

            if(field.getType()!= FieldType.OBJECT){
                throw new RuntimeException(
                        "Cannot reference fields on non object fields: " + fieldName);
            }

            // to traverse to next object, may need to create it
            FieldValue objectValue = getAssignedValue(fieldName);
            if(objectValue==null){
                objectValue = createObjectField(fieldName);
            }
            final InstanceFields fieldInstance = objectValue.asObject();

            fieldNames.remove(0); // processed this field

            fieldInstance.setFieldValue(fieldNames, value, shouldValidateValue);
        }

    }

    private FieldValue createObjectField(final String fieldName) {
        if(objectDefinition.hasFieldNameDefined(fieldName)){
            Field field = objectDefinition.getField(fieldName);
            if(field.getType()==FieldType.OBJECT){
                final FieldValue objectValue = FieldValue.is(fieldName,
                        new InstanceFields(field.getObjectDefinition()).
                                addIdsToInstance());
                addValue(objectValue);
                return objectValue;
            }
        }
        return null;
    }

    private void reportCannotFindFieldError(final String fieldName) {
        throw new RuntimeException("Could not find field: " + fieldName);
    }



    public ValidationReport validateFields(final List<String> excluding, final boolean amAllowedToSetIds) {
        ValidationReport report = new ValidationReport();

        // Field validation

        for (String fieldName : objectDefinition.getFieldNames()) {
            if(!excluding.contains(fieldName)) {
                Field field = objectDefinition.getField(fieldName);
                ValidationReport validity = field.validate(
                                                    getAssignedValue(fieldName),
                                                amAllowedToSetIds);
                report.combine(validity);

                // if object then need to go deeper and recursively validate the instance fields
//                if(field.getType() == FieldType.OBJECT){
//                    // if instantiated
//                    FieldValue object = getAssignedValue(fieldName);
//                    if(object!= null && object.asObject()!=null){
//                        final ValidationReport objectValidity =
//                                object.asObject().
//                                validateFields(new ArrayList<>(), true);
//                        report.combine(objectValidity);
//                    }
//                }
            }
        }

        return report;
    }

    public List<String> findAnyGuidOrIdDifferences(final List<Map.Entry<String, String>> args) {

        List<String> errorMessages = new ArrayList<>();

        List<Field> idOrGuidFields = objectDefinition.getFieldsOfType(
                                        FieldType.GUID, FieldType.ID);

        for (Map.Entry<String, String> entry : args) {

            // Handle attempt to amend a protected field
            Field field = objectDefinition.getField(entry.getKey());
            if (idOrGuidFields.contains(field)) {
                // if editing it then throw error, ignore if same value
                String existingValue = getFieldValue(entry.getKey()).asString();
                if (existingValue != null && existingValue.trim().length() > 0) {
                    // if value is different then it is an attempt to amend it
                    if (!existingValue.equalsIgnoreCase(entry.getValue())) {
                        errorMessages.add(
                                String.format("Can not amend %s from %s to %s",
                                        entry.getKey(),
                                        existingValue,
                                        entry.getValue()));
                    }
                }
            }
        }
        return errorMessages;
    }

    public void setValuesFromClone(final InstanceFields args) {
        for(String fieldName : objectDefinition.getFieldNames()){
            FieldValue value = args.getAssignedValue(fieldName);
            if(value!=null){
                addValue(value);
            }
        }
    }
}
