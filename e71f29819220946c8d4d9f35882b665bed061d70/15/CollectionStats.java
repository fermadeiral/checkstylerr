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
 * CollectionStats
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-06-17T23:04:08.108Z")
public class CollectionStats {
  @SerializedName("doc_count")
  private Long docCount = null;

  @SerializedName("total_size")
  private Long totalSize = null;

  @SerializedName("fill_progress")
  private Double fillProgress = null;

  @SerializedName("purged_doc_count")
  private Long purgedDocCount = null;

  @SerializedName("purged_doc_size")
  private Long purgedDocSize = null;

  @SerializedName("last_updated_ms")
  private Long lastUpdatedMs = null;

  @SerializedName("last_queried_ms")
  private Long lastQueriedMs = null;

  public CollectionStats docCount(Long docCount) {
    this.docCount = docCount;
    return this;
  }

   /**
   * number of documents in the collection
   * @return docCount
  **/

@JsonProperty("doc_count")
@ApiModelProperty(example = "2145", value = "number of documents in the collection")
  public Long getDocCount() {
    return docCount;
  }

  public void setDocCount(Long docCount) {
    this.docCount = docCount;
  }

  public CollectionStats totalSize(Long totalSize) {
    this.totalSize = totalSize;
    return this;
  }

   /**
   * total collection size in bytes
   * @return totalSize
  **/

@JsonProperty("total_size")
@ApiModelProperty(example = "123456", value = "total collection size in bytes")
  public Long getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(Long totalSize) {
    this.totalSize = totalSize;
  }

  public CollectionStats fillProgress(Double fillProgress) {
    this.fillProgress = fillProgress;
    return this;
  }

   /**
   * number between 0 and 1 that indicates progress of collection creation
   * @return fillProgress
  **/

@JsonProperty("fill_progress")
@ApiModelProperty(example = "0.6", value = "number between 0 and 1 that indicates progress of collection creation")
  public Double getFillProgress() {
    return fillProgress;
  }

  public void setFillProgress(Double fillProgress) {
    this.fillProgress = fillProgress;
  }

  public CollectionStats purgedDocCount(Long purgedDocCount) {
    this.purgedDocCount = purgedDocCount;
    return this;
  }

   /**
   * number of documents purged from the collection
   * @return purgedDocCount
  **/

@JsonProperty("purged_doc_count")
@ApiModelProperty(example = "2145", value = "number of documents purged from the collection")
  public Long getPurgedDocCount() {
    return purgedDocCount;
  }

  public void setPurgedDocCount(Long purgedDocCount) {
    this.purgedDocCount = purgedDocCount;
  }

  public CollectionStats purgedDocSize(Long purgedDocSize) {
    this.purgedDocSize = purgedDocSize;
    return this;
  }

   /**
   * total collection size in bytes purged
   * @return purgedDocSize
  **/

@JsonProperty("purged_doc_size")
@ApiModelProperty(example = "123456", value = "total collection size in bytes purged")
  public Long getPurgedDocSize() {
    return purgedDocSize;
  }

  public void setPurgedDocSize(Long purgedDocSize) {
    this.purgedDocSize = purgedDocSize;
  }

  public CollectionStats lastUpdatedMs(Long lastUpdatedMs) {
    this.lastUpdatedMs = lastUpdatedMs;
    return this;
  }

   /**
   * milliseconds since Unix epoch Jan 1, 1970
   * @return lastUpdatedMs
  **/

@JsonProperty("last_updated_ms")
@ApiModelProperty(example = "1535101094433", value = "milliseconds since Unix epoch Jan 1, 1970")
  public Long getLastUpdatedMs() {
    return lastUpdatedMs;
  }

  public void setLastUpdatedMs(Long lastUpdatedMs) {
    this.lastUpdatedMs = lastUpdatedMs;
  }

  public CollectionStats lastQueriedMs(Long lastQueriedMs) {
    this.lastQueriedMs = lastQueriedMs;
    return this;
  }

   /**
   * milliseconds since Unix epoch Jan 1, 1970
   * @return lastQueriedMs
  **/

@JsonProperty("last_queried_ms")
@ApiModelProperty(example = "1535101119334", value = "milliseconds since Unix epoch Jan 1, 1970")
  public Long getLastQueriedMs() {
    return lastQueriedMs;
  }

  public void setLastQueriedMs(Long lastQueriedMs) {
    this.lastQueriedMs = lastQueriedMs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CollectionStats collectionStats = (CollectionStats) o;
    return Objects.equals(this.docCount, collectionStats.docCount) &&
        Objects.equals(this.totalSize, collectionStats.totalSize) &&
        Objects.equals(this.fillProgress, collectionStats.fillProgress) &&
        Objects.equals(this.purgedDocCount, collectionStats.purgedDocCount) &&
        Objects.equals(this.purgedDocSize, collectionStats.purgedDocSize) &&
        Objects.equals(this.lastUpdatedMs, collectionStats.lastUpdatedMs) &&
        Objects.equals(this.lastQueriedMs, collectionStats.lastQueriedMs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(docCount, totalSize, fillProgress, purgedDocCount, purgedDocSize, lastUpdatedMs, lastQueriedMs);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CollectionStats {\n");
    
    sb.append("    docCount: ").append(toIndentedString(docCount)).append("\n");
    sb.append("    totalSize: ").append(toIndentedString(totalSize)).append("\n");
    sb.append("    fillProgress: ").append(toIndentedString(fillProgress)).append("\n");
    sb.append("    purgedDocCount: ").append(toIndentedString(purgedDocCount)).append("\n");
    sb.append("    purgedDocSize: ").append(toIndentedString(purgedDocSize)).append("\n");
    sb.append("    lastUpdatedMs: ").append(toIndentedString(lastUpdatedMs)).append("\n");
    sb.append("    lastQueriedMs: ").append(toIndentedString(lastQueriedMs)).append("\n");
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

