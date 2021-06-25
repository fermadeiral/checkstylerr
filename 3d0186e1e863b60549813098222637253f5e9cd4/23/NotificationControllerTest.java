package com.ctrip.apollo.configservice.controller;

import com.google.common.collect.Multimap;

import com.ctrip.apollo.biz.message.Topics;
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

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest {
  private NotificationController controller;
  private String someAppId;
  private String someCluster;
  private String someNamespace;
  private String someDataCenter;
  private String someReleaseId;
  @Mock
  private HttpServletResponse response;
  private Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>> deferredResults;

  @Before
  public void setUp() throws Exception {
    controller = new NotificationController();
    someAppId = "someAppId";
    someCluster = "someCluster";
    someNamespace = "someNamespace";
    someDataCenter = "someDC";
    someReleaseId = "someRelease";

    deferredResults =
        (Multimap<String, DeferredResult<ResponseEntity<ApolloConfigNotification>>>) ReflectionTestUtils
            .getField(controller, "deferredResults");
  }

  @Test
  public void testPollNotificationWithDefaultNamespace() throws Exception {
    someNamespace = someAppId; //default namespace

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, someNamespace, someDataCenter, someReleaseId,
            response);

    String key = String.format("%s-%s-%s", someAppId, someCluster, someNamespace);
    assertEquals(1, deferredResults.size());
    assertTrue(deferredResults.get(key).contains(deferredResult));

  }

  @Test
  public void testPollNotificationWithDefaultNamespaceAndHandleMessage() throws Exception {
    someNamespace = someAppId; //default namespace

    DeferredResult<ResponseEntity<ApolloConfigNotification>>
        deferredResult = controller
        .pollNotification(someAppId, someCluster, someNamespace, someDataCenter, someReleaseId,
            response);

    String key = String.format("%s-%s-%s", someAppId, someCluster, someNamespace);

    controller.handleMessage(key, Topics.APOLLO_RELEASE_TOPIC);

    ResponseEntity<ApolloConfigNotification> response =
        (ResponseEntity<ApolloConfigNotification>) deferredResult.getResult();
    ApolloConfigNotification notification = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(someAppId, notification.getAppId());
    assertEquals(someCluster, notification.getCluster());
    assertEquals(someNamespace, notification.getNamespace());
  }
}
