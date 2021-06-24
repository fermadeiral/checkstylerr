/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TTypeInfo implements org.apache.thrift.TBase<TTypeInfo, TTypeInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TTypeInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TTypeInfo");

  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ENCODING_FIELD_DESC = new org.apache.thrift.protocol.TField("encoding", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField NULLABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("nullable", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField IS_ARRAY_FIELD_DESC = new org.apache.thrift.protocol.TField("is_array", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField PRECISION_FIELD_DESC = new org.apache.thrift.protocol.TField("precision", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField SCALE_FIELD_DESC = new org.apache.thrift.protocol.TField("scale", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField COMP_PARAM_FIELD_DESC = new org.apache.thrift.protocol.TField("comp_param", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("size", org.apache.thrift.protocol.TType.I32, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TTypeInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TTypeInfoTupleSchemeFactory();

  /**
   * 
   * @see TDatumType
   */
  public @org.apache.thrift.annotation.Nullable TDatumType type; // required
  /**
   * 
   * @see TEncodingType
   */
  public @org.apache.thrift.annotation.Nullable TEncodingType encoding; // required
  public boolean nullable; // required
  public boolean is_array; // required
  public int precision; // required
  public int scale; // required
  public int comp_param; // required
  public int size; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see TDatumType
     */
    TYPE((short)1, "type"),
    /**
     * 
     * @see TEncodingType
     */
    ENCODING((short)4, "encoding"),
    NULLABLE((short)2, "nullable"),
    IS_ARRAY((short)3, "is_array"),
    PRECISION((short)5, "precision"),
    SCALE((short)6, "scale"),
    COMP_PARAM((short)7, "comp_param"),
    SIZE((short)8, "size");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TYPE
          return TYPE;
        case 4: // ENCODING
          return ENCODING;
        case 2: // NULLABLE
          return NULLABLE;
        case 3: // IS_ARRAY
          return IS_ARRAY;
        case 5: // PRECISION
          return PRECISION;
        case 6: // SCALE
          return SCALE;
        case 7: // COMP_PARAM
          return COMP_PARAM;
        case 8: // SIZE
          return SIZE;
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
    @org.apache.thrift.annotation.Nullable
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
  private static final int __NULLABLE_ISSET_ID = 0;
  private static final int __IS_ARRAY_ISSET_ID = 1;
  private static final int __PRECISION_ISSET_ID = 2;
  private static final int __SCALE_ISSET_ID = 3;
  private static final int __COMP_PARAM_ISSET_ID = 4;
  private static final int __SIZE_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.SIZE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TDatumType.class)));
    tmpMap.put(_Fields.ENCODING, new org.apache.thrift.meta_data.FieldMetaData("encoding", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TEncodingType.class)));
    tmpMap.put(_Fields.NULLABLE, new org.apache.thrift.meta_data.FieldMetaData("nullable", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.IS_ARRAY, new org.apache.thrift.meta_data.FieldMetaData("is_array", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.PRECISION, new org.apache.thrift.meta_data.FieldMetaData("precision", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SCALE, new org.apache.thrift.meta_data.FieldMetaData("scale", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.COMP_PARAM, new org.apache.thrift.meta_data.FieldMetaData("comp_param", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SIZE, new org.apache.thrift.meta_data.FieldMetaData("size", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TTypeInfo.class, metaDataMap);
  }

  public TTypeInfo() {
    this.size = -1;

  }

  public TTypeInfo(
    TDatumType type,
    TEncodingType encoding,
    boolean nullable,
    boolean is_array,
    int precision,
    int scale,
    int comp_param)
  {
    this();
    this.type = type;
    this.encoding = encoding;
    this.nullable = nullable;
    setNullableIsSet(true);
    this.is_array = is_array;
    setIs_arrayIsSet(true);
    this.precision = precision;
    setPrecisionIsSet(true);
    this.scale = scale;
    setScaleIsSet(true);
    this.comp_param = comp_param;
    setComp_paramIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TTypeInfo(TTypeInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetEncoding()) {
      this.encoding = other.encoding;
    }
    this.nullable = other.nullable;
    this.is_array = other.is_array;
    this.precision = other.precision;
    this.scale = other.scale;
    this.comp_param = other.comp_param;
    this.size = other.size;
  }

  public TTypeInfo deepCopy() {
    return new TTypeInfo(this);
  }

  @Override
  public void clear() {
    this.type = null;
    this.encoding = null;
    setNullableIsSet(false);
    this.nullable = false;
    setIs_arrayIsSet(false);
    this.is_array = false;
    setPrecisionIsSet(false);
    this.precision = 0;
    setScaleIsSet(false);
    this.scale = 0;
    setComp_paramIsSet(false);
    this.comp_param = 0;
    this.size = -1;

  }

  /**
   * 
   * @see TDatumType
   */
  @org.apache.thrift.annotation.Nullable
  public TDatumType getType() {
    return this.type;
  }

  /**
   * 
   * @see TDatumType
   */
  public TTypeInfo setType(@org.apache.thrift.annotation.Nullable TDatumType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  /**
   * 
   * @see TEncodingType
   */
  @org.apache.thrift.annotation.Nullable
  public TEncodingType getEncoding() {
    return this.encoding;
  }

  /**
   * 
   * @see TEncodingType
   */
  public TTypeInfo setEncoding(@org.apache.thrift.annotation.Nullable TEncodingType encoding) {
    this.encoding = encoding;
    return this;
  }

  public void unsetEncoding() {
    this.encoding = null;
  }

  /** Returns true if field encoding is set (has been assigned a value) and false otherwise */
  public boolean isSetEncoding() {
    return this.encoding != null;
  }

  public void setEncodingIsSet(boolean value) {
    if (!value) {
      this.encoding = null;
    }
  }

  public boolean isNullable() {
    return this.nullable;
  }

  public TTypeInfo setNullable(boolean nullable) {
    this.nullable = nullable;
    setNullableIsSet(true);
    return this;
  }

  public void unsetNullable() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NULLABLE_ISSET_ID);
  }

  /** Returns true if field nullable is set (has been assigned a value) and false otherwise */
  public boolean isSetNullable() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NULLABLE_ISSET_ID);
  }

  public void setNullableIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NULLABLE_ISSET_ID, value);
  }

  public boolean isIs_array() {
    return this.is_array;
  }

  public TTypeInfo setIs_array(boolean is_array) {
    this.is_array = is_array;
    setIs_arrayIsSet(true);
    return this;
  }

  public void unsetIs_array() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IS_ARRAY_ISSET_ID);
  }

  /** Returns true if field is_array is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_array() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IS_ARRAY_ISSET_ID);
  }

  public void setIs_arrayIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IS_ARRAY_ISSET_ID, value);
  }

  public int getPrecision() {
    return this.precision;
  }

  public TTypeInfo setPrecision(int precision) {
    this.precision = precision;
    setPrecisionIsSet(true);
    return this;
  }

  public void unsetPrecision() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PRECISION_ISSET_ID);
  }

  /** Returns true if field precision is set (has been assigned a value) and false otherwise */
  public boolean isSetPrecision() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PRECISION_ISSET_ID);
  }

  public void setPrecisionIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PRECISION_ISSET_ID, value);
  }

  public int getScale() {
    return this.scale;
  }

  public TTypeInfo setScale(int scale) {
    this.scale = scale;
    setScaleIsSet(true);
    return this;
  }

  public void unsetScale() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SCALE_ISSET_ID);
  }

  /** Returns true if field scale is set (has been assigned a value) and false otherwise */
  public boolean isSetScale() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SCALE_ISSET_ID);
  }

  public void setScaleIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SCALE_ISSET_ID, value);
  }

  public int getComp_param() {
    return this.comp_param;
  }

  public TTypeInfo setComp_param(int comp_param) {
    this.comp_param = comp_param;
    setComp_paramIsSet(true);
    return this;
  }

  public void unsetComp_param() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COMP_PARAM_ISSET_ID);
  }

  /** Returns true if field comp_param is set (has been assigned a value) and false otherwise */
  public boolean isSetComp_param() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COMP_PARAM_ISSET_ID);
  }

  public void setComp_paramIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COMP_PARAM_ISSET_ID, value);
  }

  public int getSize() {
    return this.size;
  }

  public TTypeInfo setSize(int size) {
    this.size = size;
    setSizeIsSet(true);
    return this;
  }

  public void unsetSize() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SIZE_ISSET_ID);
  }

  /** Returns true if field size is set (has been assigned a value) and false otherwise */
  public boolean isSetSize() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SIZE_ISSET_ID);
  }

  public void setSizeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SIZE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((TDatumType)value);
      }
      break;

    case ENCODING:
      if (value == null) {
        unsetEncoding();
      } else {
        setEncoding((TEncodingType)value);
      }
      break;

    case NULLABLE:
      if (value == null) {
        unsetNullable();
      } else {
        setNullable((java.lang.Boolean)value);
      }
      break;

    case IS_ARRAY:
      if (value == null) {
        unsetIs_array();
      } else {
        setIs_array((java.lang.Boolean)value);
      }
      break;

    case PRECISION:
      if (value == null) {
        unsetPrecision();
      } else {
        setPrecision((java.lang.Integer)value);
      }
      break;

    case SCALE:
      if (value == null) {
        unsetScale();
      } else {
        setScale((java.lang.Integer)value);
      }
      break;

    case COMP_PARAM:
      if (value == null) {
        unsetComp_param();
      } else {
        setComp_param((java.lang.Integer)value);
      }
      break;

    case SIZE:
      if (value == null) {
        unsetSize();
      } else {
        setSize((java.lang.Integer)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TYPE:
      return getType();

    case ENCODING:
      return getEncoding();

    case NULLABLE:
      return isNullable();

    case IS_ARRAY:
      return isIs_array();

    case PRECISION:
      return getPrecision();

    case SCALE:
      return getScale();

    case COMP_PARAM:
      return getComp_param();

    case SIZE:
      return getSize();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TYPE:
      return isSetType();
    case ENCODING:
      return isSetEncoding();
    case NULLABLE:
      return isSetNullable();
    case IS_ARRAY:
      return isSetIs_array();
    case PRECISION:
      return isSetPrecision();
    case SCALE:
      return isSetScale();
    case COMP_PARAM:
      return isSetComp_param();
    case SIZE:
      return isSetSize();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TTypeInfo)
      return this.equals((TTypeInfo)that);
    return false;
  }

  public boolean equals(TTypeInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_encoding = true && this.isSetEncoding();
    boolean that_present_encoding = true && that.isSetEncoding();
    if (this_present_encoding || that_present_encoding) {
      if (!(this_present_encoding && that_present_encoding))
        return false;
      if (!this.encoding.equals(that.encoding))
        return false;
    }

    boolean this_present_nullable = true;
    boolean that_present_nullable = true;
    if (this_present_nullable || that_present_nullable) {
      if (!(this_present_nullable && that_present_nullable))
        return false;
      if (this.nullable != that.nullable)
        return false;
    }

    boolean this_present_is_array = true;
    boolean that_present_is_array = true;
    if (this_present_is_array || that_present_is_array) {
      if (!(this_present_is_array && that_present_is_array))
        return false;
      if (this.is_array != that.is_array)
        return false;
    }

    boolean this_present_precision = true;
    boolean that_present_precision = true;
    if (this_present_precision || that_present_precision) {
      if (!(this_present_precision && that_present_precision))
        return false;
      if (this.precision != that.precision)
        return false;
    }

    boolean this_present_scale = true;
    boolean that_present_scale = true;
    if (this_present_scale || that_present_scale) {
      if (!(this_present_scale && that_present_scale))
        return false;
      if (this.scale != that.scale)
        return false;
    }

    boolean this_present_comp_param = true;
    boolean that_present_comp_param = true;
    if (this_present_comp_param || that_present_comp_param) {
      if (!(this_present_comp_param && that_present_comp_param))
        return false;
      if (this.comp_param != that.comp_param)
        return false;
    }

    boolean this_present_size = true && this.isSetSize();
    boolean that_present_size = true && that.isSetSize();
    if (this_present_size || that_present_size) {
      if (!(this_present_size && that_present_size))
        return false;
      if (this.size != that.size)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetType()) ? 131071 : 524287);
    if (isSetType())
      hashCode = hashCode * 8191 + type.getValue();

    hashCode = hashCode * 8191 + ((isSetEncoding()) ? 131071 : 524287);
    if (isSetEncoding())
      hashCode = hashCode * 8191 + encoding.getValue();

    hashCode = hashCode * 8191 + ((nullable) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((is_array) ? 131071 : 524287);

    hashCode = hashCode * 8191 + precision;

    hashCode = hashCode * 8191 + scale;

    hashCode = hashCode * 8191 + comp_param;

    hashCode = hashCode * 8191 + ((isSetSize()) ? 131071 : 524287);
    if (isSetSize())
      hashCode = hashCode * 8191 + size;

    return hashCode;
  }

  @Override
  public int compareTo(TTypeInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetEncoding()).compareTo(other.isSetEncoding());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEncoding()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.encoding, other.encoding);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetNullable()).compareTo(other.isSetNullable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNullable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nullable, other.nullable);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIs_array()).compareTo(other.isSetIs_array());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_array()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_array, other.is_array);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPrecision()).compareTo(other.isSetPrecision());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrecision()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.precision, other.precision);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetScale()).compareTo(other.isSetScale());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScale()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.scale, other.scale);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetComp_param()).compareTo(other.isSetComp_param());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetComp_param()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.comp_param, other.comp_param);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSize()).compareTo(other.isSetSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.size, other.size);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TTypeInfo(");
    boolean first = true;

    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("encoding:");
    if (this.encoding == null) {
      sb.append("null");
    } else {
      sb.append(this.encoding);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("nullable:");
    sb.append(this.nullable);
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_array:");
    sb.append(this.is_array);
    first = false;
    if (!first) sb.append(", ");
    sb.append("precision:");
    sb.append(this.precision);
    first = false;
    if (!first) sb.append(", ");
    sb.append("scale:");
    sb.append(this.scale);
    first = false;
    if (!first) sb.append(", ");
    sb.append("comp_param:");
    sb.append(this.comp_param);
    first = false;
    if (isSetSize()) {
      if (!first) sb.append(", ");
      sb.append("size:");
      sb.append(this.size);
      first = false;
    }
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

  private static class TTypeInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTypeInfoStandardScheme getScheme() {
      return new TTypeInfoStandardScheme();
    }
  }

  private static class TTypeInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<TTypeInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TTypeInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = com.omnisci.thrift.server.TDatumType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ENCODING
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.encoding = com.omnisci.thrift.server.TEncodingType.findByValue(iprot.readI32());
              struct.setEncodingIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NULLABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.nullable = iprot.readBool();
              struct.setNullableIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IS_ARRAY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_array = iprot.readBool();
              struct.setIs_arrayIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PRECISION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.precision = iprot.readI32();
              struct.setPrecisionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SCALE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.scale = iprot.readI32();
              struct.setScaleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // COMP_PARAM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.comp_param = iprot.readI32();
              struct.setComp_paramIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.size = iprot.readI32();
              struct.setSizeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TTypeInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(NULLABLE_FIELD_DESC);
      oprot.writeBool(struct.nullable);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_ARRAY_FIELD_DESC);
      oprot.writeBool(struct.is_array);
      oprot.writeFieldEnd();
      if (struct.encoding != null) {
        oprot.writeFieldBegin(ENCODING_FIELD_DESC);
        oprot.writeI32(struct.encoding.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PRECISION_FIELD_DESC);
      oprot.writeI32(struct.precision);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SCALE_FIELD_DESC);
      oprot.writeI32(struct.scale);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(COMP_PARAM_FIELD_DESC);
      oprot.writeI32(struct.comp_param);
      oprot.writeFieldEnd();
      if (struct.isSetSize()) {
        oprot.writeFieldBegin(SIZE_FIELD_DESC);
        oprot.writeI32(struct.size);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TTypeInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTypeInfoTupleScheme getScheme() {
      return new TTypeInfoTupleScheme();
    }
  }

  private static class TTypeInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<TTypeInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TTypeInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetType()) {
        optionals.set(0);
      }
      if (struct.isSetEncoding()) {
        optionals.set(1);
      }
      if (struct.isSetNullable()) {
        optionals.set(2);
      }
      if (struct.isSetIs_array()) {
        optionals.set(3);
      }
      if (struct.isSetPrecision()) {
        optionals.set(4);
      }
      if (struct.isSetScale()) {
        optionals.set(5);
      }
      if (struct.isSetComp_param()) {
        optionals.set(6);
      }
      if (struct.isSetSize()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetEncoding()) {
        oprot.writeI32(struct.encoding.getValue());
      }
      if (struct.isSetNullable()) {
        oprot.writeBool(struct.nullable);
      }
      if (struct.isSetIs_array()) {
        oprot.writeBool(struct.is_array);
      }
      if (struct.isSetPrecision()) {
        oprot.writeI32(struct.precision);
      }
      if (struct.isSetScale()) {
        oprot.writeI32(struct.scale);
      }
      if (struct.isSetComp_param()) {
        oprot.writeI32(struct.comp_param);
      }
      if (struct.isSetSize()) {
        oprot.writeI32(struct.size);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TTypeInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.type = com.omnisci.thrift.server.TDatumType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.encoding = com.omnisci.thrift.server.TEncodingType.findByValue(iprot.readI32());
        struct.setEncodingIsSet(true);
      }
      if (incoming.get(2)) {
        struct.nullable = iprot.readBool();
        struct.setNullableIsSet(true);
      }
      if (incoming.get(3)) {
        struct.is_array = iprot.readBool();
        struct.setIs_arrayIsSet(true);
      }
      if (incoming.get(4)) {
        struct.precision = iprot.readI32();
        struct.setPrecisionIsSet(true);
      }
      if (incoming.get(5)) {
        struct.scale = iprot.readI32();
        struct.setScaleIsSet(true);
      }
      if (incoming.get(6)) {
        struct.comp_param = iprot.readI32();
        struct.setComp_paramIsSet(true);
      }
      if (incoming.get(7)) {
        struct.size = iprot.readI32();
        struct.setSizeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

