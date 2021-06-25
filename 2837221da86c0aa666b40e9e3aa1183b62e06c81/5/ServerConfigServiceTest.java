package com.ctrip.framework.apollo.biz.service;

import com.ctrip.framework.apollo.biz.entity.ServerConfig;
import com.ctrip.framework.apollo.biz.repository.ServerConfigRepository;
import com.ctrip.framework.apollo.core.ConfigConsts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ServerConfigServiceTest {
  private ServerConfigService serverConfigService;
  @Mock
  private ServerConfigRepository serverConfigRepository;
  private String someCluster;
  private String someDC;
  private String someKey;
  private String someClusterValue;
  private String someDCValue;
  private String defaultClusterValue;

  @Before
  public void setUp() throws Exception {
    serverConfigService = spy(new ServerConfigService());
    ReflectionTestUtils.setField(serverConfigService, "serverConfigRepository", serverConfigRepository);

    someCluster = "someCluster";
    someDC = "someDC";
    someKey = "someKey";
    someClusterValue = "someClusterValue";
    someDCValue = "someDCValue";
    defaultClusterValue = "defaultClusterValue";

    when(serverConfigRepository.findTopByKeyAndCluster(someKey, someCluster))
        .thenReturn(assembleServerConfig(someKey, someClusterValue));
    when(serverConfigRepository.findTopByKeyAndCluster(someKey, someDC))
        .thenReturn(assembleServerConfig(someKey, someDCValue));
    when(serverConfigRepository.findTopByKeyAndCluster(someKey, ConfigConsts.CLUSTER_NAME_DEFAULT))
        .thenReturn(assembleServerConfig(someKey, defaultClusterValue));
  }

  @After
  public void tearDown() throws Exception {
    System.clearProperty(ConfigConsts.APOLLO_CLUSTER_KEY);
  }

  @Test
  public void testGetValueWithNoCluster() throws Exception {
    when(serverConfigService.getDataCenter()).thenReturn(null);

    assertEquals(defaultClusterValue, serverConfigService.getValue(someKey));
  }

  @Test
  public void testGetValueWithCluster() throws Exception {
    System.setProperty(ConfigConsts.APOLLO_CLUSTER_KEY, someCluster);

    assertEquals(someClusterValue, serverConfigService.getValue(someKey));
  }

  @Test
  public void testGetValueWithDataCenter() throws Exception {
    when(serverConfigService.getDataCenter()).thenReturn(someDC);

    assertEquals(someDCValue, serverConfigService.getValue(someKey));
  }

  @Test
  public void testGetValueWithKeyNotExists() throws Exception {
    String someKeyNotExists = "someKeyNotExists";
    System.setProperty(ConfigConsts.APOLLO_CLUSTER_KEY, someCluster);
    when(serverConfigService.getDataCenter()).thenReturn(someDC);

    assertNull(serverConfigService.getValue(someKeyNotExists));
  }

  @Test
  public void testGetValueWithClusterNotExists() throws Exception {
    String someClusterNotExists = "someClusterNotExists";
    System.setProperty(ConfigConsts.APOLLO_CLUSTER_KEY, someClusterNotExists);

    assertEquals(defaultClusterValue, serverConfigService.getValue(someKey));
  }

  @Test
  public void testGetValueWithDCNotExists() throws Exception {
    String someDCNotExists = "someDCNotExists";
    when(serverConfigService.getDataCenter()).thenReturn(someDCNotExists);

    assertEquals(defaultClusterValue, serverConfigService.getValue(someKey));
  }

  private ServerConfig assembleServerConfig(String key, String value) {
    ServerConfig serverConfig = new ServerConfig();
    serverConfig.setKey(key);
    serverConfig.setValue(value);
    return serverConfig;
  }
}
