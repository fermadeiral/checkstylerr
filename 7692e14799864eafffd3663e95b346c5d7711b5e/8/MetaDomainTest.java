package com.ctrip.apollo.core;

import org.junit.Assert;
import org.junit.Test;

import com.ctrip.apollo.Apollo.Env;

public class MetaDomainTest {

  @Test
  public void testGetMetaDomain() {
    Assert.assertEquals("http://localhost:8090", MetaDomainConsts.getDomain(Env.LOCAL));
    Assert.assertEquals("http://dev:8080", MetaDomainConsts.getDomain(Env.DEV));
    Assert.assertNull(MetaDomainConsts.getDomain(Env.PRO));
  }
}
