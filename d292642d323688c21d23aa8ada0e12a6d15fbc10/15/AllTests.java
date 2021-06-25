package com.ctrip.framework.apollo.portal;


import com.ctrip.framework.apollo.portal.service.RolePermissionServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ConfigServiceTest.class, PropertyResolverTest.class,
    NamespaceServiceTest.class, ServiceExceptionTest.class, RolePermissionServiceTest.class
})
public class AllTests {

}
