package com.ctrip.framework.apollo.configservice.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import com.ctrip.framework.apollo.biz.entity.AppNamespace;
import com.ctrip.framework.apollo.biz.message.Topics;
import com.ctrip.framework.apollo.biz.service.AppNamespaceService;
import com.ctrip.framework.apollo.biz.utils.EntityManagerUtil;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.dto.ApolloConfigNotification;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest {
  private NotificationController controller;
  private String someAppId;
  private String someCluster;
  private String defaultCluster;
  private String defaultNamespace;
  private String somePublicNamespace;
  private String someDataCenter;
  private String someClientIp;
  @Mock
  private AppNamespaceService appNamespaceService;
  @Mock
  private EntityManagerUtil entityManagerUtil;
  private Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>>
      deferredResults;

  @Before
  public void setUp() throws Exception {
    controller = new NotificationController();
    ReflectionTestUtils.setField(controller, "appNamespaceService", appNamespaceService);
    ReflectionTestUtils.setField(controller, "entityManagerUtil", entityManagerUtil);

    someAppId = "someAppId";
    someCluster = "someCluster";
    defaultCluster = ConfigConsts.CLUSTER_NAME_DEFAULT;
    defaultNamespace = ConfigConsts.NAMESPACE_DEFAULT;
    somePublicNamespace = "somePublicNamespace";
    someDataCenter = "someDC";
    someClientIp = "someClientIp";

    deferredResults =
        (Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>>) ReflectionTestUtils
            .getField(controller, "deferredResults");
  }

  @Test
  public void testPollNotificationWithDefaultNamespace() throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, defaultNamespace, someDataCenter, someClientIp);

    List<String> clusters =
        Lists.newArrayList(someCluster, someDataCenter, ConfigConsts.CLUSTER_NAME_DEFAULT);

    assertEquals(clusters.size(), deferredResults.size());

    for (String cluster : clusters) {
      String key =
          Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
              .join(someAppId, cluster, defaultNamespace);
      assertTrue(deferredResults.get(key).contains(deferredResult));
    }
  }

  @Test
  public void testPollNotificationWithDefaultNamespaceWithDefaultClusterWithDataCenter()
      throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, defaultCluster, defaultNamespace, someDataCenter, someClientIp);

    List<String> clusters =
        Lists.newArrayList(someDataCenter, defaultCluster);

    assertEquals(clusters.size(), deferredResults.size());

    for (String cluster : clusters) {
      String key =
          Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
              .join(someAppId, cluster, defaultNamespace);
      assertTrue(deferredResults.get(key).contains(deferredResult));
    }
  }

  @Test
  public void testPollNotificationWithDefaultNamespaceWithDefaultClusterWithNoDataCenter()
      throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, defaultCluster, defaultNamespace, null, someClientIp);

    List<String> clusters =
        Lists.newArrayList(defaultCluster);

    assertEquals(clusters.size(), deferredResults.size());

    for (String cluster : clusters) {
      String key =
          Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
              .join(someAppId, cluster, defaultNamespace);
      assertTrue(deferredResults.get(key).contains(deferredResult));
    }

  }

  @Test
  public void testPollNotificationWithPublicNamespace() throws Exception {
    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespace);

    when(appNamespaceService.findByNamespaceName(somePublicNamespace))
        .thenReturn(somePublicAppNamespace);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, somePublicNamespace, someDataCenter, someClientIp);

    List<String> clusters =
        Lists.newArrayList(someCluster, someDataCenter, ConfigConsts.CLUSTER_NAME_DEFAULT);

    assertEquals(clusters.size() * 2, deferredResults.size());

    for (String cluster : clusters) {
      String publicKey =
          Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
              .join(someAppId, cluster, somePublicNamespace);
      assertTrue(deferredResults.get(publicKey).contains(deferredResult));
    }

    for (String cluster : clusters) {
      String publicKey =
          Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
              .join(somePublicAppId, cluster, somePublicNamespace);
      assertTrue(deferredResults.get(publicKey).contains(deferredResult));
    }
  }

  @Test
  public void testPollNotificationWithDefaultNamespaceAndHandleMessage() throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, defaultNamespace, someDataCenter, someClientIp);

    String key =
        Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
            .join(someAppId, someCluster, defaultNamespace);

    controller.handleMessage(key, Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> response =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();
    ApolloConfigNotification notification = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(defaultNamespace, notification.getNamespaceName());
  }

  @Test
  public void testPollNotificationWithPublicNamespaceAndHandleMessage() throws Exception {
    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespace);

    when(appNamespaceService.findByNamespaceName(somePublicNamespace))
        .thenReturn(somePublicAppNamespace);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, somePublicNamespace, someDataCenter, someClientIp);

    String key =
        Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
            .join(somePublicAppId, someDataCenter, somePublicNamespace);

    controller.handleMessage(key, Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> response =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();
    ApolloConfigNotification notification = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespaceName());

  }

  private AppNamespace assmbleAppNamespace(String appId, String namespace) {
    AppNamespace appNamespace = new AppNamespace();
    appNamespace.setAppId(appId);
    appNamespace.setName(namespace);
    return appNamespace;
  }
}
