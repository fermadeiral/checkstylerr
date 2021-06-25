package com.ctrip.apollo.adminservice.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.ctrip.apollo.AdminServiceTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AdminServiceTestConfiguration.class)
@WebIntegrationTest(randomPort = true)
public abstract class AbstractControllerTest {

  RestTemplate restTemplate = new TestRestTemplate("user", "");

  @Value("${local.server.port}")
  int port;
}
