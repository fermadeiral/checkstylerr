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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class NotificationControllerIntegrationTest extends AbstractBaseIntegrationTest {
  @Autowired
  private NotificationController notificationController;
  private String someAppId;
  private String someCluster;
  private String someNamespace;
  private ExecutorService executorService;

  @Before
  public void setUp() throws Exception {
    someAppId = "someAppId";
    someCluster = ConfigConsts.CLUSTER_NAME_DEFAULT;
    someNamespace = "someNamespace";
    executorService = Executors.newSingleThreadExecutor();
  }

  @Test
  public void testPollNotification() throws Exception {
    Future<ResponseEntity<ApolloConfigNotification>> future =
        executorService.submit(() -> restTemplate
            .getForEntity(
                "{baseurl}/notifications?appId={appId}&cluster={clusterName}&namespace={namespace}",
                ApolloConfigNotification.class,
                getHostUrl(), someAppId, someCluster, someNamespace));

    //wait for the request connected to server
    TimeUnit.MILLISECONDS.sleep(500);

    notificationController.handleMessage(assembleKey(someAppId, someCluster, someNamespace),
        Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> result = future.get(500, TimeUnit.MILLISECONDS);
    ApolloConfigNotification notification = result.getBody();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(someAppId, notification.getAppId());
    assertEquals(someCluster, notification.getCluster());
    assertEquals(someNamespace, notification.getNamespace());
  }

  private String assembleKey(String appId, String cluster, String namespace) {
    return String.format("%s-%s-%s", appId, cluster, namespace);
  }
}
