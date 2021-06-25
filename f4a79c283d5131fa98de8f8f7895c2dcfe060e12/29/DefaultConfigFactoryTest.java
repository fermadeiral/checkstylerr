package com.ctrip.apollo.spi;

import com.ctrip.apollo.Config;
import com.ctrip.apollo.internals.DefaultConfig;
import com.ctrip.apollo.internals.RemoteConfig;

import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigFactoryTest extends ComponentTestCase {
  private DefaultConfigFactory defaultConfigFactory;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    defaultConfigFactory = spy((DefaultConfigFactory) lookup(ConfigFactory.class, "default"));
  }

  @Test
  public void testCreate() throws Exception {
    String someNamespace = "someName";
    Properties someProperties = new Properties();
    String someKey = "someKey";
    String someValue = "someValue";
    someProperties.setProperty(someKey, someValue);

    RemoteConfig someRemoteConfig = mock(RemoteConfig.class);
    when(someRemoteConfig.loadConfig()).thenReturn(someProperties);

    doReturn(someRemoteConfig).when(defaultConfigFactory).createRemoteConfig(someNamespace);

    Config result = defaultConfigFactory.create(someNamespace);

    assertThat("DefaultConfigFactory should create DefaultConfig", result,
        is(instanceOf(DefaultConfig.class)));
    assertThat("DefaultConfigFactory should set remote config as the fallback loader",
        ((DefaultConfig) result).getFallbackLoader(), instanceOf(RemoteConfig.class));
  }

}
