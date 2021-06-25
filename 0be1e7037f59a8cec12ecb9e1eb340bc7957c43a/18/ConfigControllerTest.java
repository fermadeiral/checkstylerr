package com.ctrip.apollo.configservice.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.ctrip.apollo.biz.entity.AppNamespace;
import com.ctrip.apollo.biz.entity.Release;
import com.ctrip.apollo.biz.service.AppNamespaceService;
import com.ctrip.apollo.biz.service.ConfigService;
import com.ctrip.apollo.core.ConfigConsts;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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
  @Mock
  private AppNamespaceService appNamespaceService;
  private String someAppId;
  private String someClusterName;
  private String defaultNamespaceName;
  private String somePublicNamespaceName;
  private String someDataCenter;
  @Mock
  private Release someRelease;
  @Mock
  private Release somePublicRelease;

  @Before
  public void setUp() throws Exception {
    configController = new ConfigController();
    ReflectionTestUtils.setField(configController, "configService", configService);
    ReflectionTestUtils.setField(configController, "appNamespaceService", appNamespaceService);

    someAppId = "1";
    someClusterName = "someClusterName";
    defaultNamespaceName = ConfigConsts.NAMESPACE_DEFAULT;
    somePublicNamespaceName = "somePublicNamespace";
    someDataCenter = "someDC";
    String someValidConfiguration = "{\"apollo.bar\": \"foo\"}";
    String somePublicConfiguration = "{\"apollo.public.bar\": \"foo\"}";

    when(someRelease.getConfigurations()).thenReturn(someValidConfiguration);
    when(somePublicRelease.getConfigurations()).thenReturn(somePublicConfiguration);
  }

  @Test
  public void testQueryConfig() throws Exception {
    long someClientSideReleaseId = 1;
    long someServerSideNewReleaseId = 2;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);

    when(configService.findRelease(someAppId, someClusterName, defaultNamespaceName))
        .thenReturn(someRelease);
    when(someRelease.getId()).thenReturn(someServerSideNewReleaseId);

    ApolloConfig result = configController.queryConfig(someAppId, someClusterName,
        defaultNamespaceName, someDataCenter, String.valueOf(someClientSideReleaseId),
        someResponse);

    verify(configService, times(1)).findRelease(someAppId, someClusterName, defaultNamespaceName);
    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getCluster());
    assertEquals(defaultNamespaceName, result.getNamespace());
    assertEquals(String.valueOf(someServerSideNewReleaseId), result.getReleaseId());
  }


  @Test
  public void testQueryConfigWithReleaseNotFound() throws Exception {
    long someClientSideReleaseId = 1;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);

    when(configService.findRelease(someAppId, someClusterName, defaultNamespaceName))
        .thenReturn(null);

    ApolloConfig result = configController.queryConfig(someAppId, someClusterName,
        defaultNamespaceName, someDataCenter, String.valueOf(someClientSideReleaseId),
        someResponse);

    assertNull(result);
    verify(someResponse, times(1)).sendError(eq(HttpServletResponse.SC_NOT_FOUND), anyString());
  }

  @Test
  public void testQueryConfigWithApolloConfigNotModified() throws Exception {
    long someClientSideReleaseId = 1;
    long someServerSideReleaseId = someClientSideReleaseId;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);

    when(configService.findRelease(someAppId, someClusterName, defaultNamespaceName))
        .thenReturn(someRelease);
    when(someRelease.getId()).thenReturn(someServerSideReleaseId);

    ApolloConfig
        result =
        configController.queryConfig(someAppId, someClusterName, defaultNamespaceName,
            someDataCenter, String.valueOf(someClientSideReleaseId), someResponse);

    assertNull(result);
    verify(someResponse, times(1)).setStatus(HttpServletResponse.SC_NOT_MODIFIED);
  }

  @Test
  public void testQueryConfigWithAppOwnNamespace() throws Exception {
    String someClientSideReleaseId = "1";
    String someServerSideReleaseId = "2";
    String someAppOwnNamespaceName = "someAppOwn";
    HttpServletResponse someResponse = mock(HttpServletResponse.class);
    AppNamespace someAppOwnNamespace =
        assmbleAppNamespace(someAppId, someAppOwnNamespaceName);

    when(configService.findRelease(someAppId, someClusterName, someAppOwnNamespaceName))
        .thenReturn(someRelease);
    when(appNamespaceService.findByNamespaceName(someAppOwnNamespaceName))
        .thenReturn(someAppOwnNamespace);
    when(someRelease.getId()).thenReturn(Long.valueOf(someServerSideReleaseId));

    ApolloConfig result =
        configController
            .queryConfig(someAppId, someClusterName, someAppOwnNamespaceName, someDataCenter,
                someClientSideReleaseId, someResponse);

    assertEquals(someServerSideReleaseId, result.getReleaseId());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getCluster());
    assertEquals(someAppOwnNamespaceName, result.getNamespace());
    assertEquals("foo", result.getConfigurations().get("apollo.bar"));
  }

  @Test
  public void testQueryConfigWithPubicNamespaceAndNoAppOverride() throws Exception {
    String someClientSideReleaseId = "1";
    String someServerSideReleaseId = "2";
    HttpServletResponse someResponse = mock(HttpServletResponse.class);
    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespaceName);

    when(configService.findRelease(someAppId, someClusterName, somePublicNamespaceName))
        .thenReturn(null);
    when(appNamespaceService.findByNamespaceName(somePublicNamespaceName))
        .thenReturn(somePublicAppNamespace);
    when(configService.findRelease(somePublicAppId, someDataCenter, somePublicNamespaceName))
        .thenReturn(somePublicRelease);
    when(somePublicRelease.getId()).thenReturn(Long.valueOf(someServerSideReleaseId));

    ApolloConfig result =
        configController
            .queryConfig(someAppId, someClusterName, somePublicNamespaceName, someDataCenter,
                someClientSideReleaseId, someResponse);

    assertEquals(someServerSideReleaseId, result.getReleaseId());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getCluster());
    assertEquals(somePublicNamespaceName, result.getNamespace());
    assertEquals("foo", result.getConfigurations().get("apollo.public.bar"));
  }

  @Test
  public void testQueryConfigWithPublicNamespaceAndNoAppOverrideAndNoDataCenter() throws Exception {
    String someClientSideReleaseId = "1";
    String someServerSideReleaseId = "2";
    HttpServletResponse someResponse = mock(HttpServletResponse.class);
    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespaceName);

    when(configService.findRelease(someAppId, someClusterName, somePublicNamespaceName))
        .thenReturn(null);
    when(appNamespaceService.findByNamespaceName(somePublicNamespaceName))
        .thenReturn(somePublicAppNamespace);
    when(configService.findRelease(somePublicAppId, someDataCenter, somePublicNamespaceName))
        .thenReturn(null);
    when(configService
        .findRelease(somePublicAppId, ConfigConsts.CLUSTER_NAME_DEFAULT, somePublicNamespaceName))
        .thenReturn(somePublicRelease);
    when(somePublicRelease.getId()).thenReturn(Long.valueOf(someServerSideReleaseId));

    ApolloConfig result =
        configController
            .queryConfig(someAppId, someClusterName, somePublicNamespaceName, someDataCenter,
                someClientSideReleaseId, someResponse);

    assertEquals(someServerSideReleaseId, result.getReleaseId());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getCluster());
    assertEquals(somePublicNamespaceName, result.getNamespace());
    assertEquals("foo", result.getConfigurations().get("apollo.public.bar"));
  }

  @Test
  public void testQueryConfigWithPublicNamespaceAndAppOverride() throws Exception {
    String someAppSideReleaseId = "1";
    String somePublicAppSideReleaseId = "2";

    HttpServletResponse someResponse = mock(HttpServletResponse.class);
    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespaceName);

    when(someRelease.getConfigurations()).thenReturn("{\"apollo.public.foo\": \"foo-override\"}");
    when(somePublicRelease.getConfigurations())
        .thenReturn("{\"apollo.public.foo\": \"foo\", \"apollo.public.bar\": \"bar\"}");

    when(configService.findRelease(someAppId, someClusterName, somePublicNamespaceName))
        .thenReturn(someRelease);
    when(someRelease.getId()).thenReturn(Long.valueOf(someAppSideReleaseId));
    when(appNamespaceService.findByNamespaceName(somePublicNamespaceName))
        .thenReturn(somePublicAppNamespace);
    when(configService.findRelease(somePublicAppId, someDataCenter, somePublicNamespaceName))
        .thenReturn(somePublicRelease);
    when(somePublicRelease.getId()).thenReturn(Long.valueOf(somePublicAppSideReleaseId));

    ApolloConfig result =
        configController
            .queryConfig(someAppId, someClusterName, somePublicNamespaceName, someDataCenter,
                someAppSideReleaseId, someResponse);

    assertEquals(Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
            .join(someAppSideReleaseId, somePublicAppSideReleaseId),
        result.getReleaseId());
    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getCluster());
    assertEquals(somePublicNamespaceName, result.getNamespace());
    assertEquals("foo-override", result.getConfigurations().get("apollo.public.foo"));
    assertEquals("bar", result.getConfigurations().get("apollo.public.bar"));
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

  private AppNamespace assmbleAppNamespace(String appId, String namespace) {
    AppNamespace appNamespace = new AppNamespace();
    appNamespace.setAppId(appId);
    appNamespace.setName(namespace);
    return appNamespace;
  }
}
