/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TDatumVal implements org.apache.thrift.TBase<TDatumVal, TDatumVal._Fields>, java.io.Serializable, Cloneable, Comparable<TDatumVal> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TDatumVal");

  private static final org.apache.thrift.protocol.TField INT_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("int_val", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField REAL_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("real_val", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField STR_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("str_val", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ARR_VAL_FIELD_DESC = new org.apache.thrift.protocol.TField("arr_val", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TDatumValStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TDatumValTupleSchemeFactory();

  public long int_val; // required
  public double real_val; // required
  public java.lang.String str_val; // required
  public java.util.List<TDatum> arr_val; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    INT_VAL((short)1, "int_val"),
    REAL_VAL((short)2, "real_val"),
    STR_VAL((short)3, "str_val"),
    ARR_VAL((short)4, "arr_val");

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
        case 1: // INT_VAL
          return INT_VAL;
        case 2: // REAL_VAL
          return REAL_VAL;
        case 3: // STR_VAL
          return STR_VAL;
        case 4: // ARR_VAL
          return ARR_VAL;
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
  private static final int __INT_VAL_ISSET_ID = 0;
  private static final int __REAL_VAL_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.INT_VAL, new org.apache.thrift.meta_data.FieldMetaData("int_val", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.REAL_VAL, new org.apache.thrift.meta_data.FieldMetaData("real_val", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.STR_VAL, new org.apache.thrift.meta_data.FieldMetaData("str_val", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ARR_VAL, new org.apache.thrift.meta_data.FieldMetaData("arr_val", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "TDatum"))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TDatumVal.class, metaDataMap);
  }

  public TDatumVal() {
  }

  public TDatumVal(
    long int_val,
    double real_val,
    java.lang.String str_val,
    java.util.List<TDatum> arr_val)
  {
    this();
    this.int_val = int_val;
    setInt_valIsSet(true);
    this.real_val = real_val;
    setReal_valIsSet(true);
    this.str_val = str_val;
    this.arr_val = arr_val;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TDatumVal(TDatumVal other) {
    __isset_bitfield = other.__isset_bitfield;
    this.int_val = other.int_val;
    this.real_val = other.real_val;
    if (other.isSetStr_val()) {
      this.str_val = other.str_val;
    }
    if (other.isSetArr_val()) {
      java.util.List<TDatum> __this__arr_val = new java.util.ArrayList<TDatum>(other.arr_val.size());
      for (TDatum other_element : other.arr_val) {
        __this__arr_val.add(new TDatum(other_element));
      }
      this.arr_val = __this__arr_val;
    }
  }

  public TDatumVal deepCopy() {
    return new TDatumVal(this);
  }

  @Override
  public void clear() {
    setInt_valIsSet(false);
    this.int_val = 0;
    setReal_valIsSet(false);
    this.real_val = 0.0;
    this.str_val = null;
    this.arr_val = null;
  }

  public long getInt_val() {
    return this.int_val;
  }

  public TDatumVal setInt_val(long int_val) {
    this.int_val = int_val;
    setInt_valIsSet(true);
    return this;
  }

  public void unsetInt_val() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __INT_VAL_ISSET_ID);
  }

  /** Returns true if field int_val is set (has been assigned a value) and false otherwise */
  public boolean isSetInt_val() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __INT_VAL_ISSET_ID);
  }

  public void setInt_valIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __INT_VAL_ISSET_ID, value);
  }

  public double getReal_val() {
    return this.real_val;
  }

  public TDatumVal setReal_val(double real_val) {
    this.real_val = real_val;
    setReal_valIsSet(true);
    return this;
  }

  public void unsetReal_val() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __REAL_VAL_ISSET_ID);
  }

  /** Returns true if field real_val is set (has been assigned a value) and false otherwise */
  public boolean isSetReal_val() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __REAL_VAL_ISSET_ID);
  }

  public void setReal_valIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __REAL_VAL_ISSET_ID, value);
  }

  public java.lang.String getStr_val() {
    return this.str_val;
  }

  public TDatumVal setStr_val(java.lang.String str_val) {
    this.str_val = str_val;
    return this;
  }

  public void unsetStr_val() {
    this.str_val = null;
  }

  /** Returns true if field str_val is set (has been assigned a value) and false otherwise */
  public boolean isSetStr_val() {
    return this.str_val != null;
  }

  public void setStr_valIsSet(boolean value) {
    if (!value) {
      this.str_val = null;
    }
  }

  public int getArr_valSize() {
    return (this.arr_val == null) ? 0 : this.arr_val.size();
  }

  public java.util.Iterator<TDatum> getArr_valIterator() {
    return (this.arr_val == null) ? null : this.arr_val.iterator();
  }

  public void addToArr_val(TDatum elem) {
    if (this.arr_val == null) {
      this.arr_val = new java.util.ArrayList<TDatum>();
    }
    this.arr_val.add(elem);
  }

  public java.util.List<TDatum> getArr_val() {
    return this.arr_val;
  }

  public TDatumVal setArr_val(java.util.List<TDatum> arr_val) {
    this.arr_val = arr_val;
    return this;
  }

  public void unsetArr_val() {
    this.arr_val = null;
  }

  /** Returns true if field arr_val is set (has been assigned a value) and false otherwise */
  public boolean isSetArr_val() {
    return this.arr_val != null;
  }

  public void setArr_valIsSet(boolean value) {
    if (!value) {
      this.arr_val = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case INT_VAL:
      if (value == null) {
        unsetInt_val();
      } else {
        setInt_val((java.lang.Long)value);
      }
      break;

    case REAL_VAL:
      if (value == null) {
        unsetReal_val();
      } else {
        setReal_val((java.lang.Double)value);
      }
      break;

    case STR_VAL:
      if (value == null) {
        unsetStr_val();
      } else {
        setStr_val((java.lang.String)value);
      }
      break;

    case ARR_VAL:
      if (value == null) {
        unsetArr_val();
      } else {
        setArr_val((java.util.List<TDatum>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case INT_VAL:
      return getInt_val();

    case REAL_VAL:
      return getReal_val();

    case STR_VAL:
      return getStr_val();

    case ARR_VAL:
      return getArr_val();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case INT_VAL:
      return isSetInt_val();
    case REAL_VAL:
      return isSetReal_val();
    case STR_VAL:
      return isSetStr_val();
    case ARR_VAL:
      return isSetArr_val();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TDatumVal)
      return this.equals((TDatumVal)that);
    return false;
  }

  public boolean equals(TDatumVal that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_int_val = true;
    boolean that_present_int_val = true;
    if (this_present_int_val || that_present_int_val) {
      if (!(this_present_int_val && that_present_int_val))
        return false;
      if (this.int_val != that.int_val)
        return false;
    }

    boolean this_present_real_val = true;
    boolean that_present_real_val = true;
    if (this_present_real_val || that_present_real_val) {
      if (!(this_present_real_val && that_present_real_val))
        return false;
      if (this.real_val != that.real_val)
        return false;
    }

    boolean this_present_str_val = true && this.isSetStr_val();
    boolean that_present_str_val = true && that.isSetStr_val();
    if (this_present_str_val || that_present_str_val) {
      if (!(this_present_str_val && that_present_str_val))
        return false;
      if (!this.str_val.equals(that.str_val))
        return false;
    }

    boolean this_present_arr_val = true && this.isSetArr_val();
    boolean that_present_arr_val = true && that.isSetArr_val();
    if (this_present_arr_val || that_present_arr_val) {
      if (!(this_present_arr_val && that_present_arr_val))
        return false;
      if (!this.arr_val.equals(that.arr_val))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(int_val);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(real_val);

    hashCode = hashCode * 8191 + ((isSetStr_val()) ? 131071 : 524287);
    if (isSetStr_val())
      hashCode = hashCode * 8191 + str_val.hashCode();

    hashCode = hashCode * 8191 + ((isSetArr_val()) ? 131071 : 524287);
    if (isSetArr_val())
      hashCode = hashCode * 8191 + arr_val.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TDatumVal other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetInt_val()).compareTo(other.isSetInt_val());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInt_val()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.int_val, other.int_val);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetReal_val()).compareTo(other.isSetReal_val());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReal_val()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.real_val, other.real_val);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetStr_val()).compareTo(other.isSetStr_val());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStr_val()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.str_val, other.str_val);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetArr_val()).compareTo(other.isSetArr_val());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArr_val()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arr_val, other.arr_val);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TDatumVal(");
    boolean first = true;

    sb.append("int_val:");
    sb.append(this.int_val);
    first = false;
    if (!first) sb.append(", ");
    sb.append("real_val:");
    sb.append(this.real_val);
    first = false;
    if (!first) sb.append(", ");
    sb.append("str_val:");
    if (this.str_val == null) {
      sb.append("null");
    } else {
      sb.append(this.str_val);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("arr_val:");
    if (this.arr_val == null) {
      sb.append("null");
    } else {
      sb.append(this.arr_val);
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

  private static class TDatumValStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDatumValStandardScheme getScheme() {
      return new TDatumValStandardScheme();
    }
  }

  private static class TDatumValStandardScheme extends org.apache.thrift.scheme.StandardScheme<TDatumVal> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TDatumVal struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // INT_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.int_val = iprot.readI64();
              struct.setInt_valIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // REAL_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.real_val = iprot.readDouble();
              struct.setReal_valIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // STR_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.str_val = iprot.readString();
              struct.setStr_valIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ARR_VAL
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.arr_val = new java.util.ArrayList<TDatum>(_list0.size);
                TDatum _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new TDatum();
                  _elem1.read(iprot);
                  struct.arr_val.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setArr_valIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TDatumVal struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(INT_VAL_FIELD_DESC);
      oprot.writeI64(struct.int_val);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REAL_VAL_FIELD_DESC);
      oprot.writeDouble(struct.real_val);
      oprot.writeFieldEnd();
      if (struct.str_val != null) {
        oprot.writeFieldBegin(STR_VAL_FIELD_DESC);
        oprot.writeString(struct.str_val);
        oprot.writeFieldEnd();
      }
      if (struct.arr_val != null) {
        oprot.writeFieldBegin(ARR_VAL_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.arr_val.size()));
          for (TDatum _iter3 : struct.arr_val)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TDatumValTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TDatumValTupleScheme getScheme() {
      return new TDatumValTupleScheme();
    }
  }

  private static class TDatumValTupleScheme extends org.apache.thrift.scheme.TupleScheme<TDatumVal> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TDatumVal struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetInt_val()) {
        optionals.set(0);
      }
      if (struct.isSetReal_val()) {
        optionals.set(1);
      }
      if (struct.isSetStr_val()) {
        optionals.set(2);
      }
      if (struct.isSetArr_val()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetInt_val()) {
        oprot.writeI64(struct.int_val);
      }
      if (struct.isSetReal_val()) {
        oprot.writeDouble(struct.real_val);
      }
      if (struct.isSetStr_val()) {
        oprot.writeString(struct.str_val);
      }
      if (struct.isSetArr_val()) {
        {
          oprot.writeI32(struct.arr_val.size());
          for (TDatum _iter4 : struct.arr_val)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TDatumVal struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.int_val = iprot.readI64();
        struct.setInt_valIsSet(true);
      }
      if (incoming.get(1)) {
        struct.real_val = iprot.readDouble();
        struct.setReal_valIsSet(true);
      }
      if (incoming.get(2)) {
        struct.str_val = iprot.readString();
        struct.setStr_valIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.arr_val = new java.util.ArrayList<TDatum>(_list5.size);
          TDatum _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new TDatum();
            _elem6.read(iprot);
            struct.arr_val.add(_elem6);
          }
        }
        struct.setArr_valIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

