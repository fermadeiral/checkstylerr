/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TDetectResult implements org.apache.thrift.TBase<TDetectResult, TDetectResult._Fields>, java.io.Serializable, Cloneable, Comparable<TDetectResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TDetectResult");

  private static final org.apache.thrift.protocol.TField ROW_SET_FIELD_DESC = new org.apache.thrift.protocol.TField("row_set", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField COPY_PARAMS_FIELD_DESC = new org.apache.thrift.protocol.TField("copy_params", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TDetectResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TDetectResultTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable TRowSet row_set; // required
  public @org.apache.thrift.annotation.Nullable TCopyParams copy_params; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ROW_SET((short)1, "row_set"),
    COPY_PARAMS((short)2, "copy_params");

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
        case 1: // ROW_SET
          return ROW_SET;
        case 2: // COPY_PARAMS
          return COPY_PARAMS;
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
    tmpMap.put(_Fields.ROW_SET, new org.apache.thrift.meta_data.FieldMetaData("row_set", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TRowSet.class)));
    tmpMap.put(_Fields.COPY_PARAMS, new org.apache.thrift.meta_data.FieldMetaData("copy_params", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TCopyParams.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TDetectResult.class, metaDataMap);
  }

  public TDetectResult() {
  }

  public TDetectResult(
    TRowSet row_set,
    TCopyParams copy_params)
  {
    this();
    this.row_set = row_set;
    this.copy_params = copy_params;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TDetectResult(TDetectResult other) {
    if (other.isSetRow_set()) {
      this.row_set = new TRowSet(other.row_set);
    }
    if (other.isSetCopy_params()) {
      this.copy_params = new TCopyParams(other.copy_params);
    }
  }

  public TDetectResult deepCopy() {
    return new TDetectResult(this);
  }

  @Override
  public void clear() {
    this.row_set = null;
    this.copy_params = null;
  }

  @org.apache.thrift.annotation.Nullable
  public TRowSet getRow_set() {
    return this.row_set;
  }

  public TDetectResult setRow_set(@org.apache.thrift.annotation.Nullable TRowSet row_set) {
    this.row_set = row_set;
    return this;
  }

  public void unsetRow_set() {
    this.row_set = null;
  }

  /** Returns true if field row_set is set (has been assigned a value) and false otherwise */
  public boolean isSetRow_set() {
    return this.row_set != null;
  }

  public void setRow_setIsSet(boolean value) {
    if (!value) {
      this.row_set = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public TCopyParams getCopy_params() {
    return this.copy_params;
  }

  public TDetectResult setCopy_params(@org.apache.thrift.annotation.Nullable TCopyParams copy_params) {
    this.copy_params = copy_params;
    return this;
  }

  public void unsetCopy_params() {
    this.copy_params = null;
  }

  /** Returns true if field copy_params is set (has been assigned a value) and false otherwise */
  public boolean isSetCopy_params() {
    return this.copy_params != null;
  }

  public void setCopy_paramsIsSet(boolean value) {
    if (!value) {
      this.copy_params = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case ROW_SET:
      if (value == null) {
        unsetRow_set();
      } else {
        setRow_set((TRowSet)value);
      }
      break;

    case COPY_PARAMS:
      if (value == null) {
        unsetCopy_params();
      } else {
        setCopy_params((TCopyParams)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ROW_SET:
      return getRow_set();

    case COPY_PARAMS:
      return getCopy_params();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ROW_SET:
      return isSetRow_set();
    case COPY_PARAMS:
      return isSetCopy_params();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TDetectResult)
      return this.equals((TDetectResult)that);
    return false;
  }

  public boolean equals(TDetectResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_row_set = true && this.isSetRow_set();
    boolean that_present_row_set = true && that.isSetRow_set();
    if (this_present_row_set || that_present_row_set) {
      if (!(this_present_row_set && that_present_row_set))
        return false;
      if (!this.row_set.equals(that.row_set))
        return false;
    }

    boolean this_present_copy_params = true && this.isSetCopy_params();
    boolean that_present_copy_params = true && that.isSetCopy_params();
    if (this_present_copy_params || that_present_copy_params) {
      if (!(this_present_copy_params && that_present_copy_params))
        return false;
      if (!this.copy_params.equals(that.copy_params))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetRow_set()) ? 131071 : 524287);
    if (isSetRow_set())
      hashCode = hashCode * 8191 + row_set.hashCode();

    hashCode = hashCode * 8191 + ((isSetCopy_params()) ? 131071 : 524287);
    if (isSetCopy_params())
      hashCode = hashCode * 8191 + copy_params.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TDetectResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetRow_set()).compareTo(other.isSetRow_set());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRow_set()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.row_set, other.row_set);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCopy_params()).compareTo(other.isSetCopy_params());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCopy_params()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.copy_params, other.copy_params);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TDetectResult(");
    boolean first = true;

    sb.append("row_set:");
    if (this.row_set == null) {
      sb.append("null");
    } else {
      sb.append(this.row_set);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("copy_params:");
    if (this.copy_params == null) {
      sb.append("null");
    } else {
      sb.append(this.copy_params);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (row_set != null) {
      row_set.validate();
    }
    if (copy_params != null) {
      copy_params.validate();
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TDetectResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDetectResultStandardScheme getScheme() {
      return new TDetectResultStandardScheme();
    }
  }

  private static class TDetectResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<TDetectResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TDetectResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ROW_SET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.row_set = new TRowSet();
              struct.row_set.read(iprot);
              struct.setRow_setIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COPY_PARAMS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.copy_params = new TCopyParams();
              struct.copy_params.read(iprot);
              struct.setCopy_paramsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TDetectResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.row_set != null) {
        oprot.writeFieldBegin(ROW_SET_FIELD_DESC);
        struct.row_set.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.copy_params != null) {
        oprot.writeFieldBegin(COPY_PARAMS_FIELD_DESC);
        struct.copy_params.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TDetectResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDetectResultTupleScheme getScheme() {
      return new TDetectResultTupleScheme();
    }
  }

  private static class TDetectResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<TDetectResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TDetectResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetRow_set()) {
        optionals.set(0);
      }
      if (struct.isSetCopy_params()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetRow_set()) {
        struct.row_set.write(oprot);
      }
      if (struct.isSetCopy_params()) {
        struct.copy_params.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TDetectResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.row_set = new TRowSet();
        struct.row_set.read(iprot);
        struct.setRow_setIsSet(true);
      }
      if (incoming.get(1)) {
        struct.copy_params = new TCopyParams();
        struct.copy_params.read(iprot);
        struct.setCopy_paramsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

