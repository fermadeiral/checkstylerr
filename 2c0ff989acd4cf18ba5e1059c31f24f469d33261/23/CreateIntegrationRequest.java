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
import com.rockset.client.model.AwsExternalIdIntegration;
import com.rockset.client.model.AwsKeyIntegration;
import com.rockset.client.model.GcpServiceAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * CreateIntegrationRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2019-03-06T00:47:37.549Z")
public class CreateIntegrationRequest {
  @SerializedName("name")
  private String name = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("aws")
  private AwsKeyIntegration aws = null;

  @SerializedName("aws_external_id")
  private AwsExternalIdIntegration awsExternalId = null;

  @SerializedName("gcp_service_account")
  private GcpServiceAccount gcpServiceAccount = null;

  public CreateIntegrationRequest name(String name) {
    this.name = name;
    return this;
  }

   /**
   * descriptive label
   * @return name
  **/
  @ApiModelProperty(example = "event-logs", required = true, value = "descriptive label")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateIntegrationRequest description(String description) {
    this.description = description;
    return this;
  }

   /**
   * longer explanation for the integration
   * @return description
  **/
  @ApiModelProperty(example = "AWS account with event data for the data science team.", value = "longer explanation for the integration")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CreateIntegrationRequest aws(AwsKeyIntegration aws) {
    this.aws = aws;
    return this;
  }

   /**
   * credentials for an AWS key integration
   * @return aws
  **/
  @ApiModelProperty(value = "credentials for an AWS key integration")
  public AwsKeyIntegration getAws() {
    return aws;
  }

  public void setAws(AwsKeyIntegration aws) {
    this.aws = aws;
  }

   /**
   * details for an AWS External Id integration
   * @return awsExternalId
  **/
  @ApiModelProperty(value = "details for an AWS External Id integration")
  public AwsExternalIdIntegration getAwsExternalId() {
    return awsExternalId;
  }

  public CreateIntegrationRequest gcpServiceAccount(GcpServiceAccount gcpServiceAccount) {
    this.gcpServiceAccount = gcpServiceAccount;
    return this;
  }

   /**
   * details of a GCP Service Account integration
   * @return gcpServiceAccount
  **/
  @ApiModelProperty(value = "details of a GCP Service Account integration")
  public GcpServiceAccount getGcpServiceAccount() {
    return gcpServiceAccount;
  }

  public void setGcpServiceAccount(GcpServiceAccount gcpServiceAccount) {
    this.gcpServiceAccount = gcpServiceAccount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateIntegrationRequest createIntegrationRequest = (CreateIntegrationRequest) o;
    return Objects.equals(this.name, createIntegrationRequest.name) &&
        Objects.equals(this.description, createIntegrationRequest.description) &&
        Objects.equals(this.aws, createIntegrationRequest.aws) &&
        Objects.equals(this.awsExternalId, createIntegrationRequest.awsExternalId) &&
        Objects.equals(this.gcpServiceAccount, createIntegrationRequest.gcpServiceAccount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, aws, awsExternalId, gcpServiceAccount);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateIntegrationRequest {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    aws: ").append(toIndentedString(aws)).append("\n");
    sb.append("    awsExternalId: ").append(toIndentedString(awsExternalId)).append("\n");
    sb.append("    gcpServiceAccount: ").append(toIndentedString(gcpServiceAccount)).append("\n");
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

