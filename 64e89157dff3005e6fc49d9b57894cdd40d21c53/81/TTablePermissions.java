/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TTablePermissions implements org.apache.thrift.TBase<TTablePermissions, TTablePermissions._Fields>, java.io.Serializable, Cloneable, Comparable<TTablePermissions> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TTablePermissions");

  private static final org.apache.thrift.protocol.TField CREATE__FIELD_DESC = new org.apache.thrift.protocol.TField("create_", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField DROP__FIELD_DESC = new org.apache.thrift.protocol.TField("drop_", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField SELECT__FIELD_DESC = new org.apache.thrift.protocol.TField("select_", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField INSERT__FIELD_DESC = new org.apache.thrift.protocol.TField("insert_", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField UPDATE__FIELD_DESC = new org.apache.thrift.protocol.TField("update_", org.apache.thrift.protocol.TType.BOOL, (short)5);
  private static final org.apache.thrift.protocol.TField DELETE__FIELD_DESC = new org.apache.thrift.protocol.TField("delete_", org.apache.thrift.protocol.TType.BOOL, (short)6);
  private static final org.apache.thrift.protocol.TField TRUNCATE__FIELD_DESC = new org.apache.thrift.protocol.TField("truncate_", org.apache.thrift.protocol.TType.BOOL, (short)7);
  private static final org.apache.thrift.protocol.TField ALTER__FIELD_DESC = new org.apache.thrift.protocol.TField("alter_", org.apache.thrift.protocol.TType.BOOL, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TTablePermissionsStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TTablePermissionsTupleSchemeFactory();

  public boolean create_; // required
  public boolean drop_; // required
  public boolean select_; // required
  public boolean insert_; // required
  public boolean update_; // required
  public boolean delete_; // required
  public boolean truncate_; // required
  public boolean alter_; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CREATE_((short)1, "create_"),
    DROP_((short)2, "drop_"),
    SELECT_((short)3, "select_"),
    INSERT_((short)4, "insert_"),
    UPDATE_((short)5, "update_"),
    DELETE_((short)6, "delete_"),
    TRUNCATE_((short)7, "truncate_"),
    ALTER_((short)8, "alter_");

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
        case 1: // CREATE_
          return CREATE_;
        case 2: // DROP_
          return DROP_;
        case 3: // SELECT_
          return SELECT_;
        case 4: // INSERT_
          return INSERT_;
        case 5: // UPDATE_
          return UPDATE_;
        case 6: // DELETE_
          return DELETE_;
        case 7: // TRUNCATE_
          return TRUNCATE_;
        case 8: // ALTER_
          return ALTER_;
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
  private static final int __CREATE__ISSET_ID = 0;
  private static final int __DROP__ISSET_ID = 1;
  private static final int __SELECT__ISSET_ID = 2;
  private static final int __INSERT__ISSET_ID = 3;
  private static final int __UPDATE__ISSET_ID = 4;
  private static final int __DELETE__ISSET_ID = 5;
  private static final int __TRUNCATE__ISSET_ID = 6;
  private static final int __ALTER__ISSET_ID = 7;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CREATE_, new org.apache.thrift.meta_data.FieldMetaData("create_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.DROP_, new org.apache.thrift.meta_data.FieldMetaData("drop_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.SELECT_, new org.apache.thrift.meta_data.FieldMetaData("select_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.INSERT_, new org.apache.thrift.meta_data.FieldMetaData("insert_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.UPDATE_, new org.apache.thrift.meta_data.FieldMetaData("update_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.DELETE_, new org.apache.thrift.meta_data.FieldMetaData("delete_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.TRUNCATE_, new org.apache.thrift.meta_data.FieldMetaData("truncate_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.ALTER_, new org.apache.thrift.meta_data.FieldMetaData("alter_", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TTablePermissions.class, metaDataMap);
  }

  public TTablePermissions() {
  }

  public TTablePermissions(
    boolean create_,
    boolean drop_,
    boolean select_,
    boolean insert_,
    boolean update_,
    boolean delete_,
    boolean truncate_,
    boolean alter_)
  {
    this();
    this.create_ = create_;
    setCreate_IsSet(true);
    this.drop_ = drop_;
    setDrop_IsSet(true);
    this.select_ = select_;
    setSelect_IsSet(true);
    this.insert_ = insert_;
    setInsert_IsSet(true);
    this.update_ = update_;
    setUpdate_IsSet(true);
    this.delete_ = delete_;
    setDelete_IsSet(true);
    this.truncate_ = truncate_;
    setTruncate_IsSet(true);
    this.alter_ = alter_;
    setAlter_IsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TTablePermissions(TTablePermissions other) {
    __isset_bitfield = other.__isset_bitfield;
    this.create_ = other.create_;
    this.drop_ = other.drop_;
    this.select_ = other.select_;
    this.insert_ = other.insert_;
    this.update_ = other.update_;
    this.delete_ = other.delete_;
    this.truncate_ = other.truncate_;
    this.alter_ = other.alter_;
  }

  public TTablePermissions deepCopy() {
    return new TTablePermissions(this);
  }

  @Override
  public void clear() {
    setCreate_IsSet(false);
    this.create_ = false;
    setDrop_IsSet(false);
    this.drop_ = false;
    setSelect_IsSet(false);
    this.select_ = false;
    setInsert_IsSet(false);
    this.insert_ = false;
    setUpdate_IsSet(false);
    this.update_ = false;
    setDelete_IsSet(false);
    this.delete_ = false;
    setTruncate_IsSet(false);
    this.truncate_ = false;
    setAlter_IsSet(false);
    this.alter_ = false;
  }

  public boolean isCreate_() {
    return this.create_;
  }

  public TTablePermissions setCreate_(boolean create_) {
    this.create_ = create_;
    setCreate_IsSet(true);
    return this;
  }

  public void unsetCreate_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CREATE__ISSET_ID);
  }

  /** Returns true if field create_ is set (has been assigned a value) and false otherwise */
  public boolean isSetCreate_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CREATE__ISSET_ID);
  }

  public void setCreate_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CREATE__ISSET_ID, value);
  }

  public boolean isDrop_() {
    return this.drop_;
  }

  public TTablePermissions setDrop_(boolean drop_) {
    this.drop_ = drop_;
    setDrop_IsSet(true);
    return this;
  }

  public void unsetDrop_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DROP__ISSET_ID);
  }

  /** Returns true if field drop_ is set (has been assigned a value) and false otherwise */
  public boolean isSetDrop_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DROP__ISSET_ID);
  }

  public void setDrop_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DROP__ISSET_ID, value);
  }

  public boolean isSelect_() {
    return this.select_;
  }

  public TTablePermissions setSelect_(boolean select_) {
    this.select_ = select_;
    setSelect_IsSet(true);
    return this;
  }

  public void unsetSelect_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SELECT__ISSET_ID);
  }

  /** Returns true if field select_ is set (has been assigned a value) and false otherwise */
  public boolean isSetSelect_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SELECT__ISSET_ID);
  }

  public void setSelect_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SELECT__ISSET_ID, value);
  }

  public boolean isInsert_() {
    return this.insert_;
  }

  public TTablePermissions setInsert_(boolean insert_) {
    this.insert_ = insert_;
    setInsert_IsSet(true);
    return this;
  }

  public void unsetInsert_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __INSERT__ISSET_ID);
  }

  /** Returns true if field insert_ is set (has been assigned a value) and false otherwise */
  public boolean isSetInsert_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __INSERT__ISSET_ID);
  }

  public void setInsert_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __INSERT__ISSET_ID, value);
  }

  public boolean isUpdate_() {
    return this.update_;
  }

  public TTablePermissions setUpdate_(boolean update_) {
    this.update_ = update_;
    setUpdate_IsSet(true);
    return this;
  }

  public void unsetUpdate_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __UPDATE__ISSET_ID);
  }

  /** Returns true if field update_ is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdate_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __UPDATE__ISSET_ID);
  }

  public void setUpdate_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __UPDATE__ISSET_ID, value);
  }

  public boolean isDelete_() {
    return this.delete_;
  }

  public TTablePermissions setDelete_(boolean delete_) {
    this.delete_ = delete_;
    setDelete_IsSet(true);
    return this;
  }

  public void unsetDelete_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DELETE__ISSET_ID);
  }

  /** Returns true if field delete_ is set (has been assigned a value) and false otherwise */
  public boolean isSetDelete_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DELETE__ISSET_ID);
  }

  public void setDelete_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DELETE__ISSET_ID, value);
  }

  public boolean isTruncate_() {
    return this.truncate_;
  }

  public TTablePermissions setTruncate_(boolean truncate_) {
    this.truncate_ = truncate_;
    setTruncate_IsSet(true);
    return this;
  }

  public void unsetTruncate_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TRUNCATE__ISSET_ID);
  }

  /** Returns true if field truncate_ is set (has been assigned a value) and false otherwise */
  public boolean isSetTruncate_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TRUNCATE__ISSET_ID);
  }

  public void setTruncate_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TRUNCATE__ISSET_ID, value);
  }

  public boolean isAlter_() {
    return this.alter_;
  }

  public TTablePermissions setAlter_(boolean alter_) {
    this.alter_ = alter_;
    setAlter_IsSet(true);
    return this;
  }

  public void unsetAlter_() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ALTER__ISSET_ID);
  }

  /** Returns true if field alter_ is set (has been assigned a value) and false otherwise */
  public boolean isSetAlter_() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ALTER__ISSET_ID);
  }

  public void setAlter_IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ALTER__ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case CREATE_:
      if (value == null) {
        unsetCreate_();
      } else {
        setCreate_((java.lang.Boolean)value);
      }
      break;

    case DROP_:
      if (value == null) {
        unsetDrop_();
      } else {
        setDrop_((java.lang.Boolean)value);
      }
      break;

    case SELECT_:
      if (value == null) {
        unsetSelect_();
      } else {
        setSelect_((java.lang.Boolean)value);
      }
      break;

    case INSERT_:
      if (value == null) {
        unsetInsert_();
      } else {
        setInsert_((java.lang.Boolean)value);
      }
      break;

    case UPDATE_:
      if (value == null) {
        unsetUpdate_();
      } else {
        setUpdate_((java.lang.Boolean)value);
      }
      break;

    case DELETE_:
      if (value == null) {
        unsetDelete_();
      } else {
        setDelete_((java.lang.Boolean)value);
      }
      break;

    case TRUNCATE_:
      if (value == null) {
        unsetTruncate_();
      } else {
        setTruncate_((java.lang.Boolean)value);
      }
      break;

    case ALTER_:
      if (value == null) {
        unsetAlter_();
      } else {
        setAlter_((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case CREATE_:
      return isCreate_();

    case DROP_:
      return isDrop_();

    case SELECT_:
      return isSelect_();

    case INSERT_:
      return isInsert_();

    case UPDATE_:
      return isUpdate_();

    case DELETE_:
      return isDelete_();

    case TRUNCATE_:
      return isTruncate_();

    case ALTER_:
      return isAlter_();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case CREATE_:
      return isSetCreate_();
    case DROP_:
      return isSetDrop_();
    case SELECT_:
      return isSetSelect_();
    case INSERT_:
      return isSetInsert_();
    case UPDATE_:
      return isSetUpdate_();
    case DELETE_:
      return isSetDelete_();
    case TRUNCATE_:
      return isSetTruncate_();
    case ALTER_:
      return isSetAlter_();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TTablePermissions)
      return this.equals((TTablePermissions)that);
    return false;
  }

  public boolean equals(TTablePermissions that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_create_ = true;
    boolean that_present_create_ = true;
    if (this_present_create_ || that_present_create_) {
      if (!(this_present_create_ && that_present_create_))
        return false;
      if (this.create_ != that.create_)
        return false;
    }

    boolean this_present_drop_ = true;
    boolean that_present_drop_ = true;
    if (this_present_drop_ || that_present_drop_) {
      if (!(this_present_drop_ && that_present_drop_))
        return false;
      if (this.drop_ != that.drop_)
        return false;
    }

    boolean this_present_select_ = true;
    boolean that_present_select_ = true;
    if (this_present_select_ || that_present_select_) {
      if (!(this_present_select_ && that_present_select_))
        return false;
      if (this.select_ != that.select_)
        return false;
    }

    boolean this_present_insert_ = true;
    boolean that_present_insert_ = true;
    if (this_present_insert_ || that_present_insert_) {
      if (!(this_present_insert_ && that_present_insert_))
        return false;
      if (this.insert_ != that.insert_)
        return false;
    }

    boolean this_present_update_ = true;
    boolean that_present_update_ = true;
    if (this_present_update_ || that_present_update_) {
      if (!(this_present_update_ && that_present_update_))
        return false;
      if (this.update_ != that.update_)
        return false;
    }

    boolean this_present_delete_ = true;
    boolean that_present_delete_ = true;
    if (this_present_delete_ || that_present_delete_) {
      if (!(this_present_delete_ && that_present_delete_))
        return false;
      if (this.delete_ != that.delete_)
        return false;
    }

    boolean this_present_truncate_ = true;
    boolean that_present_truncate_ = true;
    if (this_present_truncate_ || that_present_truncate_) {
      if (!(this_present_truncate_ && that_present_truncate_))
        return false;
      if (this.truncate_ != that.truncate_)
        return false;
    }

    boolean this_present_alter_ = true;
    boolean that_present_alter_ = true;
    if (this_present_alter_ || that_present_alter_) {
      if (!(this_present_alter_ && that_present_alter_))
        return false;
      if (this.alter_ != that.alter_)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((create_) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((drop_) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((select_) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((insert_) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((update_) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((delete_) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((truncate_) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((alter_) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TTablePermissions other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetCreate_()).compareTo(other.isSetCreate_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreate_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.create_, other.create_);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDrop_()).compareTo(other.isSetDrop_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDrop_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.drop_, other.drop_);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSelect_()).compareTo(other.isSetSelect_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSelect_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.select_, other.select_);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetInsert_()).compareTo(other.isSetInsert_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInsert_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.insert_, other.insert_);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUpdate_()).compareTo(other.isSetUpdate_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdate_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.update_, other.update_);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDelete_()).compareTo(other.isSetDelete_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDelete_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.delete_, other.delete_);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTruncate_()).compareTo(other.isSetTruncate_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTruncate_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.truncate_, other.truncate_);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAlter_()).compareTo(other.isSetAlter_());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAlter_()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.alter_, other.alter_);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TTablePermissions(");
    boolean first = true;

    sb.append("create_:");
    sb.append(this.create_);
    first = false;
    if (!first) sb.append(", ");
    sb.append("drop_:");
    sb.append(this.drop_);
    first = false;
    if (!first) sb.append(", ");
    sb.append("select_:");
    sb.append(this.select_);
    first = false;
    if (!first) sb.append(", ");
    sb.append("insert_:");
    sb.append(this.insert_);
    first = false;
    if (!first) sb.append(", ");
    sb.append("update_:");
    sb.append(this.update_);
    first = false;
    if (!first) sb.append(", ");
    sb.append("delete_:");
    sb.append(this.delete_);
    first = false;
    if (!first) sb.append(", ");
    sb.append("truncate_:");
    sb.append(this.truncate_);
    first = false;
    if (!first) sb.append(", ");
    sb.append("alter_:");
    sb.append(this.alter_);
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

  private static class TTablePermissionsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTablePermissionsStandardScheme getScheme() {
      return new TTablePermissionsStandardScheme();
    }
  }

  private static class TTablePermissionsStandardScheme extends org.apache.thrift.scheme.StandardScheme<TTablePermissions> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TTablePermissions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CREATE_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.create_ = iprot.readBool();
              struct.setCreate_IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DROP_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.drop_ = iprot.readBool();
              struct.setDrop_IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SELECT_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.select_ = iprot.readBool();
              struct.setSelect_IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // INSERT_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.insert_ = iprot.readBool();
              struct.setInsert_IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // UPDATE_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.update_ = iprot.readBool();
              struct.setUpdate_IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // DELETE_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.delete_ = iprot.readBool();
              struct.setDelete_IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // TRUNCATE_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.truncate_ = iprot.readBool();
              struct.setTruncate_IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // ALTER_
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.alter_ = iprot.readBool();
              struct.setAlter_IsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TTablePermissions struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CREATE__FIELD_DESC);
      oprot.writeBool(struct.create_);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DROP__FIELD_DESC);
      oprot.writeBool(struct.drop_);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SELECT__FIELD_DESC);
      oprot.writeBool(struct.select_);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(INSERT__FIELD_DESC);
      oprot.writeBool(struct.insert_);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(UPDATE__FIELD_DESC);
      oprot.writeBool(struct.update_);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DELETE__FIELD_DESC);
      oprot.writeBool(struct.delete_);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TRUNCATE__FIELD_DESC);
      oprot.writeBool(struct.truncate_);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ALTER__FIELD_DESC);
      oprot.writeBool(struct.alter_);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TTablePermissionsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTablePermissionsTupleScheme getScheme() {
      return new TTablePermissionsTupleScheme();
    }
  }

  private static class TTablePermissionsTupleScheme extends org.apache.thrift.scheme.TupleScheme<TTablePermissions> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TTablePermissions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetCreate_()) {
        optionals.set(0);
      }
      if (struct.isSetDrop_()) {
        optionals.set(1);
      }
      if (struct.isSetSelect_()) {
        optionals.set(2);
      }
      if (struct.isSetInsert_()) {
        optionals.set(3);
      }
      if (struct.isSetUpdate_()) {
        optionals.set(4);
      }
      if (struct.isSetDelete_()) {
        optionals.set(5);
      }
      if (struct.isSetTruncate_()) {
        optionals.set(6);
      }
      if (struct.isSetAlter_()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetCreate_()) {
        oprot.writeBool(struct.create_);
      }
      if (struct.isSetDrop_()) {
        oprot.writeBool(struct.drop_);
      }
      if (struct.isSetSelect_()) {
        oprot.writeBool(struct.select_);
      }
      if (struct.isSetInsert_()) {
        oprot.writeBool(struct.insert_);
      }
      if (struct.isSetUpdate_()) {
        oprot.writeBool(struct.update_);
      }
      if (struct.isSetDelete_()) {
        oprot.writeBool(struct.delete_);
      }
      if (struct.isSetTruncate_()) {
        oprot.writeBool(struct.truncate_);
      }
      if (struct.isSetAlter_()) {
        oprot.writeBool(struct.alter_);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TTablePermissions struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.create_ = iprot.readBool();
        struct.setCreate_IsSet(true);
      }
      if (incoming.get(1)) {
        struct.drop_ = iprot.readBool();
        struct.setDrop_IsSet(true);
      }
      if (incoming.get(2)) {
        struct.select_ = iprot.readBool();
        struct.setSelect_IsSet(true);
      }
      if (incoming.get(3)) {
        struct.insert_ = iprot.readBool();
        struct.setInsert_IsSet(true);
      }
      if (incoming.get(4)) {
        struct.update_ = iprot.readBool();
        struct.setUpdate_IsSet(true);
      }
      if (incoming.get(5)) {
        struct.delete_ = iprot.readBool();
        struct.setDelete_IsSet(true);
      }
      if (incoming.get(6)) {
        struct.truncate_ = iprot.readBool();
        struct.setTruncate_IsSet(true);
      }
      if (incoming.get(7)) {
        struct.alter_ = iprot.readBool();
        struct.setAlter_IsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

