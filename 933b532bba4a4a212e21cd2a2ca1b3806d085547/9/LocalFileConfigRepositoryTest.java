package com.ctrip.apollo.internals;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import com.ctrip.apollo.util.ConfigUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jason on 4/9/16.
 */
public class LocalFileConfigRepositoryTest {
  private File someBaseDir;
  private String someNamespace;
  private ConfigRepository fallbackRepo;
  private Properties someProperties;
  private ConfigUtil someConfigUtil;

  @Before
  public void setUp() throws Exception {
    someBaseDir = new File("src/test/resources/config-cache");
    someBaseDir.mkdir();

    someNamespace = "someName";
    someProperties = new Properties();
    someProperties.setProperty("defaultKey", "defaultValue");
    fallbackRepo = mock(ConfigRepository.class);
    when(fallbackRepo.loadConfig()).thenReturn(someProperties);

    String someAppId = "someApp";
    String someCluster = "someCluster";
    someConfigUtil = mock(ConfigUtil.class);
    when(someConfigUtil.getAppId()).thenReturn(someAppId);
    when(someConfigUtil.getCluster()).thenReturn(someCluster);

  }

  @After
  public void tearDown() throws Exception {
    recursiveDelete(someBaseDir);
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
  public void testLoadConfig() throws Exception {

  }

  @Test
  public void testLoadConfigWithLocalFile() throws Exception {
    File file = new File(someBaseDir, assembleLocalCacheFileName());

    String someKey = "someKey";
    String someValue = "someValue";

    Files.write(someKey + "=" + someValue, file, Charsets.UTF_8);

    LocalFileConfigRepository localRepo = new LocalFileConfigRepository(someBaseDir, someNamespace, someConfigUtil);
    Properties properties = localRepo.loadConfig();

    assertEquals(someValue, properties.getProperty(someKey));

  }

  @Test
  public void testLoadConfigWithNoLocalFile() throws Exception {
    LocalFileConfigRepository
        localFileConfigRepository =
        new LocalFileConfigRepository(someBaseDir, someNamespace, someConfigUtil);

    localFileConfigRepository.setFallback(fallbackRepo);

    Properties result = localFileConfigRepository.loadConfig();

    assertThat(
        "LocalFileConfigRepository's properties should be the same as fallback repo's when there is no local cache",
        result.entrySet(), equalTo(someProperties.entrySet()));
  }

  @Test
  public void testLoadConfigWithNoLocalFileMultipleTimes() throws Exception {
    LocalFileConfigRepository localRepo =
        new LocalFileConfigRepository(someBaseDir, someNamespace, someConfigUtil);

    localRepo.setFallback(fallbackRepo);

    Properties someProperties = localRepo.loadConfig();

    LocalFileConfigRepository
        anotherLocalRepoWithNoFallback =
        new LocalFileConfigRepository(someBaseDir, someNamespace, someConfigUtil);

    Properties anotherProperties = anotherLocalRepoWithNoFallback.loadConfig();

    assertThat(
        "LocalFileConfigRepository should persist local cache files and return that afterwards",
            someProperties.entrySet(), equalTo(anotherProperties.entrySet()));

  }

}