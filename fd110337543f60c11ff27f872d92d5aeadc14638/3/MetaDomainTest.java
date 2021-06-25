package com.ctrip.apollo.core;

import org.junit.Test;
import org.springframework.util.Assert;

import com.ctrip.apollo.Apollo.Env;

public class MetaDomainTest {

  @Test
  public void testDefaultDomain() {
    Assert.notNull(MetaDomainConsts.getDomain(Env.LOCAL));
    Assert.isNull(MetaDomainConsts.getDomain(Env.PRO));
  }
}
