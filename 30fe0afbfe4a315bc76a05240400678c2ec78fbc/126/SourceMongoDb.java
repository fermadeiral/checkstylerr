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
import com.rockset.client.model.StatusMongoDb;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SourceMongoDb
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-25T08:10:23.542Z")
public class SourceMongoDb {
  @SerializedName("database_name")
  private String databaseName = null;

  @SerializedName("collection_name")
  private String collectionName = null;

  @SerializedName("status")
  private StatusMongoDb status = null;

  public SourceMongoDb databaseName(String databaseName) {
    this.databaseName = databaseName;
    return this;
  }

   /**
   * MongoDB database name containing this collection
   * @return databaseName
  **/

@JsonProperty("database_name")
@ApiModelProperty(example = "my_database", required = true, value = "MongoDB database name containing this collection")
  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public SourceMongoDb collectionName(String collectionName) {
    this.collectionName = collectionName;
    return this;
  }

   /**
   * MongoDB collection name
   * @return collectionName
  **/

@JsonProperty("collection_name")
@ApiModelProperty(example = "my_collection", required = true, value = "MongoDB collection name")
  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

   /**
   * MongoDB source status
   * @return status
  **/

@JsonProperty("status")
@ApiModelProperty(value = "MongoDB source status")
  public StatusMongoDb getStatus() {
    return status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SourceMongoDb sourceMongoDb = (SourceMongoDb) o;
    return Objects.equals(this.databaseName, sourceMongoDb.databaseName) &&
        Objects.equals(this.collectionName, sourceMongoDb.collectionName) &&
        Objects.equals(this.status, sourceMongoDb.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(databaseName, collectionName, status);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SourceMongoDb {\n");
    
    sb.append("    databaseName: ").append(toIndentedString(databaseName)).append("\n");
    sb.append("    collectionName: ").append(toIndentedString(collectionName)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

