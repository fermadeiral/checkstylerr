package com.ctrip.framework.apollo.mockserver;

import static org.junit.Assert.assertEquals;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.google.common.util.concurrent.SettableFuture;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.WebIntegrationTest;

/**
 * Create by zhangzheng on 8/10/18
 * Email:zhangzheng@youzan.com
 */
public class ApolloMockServerTest {




  @ClassRule
  public static EmbeddedApollo embeddedApollo = new EmbeddedApollo();

  @Test
  public void testPropertyInject() {
    String namespace = "application";
    Config config = ConfigService.getConfig(namespace);
    assertEquals("value1",config.getProperty("key1",""));
    assertEquals("value2",config.getProperty("key2",""));

    String otherNamespace = "othernamespace";
    config = ConfigService.getConfig(otherNamespace);
    assertEquals("othervalue1",config.getProperty("key1",""));
    assertEquals("othervalue2",config.getProperty("key2",""));
  }

  @Test
  public void testListennerTriggeredByAdd()
      throws InterruptedException, ExecutionException, TimeoutException {
    System.setProperty("apollo.longPollingInitialDelayInMills","1");
    String someNamespace = "application";
    Config config = ConfigService.getConfig(someNamespace);
    SettableFuture<Boolean> changed = SettableFuture.create();
    String someKey = "someKey";
    String someValue = "someValue";
    config.addChangeListener(new ConfigChangeListener() {
      @Override
      public void onChange(ConfigChangeEvent changeEvent) {
        assertEquals(true, changeEvent.isChanged(someKey));
        assertEquals(someValue, changeEvent.getChange(someKey).getNewValue());
        changed.set(true);
      }
    });
    embeddedApollo.addOrModifyPropery(someNamespace, someKey, someValue);
    assertEquals(true, changed.get(1000, TimeUnit.MILLISECONDS));
  }


  @Test
  public void testListennerTriggeredByDel()
      throws InterruptedException, ExecutionException, TimeoutException {
    String someNamespace = "application";
    Config config = ConfigService.getConfig(someNamespace);
    SettableFuture<Boolean> changed = SettableFuture.create();
    String someKey = "key1";
    config.addChangeListener(new ConfigChangeListener() {
      @Override
      public void onChange(ConfigChangeEvent changeEvent) {
        assertEquals(true, changeEvent.isChanged(someKey));
        assertEquals(PropertyChangeType.DELETED, changeEvent.getChange(someKey).getChangeType());
        changed.set(true);
      }
    });
    embeddedApollo.delete(someNamespace, someKey);
    assertEquals(true, changed.get(1000, TimeUnit.MILLISECONDS));

  }



}
