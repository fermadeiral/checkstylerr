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
import com.influxdb.client.domain.Label;
import com.influxdb.client.domain.NotificationEndpointBase;
import com.influxdb.client.domain.NotificationEndpointBaseLinks;
import com.influxdb.client.domain.NotificationEndpointType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * PagerDutyNotificationEndpoint
 */

public class PagerDutyNotificationEndpoint extends NotificationEndpoint {
  public static final String SERIALIZED_NAME_CLIENT_U_R_L = "clientURL";
  @SerializedName(SERIALIZED_NAME_CLIENT_U_R_L)
  private String clientURL;

  public static final String SERIALIZED_NAME_ROUTING_KEY = "routingKey";
  @SerializedName(SERIALIZED_NAME_ROUTING_KEY)
  private String routingKey;

  public PagerDutyNotificationEndpoint clientURL(String clientURL) {
    this.clientURL = clientURL;
    return this;
  }

   /**
   * Get clientURL
   * @return clientURL
  **/
  @ApiModelProperty(required = true, value = "")
  public String getClientURL() {
    return clientURL;
  }

  public void setClientURL(String clientURL) {
    this.clientURL = clientURL;
  }

  public PagerDutyNotificationEndpoint routingKey(String routingKey) {
    this.routingKey = routingKey;
    return this;
  }

   /**
   * Get routingKey
   * @return routingKey
  **/
  @ApiModelProperty(required = true, value = "")
  public String getRoutingKey() {
    return routingKey;
  }

  public void setRoutingKey(String routingKey) {
    this.routingKey = routingKey;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PagerDutyNotificationEndpoint pagerDutyNotificationEndpoint = (PagerDutyNotificationEndpoint) o;
    return Objects.equals(this.clientURL, pagerDutyNotificationEndpoint.clientURL) &&
        Objects.equals(this.routingKey, pagerDutyNotificationEndpoint.routingKey) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientURL, routingKey, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PagerDutyNotificationEndpoint {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    clientURL: ").append(toIndentedString(clientURL)).append("\n");
    sb.append("    routingKey: ").append(toIndentedString(routingKey)).append("\n");
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

