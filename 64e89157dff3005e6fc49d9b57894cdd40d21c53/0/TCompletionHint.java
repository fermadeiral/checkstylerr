/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.calciteserver;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TCompletionHint implements org.apache.thrift.TBase<TCompletionHint, TCompletionHint._Fields>, java.io.Serializable, Cloneable, Comparable<TCompletionHint> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TCompletionHint");

  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField HINTS_FIELD_DESC = new org.apache.thrift.protocol.TField("hints", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField REPLACED_FIELD_DESC = new org.apache.thrift.protocol.TField("replaced", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TCompletionHintStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TCompletionHintTupleSchemeFactory();

  /**
   * 
   * @see TCompletionHintType
   */
  public TCompletionHintType type; // required
  public java.util.List<java.lang.String> hints; // required
  public java.lang.String replaced; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see TCompletionHintType
     */
    TYPE((short)1, "type"),
    HINTS((short)2, "hints"),
    REPLACED((short)3, "replaced");

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
        case 2: // HINTS
          return HINTS;
        case 3: // REPLACED
          return REPLACED;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TCompletionHintType.class)));
    tmpMap.put(_Fields.HINTS, new org.apache.thrift.meta_data.FieldMetaData("hints", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.REPLACED, new org.apache.thrift.meta_data.FieldMetaData("replaced", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TCompletionHint.class, metaDataMap);
  }

  public TCompletionHint() {
  }

  public TCompletionHint(
    TCompletionHintType type,
    java.util.List<java.lang.String> hints,
    java.lang.String replaced)
  {
    this();
    this.type = type;
    this.hints = hints;
    this.replaced = replaced;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TCompletionHint(TCompletionHint other) {
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetHints()) {
      java.util.List<java.lang.String> __this__hints = new java.util.ArrayList<java.lang.String>(other.hints);
      this.hints = __this__hints;
    }
    if (other.isSetReplaced()) {
      this.replaced = other.replaced;
    }
  }

  public TCompletionHint deepCopy() {
    return new TCompletionHint(this);
  }

  @Override
  public void clear() {
    this.type = null;
    this.hints = null;
    this.replaced = null;
  }

  /**
   * 
   * @see TCompletionHintType
   */
  public TCompletionHintType getType() {
    return this.type;
  }

  /**
   * 
   * @see TCompletionHintType
   */
  public TCompletionHint setType(TCompletionHintType type) {
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

  public int getHintsSize() {
    return (this.hints == null) ? 0 : this.hints.size();
  }

  public java.util.Iterator<java.lang.String> getHintsIterator() {
    return (this.hints == null) ? null : this.hints.iterator();
  }

  public void addToHints(java.lang.String elem) {
    if (this.hints == null) {
      this.hints = new java.util.ArrayList<java.lang.String>();
    }
    this.hints.add(elem);
  }

  public java.util.List<java.lang.String> getHints() {
    return this.hints;
  }

  public TCompletionHint setHints(java.util.List<java.lang.String> hints) {
    this.hints = hints;
    return this;
  }

  public void unsetHints() {
    this.hints = null;
  }

  /** Returns true if field hints is set (has been assigned a value) and false otherwise */
  public boolean isSetHints() {
    return this.hints != null;
  }

  public void setHintsIsSet(boolean value) {
    if (!value) {
      this.hints = null;
    }
  }

  public java.lang.String getReplaced() {
    return this.replaced;
  }

  public TCompletionHint setReplaced(java.lang.String replaced) {
    this.replaced = replaced;
    return this;
  }

  public void unsetReplaced() {
    this.replaced = null;
  }

  /** Returns true if field replaced is set (has been assigned a value) and false otherwise */
  public boolean isSetReplaced() {
    return this.replaced != null;
  }

  public void setReplacedIsSet(boolean value) {
    if (!value) {
      this.replaced = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((TCompletionHintType)value);
      }
      break;

    case HINTS:
      if (value == null) {
        unsetHints();
      } else {
        setHints((java.util.List<java.lang.String>)value);
      }
      break;

    case REPLACED:
      if (value == null) {
        unsetReplaced();
      } else {
        setReplaced((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TYPE:
      return getType();

    case HINTS:
      return getHints();

    case REPLACED:
      return getReplaced();

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
    case HINTS:
      return isSetHints();
    case REPLACED:
      return isSetReplaced();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TCompletionHint)
      return this.equals((TCompletionHint)that);
    return false;
  }

  public boolean equals(TCompletionHint that) {
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

    boolean this_present_hints = true && this.isSetHints();
    boolean that_present_hints = true && that.isSetHints();
    if (this_present_hints || that_present_hints) {
      if (!(this_present_hints && that_present_hints))
        return false;
      if (!this.hints.equals(that.hints))
        return false;
    }

    boolean this_present_replaced = true && this.isSetReplaced();
    boolean that_present_replaced = true && that.isSetReplaced();
    if (this_present_replaced || that_present_replaced) {
      if (!(this_present_replaced && that_present_replaced))
        return false;
      if (!this.replaced.equals(that.replaced))
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

    hashCode = hashCode * 8191 + ((isSetHints()) ? 131071 : 524287);
    if (isSetHints())
      hashCode = hashCode * 8191 + hints.hashCode();

    hashCode = hashCode * 8191 + ((isSetReplaced()) ? 131071 : 524287);
    if (isSetReplaced())
      hashCode = hashCode * 8191 + replaced.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TCompletionHint other) {
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
    lastComparison = java.lang.Boolean.valueOf(isSetHints()).compareTo(other.isSetHints());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHints()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hints, other.hints);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetReplaced()).compareTo(other.isSetReplaced());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReplaced()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.replaced, other.replaced);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TCompletionHint(");
    boolean first = true;

    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("hints:");
    if (this.hints == null) {
      sb.append("null");
    } else {
      sb.append(this.hints);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("replaced:");
    if (this.replaced == null) {
      sb.append("null");
    } else {
      sb.append(this.replaced);
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

  private static class TCompletionHintStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TCompletionHintStandardScheme getScheme() {
      return new TCompletionHintStandardScheme();
    }
  }

  private static class TCompletionHintStandardScheme extends org.apache.thrift.scheme.StandardScheme<TCompletionHint> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TCompletionHint struct) throws org.apache.thrift.TException {
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
              struct.type = com.omnisci.thrift.calciteserver.TCompletionHintType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HINTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.hints = new java.util.ArrayList<java.lang.String>(_list0.size);
                java.lang.String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.hints.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setHintsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // REPLACED
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.replaced = iprot.readString();
              struct.setReplacedIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TCompletionHint struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.hints != null) {
        oprot.writeFieldBegin(HINTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.hints.size()));
          for (java.lang.String _iter3 : struct.hints)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.replaced != null) {
        oprot.writeFieldBegin(REPLACED_FIELD_DESC);
        oprot.writeString(struct.replaced);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TCompletionHintTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TCompletionHintTupleScheme getScheme() {
      return new TCompletionHintTupleScheme();
    }
  }

  private static class TCompletionHintTupleScheme extends org.apache.thrift.scheme.TupleScheme<TCompletionHint> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TCompletionHint struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetType()) {
        optionals.set(0);
      }
      if (struct.isSetHints()) {
        optionals.set(1);
      }
      if (struct.isSetReplaced()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetHints()) {
        {
          oprot.writeI32(struct.hints.size());
          for (java.lang.String _iter4 : struct.hints)
          {
            oprot.writeString(_iter4);
          }
        }
      }
      if (struct.isSetReplaced()) {
        oprot.writeString(struct.replaced);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TCompletionHint struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.type = com.omnisci.thrift.calciteserver.TCompletionHintType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.hints = new java.util.ArrayList<java.lang.String>(_list5.size);
          java.lang.String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.hints.add(_elem6);
          }
        }
        struct.setHintsIsSet(true);
      }
      if (incoming.get(2)) {
        struct.replaced = iprot.readString();
        struct.setReplacedIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

