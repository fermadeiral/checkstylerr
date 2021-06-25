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

import static com.google.common.truth.Truth.assertThat;

import com.google.bigtable.v2.Mutation.DeleteFromColumn;
import com.google.bigtable.v2.Mutation.DeleteFromFamily;
import com.google.bigtable.v2.Mutation.DeleteFromRow;
import com.google.cloud.bigtable.data.v2.models.Range.TimestampRange;
import com.google.protobuf.ByteString;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MutationTest {
  private Mutation mutation;

  @Before
  public void setUp() {
    mutation = Mutation.create();
  }

  @Test
  public void setCellTest() {
    long minTimestamp = System.currentTimeMillis() * 1_000;

    mutation
        .setCell(
            "fake-family",
            ByteString.copyFromUtf8("fake-qualifier"),
            1_000,
            ByteString.copyFromUtf8("fake-value"))
        .setCell(
            "fake-family",
            ByteString.copyFromUtf8("fake-qualifier"),
            ByteString.copyFromUtf8("fake-value"))
        .setCell("fake-family2", "fake-qualifier2", 1_000, "fake-value2")
        .setCell("fake-family2", "fake-qualifier2", "fake-value2");

    List<com.google.bigtable.v2.Mutation> actual = mutation.getMutations();

    long maxTimestamp = System.currentTimeMillis() * 1_000;
    com.google.common.collect.Range<Long> expectedTimestampRange =
        com.google.common.collect.Range.closed(minTimestamp, maxTimestamp);

    assertThat(actual).hasSize(4);

    assertThat(actual.get(0).getSetCell().getFamilyName()).isEqualTo("fake-family");
    assertThat(actual.get(0).getSetCell().getColumnQualifier())
        .isEqualTo(ByteString.copyFromUtf8("fake-qualifier"));
    assertThat(actual.get(0).getSetCell().getValue())
        .isEqualTo(ByteString.copyFromUtf8("fake-value"));
    assertThat(actual.get(0).getSetCell().getTimestampMicros()).isEqualTo(1_000);

    assertThat(actual.get(1).getSetCell().getFamilyName()).isEqualTo("fake-family");
    assertThat(actual.get(1).getSetCell().getColumnQualifier())
        .isEqualTo(ByteString.copyFromUtf8("fake-qualifier"));
    assertThat(actual.get(1).getSetCell().getValue())
        .isEqualTo(ByteString.copyFromUtf8("fake-value"));
    assertThat(actual.get(1).getSetCell().getTimestampMicros()).isIn(expectedTimestampRange);

    assertThat(actual.get(2).getSetCell().getFamilyName()).isEqualTo("fake-family2");
    assertThat(actual.get(2).getSetCell().getColumnQualifier())
        .isEqualTo(ByteString.copyFromUtf8("fake-qualifier2"));
    assertThat(actual.get(2).getSetCell().getValue())
        .isEqualTo(ByteString.copyFromUtf8("fake-value2"));
    assertThat(actual.get(2).getSetCell().getTimestampMicros()).isEqualTo(1_000);

    assertThat(actual.get(3).getSetCell().getFamilyName()).isEqualTo("fake-family2");
    assertThat(actual.get(3).getSetCell().getColumnQualifier())
        .isEqualTo(ByteString.copyFromUtf8("fake-qualifier2"));
    assertThat(actual.get(3).getSetCell().getValue())
        .isEqualTo(ByteString.copyFromUtf8("fake-value2"));
    assertThat(actual.get(3).getSetCell().getTimestampMicros()).isIn(expectedTimestampRange);
  }

  @Test
  public void deleteColumnTest() {
    mutation
        .deleteCells("fake-family", "fake-qualifier")
        .deleteCells("fake-family2", ByteString.copyFromUtf8("fake-qualifier2"))
        .deleteCells(
            "fake-family3",
            ByteString.copyFromUtf8("fake-qualifier3"),
            TimestampRange.create(1000L, 2000L));

    List<com.google.bigtable.v2.Mutation> actual = mutation.getMutations();

    assertThat(actual)
        .containsExactly(
            com.google.bigtable.v2.Mutation.newBuilder()
                .setDeleteFromColumn(
                    DeleteFromColumn.newBuilder()
                        .setFamilyName("fake-family")
                        .setColumnQualifier(ByteString.copyFromUtf8("fake-qualifier")))
                .build(),
            com.google.bigtable.v2.Mutation.newBuilder()
                .setDeleteFromColumn(
                    DeleteFromColumn.newBuilder()
                        .setFamilyName("fake-family2")
                        .setColumnQualifier(ByteString.copyFromUtf8("fake-qualifier2")))
                .build(),
            com.google.bigtable.v2.Mutation.newBuilder()
                .setDeleteFromColumn(
                    DeleteFromColumn.newBuilder()
                        .setFamilyName("fake-family3")
                        .setColumnQualifier(ByteString.copyFromUtf8("fake-qualifier3"))
                        .setTimeRange(
                            com.google.bigtable.v2.TimestampRange.newBuilder()
                                .setStartTimestampMicros(1000)
                                .setEndTimestampMicros(2000)))
                .build());
  }

  @Test
  public void deleteFamilyTest() {
    mutation.deleteFamily("fake-family1").deleteFamily("fake-family2");

    List<com.google.bigtable.v2.Mutation> actual = mutation.getMutations();

    assertThat(actual)
        .containsExactly(
            com.google.bigtable.v2.Mutation.newBuilder()
                .setDeleteFromFamily(DeleteFromFamily.newBuilder().setFamilyName("fake-family1"))
                .build(),
            com.google.bigtable.v2.Mutation.newBuilder()
                .setDeleteFromFamily(DeleteFromFamily.newBuilder().setFamilyName("fake-family2"))
                .build());
  }

  @Test
  public void deleteRowTest() {
    mutation.deleteRow();
    List<com.google.bigtable.v2.Mutation> actual = mutation.getMutations();

    assertThat(actual)
        .containsExactly(
            com.google.bigtable.v2.Mutation.newBuilder()
                .setDeleteFromRow(DeleteFromRow.newBuilder())
                .build());
  }
}
