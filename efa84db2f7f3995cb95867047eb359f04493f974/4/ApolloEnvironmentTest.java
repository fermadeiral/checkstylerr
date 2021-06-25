package com.ctrip.apollo.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ApolloEnvironmentTest {
  private ApolloEnvironment apolloEnvironment;
  @Mock
  private ApolloEnvironmentManager apolloEnvironmentManager;

  @Before
  public void setUp() throws Exception {
    apolloEnvironment = spy(ApolloEnvironment.getInstance());
    ReflectionTestUtils
        .setField(apolloEnvironment, "apolloEnvironmentManager", apolloEnvironmentManager);

  }

  @Test
  public void testInit() throws Exception {
    apolloEnvironment.init();

    verify(apolloEnvironmentManager, times(1)).init();
  }

  @Test
  public void testGetProperty() throws Exception {
    CompositePropertySource somePropertySource = mock(CompositePropertySource.class);
    String someKey = "someKey";
    String someValue = "someValue";
    apolloEnvironment.updatePropertySource(somePropertySource);

    when(somePropertySource.getProperty(someKey)).thenReturn(someValue);

    String result = apolloEnvironment.getProperty(someKey);

    assertEquals(someValue, result);
  }

  @Test
  public void testGetPropertyWithDefaultValue() throws Exception {
    CompositePropertySource somePropertySource = mock(CompositePropertySource.class);
    String someKey = "someKey";
    String someDefaultValue = "someDefault";
    apolloEnvironment.updatePropertySource(somePropertySource);

    when(somePropertySource.getProperty(someKey)).thenReturn(null);

    String result = apolloEnvironment.getProperty(someKey, someDefaultValue);

    assertEquals(someDefaultValue, result);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPropertyWithNoPropertySource() throws Exception {
    String someKey = "someKey";
    apolloEnvironment.getProperty(someKey);
  }
}
