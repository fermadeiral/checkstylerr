package com.ctrip.apollo.internals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleConfigTest {
  private String someNamespace;
  @Mock
  private ConfigRepository configRepository;

  @Before
  public void setUp() throws Exception {
    someNamespace = "someName";
  }

  @Test
  public void testGetProperty() throws Exception {
    Properties someProperties = new Properties();
    String someKey = "someKey";
    String someValue = "someValue";
    someProperties.setProperty(someKey, someValue);

    when(configRepository.loadConfig()).thenReturn(someProperties);

    SimpleConfig config = new SimpleConfig(someNamespace, configRepository);

    assertEquals(someValue, config.getProperty(someKey, null));
  }

  @Test(expected = RuntimeException.class)
  public void testLoadConfigFromConfigRepositoryError() throws Exception {
    when(configRepository.loadConfig()).thenThrow(Throwable.class);

    new SimpleConfig(someNamespace, configRepository);
  }
}
