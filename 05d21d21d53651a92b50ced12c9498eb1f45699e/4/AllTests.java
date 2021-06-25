package com.ctrip.framework.apollo.adminservice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ctrip.framework.apollo.adminservice.aop.NamespaceLockTest;
import com.ctrip.framework.apollo.adminservice.controller.AppControllerTest;
import com.ctrip.framework.apollo.adminservice.controller.ControllerExceptionTest;
import com.ctrip.framework.apollo.adminservice.controller.ControllerIntegrationExceptionTest;
import com.ctrip.framework.apollo.adminservice.controller.ItemSetControllerTest;
import com.ctrip.framework.apollo.adminservice.controller.ReleaseControllerTest;

@RunWith(Suite.class)
@SuiteClasses({AppControllerTest.class, ReleaseControllerTest.class, ItemSetControllerTest.class,
               ControllerExceptionTest.class, ControllerIntegrationExceptionTest.class, NamespaceLockTest.class})
public class AllTests {

}
