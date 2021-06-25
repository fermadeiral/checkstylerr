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
import com.ctrip.framework.apollo.core.dto.AppDTO;
import com.ctrip.framework.apollo.core.exception.ServiceException;
import com.ctrip.framework.apollo.portal.controller.AppController;
import com.ctrip.framework.apollo.portal.service.AppService;

import com.google.gson.Gson;

public class ServiceExceptionTest extends AbstractPortalTest {

  @Autowired
  private AppController appController;
  @Mock
  private AppService appService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(appController, "appService", appService);
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

    when(appService.createOrUpdateAppInLocal(any(App.class))).thenThrow(adminException);

    App app = generateSampleApp();
    try {
      restTemplate.postForEntity(getBaseAppUrl(), app, AppDTO.class);
    } catch (HttpStatusCodeException e) {
      @SuppressWarnings("unchecked")
      Map<String, String> attr = new Gson().fromJson(e.getResponseBodyAsString(), Map.class);
      System.out.println(ExceptionUtils.toString(e));
      Assert.assertEquals("No available admin service", attr.get("message"));
      Assert.assertEquals("8848", attr.get("errorCode"));
    }
  }

  private App generateSampleApp() {
    App app = new App();
    app.setAppId("someAppId");
    app.setName("someName");
    app.setOwnerName("someOwner");
    app.setOwnerEmail("someOwner@ctrip.com");
    return app;
  }
}
