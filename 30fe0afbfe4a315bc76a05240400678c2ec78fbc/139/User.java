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
import com.rockset.client.model.OrgMembership;
import com.rockset.client.model.Organization;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2021-02-25T08:10:23.542Z")
public class User {
  @SerializedName("created_at")
  private String createdAt = null;

  @SerializedName("email")
  private String email = null;

  @SerializedName("first_name")
  private String firstName = null;

  @SerializedName("last_name")
  private String lastName = null;

  @SerializedName("roles")
  private List<String> roles = null;

  @SerializedName("state")
  private String state = null;

  @SerializedName("org")
  private String org = null;

  /**
   * Gets or Sets inviteState
   */
  @JsonAdapter(InviteStateEnum.Adapter.class)
  public enum InviteStateEnum {
    PENDING("PENDING"),
    
    ACCEPTED("ACCEPTED"),
    
    EXPIRED("EXPIRED"),
    
    CANCELLED("CANCELLED");

    private String value;

    InviteStateEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static InviteStateEnum fromValue(String text) {
      for (InviteStateEnum b : InviteStateEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<InviteStateEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final InviteStateEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public InviteStateEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return InviteStateEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("invite_state")
  private InviteStateEnum inviteState = null;

  @SerializedName("orgs")
  private List<Organization> orgs = null;

  @SerializedName("org_memberships")
  private List<OrgMembership> orgMemberships = null;

  public User createdAt(String createdAt) {
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

  public User email(String email) {
    this.email = email;
    return this;
  }

   /**
   * user email
   * @return email
  **/

@JsonProperty("email")
@ApiModelProperty(example = "hello@rockset.com", required = true, value = "user email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * user first name
   * @return firstName
  **/

@JsonProperty("first_name")
@ApiModelProperty(example = "John", value = "user first name")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public User lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * user last name
   * @return lastName
  **/

@JsonProperty("last_name")
@ApiModelProperty(example = "Doe", value = "user last name")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User roles(List<String> roles) {
    this.roles = roles;
    return this;
  }

  public User addRolesItem(String rolesItem) {
    if (this.roles == null) {
      this.roles = new ArrayList<String>();
    }
    this.roles.add(rolesItem);
    return this;
  }

   /**
   * List of roles for a given user
   * @return roles
  **/

@JsonProperty("roles")
@ApiModelProperty(example = "\"[\\\"admin\\\", \\\"member\\\", \\\"read-only\\\"]\"", value = "List of roles for a given user")
  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public User state(String state) {
    this.state = state;
    return this;
  }

   /**
   * state of user - NEW / ACTIVE
   * @return state
  **/

@JsonProperty("state")
@ApiModelProperty(example = "ACTIVE", value = "state of user - NEW / ACTIVE")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public User org(String org) {
    this.org = org;
    return this;
  }

   /**
   * Get org
   * @return org
  **/

@JsonProperty("org")
@ApiModelProperty(value = "")
  public String getOrg() {
    return org;
  }

  public void setOrg(String org) {
    this.org = org;
  }

  public User inviteState(InviteStateEnum inviteState) {
    this.inviteState = inviteState;
    return this;
  }

   /**
   * Get inviteState
   * @return inviteState
  **/

@JsonProperty("invite_state")
@ApiModelProperty(value = "")
  public InviteStateEnum getInviteState() {
    return inviteState;
  }

  public void setInviteState(InviteStateEnum inviteState) {
    this.inviteState = inviteState;
  }

  public User orgs(List<Organization> orgs) {
    this.orgs = orgs;
    return this;
  }

  public User addOrgsItem(Organization orgsItem) {
    if (this.orgs == null) {
      this.orgs = new ArrayList<Organization>();
    }
    this.orgs.add(orgsItem);
    return this;
  }

   /**
   * Get orgs
   * @return orgs
  **/

@JsonProperty("orgs")
@ApiModelProperty(value = "")
  public List<Organization> getOrgs() {
    return orgs;
  }

  public void setOrgs(List<Organization> orgs) {
    this.orgs = orgs;
  }

  public User orgMemberships(List<OrgMembership> orgMemberships) {
    this.orgMemberships = orgMemberships;
    return this;
  }

  public User addOrgMembershipsItem(OrgMembership orgMembershipsItem) {
    if (this.orgMemberships == null) {
      this.orgMemberships = new ArrayList<OrgMembership>();
    }
    this.orgMemberships.add(orgMembershipsItem);
    return this;
  }

   /**
   * Get orgMemberships
   * @return orgMemberships
  **/

@JsonProperty("org_memberships")
@ApiModelProperty(value = "")
  public List<OrgMembership> getOrgMemberships() {
    return orgMemberships;
  }

  public void setOrgMemberships(List<OrgMembership> orgMemberships) {
    this.orgMemberships = orgMemberships;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.createdAt, user.createdAt) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.firstName, user.firstName) &&
        Objects.equals(this.lastName, user.lastName) &&
        Objects.equals(this.roles, user.roles) &&
        Objects.equals(this.state, user.state) &&
        Objects.equals(this.org, user.org) &&
        Objects.equals(this.inviteState, user.inviteState) &&
        Objects.equals(this.orgs, user.orgs) &&
        Objects.equals(this.orgMemberships, user.orgMemberships);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, email, firstName, lastName, roles, state, org, inviteState, orgs, orgMemberships);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    org: ").append(toIndentedString(org)).append("\n");
    sb.append("    inviteState: ").append(toIndentedString(inviteState)).append("\n");
    sb.append("    orgs: ").append(toIndentedString(orgs)).append("\n");
    sb.append("    orgMemberships: ").append(toIndentedString(orgMemberships)).append("\n");
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

