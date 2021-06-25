package com.ctrip.apollo.biz.service;

import com.google.common.collect.Maps;
import com.ctrip.apollo.biz.entity.Release;
import com.ctrip.apollo.biz.repository.ReleaseRepository;
import com.ctrip.apollo.biz.service.ConfigService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceTest {
  @Mock
  private ReleaseRepository releaseRepository;
  @Mock
  private ObjectMapper objectMapper;
  private ConfigService configService;

  @Before
  public void setUp() throws Exception {
    configService = new ConfigService();
    ReflectionTestUtils
        .setField(configService, "releaseRepository", releaseRepository);
    ReflectionTestUtils.setField(configService, "objectMapper", objectMapper);
  }

//  @Test
//  public void testLoadConfig() throws Exception {
//    String someAppId = "1";
//    String someClusterName = "someClusterName";
//    String someGroupName = "someGroupName";
//    String someVersionName = "someVersionName";
//    long someReleaseId = 1;
//    String someValidConfiguration = "{\"apollo.bar\": \"foo\"}";
//
//    Version someVersion = assembleVersion(someAppId, someVersionName, someReleaseId);
//    Release
//        someRelease =
//        assembleRelease(someReleaseId, someClusterName, someGroupName, someValidConfiguration);
//    Map<String, Object> someMap = Maps.newHashMap();
//
//    when(versionRepository.findByAppIdAndName(someAppId, someVersionName)).thenReturn(someVersion);
//    when(releaseRepository.findByReleaseIdAndClusterName(someReleaseId, someClusterName))
//        .thenReturn(someReleaseSnapShot);
//    when(objectMapper.readValue(eq(someValidConfiguration), (TypeReference) anyObject()))
//        .thenReturn(someMap);
//
//    ApolloConfig result = configService.loadConfig(someAppId, someClusterName, someVersionName);
//
//    assertEquals(someAppId, result.getAppId());
//    assertEquals(someClusterName, result.getCluster());
//    assertEquals(someVersionName, result.getVersion());
//    assertEquals(someReleaseId, result.getReleaseId());
//    assertEquals(someMap, result.getConfigurations());
//  }
//
//  @Test
//  public void testLoadConfigWithVersionNotFound() throws Exception {
//    String someAppId = "1";
//    String someClusterName = "someClusterName";
//    String someVersionName = "someVersionName";
//
//    when(versionRepository.findByAppIdAndName(someAppId, someVersionName)).thenReturn(null);
//
//    ApolloConfig result = configService.loadConfig(someAppId, someClusterName, someVersionName);
//
//    assertNull(result);
//    verify(versionRepository, times(1)).findByAppIdAndName(someAppId, someVersionName);
//  }
//
//  @Test
//  public void testLoadConfigWithConfigNotFound() throws Exception {
//    String someAppId = "1";
//    String someClusterName = "someClusterName";
//    String someVersionName = "someVersionName";
//    long someReleaseId = 1;
//    Version someVersion = assembleVersion(someAppId, someVersionName, someReleaseId);
//
//    when(versionRepository.findByAppIdAndName(someAppId, someVersionName)).thenReturn(someVersion);
//    when(releaseRepository.findByReleaseIdAndClusterName(someReleaseId, someClusterName))
//        .thenReturn(null);
//
//    ApolloConfig result = configService.loadConfig(someAppId, someClusterName, someVersionName);
//
//    assertNull(result);
//    verify(versionRepository, times(1)).findByAppIdAndName(someAppId, someVersionName);
//    verify(releaseRepository, times(1))
//        .findByReleaseIdAndClusterName(someReleaseId, someClusterName);
//  }
//
//  private Version assembleVersion(String appId, String versionName, long releaseId) {
//    Version version = new Version();
//    version.setAppId(appId);
//    version.setName(versionName);
//    version.setReleaseId(releaseId);
//    return version;
//  }

  private Release assembleRelease(long releaseId, String clusterName, String groupName,
                                                  String configurations) {
    Release release = new Release();
    release.setId(releaseId);
    release.setClusterName(clusterName);
    release.setNamespaceName(groupName);
    release.setConfigurations(configurations);
    return release;
  }


  @Test
  public void testTransformConfigurationToMapSuccessful() throws Exception {
    String someValidConfiguration = "{\"apollo.bar\": \"foo\"}";
    Map<String, String> someMap = Maps.newHashMap();
    when(objectMapper.readValue(eq(someValidConfiguration), (TypeReference) anyObject()))
        .thenReturn(someMap);

    Map<String, String> result = configService.transformConfigurationToMap(someValidConfiguration);

    assertEquals(someMap, result);
    verify(objectMapper, times(1))
        .readValue(eq(someValidConfiguration), (TypeReference) anyObject());
  }

  @Test
  public void testTransformConfigurationToMapFailed() throws Exception {
    String someInvalidConfiguration = "xxx";
    when(objectMapper.readValue(eq(someInvalidConfiguration), (TypeReference) anyObject()))
        .thenThrow(IOException.class);

    Map<String, String>
        result =
        configService.transformConfigurationToMap(someInvalidConfiguration);

    assertTrue(result.isEmpty());

  }
}
