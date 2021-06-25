package com.ctrip.framework.apollo.portal;


import com.ctrip.framework.apollo.openapi.filter.ConsumerAuthenticationFilterTest;
import com.ctrip.framework.apollo.openapi.service.ConsumerRolePermissionServiceTest;
import com.ctrip.framework.apollo.openapi.service.ConsumerServiceTest;
import com.ctrip.framework.apollo.openapi.util.ConsumerAuditUtilTest;
import com.ctrip.framework.apollo.openapi.util.ConsumerAuthUtilTest;
import com.ctrip.framework.apollo.portal.components.txtresolver.FileTextResolverTest;
import com.ctrip.framework.apollo.portal.components.txtresolver.PropertyResolverTest;
import com.ctrip.framework.apollo.portal.controller.ConsumerControllerTest;
import com.ctrip.framework.apollo.portal.service.AppNamespaceServiceTest;
import com.ctrip.framework.apollo.portal.service.ConfigServiceTest;
import com.ctrip.framework.apollo.portal.service.NamespaceServiceTest;
import com.ctrip.framework.apollo.portal.service.RoleInitializationServiceTest;
import com.ctrip.framework.apollo.portal.service.RolePermissionServiceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ConfigServiceTest.class, PropertyResolverTest.class,
    NamespaceServiceTest.class, ServiceExceptionTest.class, RolePermissionServiceTest.class,
    AppNamespaceServiceTest.class, RoleInitializationServiceTest.class, FileTextResolverTest.class,
    RetryableRestTemplateTest.class, ConsumerRolePermissionServiceTest.class,
    ConsumerAuthenticationFilterTest.class, ConsumerAuthUtilTest.class, ConsumerServiceTest.class,
    ConsumerControllerTest.class, ConsumerAuditUtilTest.class
})
public class AllTests {

}
