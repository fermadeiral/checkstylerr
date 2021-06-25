package com.ctrip.apollo.configservice.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import com.ctrip.apollo.biz.entity.AppNamespace;
import com.ctrip.apollo.biz.message.Topics;
import com.ctrip.apollo.biz.service.AppNamespaceService;
import com.ctrip.apollo.core.ConfigConsts;
import com.ctrip.apollo.core.dto.ApolloConfigNotification;

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

import javax.servlet.http.HttpServletResponse;

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
  private String defaultNamespace;
  private String somePublicNamespace;
  private String someDataCenter;
  private String someReleaseId;
  @Mock
  private HttpServletResponse response;
  @Mock
  private AppNamespaceService appNamespaceService;
  private Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>>
      deferredResults;

  @Before
  public void setUp() throws Exception {
    controller = new NotificationController();
    ReflectionTestUtils.setField(controller, "appNamespaceService", appNamespaceService);

    someAppId = "someAppId";
    someCluster = "someCluster";
    defaultNamespace = ConfigConsts.NAMESPACE_DEFAULT;
    somePublicNamespace = "somePublicNamespace";
    someDataCenter = "someDC";
    someReleaseId = "someRelease";

    deferredResults =
        (Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>>) ReflectionTestUtils
            .getField(controller, "deferredResults");
  }

  @Test
  public void testPollNotificationWithDefaultNamespace() throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, defaultNamespace, someDataCenter, someReleaseId,
            response);

    String key = String.format("%s-%s-%s", someAppId, someCluster, defaultNamespace);
    assertEquals(1, deferredResults.size());
    assertTrue(deferredResults.get(key).contains(deferredResult));
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
        .pollNotification(someAppId, someCluster, somePublicNamespace, someDataCenter,
            someReleaseId,
            response);

    List<String> publicClusters =
        Lists.newArrayList(someDataCenter, ConfigConsts.CLUSTER_NAME_DEFAULT);

    assertEquals(3, deferredResults.size());
    String key = String.format("%s-%s-%s", someAppId, someCluster, somePublicNamespace);
    assertTrue(deferredResults.get(key).contains(deferredResult));

    for (String cluster : publicClusters) {
      String publicKey = String.format("%s-%s-%s", somePublicAppId, cluster, somePublicNamespace);
      assertTrue(deferredResults.get(publicKey).contains(deferredResult));
    }
  }

  @Test
  public void testPollNotificationWithDefaultNamespaceAndHandleMessage() throws Exception {
    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, defaultNamespace, someDataCenter, someReleaseId,
            response);

    String key = String.format("%s-%s-%s", someAppId, someCluster, defaultNamespace);

    controller.handleMessage(key, Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> response =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();
    ApolloConfigNotification notification = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(defaultNamespace, notification.getNamespace());
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
        .pollNotification(someAppId, someCluster, somePublicNamespace, someDataCenter, someReleaseId,
            response);

    String key = String.format("%s-%s-%s", somePublicAppId, someDataCenter, somePublicNamespace);

    controller.handleMessage(key, Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> response =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();
    ApolloConfigNotification notification = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(somePublicNamespace, notification.getNamespace());

  }

  private AppNamespace assmbleAppNamespace(String appId, String namespace) {
    AppNamespace appNamespace = new AppNamespace();
    appNamespace.setAppId(appId);
    appNamespace.setName(namespace);
    return appNamespace;
  }
}
