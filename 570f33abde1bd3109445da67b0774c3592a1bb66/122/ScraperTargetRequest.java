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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * ScraperTargetRequest
 */

public class ScraperTargetRequest {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  /**
   * The type of the metrics to be parsed.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    PROMETHEUS("prometheus");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private TypeEnum type = TypeEnum.PROMETHEUS;

  public static final String SERIALIZED_NAME_URL = "url";
  @SerializedName(SERIALIZED_NAME_URL)
  private String url;

  public static final String SERIALIZED_NAME_ORG_I_D = "orgID";
  @SerializedName(SERIALIZED_NAME_ORG_I_D)
  private String orgID;

  public static final String SERIALIZED_NAME_BUCKET_I_D = "bucketID";
  @SerializedName(SERIALIZED_NAME_BUCKET_I_D)
  private String bucketID;

  public ScraperTargetRequest name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name of the scraper target.
   * @return name
  **/
  @ApiModelProperty(value = "The name of the scraper target.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

   /**
   * The type of the metrics to be parsed.
   * @return type
  **/
  @ApiModelProperty(value = "The type of the metrics to be parsed.")
  public TypeEnum getType() {
    return type;
  }

  public ScraperTargetRequest url(String url) {
    this.url = url;
    return this;
  }

   /**
   * The URL of the metrics endpoint.
   * @return url
  **/
  @ApiModelProperty(example = "http://localhost:9090/metrics", value = "The URL of the metrics endpoint.")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ScraperTargetRequest orgID(String orgID) {
    this.orgID = orgID;
    return this;
  }

   /**
   * The organization ID.
   * @return orgID
  **/
  @ApiModelProperty(value = "The organization ID.")
  public String getOrgID() {
    return orgID;
  }

  public void setOrgID(String orgID) {
    this.orgID = orgID;
  }

  public ScraperTargetRequest bucketID(String bucketID) {
    this.bucketID = bucketID;
    return this;
  }

   /**
   * The ID of the bucket to write to.
   * @return bucketID
  **/
  @ApiModelProperty(value = "The ID of the bucket to write to.")
  public String getBucketID() {
    return bucketID;
  }

  public void setBucketID(String bucketID) {
    this.bucketID = bucketID;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScraperTargetRequest scraperTargetRequest = (ScraperTargetRequest) o;
    return Objects.equals(this.name, scraperTargetRequest.name) &&
        Objects.equals(this.type, scraperTargetRequest.type) &&
        Objects.equals(this.url, scraperTargetRequest.url) &&
        Objects.equals(this.orgID, scraperTargetRequest.orgID) &&
        Objects.equals(this.bucketID, scraperTargetRequest.bucketID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, url, orgID, bucketID);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScraperTargetRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    orgID: ").append(toIndentedString(orgID)).append("\n");
    sb.append("    bucketID: ").append(toIndentedString(bucketID)).append("\n");
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

