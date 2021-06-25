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

import com.ctrip.framework.apollo.common.utils.ExceptionUtils;
import com.ctrip.framework.apollo.core.dto.AppDTO;
import com.ctrip.framework.apollo.core.enums.Env;
import com.ctrip.framework.apollo.core.exception.ServiceException;
import com.ctrip.framework.apollo.portal.api.AdminServiceAPI;
import com.ctrip.framework.apollo.portal.service.PortalAppService;

import com.google.gson.Gson;

public class ServiceExceptionTest extends AbstractPortalTest {

  @Autowired
  private PortalAppService appService;

  @Mock
  private AdminServiceAPI.AppAPI appAPI;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(appService, "appAPI", appAPI);
  }

  private String getBaseAppUrl() {
    return "http://localhost:" + port + "/apps/";
  }

  @Test
  public void testAdminServiceException() {
    Map<String, Object> errorAttributes = new LinkedHashMap<>();
    errorAttributes.put("status", 500);
    errorAttributes.put("message", "admin server error");
    errorAttributes.put("timestamp",
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    errorAttributes.put("exception", ServiceException.class.getName());
    errorAttributes.put("errorCode", "8848");

    HttpStatusCodeException adminException =
        new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "admin server error",
            new Gson().toJson(errorAttributes).getBytes(), Charset.defaultCharset());
    when(appAPI.createApp(any(Env.class), any(AppDTO.class))).thenThrow(adminException);

    AppDTO dto = generateSampleDTOData();
    try {
      restTemplate.postForEntity(getBaseAppUrl(), dto, AppDTO.class);
    } catch (HttpStatusCodeException e) {
      @SuppressWarnings("unchecked")
      Map<String, String> attr = new Gson().fromJson(e.getResponseBodyAsString(), Map.class);
      System.out.println(ExceptionUtils.toString(e));
      Assert.assertEquals("admin server error", attr.get("message"));
      Assert.assertEquals("8848", attr.get("errorCode"));
    }
  }

  private AppDTO generateSampleDTOData() {
    AppDTO dto = new AppDTO();
    dto.setAppId("someAppId");
    dto.setName("someName");
    dto.setOwnerName("someOwner");
    dto.setOwnerEmail("someOwner@ctrip.com");
    return dto;
  }
}
