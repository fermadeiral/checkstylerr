package com.ctrip.apollo.adminservice.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.ctrip.apollo.AdminServiceTestConfiguration;
import com.ctrip.apollo.biz.entity.App;
import com.ctrip.apollo.biz.repository.AppRepository;
import com.ctrip.apollo.biz.utils.BeanUtils;
import com.ctrip.apollo.core.dto.AppDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AdminServiceTestConfiguration.class)
@WebIntegrationTest
public class AppControllerTest {

  RestTemplate restTemplate = new TestRestTemplate();

  @Autowired
  AppRepository appRepository;

  @Test
  public void testCreate() {
    AppDTO dto = generateSampleDTOData();
    ResponseEntity<AppDTO> response =
        restTemplate.postForEntity("http://localhost:8090/apps/", dto, AppDTO.class);
    AppDTO result = response.getBody();
    Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Assert.assertEquals(dto.getAppId(), result.getAppId());
    Assert.assertTrue(result.getId() > 0);

    App savedApp = appRepository.findOne(result.getId());
    Assert.assertEquals(dto.getAppId(), savedApp.getAppId());
    Assert.assertNotNull(savedApp.getDataChangeCreatedTime());

    appRepository.delete(savedApp.getId());
  }

  @Test
  public void testFind() {
    AppDTO dto = generateSampleDTOData();
    App app = BeanUtils.transfrom(App.class, dto);
    app = appRepository.save(app);

    AppDTO result =
        restTemplate.getForObject("http://localhost:8090/apps/" + dto.getAppId(), AppDTO.class);
    Assert.assertEquals(dto.getAppId(), result.getAppId());
    Assert.assertEquals(dto.getName(), result.getName());

    appRepository.delete(app.getId());
  }

  @Test
  public void testFindNotExist() {
    ResponseEntity<AppDTO> result =
        restTemplate.getForEntity("http://localhost:8090/apps/" + "notExists", AppDTO.class);
    Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  @Test
  public void testDelete() {
    AppDTO dto = generateSampleDTOData();
    App app = BeanUtils.transfrom(App.class, dto);
    app = appRepository.save(app);

    restTemplate.delete("http://localhost:8090/apps/" + dto.getAppId());

    App deletedApp = appRepository.findOne(app.getId());
    Assert.assertNull(deletedApp);
  }

  @Test
  public void testUpdate() {
    AppDTO dto = generateSampleDTOData();
    App app = BeanUtils.transfrom(App.class, dto);
    app = appRepository.save(app);

    dto.setName("newName");
    restTemplate.put("http://localhost:8090/apps/" + dto.getAppId(), dto);

    App updatedApp = appRepository.findOne(app.getId());
    Assert.assertEquals(dto.getName(), updatedApp.getName());
    Assert.assertNotNull(updatedApp.getDataChangeLastModifiedTime());
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
