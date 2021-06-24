/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class TPixelTableRowResult implements org.apache.thrift.TBase<TPixelTableRowResult, TPixelTableRowResult._Fields>, java.io.Serializable, Cloneable, Comparable<TPixelTableRowResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TPixelTableRowResult");

  private static final org.apache.thrift.protocol.TField PIXEL_FIELD_DESC = new org.apache.thrift.protocol.TField("pixel", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField VEGA_TABLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("vega_table_name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TABLE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("table_id", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField ROW_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("row_id", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField ROW_SET_FIELD_DESC = new org.apache.thrift.protocol.TField("row_set", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField NONCE_FIELD_DESC = new org.apache.thrift.protocol.TField("nonce", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TPixelTableRowResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TPixelTableRowResultTupleSchemeFactory();

  public TPixel pixel; // required
  public java.lang.String vega_table_name; // required
  public java.util.List<java.lang.Long> table_id; // required
  public java.util.List<java.lang.Long> row_id; // required
  public TRowSet row_set; // required
  public java.lang.String nonce; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PIXEL((short)1, "pixel"),
    VEGA_TABLE_NAME((short)2, "vega_table_name"),
    TABLE_ID((short)3, "table_id"),
    ROW_ID((short)4, "row_id"),
    ROW_SET((short)5, "row_set"),
    NONCE((short)6, "nonce");

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
        case 1: // PIXEL
          return PIXEL;
        case 2: // VEGA_TABLE_NAME
          return VEGA_TABLE_NAME;
        case 3: // TABLE_ID
          return TABLE_ID;
        case 4: // ROW_ID
          return ROW_ID;
        case 5: // ROW_SET
          return ROW_SET;
        case 6: // NONCE
          return NONCE;
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
    tmpMap.put(_Fields.PIXEL, new org.apache.thrift.meta_data.FieldMetaData("pixel", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TPixel.class)));
    tmpMap.put(_Fields.VEGA_TABLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("vega_table_name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLE_ID, new org.apache.thrift.meta_data.FieldMetaData("table_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.ROW_ID, new org.apache.thrift.meta_data.FieldMetaData("row_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.ROW_SET, new org.apache.thrift.meta_data.FieldMetaData("row_set", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TRowSet.class)));
    tmpMap.put(_Fields.NONCE, new org.apache.thrift.meta_data.FieldMetaData("nonce", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TPixelTableRowResult.class, metaDataMap);
  }

  public TPixelTableRowResult() {
  }

  public TPixelTableRowResult(
    TPixel pixel,
    java.lang.String vega_table_name,
    java.util.List<java.lang.Long> table_id,
    java.util.List<java.lang.Long> row_id,
    TRowSet row_set,
    java.lang.String nonce)
  {
    this();
    this.pixel = pixel;
    this.vega_table_name = vega_table_name;
    this.table_id = table_id;
    this.row_id = row_id;
    this.row_set = row_set;
    this.nonce = nonce;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TPixelTableRowResult(TPixelTableRowResult other) {
    if (other.isSetPixel()) {
      this.pixel = new TPixel(other.pixel);
    }
    if (other.isSetVega_table_name()) {
      this.vega_table_name = other.vega_table_name;
    }
    if (other.isSetTable_id()) {
      java.util.List<java.lang.Long> __this__table_id = new java.util.ArrayList<java.lang.Long>(other.table_id);
      this.table_id = __this__table_id;
    }
    if (other.isSetRow_id()) {
      java.util.List<java.lang.Long> __this__row_id = new java.util.ArrayList<java.lang.Long>(other.row_id);
      this.row_id = __this__row_id;
    }
    if (other.isSetRow_set()) {
      this.row_set = new TRowSet(other.row_set);
    }
    if (other.isSetNonce()) {
      this.nonce = other.nonce;
    }
  }

  public TPixelTableRowResult deepCopy() {
    return new TPixelTableRowResult(this);
  }

  @Override
  public void clear() {
    this.pixel = null;
    this.vega_table_name = null;
    this.table_id = null;
    this.row_id = null;
    this.row_set = null;
    this.nonce = null;
  }

  public TPixel getPixel() {
    return this.pixel;
  }

  public TPixelTableRowResult setPixel(TPixel pixel) {
    this.pixel = pixel;
    return this;
  }

  public void unsetPixel() {
    this.pixel = null;
  }

  /** Returns true if field pixel is set (has been assigned a value) and false otherwise */
  public boolean isSetPixel() {
    return this.pixel != null;
  }

  public void setPixelIsSet(boolean value) {
    if (!value) {
      this.pixel = null;
    }
  }

  public java.lang.String getVega_table_name() {
    return this.vega_table_name;
  }

  public TPixelTableRowResult setVega_table_name(java.lang.String vega_table_name) {
    this.vega_table_name = vega_table_name;
    return this;
  }

  public void unsetVega_table_name() {
    this.vega_table_name = null;
  }

  /** Returns true if field vega_table_name is set (has been assigned a value) and false otherwise */
  public boolean isSetVega_table_name() {
    return this.vega_table_name != null;
  }

  public void setVega_table_nameIsSet(boolean value) {
    if (!value) {
      this.vega_table_name = null;
    }
  }

  public int getTable_idSize() {
    return (this.table_id == null) ? 0 : this.table_id.size();
  }

  public java.util.Iterator<java.lang.Long> getTable_idIterator() {
    return (this.table_id == null) ? null : this.table_id.iterator();
  }

  public void addToTable_id(long elem) {
    if (this.table_id == null) {
      this.table_id = new java.util.ArrayList<java.lang.Long>();
    }
    this.table_id.add(elem);
  }

  public java.util.List<java.lang.Long> getTable_id() {
    return this.table_id;
  }

  public TPixelTableRowResult setTable_id(java.util.List<java.lang.Long> table_id) {
    this.table_id = table_id;
    return this;
  }

  public void unsetTable_id() {
    this.table_id = null;
  }

  /** Returns true if field table_id is set (has been assigned a value) and false otherwise */
  public boolean isSetTable_id() {
    return this.table_id != null;
  }

  public void setTable_idIsSet(boolean value) {
    if (!value) {
      this.table_id = null;
    }
  }

  public int getRow_idSize() {
    return (this.row_id == null) ? 0 : this.row_id.size();
  }

  public java.util.Iterator<java.lang.Long> getRow_idIterator() {
    return (this.row_id == null) ? null : this.row_id.iterator();
  }

  public void addToRow_id(long elem) {
    if (this.row_id == null) {
      this.row_id = new java.util.ArrayList<java.lang.Long>();
    }
    this.row_id.add(elem);
  }

  public java.util.List<java.lang.Long> getRow_id() {
    return this.row_id;
  }

  public TPixelTableRowResult setRow_id(java.util.List<java.lang.Long> row_id) {
    this.row_id = row_id;
    return this;
  }

  public void unsetRow_id() {
    this.row_id = null;
  }

  /** Returns true if field row_id is set (has been assigned a value) and false otherwise */
  public boolean isSetRow_id() {
    return this.row_id != null;
  }

  public void setRow_idIsSet(boolean value) {
    if (!value) {
      this.row_id = null;
    }
  }

  public TRowSet getRow_set() {
    return this.row_set;
  }

  public TPixelTableRowResult setRow_set(TRowSet row_set) {
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

  public java.lang.String getNonce() {
    return this.nonce;
  }

  public TPixelTableRowResult setNonce(java.lang.String nonce) {
    this.nonce = nonce;
    return this;
  }

  public void unsetNonce() {
    this.nonce = null;
  }

  /** Returns true if field nonce is set (has been assigned a value) and false otherwise */
  public boolean isSetNonce() {
    return this.nonce != null;
  }

  public void setNonceIsSet(boolean value) {
    if (!value) {
      this.nonce = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PIXEL:
      if (value == null) {
        unsetPixel();
      } else {
        setPixel((TPixel)value);
      }
      break;

    case VEGA_TABLE_NAME:
      if (value == null) {
        unsetVega_table_name();
      } else {
        setVega_table_name((java.lang.String)value);
      }
      break;

    case TABLE_ID:
      if (value == null) {
        unsetTable_id();
      } else {
        setTable_id((java.util.List<java.lang.Long>)value);
      }
      break;

    case ROW_ID:
      if (value == null) {
        unsetRow_id();
      } else {
        setRow_id((java.util.List<java.lang.Long>)value);
      }
      break;

    case ROW_SET:
      if (value == null) {
        unsetRow_set();
      } else {
        setRow_set((TRowSet)value);
      }
      break;

    case NONCE:
      if (value == null) {
        unsetNonce();
      } else {
        setNonce((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PIXEL:
      return getPixel();

    case VEGA_TABLE_NAME:
      return getVega_table_name();

    case TABLE_ID:
      return getTable_id();

    case ROW_ID:
      return getRow_id();

    case ROW_SET:
      return getRow_set();

    case NONCE:
      return getNonce();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PIXEL:
      return isSetPixel();
    case VEGA_TABLE_NAME:
      return isSetVega_table_name();
    case TABLE_ID:
      return isSetTable_id();
    case ROW_ID:
      return isSetRow_id();
    case ROW_SET:
      return isSetRow_set();
    case NONCE:
      return isSetNonce();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TPixelTableRowResult)
      return this.equals((TPixelTableRowResult)that);
    return false;
  }

  public boolean equals(TPixelTableRowResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pixel = true && this.isSetPixel();
    boolean that_present_pixel = true && that.isSetPixel();
    if (this_present_pixel || that_present_pixel) {
      if (!(this_present_pixel && that_present_pixel))
        return false;
      if (!this.pixel.equals(that.pixel))
        return false;
    }

    boolean this_present_vega_table_name = true && this.isSetVega_table_name();
    boolean that_present_vega_table_name = true && that.isSetVega_table_name();
    if (this_present_vega_table_name || that_present_vega_table_name) {
      if (!(this_present_vega_table_name && that_present_vega_table_name))
        return false;
      if (!this.vega_table_name.equals(that.vega_table_name))
        return false;
    }

    boolean this_present_table_id = true && this.isSetTable_id();
    boolean that_present_table_id = true && that.isSetTable_id();
    if (this_present_table_id || that_present_table_id) {
      if (!(this_present_table_id && that_present_table_id))
        return false;
      if (!this.table_id.equals(that.table_id))
        return false;
    }

    boolean this_present_row_id = true && this.isSetRow_id();
    boolean that_present_row_id = true && that.isSetRow_id();
    if (this_present_row_id || that_present_row_id) {
      if (!(this_present_row_id && that_present_row_id))
        return false;
      if (!this.row_id.equals(that.row_id))
        return false;
    }

    boolean this_present_row_set = true && this.isSetRow_set();
    boolean that_present_row_set = true && that.isSetRow_set();
    if (this_present_row_set || that_present_row_set) {
      if (!(this_present_row_set && that_present_row_set))
        return false;
      if (!this.row_set.equals(that.row_set))
        return false;
    }

    boolean this_present_nonce = true && this.isSetNonce();
    boolean that_present_nonce = true && that.isSetNonce();
    if (this_present_nonce || that_present_nonce) {
      if (!(this_present_nonce && that_present_nonce))
        return false;
      if (!this.nonce.equals(that.nonce))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPixel()) ? 131071 : 524287);
    if (isSetPixel())
      hashCode = hashCode * 8191 + pixel.hashCode();

    hashCode = hashCode * 8191 + ((isSetVega_table_name()) ? 131071 : 524287);
    if (isSetVega_table_name())
      hashCode = hashCode * 8191 + vega_table_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetTable_id()) ? 131071 : 524287);
    if (isSetTable_id())
      hashCode = hashCode * 8191 + table_id.hashCode();

    hashCode = hashCode * 8191 + ((isSetRow_id()) ? 131071 : 524287);
    if (isSetRow_id())
      hashCode = hashCode * 8191 + row_id.hashCode();

    hashCode = hashCode * 8191 + ((isSetRow_set()) ? 131071 : 524287);
    if (isSetRow_set())
      hashCode = hashCode * 8191 + row_set.hashCode();

    hashCode = hashCode * 8191 + ((isSetNonce()) ? 131071 : 524287);
    if (isSetNonce())
      hashCode = hashCode * 8191 + nonce.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TPixelTableRowResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPixel()).compareTo(other.isSetPixel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPixel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pixel, other.pixel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetVega_table_name()).compareTo(other.isSetVega_table_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVega_table_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vega_table_name, other.vega_table_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTable_id()).compareTo(other.isSetTable_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTable_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.table_id, other.table_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRow_id()).compareTo(other.isSetRow_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRow_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.row_id, other.row_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = java.lang.Boolean.valueOf(isSetNonce()).compareTo(other.isSetNonce());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNonce()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nonce, other.nonce);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TPixelTableRowResult(");
    boolean first = true;

    sb.append("pixel:");
    if (this.pixel == null) {
      sb.append("null");
    } else {
      sb.append(this.pixel);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("vega_table_name:");
    if (this.vega_table_name == null) {
      sb.append("null");
    } else {
      sb.append(this.vega_table_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("table_id:");
    if (this.table_id == null) {
      sb.append("null");
    } else {
      sb.append(this.table_id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("row_id:");
    if (this.row_id == null) {
      sb.append("null");
    } else {
      sb.append(this.row_id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("row_set:");
    if (this.row_set == null) {
      sb.append("null");
    } else {
      sb.append(this.row_set);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("nonce:");
    if (this.nonce == null) {
      sb.append("null");
    } else {
      sb.append(this.nonce);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (pixel != null) {
      pixel.validate();
    }
    if (row_set != null) {
      row_set.validate();
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

  private static class TPixelTableRowResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TPixelTableRowResultStandardScheme getScheme() {
      return new TPixelTableRowResultStandardScheme();
    }
  }

  private static class TPixelTableRowResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<TPixelTableRowResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TPixelTableRowResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PIXEL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pixel = new TPixel();
              struct.pixel.read(iprot);
              struct.setPixelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VEGA_TABLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.vega_table_name = iprot.readString();
              struct.setVega_table_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TABLE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list96 = iprot.readListBegin();
                struct.table_id = new java.util.ArrayList<java.lang.Long>(_list96.size);
                long _elem97;
                for (int _i98 = 0; _i98 < _list96.size; ++_i98)
                {
                  _elem97 = iprot.readI64();
                  struct.table_id.add(_elem97);
                }
                iprot.readListEnd();
              }
              struct.setTable_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ROW_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list99 = iprot.readListBegin();
                struct.row_id = new java.util.ArrayList<java.lang.Long>(_list99.size);
                long _elem100;
                for (int _i101 = 0; _i101 < _list99.size; ++_i101)
                {
                  _elem100 = iprot.readI64();
                  struct.row_id.add(_elem100);
                }
                iprot.readListEnd();
              }
              struct.setRow_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ROW_SET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.row_set = new TRowSet();
              struct.row_set.read(iprot);
              struct.setRow_setIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // NONCE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.nonce = iprot.readString();
              struct.setNonceIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TPixelTableRowResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pixel != null) {
        oprot.writeFieldBegin(PIXEL_FIELD_DESC);
        struct.pixel.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.vega_table_name != null) {
        oprot.writeFieldBegin(VEGA_TABLE_NAME_FIELD_DESC);
        oprot.writeString(struct.vega_table_name);
        oprot.writeFieldEnd();
      }
      if (struct.table_id != null) {
        oprot.writeFieldBegin(TABLE_ID_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.table_id.size()));
          for (long _iter102 : struct.table_id)
          {
            oprot.writeI64(_iter102);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.row_id != null) {
        oprot.writeFieldBegin(ROW_ID_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.row_id.size()));
          for (long _iter103 : struct.row_id)
          {
            oprot.writeI64(_iter103);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.row_set != null) {
        oprot.writeFieldBegin(ROW_SET_FIELD_DESC);
        struct.row_set.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.nonce != null) {
        oprot.writeFieldBegin(NONCE_FIELD_DESC);
        oprot.writeString(struct.nonce);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TPixelTableRowResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TPixelTableRowResultTupleScheme getScheme() {
      return new TPixelTableRowResultTupleScheme();
    }
  }

  private static class TPixelTableRowResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<TPixelTableRowResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TPixelTableRowResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPixel()) {
        optionals.set(0);
      }
      if (struct.isSetVega_table_name()) {
        optionals.set(1);
      }
      if (struct.isSetTable_id()) {
        optionals.set(2);
      }
      if (struct.isSetRow_id()) {
        optionals.set(3);
      }
      if (struct.isSetRow_set()) {
        optionals.set(4);
      }
      if (struct.isSetNonce()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetPixel()) {
        struct.pixel.write(oprot);
      }
      if (struct.isSetVega_table_name()) {
        oprot.writeString(struct.vega_table_name);
      }
      if (struct.isSetTable_id()) {
        {
          oprot.writeI32(struct.table_id.size());
          for (long _iter104 : struct.table_id)
          {
            oprot.writeI64(_iter104);
          }
        }
      }
      if (struct.isSetRow_id()) {
        {
          oprot.writeI32(struct.row_id.size());
          for (long _iter105 : struct.row_id)
          {
            oprot.writeI64(_iter105);
          }
        }
      }
      if (struct.isSetRow_set()) {
        struct.row_set.write(oprot);
      }
      if (struct.isSetNonce()) {
        oprot.writeString(struct.nonce);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TPixelTableRowResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.pixel = new TPixel();
        struct.pixel.read(iprot);
        struct.setPixelIsSet(true);
      }
      if (incoming.get(1)) {
        struct.vega_table_name = iprot.readString();
        struct.setVega_table_nameIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list106 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.table_id = new java.util.ArrayList<java.lang.Long>(_list106.size);
          long _elem107;
          for (int _i108 = 0; _i108 < _list106.size; ++_i108)
          {
            _elem107 = iprot.readI64();
            struct.table_id.add(_elem107);
          }
        }
        struct.setTable_idIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list109 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.row_id = new java.util.ArrayList<java.lang.Long>(_list109.size);
          long _elem110;
          for (int _i111 = 0; _i111 < _list109.size; ++_i111)
          {
            _elem110 = iprot.readI64();
            struct.row_id.add(_elem110);
          }
        }
        struct.setRow_idIsSet(true);
      }
      if (incoming.get(4)) {
        struct.row_set = new TRowSet();
        struct.row_set.read(iprot);
        struct.setRow_setIsSet(true);
      }
      if (incoming.get(5)) {
        struct.nonce = iprot.readString();
        struct.setNonceIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

