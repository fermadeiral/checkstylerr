package com.ctrip.apollo.biz.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctrip.apollo.biz.SpringTestConfiguration;
import com.ctrip.apollo.biz.entity.App;
import com.ctrip.apollo.biz.entity.Cluster;
import com.ctrip.apollo.biz.entity.Namespace;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestConfiguration.class)
public class AdminServiceTest {

  @Autowired
  private AdminService adminService;

  @Autowired
  private ViewService viewService;

  @Test
  public void testCreateNewApp() {
    String appId = "someAppId";
    String appName = "someAppName";
    String ownerName = "someOwnerName";
    String ownerEmail = "someOwnerName@ctrip.com";
    String namespace = "someNamespace";

    App app = adminService.createNewApp(appId, appName, ownerName, ownerEmail, namespace);
    Assert.assertEquals(appId, app.getAppId());

    List<Cluster> clusters = viewService.findClusters(app.getAppId());
    Assert.assertEquals(1, clusters.size());
    Assert.assertEquals("default", clusters.get(0).getName());

    List<Namespace> namespaces = viewService.findNamespaces(appId, clusters.get(0).getName());
    Assert.assertEquals(1, namespaces.size());
    Assert.assertEquals(namespace, namespaces.get(0).getNamespaceName());
  }
}
