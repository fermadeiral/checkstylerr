/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.qa.utils;

import org.ldaptive.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

public class LdapUtilsIT {
    private final Logger logger = LoggerFactory.getLogger(LdapUtilsIT.class);

    private LdapUtils ldapUtils;


    @BeforeMethod
    public void setTestSettings(){
        this.ldapUtils = new LdapUtils();
        this.ldapUtils.connect("");
    }

    @Test
    public void searchAdminUserTest(){
        String dn = System.getProperty("LDAP_USER");
        String filter = System.getProperty("LDAP_USER").substring(0,System.getProperty("LDAP_USER").indexOf(","));
        SearchRequest request = new SearchRequest(dn, filter);
        try {
            SearchResult  result = this.ldapUtils.search(request);
            LdapEntry entry = result.getEntry(System.getProperty("LDAP_USER"));
            assertThat(entry.getDn()).isEqualTo(System.getProperty("LDAP_USER"));
        } catch (LdapException e) {
            fail("Error searching LDAP: " + e);
        }
    }

    @Test(enabled=false)
    public void searchRegularUserTest(){
        String dn = "dc=stratio,dc=com";
        String filter = "uid=abrookes";
        try {
            SearchResult result = this.ldapUtils.search(new SearchRequest(dn, filter));
            LdapEntry entry = result.getEntry();
            assertThat(entry.getAttribute("uid").getStringValue()).isEqualTo("abrookes");
        } catch (LdapException e) {
            fail("Error searching LDAP: " + e);
        }
    }

    @Test
    public void searchEmptyRequestTest(){
        try {
            this.ldapUtils.search(new SearchRequest());
            fail("Expected exception was not thrown while searching without a valid request");
        } catch (LdapException e) {
            assertThat(e.getResultCode()).isEqualTo(ResultCode.INAPPROPRIATE_MATCHING);
        }
    }

    @Test
    public void searchFailTest(){
        try {
            SearchResult result = this.ldapUtils.search(new SearchRequest("dc=stratio,dc=com", "cn=nonexistentuser,dc=test,dc=com"));
            assertThat(result.size()).isEqualTo(0);
        } catch (LdapException e) {
            fail("Unexpected exception searching for a non-existent user: " + e);
        }
    }

    @Test(enabled=false)
    /*
     * This test assumes RFC2307bis schema in the LDAP server
     */
    public void crudTest(){
        String dn = "cn=AddTestGroup,ou=Groups,dc=stratio,dc=com";
        LdapEntry entry = new LdapEntry(
                dn,
                new LdapAttribute("objectClass", "posixGroup"),
                new LdapAttribute("cn", "AddTestGroup"),
                new LdapAttribute("gidNumber", "105"),
                new LdapAttribute( "description", "Test Group"));
        // Add entry
        try {
            this.ldapUtils.add(entry);
            SearchResult result = this.ldapUtils.search(new SearchRequest("dc=stratio,dc=com", "cn=AddTestGroup"));
            LdapEntry resultEntry = result.getEntry();
            assertThat(entry).isEqualToIgnoringGivenFields(resultEntry, "responseControls", "messageId", "HASH_CODE_SEED", "serialVersionUID");
        } catch (LdapException e) {
            e.printStackTrace();
            fail("Unexpected exception adding entry: " + e);
        }

        String gidNumber = "106";
        String memberUid = "uid=adoucet,ou=People,dc=stratio,dc=com";

        AttributeModification changeGidNumber = new AttributeModification(AttributeModificationType.REPLACE, new LdapAttribute("gidNumber", gidNumber));
        AttributeModification removeDescription = new AttributeModification(AttributeModificationType.REMOVE, new LdapAttribute("description"));
        AttributeModification addCommonName = new AttributeModification(AttributeModificationType.ADD, new LdapAttribute("memberUid", memberUid));
        // Modify entry
        try {
            this.ldapUtils.modify(dn, changeGidNumber, removeDescription, addCommonName);
            SearchResult result = this.ldapUtils.search(new SearchRequest("dc=stratio,dc=com", "cn=AddTestGroup"));
            LdapEntry modifiedEntry = result.getEntry();
            assertThat(modifiedEntry.getAttribute("gidNumber").getStringValue()).isEqualTo(gidNumber);
            assertThat(modifiedEntry.getAttribute("memberUid").getStringValue()).isEqualTo(memberUid);
            assertNull(modifiedEntry.getAttribute("description"));

        } catch (LdapException e) {
            e.printStackTrace();
            fail("Unexpected exception modifying entry: " + e);
        }
        // Delete entry
        try {
            this.ldapUtils.delete(dn);
            SearchResult result = this.ldapUtils.search(new SearchRequest("dc=stratio,dc=com", "cn=AddTestGroup"));
            assertThat(result.size()).isEqualTo(0);
        } catch (LdapException e) {
            e.printStackTrace();
            fail("Unexpected exception deleting entry: " + e);
        }
    }
}
