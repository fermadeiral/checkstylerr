package com.ctrip.framework.apollo.biz.service;

import com.ctrip.framework.apollo.biz.AbstractUnitTest;
import com.ctrip.framework.apollo.biz.entity.Release;
import com.ctrip.framework.apollo.biz.repository.ReleaseRepository;
import com.ctrip.framework.apollo.common.exception.BadRequestException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReleaseServiceTest extends AbstractUnitTest {

  @Mock
  private ReleaseRepository releaseRepository;
  @Mock
  private NamespaceService namespaceService;
  @InjectMocks
  private ReleaseService releaseService;

  private String appId = "appId-test";
  private String clusterName = "cluster-test";
  private String namespaceName = "namespace-test";
  private String user = "user-test";
  private long releaseId = 1;
  private Release firstRelease;
  private Release secondRelease;
  private PageRequest pageRequest;

  @Before
  public void init() {

    firstRelease = new Release();
    firstRelease.setId(releaseId);
    firstRelease.setAppId(appId);
    firstRelease.setClusterName(clusterName);
    firstRelease.setNamespaceName(namespaceName);
    firstRelease.setAbandoned(false);

    secondRelease = new Release();
    secondRelease.setAppId(appId);
    secondRelease.setClusterName(clusterName);
    secondRelease.setNamespaceName(namespaceName);
    secondRelease.setAbandoned(false);

    pageRequest = new PageRequest(0, 2);
  }

  @Test(expected = BadRequestException.class)
  public void testNamespaceNotExist() {

    when(releaseRepository.findOne(releaseId)).thenReturn(firstRelease);

    releaseService.rollback(releaseId, user);
  }

  @Test(expected = BadRequestException.class)
  public void testHasNoRelease() {

    when(releaseRepository.findOne(releaseId)).thenReturn(firstRelease);
    when(releaseRepository.findByAppIdAndClusterNameAndNamespaceNameAndIsAbandonedFalseOrderByIdDesc(appId,
                                                                                                     clusterName,
                                                                                                     namespaceName,
                                                                                                     pageRequest))
        .thenReturn(null);

    releaseService.rollback(releaseId, user);
  }

  @Test
  public void testRollback() {

    when(releaseRepository.findOne(releaseId)).thenReturn(firstRelease);
    when(releaseRepository.findByAppIdAndClusterNameAndNamespaceNameAndIsAbandonedFalseOrderByIdDesc(appId,
                                                                                                     clusterName,
                                                                                                     namespaceName,
                                                                                                     pageRequest))
        .thenReturn(
            Arrays.asList(firstRelease, secondRelease));

    releaseService.rollback(releaseId, user);

    verify(releaseRepository).save(firstRelease);
    Assert.assertEquals(true, firstRelease.isAbandoned());
    Assert.assertEquals(user, firstRelease.getDataChangeLastModifiedBy());
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

    when(releaseRepository.findFirstByAppIdAndClusterNameAndNamespaceNameAndIsAbandonedFalseOrderByIdDesc(someAppId,
                                                                                                          someClusterName,
                                                                                                          someNamespaceName))
        .thenReturn(someRelease);

    Release result = releaseService.findLatestActiveRelease(someAppId, someClusterName, someNamespaceName);

    verify(releaseRepository, times(1))
        .findFirstByAppIdAndClusterNameAndNamespaceNameAndIsAbandonedFalseOrderByIdDesc(someAppId, someClusterName,
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

    when(releaseRepository.findFirstByAppIdAndClusterNameAndNamespaceNameAndIsAbandonedFalseOrderByIdDesc(someAppId,
                                                                                                          someClusterName,
                                                                                                          someNamespaceName))
        .thenReturn(null);

    Release result = releaseService.findLatestActiveRelease(someAppId, someClusterName, someNamespaceName);

    assertNull(result);
    verify(releaseRepository, times(1)).findFirstByAppIdAndClusterNameAndNamespaceNameAndIsAbandonedFalseOrderByIdDesc(
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
