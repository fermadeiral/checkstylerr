package com.ctrip.framework.apollo.biz.service;

import com.google.common.collect.Sets;

import com.ctrip.framework.apollo.biz.AbstractIntegrationTest;
import com.ctrip.framework.apollo.biz.entity.Instance;
import com.ctrip.framework.apollo.biz.entity.InstanceConfig;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class InstanceServiceTest extends AbstractIntegrationTest {
  @Autowired
  private InstanceService instanceService;

  @Test
  @Rollback
  public void testCreateAndFindInstance() throws Exception {
    String someAppId = "someAppId";
    String someClusterName = "someClusterName";
    String someDataCenter = "someDataCenter";
    String someIp = "someIp";

    Instance instance = instanceService.findInstance(someAppId, someClusterName, someDataCenter,
        someIp);

    assertNull(instance);

    instanceService.createInstance(assembleInstance(someAppId, someClusterName, someDataCenter,
        someIp));

    instance = instanceService.findInstance(someAppId, someClusterName, someDataCenter,
        someIp);

    assertNotEquals(0, instance.getId());
  }

  @Test
  @Rollback
  public void testFindInstancesByIds() throws Exception {
    String someAppId = "someAppId";
    String someClusterName = "someClusterName";
    String someDataCenter = "someDataCenter";
    String someIp = "someIp";
    String anotherIp = "anotherIp";

    Instance someInstance = instanceService.createInstance(assembleInstance(someAppId,
        someClusterName, someDataCenter, someIp));
    Instance anotherInstance = instanceService.createInstance(assembleInstance(someAppId,
        someClusterName, someDataCenter, anotherIp));

    List<Instance> instances = instanceService.findInstancesByIds(Sets.newHashSet(someInstance
        .getId(), anotherInstance.getId()));

    Set<String> ips = instances.stream().map(Instance::getIp).collect(Collectors.toSet());
    assertEquals(2, instances.size());
    assertEquals(Sets.newHashSet(someIp, anotherIp), ips);
  }

  @Test
  @Rollback
  public void testCreateAndFindInstanceConfig() throws Exception {
    long someInstanceId = 1;
    String someConfigAppId = "someConfigAppId";
    String someConfigNamespaceName = "someConfigNamespaceName";
    String someReleaseKey = "someReleaseKey";
    String anotherReleaseKey = "anotherReleaseKey";

    InstanceConfig instanceConfig = instanceService.findInstanceConfig(someInstanceId,
        someConfigAppId, someConfigNamespaceName);

    assertNull(instanceConfig);

    instanceService.createInstanceConfig(assembleInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespaceName, someReleaseKey));

    instanceConfig = instanceService.findInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespaceName);

    assertNotEquals(0, instanceConfig.getId());
    assertEquals(someReleaseKey, instanceConfig.getReleaseKey());

    instanceConfig.setReleaseKey(anotherReleaseKey);

    instanceService.updateInstanceConfig(instanceConfig);

    InstanceConfig updated = instanceService.findInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespaceName);

    assertEquals(instanceConfig.getId(), updated.getId());
    assertEquals(anotherReleaseKey, updated.getReleaseKey());
  }

  @Test
  @Rollback
  public void testFindActiveInstanceConfigs() throws Exception {
    long someInstanceId = 1;
    long anotherInstanceId = 2;
    String someConfigAppId = "someConfigAppId";
    String someConfigNamespaceName = "someConfigNamespaceName";
    String someReleaseKey = "someReleaseKey";
    Date someValidDate = new Date();
    Pageable pageable = new PageRequest(0, 10);

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -2);
    Date someInvalidDate = calendar.getTime();

    InstanceConfig someValidConfig = assembleInstanceConfig(someInstanceId, someConfigAppId,
        someConfigNamespaceName, someReleaseKey);
    someValidConfig.setDataChangeCreatedTime(someValidDate);
    InstanceConfig someInvalidConfig = assembleInstanceConfig(anotherInstanceId, someConfigAppId,
        someConfigNamespaceName, someReleaseKey);
    someInvalidConfig.setDataChangeCreatedTime(someInvalidDate);

    instanceService.createInstanceConfig(someValidConfig);
    instanceService.createInstanceConfig(someInvalidConfig);

    List<InstanceConfig> validInstanceConfigs = instanceService
        .findActiveInstanceConfigsByReleaseKey(someReleaseKey, pageable);

    assertEquals(1, validInstanceConfigs.size());
    assertEquals(someInstanceId, validInstanceConfigs.get(0).getInstanceId());
  }

  private Instance assembleInstance(String appId, String clusterName, String dataCenter, String
      ip) {
    Instance instance = new Instance();
    instance.setAppId(appId);
    instance.setIp(ip);
    instance.setClusterName(clusterName);
    instance.setDataCenter(dataCenter);

    return instance;
  }

  private InstanceConfig assembleInstanceConfig(long instanceId, String configAppId, String
      configNamespaceName, String releaseKey) {
    InstanceConfig instanceConfig = new InstanceConfig();
    instanceConfig.setInstanceId(instanceId);
    instanceConfig.setConfigAppId(configAppId);
    instanceConfig.setConfigNamespaceName(configNamespaceName);
    instanceConfig.setReleaseKey(releaseKey);
    return instanceConfig;
  }
}