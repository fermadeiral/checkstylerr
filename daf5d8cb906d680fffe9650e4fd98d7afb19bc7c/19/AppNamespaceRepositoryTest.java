package com.ctrip.framework.apollo.biz.repository;

import com.ctrip.framework.apollo.biz.BizTestConfiguration;
import com.ctrip.framework.apollo.common.entity.AppNamespace;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BizTestConfiguration.class)
@Transactional
@Rollback
public class AppNamespaceRepositoryTest {

  @Autowired
  private AppNamespaceRepository repository;

  @Test
  public void testFindByNameAndIsPublicTrue() throws Exception {
    AppNamespace appNamespace = repository.findByNameAndIsPublicTrue("fx.apollo.config");

    assertEquals("100003171", appNamespace.getAppId());
  }

  @Test
  public void testFindByNameAndNoPublicNamespace() throws Exception {
    AppNamespace appNamespace = repository.findByNameAndIsPublicTrue("application");

    assertNull(appNamespace);
  }
}
