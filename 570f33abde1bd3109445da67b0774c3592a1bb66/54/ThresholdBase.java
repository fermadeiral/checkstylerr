/*
 * Influx API Service
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * OpenAPI spec version: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.influxdb.client.domain;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.influxdb.client.domain.CheckStatusLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * ThresholdBase
 */

public class ThresholdBase {
  public static final String SERIALIZED_NAME_LEVEL = "level";
  @SerializedName(SERIALIZED_NAME_LEVEL)
  private CheckStatusLevel level = null;

  public static final String SERIALIZED_NAME_ALL_VALUES = "allValues";
  @SerializedName(SERIALIZED_NAME_ALL_VALUES)
  private Boolean allValues;

  public ThresholdBase level(CheckStatusLevel level) {
    this.level = level;
    return this;
  }

   /**
   * Get level
   * @return level
  **/
  @ApiModelProperty(value = "")
  public CheckStatusLevel getLevel() {
    return level;
  }

  public void setLevel(CheckStatusLevel level) {
    this.level = level;
  }

  public ThresholdBase allValues(Boolean allValues) {
    this.allValues = allValues;
    return this;
  }

   /**
   * If true, only alert if all values meet threshold.
   * @return allValues
  **/
  @ApiModelProperty(value = "If true, only alert if all values meet threshold.")
  public Boolean getAllValues() {
    return allValues;
  }

  public void setAllValues(Boolean allValues) {
    this.allValues = allValues;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ThresholdBase thresholdBase = (ThresholdBase) o;
    return Objects.equals(this.level, thresholdBase.level) &&
        Objects.equals(this.allValues, thresholdBase.allValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(level, allValues);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ThresholdBase {\n");
    sb.append("    level: ").append(toIndentedString(level)).append("\n");
    sb.append("    allValues: ").append(toIndentedString(allValues)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

