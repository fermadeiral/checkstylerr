/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.bigtable.data.v2.models;

import static com.google.cloud.bigtable.data.v2.models.Filters.FILTERS;
import static com.google.common.truth.Truth.assertThat;

import com.google.cloud.bigtable.data.v2.models.InstanceName;
import com.google.bigtable.v2.ReadRowsRequest;
import com.google.bigtable.v2.ReadRowsRequest.Builder;
import com.google.bigtable.v2.RowFilter;
import com.google.bigtable.v2.RowRange;
import com.google.bigtable.v2.TableName;
import com.google.cloud.bigtable.data.v2.internal.RequestContext;
import com.google.cloud.bigtable.data.v2.models.Range.ByteStringRange;
import com.google.protobuf.ByteString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class QueryTest {
  private static final InstanceName INSTANCE_NAME =
      InstanceName.of("fake-project", "fake-instance");
  private static final TableName TABLE_NAME =
      TableName.of("fake-project", "fake-instance", "fake-table");
  private static final String APP_PROFILE_ID = "fake-profile-id";
  private RequestContext requestContext;

  @Before
  public void setUp() {
    requestContext = RequestContext.create(INSTANCE_NAME, APP_PROFILE_ID);
  }

  @Test
  public void requestContextTest() {
    Query query = Query.create(TABLE_NAME.getTable());

    ReadRowsRequest proto = query.toProto(requestContext);
    assertThat(proto).isEqualTo(expectedProtoBuilder().build());
  }

  @Test
  public void rowKeysTest() {
    Query query =
        Query.create(TABLE_NAME.getTable())
            .rowKey("simple-string")
            .rowKey(ByteString.copyFromUtf8("byte-string"));

    ReadRowsRequest.Builder expectedProto = expectedProtoBuilder();
    expectedProto
        .getRowsBuilder()
        .addRowKeys(ByteString.copyFromUtf8("simple-string"))
        .addRowKeys(ByteString.copyFromUtf8("byte-string"));

    ReadRowsRequest actualProto = query.toProto(requestContext);
    assertThat(actualProto).isEqualTo(expectedProto.build());
  }

  @Test
  public void rowRangeTest() {
    Query query =
        Query.create(TABLE_NAME.getTable())
            .range("simple-begin", "simple-end")
            .range(ByteString.copyFromUtf8("byte-begin"), ByteString.copyFromUtf8("byte-end"))
            .range(ByteStringRange.create("range-begin", "range-end"));

    Builder expectedProto = expectedProtoBuilder();
    expectedProto
        .getRowsBuilder()
        .addRowRanges(
            RowRange.newBuilder()
                .setStartKeyClosed(ByteString.copyFromUtf8("simple-begin"))
                .setEndKeyOpen(ByteString.copyFromUtf8("simple-end")))
        .addRowRanges(
            RowRange.newBuilder()
                .setStartKeyClosed(ByteString.copyFromUtf8("byte-begin"))
                .setEndKeyOpen(ByteString.copyFromUtf8("byte-end")))
        .addRowRanges(
            RowRange.newBuilder()
                .setStartKeyClosed(ByteString.copyFromUtf8("range-begin"))
                .setEndKeyOpen(ByteString.copyFromUtf8("range-end")));

    ReadRowsRequest actualProto = query.toProto(requestContext);
    assertThat(actualProto).isEqualTo(expectedProto.build());
  }

  @Test
  public void filterTest() {
    Query query = Query.create(TABLE_NAME.getTable()).filter(FILTERS.key().regex(".*"));

    Builder expectedProto =
        expectedProtoBuilder()
            .setFilter(RowFilter.newBuilder().setRowKeyRegexFilter(ByteString.copyFromUtf8(".*")));

    ReadRowsRequest actualProto = query.toProto(requestContext);
    assertThat(actualProto).isEqualTo(expectedProto.build());
  }

  @Test
  public void limitTest() {
    Query query = Query.create(TABLE_NAME.getTable()).limit(10);

    Builder expectedProto = expectedProtoBuilder().setRowsLimit(10);

    ReadRowsRequest actualProto = query.toProto(requestContext);
    assertThat(actualProto).isEqualTo(expectedProto.build());
  }

  private static ReadRowsRequest.Builder expectedProtoBuilder() {
    return ReadRowsRequest.newBuilder()
        .setTableName(TABLE_NAME.toString())
        .setAppProfileId(APP_PROFILE_ID);
  }
}
