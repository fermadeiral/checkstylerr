package com.ctrip.apollo.client.loader;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.ctrip.apollo.client.enums.PropertyChangeType;
import com.ctrip.apollo.client.model.ApolloRegistry;
import com.ctrip.apollo.client.model.PropertyChange;
import com.ctrip.apollo.client.model.PropertySourceReloadResult;
import com.ctrip.apollo.client.util.ConfigUtil;
import com.ctrip.apollo.core.dto.ApolloConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigLoaderManagerTest {
  private ConfigLoaderManager configLoaderManager;
  @Mock
  private ConfigLoader configLoader;
  @Mock
  private ConfigUtil configUtil;

  @Before
  public void setUp() {
    configLoaderManager = spy(new ConfigLoaderManager(configLoader, configUtil));
  }

  @Test
  public void testLoadPropertySource() throws Exception {
    String someAppId = "100";
    String anotherAppId = "101";
    ApolloRegistry someApolloRegistry = assembleSomeApolloRegistry(someAppId, "someVersion");
    ApolloRegistry
        anotherApolloRegistry =
        assembleSomeApolloRegistry(anotherAppId, "anotherVersion");
    ApolloConfig someApolloConfig = mock(ApolloConfig.class);
    ApolloConfig anotherApolloConfig = mock(ApolloConfig.class);
    Map<String, Object> someMap = mock(Map.class);
    Map<String, Object> anotherMap = mock(Map.class);

    when(someApolloConfig.getAppId()).thenReturn(someAppId);
    when(someApolloConfig.getAppId()).thenReturn(anotherAppId);
    when(configUtil.loadApolloRegistries())
        .thenReturn(Lists.newArrayList(someApolloRegistry, anotherApolloRegistry));
    doReturn(someApolloConfig).when(configLoaderManager).loadSingleApolloConfig(someApolloRegistry);
    doReturn(anotherApolloConfig).when(configLoaderManager)
        .loadSingleApolloConfig(anotherApolloRegistry);
    when(someApolloConfig.getConfigurations()).thenReturn(someMap);
    when(anotherApolloConfig.getConfigurations()).thenReturn(anotherMap);

    CompositePropertySource result = configLoaderManager.loadPropertySource();

    assertEquals(2, result.getPropertySources().size());

    List<Map<String, Object>>
        resultMaps =
        FluentIterable.from(result.getPropertySources())
            .transform(new Function<PropertySource<?>, Map<String, Object>>() {
              @Override
              public Map<String, Object> apply(PropertySource<?> input) {
                return (Map<String, Object>) input.getSource();
              }
            }).toList();

    assertTrue(resultMaps.containsAll(Lists.newArrayList(someMap, anotherMap)));
  }

  @Test(expected = RuntimeException.class)
  public void testLoadPropertySourceWithError() throws Exception {
    Exception someException = mock(Exception.class);
    String someAppId = "100";
    ApolloRegistry someApolloRegistry = assembleSomeApolloRegistry(someAppId, "someVersion");
    when(configUtil.loadApolloRegistries()).thenReturn(Lists.newArrayList(someApolloRegistry));

    doThrow(someException).when(configLoaderManager).loadSingleApolloConfig(someApolloRegistry);

    configLoaderManager.loadPropertySource();
  }

  @Test
  public void testLoadApolloConfigsWithNoApolloRegistry() throws Exception {
    when(configUtil.loadApolloRegistries()).thenReturn(null);

    CompositePropertySource result = configLoaderManager.loadPropertySource();

    assertTrue(result.getPropertySources().isEmpty());
  }

  @Test
  public void testLoadSingleApolloConfig() throws Exception {
    ApolloConfig someApolloConfig = mock(ApolloConfig.class);
    Map<String, Object> someMap = Maps.newHashMap();
    String someAppId = "100";
    ApolloRegistry someApolloRegistry = assembleSomeApolloRegistry(someAppId, "someVersion");
    ApolloConfig previousConfig = null;

    doReturn(null).when(configLoaderManager).getPreviousApolloConfig(someApolloRegistry);
    when(someApolloConfig.getConfigurations()).thenReturn(someMap);
    when(configLoader.loadApolloConfig(someApolloRegistry, previousConfig))
        .thenReturn(someApolloConfig);

    ApolloConfig result = configLoaderManager.loadSingleApolloConfig(someApolloRegistry);

    assertEquals(someMap, result.getConfigurations());
  }

  @Test
  public void testReloadPropertySource() throws Exception {
    String someAppId = "100";
    ApolloRegistry someApolloRegistry = assembleSomeApolloRegistry(someAppId, "someVersion");
    ApolloConfig someApolloConfig = mock(ApolloConfig.class);
    Map<String, Object> someMap = mock(Map.class);
    List<PropertyChange> someChanges = mock(List.class);
    ReflectionTestUtils
        .setField(configLoaderManager, "apolloRegistries", Lists.newArrayList(someApolloRegistry));

    doReturn(someApolloConfig).when(configLoaderManager).loadSingleApolloConfig(someApolloRegistry);
    when(someApolloConfig.getAppId()).thenReturn(someAppId);
    when(someApolloConfig.getConfigurations()).thenReturn(someMap);
    doReturn(someChanges).when(configLoaderManager).calcPropertyChanges(anyList(), anyList());

    PropertySourceReloadResult result = configLoaderManager.reloadPropertySource();

    assertEquals(1, result.getPropertySource().getPropertySources().size());
    assertEquals(someChanges, result.getChanges());
    List<Map<String, Object>>
        resultMaps =
        FluentIterable.from(result.getPropertySource().getPropertySources())
            .transform(new Function<PropertySource<?>, Map<String, Object>>() {
              @Override
              public Map<String, Object> apply(PropertySource<?> input) {
                return (Map<String, Object>) input.getSource();
              }
            }).toList();

    assertTrue(resultMaps.containsAll(Lists.newArrayList(someMap)));
  }

  @Test
  public void testCalcPropertyChanges() throws Exception {
    String someAppId = "1";
    Map<String, Object> someConfig = Maps.newHashMap();
    someConfig.put("key1", "val1");
    someConfig.put("key2", "val2");

    Map<String, Object> anotherConfig = Maps.newHashMap();
    anotherConfig.put("key1", "val11");
    anotherConfig.put("key3", "val3");

    List<ApolloConfig> previous = Lists.newArrayList(assembleApolloConfig(someAppId, someConfig));
    List<ApolloConfig> current = Lists.newArrayList(assembleApolloConfig(someAppId, anotherConfig));

    List<PropertyChange> changes = configLoaderManager.calcPropertyChanges(previous, current);

    assertEquals(3, changes.size());

    List<String>
        changeResult =
        FluentIterable.from(changes).transform(new Function<PropertyChange, String>() {
          @Override
          public String apply(PropertyChange input) {
            return String.format("%s-%s", input.getPropertyName(), input.getChangeType());
          }
        }).toList();

    assertTrue(changeResult.containsAll(
        Lists.newArrayList(
            "key1-" + PropertyChangeType.MODIFIED,
            "key2-" + PropertyChangeType.DELETED,
            "key3-" + PropertyChangeType.NEW
        )));
  }

  ApolloConfig assembleApolloConfig(String appId, Map<String, Object> configurations) {
    String someCluster = "someCluster";
    String someVersion = "someVersion";
    long someReleaseId = 1;

    ApolloConfig config = new ApolloConfig(appId, someCluster, someVersion, someReleaseId);

    config.setConfigurations(configurations);

    return config;
  }

  private ApolloRegistry assembleSomeApolloRegistry(String someAppId, String someVersion) {
    ApolloRegistry someApolloRegistry = new ApolloRegistry();
    someApolloRegistry.setAppId(someAppId);
    someApolloRegistry.setVersion(someVersion);

    return someApolloRegistry;
  }
}
