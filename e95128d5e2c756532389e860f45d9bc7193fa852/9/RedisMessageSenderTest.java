package com.ctrip.apollo.biz.message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class RedisMessageSenderTest {
  @Mock
  private RedisTemplate<String, String> redisTemplate;
  private RedisMessageSender redisMessageSender;

  @Before
  public void setUp() throws Exception {
    redisMessageSender = new RedisMessageSender(redisTemplate);
  }

  @Test
  public void testSendMessage() throws Exception {
    String someMessage = "someMessage";
    String someChannel = "someChannel";

    redisMessageSender.sendMessage(someMessage, someChannel);

    verify(redisTemplate, times(1)).convertAndSend(someChannel, someMessage);
  }

  @Test
  public void testSendMessageWithError() throws Exception {
    String someMessage = "someMessage";
    String someChannel = "someChannel";

    doThrow(new RuntimeException()).when(redisTemplate).convertAndSend(someChannel, someMessage);

    redisMessageSender.sendMessage(someMessage, someChannel);

  }

}
