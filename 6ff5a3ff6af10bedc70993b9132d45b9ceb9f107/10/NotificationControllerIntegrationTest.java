package com.ctrip.framework.apollo.configservice.integration;

import com.google.common.base.Joiner;

import com.ctrip.framework.apollo.biz.entity.ReleaseMessage;
import com.ctrip.framework.apollo.biz.repository.ReleaseMessageRepository;
import com.ctrip.framework.apollo.configservice.controller.NotificationController;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.dto.ApolloConfigNotification;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class NotificationControllerIntegrationTest extends AbstractBaseIntegrationTest {
  @Autowired
  private NotificationController notificationController;
  @Autowired
  private ReleaseMessageRepository releaseMessageRepository;
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

  @Test(timeout = 5000L)
  public void testPollNotificationWithDefaultNamespace() throws Exception {
    AtomicBoolean stop = new AtomicBoolean();
    perodicSendMessage(assembleKey(someAppId, someCluster, defaultNamespace), stop);

    ResponseEntity<ApolloConfigNotification> result = restTemplate.getForEntity(
        "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}",
        ApolloConfigNotification.class,
        getHostUrl(), someAppId, someCluster, defaultNamespace);

    stop.set(true);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(defaultNamespace, notification.getNamespaceName());
    assertNotEquals(0, notification.getNotificationId());
  }

  @Test(timeout = 5000L)
  @Sql(scripts = "/integration-test/test-release-message.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testPollNotificationWithDefaultNamespaceWithNotificationIdNull() throws Exception {
    ResponseEntity<ApolloConfigNotification> result = restTemplate.getForEntity(
        "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}",
        ApolloConfigNotification.class,
        getHostUrl(), someAppId, someCluster, defaultNamespace);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(defaultNamespace, notification.getNamespaceName());
    assertEquals(10, notification.getNotificationId());
  }

  @Test(timeout = 5000L)
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/test-release-message.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testPollNotificationWithDefaultNamespaceWithNotificationIdOutDated() throws Exception {
    long someOutDatedNotificationId = 1;
    ResponseEntity<ApolloConfigNotification> result = restTemplate.getForEntity(
        "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}&notificationId={notificationId}",
        ApolloConfigNotification.class,
        getHostUrl(), someAppId, someCluster, defaultNamespace, someOutDatedNotificationId);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(defaultNamespace, notification.getNamespaceName());
    assertEquals(10, notification.getNotificationId());
  }

  @Test(timeout = 5000L)
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testPollNotificationWthPublicNamespaceAndNoDataCenter() throws Exception {
    String publicAppId = "somePublicAppId";

    AtomicBoolean stop = new AtomicBoolean();
    perodicSendMessage(assembleKey(publicAppId, ConfigConsts.CLUSTER_NAME_DEFAULT, somePublicNamespace), stop);

    ResponseEntity<ApolloConfigNotification> result = restTemplate
        .getForEntity(
            "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}",
            ApolloConfigNotification.class,
            getHostUrl(), someAppId, someCluster, somePublicNamespace);

    stop.set(true);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespaceName());
    assertNotEquals(0, notification.getNotificationId());
  }

  @Test(timeout = 5000L)
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testPollNotificationWthPublicNamespaceAndDataCenter() throws Exception {
    String publicAppId = "somePublicAppId";
    String someDC = "someDC";

    AtomicBoolean stop = new AtomicBoolean();
    perodicSendMessage(assembleKey(publicAppId, someDC, somePublicNamespace), stop);

    ResponseEntity<ApolloConfigNotification> result = restTemplate
        .getForEntity(
            "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}&dataCenter={dataCenter}",
            ApolloConfigNotification.class,
            getHostUrl(), someAppId, someCluster, somePublicNamespace, someDC);

    stop.set(true);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespaceName());
    assertNotEquals(0, notification.getNotificationId());
  }

  @Test(timeout = 5000L)
  @Sql(scripts = "/integration-test/test-release.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/test-release-message.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(scripts = "/integration-test/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testPollNotificationWithPublicNamespaceWithNotificationIdOutDated() throws Exception {
    long someOutDatedNotificationId = 1;
    ResponseEntity<ApolloConfigNotification> result = restTemplate.getForEntity(
        "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}&notificationId={notificationId}",
        ApolloConfigNotification.class,
        getHostUrl(), someAppId, someCluster, somePublicNamespace, someOutDatedNotificationId);

    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespaceName());
    assertEquals(20, notification.getNotificationId());
  }

  private String assembleKey(String appId, String cluster, String namespace) {
    return Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR).join(appId, cluster, namespace);
  }

  private void perodicSendMessage(String message, AtomicBoolean stop) {
    executorService.submit((Runnable) () -> {
      //wait for the request connected to server
      while (!stop.get() && !Thread.currentThread().isInterrupted()) {
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
        }

        ReleaseMessage releaseMessage = new ReleaseMessage(message);
        releaseMessageRepository.save(releaseMessage);
      }
    });
  }
}
