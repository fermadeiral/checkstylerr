package com.ctrip.framework.apollo.spi;

import com.ctrip.framework.apollo.Config;

import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigFactoryManagerTest extends ComponentTestCase {
  private DefaultConfigFactoryManager defaultConfigFactoryManager;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    defineComponent(ConfigRegistry.class, MockConfigRegistry.class);
    defaultConfigFactoryManager =
        (DefaultConfigFactoryManager) lookup(ConfigFactoryManager.class);
  }

  @Test
  public void testGetFactoryFromRegistry() throws Exception {
    ConfigFactory result =
        defaultConfigFactoryManager.getFactory(MockConfigRegistry.NAMESPACE_REGISTERED);

    assertEquals(MockConfigRegistry.REGISTERED_CONFIGFACTORY, result);
  }

  @Test
  public void testGetFactoryFromNamespace() throws Exception {
    String someNamespace = "someName";
    defineComponent(ConfigFactory.class, someNamespace, SomeConfigFactory.class);

    ConfigFactory result = defaultConfigFactoryManager.getFactory(someNamespace);

    assertThat("When namespace is registered, should return the registerd config factory", result,
        instanceOf(SomeConfigFactory.class));
  }

  @Test
  public void testGetFactoryFromNamespaceMultipleTimes() throws Exception {
    String someNamespace = "someName";
    defineComponent(ConfigFactory.class, someNamespace, SomeConfigFactory.class);

    ConfigFactory result = defaultConfigFactoryManager.getFactory(someNamespace);
    ConfigFactory anotherResult = defaultConfigFactoryManager.getFactory(someNamespace);

    assertThat(
        "Get config factory with the same namespace multiple times should returnt the same instance",
        anotherResult, equalTo(result));
  }

  @Test
  public void testGetFactoryFromDefault() throws Exception {
    String someNamespace = "someName";
    defineComponent(ConfigFactory.class, AnotherConfigFactory.class);

    ConfigFactory result = defaultConfigFactoryManager.getFactory(someNamespace);

    assertThat("When namespace is not registered, should return the default config factory", result,
        instanceOf(AnotherConfigFactory.class));
  }

  public static class MockConfigRegistry implements ConfigRegistry {
    public static String NAMESPACE_REGISTERED = "some-namespace-registered";
    public static ConfigFactory REGISTERED_CONFIGFACTORY = new ConfigFactory() {
      @Override
      public Config create(String namespace) {
        return null;
      }
    };

    @Override
    public void register(String namespace, ConfigFactory factory) {
      //do nothing
    }

    @Override
    public ConfigFactory getFactory(String namespace) {
      if (namespace.equals(NAMESPACE_REGISTERED)) {
        return REGISTERED_CONFIGFACTORY;
      }
      return null;
    }
  }

  public static class SomeConfigFactory implements ConfigFactory {
    @Override
    public Config create(String namespace) {
      return null;
    }
  }

  public static class AnotherConfigFactory implements ConfigFactory {
    @Override
    public Config create(String namespace) {
      return null;
    }
  }

}
