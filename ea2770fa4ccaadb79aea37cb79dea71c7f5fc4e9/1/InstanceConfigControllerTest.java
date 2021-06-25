package com.ctrip.framework.apollo.adminservice.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import com.ctrip.framework.apollo.biz.entity.Instance;
import com.ctrip.framework.apollo.biz.entity.InstanceConfig;
import com.ctrip.framework.apollo.biz.entity.Release;
import com.ctrip.framework.apollo.biz.service.InstanceService;
import com.ctrip.framework.apollo.biz.service.ReleaseService;
import com.ctrip.framework.apollo.common.dto.InstanceDTO;
import com.ctrip.framework.apollo.common.exception.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class InstanceConfigControllerTest {
  private InstanceConfigController instanceConfigController;

  @Mock
  private ReleaseService releaseService;

  @Mock
  private InstanceService instanceService;

  @Mock
  private Pageable pageable;

  @Before
  public void setUp() throws Exception {
    instanceConfigController = new InstanceConfigController();
    ReflectionTestUtils.setField(instanceConfigController, "releaseService", releaseService);
    ReflectionTestUtils.setField(instanceConfigController, "instanceService", instanceService);
  }

  @Test
  public void getByRelease() throws Exception {
    long someReleaseId = 1;
    long someInstanceId = 1;
    long anotherInstanceId = 2;
    String someReleaseKey = "someKey";
    Release someRelease = new Release();
    someRelease.setReleaseKey(someReleaseKey);
    String someAppId = "someAppId";
    String anotherAppId = "anotherAppId";
    String someCluster = "someCluster";
    String someDataCenter = "someDC";
    String someConfigAppId = "someConfigAppId";
    String someConfigNamespace = "someNamespace";
    String someIp = "someIp";
    boolean withReleaseDetail = true;

    when(releaseService.findOne(someReleaseId)).thenReturn(someRelease);

    InstanceConfig someInstanceConfig = assembleInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespace, someReleaseKey);
    InstanceConfig anotherInstanceConfig = assembleInstanceConfig(anotherInstanceId,
        someConfigAppId, someConfigNamespace, someReleaseKey);
    List<InstanceConfig> instanceConfigs = Lists.newArrayList(someInstanceConfig,
        anotherInstanceConfig);

    when(instanceService.findActiveInstanceConfigsByReleaseKey(someReleaseKey, pageable))
        .thenReturn(instanceConfigs);

    Instance someInstance = assembleInstance(someInstanceId, someAppId,
        someCluster, someDataCenter, someIp);
    Instance anotherInstance = assembleInstance(anotherInstanceId, anotherAppId,
        someCluster, someDataCenter, someIp);
    List<Instance> instances = Lists.newArrayList(someInstance, anotherInstance);

    Set<Long> instanceIds = Sets.newHashSet(someInstanceId, anotherInstanceId);
    when(instanceService.findInstancesByIds(instanceIds))
        .thenReturn(instances);

    List<InstanceDTO> result = instanceConfigController.getByRelease(someReleaseId,
        withReleaseDetail, pageable);

    assertEquals(2, result.size());
    InstanceDTO someInstanceDto = null;
    InstanceDTO anotherInstanceDto = null;

    for (InstanceDTO instanceDTO : result) {
      if (instanceDTO.getId() == someInstanceId) {
        someInstanceDto = instanceDTO;
      } else if (instanceDTO.getId() == anotherInstanceId) {
        anotherInstanceDto = instanceDTO;
      }
    }

    verifyInstance(someInstance, someInstanceDto);
    verifyInstance(anotherInstance, anotherInstanceDto);

    assertEquals(1, someInstanceDto.getConfigs().size());
    assertEquals(someReleaseKey, someInstanceDto.getConfigs().get(0).getRelease().getReleaseKey());
    assertEquals(someInstanceConfig.getDataChangeLastModifiedTime(), someInstanceDto.getConfigs()
        .get(0).getDataChangeLastModifiedTime());

    assertEquals(1, anotherInstanceDto.getConfigs().size());
    assertEquals(someReleaseKey, anotherInstanceDto.getConfigs().get(0).getRelease().getReleaseKey());
    assertEquals(anotherInstanceConfig.getDataChangeLastModifiedTime(), anotherInstanceDto.getConfigs()
        .get(0).getDataChangeLastModifiedTime());
  }

  @Test(expected = NotFoundException.class)
  public void testGetByReleaseWhenReleaseIsNotFound() throws Exception {
    long someReleaseIdNotExists = 1;
    boolean withReleaseDetail = false;

    when(releaseService.findOne(someReleaseIdNotExists)).thenReturn(null);

    instanceConfigController.getByRelease(someReleaseIdNotExists, withReleaseDetail, pageable);
  }

  private void verifyInstance(Instance instance, InstanceDTO instanceDTO) {
    assertEquals(instance.getId(), instanceDTO.getId());
    assertEquals(instance.getAppId(), instanceDTO.getAppId());
    assertEquals(instance.getClusterName(), instanceDTO.getClusterName());
    assertEquals(instance.getDataCenter(), instanceDTO.getDataCenter());
    assertEquals(instance.getIp(), instanceDTO.getIp());
    assertEquals(instance.getDataChangeCreatedTime(), instanceDTO.getDataChangeCreatedTime());
  }

  private Instance assembleInstance(long instanceId, String appId, String clusterName, String
      dataCenter, String
                                        ip) {
    Instance instance = new Instance();
    instance.setId(instanceId);
    instance.setAppId(appId);
    instance.setIp(ip);
    instance.setClusterName(clusterName);
    instance.setDataCenter(dataCenter);
    instance.setDataChangeCreatedTime(new Date());

    return instance;
  }

  private InstanceConfig assembleInstanceConfig(long instanceId, String configAppId, String
      configNamespaceName, String releaseKey) {
    InstanceConfig instanceConfig = new InstanceConfig();
    instanceConfig.setInstanceId(instanceId);
    instanceConfig.setConfigAppId(configAppId);
    instanceConfig.setConfigNamespaceName(configNamespaceName);
    instanceConfig.setReleaseKey(releaseKey);
    instanceConfig.setDataChangeLastModifiedTime(new Date());
    return instanceConfig;
  }
}