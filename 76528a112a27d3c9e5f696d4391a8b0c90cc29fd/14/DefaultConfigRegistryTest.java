package com.ctrip.apollo.spi;

import com.ctrip.apollo.Config;

import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigRegistryTest extends ComponentTestCase {
  private DefaultConfigRegistry defaultConfigRegistry;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    defaultConfigRegistry = (DefaultConfigRegistry) lookup(ConfigRegistry.class, "default");
  }

  @Test
  public void testGetFactory() throws Exception {
    String someNamespace = "someName";
    ConfigFactory someConfigFactory = new MockConfigFactory();

    defaultConfigRegistry.register(someNamespace, someConfigFactory);

    assertThat("Should return the registered config factory",
        defaultConfigRegistry.getFactory(someNamespace), equalTo(someConfigFactory));
  }

  @Test
  public void testGetFactoryWithNamespaceUnregistered() throws Exception {
    String someUnregisteredNamespace = "someName";

    assertNull(defaultConfigRegistry.getFactory(someUnregisteredNamespace));
  }

  public static class MockConfigFactory implements ConfigFactory {

    @Override
    public Config create(String namespace) {
      return null;
    }
  }
}
