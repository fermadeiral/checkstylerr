/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TColumnType implements org.apache.thrift.TBase<TColumnType, TColumnType._Fields>, java.io.Serializable, Cloneable, Comparable<TColumnType> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TColumnType");

  private static final org.apache.thrift.protocol.TField COL_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("col_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField COL_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("col_type", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField IS_RESERVED_KEYWORD_FIELD_DESC = new org.apache.thrift.protocol.TField("is_reserved_keyword", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField SRC_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("src_name", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField IS_SYSTEM_FIELD_DESC = new org.apache.thrift.protocol.TField("is_system", org.apache.thrift.protocol.TType.BOOL, (short)5);
  private static final org.apache.thrift.protocol.TField IS_PHYSICAL_FIELD_DESC = new org.apache.thrift.protocol.TField("is_physical", org.apache.thrift.protocol.TType.BOOL, (short)6);
  private static final org.apache.thrift.protocol.TField COL_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("col_id", org.apache.thrift.protocol.TType.I64, (short)7);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TColumnTypeStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TColumnTypeTupleSchemeFactory();

  public java.lang.String col_name; // required
  public com.omnisci.thrift.server.TTypeInfo col_type; // required
  public boolean is_reserved_keyword; // required
  public java.lang.String src_name; // required
  public boolean is_system; // required
  public boolean is_physical; // required
  public long col_id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COL_NAME((short)1, "col_name"),
    COL_TYPE((short)2, "col_type"),
    IS_RESERVED_KEYWORD((short)3, "is_reserved_keyword"),
    SRC_NAME((short)4, "src_name"),
    IS_SYSTEM((short)5, "is_system"),
    IS_PHYSICAL((short)6, "is_physical"),
    COL_ID((short)7, "col_id");

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
        case 1: // COL_NAME
          return COL_NAME;
        case 2: // COL_TYPE
          return COL_TYPE;
        case 3: // IS_RESERVED_KEYWORD
          return IS_RESERVED_KEYWORD;
        case 4: // SRC_NAME
          return SRC_NAME;
        case 5: // IS_SYSTEM
          return IS_SYSTEM;
        case 6: // IS_PHYSICAL
          return IS_PHYSICAL;
        case 7: // COL_ID
          return COL_ID;
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
  private static final int __IS_RESERVED_KEYWORD_ISSET_ID = 0;
  private static final int __IS_SYSTEM_ISSET_ID = 1;
  private static final int __IS_PHYSICAL_ISSET_ID = 2;
  private static final int __COL_ID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COL_NAME, new org.apache.thrift.meta_data.FieldMetaData("col_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COL_TYPE, new org.apache.thrift.meta_data.FieldMetaData("col_type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.omnisci.thrift.server.TTypeInfo.class)));
    tmpMap.put(_Fields.IS_RESERVED_KEYWORD, new org.apache.thrift.meta_data.FieldMetaData("is_reserved_keyword", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.SRC_NAME, new org.apache.thrift.meta_data.FieldMetaData("src_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_SYSTEM, new org.apache.thrift.meta_data.FieldMetaData("is_system", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.IS_PHYSICAL, new org.apache.thrift.meta_data.FieldMetaData("is_physical", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.COL_ID, new org.apache.thrift.meta_data.FieldMetaData("col_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TColumnType.class, metaDataMap);
  }

  public TColumnType() {
  }

  public TColumnType(
    java.lang.String col_name,
    com.omnisci.thrift.server.TTypeInfo col_type,
    boolean is_reserved_keyword,
    java.lang.String src_name,
    boolean is_system,
    boolean is_physical,
    long col_id)
  {
    this();
    this.col_name = col_name;
    this.col_type = col_type;
    this.is_reserved_keyword = is_reserved_keyword;
    setIs_reserved_keywordIsSet(true);
    this.src_name = src_name;
    this.is_system = is_system;
    setIs_systemIsSet(true);
    this.is_physical = is_physical;
    setIs_physicalIsSet(true);
    this.col_id = col_id;
    setCol_idIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TColumnType(TColumnType other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetCol_name()) {
      this.col_name = other.col_name;
    }
    if (other.isSetCol_type()) {
      this.col_type = new com.omnisci.thrift.server.TTypeInfo(other.col_type);
    }
    this.is_reserved_keyword = other.is_reserved_keyword;
    if (other.isSetSrc_name()) {
      this.src_name = other.src_name;
    }
    this.is_system = other.is_system;
    this.is_physical = other.is_physical;
    this.col_id = other.col_id;
  }

  public TColumnType deepCopy() {
    return new TColumnType(this);
  }

  @Override
  public void clear() {
    this.col_name = null;
    this.col_type = null;
    setIs_reserved_keywordIsSet(false);
    this.is_reserved_keyword = false;
    this.src_name = null;
    setIs_systemIsSet(false);
    this.is_system = false;
    setIs_physicalIsSet(false);
    this.is_physical = false;
    setCol_idIsSet(false);
    this.col_id = 0;
  }

  public java.lang.String getCol_name() {
    return this.col_name;
  }

  public TColumnType setCol_name(java.lang.String col_name) {
    this.col_name = col_name;
    return this;
  }

  public void unsetCol_name() {
    this.col_name = null;
  }

  /** Returns true if field col_name is set (has been assigned a value) and false otherwise */
  public boolean isSetCol_name() {
    return this.col_name != null;
  }

  public void setCol_nameIsSet(boolean value) {
    if (!value) {
      this.col_name = null;
    }
  }

  public com.omnisci.thrift.server.TTypeInfo getCol_type() {
    return this.col_type;
  }

  public TColumnType setCol_type(com.omnisci.thrift.server.TTypeInfo col_type) {
    this.col_type = col_type;
    return this;
  }

  public void unsetCol_type() {
    this.col_type = null;
  }

  /** Returns true if field col_type is set (has been assigned a value) and false otherwise */
  public boolean isSetCol_type() {
    return this.col_type != null;
  }

  public void setCol_typeIsSet(boolean value) {
    if (!value) {
      this.col_type = null;
    }
  }

  public boolean isIs_reserved_keyword() {
    return this.is_reserved_keyword;
  }

  public TColumnType setIs_reserved_keyword(boolean is_reserved_keyword) {
    this.is_reserved_keyword = is_reserved_keyword;
    setIs_reserved_keywordIsSet(true);
    return this;
  }

  public void unsetIs_reserved_keyword() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IS_RESERVED_KEYWORD_ISSET_ID);
  }

  /** Returns true if field is_reserved_keyword is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_reserved_keyword() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IS_RESERVED_KEYWORD_ISSET_ID);
  }

  public void setIs_reserved_keywordIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IS_RESERVED_KEYWORD_ISSET_ID, value);
  }

  public java.lang.String getSrc_name() {
    return this.src_name;
  }

  public TColumnType setSrc_name(java.lang.String src_name) {
    this.src_name = src_name;
    return this;
  }

  public void unsetSrc_name() {
    this.src_name = null;
  }

  /** Returns true if field src_name is set (has been assigned a value) and false otherwise */
  public boolean isSetSrc_name() {
    return this.src_name != null;
  }

  public void setSrc_nameIsSet(boolean value) {
    if (!value) {
      this.src_name = null;
    }
  }

  public boolean isIs_system() {
    return this.is_system;
  }

  public TColumnType setIs_system(boolean is_system) {
    this.is_system = is_system;
    setIs_systemIsSet(true);
    return this;
  }

  public void unsetIs_system() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IS_SYSTEM_ISSET_ID);
  }

  /** Returns true if field is_system is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_system() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IS_SYSTEM_ISSET_ID);
  }

  public void setIs_systemIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IS_SYSTEM_ISSET_ID, value);
  }

  public boolean isIs_physical() {
    return this.is_physical;
  }

  public TColumnType setIs_physical(boolean is_physical) {
    this.is_physical = is_physical;
    setIs_physicalIsSet(true);
    return this;
  }

  public void unsetIs_physical() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IS_PHYSICAL_ISSET_ID);
  }

  /** Returns true if field is_physical is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_physical() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IS_PHYSICAL_ISSET_ID);
  }

  public void setIs_physicalIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IS_PHYSICAL_ISSET_ID, value);
  }

  public long getCol_id() {
    return this.col_id;
  }

  public TColumnType setCol_id(long col_id) {
    this.col_id = col_id;
    setCol_idIsSet(true);
    return this;
  }

  public void unsetCol_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __COL_ID_ISSET_ID);
  }

  /** Returns true if field col_id is set (has been assigned a value) and false otherwise */
  public boolean isSetCol_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __COL_ID_ISSET_ID);
  }

  public void setCol_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __COL_ID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case COL_NAME:
      if (value == null) {
        unsetCol_name();
      } else {
        setCol_name((java.lang.String)value);
      }
      break;

    case COL_TYPE:
      if (value == null) {
        unsetCol_type();
      } else {
        setCol_type((com.omnisci.thrift.server.TTypeInfo)value);
      }
      break;

    case IS_RESERVED_KEYWORD:
      if (value == null) {
        unsetIs_reserved_keyword();
      } else {
        setIs_reserved_keyword((java.lang.Boolean)value);
      }
      break;

    case SRC_NAME:
      if (value == null) {
        unsetSrc_name();
      } else {
        setSrc_name((java.lang.String)value);
      }
      break;

    case IS_SYSTEM:
      if (value == null) {
        unsetIs_system();
      } else {
        setIs_system((java.lang.Boolean)value);
      }
      break;

    case IS_PHYSICAL:
      if (value == null) {
        unsetIs_physical();
      } else {
        setIs_physical((java.lang.Boolean)value);
      }
      break;

    case COL_ID:
      if (value == null) {
        unsetCol_id();
      } else {
        setCol_id((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case COL_NAME:
      return getCol_name();

    case COL_TYPE:
      return getCol_type();

    case IS_RESERVED_KEYWORD:
      return isIs_reserved_keyword();

    case SRC_NAME:
      return getSrc_name();

    case IS_SYSTEM:
      return isIs_system();

    case IS_PHYSICAL:
      return isIs_physical();

    case COL_ID:
      return getCol_id();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case COL_NAME:
      return isSetCol_name();
    case COL_TYPE:
      return isSetCol_type();
    case IS_RESERVED_KEYWORD:
      return isSetIs_reserved_keyword();
    case SRC_NAME:
      return isSetSrc_name();
    case IS_SYSTEM:
      return isSetIs_system();
    case IS_PHYSICAL:
      return isSetIs_physical();
    case COL_ID:
      return isSetCol_id();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TColumnType)
      return this.equals((TColumnType)that);
    return false;
  }

  public boolean equals(TColumnType that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_col_name = true && this.isSetCol_name();
    boolean that_present_col_name = true && that.isSetCol_name();
    if (this_present_col_name || that_present_col_name) {
      if (!(this_present_col_name && that_present_col_name))
        return false;
      if (!this.col_name.equals(that.col_name))
        return false;
    }

    boolean this_present_col_type = true && this.isSetCol_type();
    boolean that_present_col_type = true && that.isSetCol_type();
    if (this_present_col_type || that_present_col_type) {
      if (!(this_present_col_type && that_present_col_type))
        return false;
      if (!this.col_type.equals(that.col_type))
        return false;
    }

    boolean this_present_is_reserved_keyword = true;
    boolean that_present_is_reserved_keyword = true;
    if (this_present_is_reserved_keyword || that_present_is_reserved_keyword) {
      if (!(this_present_is_reserved_keyword && that_present_is_reserved_keyword))
        return false;
      if (this.is_reserved_keyword != that.is_reserved_keyword)
        return false;
    }

    boolean this_present_src_name = true && this.isSetSrc_name();
    boolean that_present_src_name = true && that.isSetSrc_name();
    if (this_present_src_name || that_present_src_name) {
      if (!(this_present_src_name && that_present_src_name))
        return false;
      if (!this.src_name.equals(that.src_name))
        return false;
    }

    boolean this_present_is_system = true;
    boolean that_present_is_system = true;
    if (this_present_is_system || that_present_is_system) {
      if (!(this_present_is_system && that_present_is_system))
        return false;
      if (this.is_system != that.is_system)
        return false;
    }

    boolean this_present_is_physical = true;
    boolean that_present_is_physical = true;
    if (this_present_is_physical || that_present_is_physical) {
      if (!(this_present_is_physical && that_present_is_physical))
        return false;
      if (this.is_physical != that.is_physical)
        return false;
    }

    boolean this_present_col_id = true;
    boolean that_present_col_id = true;
    if (this_present_col_id || that_present_col_id) {
      if (!(this_present_col_id && that_present_col_id))
        return false;
      if (this.col_id != that.col_id)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetCol_name()) ? 131071 : 524287);
    if (isSetCol_name())
      hashCode = hashCode * 8191 + col_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetCol_type()) ? 131071 : 524287);
    if (isSetCol_type())
      hashCode = hashCode * 8191 + col_type.hashCode();

    hashCode = hashCode * 8191 + ((is_reserved_keyword) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetSrc_name()) ? 131071 : 524287);
    if (isSetSrc_name())
      hashCode = hashCode * 8191 + src_name.hashCode();

    hashCode = hashCode * 8191 + ((is_system) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((is_physical) ? 131071 : 524287);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(col_id);

    return hashCode;
  }

  @Override
  public int compareTo(TColumnType other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetCol_name()).compareTo(other.isSetCol_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCol_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.col_name, other.col_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCol_type()).compareTo(other.isSetCol_type());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCol_type()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.col_type, other.col_type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIs_reserved_keyword()).compareTo(other.isSetIs_reserved_keyword());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_reserved_keyword()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_reserved_keyword, other.is_reserved_keyword);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSrc_name()).compareTo(other.isSetSrc_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSrc_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.src_name, other.src_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIs_system()).compareTo(other.isSetIs_system());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_system()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_system, other.is_system);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIs_physical()).compareTo(other.isSetIs_physical());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_physical()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_physical, other.is_physical);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCol_id()).compareTo(other.isSetCol_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCol_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.col_id, other.col_id);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TColumnType(");
    boolean first = true;

    sb.append("col_name:");
    if (this.col_name == null) {
      sb.append("null");
    } else {
      sb.append(this.col_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("col_type:");
    if (this.col_type == null) {
      sb.append("null");
    } else {
      sb.append(this.col_type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_reserved_keyword:");
    sb.append(this.is_reserved_keyword);
    first = false;
    if (!first) sb.append(", ");
    sb.append("src_name:");
    if (this.src_name == null) {
      sb.append("null");
    } else {
      sb.append(this.src_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_system:");
    sb.append(this.is_system);
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_physical:");
    sb.append(this.is_physical);
    first = false;
    if (!first) sb.append(", ");
    sb.append("col_id:");
    sb.append(this.col_id);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (col_type != null) {
      col_type.validate();
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

  private static class TColumnTypeStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TColumnTypeStandardScheme getScheme() {
      return new TColumnTypeStandardScheme();
    }
  }

  private static class TColumnTypeStandardScheme extends org.apache.thrift.scheme.StandardScheme<TColumnType> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TColumnType struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COL_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.col_name = iprot.readString();
              struct.setCol_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COL_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.col_type = new com.omnisci.thrift.server.TTypeInfo();
              struct.col_type.read(iprot);
              struct.setCol_typeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IS_RESERVED_KEYWORD
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_reserved_keyword = iprot.readBool();
              struct.setIs_reserved_keywordIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SRC_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.src_name = iprot.readString();
              struct.setSrc_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // IS_SYSTEM
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_system = iprot.readBool();
              struct.setIs_systemIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // IS_PHYSICAL
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_physical = iprot.readBool();
              struct.setIs_physicalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // COL_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.col_id = iprot.readI64();
              struct.setCol_idIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TColumnType struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.col_name != null) {
        oprot.writeFieldBegin(COL_NAME_FIELD_DESC);
        oprot.writeString(struct.col_name);
        oprot.writeFieldEnd();
      }
      if (struct.col_type != null) {
        oprot.writeFieldBegin(COL_TYPE_FIELD_DESC);
        struct.col_type.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_RESERVED_KEYWORD_FIELD_DESC);
      oprot.writeBool(struct.is_reserved_keyword);
      oprot.writeFieldEnd();
      if (struct.src_name != null) {
        oprot.writeFieldBegin(SRC_NAME_FIELD_DESC);
        oprot.writeString(struct.src_name);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_SYSTEM_FIELD_DESC);
      oprot.writeBool(struct.is_system);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_PHYSICAL_FIELD_DESC);
      oprot.writeBool(struct.is_physical);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(COL_ID_FIELD_DESC);
      oprot.writeI64(struct.col_id);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TColumnTypeTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TColumnTypeTupleScheme getScheme() {
      return new TColumnTypeTupleScheme();
    }
  }

  private static class TColumnTypeTupleScheme extends org.apache.thrift.scheme.TupleScheme<TColumnType> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TColumnType struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetCol_name()) {
        optionals.set(0);
      }
      if (struct.isSetCol_type()) {
        optionals.set(1);
      }
      if (struct.isSetIs_reserved_keyword()) {
        optionals.set(2);
      }
      if (struct.isSetSrc_name()) {
        optionals.set(3);
      }
      if (struct.isSetIs_system()) {
        optionals.set(4);
      }
      if (struct.isSetIs_physical()) {
        optionals.set(5);
      }
      if (struct.isSetCol_id()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetCol_name()) {
        oprot.writeString(struct.col_name);
      }
      if (struct.isSetCol_type()) {
        struct.col_type.write(oprot);
      }
      if (struct.isSetIs_reserved_keyword()) {
        oprot.writeBool(struct.is_reserved_keyword);
      }
      if (struct.isSetSrc_name()) {
        oprot.writeString(struct.src_name);
      }
      if (struct.isSetIs_system()) {
        oprot.writeBool(struct.is_system);
      }
      if (struct.isSetIs_physical()) {
        oprot.writeBool(struct.is_physical);
      }
      if (struct.isSetCol_id()) {
        oprot.writeI64(struct.col_id);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TColumnType struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.col_name = iprot.readString();
        struct.setCol_nameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.col_type = new com.omnisci.thrift.server.TTypeInfo();
        struct.col_type.read(iprot);
        struct.setCol_typeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.is_reserved_keyword = iprot.readBool();
        struct.setIs_reserved_keywordIsSet(true);
      }
      if (incoming.get(3)) {
        struct.src_name = iprot.readString();
        struct.setSrc_nameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.is_system = iprot.readBool();
        struct.setIs_systemIsSet(true);
      }
      if (incoming.get(5)) {
        struct.is_physical = iprot.readBool();
        struct.setIs_physicalIsSet(true);
      }
      if (incoming.get(6)) {
        struct.col_id = iprot.readI64();
        struct.setCol_idIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

