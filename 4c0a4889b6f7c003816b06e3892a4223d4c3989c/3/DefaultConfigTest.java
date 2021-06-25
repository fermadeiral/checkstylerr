package com.ctrip.apollo.internals;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import com.ctrip.apollo.core.utils.ClassLoaderUtil;
import com.ctrip.apollo.util.ConfigUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import java.io.File;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigTest extends ComponentTestCase {
  private File someBaseDir;
  private File someResourceDir;
  private String someNamespace;
  private ConfigLoader fallbackLoader;
  private Properties someProperties;
  private ConfigUtil someConfigUtil;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    someBaseDir = new File("src/test/resources/config-cache");
    someBaseDir.mkdir();

    someResourceDir = new File(ClassLoaderUtil.getClassPath() + "/META-INF/config");
    someResourceDir.mkdir();

    someNamespace = "someName";
    someProperties = new Properties();
    someProperties.setProperty("defaultKey", "defaultValue");
    fallbackLoader = mock(RemoteConfig.class);
    when(fallbackLoader.loadConfig()).thenReturn(someProperties);

    String someAppId = "someApp";
    String someCluster = "someCluster";
    someConfigUtil = mock(ConfigUtil.class);
    when(someConfigUtil.getAppId()).thenReturn(someAppId);
    when(someConfigUtil.getCluster()).thenReturn(someCluster);
  }

  @Override
  @After
  public void tearDown() throws Exception {
    recursiveDelete(someBaseDir);
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

  private String assembleLocalCacheFileName() {
    return String.format("%s-%s-%s.properties", someConfigUtil.getAppId(),
        someConfigUtil.getCluster(), someNamespace);
  }

  @Test
  public void testGetPropertyWithLocalFile() throws Exception {
    File file = new File(someBaseDir, assembleLocalCacheFileName());

    String someKey = "someKey";
    String someValue = "someValue";

    Files.write(someKey + "=" + someValue, file, Charsets.UTF_8);

    DefaultConfig defaultConfig = new DefaultConfig(someBaseDir, someNamespace, fallbackLoader, someConfigUtil);

    file.delete();

    assertEquals(someValue, defaultConfig.getProperty(someKey, null));

  }

  @Test
  public void testGetPropertyWithLocalResource() throws Exception {
    File file = new File(someResourceDir, someNamespace + ".properties");
    file.deleteOnExit();
    Files.createParentDirs(file);

    String someKey = "someKey";
    String someValue = "someValue";

    Files.write(someKey + "=" + someValue, file, Charsets.UTF_8);

    DefaultConfig
        defaultConfig =
        new DefaultConfig(someBaseDir, someNamespace, fallbackLoader, someConfigUtil);

    file.delete();
    assertEquals(someValue, defaultConfig.getProperty(someKey, null));
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

    //set up local file
    File localCacheFile = new File(someBaseDir, assembleLocalCacheFileName());
    Files.write(someKey + "=" + someLocalFileValue, localCacheFile, Charsets.UTF_8);
    Files.append(System.getProperty("line.separator"), localCacheFile, Charsets.UTF_8);
    Files.append(anotherKey + "=" + someLocalFileValue, localCacheFile, Charsets.UTF_8);

    //set up resource file
    File resourceFile = new File(someResourceDir, someNamespace + ".properties");
    Files.write(someKey + "=" + someResourceValue, resourceFile, Charsets.UTF_8);
    Files.append(System.getProperty("line.separator"), resourceFile, Charsets.UTF_8);
    Files.append(anotherKey + "=" + someResourceValue, resourceFile, Charsets.UTF_8);
    Files.append(System.getProperty("line.separator"), resourceFile, Charsets.UTF_8);
    Files.append(lastKey + "=" + someResourceValue, resourceFile, Charsets.UTF_8);

    DefaultConfig
        defaultConfig =
        new DefaultConfig(someBaseDir, someNamespace, fallbackLoader, someConfigUtil);

    String someKeyValue = defaultConfig.getProperty(someKey, null);
    String anotherKeyValue = defaultConfig.getProperty(anotherKey, null);
    String lastKeyValue = defaultConfig.getProperty(lastKey, null);

    localCacheFile.delete();
    resourceFile.delete();

    assertEquals(someSystemPropertyValue, someKeyValue);
    assertEquals(someLocalFileValue, anotherKeyValue);
    assertEquals(someResourceValue, lastKeyValue);

  }

  @Test
  public void testInitLocalConfigWithNoLocalFile() throws Exception {
    DefaultConfig
        defaultConfig =
        new DefaultConfig(someBaseDir, someNamespace, fallbackLoader, someConfigUtil);

    Properties result = defaultConfig.loadConfig();

    assertThat(
        "Default config's properties should be the same as fallback loader's when there is no local cache",
        result.entrySet(), equalTo(someProperties.entrySet()));
  }

  @Test
  public void testInitLocalConfigWithNoLocalFileMultipleTimes() throws Exception {
    Properties anotherProperties = new Properties();
    anotherProperties.setProperty("anotherKey", "anotherValue");
    ConfigLoader anotherLoader = mock(RemoteConfig.class);
    when(anotherLoader.loadConfig()).thenReturn(anotherProperties);

    DefaultConfig
        defaultConfig =
        new DefaultConfig(someBaseDir, someNamespace, fallbackLoader, someConfigUtil);
    DefaultConfig
        anotherConfig =
        new DefaultConfig(someBaseDir, someNamespace, anotherLoader, someConfigUtil);

    assertThat(
        "Default config should persist local cache files and return that afterwards",
        defaultConfig.loadConfig().entrySet(), equalTo(anotherConfig.loadConfig().entrySet()));

  }
}
