package com.ctrip.framework.apollo.biz.service;

import com.ctrip.framework.apollo.biz.entity.Release;
import com.ctrip.framework.apollo.biz.repository.ReleaseRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

  private ConfigService configService;

  @Before
  public void setUp() throws Exception {
    configService = new ConfigService();
    ReflectionTestUtils.setField(configService, "releaseRepository", releaseRepository);
  }

  @Test
  public void testFindRelease() throws Exception {
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someNamespaceName = "someNamespaceName";
    long someReleaseId = 1;
    String someReleaseKey = "someKey";
    String someValidConfiguration = "{\"apollo.bar\": \"foo\"}";

    Release
        someRelease =
        assembleRelease(someReleaseId, someReleaseKey, someAppId, someClusterName,
            someNamespaceName,
            someValidConfiguration);

    when(releaseRepository.findFirstByAppIdAndClusterNameAndNamespaceNameOrderByIdDesc(someAppId,
        someClusterName, someNamespaceName)).thenReturn(someRelease);

    Release result = configService.findRelease(someAppId, someClusterName, someNamespaceName);

    verify(releaseRepository, times(1))
        .findFirstByAppIdAndClusterNameAndNamespaceNameOrderByIdDesc(someAppId, someClusterName,
            someNamespaceName);
    assertEquals(someAppId, result.getAppId());
    assertEquals(someClusterName, result.getClusterName());
    assertEquals(someReleaseId, result.getId());
    assertEquals(someReleaseKey, result.getReleaseKey());
    assertEquals(someValidConfiguration, result.getConfigurations());
  }

  @Test
  public void testLoadConfigWithConfigNotFound() throws Exception {
    String someAppId = "1";
    String someClusterName = "someClusterName";
    String someNamespaceName = "someNamespaceName";

    when(releaseRepository.findFirstByAppIdAndClusterNameAndNamespaceNameOrderByIdDesc(someAppId,
        someClusterName, someNamespaceName)).thenReturn(null);

    Release result = configService.findRelease(someAppId, someClusterName, someNamespaceName);

    assertNull(result);
    verify(releaseRepository, times(1)).findFirstByAppIdAndClusterNameAndNamespaceNameOrderByIdDesc(
        someAppId, someClusterName, someNamespaceName);
  }

  private Release assembleRelease(long releaseId, String releaseKey, String appId,
                                  String clusterName,
                                  String groupName, String configurations) {
    Release release = new Release();
    release.setId(releaseId);
    release.setReleaseKey(releaseKey);
    release.setAppId(appId);
    release.setClusterName(clusterName);
    release.setNamespaceName(groupName);
    release.setConfigurations(configurations);
    return release;
  }

}
