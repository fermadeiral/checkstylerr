package com.ctrip.framework.apollo.mockserver;

import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.mockserver.SpringIntegrationTest.TestConfiguration;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import static org.junit.Assert.*;

import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Create by zhangzheng on 8/16/18
 * Email:zhangzheng@youzan.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
public class SpringIntegrationTest {
  @Autowired
  TestBean testBean;

  @ClassRule
  public static EmbeddedApollo embeddedApollo = new EmbeddedApollo();

  @Test
  @DirtiesContext
  public void testPropertyInject(){
    assertEquals("value1", testBean.key1);
    assertEquals("value2", testBean.key2);
  }

  @Test
  @DirtiesContext
  public void testListenerTriggeredByAdd() throws InterruptedException, ExecutionException, TimeoutException {
    String otherNamespace = "othernamespace";
    embeddedApollo.addOrModifyPropery(otherNamespace,"someKey","someValue");
    ConfigChangeEvent changeEvent = testBean.futureData.get(5000, TimeUnit.MILLISECONDS);
    assertEquals(otherNamespace, changeEvent.getNamespace());
    assertEquals("someValue", changeEvent.getChange("someKey").getNewValue());
  }

  @Test
  @DirtiesContext
  public void testListenerTriggeredByDel()
      throws InterruptedException, ExecutionException, TimeoutException {
    String otherNamespace = "othernamespace";
    embeddedApollo.delete(otherNamespace, "key1");
    ConfigChangeEvent changeEvent = testBean.futureData.get(5000, TimeUnit.MILLISECONDS);
    assertEquals(otherNamespace, changeEvent.getNamespace());
    assertEquals(PropertyChangeType.DELETED, changeEvent.getChange("key1").getChangeType());
  }








  @EnableApolloConfig("application")
  @Configuration
  static class TestConfiguration{
    @Bean
    public TestBean testBean(){
      return new TestBean();
    }
  }

  static class TestBean{
    @Value("${key1:default}")
    String key1;
    @Value("${key2:default}")
    String key2;

    SettableFuture<ConfigChangeEvent> futureData = SettableFuture.create();

    @ApolloConfigChangeListener("othernamespace")
    private void onChange(ConfigChangeEvent changeEvent) {
      futureData.set(changeEvent);
    }
  }


}
