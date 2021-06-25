package com.ctrip.framework.apollo.portal;


import com.ctrip.framework.apollo.portal.service.AppNamespaceServiceTest;
import com.ctrip.framework.apollo.portal.service.ConfigServiceTest;
import com.ctrip.framework.apollo.portal.service.NamespaceServiceTest;
import com.ctrip.framework.apollo.portal.service.RoleInitializationServiceTest;
import com.ctrip.framework.apollo.portal.service.RolePermissionServiceTest;
import com.ctrip.framework.apollo.portal.service.txtresolver.FileTextResolverTest;
import com.ctrip.framework.apollo.portal.service.txtresolver.PropertyResolverTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ConfigServiceTest.class, PropertyResolverTest.class,
    NamespaceServiceTest.class, ServiceExceptionTest.class, RolePermissionServiceTest.class,
    AppNamespaceServiceTest.class, RoleInitializationServiceTest.class, FileTextResolverTest.class,
    RetryableRestTemplateTest.class
})
public class AllTests {

}
