package com.ctrip.apollo.client;

import com.google.common.collect.Lists;

import com.ctrip.apollo.client.loader.ConfigLoaderManager;
import com.ctrip.apollo.client.model.PropertyChange;
import com.ctrip.apollo.client.model.PropertySourceReloadResult;
import com.ctrip.apollo.client.util.ConfigUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ApolloEnvironmentManagerTest {
  private ApolloEnvironmentManager apolloEnvironmentManager;
  @Mock
  private ConfigLoaderManager configLoaderManager;
  @Mock
  private ConfigUtil configUtil;
  @Mock
  private ApolloEnvironment apolloEnvironment;

  @Before
  public void setUp() throws Exception {
    apolloEnvironmentManager = spy(new ApolloEnvironmentManager(apolloEnvironment));
    ReflectionTestUtils
        .setField(apolloEnvironmentManager, "configLoaderManager", configLoaderManager);
    ReflectionTestUtils.setField(apolloEnvironmentManager, "configUtil", configUtil);

    int someInterval = 1;
    TimeUnit someUnit = TimeUnit.MINUTES;
    when(configUtil.getRefreshInterval()).thenReturn(someInterval);
    when(configUtil.getRefreshTimeUnit()).thenReturn(someUnit);
  }

  @Test
  public void testInit() throws Exception {
    CompositePropertySource somePropertySource = mock(CompositePropertySource.class);

    when(configLoaderManager.loadPropertySource()).thenReturn(somePropertySource);

    apolloEnvironmentManager.init();

    verify(configLoaderManager, times(1)).loadPropertySource();
    verify(apolloEnvironment, times(1)).updatePropertySource(somePropertySource);
  }

  @Test
  public void testUpdatePropertySource() throws Exception {
    PropertySourceReloadResult someResult = mock(PropertySourceReloadResult.class);
    CompositePropertySource somePropertySource = mock(CompositePropertySource.class);
    List<PropertyChange> someChanges = Lists.newArrayList();

    when(someResult.hasChanges()).thenReturn(true);
    when(someResult.getPropertySource()).thenReturn(somePropertySource);
    when(someResult.getChanges()).thenReturn(someChanges);
    when(configLoaderManager.reloadPropertySource()).thenReturn(someResult);

    apolloEnvironmentManager.updatePropertySource();

    verify(configLoaderManager, times(1)).reloadPropertySource();
    verify(apolloEnvironment, times(1)).updatePropertySource(somePropertySource, someChanges);
  }
}
