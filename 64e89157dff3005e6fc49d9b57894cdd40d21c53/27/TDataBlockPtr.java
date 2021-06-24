/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TDataBlockPtr extends org.apache.thrift.TUnion<TDataBlockPtr, TDataBlockPtr._Fields> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TDataBlockPtr");
  private static final org.apache.thrift.protocol.TField FIXED_LEN_DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("fixed_len_data", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField VAR_LEN_DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("var_len_data", org.apache.thrift.protocol.TType.LIST, (short)2);

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FIXED_LEN_DATA((short)1, "fixed_len_data"),
    VAR_LEN_DATA((short)2, "var_len_data");

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
        case 1: // FIXED_LEN_DATA
          return FIXED_LEN_DATA;
        case 2: // VAR_LEN_DATA
          return VAR_LEN_DATA;
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

  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FIXED_LEN_DATA, new org.apache.thrift.meta_data.FieldMetaData("fixed_len_data", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.VAR_LEN_DATA, new org.apache.thrift.meta_data.FieldMetaData("var_len_data", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TVarLen.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TDataBlockPtr.class, metaDataMap);
  }

  public TDataBlockPtr() {
    super();
  }

  public TDataBlockPtr(_Fields setField, java.lang.Object value) {
    super(setField, value);
  }

  public TDataBlockPtr(TDataBlockPtr other) {
    super(other);
  }
  public TDataBlockPtr deepCopy() {
    return new TDataBlockPtr(this);
  }

  public static TDataBlockPtr fixed_len_data(java.nio.ByteBuffer value) {
    TDataBlockPtr x = new TDataBlockPtr();
    x.setFixed_len_data(value);
    return x;
  }

  public static TDataBlockPtr fixed_len_data(byte[] value) {
    TDataBlockPtr x = new TDataBlockPtr();
    x.setFixed_len_data(java.nio.ByteBuffer.wrap(value.clone()));
    return x;
  }

  public static TDataBlockPtr var_len_data(java.util.List<TVarLen> value) {
    TDataBlockPtr x = new TDataBlockPtr();
    x.setVar_len_data(value);
    return x;
  }


  @Override
  protected void checkType(_Fields setField, java.lang.Object value) throws java.lang.ClassCastException {
    switch (setField) {
      case FIXED_LEN_DATA:
        if (value instanceof java.nio.ByteBuffer) {
          break;
        }
        throw new java.lang.ClassCastException("Was expecting value of type java.nio.ByteBuffer for field 'fixed_len_data', but got " + value.getClass().getSimpleName());
      case VAR_LEN_DATA:
        if (value instanceof java.util.List) {
          break;
        }
        throw new java.lang.ClassCastException("Was expecting value of type java.util.List<TVarLen> for field 'var_len_data', but got " + value.getClass().getSimpleName());
      default:
        throw new java.lang.IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected java.lang.Object standardSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TField field) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(field.id);
    if (setField != null) {
      switch (setField) {
        case FIXED_LEN_DATA:
          if (field.type == FIXED_LEN_DATA_FIELD_DESC.type) {
            java.nio.ByteBuffer fixed_len_data;
            fixed_len_data = iprot.readBinary();
            return fixed_len_data;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        case VAR_LEN_DATA:
          if (field.type == VAR_LEN_DATA_FIELD_DESC.type) {
            java.util.List<TVarLen> var_len_data;
            {
              org.apache.thrift.protocol.TList _list192 = iprot.readListBegin();
              var_len_data = new java.util.ArrayList<TVarLen>(_list192.size);
              TVarLen _elem193;
              for (int _i194 = 0; _i194 < _list192.size; ++_i194)
              {
                _elem193 = new TVarLen();
                _elem193.read(iprot);
                var_len_data.add(_elem193);
              }
              iprot.readListEnd();
            }
            return var_len_data;
          } else {
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
            return null;
          }
        default:
          throw new java.lang.IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      return null;
    }
  }

  @Override
  protected void standardSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case FIXED_LEN_DATA:
        java.nio.ByteBuffer fixed_len_data = (java.nio.ByteBuffer)value_;
        oprot.writeBinary(fixed_len_data);
        return;
      case VAR_LEN_DATA:
        java.util.List<TVarLen> var_len_data = (java.util.List<TVarLen>)value_;
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, var_len_data.size()));
          for (TVarLen _iter195 : var_len_data)
          {
            _iter195.write(oprot);
          }
          oprot.writeListEnd();
        }
        return;
      default:
        throw new java.lang.IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected java.lang.Object tupleSchemeReadValue(org.apache.thrift.protocol.TProtocol iprot, short fieldID) throws org.apache.thrift.TException {
    _Fields setField = _Fields.findByThriftId(fieldID);
    if (setField != null) {
      switch (setField) {
        case FIXED_LEN_DATA:
          java.nio.ByteBuffer fixed_len_data;
          fixed_len_data = iprot.readBinary();
          return fixed_len_data;
        case VAR_LEN_DATA:
          java.util.List<TVarLen> var_len_data;
          {
            org.apache.thrift.protocol.TList _list196 = iprot.readListBegin();
            var_len_data = new java.util.ArrayList<TVarLen>(_list196.size);
            TVarLen _elem197;
            for (int _i198 = 0; _i198 < _list196.size; ++_i198)
            {
              _elem197 = new TVarLen();
              _elem197.read(iprot);
              var_len_data.add(_elem197);
            }
            iprot.readListEnd();
          }
          return var_len_data;
        default:
          throw new java.lang.IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      }
    } else {
      throw new org.apache.thrift.protocol.TProtocolException("Couldn't find a field with field id " + fieldID);
    }
  }

  @Override
  protected void tupleSchemeWriteValue(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    switch (setField_) {
      case FIXED_LEN_DATA:
        java.nio.ByteBuffer fixed_len_data = (java.nio.ByteBuffer)value_;
        oprot.writeBinary(fixed_len_data);
        return;
      case VAR_LEN_DATA:
        java.util.List<TVarLen> var_len_data = (java.util.List<TVarLen>)value_;
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, var_len_data.size()));
          for (TVarLen _iter199 : var_len_data)
          {
            _iter199.write(oprot);
          }
          oprot.writeListEnd();
        }
        return;
      default:
        throw new java.lang.IllegalStateException("Cannot write union with unknown field " + setField_);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TField getFieldDesc(_Fields setField) {
    switch (setField) {
      case FIXED_LEN_DATA:
        return FIXED_LEN_DATA_FIELD_DESC;
      case VAR_LEN_DATA:
        return VAR_LEN_DATA_FIELD_DESC;
      default:
        throw new java.lang.IllegalArgumentException("Unknown field id " + setField);
    }
  }

  @Override
  protected org.apache.thrift.protocol.TStruct getStructDesc() {
    return STRUCT_DESC;
  }

  @Override
  protected _Fields enumForId(short id) {
    return _Fields.findByThriftIdOrThrow(id);
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public byte[] getFixed_len_data() {
    setFixed_len_data(org.apache.thrift.TBaseHelper.rightSize(bufferForFixed_len_data()));
    java.nio.ByteBuffer b = bufferForFixed_len_data();
    return b == null ? null : b.array();
  }

  public java.nio.ByteBuffer bufferForFixed_len_data() {
    if (getSetField() == _Fields.FIXED_LEN_DATA) {
      return org.apache.thrift.TBaseHelper.copyBinary((java.nio.ByteBuffer)getFieldValue());
    } else {
      throw new java.lang.RuntimeException("Cannot get field 'fixed_len_data' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setFixed_len_data(byte[] value) {
    setFixed_len_data(java.nio.ByteBuffer.wrap(value.clone()));
  }

  public void setFixed_len_data(java.nio.ByteBuffer value) {
    if (value == null) throw new java.lang.NullPointerException();
    setField_ = _Fields.FIXED_LEN_DATA;
    value_ = value;
  }

  public java.util.List<TVarLen> getVar_len_data() {
    if (getSetField() == _Fields.VAR_LEN_DATA) {
      return (java.util.List<TVarLen>)getFieldValue();
    } else {
      throw new java.lang.RuntimeException("Cannot get field 'var_len_data' because union is currently set to " + getFieldDesc(getSetField()).name);
    }
  }

  public void setVar_len_data(java.util.List<TVarLen> value) {
    if (value == null) throw new java.lang.NullPointerException();
    setField_ = _Fields.VAR_LEN_DATA;
    value_ = value;
  }

  public boolean isSetFixed_len_data() {
    return setField_ == _Fields.FIXED_LEN_DATA;
  }


  public boolean isSetVar_len_data() {
    return setField_ == _Fields.VAR_LEN_DATA;
  }


  public boolean equals(java.lang.Object other) {
    if (other instanceof TDataBlockPtr) {
      return equals((TDataBlockPtr)other);
    } else {
      return false;
    }
  }

  public boolean equals(TDataBlockPtr other) {
    return other != null && getSetField() == other.getSetField() && getFieldValue().equals(other.getFieldValue());
  }

  @Override
  public int compareTo(TDataBlockPtr other) {
    int lastComparison = org.apache.thrift.TBaseHelper.compareTo(getSetField(), other.getSetField());
    if (lastComparison == 0) {
      return org.apache.thrift.TBaseHelper.compareTo(getFieldValue(), other.getFieldValue());
    }
    return lastComparison;
  }


  @Override
  public int hashCode() {
    java.util.List<java.lang.Object> list = new java.util.ArrayList<java.lang.Object>();
    list.add(this.getClass().getName());
    org.apache.thrift.TFieldIdEnum setField = getSetField();
    if (setField != null) {
      list.add(setField.getThriftFieldId());
      java.lang.Object value = getFieldValue();
      if (value instanceof org.apache.thrift.TEnum) {
        list.add(((org.apache.thrift.TEnum)getFieldValue()).getValue());
      } else {
        list.add(value);
      }
    }
    return list.hashCode();
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


}
