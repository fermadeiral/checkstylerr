package com.ctrip.framework.apollo.spi;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import com.ctrip.framework.apollo.internals.DefaultConfig;
import com.ctrip.framework.apollo.internals.LocalFileConfigRepository;
import com.ctrip.framework.apollo.internals.PropertiesConfigFile;
import com.ctrip.framework.apollo.internals.XmlConfigFile;

import org.junit.Before;
import org.junit.Test;
import org.unidal.lookup.ComponentTestCase;

import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfigFactoryTest extends ComponentTestCase {
  private DefaultConfigFactory defaultConfigFactory;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    defaultConfigFactory = spy((DefaultConfigFactory) lookup(ConfigFactory.class));
  }

  @Test
  public void testCreate() throws Exception {
    String someNamespace = "someName";
    Properties someProperties = new Properties();
    String someKey = "someKey";
    String someValue = "someValue";
    someProperties.setProperty(someKey, someValue);

    LocalFileConfigRepository someLocalConfigRepo = mock(LocalFileConfigRepository.class);
    when(someLocalConfigRepo.getConfig()).thenReturn(someProperties);

    doReturn(someLocalConfigRepo).when(defaultConfigFactory).createLocalConfigRepository(someNamespace);

    Config result = defaultConfigFactory.create(someNamespace);

    assertThat("DefaultConfigFactory should create DefaultConfig", result,
        is(instanceOf(DefaultConfig.class)));
    assertEquals(someValue, result.getProperty(someKey, null));
  }

  @Test
  public void testCreateConfigFile() throws Exception {
    String someNamespace = "someName";
    String anotherNamespace = "anotherName";
    Properties someProperties = new Properties();

    LocalFileConfigRepository someLocalConfigRepo = mock(LocalFileConfigRepository.class);
    when(someLocalConfigRepo.getConfig()).thenReturn(someProperties);

    doReturn(someLocalConfigRepo).when(defaultConfigFactory).createLocalConfigRepository(someNamespace);
    doReturn(someLocalConfigRepo).when(defaultConfigFactory).createLocalConfigRepository(anotherNamespace);

    ConfigFile propertyConfigFile =
        defaultConfigFactory.createConfigFile(someNamespace, ConfigFileFormat.Properties);
    ConfigFile xmlConfigFile =
        defaultConfigFactory.createConfigFile(anotherNamespace, ConfigFileFormat.XML);

    assertThat("Should create PropertiesConfigFile for properties format", propertyConfigFile, is(instanceOf(
        PropertiesConfigFile.class)));
    assertEquals(someNamespace, propertyConfigFile.getNamespace());

    assertThat("Should create XmlConfigFile for xml format", xmlConfigFile, is(instanceOf(
        XmlConfigFile.class)));
    assertEquals(anotherNamespace, xmlConfigFile.getNamespace());
  }

}
