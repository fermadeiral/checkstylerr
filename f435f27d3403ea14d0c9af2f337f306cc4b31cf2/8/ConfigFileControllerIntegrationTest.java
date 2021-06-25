package com.ctrip.framework.apollo.configservice.integration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import com.ctrip.framework.apollo.biz.entity.Namespace;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.netflix.servo.util.Strings;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class ConfigFileControllerIntegrationTest extends AbstractBaseIntegrationTest {
  private String someAppId;
  private String someCluster;
  private String someNamespace;
  private String somePublicNamespace;
  private String someDC;
  private String someDefaultCluster;

  @Before
  public void setUp() throws Exception {
    someDefaultCluster = ConfigConsts.CLUSTER_NAME_DEFAULT;
    someAppId = "someAppId";
    someCluster = "someCluster";
    someNamespace = "someNamespace";
    somePublicNamespace = "somePublicNamespace";
    someDC = "someDC";
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryConfigAsFile() throws Exception {
    ResponseEntity<String> response =
        restTemplate
            .getForEntity("{baseurl}/configfiles/{appId}/{clusterName}/{namespace}", String.class,
                getHostUrl(), someAppId, someCluster, someNamespace);

    String result = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(result.contains("k2=v2"));
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/test-release-public-dc-override.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testQueryPublicConfigAsFile() throws Exception {
    ResponseEntity<String> response =
        restTemplate
            .getForEntity(
                "{baseurl}/configfiles/{appId}/{clusterName}/{namespace}?dataCenter={dateCenter}",
                String.class,
                getHostUrl(), someAppId, someDefaultCluster, somePublicNamespace, someDC);

    String result = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(result.contains("k1=override-someDC-v1"));
    assertTrue(result.contains("k2=someDC-v2"));
  }

  @Test
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testConfigChanged() throws Exception {
    ResponseEntity<String> response =
        restTemplate
            .getForEntity("{baseurl}/configfiles/{appId}/{clusterName}/{namespace}", String.class,
                getHostUrl(), someAppId, someCluster, someNamespace);

    String result = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(result.contains("k2=v2"));

    String someReleaseName = "someReleaseName";
    String someReleaseComment = "someReleaseComment";
    Namespace namespace = new Namespace();
    namespace.setAppId(someAppId);
    namespace.setClusterName(someCluster);
    namespace.setNamespaceName(someNamespace);
    String someOwner = "someOwner";

    Map<String, String> newConfigurations = ImmutableMap.of("k1", "v1-changed", "k2", "v2-changed");

    buildRelease(someReleaseName, someReleaseComment, namespace, newConfigurations, someOwner);

    ResponseEntity<String> anotherResponse =
        restTemplate
            .getForEntity("{baseurl}/configfiles/{appId}/{clusterName}/{namespace}", String.class,
                getHostUrl(), someAppId, someCluster, someNamespace);

    assertEquals(response.getBody(), anotherResponse.getBody());

    List<String> keys = Lists.newArrayList(someAppId, someCluster, someNamespace);
    String message = Strings.join(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR, keys.iterator());
    sendReleaseMessage(message);

    TimeUnit.MILLISECONDS.sleep(500);

    ResponseEntity<String> newResponse =
        restTemplate
            .getForEntity("{baseurl}/configfiles/{appId}/{clusterName}/{namespace}", String.class,
                getHostUrl(), someAppId, someCluster, someNamespace);

    result = newResponse.getBody();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(result.contains("k1=v1-changed"));
    assertTrue(result.contains("k2=v2-changed"));
  }

}
