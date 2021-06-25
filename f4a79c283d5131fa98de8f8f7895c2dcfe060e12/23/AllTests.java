package com.ctrip.apollo;


import com.ctrip.apollo.internals.DefaultConfigManagerTest;
import com.ctrip.apollo.internals.DefaultConfigTest;
import com.ctrip.apollo.internals.RemoteConfigTest;
import com.ctrip.apollo.spi.DefaultConfigFactoryManagerTest;
import com.ctrip.apollo.spi.DefaultConfigFactoryTest;
import com.ctrip.apollo.spi.DefaultConfigRegistryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ConfigServiceTest.class, DefaultConfigRegistryTest.class, DefaultConfigFactoryManagerTest.class,
    DefaultConfigFactoryTest.class, DefaultConfigManagerTest.class, DefaultConfigTest.class,
    RemoteConfigTest.class
})
public class AllTests {

}
