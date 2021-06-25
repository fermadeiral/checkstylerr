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

  @Before
  public void setUp() throws Exception {
    someAppId = "someAppId";
    someCluster = "someCluster";
    someNamespace = "someNamespace";
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
    assertEquals(String.valueOf(990), result.getReleaseId());
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
    assertEquals(String.valueOf(991), result.getReleaseId());
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
    String releaseId = String.valueOf(991);
    ResponseEntity<ApolloConfig> response = restTemplate
        .getForEntity("{baseurl}/configs/{appId}/{clusterName}/{namespace}?releaseId={releaseId}", ApolloConfig.class,
            getHostUrl(), someAppId, someCluster, someNamespace, releaseId);

    assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
  }

}
