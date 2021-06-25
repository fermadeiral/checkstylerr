package com.ctrip.framework.apollo.configservice.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import com.ctrip.framework.apollo.biz.entity.ReleaseMessage;
import com.ctrip.framework.apollo.biz.message.Topics;
import com.ctrip.framework.apollo.biz.service.AppNamespaceService;
import com.ctrip.framework.apollo.biz.service.ReleaseMessageService;
import com.ctrip.framework.apollo.biz.utils.EntityManagerUtil;
import com.ctrip.framework.apollo.common.entity.AppNamespace;
import com.ctrip.framework.apollo.configservice.util.NamespaceUtil;
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
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;
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
  private long someNotificationId;
  private String someClientIp;
  @Mock
  private AppNamespaceService appNamespaceService;
  @Mock
  private ReleaseMessageService releaseMessageService;
  @Mock
  private EntityManagerUtil entityManagerUtil;
  @Mock
  private NamespaceUtil namespaceUtil;

  private Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>>
      deferredResults;

  @Before
  public void setUp() throws Exception {
    controller = new NotificationController();
    ReflectionTestUtils.setField(controller, "appNamespaceService", appNamespaceService);
    ReflectionTestUtils.setField(controller, "releaseMessageService", releaseMessageService);
    ReflectionTestUtils.setField(controller, "entityManagerUtil", entityManagerUtil);
    ReflectionTestUtils.setField(controller, "namespaceUtil", namespaceUtil);

    someAppId = "someAppId";
    someCluster = "someCluster";
    defaultCluster = ConfigConsts.CLUSTER_NAME_DEFAULT;
    defaultNamespace = ConfigConsts.NAMESPACE_APPLICATION;
    somePublicNamespace = "somePublicNamespace";
    someDataCenter = "someDC";
    someNotificationId = 1;
    someClientIp = "someClientIp";

    when(namespaceUtil.filterNamespaceName(defaultNamespace)).thenReturn(defaultNamespace);
    when(namespaceUtil.filterNamespaceName(somePublicNamespace)).thenReturn(somePublicNamespace);

    deferredResults =
        (Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>>) ReflectionTestUtils
            .getField(controller, "deferredResults");
  }

  @Test
  public void testPollNotificationWithDefaultNamespace() throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, defaultNamespace, someDataCenter,
            someNotificationId, someClientIp);

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
  public void testPollNotificationWithDefaultNamespaceAsFile() throws Exception {
    String namespace = String.format("%s.%s", defaultNamespace, "properties");
    when(namespaceUtil.filterNamespaceName(namespace)).thenReturn(defaultNamespace);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, namespace, someDataCenter,
            someNotificationId, someClientIp);

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
  public void testPollNotificationWithPrivateNamespaceAsFile() throws Exception {
    String namespace = String.format("someNamespace.xml");
    AppNamespace appNamespace = mock(AppNamespace.class);

    when(namespaceUtil.filterNamespaceName(namespace)).thenReturn(namespace);
    when(appNamespaceService.findOne(someAppId, namespace)).thenReturn(appNamespace);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, namespace, someDataCenter,
            someNotificationId, someClientIp);

    List<String> clusters =
        Lists.newArrayList(someCluster, someDataCenter, ConfigConsts.CLUSTER_NAME_DEFAULT);

    assertEquals(clusters.size(), deferredResults.size());

    for (String cluster : clusters) {
      String key =
          Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
              .join(someAppId, cluster, namespace);
      assertTrue(deferredResults.get(key).contains(deferredResult));
    }
  }

  @Test
  public void testPollNotificationWithDefaultNamespaceWithNotificationIdOutDated() throws Exception {
    long notificationId = someNotificationId + 1;
    ReleaseMessage someReleaseMessage = mock(ReleaseMessage.class);

    when(someReleaseMessage.getId()).thenReturn(notificationId);
    when(releaseMessageService.findLatestReleaseMessageForMessages(anyCollectionOf(String.class)))
        .thenReturn(someReleaseMessage);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, defaultNamespace, someDataCenter,
            someNotificationId, someClientIp);

    ResponseEntity<ApolloConfigNotification> result =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(defaultNamespace, result.getBody().getNamespaceName());
    assertEquals(notificationId, result.getBody().getNotificationId());
  }

  @Test
  public void testPollNotificationWithDefaultNamespaceWithDefaultClusterWithDataCenter()
      throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, defaultCluster, defaultNamespace, someDataCenter,
            someNotificationId, someClientIp);

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
        .pollNotification(someAppId, defaultCluster, defaultNamespace, null, someNotificationId, someClientIp);

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

    when(appNamespaceService.findPublicNamespaceByName(somePublicNamespace))
        .thenReturn(somePublicAppNamespace);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, somePublicNamespace, someDataCenter,
            someNotificationId, someClientIp);

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
  public void testPollNotificationWithPublicNamespaceAsFile() throws Exception {
    String somePublicNamespaceAsFile = String.format("%s.%s", somePublicNamespace, "xml");
    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespace);

    when(namespaceUtil.filterNamespaceName(somePublicNamespaceAsFile))
        .thenReturn(somePublicNamespace);
    when(appNamespaceService.findPublicNamespaceByName(somePublicNamespace))
        .thenReturn(somePublicAppNamespace);
    when(appNamespaceService.findOne(someAppId, somePublicNamespace)).thenReturn(null);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, somePublicNamespaceAsFile, someDataCenter,
            someNotificationId, someClientIp);

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
  public void testPollNotificationWithPublicNamespaceWithNotificationIdOutDated() throws Exception {
    long notificationId = someNotificationId + 1;
    ReleaseMessage someReleaseMessage = mock(ReleaseMessage.class);

    when(someReleaseMessage.getId()).thenReturn(notificationId);
    when(releaseMessageService.findLatestReleaseMessageForMessages(anyCollectionOf(String.class)))
        .thenReturn(someReleaseMessage);

    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespace);

    when(appNamespaceService.findPublicNamespaceByName(somePublicNamespace))
        .thenReturn(somePublicAppNamespace);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, somePublicNamespace, someDataCenter,
            someNotificationId, someClientIp);

    ResponseEntity<ApolloConfigNotification> result =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(somePublicNamespace, result.getBody().getNamespaceName());
    assertEquals(notificationId, result.getBody().getNotificationId());
  }

  @Test
  public void testPollNotificationWithDefaultNamespaceAndHandleMessage() throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, defaultNamespace, someDataCenter,
            someNotificationId, someClientIp);

    String key =
        Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
            .join(someAppId, someCluster, defaultNamespace);
    long someId = 1;
    ReleaseMessage someReleaseMessage = new ReleaseMessage(key);
    someReleaseMessage.setId(someId);

    controller.handleMessage(someReleaseMessage, Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> response =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();
    ApolloConfigNotification notification = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(defaultNamespace, notification.getNamespaceName());
    assertEquals(someId, notification.getNotificationId());
  }

  @Test
  public void testPollNotificationWithPublicNamespaceAndHandleMessage() throws Exception {
    String somePublicAppId = "somePublicAppId";
    AppNamespace somePublicAppNamespace =
        assmbleAppNamespace(somePublicAppId, somePublicNamespace);

    when(appNamespaceService.findPublicNamespaceByName(somePublicNamespace))
        .thenReturn(somePublicAppNamespace);

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, somePublicNamespace, someDataCenter,
            someNotificationId, someClientIp);

    String key =
        Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
            .join(somePublicAppId, someDataCenter, somePublicNamespace);
    long someId = 1;
    ReleaseMessage someReleaseMessage = new ReleaseMessage(key);
    someReleaseMessage.setId(someId);

    controller.handleMessage(someReleaseMessage, Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> response =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();
    ApolloConfigNotification notification = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespaceName());
    assertEquals(someId, notification.getNotificationId());
  }

  private AppNamespace assmbleAppNamespace(String appId, String namespace) {
    AppNamespace appNamespace = new AppNamespace();
    appNamespace.setAppId(appId);
    appNamespace.setName(namespace);
    return appNamespace;
  }
}
