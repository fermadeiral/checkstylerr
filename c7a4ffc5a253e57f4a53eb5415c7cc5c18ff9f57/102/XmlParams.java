/*
 * REST API
 * Rockset's REST API allows for creating and managing all resources in Rockset. Each supported endpoint is documented below.  All requests must be authorized with a Rockset API key, which can be created in the [Rockset console](https://console.rockset.com). The API key must be provided as `ApiKey <api_key>` in the `Authorization` request header. For example: ``` Authorization: ApiKey aB35kDjg93J5nsf4GjwMeErAVd832F7ad4vhsW1S02kfZiab42sTsfW5Sxt25asT ```  All endpoints are only accessible via https.  Build something awesome!
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.rockset.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * XmlParams
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-01-21T23:08:54.250Z")
public class XmlParams {
  @SerializedName("root_tag")
  private String rootTag = null;

  @SerializedName("encoding")
  private String encoding = null;

  @SerializedName("doc_tag")
  private String docTag = null;

  @SerializedName("value_tag")
  private String valueTag = null;

  @SerializedName("attribute_prefix")
  private String attributePrefix = null;

  public XmlParams rootTag(String rootTag) {
    this.rootTag = rootTag;
    return this;
  }

   /**
   * tag until which xml is ignored
   * @return rootTag
  **/

@JsonProperty("root_tag")
@ApiModelProperty(example = "root", value = "tag until which xml is ignored")
  public String getRootTag() {
    return rootTag;
  }

  public void setRootTag(String rootTag) {
    this.rootTag = rootTag;
  }

  public XmlParams encoding(String encoding) {
    this.encoding = encoding;
    return this;
  }

   /**
   * encoding in which data source is encoded
   * @return encoding
  **/

@JsonProperty("encoding")
@ApiModelProperty(example = "UTF-8", value = "encoding in which data source is encoded")
  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public XmlParams docTag(String docTag) {
    this.docTag = docTag;
    return this;
  }

   /**
   * tags with which documents are identified
   * @return docTag
  **/

@JsonProperty("doc_tag")
@ApiModelProperty(example = "row", value = "tags with which documents are identified")
  public String getDocTag() {
    return docTag;
  }

  public void setDocTag(String docTag) {
    this.docTag = docTag;
  }

  public XmlParams valueTag(String valueTag) {
    this.valueTag = valueTag;
    return this;
  }

   /**
   * tag used for the value when there are attributes in the element having no child
   * @return valueTag
  **/

@JsonProperty("value_tag")
@ApiModelProperty(example = "value", value = "tag used for the value when there are attributes in the element having no child")
  public String getValueTag() {
    return valueTag;
  }

  public void setValueTag(String valueTag) {
    this.valueTag = valueTag;
  }

  public XmlParams attributePrefix(String attributePrefix) {
    this.attributePrefix = attributePrefix;
    return this;
  }

   /**
   * tag to differentiate between attributes and elements
   * @return attributePrefix
  **/

@JsonProperty("attribute_prefix")
@ApiModelProperty(example = "_attr", value = "tag to differentiate between attributes and elements")
  public String getAttributePrefix() {
    return attributePrefix;
  }

  public void setAttributePrefix(String attributePrefix) {
    this.attributePrefix = attributePrefix;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XmlParams xmlParams = (XmlParams) o;
    return Objects.equals(this.rootTag, xmlParams.rootTag) &&
        Objects.equals(this.encoding, xmlParams.encoding) &&
        Objects.equals(this.docTag, xmlParams.docTag) &&
        Objects.equals(this.valueTag, xmlParams.valueTag) &&
        Objects.equals(this.attributePrefix, xmlParams.attributePrefix);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rootTag, encoding, docTag, valueTag, attributePrefix);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class XmlParams {\n");
    
    sb.append("    rootTag: ").append(toIndentedString(rootTag)).append("\n");
    sb.append("    encoding: ").append(toIndentedString(encoding)).append("\n");
    sb.append("    docTag: ").append(toIndentedString(docTag)).append("\n");
    sb.append("    valueTag: ").append(toIndentedString(valueTag)).append("\n");
    sb.append("    attributePrefix: ").append(toIndentedString(attributePrefix)).append("\n");
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

