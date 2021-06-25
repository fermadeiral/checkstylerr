package com.ctrip.framework.apollo.portal;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.ctrip.framework.apollo.common.entity.App;
import com.ctrip.framework.apollo.common.utils.ExceptionUtils;
import com.ctrip.framework.apollo.common.dto.AppDTO;
import com.ctrip.framework.apollo.common.exception.ServiceException;
import com.ctrip.framework.apollo.portal.controller.AppController;
import com.ctrip.framework.apollo.portal.service.UserService;

import com.google.gson.Gson;

public class ServiceExceptionTest extends AbstractIntegrationTest {

  @Autowired
  private AppController appController;
  @Mock
  private UserService userService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(appController, "userService", userService);
  }

  private String getBaseAppUrl() {
    return "http://localhost:" + port + "/apps";
  }

  @Test
  public void testAdminServiceException() {
    Map<String, Object> errorAttributes = new LinkedHashMap<>();
    errorAttributes.put("status", 500);
    errorAttributes.put("message", "No available admin service");
    errorAttributes.put("timestamp",
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    errorAttributes.put("exception", ServiceException.class.getName());
    errorAttributes.put("errorCode", "8848");

    HttpStatusCodeException adminException =
        new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "admin server error",
            new Gson().toJson(errorAttributes).getBytes(), Charset.defaultCharset());

    when(userService.findByUserId(any(String.class))).thenThrow(adminException);

    App app = generateSampleApp();
    try {
      restTemplate.postForEntity(getBaseAppUrl(), app, AppDTO.class);
    } catch (HttpStatusCodeException e) {
      @SuppressWarnings("unchecked")
      Map<String, String> attr = new Gson().fromJson(e.getResponseBodyAsString(), Map.class);
      Assert.assertEquals("500 admin server error", attr.get("message"));
      Assert.assertEquals(500.0, attr.get("status"));
    }
  }

  private App generateSampleApp() {
    App app = new App();
    app.setAppId("someAppId");
    app.setName("someName");
    app.setOrgId("someOrgId");
    app.setOrgName("someOrgNam");
    app.setOwnerName("someOwner");
    app.setOwnerEmail("someOwner@ctrip.com");
    return app;
  }
}
