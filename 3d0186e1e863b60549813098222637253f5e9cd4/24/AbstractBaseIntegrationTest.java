package com.ctrip.apollo.configservice.integration;

import com.ctrip.apollo.ConfigServiceTestConfiguration;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AbstractBaseIntegrationTest.TestConfiguration.class)
@WebIntegrationTest(randomPort = true)
public abstract class AbstractBaseIntegrationTest {
  RestTemplate restTemplate = new TestRestTemplate("user", "");

  @PostConstruct
  private void postConstruct() {
    restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
  }

  @Value("${local.server.port}")
  int port;

  protected String getHostUrl() {
    return "http://localhost:" + port;
  }

  @Configuration
  @Import(ConfigServiceTestConfiguration.class)
  protected static class TestConfiguration {


  }

}
