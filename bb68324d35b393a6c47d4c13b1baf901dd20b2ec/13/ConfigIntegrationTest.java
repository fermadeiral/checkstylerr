package com.ctrip.apollo.integration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.ctrip.apollo.Config;
import com.ctrip.apollo.ConfigChangeListener;
import com.ctrip.apollo.ConfigService;
import com.ctrip.apollo.core.ConfigConsts;
import com.ctrip.apollo.core.dto.ApolloConfig;
import com.ctrip.apollo.core.utils.ClassLoaderUtil;
import com.ctrip.apollo.model.ConfigChangeEvent;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class ConfigIntegrationTest extends BaseIntegrationTest {
  private long someReleaseId;
  private File configDir;
  private String someNamespace;

  @Before
  public void setUp() throws Exception {
    super.setUp();

    someNamespace = ConfigConsts.NAMESPACE_APPLICATION;
    someReleaseId = 1;
    configDir = new File(ClassLoaderUtil.getClassPath() + "config-cache");
    configDir.mkdirs();
  }

  @Override
  @After
  public void tearDown() throws Exception {
    recursiveDelete(configDir);
    super.tearDown();
  }

  private void recursiveDelete(File file) {
    if (!file.exists()) {
      return;
    }
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        recursiveDelete(f);
      }
    }
    try {
      Files.deleteIfExists(file.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void testGetConfigWithNoLocalFileButWithRemoteConfig() throws Exception {
    String someKey = "someKey";
    String someValue = "someValue";
    String someNonExistedKey = "someNonExistedKey";
    String someDefaultValue = "someDefaultValue";
    ApolloConfig apolloConfig = assembleApolloConfig(ImmutableMap.of(someKey, someValue));
    ContextHandler handler = mockConfigServerHandler(HttpServletResponse.SC_OK, apolloConfig);
    startServerWithHandlers(handler);

    Config config = ConfigService.getConfig();

    assertEquals(someValue, config.getProperty(someKey, null));
    assertEquals(someDefaultValue, config.getProperty(someNonExistedKey, someDefaultValue));
  }

  @Test
  public void testGetConfigWithLocalFileAndWithRemoteConfig() throws Exception {
    String someKey = "someKey";
    String someValue = "someValue";
    String anotherValue = "anotherValue";
    Properties properties = new Properties();
    properties.put(someKey, someValue);
    createLocalCachePropertyFile(properties);

    ApolloConfig apolloConfig = assembleApolloConfig(ImmutableMap.of(someKey, anotherValue));
    ContextHandler handler = mockConfigServerHandler(HttpServletResponse.SC_OK, apolloConfig);
    startServerWithHandlers(handler);

    Config config = ConfigService.getConfig();

    assertEquals(anotherValue, config.getProperty(someKey, null));
  }

  @Test
  public void testGetConfigWithNoLocalFileAndRemoteConfigError() throws Exception {
    ContextHandler handler =
        mockConfigServerHandler(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    startServerWithHandlers(handler);

    Config config = ConfigService.getConfig();

    String someKey = "someKey";
    String someDefaultValue = "defaultValue" + Math.random();

    assertEquals(someDefaultValue, config.getProperty(someKey, someDefaultValue));
  }

  @Test
  public void testGetConfigWithLocalFileAndRemoteConfigError() throws Exception {
    String someKey = "someKey";
    String someValue = "someValue";
    Properties properties = new Properties();
    properties.put(someKey, someValue);
    createLocalCachePropertyFile(properties);

    ContextHandler handler =
        mockConfigServerHandler(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    startServerWithHandlers(handler);

    Config config = ConfigService.getConfig();
    assertEquals(someValue, config.getProperty(someKey, null));
  }

  @Test
  public void testGetConfigWithNoLocalFileAndRemoteMetaServiceRetry() throws Exception {
    String someKey = "someKey";
    String someValue = "someValue";
    ApolloConfig apolloConfig = assembleApolloConfig(ImmutableMap.of(someKey, someValue));
    ContextHandler configHandler = mockConfigServerHandler(HttpServletResponse.SC_OK, apolloConfig);
    boolean failAtFirstTime = true;
    ContextHandler metaServerHandler = mockMetaServerHandler(failAtFirstTime);
    startServerWithHandlers(metaServerHandler, configHandler);

    Config config = ConfigService.getConfig();

    assertEquals(someValue, config.getProperty(someKey, null));
  }

  @Test
  public void testGetConfigWithNoLocalFileAndRemoteConfigServiceRetry() throws Exception {
    String someKey = "someKey";
    String someValue = "someValue";
    ApolloConfig apolloConfig = assembleApolloConfig(ImmutableMap.of(someKey, someValue));
    boolean failedAtFirstTime = true;
    ContextHandler handler =
        mockConfigServerHandler(HttpServletResponse.SC_OK, apolloConfig, failedAtFirstTime);
    startServerWithHandlers(handler);

    Config config = ConfigService.getConfig();

    assertEquals(someValue, config.getProperty(someKey, null));
  }

  @Test
  public void testRefreshConfig() throws Exception {
    final String someKey = "someKey";
    final String someValue = "someValue";
    final String anotherValue = "anotherValue";

    int someRefreshInterval = 500;
    TimeUnit someRefreshTimeUnit = TimeUnit.MILLISECONDS;

    setRefreshInterval(someRefreshInterval);
    setRefreshTimeUnit(someRefreshTimeUnit);

    Map<String, String> configurations = Maps.newHashMap();
    configurations.put(someKey, someValue);
    ApolloConfig apolloConfig = assembleApolloConfig(configurations);
    ContextHandler handler = mockConfigServerHandler(HttpServletResponse.SC_OK, apolloConfig);
    startServerWithHandlers(handler);

    Config config = ConfigService.getConfig();
    final List<ConfigChangeEvent> changeEvents = Lists.newArrayList();

    config.addChangeListener(new ConfigChangeListener() {
      @Override
      public void onChange(ConfigChangeEvent changeEvent) {
        assertEquals(1, changeEvent.getChanges().size());
        assertEquals(someValue, changeEvent.getChange(someKey).getOldValue());
        assertEquals(anotherValue, changeEvent.getChange(someKey).getNewValue());
        // if there is any assertion failed above, this line won't be executed
        changeEvents.add(changeEvent);
      }
    });

    apolloConfig.getConfigurations().put(someKey, anotherValue);

    Thread.sleep(someRefreshTimeUnit.toMillis(someRefreshInterval * 2));

    assertThat(
        "Change event's size should equal to one or there must be some assertion failed in change listener",
        1, equalTo(changeEvents.size()));
    assertEquals(anotherValue, config.getProperty(someKey, null));
  }

  private ContextHandler mockConfigServerHandler(final int statusCode, final ApolloConfig result,
                                                 final boolean failedAtFirstTime) {
    ContextHandler context = new ContextHandler("/config/*");
    context.setHandler(new AbstractHandler() {
      AtomicInteger counter = new AtomicInteger(0);

      @Override
      public void handle(String target, Request baseRequest, HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {
        if (failedAtFirstTime && counter.incrementAndGet() == 1) {
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          baseRequest.setHandled(true);
          return;
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);

        response.getWriter().println(gson.toJson(result));

        baseRequest.setHandled(true);
      }
    });

    return context;

  }


  private ContextHandler mockConfigServerHandler(int statusCode, ApolloConfig result) {
    return mockConfigServerHandler(statusCode, result, false);
  }

  private ApolloConfig assembleApolloConfig(Map<String, String> configurations) {
    ApolloConfig apolloConfig =
        new ApolloConfig(someAppId, someClusterName, someNamespace, someReleaseId);

    apolloConfig.setConfigurations(configurations);

    return apolloConfig;
  }

  private File createLocalCachePropertyFile(Properties properties) throws IOException {
    File file = new File(configDir, assembleLocalCacheFileName());
    FileOutputStream in = null;
    try {
      in = new FileOutputStream(file);
      properties.store(in, "Persisted by ConfigIntegrationTest");
    } finally {
      if (in != null) {
        in.close();
      }
    }
    return file;
  }

  private String assembleLocalCacheFileName() {
    return String.format("%s-%s-%s.properties", someAppId, someClusterName, someNamespace);
  }
}
