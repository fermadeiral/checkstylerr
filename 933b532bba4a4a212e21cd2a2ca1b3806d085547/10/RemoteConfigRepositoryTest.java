package com.ctrip.apollo.internals;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.ctrip.apollo.core.dto.ApolloConfig;
import com.ctrip.apollo.core.dto.ServiceDTO;
import com.ctrip.apollo.util.ConfigUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jason on 4/9/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RemoteConfigRepositoryTest {
  @Mock
  private RestTemplate restTemplate;
  @Mock
  private ConfigServiceLocator configServiceLocator;
  private String someNamespace;
  @Mock
  private ResponseEntity<ApolloConfig> someResponse;
  @Mock
  private ConfigUtil someConfigUtil;

  @Before
  public void setUp() throws Exception {
    someNamespace = "someName";
    String someServerUrl = "http://someServer";
    mockConfigServiceLocator(someServerUrl);

    String someAppId = "someApp";
    String someCluster = "someCluster";
    when(someConfigUtil.getAppId()).thenReturn(someAppId);
    when(someConfigUtil.getCluster()).thenReturn(someCluster);
  }

  @Test
  public void testLoadConfig() throws Exception {
    String someKey = "someKey";
    String someValue = "someValue";
    Map<String, String> configurations = Maps.newHashMap();
    configurations.put(someKey, someValue);
    ApolloConfig someApolloConfig = assembleApolloConfig(configurations);

    when(someResponse.getStatusCode()).thenReturn(HttpStatus.OK);
    when(someResponse.getBody()).thenReturn(someApolloConfig);
    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
            eq(ApolloConfig.class), anyMap())).thenReturn(someResponse);


    RemoteConfigRepository remoteConfigRepository = new RemoteConfigRepository(restTemplate, configServiceLocator, someConfigUtil, someNamespace);
    Properties config = remoteConfigRepository.loadConfig();

    assertEquals(configurations, config);
  }

  @Test(expected = RuntimeException.class)
  public void testGetRemoteConfigWithServerError() throws Exception {

    when(someResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
            eq(ApolloConfig.class), anyMap())).thenReturn(someResponse);

    RemoteConfigRepository remoteConfigRepository = new RemoteConfigRepository(restTemplate, configServiceLocator, someConfigUtil, someNamespace);
    remoteConfigRepository.loadConfig();
  }

  private ApolloConfig assembleApolloConfig(Map<String, String> configurations) {
    String someAppId = "appId";
    String someClusterName = "cluster";
    long someReleaseId = 1;
    ApolloConfig apolloConfig =
            new ApolloConfig(someAppId, someClusterName, someNamespace, someReleaseId);

    apolloConfig.setConfigurations(configurations);

    return apolloConfig;
  }

  private void mockConfigServiceLocator(String serverUrl) {
    ServiceDTO serviceDTO = mock(ServiceDTO.class);

    when(serviceDTO.getHomepageUrl()).thenReturn(serverUrl);
    when(configServiceLocator.getConfigServices()).thenReturn(Lists.newArrayList(serviceDTO));
  }
}
