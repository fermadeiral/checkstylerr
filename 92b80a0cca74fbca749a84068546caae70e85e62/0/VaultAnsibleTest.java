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

import com.stratio.qa.utils.vaultansible.VaultAnsible;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class VaultAnsibleTest  {

    private String secretPath = "vaultansible/secret.yml";

    @Test
    public void testDecrypt() throws IOException {
        String secret = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(secretPath).getFile())));
        String password = "qabdt";

        byte [] decryptedVault = VaultAnsible.decrypt(secret.getBytes(), password);
        String decryptedString = new String(decryptedVault, StandardCharsets.UTF_8);

        LinkedHashMap<String,Object> yamlMap = (LinkedHashMap<String,Object>)new Yaml().load(decryptedString);
        assertThat(yamlMap.keySet().size()).as("only 1 key expected").isEqualTo(1);
        assertThat(yamlMap.get("secrets")).as("secrets key not found in yaml").isNotNull();

        LinkedHashMap<String,Object> yamlMapLdap = (LinkedHashMap<String,Object>)yamlMap.get("secrets");
        assertThat(yamlMapLdap.keySet().size()).as("only 1 key expected").isEqualTo(1);
        assertThat(yamlMapLdap.get("ldap")).as("ldap key not found in yaml").isNotNull();

        LinkedHashMap<String,Object> yamlMapUsers = (LinkedHashMap<String,Object>)yamlMapLdap.get("ldap");
        assertThat(yamlMapUsers.keySet().size()).as("only 2 keys expected").isEqualTo(2);
        assertThat(yamlMapUsers.get("admin")).as("admin key not found in yaml").isNotNull();
        assertThat(yamlMapUsers.get("super_admin")).as("super_admin key not found in yaml").isNotNull();

        LinkedHashMap<String,Object> yamlMapAdmin = (LinkedHashMap<String,Object>)yamlMapUsers.get("admin");
        assertThat(yamlMapAdmin.keySet().size()).as("only 2 keys expected").isEqualTo(2);
        assertThat(yamlMapAdmin.get("user").toString()).as("user key not found in yaml").isEqualTo("bdt1");
        assertThat(yamlMapAdmin.get("pass").toString()).as("pass key not found in yaml").isEqualTo("1234");

        LinkedHashMap<String,Object> yamlMapSuperAdmin = (LinkedHashMap<String,Object>)yamlMapUsers.get("super_admin");
        assertThat(yamlMapSuperAdmin.keySet().size()).as("only 2 keys expected").isEqualTo(2);
        assertThat(yamlMapSuperAdmin.get("user").toString()).as("user key not found in yaml").isEqualTo("bdt2");
        assertThat(yamlMapSuperAdmin.get("pass").toString()).as("pass key not found in yaml").isEqualTo("4321");
    }
}
