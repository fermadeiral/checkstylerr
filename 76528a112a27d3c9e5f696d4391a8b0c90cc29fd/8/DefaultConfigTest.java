package com.ctrip.apollo.internals;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import com.ctrip.apollo.core.utils.ClassLoaderUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigTest {
  private File someResourceDir;
  private String someNamespace;
  private ConfigRepository configRepository;
  private Properties someProperties;

  @Before
  public void setUp() throws Exception {
    someResourceDir = new File(ClassLoaderUtil.getClassPath() + "/META-INF/config");
    someResourceDir.mkdir();
    someNamespace = "someName";
    configRepository = mock(ConfigRepository.class);
  }

  @After
  public void tearDown() throws Exception {
    recursiveDelete(someResourceDir);
  }

  //helper method to clean created files
  private void recursiveDelete(File file) {
    if (!file.exists()) {
      return;
    }
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        recursiveDelete(f);
      }
    }
    file.delete();
  }

  @Test
  public void testGetPropertyWithAllPropertyHierarchy() throws Exception {
    String someKey = "someKey";
    String someSystemPropertyValue = "system-property-value";

    String anotherKey = "anotherKey";
    String someLocalFileValue = "local-file-value";

    String lastKey = "lastKey";
    String someResourceValue = "resource-value";

    //set up system property
    System.setProperty(someKey, someSystemPropertyValue);

    //set up config repo
    someProperties = new Properties();
    someProperties.setProperty(someKey, someLocalFileValue);
    someProperties.setProperty(anotherKey, someLocalFileValue);
    when(configRepository.loadConfig()).thenReturn(someProperties);

    //set up resource file
    File resourceFile = new File(someResourceDir, someNamespace + ".properties");
    Files.write(someKey + "=" + someResourceValue, resourceFile, Charsets.UTF_8);
    Files.append(System.getProperty("line.separator"), resourceFile, Charsets.UTF_8);
    Files.append(anotherKey + "=" + someResourceValue, resourceFile, Charsets.UTF_8);
    Files.append(System.getProperty("line.separator"), resourceFile, Charsets.UTF_8);
    Files.append(lastKey + "=" + someResourceValue, resourceFile, Charsets.UTF_8);

    DefaultConfig
            defaultConfig =
            new DefaultConfig(someNamespace, configRepository);

    String someKeyValue = defaultConfig.getProperty(someKey, null);
    String anotherKeyValue = defaultConfig.getProperty(anotherKey, null);
    String lastKeyValue = defaultConfig.getProperty(lastKey, null);

    //clean up
    System.clearProperty(someKey);

    assertEquals(someSystemPropertyValue, someKeyValue);
    assertEquals(someLocalFileValue, anotherKeyValue);
    assertEquals(someResourceValue, lastKeyValue);

  }
}
