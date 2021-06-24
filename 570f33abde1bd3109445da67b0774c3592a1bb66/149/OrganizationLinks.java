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
 * OrganizationLinks
 */

public class OrganizationLinks {
  public static final String SERIALIZED_NAME_SELF = "self";
  @SerializedName(SERIALIZED_NAME_SELF)
  private String self;

  public static final String SERIALIZED_NAME_MEMBERS = "members";
  @SerializedName(SERIALIZED_NAME_MEMBERS)
  private String members;

  public static final String SERIALIZED_NAME_OWNERS = "owners";
  @SerializedName(SERIALIZED_NAME_OWNERS)
  private String owners;

  public static final String SERIALIZED_NAME_LABELS = "labels";
  @SerializedName(SERIALIZED_NAME_LABELS)
  private String labels;

  public static final String SERIALIZED_NAME_SECRETS = "secrets";
  @SerializedName(SERIALIZED_NAME_SECRETS)
  private String secrets;

  public static final String SERIALIZED_NAME_BUCKETS = "buckets";
  @SerializedName(SERIALIZED_NAME_BUCKETS)
  private String buckets;

  public static final String SERIALIZED_NAME_TASKS = "tasks";
  @SerializedName(SERIALIZED_NAME_TASKS)
  private String tasks;

  public static final String SERIALIZED_NAME_DASHBOARDS = "dashboards";
  @SerializedName(SERIALIZED_NAME_DASHBOARDS)
  private String dashboards;

   /**
   * URI of resource.
   * @return self
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getSelf() {
    return self;
  }

   /**
   * URI of resource.
   * @return members
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getMembers() {
    return members;
  }

   /**
   * URI of resource.
   * @return owners
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getOwners() {
    return owners;
  }

   /**
   * URI of resource.
   * @return labels
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getLabels() {
    return labels;
  }

   /**
   * URI of resource.
   * @return secrets
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getSecrets() {
    return secrets;
  }

   /**
   * URI of resource.
   * @return buckets
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getBuckets() {
    return buckets;
  }

   /**
   * URI of resource.
   * @return tasks
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getTasks() {
    return tasks;
  }

   /**
   * URI of resource.
   * @return dashboards
  **/
  @ApiModelProperty(value = "URI of resource.")
  public String getDashboards() {
    return dashboards;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationLinks organizationLinks = (OrganizationLinks) o;
    return Objects.equals(this.self, organizationLinks.self) &&
        Objects.equals(this.members, organizationLinks.members) &&
        Objects.equals(this.owners, organizationLinks.owners) &&
        Objects.equals(this.labels, organizationLinks.labels) &&
        Objects.equals(this.secrets, organizationLinks.secrets) &&
        Objects.equals(this.buckets, organizationLinks.buckets) &&
        Objects.equals(this.tasks, organizationLinks.tasks) &&
        Objects.equals(this.dashboards, organizationLinks.dashboards);
  }

  @Override
  public int hashCode() {
    return Objects.hash(self, members, owners, labels, secrets, buckets, tasks, dashboards);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationLinks {\n");
    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    members: ").append(toIndentedString(members)).append("\n");
    sb.append("    owners: ").append(toIndentedString(owners)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    secrets: ").append(toIndentedString(secrets)).append("\n");
    sb.append("    buckets: ").append(toIndentedString(buckets)).append("\n");
    sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
    sb.append("    dashboards: ").append(toIndentedString(dashboards)).append("\n");
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

