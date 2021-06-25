package com.ctrip.framework.apollo.configservice.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class NamespaceUtilTest {
  private NamespaceUtil namespaceUtil;

  @Before
  public void setUp() throws Exception {
    namespaceUtil = new NamespaceUtil();
  }

  @Test
  public void testFilterNamespaceName() throws Exception {
    String someName = "a.properties";

    assertEquals("a", namespaceUtil.filterNamespaceName(someName));
  }

  @Test
  public void testFilterNamespaceNameUnchanged() throws Exception {
    String someName = "a.xml";

    assertEquals(someName, namespaceUtil.filterNamespaceName(someName));
  }

  @Test
  public void testFilterNamespaceNameWithMultiplePropertiesSuffix() throws Exception {
    String someName = "a.properties.properties";

    assertEquals("a.properties", namespaceUtil.filterNamespaceName(someName));
  }

  @Test
  public void testFilterNamespaceNameWithRandomCase() throws Exception {
    String someName = "AbC.ProPErties";

    assertEquals("AbC", namespaceUtil.filterNamespaceName(someName));
  }

  @Test
  public void testFilterNamespaceNameWithRandomCaseUnchanged() throws Exception {
    String someName = "AbCD.xMl";

    assertEquals(someName, namespaceUtil.filterNamespaceName(someName));
  }
}
