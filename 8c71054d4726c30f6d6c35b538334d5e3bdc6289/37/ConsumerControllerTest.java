package com.ctrip.framework.apollo.portal.controller;

import com.ctrip.framework.apollo.openapi.entity.ConsumerToken;
import com.ctrip.framework.apollo.openapi.service.ConsumerService;
import com.ctrip.framework.apollo.portal.spi.UserInfoHolder;
import com.ctrip.framework.apollo.portal.entity.po.UserInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsumerControllerTest {
  private ConsumerController consumerController;
  @Mock
  private ConsumerService consumerService;
  @Mock
  private UserInfoHolder userInfoHolder;

  @Before
  public void setUp() throws Exception {
    consumerController = new ConsumerController();
    ReflectionTestUtils.setField(consumerController, "consumerService", consumerService);
    ReflectionTestUtils.setField(consumerController, "userInfoHolder", userInfoHolder);
  }

  @Test
  public void testCreateConsumerToken() throws Exception {
    UserInfo userInfo = mock(UserInfo.class);
    String someUserId = "someUser";
    ConsumerToken consumerToken = mock(ConsumerToken.class);
    long someConsumerId = 1;
    Date someDateExpires = new Date();

    when(userInfo.getUserId()).thenReturn(someUserId);
    when(userInfoHolder.getUser()).thenReturn(userInfo);
    when(consumerService.createConsumerToken(any(ConsumerToken.class))).thenReturn(consumerToken);

    assertEquals(consumerToken, consumerController.createConsumerToken(someConsumerId, someDateExpires));
    verify(consumerService, times(1)).generateAndEnrichConsumerToken(any(ConsumerToken.class));
    verify(consumerService, times(1)).createConsumerToken(any(ConsumerToken.class));
  }

}
