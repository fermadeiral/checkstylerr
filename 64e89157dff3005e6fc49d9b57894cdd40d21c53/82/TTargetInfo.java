/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TTargetInfo implements org.apache.thrift.TBase<TTargetInfo, TTargetInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TTargetInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TTargetInfo");

  private static final org.apache.thrift.protocol.TField IS_AGG_FIELD_DESC = new org.apache.thrift.protocol.TField("is_agg", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField KIND_FIELD_DESC = new org.apache.thrift.protocol.TField("kind", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField ARG_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("arg_type", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField SKIP_NULLS_FIELD_DESC = new org.apache.thrift.protocol.TField("skip_nulls", org.apache.thrift.protocol.TType.BOOL, (short)5);
  private static final org.apache.thrift.protocol.TField IS_DISTINCT_FIELD_DESC = new org.apache.thrift.protocol.TField("is_distinct", org.apache.thrift.protocol.TType.BOOL, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TTargetInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TTargetInfoTupleSchemeFactory();

  public boolean is_agg; // required
  /**
   * 
   * @see TAggKind
   */
  public TAggKind kind; // required
  public com.omnisci.thrift.server.TTypeInfo type; // required
  public com.omnisci.thrift.server.TTypeInfo arg_type; // required
  public boolean skip_nulls; // required
  public boolean is_distinct; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IS_AGG((short)1, "is_agg"),
    /**
     * 
     * @see TAggKind
     */
    KIND((short)2, "kind"),
    TYPE((short)3, "type"),
    ARG_TYPE((short)4, "arg_type"),
    SKIP_NULLS((short)5, "skip_nulls"),
    IS_DISTINCT((short)6, "is_distinct");

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
        case 1: // IS_AGG
          return IS_AGG;
        case 2: // KIND
          return KIND;
        case 3: // TYPE
          return TYPE;
        case 4: // ARG_TYPE
          return ARG_TYPE;
        case 5: // SKIP_NULLS
          return SKIP_NULLS;
        case 6: // IS_DISTINCT
          return IS_DISTINCT;
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
  private static final int __IS_AGG_ISSET_ID = 0;
  private static final int __SKIP_NULLS_ISSET_ID = 1;
  private static final int __IS_DISTINCT_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IS_AGG, new org.apache.thrift.meta_data.FieldMetaData("is_agg", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.KIND, new org.apache.thrift.meta_data.FieldMetaData("kind", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TAggKind.class)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.omnisci.thrift.server.TTypeInfo.class)));
    tmpMap.put(_Fields.ARG_TYPE, new org.apache.thrift.meta_data.FieldMetaData("arg_type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.omnisci.thrift.server.TTypeInfo.class)));
    tmpMap.put(_Fields.SKIP_NULLS, new org.apache.thrift.meta_data.FieldMetaData("skip_nulls", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.IS_DISTINCT, new org.apache.thrift.meta_data.FieldMetaData("is_distinct", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TTargetInfo.class, metaDataMap);
  }

  public TTargetInfo() {
  }

  public TTargetInfo(
    boolean is_agg,
    TAggKind kind,
    com.omnisci.thrift.server.TTypeInfo type,
    com.omnisci.thrift.server.TTypeInfo arg_type,
    boolean skip_nulls,
    boolean is_distinct)
  {
    this();
    this.is_agg = is_agg;
    setIs_aggIsSet(true);
    this.kind = kind;
    this.type = type;
    this.arg_type = arg_type;
    this.skip_nulls = skip_nulls;
    setSkip_nullsIsSet(true);
    this.is_distinct = is_distinct;
    setIs_distinctIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TTargetInfo(TTargetInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.is_agg = other.is_agg;
    if (other.isSetKind()) {
      this.kind = other.kind;
    }
    if (other.isSetType()) {
      this.type = new com.omnisci.thrift.server.TTypeInfo(other.type);
    }
    if (other.isSetArg_type()) {
      this.arg_type = new com.omnisci.thrift.server.TTypeInfo(other.arg_type);
    }
    this.skip_nulls = other.skip_nulls;
    this.is_distinct = other.is_distinct;
  }

  public TTargetInfo deepCopy() {
    return new TTargetInfo(this);
  }

  @Override
  public void clear() {
    setIs_aggIsSet(false);
    this.is_agg = false;
    this.kind = null;
    this.type = null;
    this.arg_type = null;
    setSkip_nullsIsSet(false);
    this.skip_nulls = false;
    setIs_distinctIsSet(false);
    this.is_distinct = false;
  }

  public boolean isIs_agg() {
    return this.is_agg;
  }

  public TTargetInfo setIs_agg(boolean is_agg) {
    this.is_agg = is_agg;
    setIs_aggIsSet(true);
    return this;
  }

  public void unsetIs_agg() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IS_AGG_ISSET_ID);
  }

  /** Returns true if field is_agg is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_agg() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IS_AGG_ISSET_ID);
  }

  public void setIs_aggIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IS_AGG_ISSET_ID, value);
  }

  /**
   * 
   * @see TAggKind
   */
  public TAggKind getKind() {
    return this.kind;
  }

  /**
   * 
   * @see TAggKind
   */
  public TTargetInfo setKind(TAggKind kind) {
    this.kind = kind;
    return this;
  }

  public void unsetKind() {
    this.kind = null;
  }

  /** Returns true if field kind is set (has been assigned a value) and false otherwise */
  public boolean isSetKind() {
    return this.kind != null;
  }

  public void setKindIsSet(boolean value) {
    if (!value) {
      this.kind = null;
    }
  }

  public com.omnisci.thrift.server.TTypeInfo getType() {
    return this.type;
  }

  public TTargetInfo setType(com.omnisci.thrift.server.TTypeInfo type) {
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

  public com.omnisci.thrift.server.TTypeInfo getArg_type() {
    return this.arg_type;
  }

  public TTargetInfo setArg_type(com.omnisci.thrift.server.TTypeInfo arg_type) {
    this.arg_type = arg_type;
    return this;
  }

  public void unsetArg_type() {
    this.arg_type = null;
  }

  /** Returns true if field arg_type is set (has been assigned a value) and false otherwise */
  public boolean isSetArg_type() {
    return this.arg_type != null;
  }

  public void setArg_typeIsSet(boolean value) {
    if (!value) {
      this.arg_type = null;
    }
  }

  public boolean isSkip_nulls() {
    return this.skip_nulls;
  }

  public TTargetInfo setSkip_nulls(boolean skip_nulls) {
    this.skip_nulls = skip_nulls;
    setSkip_nullsIsSet(true);
    return this;
  }

  public void unsetSkip_nulls() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SKIP_NULLS_ISSET_ID);
  }

  /** Returns true if field skip_nulls is set (has been assigned a value) and false otherwise */
  public boolean isSetSkip_nulls() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SKIP_NULLS_ISSET_ID);
  }

  public void setSkip_nullsIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SKIP_NULLS_ISSET_ID, value);
  }

  public boolean isIs_distinct() {
    return this.is_distinct;
  }

  public TTargetInfo setIs_distinct(boolean is_distinct) {
    this.is_distinct = is_distinct;
    setIs_distinctIsSet(true);
    return this;
  }

  public void unsetIs_distinct() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IS_DISTINCT_ISSET_ID);
  }

  /** Returns true if field is_distinct is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_distinct() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IS_DISTINCT_ISSET_ID);
  }

  public void setIs_distinctIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IS_DISTINCT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case IS_AGG:
      if (value == null) {
        unsetIs_agg();
      } else {
        setIs_agg((java.lang.Boolean)value);
      }
      break;

    case KIND:
      if (value == null) {
        unsetKind();
      } else {
        setKind((TAggKind)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((com.omnisci.thrift.server.TTypeInfo)value);
      }
      break;

    case ARG_TYPE:
      if (value == null) {
        unsetArg_type();
      } else {
        setArg_type((com.omnisci.thrift.server.TTypeInfo)value);
      }
      break;

    case SKIP_NULLS:
      if (value == null) {
        unsetSkip_nulls();
      } else {
        setSkip_nulls((java.lang.Boolean)value);
      }
      break;

    case IS_DISTINCT:
      if (value == null) {
        unsetIs_distinct();
      } else {
        setIs_distinct((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case IS_AGG:
      return isIs_agg();

    case KIND:
      return getKind();

    case TYPE:
      return getType();

    case ARG_TYPE:
      return getArg_type();

    case SKIP_NULLS:
      return isSkip_nulls();

    case IS_DISTINCT:
      return isIs_distinct();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case IS_AGG:
      return isSetIs_agg();
    case KIND:
      return isSetKind();
    case TYPE:
      return isSetType();
    case ARG_TYPE:
      return isSetArg_type();
    case SKIP_NULLS:
      return isSetSkip_nulls();
    case IS_DISTINCT:
      return isSetIs_distinct();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TTargetInfo)
      return this.equals((TTargetInfo)that);
    return false;
  }

  public boolean equals(TTargetInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_is_agg = true;
    boolean that_present_is_agg = true;
    if (this_present_is_agg || that_present_is_agg) {
      if (!(this_present_is_agg && that_present_is_agg))
        return false;
      if (this.is_agg != that.is_agg)
        return false;
    }

    boolean this_present_kind = true && this.isSetKind();
    boolean that_present_kind = true && that.isSetKind();
    if (this_present_kind || that_present_kind) {
      if (!(this_present_kind && that_present_kind))
        return false;
      if (!this.kind.equals(that.kind))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_arg_type = true && this.isSetArg_type();
    boolean that_present_arg_type = true && that.isSetArg_type();
    if (this_present_arg_type || that_present_arg_type) {
      if (!(this_present_arg_type && that_present_arg_type))
        return false;
      if (!this.arg_type.equals(that.arg_type))
        return false;
    }

    boolean this_present_skip_nulls = true;
    boolean that_present_skip_nulls = true;
    if (this_present_skip_nulls || that_present_skip_nulls) {
      if (!(this_present_skip_nulls && that_present_skip_nulls))
        return false;
      if (this.skip_nulls != that.skip_nulls)
        return false;
    }

    boolean this_present_is_distinct = true;
    boolean that_present_is_distinct = true;
    if (this_present_is_distinct || that_present_is_distinct) {
      if (!(this_present_is_distinct && that_present_is_distinct))
        return false;
      if (this.is_distinct != that.is_distinct)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((is_agg) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetKind()) ? 131071 : 524287);
    if (isSetKind())
      hashCode = hashCode * 8191 + kind.getValue();

    hashCode = hashCode * 8191 + ((isSetType()) ? 131071 : 524287);
    if (isSetType())
      hashCode = hashCode * 8191 + type.hashCode();

    hashCode = hashCode * 8191 + ((isSetArg_type()) ? 131071 : 524287);
    if (isSetArg_type())
      hashCode = hashCode * 8191 + arg_type.hashCode();

    hashCode = hashCode * 8191 + ((skip_nulls) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((is_distinct) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TTargetInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetIs_agg()).compareTo(other.isSetIs_agg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_agg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_agg, other.is_agg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetKind()).compareTo(other.isSetKind());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKind()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kind, other.kind);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = java.lang.Boolean.valueOf(isSetArg_type()).compareTo(other.isSetArg_type());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArg_type()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg_type, other.arg_type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSkip_nulls()).compareTo(other.isSetSkip_nulls());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSkip_nulls()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.skip_nulls, other.skip_nulls);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIs_distinct()).compareTo(other.isSetIs_distinct());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_distinct()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_distinct, other.is_distinct);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TTargetInfo(");
    boolean first = true;

    sb.append("is_agg:");
    sb.append(this.is_agg);
    first = false;
    if (!first) sb.append(", ");
    sb.append("kind:");
    if (this.kind == null) {
      sb.append("null");
    } else {
      sb.append(this.kind);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("arg_type:");
    if (this.arg_type == null) {
      sb.append("null");
    } else {
      sb.append(this.arg_type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("skip_nulls:");
    sb.append(this.skip_nulls);
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_distinct:");
    sb.append(this.is_distinct);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (type != null) {
      type.validate();
    }
    if (arg_type != null) {
      arg_type.validate();
    }
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

  private static class TTargetInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTargetInfoStandardScheme getScheme() {
      return new TTargetInfoStandardScheme();
    }
  }

  private static class TTargetInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<TTargetInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TTargetInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IS_AGG
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_agg = iprot.readBool();
              struct.setIs_aggIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // KIND
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.kind = com.omnisci.thrift.server.TAggKind.findByValue(iprot.readI32());
              struct.setKindIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.type = new com.omnisci.thrift.server.TTypeInfo();
              struct.type.read(iprot);
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ARG_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.arg_type = new com.omnisci.thrift.server.TTypeInfo();
              struct.arg_type.read(iprot);
              struct.setArg_typeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SKIP_NULLS
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.skip_nulls = iprot.readBool();
              struct.setSkip_nullsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // IS_DISTINCT
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_distinct = iprot.readBool();
              struct.setIs_distinctIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TTargetInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(IS_AGG_FIELD_DESC);
      oprot.writeBool(struct.is_agg);
      oprot.writeFieldEnd();
      if (struct.kind != null) {
        oprot.writeFieldBegin(KIND_FIELD_DESC);
        oprot.writeI32(struct.kind.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        struct.type.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.arg_type != null) {
        oprot.writeFieldBegin(ARG_TYPE_FIELD_DESC);
        struct.arg_type.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SKIP_NULLS_FIELD_DESC);
      oprot.writeBool(struct.skip_nulls);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_DISTINCT_FIELD_DESC);
      oprot.writeBool(struct.is_distinct);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TTargetInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTargetInfoTupleScheme getScheme() {
      return new TTargetInfoTupleScheme();
    }
  }

  private static class TTargetInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<TTargetInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TTargetInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetIs_agg()) {
        optionals.set(0);
      }
      if (struct.isSetKind()) {
        optionals.set(1);
      }
      if (struct.isSetType()) {
        optionals.set(2);
      }
      if (struct.isSetArg_type()) {
        optionals.set(3);
      }
      if (struct.isSetSkip_nulls()) {
        optionals.set(4);
      }
      if (struct.isSetIs_distinct()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetIs_agg()) {
        oprot.writeBool(struct.is_agg);
      }
      if (struct.isSetKind()) {
        oprot.writeI32(struct.kind.getValue());
      }
      if (struct.isSetType()) {
        struct.type.write(oprot);
      }
      if (struct.isSetArg_type()) {
        struct.arg_type.write(oprot);
      }
      if (struct.isSetSkip_nulls()) {
        oprot.writeBool(struct.skip_nulls);
      }
      if (struct.isSetIs_distinct()) {
        oprot.writeBool(struct.is_distinct);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TTargetInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.is_agg = iprot.readBool();
        struct.setIs_aggIsSet(true);
      }
      if (incoming.get(1)) {
        struct.kind = com.omnisci.thrift.server.TAggKind.findByValue(iprot.readI32());
        struct.setKindIsSet(true);
      }
      if (incoming.get(2)) {
        struct.type = new com.omnisci.thrift.server.TTypeInfo();
        struct.type.read(iprot);
        struct.setTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.arg_type = new com.omnisci.thrift.server.TTypeInfo();
        struct.arg_type.read(iprot);
        struct.setArg_typeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.skip_nulls = iprot.readBool();
        struct.setSkip_nullsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.is_distinct = iprot.readBool();
        struct.setIs_distinctIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

