/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.mapd.thrift.calciteserver;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TUserDefinedTableFunction implements org.apache.thrift.TBase<TUserDefinedTableFunction, TUserDefinedTableFunction._Fields>, java.io.Serializable, Cloneable, Comparable<TUserDefinedTableFunction> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TUserDefinedTableFunction");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SIZER_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("sizerType", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField SIZER_ARG_POS_FIELD_DESC = new org.apache.thrift.protocol.TField("sizerArgPos", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField INPUT_ARG_TYPES_FIELD_DESC = new org.apache.thrift.protocol.TField("inputArgTypes", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField OUTPUT_ARG_TYPES_FIELD_DESC = new org.apache.thrift.protocol.TField("outputArgTypes", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField SQL_ARG_TYPES_FIELD_DESC = new org.apache.thrift.protocol.TField("sqlArgTypes", org.apache.thrift.protocol.TType.LIST, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TUserDefinedTableFunctionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TUserDefinedTableFunctionTupleSchemeFactory();

  public java.lang.String name; // required
  /**
   * 
   * @see TOutputBufferSizeType
   */
  public TOutputBufferSizeType sizerType; // required
  public int sizerArgPos; // required
  public java.util.List<TExtArgumentType> inputArgTypes; // required
  public java.util.List<TExtArgumentType> outputArgTypes; // required
  public java.util.List<TExtArgumentType> sqlArgTypes; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    /**
     * 
     * @see TOutputBufferSizeType
     */
    SIZER_TYPE((short)2, "sizerType"),
    SIZER_ARG_POS((short)3, "sizerArgPos"),
    INPUT_ARG_TYPES((short)4, "inputArgTypes"),
    OUTPUT_ARG_TYPES((short)5, "outputArgTypes"),
    SQL_ARG_TYPES((short)6, "sqlArgTypes");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // NAME
          return NAME;
        case 2: // SIZER_TYPE
          return SIZER_TYPE;
        case 3: // SIZER_ARG_POS
          return SIZER_ARG_POS;
        case 4: // INPUT_ARG_TYPES
          return INPUT_ARG_TYPES;
        case 5: // OUTPUT_ARG_TYPES
          return OUTPUT_ARG_TYPES;
        case 6: // SQL_ARG_TYPES
          return SQL_ARG_TYPES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SIZERARGPOS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SIZER_TYPE, new org.apache.thrift.meta_data.FieldMetaData("sizerType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TOutputBufferSizeType.class)));
    tmpMap.put(_Fields.SIZER_ARG_POS, new org.apache.thrift.meta_data.FieldMetaData("sizerArgPos", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.INPUT_ARG_TYPES, new org.apache.thrift.meta_data.FieldMetaData("inputArgTypes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TExtArgumentType.class))));
    tmpMap.put(_Fields.OUTPUT_ARG_TYPES, new org.apache.thrift.meta_data.FieldMetaData("outputArgTypes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TExtArgumentType.class))));
    tmpMap.put(_Fields.SQL_ARG_TYPES, new org.apache.thrift.meta_data.FieldMetaData("sqlArgTypes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TExtArgumentType.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TUserDefinedTableFunction.class, metaDataMap);
  }

  public TUserDefinedTableFunction() {
  }

  public TUserDefinedTableFunction(
    java.lang.String name,
    TOutputBufferSizeType sizerType,
    int sizerArgPos,
    java.util.List<TExtArgumentType> inputArgTypes,
    java.util.List<TExtArgumentType> outputArgTypes,
    java.util.List<TExtArgumentType> sqlArgTypes)
  {
    this();
    this.name = name;
    this.sizerType = sizerType;
    this.sizerArgPos = sizerArgPos;
    setSizerArgPosIsSet(true);
    this.inputArgTypes = inputArgTypes;
    this.outputArgTypes = outputArgTypes;
    this.sqlArgTypes = sqlArgTypes;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TUserDefinedTableFunction(TUserDefinedTableFunction other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetSizerType()) {
      this.sizerType = other.sizerType;
    }
    this.sizerArgPos = other.sizerArgPos;
    if (other.isSetInputArgTypes()) {
      java.util.List<TExtArgumentType> __this__inputArgTypes = new java.util.ArrayList<TExtArgumentType>(other.inputArgTypes.size());
      for (TExtArgumentType other_element : other.inputArgTypes) {
        __this__inputArgTypes.add(other_element);
      }
      this.inputArgTypes = __this__inputArgTypes;
    }
    if (other.isSetOutputArgTypes()) {
      java.util.List<TExtArgumentType> __this__outputArgTypes = new java.util.ArrayList<TExtArgumentType>(other.outputArgTypes.size());
      for (TExtArgumentType other_element : other.outputArgTypes) {
        __this__outputArgTypes.add(other_element);
      }
      this.outputArgTypes = __this__outputArgTypes;
    }
    if (other.isSetSqlArgTypes()) {
      java.util.List<TExtArgumentType> __this__sqlArgTypes = new java.util.ArrayList<TExtArgumentType>(other.sqlArgTypes.size());
      for (TExtArgumentType other_element : other.sqlArgTypes) {
        __this__sqlArgTypes.add(other_element);
      }
      this.sqlArgTypes = __this__sqlArgTypes;
    }
  }

  public TUserDefinedTableFunction deepCopy() {
    return new TUserDefinedTableFunction(this);
  }

  @Override
  public void clear() {
    this.name = null;
    this.sizerType = null;
    setSizerArgPosIsSet(false);
    this.sizerArgPos = 0;
    this.inputArgTypes = null;
    this.outputArgTypes = null;
    this.sqlArgTypes = null;
  }

  public java.lang.String getName() {
    return this.name;
  }

  public TUserDefinedTableFunction setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  /**
   * 
   * @see TOutputBufferSizeType
   */
  public TOutputBufferSizeType getSizerType() {
    return this.sizerType;
  }

  /**
   * 
   * @see TOutputBufferSizeType
   */
  public TUserDefinedTableFunction setSizerType(TOutputBufferSizeType sizerType) {
    this.sizerType = sizerType;
    return this;
  }

  public void unsetSizerType() {
    this.sizerType = null;
  }

  /** Returns true if field sizerType is set (has been assigned a value) and false otherwise */
  public boolean isSetSizerType() {
    return this.sizerType != null;
  }

  public void setSizerTypeIsSet(boolean value) {
    if (!value) {
      this.sizerType = null;
    }
  }

  public int getSizerArgPos() {
    return this.sizerArgPos;
  }

  public TUserDefinedTableFunction setSizerArgPos(int sizerArgPos) {
    this.sizerArgPos = sizerArgPos;
    setSizerArgPosIsSet(true);
    return this;
  }

  public void unsetSizerArgPos() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SIZERARGPOS_ISSET_ID);
  }

  /** Returns true if field sizerArgPos is set (has been assigned a value) and false otherwise */
  public boolean isSetSizerArgPos() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SIZERARGPOS_ISSET_ID);
  }

  public void setSizerArgPosIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SIZERARGPOS_ISSET_ID, value);
  }

  public int getInputArgTypesSize() {
    return (this.inputArgTypes == null) ? 0 : this.inputArgTypes.size();
  }

  public java.util.Iterator<TExtArgumentType> getInputArgTypesIterator() {
    return (this.inputArgTypes == null) ? null : this.inputArgTypes.iterator();
  }

  public void addToInputArgTypes(TExtArgumentType elem) {
    if (this.inputArgTypes == null) {
      this.inputArgTypes = new java.util.ArrayList<TExtArgumentType>();
    }
    this.inputArgTypes.add(elem);
  }

  public java.util.List<TExtArgumentType> getInputArgTypes() {
    return this.inputArgTypes;
  }

  public TUserDefinedTableFunction setInputArgTypes(java.util.List<TExtArgumentType> inputArgTypes) {
    this.inputArgTypes = inputArgTypes;
    return this;
  }

  public void unsetInputArgTypes() {
    this.inputArgTypes = null;
  }

  /** Returns true if field inputArgTypes is set (has been assigned a value) and false otherwise */
  public boolean isSetInputArgTypes() {
    return this.inputArgTypes != null;
  }

  public void setInputArgTypesIsSet(boolean value) {
    if (!value) {
      this.inputArgTypes = null;
    }
  }

  public int getOutputArgTypesSize() {
    return (this.outputArgTypes == null) ? 0 : this.outputArgTypes.size();
  }

  public java.util.Iterator<TExtArgumentType> getOutputArgTypesIterator() {
    return (this.outputArgTypes == null) ? null : this.outputArgTypes.iterator();
  }

  public void addToOutputArgTypes(TExtArgumentType elem) {
    if (this.outputArgTypes == null) {
      this.outputArgTypes = new java.util.ArrayList<TExtArgumentType>();
    }
    this.outputArgTypes.add(elem);
  }

  public java.util.List<TExtArgumentType> getOutputArgTypes() {
    return this.outputArgTypes;
  }

  public TUserDefinedTableFunction setOutputArgTypes(java.util.List<TExtArgumentType> outputArgTypes) {
    this.outputArgTypes = outputArgTypes;
    return this;
  }

  public void unsetOutputArgTypes() {
    this.outputArgTypes = null;
  }

  /** Returns true if field outputArgTypes is set (has been assigned a value) and false otherwise */
  public boolean isSetOutputArgTypes() {
    return this.outputArgTypes != null;
  }

  public void setOutputArgTypesIsSet(boolean value) {
    if (!value) {
      this.outputArgTypes = null;
    }
  }

  public int getSqlArgTypesSize() {
    return (this.sqlArgTypes == null) ? 0 : this.sqlArgTypes.size();
  }

  public java.util.Iterator<TExtArgumentType> getSqlArgTypesIterator() {
    return (this.sqlArgTypes == null) ? null : this.sqlArgTypes.iterator();
  }

  public void addToSqlArgTypes(TExtArgumentType elem) {
    if (this.sqlArgTypes == null) {
      this.sqlArgTypes = new java.util.ArrayList<TExtArgumentType>();
    }
    this.sqlArgTypes.add(elem);
  }

  public java.util.List<TExtArgumentType> getSqlArgTypes() {
    return this.sqlArgTypes;
  }

  public TUserDefinedTableFunction setSqlArgTypes(java.util.List<TExtArgumentType> sqlArgTypes) {
    this.sqlArgTypes = sqlArgTypes;
    return this;
  }

  public void unsetSqlArgTypes() {
    this.sqlArgTypes = null;
  }

  /** Returns true if field sqlArgTypes is set (has been assigned a value) and false otherwise */
  public boolean isSetSqlArgTypes() {
    return this.sqlArgTypes != null;
  }

  public void setSqlArgTypesIsSet(boolean value) {
    if (!value) {
      this.sqlArgTypes = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((java.lang.String)value);
      }
      break;

    case SIZER_TYPE:
      if (value == null) {
        unsetSizerType();
      } else {
        setSizerType((TOutputBufferSizeType)value);
      }
      break;

    case SIZER_ARG_POS:
      if (value == null) {
        unsetSizerArgPos();
      } else {
        setSizerArgPos((java.lang.Integer)value);
      }
      break;

    case INPUT_ARG_TYPES:
      if (value == null) {
        unsetInputArgTypes();
      } else {
        setInputArgTypes((java.util.List<TExtArgumentType>)value);
      }
      break;

    case OUTPUT_ARG_TYPES:
      if (value == null) {
        unsetOutputArgTypes();
      } else {
        setOutputArgTypes((java.util.List<TExtArgumentType>)value);
      }
      break;

    case SQL_ARG_TYPES:
      if (value == null) {
        unsetSqlArgTypes();
      } else {
        setSqlArgTypes((java.util.List<TExtArgumentType>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case SIZER_TYPE:
      return getSizerType();

    case SIZER_ARG_POS:
      return getSizerArgPos();

    case INPUT_ARG_TYPES:
      return getInputArgTypes();

    case OUTPUT_ARG_TYPES:
      return getOutputArgTypes();

    case SQL_ARG_TYPES:
      return getSqlArgTypes();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case SIZER_TYPE:
      return isSetSizerType();
    case SIZER_ARG_POS:
      return isSetSizerArgPos();
    case INPUT_ARG_TYPES:
      return isSetInputArgTypes();
    case OUTPUT_ARG_TYPES:
      return isSetOutputArgTypes();
    case SQL_ARG_TYPES:
      return isSetSqlArgTypes();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TUserDefinedTableFunction)
      return this.equals((TUserDefinedTableFunction)that);
    return false;
  }

  public boolean equals(TUserDefinedTableFunction that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_sizerType = true && this.isSetSizerType();
    boolean that_present_sizerType = true && that.isSetSizerType();
    if (this_present_sizerType || that_present_sizerType) {
      if (!(this_present_sizerType && that_present_sizerType))
        return false;
      if (!this.sizerType.equals(that.sizerType))
        return false;
    }

    boolean this_present_sizerArgPos = true;
    boolean that_present_sizerArgPos = true;
    if (this_present_sizerArgPos || that_present_sizerArgPos) {
      if (!(this_present_sizerArgPos && that_present_sizerArgPos))
        return false;
      if (this.sizerArgPos != that.sizerArgPos)
        return false;
    }

    boolean this_present_inputArgTypes = true && this.isSetInputArgTypes();
    boolean that_present_inputArgTypes = true && that.isSetInputArgTypes();
    if (this_present_inputArgTypes || that_present_inputArgTypes) {
      if (!(this_present_inputArgTypes && that_present_inputArgTypes))
        return false;
      if (!this.inputArgTypes.equals(that.inputArgTypes))
        return false;
    }

    boolean this_present_outputArgTypes = true && this.isSetOutputArgTypes();
    boolean that_present_outputArgTypes = true && that.isSetOutputArgTypes();
    if (this_present_outputArgTypes || that_present_outputArgTypes) {
      if (!(this_present_outputArgTypes && that_present_outputArgTypes))
        return false;
      if (!this.outputArgTypes.equals(that.outputArgTypes))
        return false;
    }

    boolean this_present_sqlArgTypes = true && this.isSetSqlArgTypes();
    boolean that_present_sqlArgTypes = true && that.isSetSqlArgTypes();
    if (this_present_sqlArgTypes || that_present_sqlArgTypes) {
      if (!(this_present_sqlArgTypes && that_present_sqlArgTypes))
        return false;
      if (!this.sqlArgTypes.equals(that.sqlArgTypes))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetName()) ? 131071 : 524287);
    if (isSetName())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + ((isSetSizerType()) ? 131071 : 524287);
    if (isSetSizerType())
      hashCode = hashCode * 8191 + sizerType.getValue();

    hashCode = hashCode * 8191 + sizerArgPos;

    hashCode = hashCode * 8191 + ((isSetInputArgTypes()) ? 131071 : 524287);
    if (isSetInputArgTypes())
      hashCode = hashCode * 8191 + inputArgTypes.hashCode();

    hashCode = hashCode * 8191 + ((isSetOutputArgTypes()) ? 131071 : 524287);
    if (isSetOutputArgTypes())
      hashCode = hashCode * 8191 + outputArgTypes.hashCode();

    hashCode = hashCode * 8191 + ((isSetSqlArgTypes()) ? 131071 : 524287);
    if (isSetSqlArgTypes())
      hashCode = hashCode * 8191 + sqlArgTypes.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TUserDefinedTableFunction other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSizerType()).compareTo(other.isSetSizerType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSizerType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sizerType, other.sizerType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSizerArgPos()).compareTo(other.isSetSizerArgPos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSizerArgPos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sizerArgPos, other.sizerArgPos);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetInputArgTypes()).compareTo(other.isSetInputArgTypes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInputArgTypes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.inputArgTypes, other.inputArgTypes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOutputArgTypes()).compareTo(other.isSetOutputArgTypes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOutputArgTypes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.outputArgTypes, other.outputArgTypes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSqlArgTypes()).compareTo(other.isSetSqlArgTypes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSqlArgTypes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sqlArgTypes, other.sqlArgTypes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TUserDefinedTableFunction(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sizerType:");
    if (this.sizerType == null) {
      sb.append("null");
    } else {
      sb.append(this.sizerType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sizerArgPos:");
    sb.append(this.sizerArgPos);
    first = false;
    if (!first) sb.append(", ");
    sb.append("inputArgTypes:");
    if (this.inputArgTypes == null) {
      sb.append("null");
    } else {
      sb.append(this.inputArgTypes);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("outputArgTypes:");
    if (this.outputArgTypes == null) {
      sb.append("null");
    } else {
      sb.append(this.outputArgTypes);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("sqlArgTypes:");
    if (this.sqlArgTypes == null) {
      sb.append("null");
    } else {
      sb.append(this.sqlArgTypes);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TUserDefinedTableFunctionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TUserDefinedTableFunctionStandardScheme getScheme() {
      return new TUserDefinedTableFunctionStandardScheme();
    }
  }

  private static class TUserDefinedTableFunctionStandardScheme extends org.apache.thrift.scheme.StandardScheme<TUserDefinedTableFunction> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TUserDefinedTableFunction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SIZER_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sizerType = com.mapd.thrift.calciteserver.TOutputBufferSizeType.findByValue(iprot.readI32());
              struct.setSizerTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SIZER_ARG_POS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sizerArgPos = iprot.readI32();
              struct.setSizerArgPosIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // INPUT_ARG_TYPES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.inputArgTypes = new java.util.ArrayList<TExtArgumentType>(_list8.size);
                TExtArgumentType _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = com.mapd.thrift.calciteserver.TExtArgumentType.findByValue(iprot.readI32());
                  if (_elem9 != null)
                  {
                    struct.inputArgTypes.add(_elem9);
                  }
                }
                iprot.readListEnd();
              }
              struct.setInputArgTypesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // OUTPUT_ARG_TYPES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list11 = iprot.readListBegin();
                struct.outputArgTypes = new java.util.ArrayList<TExtArgumentType>(_list11.size);
                TExtArgumentType _elem12;
                for (int _i13 = 0; _i13 < _list11.size; ++_i13)
                {
                  _elem12 = com.mapd.thrift.calciteserver.TExtArgumentType.findByValue(iprot.readI32());
                  if (_elem12 != null)
                  {
                    struct.outputArgTypes.add(_elem12);
                  }
                }
                iprot.readListEnd();
              }
              struct.setOutputArgTypesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SQL_ARG_TYPES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list14 = iprot.readListBegin();
                struct.sqlArgTypes = new java.util.ArrayList<TExtArgumentType>(_list14.size);
                TExtArgumentType _elem15;
                for (int _i16 = 0; _i16 < _list14.size; ++_i16)
                {
                  _elem15 = com.mapd.thrift.calciteserver.TExtArgumentType.findByValue(iprot.readI32());
                  if (_elem15 != null)
                  {
                    struct.sqlArgTypes.add(_elem15);
                  }
                }
                iprot.readListEnd();
              }
              struct.setSqlArgTypesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TUserDefinedTableFunction struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.sizerType != null) {
        oprot.writeFieldBegin(SIZER_TYPE_FIELD_DESC);
        oprot.writeI32(struct.sizerType.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SIZER_ARG_POS_FIELD_DESC);
      oprot.writeI32(struct.sizerArgPos);
      oprot.writeFieldEnd();
      if (struct.inputArgTypes != null) {
        oprot.writeFieldBegin(INPUT_ARG_TYPES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.inputArgTypes.size()));
          for (TExtArgumentType _iter17 : struct.inputArgTypes)
          {
            oprot.writeI32(_iter17.getValue());
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.outputArgTypes != null) {
        oprot.writeFieldBegin(OUTPUT_ARG_TYPES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.outputArgTypes.size()));
          for (TExtArgumentType _iter18 : struct.outputArgTypes)
          {
            oprot.writeI32(_iter18.getValue());
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.sqlArgTypes != null) {
        oprot.writeFieldBegin(SQL_ARG_TYPES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, struct.sqlArgTypes.size()));
          for (TExtArgumentType _iter19 : struct.sqlArgTypes)
          {
            oprot.writeI32(_iter19.getValue());
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TUserDefinedTableFunctionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TUserDefinedTableFunctionTupleScheme getScheme() {
      return new TUserDefinedTableFunctionTupleScheme();
    }
  }

  private static class TUserDefinedTableFunctionTupleScheme extends org.apache.thrift.scheme.TupleScheme<TUserDefinedTableFunction> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TUserDefinedTableFunction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetName()) {
        optionals.set(0);
      }
      if (struct.isSetSizerType()) {
        optionals.set(1);
      }
      if (struct.isSetSizerArgPos()) {
        optionals.set(2);
      }
      if (struct.isSetInputArgTypes()) {
        optionals.set(3);
      }
      if (struct.isSetOutputArgTypes()) {
        optionals.set(4);
      }
      if (struct.isSetSqlArgTypes()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetSizerType()) {
        oprot.writeI32(struct.sizerType.getValue());
      }
      if (struct.isSetSizerArgPos()) {
        oprot.writeI32(struct.sizerArgPos);
      }
      if (struct.isSetInputArgTypes()) {
        {
          oprot.writeI32(struct.inputArgTypes.size());
          for (TExtArgumentType _iter20 : struct.inputArgTypes)
          {
            oprot.writeI32(_iter20.getValue());
          }
        }
      }
      if (struct.isSetOutputArgTypes()) {
        {
          oprot.writeI32(struct.outputArgTypes.size());
          for (TExtArgumentType _iter21 : struct.outputArgTypes)
          {
            oprot.writeI32(_iter21.getValue());
          }
        }
      }
      if (struct.isSetSqlArgTypes()) {
        {
          oprot.writeI32(struct.sqlArgTypes.size());
          for (TExtArgumentType _iter22 : struct.sqlArgTypes)
          {
            oprot.writeI32(_iter22.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TUserDefinedTableFunction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sizerType = com.mapd.thrift.calciteserver.TOutputBufferSizeType.findByValue(iprot.readI32());
        struct.setSizerTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.sizerArgPos = iprot.readI32();
        struct.setSizerArgPosIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list23 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.inputArgTypes = new java.util.ArrayList<TExtArgumentType>(_list23.size);
          TExtArgumentType _elem24;
          for (int _i25 = 0; _i25 < _list23.size; ++_i25)
          {
            _elem24 = com.mapd.thrift.calciteserver.TExtArgumentType.findByValue(iprot.readI32());
            if (_elem24 != null)
            {
              struct.inputArgTypes.add(_elem24);
            }
          }
        }
        struct.setInputArgTypesIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list26 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.outputArgTypes = new java.util.ArrayList<TExtArgumentType>(_list26.size);
          TExtArgumentType _elem27;
          for (int _i28 = 0; _i28 < _list26.size; ++_i28)
          {
            _elem27 = com.mapd.thrift.calciteserver.TExtArgumentType.findByValue(iprot.readI32());
            if (_elem27 != null)
            {
              struct.outputArgTypes.add(_elem27);
            }
          }
        }
        struct.setOutputArgTypesIsSet(true);
      }
      if (incoming.get(5)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.sqlArgTypes = new java.util.ArrayList<TExtArgumentType>(_list29.size);
          TExtArgumentType _elem30;
          for (int _i31 = 0; _i31 < _list29.size; ++_i31)
          {
            _elem30 = com.mapd.thrift.calciteserver.TExtArgumentType.findByValue(iprot.readI32());
            if (_elem30 != null)
            {
              struct.sqlArgTypes.add(_elem30);
            }
          }
        }
        struct.setSqlArgTypesIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

