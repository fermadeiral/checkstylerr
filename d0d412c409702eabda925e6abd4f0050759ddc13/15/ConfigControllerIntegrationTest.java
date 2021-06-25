package com.ctrip.apollo.configservice.integration;

import com.ctrip.apollo.core.ConfigConsts;
import com.ctrip.apollo.core.dto.ApolloConfig;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpStatusCodeException;

import static org.junit.Assert.assertEquals;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class ConfigControllerIntegrationTest extends AbstractBaseIntegrationTest {
  private String someAppId;
  private String someCluster;
  private String someNamespace;
  private String somePublicNamespace;
  private String someDC;
  private String someDefaultCluster;

  @Before
  public void setUp() throws Exception {
    someAppId = "someAppId";
    someCluster = "someCluster";
    someNamespace = "someNamespace";
    somePublicNamespace = "somePublicNamespace";
    someDC = "someDC";
    someDefaultCluster = ConfigConsts.CLUSTER_NAME_DEFAULT;
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryConfigWithDefaultClusterAndDefaultNamespaceOK() throws Exception {
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}", ApolloConfig.class,
            getHostUrl(), someAppId, ConfigConsts.CLUSTER_NAME_DEFAULT);
    ApolloConfig result = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("TEST-RELEASE-KEY1", result.getReleaseKey());
    assertEquals("v1", result.getConfigurations().get("k1"));
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryConfigWithNamespaceOK() throws Exception {
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}", ApolloConfig.class,
            getHostUrl(), someAppId, someCluster, someNamespace);
    ApolloConfig result = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("TEST-RELEASE-KEY2", result.getReleaseKey());
    assertEquals("v2", result.getConfigurations().get("k2"));
  }

  @Test
  public void testQueryConfigError() throws Exception {
    String someNamespaceNotExists = "someNamespaceNotExists";

    HttpStatusCodeException httpException = null;
    try {
      ResponseEntity<ApolloConfig> response = restTemplate
          .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}", ApolloConfig.class,
              getHostUrl(), someAppId, someCluster, someNamespaceNotExists);
    } catch (HttpStatusCodeException ex) {
      httpException = ex;
    }

    assertEquals(HttpStatus.NOT_FOUND, httpException.getStatusCode());
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryConfigNotModified() throws Exception {
    String releaseKey = "TEST-RELEASE-KEY2";
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}?releaseKey={releaseKey}",
            ApolloConfig.class,
            getHostUrl(), someAppId, someCluster, someNamespace, releaseKey);

    assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryPublicConfigWithDataCenterFoundAndNoOverride() throws Exception {
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}?dataCenter={dateCenter}",
            ApolloConfig.class,
            getHostUrl(), someAppId, someCluster, somePublicNamespace, someDC);
    ApolloConfig result = response.getBody();

    assertEquals("TEST-RELEASE-KEY4", result.getReleaseKey());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someCluster, result.getCluster());
    assertEquals(somePublicNamespace, result.getNamespace());
    assertEquals("someDC-v1", result.getConfigurations().get("k1"));
    assertEquals("someDC-v2", result.getConfigurations().get("k2"));
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryPublicConfigWithDataCenterFoundAndOverride() throws Exception {
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}?dataCenter={dateCenter}",
            ApolloConfig.class,
            getHostUrl(), someAppId, someDefaultCluster, somePublicNamespace, someDC);
    ApolloConfig result = response.getBody();

    assertEquals("TEST-RELEASE-KEY5" + ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR + "TEST-RELEASE-KEY4", result.getReleaseKey());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someDefaultCluster, result.getCluster());
    assertEquals(somePublicNamespace, result.getNamespace());
    assertEquals("override-v1", result.getConfigurations().get("k1"));
    assertEquals("someDC-v2", result.getConfigurations().get("k2"));
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryPublicConfigWithDataCenterNotFoundAndNoOverride() throws Exception {
    String someDCNotFound = "someDCNotFound";
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}?dataCenter={dateCenter}",
            ApolloConfig.class,
            getHostUrl(), someAppId, someCluster, somePublicNamespace, someDCNotFound);
    ApolloConfig result = response.getBody();

    assertEquals("TEST-RELEASE-KEY3", result.getReleaseKey());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someCluster, result.getCluster());
    assertEquals(somePublicNamespace, result.getNamespace());
    assertEquals("default-v1", result.getConfigurations().get("k1"));
    assertEquals("default-v2", result.getConfigurations().get("k2"));
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryPublicConfigWithDataCenterNotFoundAndOverride() throws Exception {
    String someDCNotFound = "someDCNotFound";
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}?dataCenter={dateCenter}",
            ApolloConfig.class,
            getHostUrl(), someAppId, someDefaultCluster, somePublicNamespace, someDCNotFound);
    ApolloConfig result = response.getBody();

    assertEquals("TEST-RELEASE-KEY5" + ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR + "TEST-RELEASE-KEY3", result.getReleaseKey());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someDefaultCluster, result.getCluster());
    assertEquals(somePublicNamespace, result.getNamespace());
    assertEquals("override-v1", result.getConfigurations().get("k1"));
    assertEquals("default-v2", result.getConfigurations().get("k2"));

  }
}
