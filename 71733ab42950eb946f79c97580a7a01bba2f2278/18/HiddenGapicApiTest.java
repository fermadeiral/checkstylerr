/*
 * Copyright 2019 Google LLC
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
package com.google.cloud.bigtable.data.v2;

import static com.google.common.truth.Truth.assertWithMessage;

import com.google.api.core.InternalApi;
import com.google.cloud.bigtable.data.v2.stub.BigtableStub;
import com.google.cloud.bigtable.data.v2.stub.BigtableStubSettings;
import com.google.cloud.bigtable.data.v2.stub.GrpcBigtableCallableFactory;
import com.google.cloud.bigtable.data.v2.stub.GrpcBigtableStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests that the underlying GAPIC api is marked as {@code @InternalApi}. This transformation
 * happens in synth.py.
 */
@RunWith(Parameterized.class)
public class HiddenGapicApiTest {
  private Class<?> testClass;

  @Parameters
  public static Object[] data() {
    return new Object[] {
      BigtableStub.class,
      BigtableStubSettings.class,
      GrpcBigtableStub.class,
      GrpcBigtableCallableFactory.class
    };
  }

  public HiddenGapicApiTest(Class<?> testClass) {
    this.testClass = testClass;
  }

  @Test
  public void test() {
    assertWithMessage("%s should be annotated with @InternaOnly", testClass.getName())
        .that(testClass.getAnnotation(InternalApi.class))
        .isNotNull();
  }
}
