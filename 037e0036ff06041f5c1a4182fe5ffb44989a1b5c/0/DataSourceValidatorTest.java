package com.ctrip.framework.apollo.common.utils;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class DataSourceValidatorTest {
  private DataSourceValidator datasourceValidator;

  @Before
  public void setUp() throws Exception {
    datasourceValidator = new DataSourceValidator();
  }

  @Test
  public void testValidateSuccessfully() throws Exception {
    Connection someConnection = mock(Connection.class);
    int someValidationAction = 1;

    when(someConnection.isValid(anyInt())).thenReturn(true);

    assertTrue(datasourceValidator.validate(someConnection, someValidationAction));
  }

  @Test
  public void testValidateFailed() throws Exception {
    Connection someConnection = mock(Connection.class);
    int someValidationAction = 1;

    when(someConnection.isValid(anyInt())).thenReturn(false);

    assertFalse(datasourceValidator.validate(someConnection, someValidationAction));
  }

  @Test
  public void testValidateWithException() throws Exception {
    Connection someConnection = mock(Connection.class);
    int someValidationAction = 1;

    when(someConnection.isValid(anyInt())).thenThrow(new RuntimeException("error"));

    assertFalse(datasourceValidator.validate(someConnection, someValidationAction));
  }

}