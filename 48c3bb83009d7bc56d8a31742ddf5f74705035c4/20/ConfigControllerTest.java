package com.ctrip.apollo.configservice.controller;

import com.ctrip.apollo.biz.entity.Version;
import com.ctrip.apollo.biz.service.ConfigService;
import com.ctrip.apollo.core.dto.ApolloConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

  @Before
  public void setUp() throws Exception {
    configController = new ConfigController();
    ReflectionTestUtils.setField(configController, "configService", configService);
  }

  @Test
  public void testQueryConfig() throws Exception {
    ApolloConfig someApolloConfig = mock(ApolloConfig.class);
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someVersionName = "someVersion";
    long someClientSideReleaseId = 1;
    long someServerSideNewReleaseId = 2;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);
    Version someVersion = mock(Version.class);

    when(configService.loadVersionByAppIdAndVersionName(someAppId, someVersionName))
        .thenReturn(someVersion);
    when(someVersion.getReleaseId()).thenReturn(someServerSideNewReleaseId);
    when(configService.loadConfigByVersionAndClusterName(someVersion, someClusterName))
        .thenReturn(someApolloConfig);

    ApolloConfig
        result =
        configController
            .queryConfig(someAppId, someClusterName, someVersionName, someClientSideReleaseId,
                someResponse);

    assertEquals(someApolloConfig, result);
    verify(configService, times(1)).loadVersionByAppIdAndVersionName(someAppId, someVersionName);
    verify(configService, times(1)).loadConfigByVersionAndClusterName(someVersion, someClusterName);
  }

  @Test
  public void testQueryConfigWithVersionNotFound() throws Exception {
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someVersionName = "someVersion";
    long someClientSideReleaseId = 1;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);

    when(configService.loadVersionByAppIdAndVersionName(someAppId, someVersionName))
        .thenReturn(null);

    ApolloConfig
        result =
        configController
            .queryConfig(someAppId, someClusterName, someVersionName, someClientSideReleaseId,
                someResponse);

    assertNull(result);
    verify(someResponse, times(1)).sendError(eq(HttpServletResponse.SC_NOT_FOUND), anyString());
  }

  @Test
  public void testQueryConfigWithApolloConfigNotFound() throws Exception {
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someVersionName = "someVersion";
    long someClientSideReleaseId = 1;
    long someServerSideNewReleaseId = 2;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);
    Version someVersion = mock(Version.class);

    when(configService.loadVersionByAppIdAndVersionName(someAppId, someVersionName))
        .thenReturn(someVersion);
    when(someVersion.getReleaseId()).thenReturn(someServerSideNewReleaseId);
    when(configService.loadConfigByVersionAndClusterName(someVersion, someClusterName))
        .thenReturn(null);

    ApolloConfig
        result =
        configController
            .queryConfig(someAppId, someClusterName, someVersionName, someClientSideReleaseId,
                someResponse);

    assertNull(result);
    verify(someResponse, times(1)).sendError(eq(HttpServletResponse.SC_NOT_FOUND), anyString());
  }

  @Test
  public void testQueryConfigWithApolloConfigNotModified() throws Exception {
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someVersionName = "someVersion";
    long someClientSideReleaseId = 1;
    long someServerSideReleaseId = someClientSideReleaseId;
    HttpServletResponse someResponse = mock(HttpServletResponse.class);
    Version someVersion = mock(Version.class);

    when(configService.loadVersionByAppIdAndVersionName(someAppId, someVersionName))
        .thenReturn(someVersion);
    when(someVersion.getReleaseId()).thenReturn(someServerSideReleaseId);

    ApolloConfig
        result =
        configController
            .queryConfig(someAppId, someClusterName, someVersionName, someClientSideReleaseId,
                someResponse);

    assertNull(result);
    verify(someResponse, times(1)).setStatus(HttpServletResponse.SC_NOT_MODIFIED);
    verify(configService, never())
        .loadConfigByVersionAndClusterName(any(Version.class), anyString());
  }
}
