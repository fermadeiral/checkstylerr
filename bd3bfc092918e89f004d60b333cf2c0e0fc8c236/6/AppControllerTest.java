package com.ctrip.apollo.adminservice.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.web.client.HttpClientErrorException;

import com.ctrip.apollo.biz.entity.App;
import com.ctrip.apollo.biz.repository.AppRepository;
import com.ctrip.apollo.common.utils.BeanUtils;
import com.ctrip.apollo.core.dto.AppDTO;

public class AppControllerTest extends AbstractControllerTest {

  @Autowired
  AppRepository appRepository;

  private String getBaseAppUrl() {
    return "http://localhost:" + port + "/apps/";
  }

  @Test
  public void testCheckIfAppIdUnique() {
    AppDTO dto = generateSampleDTOData();
    ResponseEntity<AppDTO> response =
        restTemplate.postForEntity(getBaseAppUrl(), dto, AppDTO.class);
    AppDTO result = response.getBody();
    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(dto.getAppId(), result.getAppId());
    Assert.assertTrue(result.getId() > 0);

    Boolean falseUnique =
        restTemplate.getForObject(getBaseAppUrl() + dto.getAppId() + "/unique", Boolean.class);
    Assert.assertFalse(falseUnique);
    Boolean trueUnique = restTemplate
        .getForObject(getBaseAppUrl() + dto.getAppId() + "true" + "/unique", Boolean.class);
    Assert.assertTrue(trueUnique);
  }

  @Test
  @Sql(scripts = "/controller/cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  public void testCreate() {
    AppDTO dto = generateSampleDTOData();
    ResponseEntity<AppDTO> response =
        restTemplate.postForEntity(getBaseAppUrl(), dto, AppDTO.class);
    AppDTO result = response.getBody();
    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(dto.getAppId(), result.getAppId());
    Assert.assertTrue(result.getId() > 0);

    App savedApp = appRepository.findOne(result.getId());
    Assert.assertEquals(dto.getAppId(), savedApp.getAppId());
    Assert.assertNotNull(savedApp.getDataChangeCreatedTime());
  }

  @Test
  @Sql(scripts = "/controller/cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  public void testCreateTwice() {
    AppDTO dto = generateSampleDTOData();
    ResponseEntity<AppDTO> response =
        restTemplate.postForEntity(getBaseAppUrl(), dto, AppDTO.class);
    AppDTO first = response.getBody();
    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(dto.getAppId(), first.getAppId());
    Assert.assertTrue(first.getId() > 0);

    App savedApp = appRepository.findOne(first.getId());
    Assert.assertEquals(dto.getAppId(), savedApp.getAppId());
    Assert.assertNotNull(savedApp.getDataChangeCreatedTime());

    response = restTemplate.postForEntity(getBaseAppUrl(), dto, AppDTO.class);
    AppDTO second = response.getBody();
    Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assert.assertEquals(dto.getAppId(), second.getAppId());
    Assert.assertEquals(first.getId(), second.getId());

    savedApp = appRepository.findOne(second.getId());
    Assert.assertEquals(dto.getAppId(), savedApp.getAppId());
    Assert.assertNotNull(savedApp.getDataChangeCreatedTime());
    Assert.assertNotNull(savedApp.getDataChangeLastModifiedTime());
  }

  @Test
  @Sql(scripts = "/controller/cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  public void testFind() {
    AppDTO dto = generateSampleDTOData();
    App app = BeanUtils.transfrom(App.class, dto);
    app = appRepository.save(app);

    AppDTO result = restTemplate.getForObject(getBaseAppUrl() + dto.getAppId(), AppDTO.class);
    Assert.assertEquals(dto.getAppId(), result.getAppId());
    Assert.assertEquals(dto.getName(), result.getName());
  }

  @Test(expected = HttpClientErrorException.class)
  @Sql(scripts = "/controller/cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  public void testFindNotExist() {
    restTemplate.getForEntity(getBaseAppUrl() + "notExists", AppDTO.class);
  }

  @Test
  @Sql(scripts = "/controller/cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  public void testDelete() {
    AppDTO dto = generateSampleDTOData();
    App app = BeanUtils.transfrom(App.class, dto);
    app = appRepository.save(app);

    restTemplate.delete(getBaseAppUrl() + dto.getAppId());

    App deletedApp = appRepository.findOne(app.getId());
    Assert.assertNull(deletedApp);
  }

  @Test
  @Sql(scripts = "/controller/cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  public void testUpdate() {
    AppDTO dto = generateSampleDTOData();
    App app = BeanUtils.transfrom(App.class, dto);
    app = appRepository.save(app);

    dto.setName("newName");
    restTemplate.postForObject(getBaseAppUrl(), dto, AppDTO.class);

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
