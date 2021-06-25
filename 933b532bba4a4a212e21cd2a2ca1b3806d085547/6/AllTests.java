package com.ctrip.apollo;


import com.ctrip.apollo.internals.DefaultConfigManagerTest;
import com.ctrip.apollo.internals.DefaultConfigTest;
import com.ctrip.apollo.internals.LocalFileConfigRepositoryTest;
import com.ctrip.apollo.internals.RemoteConfigRepositoryTest;
import com.ctrip.apollo.internals.SimpleConfigTest;
import com.ctrip.apollo.spi.DefaultConfigFactoryManagerTest;
import com.ctrip.apollo.spi.DefaultConfigFactoryTest;
import com.ctrip.apollo.spi.DefaultConfigRegistryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ConfigServiceTest.class, DefaultConfigRegistryTest.class, DefaultConfigFactoryManagerTest.class,
    DefaultConfigManagerTest.class, DefaultConfigTest.class, LocalFileConfigRepositoryTest.class,
    RemoteConfigRepositoryTest.class, SimpleConfigTest.class, DefaultConfigFactoryTest.class
})
public class AllTests {

}
