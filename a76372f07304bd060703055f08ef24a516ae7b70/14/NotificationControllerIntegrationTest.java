package com.ctrip.apollo.configservice.integration;

import com.ctrip.apollo.biz.message.Topics;
import com.ctrip.apollo.configservice.controller.NotificationController;
import com.ctrip.apollo.core.ConfigConsts;
import com.ctrip.apollo.core.dto.ApolloConfigNotification;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class NotificationControllerIntegrationTest extends AbstractBaseIntegrationTest {
  @Autowired
  private NotificationController notificationController;
  private String someAppId;
  private String someCluster;
  private String defaultNamespace;
  private String somePublicNamespace;
  private ExecutorService executorService;

  @Before
  public void setUp() throws Exception {
    someAppId = "someAppId";
    someCluster = ConfigConsts.CLUSTER_NAME_DEFAULT;
    defaultNamespace = ConfigConsts.NAMESPACE_DEFAULT;
    somePublicNamespace = "somePublicNamespace";
    executorService = Executors.newSingleThreadExecutor();
  }

  @Test
  public void testPollNotificationWithDefaultNamespace() throws Exception {
    Future<ResponseEntity<ApolloConfigNotification>> future =
        executorService.submit(() -> restTemplate
            .getForEntity(
                "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}",
                ApolloConfigNotification.class,
                getHostUrl(), someAppId, someCluster, defaultNamespace));

    //wait for the request connected to server
    TimeUnit.MILLISECONDS.sleep(500);

    notificationController.handleMessage(assembleKey(someAppId, someCluster, defaultNamespace),
        Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> result = future.get(500, TimeUnit.MILLISECONDS);
    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(defaultNamespace, notification.getNamespace());
  }

  @Test(timeout = 5000L)
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testPollNotificationWthPublicNamespaceAndNoDataCenter() throws Exception {
    String publicAppId = "somePublicAppId";

    AtomicBoolean stop = new AtomicBoolean();
    executorService.submit((Runnable) () -> {
      //wait for the request connected to server
      while (!stop.get() && !Thread.currentThread().isInterrupted()) {
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
        }

        notificationController.handleMessage(
            assembleKey(publicAppId, ConfigConsts.CLUSTER_NAME_DEFAULT, somePublicNamespace),
            Topics.APOLLO_RELEASE_TOPIC);
      }
    });

    ResponseEntity<ApolloConfigNotification> result = restTemplate
        .getForEntity(
            "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}",
            ApolloConfigNotification.class,
            getHostUrl(), someAppId, someCluster, somePublicNamespace);

    stop.set(true);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespace());
  }

  @Test(timeout = 5000L)
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testPollNotificationWthPublicNamespaceAndDataCenter() throws Exception {
    String publicAppId = "somePublicAppId";
    String someDC = "someDC";

    AtomicBoolean stop = new AtomicBoolean();
    executorService.submit((Runnable) () -> {
      //wait for the request connected to server
      while (!stop.get() && !Thread.currentThread().isInterrupted()) {
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
        }

        notificationController.handleMessage(
            assembleKey(publicAppId, someDC, somePublicNamespace),
            Topics.APOLLO_RELEASE_TOPIC);
      }
    });

    ResponseEntity<ApolloConfigNotification> result = restTemplate
        .getForEntity(
            "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}&dataCenter={dataCenter}",
            ApolloConfigNotification.class,
            getHostUrl(), someAppId, someCluster, somePublicNamespace, someDC);

    stop.set(true);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespace());
  }


  private String assembleKey(String appId, String cluster, String namespace) {
    return String.format("%s-%s-%s", appId, cluster, namespace);
  }
}
