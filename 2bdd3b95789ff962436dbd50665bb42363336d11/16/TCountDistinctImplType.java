/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.omnisci.thrift.server;


public enum TCountDistinctImplType implements org.apache.thrift.TEnum {
  Invalid(0),
  Bitmap(1),
  StdSet(2);

  private final int value;

  private TCountDistinctImplType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  @org.apache.thrift.annotation.Nullable
  public static TCountDistinctImplType findByValue(int value) { 
    switch (value) {
      case 0:
        return Invalid;
      case 1:
        return Bitmap;
      case 2:
        return StdSet;
      default:
        return null;
    }
  }
}
