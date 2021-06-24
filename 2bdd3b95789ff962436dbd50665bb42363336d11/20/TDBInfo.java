/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TDBInfo implements org.apache.thrift.TBase<TDBInfo, TDBInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TDBInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TDBInfo");

  private static final org.apache.thrift.protocol.TField DB_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("db_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DB_OWNER_FIELD_DESC = new org.apache.thrift.protocol.TField("db_owner", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TDBInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TDBInfoTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.lang.String db_name; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String db_owner; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DB_NAME((short)1, "db_name"),
    DB_OWNER((short)2, "db_owner");

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
        case 1: // DB_NAME
          return DB_NAME;
        case 2: // DB_OWNER
          return DB_OWNER;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DB_NAME, new org.apache.thrift.meta_data.FieldMetaData("db_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DB_OWNER, new org.apache.thrift.meta_data.FieldMetaData("db_owner", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TDBInfo.class, metaDataMap);
  }

  public TDBInfo() {
  }

  public TDBInfo(
    java.lang.String db_name,
    java.lang.String db_owner)
  {
    this();
    this.db_name = db_name;
    this.db_owner = db_owner;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TDBInfo(TDBInfo other) {
    if (other.isSetDb_name()) {
      this.db_name = other.db_name;
    }
    if (other.isSetDb_owner()) {
      this.db_owner = other.db_owner;
    }
  }

  public TDBInfo deepCopy() {
    return new TDBInfo(this);
  }

  @Override
  public void clear() {
    this.db_name = null;
    this.db_owner = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDb_name() {
    return this.db_name;
  }

  public TDBInfo setDb_name(@org.apache.thrift.annotation.Nullable java.lang.String db_name) {
    this.db_name = db_name;
    return this;
  }

  public void unsetDb_name() {
    this.db_name = null;
  }

  /** Returns true if field db_name is set (has been assigned a value) and false otherwise */
  public boolean isSetDb_name() {
    return this.db_name != null;
  }

  public void setDb_nameIsSet(boolean value) {
    if (!value) {
      this.db_name = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDb_owner() {
    return this.db_owner;
  }

  public TDBInfo setDb_owner(@org.apache.thrift.annotation.Nullable java.lang.String db_owner) {
    this.db_owner = db_owner;
    return this;
  }

  public void unsetDb_owner() {
    this.db_owner = null;
  }

  /** Returns true if field db_owner is set (has been assigned a value) and false otherwise */
  public boolean isSetDb_owner() {
    return this.db_owner != null;
  }

  public void setDb_ownerIsSet(boolean value) {
    if (!value) {
      this.db_owner = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case DB_NAME:
      if (value == null) {
        unsetDb_name();
      } else {
        setDb_name((java.lang.String)value);
      }
      break;

    case DB_OWNER:
      if (value == null) {
        unsetDb_owner();
      } else {
        setDb_owner((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case DB_NAME:
      return getDb_name();

    case DB_OWNER:
      return getDb_owner();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case DB_NAME:
      return isSetDb_name();
    case DB_OWNER:
      return isSetDb_owner();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TDBInfo)
      return this.equals((TDBInfo)that);
    return false;
  }

  public boolean equals(TDBInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_db_name = true && this.isSetDb_name();
    boolean that_present_db_name = true && that.isSetDb_name();
    if (this_present_db_name || that_present_db_name) {
      if (!(this_present_db_name && that_present_db_name))
        return false;
      if (!this.db_name.equals(that.db_name))
        return false;
    }

    boolean this_present_db_owner = true && this.isSetDb_owner();
    boolean that_present_db_owner = true && that.isSetDb_owner();
    if (this_present_db_owner || that_present_db_owner) {
      if (!(this_present_db_owner && that_present_db_owner))
        return false;
      if (!this.db_owner.equals(that.db_owner))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDb_name()) ? 131071 : 524287);
    if (isSetDb_name())
      hashCode = hashCode * 8191 + db_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetDb_owner()) ? 131071 : 524287);
    if (isSetDb_owner())
      hashCode = hashCode * 8191 + db_owner.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TDBInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetDb_name()).compareTo(other.isSetDb_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDb_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.db_name, other.db_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDb_owner()).compareTo(other.isSetDb_owner());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDb_owner()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.db_owner, other.db_owner);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TDBInfo(");
    boolean first = true;

    sb.append("db_name:");
    if (this.db_name == null) {
      sb.append("null");
    } else {
      sb.append(this.db_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("db_owner:");
    if (this.db_owner == null) {
      sb.append("null");
    } else {
      sb.append(this.db_owner);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TDBInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDBInfoStandardScheme getScheme() {
      return new TDBInfoStandardScheme();
    }
  }

  private static class TDBInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<TDBInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TDBInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DB_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.db_name = iprot.readString();
              struct.setDb_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DB_OWNER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.db_owner = iprot.readString();
              struct.setDb_ownerIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TDBInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.db_name != null) {
        oprot.writeFieldBegin(DB_NAME_FIELD_DESC);
        oprot.writeString(struct.db_name);
        oprot.writeFieldEnd();
      }
      if (struct.db_owner != null) {
        oprot.writeFieldBegin(DB_OWNER_FIELD_DESC);
        oprot.writeString(struct.db_owner);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TDBInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDBInfoTupleScheme getScheme() {
      return new TDBInfoTupleScheme();
    }
  }

  private static class TDBInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<TDBInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TDBInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDb_name()) {
        optionals.set(0);
      }
      if (struct.isSetDb_owner()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetDb_name()) {
        oprot.writeString(struct.db_name);
      }
      if (struct.isSetDb_owner()) {
        oprot.writeString(struct.db_owner);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TDBInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.db_name = iprot.readString();
        struct.setDb_nameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.db_owner = iprot.readString();
        struct.setDb_ownerIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

