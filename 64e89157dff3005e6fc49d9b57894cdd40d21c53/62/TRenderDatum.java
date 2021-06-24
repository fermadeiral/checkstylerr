/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TRenderDatum implements org.apache.thrift.TBase<TRenderDatum, TRenderDatum._Fields>, java.io.Serializable, Cloneable, Comparable<TRenderDatum> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TRenderDatum");

  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CNT_FIELD_DESC = new org.apache.thrift.protocol.TField("cnt", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("value", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TRenderDatumStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TRenderDatumTupleSchemeFactory();

  /**
   * 
   * @see com.omnisci.thrift.server.TDatumType
   */
  public com.omnisci.thrift.server.TDatumType type; // required
  public int cnt; // required
  public java.nio.ByteBuffer value; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see com.omnisci.thrift.server.TDatumType
     */
    TYPE((short)1, "type"),
    CNT((short)2, "cnt"),
    VALUE((short)3, "value");

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
        case 1: // TYPE
          return TYPE;
        case 2: // CNT
          return CNT;
        case 3: // VALUE
          return VALUE;
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
  private static final int __CNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.omnisci.thrift.server.TDatumType.class)));
    tmpMap.put(_Fields.CNT, new org.apache.thrift.meta_data.FieldMetaData("cnt", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VALUE, new org.apache.thrift.meta_data.FieldMetaData("value", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TRenderDatum.class, metaDataMap);
  }

  public TRenderDatum() {
  }

  public TRenderDatum(
    com.omnisci.thrift.server.TDatumType type,
    int cnt,
    java.nio.ByteBuffer value)
  {
    this();
    this.type = type;
    this.cnt = cnt;
    setCntIsSet(true);
    this.value = org.apache.thrift.TBaseHelper.copyBinary(value);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TRenderDatum(TRenderDatum other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetType()) {
      this.type = other.type;
    }
    this.cnt = other.cnt;
    if (other.isSetValue()) {
      this.value = org.apache.thrift.TBaseHelper.copyBinary(other.value);
    }
  }

  public TRenderDatum deepCopy() {
    return new TRenderDatum(this);
  }

  @Override
  public void clear() {
    this.type = null;
    setCntIsSet(false);
    this.cnt = 0;
    this.value = null;
  }

  /**
   * 
   * @see com.omnisci.thrift.server.TDatumType
   */
  public com.omnisci.thrift.server.TDatumType getType() {
    return this.type;
  }

  /**
   * 
   * @see com.omnisci.thrift.server.TDatumType
   */
  public TRenderDatum setType(com.omnisci.thrift.server.TDatumType type) {
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

  public int getCnt() {
    return this.cnt;
  }

  public TRenderDatum setCnt(int cnt) {
    this.cnt = cnt;
    setCntIsSet(true);
    return this;
  }

  public void unsetCnt() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CNT_ISSET_ID);
  }

  /** Returns true if field cnt is set (has been assigned a value) and false otherwise */
  public boolean isSetCnt() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CNT_ISSET_ID);
  }

  public void setCntIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CNT_ISSET_ID, value);
  }

  public byte[] getValue() {
    setValue(org.apache.thrift.TBaseHelper.rightSize(value));
    return value == null ? null : value.array();
  }

  public java.nio.ByteBuffer bufferForValue() {
    return org.apache.thrift.TBaseHelper.copyBinary(value);
  }

  public TRenderDatum setValue(byte[] value) {
    this.value = value == null ? (java.nio.ByteBuffer)null : java.nio.ByteBuffer.wrap(value.clone());
    return this;
  }

  public TRenderDatum setValue(java.nio.ByteBuffer value) {
    this.value = org.apache.thrift.TBaseHelper.copyBinary(value);
    return this;
  }

  public void unsetValue() {
    this.value = null;
  }

  /** Returns true if field value is set (has been assigned a value) and false otherwise */
  public boolean isSetValue() {
    return this.value != null;
  }

  public void setValueIsSet(boolean value) {
    if (!value) {
      this.value = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((com.omnisci.thrift.server.TDatumType)value);
      }
      break;

    case CNT:
      if (value == null) {
        unsetCnt();
      } else {
        setCnt((java.lang.Integer)value);
      }
      break;

    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        if (value instanceof byte[]) {
          setValue((byte[])value);
        } else {
          setValue((java.nio.ByteBuffer)value);
        }
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TYPE:
      return getType();

    case CNT:
      return getCnt();

    case VALUE:
      return getValue();

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
    case CNT:
      return isSetCnt();
    case VALUE:
      return isSetValue();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TRenderDatum)
      return this.equals((TRenderDatum)that);
    return false;
  }

  public boolean equals(TRenderDatum that) {
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

    boolean this_present_cnt = true;
    boolean that_present_cnt = true;
    if (this_present_cnt || that_present_cnt) {
      if (!(this_present_cnt && that_present_cnt))
        return false;
      if (this.cnt != that.cnt)
        return false;
    }

    boolean this_present_value = true && this.isSetValue();
    boolean that_present_value = true && that.isSetValue();
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (!this.value.equals(that.value))
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

    hashCode = hashCode * 8191 + cnt;

    hashCode = hashCode * 8191 + ((isSetValue()) ? 131071 : 524287);
    if (isSetValue())
      hashCode = hashCode * 8191 + value.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TRenderDatum other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetCnt()).compareTo(other.isSetCnt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCnt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cnt, other.cnt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetValue()).compareTo(other.isSetValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.value, other.value);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TRenderDatum(");
    boolean first = true;

    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cnt:");
    sb.append(this.cnt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("value:");
    if (this.value == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.value, sb);
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

  private static class TRenderDatumStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TRenderDatumStandardScheme getScheme() {
      return new TRenderDatumStandardScheme();
    }
  }

  private static class TRenderDatumStandardScheme extends org.apache.thrift.scheme.StandardScheme<TRenderDatum> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TRenderDatum struct) throws org.apache.thrift.TException {
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
          case 2: // CNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.cnt = iprot.readI32();
              struct.setCntIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.value = iprot.readBinary();
              struct.setValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TRenderDatum struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CNT_FIELD_DESC);
      oprot.writeI32(struct.cnt);
      oprot.writeFieldEnd();
      if (struct.value != null) {
        oprot.writeFieldBegin(VALUE_FIELD_DESC);
        oprot.writeBinary(struct.value);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TRenderDatumTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TRenderDatumTupleScheme getScheme() {
      return new TRenderDatumTupleScheme();
    }
  }

  private static class TRenderDatumTupleScheme extends org.apache.thrift.scheme.TupleScheme<TRenderDatum> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TRenderDatum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetType()) {
        optionals.set(0);
      }
      if (struct.isSetCnt()) {
        optionals.set(1);
      }
      if (struct.isSetValue()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetCnt()) {
        oprot.writeI32(struct.cnt);
      }
      if (struct.isSetValue()) {
        oprot.writeBinary(struct.value);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TRenderDatum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.type = com.omnisci.thrift.server.TDatumType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.cnt = iprot.readI32();
        struct.setCntIsSet(true);
      }
      if (incoming.get(2)) {
        struct.value = iprot.readBinary();
        struct.setValueIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

