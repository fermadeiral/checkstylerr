package com.ctrip.apollo;

import com.ctrip.apollo.spi.ConfigFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class ConfigServiceTest extends ComponentTestCase {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    ConfigService.setContainer(getContainer());
  }

  @Test
  public void testHackConfig() {
    ConfigService.setConfig(new MockConfig("hack"));

    Config config = ConfigService.getConfig();

    Assert.assertEquals("hack:first", config.getProperty("first", null));
    Assert.assertEquals(null, config.getProperty("unknown", null));
  }

  @Test
  public void testMockConfigFactory() throws Exception {
    defineComponent(ConfigFactory.class, "mock", MockConfigFactory.class);

    Config config = ConfigService.getConfig("mock");

    Assert.assertEquals("mock:first", config.getProperty("first", null));
    Assert.assertEquals(null, config.getProperty("unknown", null));
  }

  private static class MockConfig implements Config {
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
}
