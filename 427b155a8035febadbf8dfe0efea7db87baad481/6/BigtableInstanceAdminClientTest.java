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
package com.google.cloud.bigtable.admin.v2;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.gax.grpc.GrpcStatusCode;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.longrunning.OperationFutures;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.gax.rpc.testing.FakeOperationSnapshot;
import com.google.bigtable.admin.v2.Cluster;
import com.google.bigtable.admin.v2.CreateInstanceMetadata;
import com.google.bigtable.admin.v2.Instance.Type;
import com.google.bigtable.admin.v2.InstanceName;
import com.google.bigtable.admin.v2.LocationName;
import com.google.bigtable.admin.v2.ProjectName;
import com.google.bigtable.admin.v2.StorageType;
import com.google.bigtable.admin.v2.UpdateInstanceMetadata;
import com.google.cloud.bigtable.admin.v2.models.CreateInstanceRequest;
import com.google.cloud.bigtable.admin.v2.models.Instance;
import com.google.cloud.bigtable.admin.v2.models.PartialListInstancesException;
import com.google.cloud.bigtable.admin.v2.models.UpdateInstanceRequest;
import com.google.cloud.bigtable.admin.v2.stub.BigtableInstanceAdminStub;
import com.google.protobuf.Empty;
import com.google.protobuf.FieldMask;
import io.grpc.Status.Code;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class BigtableInstanceAdminClientTest {
  private static final ProjectName PROJECT_NAME = ProjectName.of("my-project");
  private static final InstanceName INSTANCE_NAME =
      InstanceName.of(PROJECT_NAME.getProject(), "my-instance");

  private BigtableInstanceAdminClient adminClient;

  @Mock
  private BigtableInstanceAdminStub mockStub;

  @Mock
  private OperationCallable<com.google.bigtable.admin.v2.CreateInstanceRequest, com.google.bigtable.admin.v2.Instance, CreateInstanceMetadata> mockCreateInstanceCallable;

  @Mock
  private OperationCallable<com.google.bigtable.admin.v2.PartialUpdateInstanceRequest, com.google.bigtable.admin.v2.Instance, UpdateInstanceMetadata> mockUpdateInstanceCallable;

  @Mock
  private UnaryCallable<com.google.bigtable.admin.v2.GetInstanceRequest, com.google.bigtable.admin.v2.Instance> mockGetInstanceCallable;

  @Mock
  private UnaryCallable<com.google.bigtable.admin.v2.ListInstancesRequest, com.google.bigtable.admin.v2.ListInstancesResponse> mockListInstancesCallable;

  @Mock
  private UnaryCallable<com.google.bigtable.admin.v2.DeleteInstanceRequest, Empty> mockDeleteInstanceCallable;


  @Before
  public void setUp() {
    adminClient = BigtableInstanceAdminClient
        .create(PROJECT_NAME, mockStub);

    Mockito.when(mockStub.createInstanceOperationCallable()).thenReturn(mockCreateInstanceCallable);
    Mockito.when(mockStub.partialUpdateInstanceOperationCallable())
        .thenReturn(mockUpdateInstanceCallable);

    Mockito.when(mockStub.getInstanceCallable()).thenReturn(mockGetInstanceCallable);
    Mockito.when(mockStub.listInstancesCallable()).thenReturn(mockListInstancesCallable);
    Mockito.when(mockStub.deleteInstanceCallable()).thenReturn(mockDeleteInstanceCallable);
  }

  @Test
  public void testProjectName() {
    assertThat(adminClient.getProjectName()).isEqualTo(PROJECT_NAME);
  }

  @Test
  public void testClose() {
    adminClient.close();
    Mockito.verify(mockStub).close();
  }

  @Test
  public void testCreateInstance() {
    // Setup
    com.google.bigtable.admin.v2.CreateInstanceRequest expectedRequest =
        com.google.bigtable.admin.v2.CreateInstanceRequest.newBuilder()
            .setParent(PROJECT_NAME.toString())
            .setInstanceId(INSTANCE_NAME.getInstance())
            .setInstance(
                com.google.bigtable.admin.v2.Instance.newBuilder()
                    .setType(Type.DEVELOPMENT)
                    .setDisplayName(INSTANCE_NAME.getInstance())
            )
            .putClusters("cluster1", Cluster.newBuilder()
                .setLocation("projects/my-project/locations/us-east1-c")
                .setServeNodes(1)
                .setDefaultStorageType(StorageType.SSD)
                .build()
            )
            .build();

    com.google.bigtable.admin.v2.Instance expectedResponse = com.google.bigtable.admin.v2.Instance
        .newBuilder()
        .setName(INSTANCE_NAME.toString())
        .build();

    mockOperationResult(mockCreateInstanceCallable, expectedRequest, expectedResponse);

    // Execute
    Instance actualResult = adminClient.createInstance(
        CreateInstanceRequest.of(INSTANCE_NAME.getInstance())
            .setType(Type.DEVELOPMENT)
            .addCluster("cluster1", "us-east1-c", 1, StorageType.SSD)
    );

    // Verify
    assertThat(actualResult).isEqualTo(Instance.fromProto(expectedResponse));
  }

  @Test
  public void testUpdateInstance() {
    // Setup
    com.google.bigtable.admin.v2.PartialUpdateInstanceRequest expectedRequest =
        com.google.bigtable.admin.v2.PartialUpdateInstanceRequest.newBuilder()
            .setUpdateMask(
                FieldMask.newBuilder()
                    .addPaths("display_name")
            )
            .setInstance(
                com.google.bigtable.admin.v2.Instance.newBuilder()
                    .setName(INSTANCE_NAME.toString())
                    .setDisplayName("new display name")
            )
            .build();

    com.google.bigtable.admin.v2.Instance expectedResponse = com.google.bigtable.admin.v2.Instance
        .newBuilder()
        .setName(INSTANCE_NAME.toString())
        .build();

    mockOperationResult(mockUpdateInstanceCallable, expectedRequest, expectedResponse);

    // Execute
    Instance actualResult = adminClient.updateInstance(
        UpdateInstanceRequest.of(INSTANCE_NAME.getInstance())
            .setDisplayName("new display name")
    );

    // Verify
    assertThat(actualResult).isEqualTo(Instance.fromProto(expectedResponse));
  }

  @Test
  public void testGetInstance() {
    // Setup
    com.google.bigtable.admin.v2.GetInstanceRequest expectedRequest =
        com.google.bigtable.admin.v2.GetInstanceRequest.newBuilder()
            .setName(INSTANCE_NAME.toString())
            .build();

    com.google.bigtable.admin.v2.Instance expectedResponse = com.google.bigtable.admin.v2.Instance
        .newBuilder()
        .setName(INSTANCE_NAME.toString())
        .build();

    Mockito.when(mockGetInstanceCallable.futureCall(expectedRequest))
        .thenReturn(ApiFutures.immediateFuture(expectedResponse));

    // Execute
    Instance actualResult = adminClient.getInstance(INSTANCE_NAME.getInstance());

    // Verify
    assertThat(actualResult).isEqualTo(Instance.fromProto(expectedResponse));
  }

  @Test
  public void testListInstances() {
    // Setup
    com.google.bigtable.admin.v2.ListInstancesRequest expectedRequest =
        com.google.bigtable.admin.v2.ListInstancesRequest.newBuilder()
            .setParent(PROJECT_NAME.toString())
            .build();

    com.google.bigtable.admin.v2.ListInstancesResponse expectedResponse =
        com.google.bigtable.admin.v2.ListInstancesResponse
            .newBuilder()
            .addInstances(
                com.google.bigtable.admin.v2.Instance.newBuilder().setName(INSTANCE_NAME + "1")
                    .build()
            )
            .addInstances(
                com.google.bigtable.admin.v2.Instance.newBuilder().setName(INSTANCE_NAME + "2")
                    .build()
            )
            .build();

    Mockito.when(mockListInstancesCallable.futureCall(expectedRequest))
        .thenReturn(ApiFutures.immediateFuture(expectedResponse));

    // Execute
    List<Instance> actualResult = adminClient.listInstances();

    // Verify
    assertThat(actualResult).containsExactly(
        Instance.fromProto(expectedResponse.getInstances(0)),
        Instance.fromProto(expectedResponse.getInstances(1))
    );
  }

  @Test
  public void testListInstancesFailedZone() {
    // Setup
    com.google.bigtable.admin.v2.ListInstancesRequest expectedRequest =
        com.google.bigtable.admin.v2.ListInstancesRequest.newBuilder()
            .setParent(PROJECT_NAME.toString())
            .build();

    com.google.bigtable.admin.v2.ListInstancesResponse expectedResponse =
        com.google.bigtable.admin.v2.ListInstancesResponse
            .newBuilder()
            .addInstances(
                com.google.bigtable.admin.v2.Instance.newBuilder().setName(INSTANCE_NAME + "1")
                    .build()
            )
            .addFailedLocations(
                LocationName.of(PROJECT_NAME.getProject(), "us-east1-d").toString()
            )
            .build();

    Mockito.when(mockListInstancesCallable.futureCall(expectedRequest))
        .thenReturn(ApiFutures.immediateFuture(expectedResponse));

    // Execute
    Exception actualError = null;

    try {
      adminClient.listInstances();
    } catch (Exception e) {
      actualError = e;
    }

    // Verify
    assertThat(actualError).isInstanceOf(PartialListInstancesException.class);
    assert actualError != null;
    PartialListInstancesException partialListError = (PartialListInstancesException) actualError;

    assertThat(partialListError.getInstances())
        .containsExactly(Instance.fromProto(expectedResponse.getInstances(0)));
    assertThat(partialListError.getUnavailableZones()).containsExactly("us-east1-d");
  }

  @Test
  public void testDeleteInstance() {
    // Setup
    com.google.bigtable.admin.v2.DeleteInstanceRequest expectedRequest =
        com.google.bigtable.admin.v2.DeleteInstanceRequest.newBuilder()
            .setName(INSTANCE_NAME.toString())
            .build();

    final AtomicBoolean wasCalled = new AtomicBoolean(false);

    Mockito.when(mockDeleteInstanceCallable.futureCall(expectedRequest))
        .thenAnswer(new Answer<ApiFuture<Empty>>() {
          @Override
          public ApiFuture<Empty> answer(InvocationOnMock invocationOnMock) {
            wasCalled.set(true);
            return ApiFutures.immediateFuture(Empty.getDefaultInstance());
          }
        });

    // Execute
    adminClient.deleteInstance(INSTANCE_NAME.getInstance());

    // Verify
    assertThat(wasCalled.get()).isTrue();
  }


  private <ReqT, RespT, MetaT> void mockOperationResult(
      OperationCallable<ReqT, RespT, MetaT> callable, ReqT request, RespT response) {
    OperationSnapshot operationSnapshot = FakeOperationSnapshot.newBuilder()
        .setDone(true)
        .setErrorCode(GrpcStatusCode.of(Code.OK))
        .setName("fake-name")
        .setResponse(response)
        .build();

    OperationFuture<RespT, MetaT> operationFuture = OperationFutures
        .immediateOperationFuture(operationSnapshot);

    Mockito.when(callable.futureCall(request)).thenReturn(operationFuture);
  }
}
