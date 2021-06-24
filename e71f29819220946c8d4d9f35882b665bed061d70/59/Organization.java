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
 * An organization in Rockset is a container for users and collections.
 */

@ApiModel(description = "An organization in Rockset is a container for users and collections.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-06-17T23:04:08.108Z")
public class Organization {
  @SerializedName("id")
  private String id = null;

  @SerializedName("created_at")
  private String createdAt = null;

  @SerializedName("display_name")
  private String displayName = null;

  @SerializedName("company_name")
  private String companyName = null;

  /**
   * pricing tier
   */
  @JsonAdapter(TierEnum.Adapter.class)
  public enum TierEnum {
    FREE("FREE"),
    
    BASIC("BASIC"),
    
    PRO("PRO"),
    
    ENTERPRISE("ENTERPRISE");

    private String value;

    TierEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TierEnum fromValue(String text) {
      for (TierEnum b : TierEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TierEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TierEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TierEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TierEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("tier")
  private TierEnum tier = null;

  @SerializedName("qcu")
  private Integer qcu = null;

  /**
   * org state
   */
  @JsonAdapter(StateEnum.Adapter.class)
  public enum StateEnum {
    ACTIVE("ACTIVE"),
    
    TRIAL("TRIAL"),
    
    TRIAL_EXPIRED("TRIALEXPIRED"),
    
    DELETED("DELETED");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StateEnum fromValue(String text) {
      for (StateEnum b : StateEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<StateEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StateEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StateEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return StateEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("state")
  private StateEnum state = null;

  public Organization id(String id) {
    this.id = id;
    return this;
  }

   /**
   * unique identifier for the organization
   * @return id
  **/

@JsonProperty("id")
@ApiModelProperty(example = "rockset", value = "unique identifier for the organization")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Organization createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * ISO-8601 date
   * @return createdAt
  **/

@JsonProperty("created_at")
@ApiModelProperty(example = "2001-08-28T00:23:41Z", value = "ISO-8601 date")
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Organization displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

   /**
   * name of the organization
   * @return displayName
  **/

@JsonProperty("display_name")
@ApiModelProperty(example = "Rockset, Inc", value = "name of the organization")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Organization companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

   /**
   * name of the company
   * @return companyName
  **/

@JsonProperty("company_name")
@ApiModelProperty(example = "Rockset, Inc", value = "name of the company")
  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Organization tier(TierEnum tier) {
    this.tier = tier;
    return this;
  }

   /**
   * pricing tier
   * @return tier
  **/

@JsonProperty("tier")
@ApiModelProperty(example = "BASIC", value = "pricing tier")
  public TierEnum getTier() {
    return tier;
  }

  public void setTier(TierEnum tier) {
    this.tier = tier;
  }

  public Organization qcu(Integer qcu) {
    this.qcu = qcu;
    return this;
  }

   /**
   * number of QCUs
   * @return qcu
  **/

@JsonProperty("qcu")
@ApiModelProperty(example = "2", value = "number of QCUs")
  public Integer getQcu() {
    return qcu;
  }

  public void setQcu(Integer qcu) {
    this.qcu = qcu;
  }

  public Organization state(StateEnum state) {
    this.state = state;
    return this;
  }

   /**
   * org state
   * @return state
  **/

@JsonProperty("state")
@ApiModelProperty(example = "ACTIVE", value = "org state")
  public StateEnum getState() {
    return state;
  }

  public void setState(StateEnum state) {
    this.state = state;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Organization organization = (Organization) o;
    return Objects.equals(this.id, organization.id) &&
        Objects.equals(this.createdAt, organization.createdAt) &&
        Objects.equals(this.displayName, organization.displayName) &&
        Objects.equals(this.companyName, organization.companyName) &&
        Objects.equals(this.tier, organization.tier) &&
        Objects.equals(this.qcu, organization.qcu) &&
        Objects.equals(this.state, organization.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, displayName, companyName, tier, qcu, state);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Organization {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    tier: ").append(toIndentedString(tier)).append("\n");
    sb.append("    qcu: ").append(toIndentedString(qcu)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

