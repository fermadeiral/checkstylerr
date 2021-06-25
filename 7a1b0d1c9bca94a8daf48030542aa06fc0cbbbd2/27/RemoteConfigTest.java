package com.ctrip.apollo.internals;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.ctrip.apollo.core.dto.ApolloConfig;
import com.ctrip.apollo.core.dto.ServiceDTO;

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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class RemoteConfigTest {
  @Mock
  private RestTemplate restTemplate;
  @Mock
  private ConfigServiceLocator configServiceLocator;
  private String someNamespace;
  @Mock
  private ResponseEntity<ApolloConfig> someResponse;

  @Before
  public void setUp() throws Exception {
    someNamespace = "someName";

    String someServerUrl = "http://someServer";
    mockConfigServiceLocator(someServerUrl);
  }

  @Test
  public void testGetProperty() throws Exception {
    String someKey = "someKey";
    String someValue = "someValue";
    String someKeyNotExisted = "key-not-existed";
    String someDefaultValue = "someDefault";
    Map<String, String> configurations = Maps.newHashMap();
    configurations.put(someKey, someValue);
    ApolloConfig someApolloConfig = assembleApolloConfig(configurations);

    when(someResponse.getStatusCode()).thenReturn(HttpStatus.OK);
    when(someResponse.getBody()).thenReturn(someApolloConfig);
    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
        eq(ApolloConfig.class), anyMap())).thenReturn(someResponse);

    RemoteConfig remoteConfig = new RemoteConfig(restTemplate, configServiceLocator, someNamespace);

    assertEquals(someValue, remoteConfig.getProperty(someKey, null));
    assertEquals(someDefaultValue, remoteConfig.getProperty(someKeyNotExisted, someDefaultValue));
  }

  @Test(expected = RuntimeException.class)
  public void testGetRemoteConfigWithServerError() throws Exception {

    when(someResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
        eq(ApolloConfig.class), anyMap())).thenReturn(someResponse);

    RemoteConfig remoteConfig = new RemoteConfig(restTemplate, configServiceLocator, someNamespace);
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


    RemoteConfig remoteConfig = new RemoteConfig(restTemplate, configServiceLocator, someNamespace);

    Properties config = remoteConfig.loadConfig();

    assertEquals(configurations, config);
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
