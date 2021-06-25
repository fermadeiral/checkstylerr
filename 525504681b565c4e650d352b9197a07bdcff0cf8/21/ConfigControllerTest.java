package com.ctrip.apollo.configservice.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.ctrip.apollo.biz.entity.Release;
import com.ctrip.apollo.biz.service.ConfigService;
import com.ctrip.apollo.core.dto.ApolloConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigControllerTest {
  private ConfigController configController;
  @Mock
  private ConfigService configService;
  private String someAppId;
  private String someClusterName;
  private String someNamespaceName;
  private String someDataCenter;
  private String someValidConfiguration;
  @Mock
  private Release someRelease;

  @Before
  public void setUp() throws Exception {
    configController = new ConfigController();
    ReflectionTestUtils.setField(configController, "configService", configService);

    someAppId = "1";
    someClusterName = "someClusterName";
    someNamespaceName = "someNamespaceName";
    someDataCenter = "someDC";
    someValidConfiguration = "{\"apollo.bar\": \"foo\"}";

    when(someRelease.getConfigurations()).thenReturn(someValidConfiguration);
  }

  @Test
  public void testQueryConfig() throws Exception {
    long someClientSideReleaseId = 1;
    long someServerSideNewReleaseId = 2;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);

    when(configService.findRelease(someAppId, someClusterName, someNamespaceName))
        .thenReturn(someRelease);
    when(someRelease.getId()).thenReturn(someServerSideNewReleaseId);

    ApolloConfig result = configController.queryConfig(someAppId, someClusterName,
        someNamespaceName, someDataCenter, String.valueOf(someClientSideReleaseId), someResponse);

    verify(configService, times(1)).findRelease(someAppId, someClusterName, someNamespaceName);
    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getCluster());
    assertEquals(someNamespaceName, result.getNamespace());
    assertEquals(String.valueOf(someServerSideNewReleaseId), result.getReleaseId());
  }


  @Test
  public void testQueryConfigWithReleaseNotFound() throws Exception {
    long someClientSideReleaseId = 1;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);

    when(configService.findRelease(someAppId, someClusterName, someNamespaceName)).thenReturn(null);

    ApolloConfig result = configController.queryConfig(someAppId, someClusterName,
        someNamespaceName, someDataCenter, String.valueOf(someClientSideReleaseId), someResponse);

    assertNull(result);
    verify(someResponse, times(1)).sendError(eq(HttpServletResponse.SC_NOT_FOUND), anyString());
  }

  @Test
  public void testQueryConfigWithApolloConfigNotModified() throws Exception {
    long someClientSideReleaseId = 1;
    long someServerSideReleaseId = someClientSideReleaseId;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);

    when(configService.findRelease(someAppId, someClusterName, someNamespaceName))
        .thenReturn(someRelease);
    when(someRelease.getId()).thenReturn(someServerSideReleaseId);

    ApolloConfig
        result =
        configController.queryConfig(someAppId, someClusterName, someNamespaceName,
            someDataCenter, String.valueOf(someClientSideReleaseId), someResponse);

    assertNull(result);
    verify(someResponse, times(1)).setStatus(HttpServletResponse.SC_NOT_MODIFIED);
  }

  @Test
  public void testMergeConfigurations() throws Exception {
    Gson gson = new Gson();
    String key1 = "key1";
    String value1 = "value1";
    String anotherValue1 = "anotherValue1";

    String key2 = "key2";
    String value2 = "value2";

    Map<String, String> config = ImmutableMap.of(key1, anotherValue1);
    Map<String, String> anotherConfig = ImmutableMap.of(key1, value1, key2, value2);

    Release releaseWithHighPriority = new Release();
    releaseWithHighPriority.setConfigurations(gson.toJson(config));

    Release releaseWithLowPriority = new Release();
    releaseWithLowPriority.setConfigurations(gson.toJson(anotherConfig));

    Map<String, String> result =
        configController.mergeReleaseConfigurations(
            Lists.newArrayList(releaseWithHighPriority, releaseWithLowPriority));

    assertEquals(2, result.keySet().size());
    assertEquals(anotherValue1, result.get(key1));
    assertEquals(value2, result.get(key2));
  }

  @Test(expected = JsonSyntaxException.class)
  public void testTransformConfigurationToMapFailed() throws Exception {
    String someInvalidConfiguration = "xxx";
    Release someRelease = new Release();
    someRelease.setConfigurations(someInvalidConfiguration);

    configController.mergeReleaseConfigurations(Lists.newArrayList(someRelease));
  }
}
