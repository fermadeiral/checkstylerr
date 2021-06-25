package com.ctrip.apollo.biz.eureka;

import com.ctrip.apollo.biz.entity.ServerConfig;
import com.ctrip.apollo.biz.repository.ServerConfigRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ApolloEurekaClientConfigTest {
  private ApolloEurekaClientConfig eurekaClientConfig;
  @Mock
  private ServerConfigRepository serverConfigRepository;
  @Mock
  private Environment environment;

  @Before
  public void setUp() throws Exception {
    eurekaClientConfig = new ApolloEurekaClientConfig();
    ReflectionTestUtils.setField(eurekaClientConfig, "serverConfigRepository", serverConfigRepository);
    ReflectionTestUtils.setField(eurekaClientConfig, "environment", environment);
  }

  @Test
  public void testGetEurekaServerServiceUrlsFromDB() throws Exception {
    String someEurekaUrl = "http://xxx,http://yyy";
    String myZone = "xx";

    when(serverConfigRepository.findByKey(ApolloEurekaClientConfig.EUREKA_URL_CONFIG))
        .thenReturn(assembleServerConfig(ApolloEurekaClientConfig.EUREKA_URL_CONFIG, someEurekaUrl));

    List<String> eurekaUrls = eurekaClientConfig.getEurekaServerServiceUrls(myZone);
    String[] expected = someEurekaUrl.split(",");

    assertEquals(expected.length, eurekaUrls.size());
    for (String url : expected) {
      assertTrue(eurekaUrls.contains(url));
    }
  }

  @Test
  public void testGetEurekaServiceUrlsFromSystemProperty() throws Exception {
    String someEurekaUrl = "http://xxx,http://yyy";
    String myZone = "xx";
    String someEurekaUrlFromSystemProperty = "http://zzz";

    when(environment.getProperty(ApolloEurekaClientConfig.EUREKA_URL_CONFIG))
        .thenReturn(someEurekaUrlFromSystemProperty);
    when(serverConfigRepository.findByKey(ApolloEurekaClientConfig.EUREKA_URL_CONFIG))
        .thenReturn(assembleServerConfig(ApolloEurekaClientConfig.EUREKA_URL_CONFIG, someEurekaUrl));

    List<String> eurekaUrls = eurekaClientConfig.getEurekaServerServiceUrls(myZone);

    String[] expected = someEurekaUrlFromSystemProperty.split(",");
    assertEquals(expected.length, eurekaUrls.size());
    for (String url : expected) {
      assertTrue(eurekaUrls.contains(url));
    }
  }

  private ServerConfig assembleServerConfig(String key, String value) {
    ServerConfig config = new ServerConfig();
    config.setKey(key);
    config.setValue(value);
    return config;
  }
}
