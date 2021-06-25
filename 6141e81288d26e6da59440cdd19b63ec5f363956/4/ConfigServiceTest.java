package com.ctrip.apollo.biz.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.ctrip.apollo.biz.entity.Release;
import com.ctrip.apollo.biz.repository.ReleaseRepository;
import com.ctrip.apollo.core.dto.ApolloConfig;
import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceTest {
  @Mock
  private ReleaseRepository releaseRepository;

  private ConfigService configService;

  @Before
  public void setUp() throws Exception {
    configService = new ConfigService();
    ReflectionTestUtils.setField(configService, "releaseRepository", releaseRepository);
  }

  @Test
  public void testLoadConfig() throws Exception {
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someGroupName = "someGroupName";
    String someReleaseId = "1";
    String someValidConfiguration = "{\"apollo.bar\": \"foo\"}";

    Release someRelease = assembleRelease(someReleaseId, someAppId, someClusterName, someGroupName,
        someValidConfiguration);

    when(releaseRepository.findFirstByAppIdAndClusterNameAndNamespaceNameOrderByIdDesc(someAppId,
        someClusterName, someGroupName)).thenReturn(someRelease);

    ApolloConfig result = configService.loadConfig(someRelease);

    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getCluster());
    assertEquals(someReleaseId, result.getReleaseId());
    assertEquals("foo", result.getConfigurations().get("apollo.bar"));
  }

  @Test
  public void testLoadConfigWithConfigNotFound() throws Exception {
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someNamespaceName = "someNamespaceName";

    when(releaseRepository.findFirstByAppIdAndClusterNameAndNamespaceNameOrderByIdDesc(someAppId,
        someClusterName, someNamespaceName)).thenReturn(null);
    Release someRelease = configService.findRelease(someAppId, someClusterName, someNamespaceName);
    ApolloConfig result = configService.loadConfig(someRelease);

    assertNull(result);
    verify(releaseRepository, times(1)).findFirstByAppIdAndClusterNameAndNamespaceNameOrderByIdDesc(
        someAppId, someClusterName, someNamespaceName);
  }

  private Release assembleRelease(String releaseId, String appId, String clusterName,
      String groupName, String configurations) {
    Release release = new Release();
    release.setId(Long.valueOf(releaseId));
    release.setAppId(appId);
    release.setClusterName(clusterName);
    release.setNamespaceName(groupName);
    release.setConfigurations(configurations);
    return release;
  }


  @Test
  public void testTransformConfigurationToMapSuccessful() throws Exception {
    String someValidConfiguration = "{\"apollo.bar\": \"foo\"}";
    Map<String, String> someMap = Maps.newHashMap();
    someMap.put("apollo.bar", "foo");

    Map<String, String> result = configService.transformConfigurationToMap(someValidConfiguration);

    assertEquals(someMap, result);
  }

  @Test(expected = JsonSyntaxException.class)
  public void testTransformConfigurationToMapFailed() throws Exception {
    String someInvalidConfiguration = "xxx";

    Map<String, String> result =
        configService.transformConfigurationToMap(someInvalidConfiguration);

    assertTrue(result.isEmpty());

  }
}
