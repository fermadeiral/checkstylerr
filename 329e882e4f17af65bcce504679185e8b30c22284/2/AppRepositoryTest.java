package com.ctrip.apollo.biz.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctrip.apollo.biz.SpringTestConfiguration;
import com.ctrip.apollo.biz.entity.App;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class AppRepositoryTest {

  @Autowired
  private AppRepository appRepository;

  @Before
  public void before() {
    appRepository.deleteAll();
  }

  @Test
  public void testCreate() {
    String appId = "someAppId";
    String appName = "someAppName";
    String ownerName = "someOwnerName";
    String ownerEmail = "someOwnerName@ctrip.com";

    App app = new App();
    app.setAppId(appId);
    app.setName(appName);
    app.setOwnerName(ownerName);
    app.setOwnerEmail(ownerEmail);

    Assert.assertEquals(0, appRepository.count());

    appRepository.save(app);

    Assert.assertEquals(1, appRepository.count());
  }

  @Test
  public void testRemove() {
    String appId = "someAppId";
    String appName = "someAppName";
    String ownerName = "someOwnerName";
    String ownerEmail = "someOwnerName@ctrip.com";

    App app = new App();
    app.setAppId(appId);
    app.setName(appName);
    app.setOwnerName(ownerName);
    app.setOwnerEmail(ownerEmail);

    Assert.assertEquals(0, appRepository.count());

    appRepository.save(app);

    Assert.assertEquals(1, appRepository.count());

    appRepository.delete(app.getId());

    Assert.assertEquals(0, appRepository.count());
  }
}
