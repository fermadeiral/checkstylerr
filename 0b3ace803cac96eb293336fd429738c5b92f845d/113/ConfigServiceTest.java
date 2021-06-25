package com.ctrip.framework.apollo;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.internals.AbstractConfig;
import com.ctrip.framework.apollo.spi.ConfigFactory;
import com.ctrip.framework.apollo.util.ConfigUtil;

import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import static org.junit.Assert.assertEquals;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class ConfigServiceTest extends ComponentTestCase {
  private static String someAppId;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    someAppId = "someAppId";
    //as ConfigService is singleton, so we must manually clear its container
    ConfigService.setContainer(getContainer());
    defineComponent(ConfigUtil.class, MockConfigUtil.class);
  }

  @Test
  public void testHackConfig() {
    String someNamespace = "hack";
    String someKey = "first";
    ConfigService.setConfig(new MockConfig(someNamespace));

    Config config = ConfigService.getAppConfig();

    assertEquals(someNamespace + ":" + someKey, config.getProperty(someKey, null));
    assertEquals(null, config.getProperty("unknown", null));
  }

  @Test
  public void testHackConfigFactory() throws Exception {
    String someKey = "someKey";
    ConfigService.setConfigFactory(new MockConfigFactory());

    Config config = ConfigService.getAppConfig();

    assertEquals(ConfigConsts.NAMESPACE_DEFAULT + ":" + someKey,
        config.getProperty(someKey, null));
  }

  @Test
  public void testMockConfigFactory() throws Exception {
    String someNamespace = "mock";
    String someKey = "someKey";
    defineComponent(ConfigFactory.class, someNamespace, MockConfigFactory.class);

    Config config = ConfigService.getConfig(someNamespace);

    assertEquals(someNamespace + ":" + someKey, config.getProperty(someKey, null));
    assertEquals(null, config.getProperty("unknown", null));
  }

  private static class MockConfig extends AbstractConfig {
    private final String m_namespace;

    public MockConfig(String namespace) {
      m_namespace = namespace;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
      if (key.equals("unknown")) {
        return null;
      }

      return m_namespace + ":" + key;
    }
  }

  public static class MockConfigFactory implements ConfigFactory {
    @Override
    public Config create(String namespace) {
      return new MockConfig(namespace);
    }
  }

  public static class MockConfigUtil extends ConfigUtil {
    @Override
    public String getAppId() {
      return someAppId;
    }
  }

}
