/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TDatum implements org.apache.thrift.TBase<TDatum, TDatum._Fields>, java.io.Serializable, Cloneable, Comparable<TDatum> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TDatum");

  private static final org.apache.thrift.protocol.TField VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("val", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField IS_NULL_FIELD_DESC = new org.apache.thrift.protocol.TField("is_null", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TDatumStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TDatumTupleSchemeFactory();

  public TDatumVal val; // required
  public boolean is_null; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    VAL((short)1, "val"),
    IS_NULL((short)2, "is_null");

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
        case 1: // VAL
          return VAL;
        case 2: // IS_NULL
          return IS_NULL;
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
  private static final int __IS_NULL_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.VAL, new org.apache.thrift.meta_data.FieldMetaData("val", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TDatumVal.class)));
    tmpMap.put(_Fields.IS_NULL, new org.apache.thrift.meta_data.FieldMetaData("is_null", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TDatum.class, metaDataMap);
  }

  public TDatum() {
  }

  public TDatum(
    TDatumVal val,
    boolean is_null)
  {
    this();
    this.val = val;
    this.is_null = is_null;
    setIs_nullIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TDatum(TDatum other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetVal()) {
      this.val = new TDatumVal(other.val);
    }
    this.is_null = other.is_null;
  }

  public TDatum deepCopy() {
    return new TDatum(this);
  }

  @Override
  public void clear() {
    this.val = null;
    setIs_nullIsSet(false);
    this.is_null = false;
  }

  public TDatumVal getVal() {
    return this.val;
  }

  public TDatum setVal(TDatumVal val) {
    this.val = val;
    return this;
  }

  public void unsetVal() {
    this.val = null;
  }

  /** Returns true if field val is set (has been assigned a value) and false otherwise */
  public boolean isSetVal() {
    return this.val != null;
  }

  public void setValIsSet(boolean value) {
    if (!value) {
      this.val = null;
    }
  }

  public boolean isIs_null() {
    return this.is_null;
  }

  public TDatum setIs_null(boolean is_null) {
    this.is_null = is_null;
    setIs_nullIsSet(true);
    return this;
  }

  public void unsetIs_null() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IS_NULL_ISSET_ID);
  }

  /** Returns true if field is_null is set (has been assigned a value) and false otherwise */
  public boolean isSetIs_null() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IS_NULL_ISSET_ID);
  }

  public void setIs_nullIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IS_NULL_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case VAL:
      if (value == null) {
        unsetVal();
      } else {
        setVal((TDatumVal)value);
      }
      break;

    case IS_NULL:
      if (value == null) {
        unsetIs_null();
      } else {
        setIs_null((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case VAL:
      return getVal();

    case IS_NULL:
      return isIs_null();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case VAL:
      return isSetVal();
    case IS_NULL:
      return isSetIs_null();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TDatum)
      return this.equals((TDatum)that);
    return false;
  }

  public boolean equals(TDatum that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_val = true && this.isSetVal();
    boolean that_present_val = true && that.isSetVal();
    if (this_present_val || that_present_val) {
      if (!(this_present_val && that_present_val))
        return false;
      if (!this.val.equals(that.val))
        return false;
    }

    boolean this_present_is_null = true;
    boolean that_present_is_null = true;
    if (this_present_is_null || that_present_is_null) {
      if (!(this_present_is_null && that_present_is_null))
        return false;
      if (this.is_null != that.is_null)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetVal()) ? 131071 : 524287);
    if (isSetVal())
      hashCode = hashCode * 8191 + val.hashCode();

    hashCode = hashCode * 8191 + ((is_null) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TDatum other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetVal()).compareTo(other.isSetVal());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVal()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.val, other.val);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIs_null()).compareTo(other.isSetIs_null());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_null()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.is_null, other.is_null);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TDatum(");
    boolean first = true;

    sb.append("val:");
    if (this.val == null) {
      sb.append("null");
    } else {
      sb.append(this.val);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_null:");
    sb.append(this.is_null);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (val != null) {
      val.validate();
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

  private static class TDatumStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDatumStandardScheme getScheme() {
      return new TDatumStandardScheme();
    }
  }

  private static class TDatumStandardScheme extends org.apache.thrift.scheme.StandardScheme<TDatum> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TDatum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.val = new TDatumVal();
              struct.val.read(iprot);
              struct.setValIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IS_NULL
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.is_null = iprot.readBool();
              struct.setIs_nullIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TDatum struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.val != null) {
        oprot.writeFieldBegin(VAL_FIELD_DESC);
        struct.val.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_NULL_FIELD_DESC);
      oprot.writeBool(struct.is_null);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TDatumTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDatumTupleScheme getScheme() {
      return new TDatumTupleScheme();
    }
  }

  private static class TDatumTupleScheme extends org.apache.thrift.scheme.TupleScheme<TDatum> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TDatum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetVal()) {
        optionals.set(0);
      }
      if (struct.isSetIs_null()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetVal()) {
        struct.val.write(oprot);
      }
      if (struct.isSetIs_null()) {
        oprot.writeBool(struct.is_null);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TDatum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.val = new TDatumVal();
        struct.val.read(iprot);
        struct.setValIsSet(true);
      }
      if (incoming.get(1)) {
        struct.is_null = iprot.readBool();
        struct.setIs_nullIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

