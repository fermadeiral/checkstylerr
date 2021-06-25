package com.ctrip.apollo.biz.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ctrip.apollo.biz.BizTestConfiguration;
import com.ctrip.apollo.biz.entity.App;
import com.ctrip.apollo.core.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BizTestConfiguration.class)
@Transactional
@Rollback
public class ClusterServiceTest {

  @Autowired
  private AdminService adminService;

  @Autowired
  private ClusterService clusterService;

  @Test(expected = ServiceException.class)
  public void testCreateDuplicateCluster() {
    String appId = "someAppId";
    App app = new App();
    app.setAppId(appId);
    app.setName("someAppName");
    String owner = "someOwnerName";
    app.setOwnerName(owner);
    app.setOwnerEmail("someOwnerName@ctrip.com");
    app.setDataChangeCreatedBy(owner);
    app.setDataChangeLastModifiedBy(owner);
    app.setDataChangeCreatedTime(new Date());

    adminService.createNewApp(app);

    clusterService.createDefaultCluster(appId, owner);
  }
}
