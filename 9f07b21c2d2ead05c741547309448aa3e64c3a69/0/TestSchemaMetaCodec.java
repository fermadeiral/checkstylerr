/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.codec.protobuf.internal.converter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.servicecomb.codec.protobuf.definition.OperationProtobuf;
import org.apache.servicecomb.codec.protobuf.definition.ProtobufManager;
import org.apache.servicecomb.codec.protobuf.internal.converter.model.ProtoSchema;
import org.apache.servicecomb.codec.protobuf.internal.converter.model.ProtoSchemaPojo;
import org.apache.servicecomb.core.definition.MicroserviceMeta;
import org.apache.servicecomb.core.definition.OperationMeta;
import org.apache.servicecomb.core.definition.SchemaMeta;
import org.apache.servicecomb.foundation.protobuf.RequestRootDeserializer;
import org.apache.servicecomb.foundation.protobuf.RequestRootSerializer;
import org.apache.servicecomb.foundation.protobuf.ResponseRootDeserializer;
import org.apache.servicecomb.foundation.protobuf.RootSerializer;
import org.apache.servicecomb.foundation.test.scaffolding.model.Color;
import org.apache.servicecomb.foundation.test.scaffolding.model.Empty;
import org.apache.servicecomb.foundation.test.scaffolding.model.User;
import org.apache.servicecomb.swagger.engine.SwaggerConsumer;
import org.apache.servicecomb.swagger.engine.SwaggerConsumerOperation;
import org.apache.servicecomb.swagger.engine.SwaggerEnvironment;
import org.apache.servicecomb.swagger.engine.SwaggerProducer;
import org.apache.servicecomb.swagger.engine.SwaggerProducerOperation;
import org.apache.servicecomb.swagger.generator.core.AbstractSwaggerGenerator;
import org.apache.servicecomb.swagger.generator.pojo.PojoSwaggerGenerator;
import org.apache.servicecomb.swagger.generator.springmvc.SpringmvcSwaggerGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.swagger.models.Swagger;
import mockit.Expectations;
import mockit.Injectable;

/**
 * SchemaMetaCodec test cases. This test cases covers POJO invoker and producer.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class TestSchemaMetaCodec {
  @Injectable
  private MicroserviceMeta providerMicroserviceMeta;

  @Injectable
  private MicroserviceMeta consumerMicroserviceMeta;

  private SchemaMeta providerSchemaMeta;

  private SchemaMeta consumerSchemaMeta;

  @Before
  public void setUp() {

  }

  private void mockSchemaMeta(AbstractSwaggerGenerator swaggerGenerator, Class<?> schemaClass) throws Exception {
    new Expectations() {
      {
        providerMicroserviceMeta.getMicroserviceName();
        result = "test";
        providerMicroserviceMeta.getExtData(ProtobufManager.EXT_ID);
        result = null;
        consumerMicroserviceMeta.getMicroserviceName();
        result = "test";
        consumerMicroserviceMeta.getExtData(ProtobufManager.EXT_ID);
        result = null;
      }
    };
    Swagger swagger = swaggerGenerator.generate();
    SwaggerEnvironment swaggerEnvironment = new SwaggerEnvironment();

    providerSchemaMeta = new SchemaMeta(providerMicroserviceMeta, "ProtoSchema", swagger);
    SwaggerProducer swaggerProducer = swaggerEnvironment.createProducer(schemaClass.newInstance(), swagger);
    for (SwaggerProducerOperation producerOperation : swaggerProducer.getAllOperations()) {
      OperationMeta operationMeta = providerSchemaMeta.ensureFindOperation(producerOperation.getOperationId());
      operationMeta.setSwaggerProducerOperation(producerOperation);
    }

    consumerSchemaMeta = new SchemaMeta(consumerMicroserviceMeta, "ProtoSchema", swagger);
    SwaggerConsumer swaggerConsumer = swaggerEnvironment.createConsumer(schemaClass, swagger);
    for (SwaggerConsumerOperation consumerOperation : swaggerConsumer.getOperations().values()) {
      OperationMeta operationMeta = consumerSchemaMeta.ensureFindOperation(consumerOperation.getSchemaOperationId());
      operationMeta.setSwaggerConsumerOperation(consumerOperation);
    }
  }

  @Test
  public void testProtoSchemaOperationUserSpringMVC() throws Exception {
    mockSchemaMeta(new SpringmvcSwaggerGenerator(ProtoSchema.class), ProtoSchema.class);
    testProtoSchemaOperationUserImpl();
  }

  @Test
  public void testProtoSchemaOperationUserPOJO() throws Exception {
    mockSchemaMeta(new PojoSwaggerGenerator(ProtoSchemaPojo.class), ProtoSchemaPojo.class);
    testProtoSchemaOperationUserImpl();
  }

  private void testProtoSchemaOperationUserImpl() throws IOException {
    OperationProtobuf providerOperationProtobuf = ProtobufManager
        .getOrCreateOperation(providerSchemaMeta.getOperations().get("user"));
    OperationProtobuf consumerOperationProtobuf = ProtobufManager
        .getOrCreateOperation(consumerSchemaMeta.getOperations().get("user"));
    User user = new User();
    user.name = "user";
    User friend = new User();
    friend.name = "friend";
    List<User> friends = new ArrayList<>();
    friends.add(friend);
    user.friends = friends;
    byte[] values;

    // request message
    Map<String, Object> args = new HashMap<>();
    RequestRootSerializer requestSerializer = consumerOperationProtobuf.findRequestSerializer();
    user.friends = friends;
    args.put("user", user);
    values = requestSerializer.serialize(args);

    RequestRootDeserializer<Object> requestDeserializer = providerOperationProtobuf.findRequestDesirializer();
    Map<String, Object> decodedUserArgs = requestDeserializer.deserialize(values);
    Assert.assertEquals(user.name, ((User) decodedUserArgs.get("user")).name);
    Assert.assertEquals(user.friends.get(0).name, ((User) decodedUserArgs.get("user")).friends.get(0).name);

    // write request map (pojo)
    args = new HashMap<>();
    Map<String, Object> userMap = new HashMap<>();
    userMap.put("name", "user");
    Map<String, Object> friendMap = new HashMap<>();
    friendMap.put("name", "friend");
    List<Map<String, Object>> friendsList = new ArrayList<>();
    friendsList.add(friendMap);
    userMap.put("friends", friendsList);
    args.put("user", userMap);
    values = requestSerializer.serialize(args);

    decodedUserArgs = requestDeserializer.deserialize(values);
    Assert.assertEquals(user.name, ((User) decodedUserArgs.get("user")).name);
    Assert.assertEquals(user.friends.get(0).name, ((User) decodedUserArgs.get("user")).friends.get(0).name);

    // response message
    RootSerializer responseSerializer = providerOperationProtobuf.findResponseSerializer(200);
    values = responseSerializer.serialize(user);
    ResponseRootDeserializer<Object> responseDeserializer = consumerOperationProtobuf.findResponseDesirialize(200);
    User decodedUser = (User) responseDeserializer.deserialize(values);
    Assert.assertEquals(user.name, decodedUser.name);
    Assert.assertEquals(user.friends.get(0).name, decodedUser.friends.get(0).name);

    user.friends = new ArrayList<>();
    values = responseSerializer.serialize(user);
    decodedUser = (User) responseDeserializer.deserialize(values);
    Assert.assertEquals(user.name, decodedUser.name);
    // proto buffer encode and decode empty list to be null
    Assert.assertEquals(null, decodedUser.friends);
  }

  @Test
  public void testProtoSchemaOperationmapUserSpringMVC() throws Exception {
    mockSchemaMeta(new SpringmvcSwaggerGenerator(ProtoSchema.class), ProtoSchema.class);
    testProtoSchemaOperationmapUserImpl();
  }

  @Test
  public void testProtoSchemaOperationmapUserPOJO() throws Exception {
    mockSchemaMeta(new PojoSwaggerGenerator(ProtoSchemaPojo.class), ProtoSchemaPojo.class);
    testProtoSchemaOperationmapUserImpl();
  }

  private void testProtoSchemaOperationmapUserImpl() throws IOException {
    OperationProtobuf providerOperationProtobuf = ProtobufManager
        .getOrCreateOperation(providerSchemaMeta.getOperations().get("mapUser"));
    OperationProtobuf consumerOperationProtobuf = ProtobufManager
        .getOrCreateOperation(consumerSchemaMeta.getOperations().get("mapUser"));
    User user = new User();
    user.name = "user";
    User friend = new User();
    friend.name = "friend";
    List<User> friends = new ArrayList<>();
    friends.add(friend);
    user.friends = friends;
    byte[] values;
    Map<String, User> userMap = new HashMap<>();
    userMap.put("test", user);

    // request message
    Map<String, Object> args = new HashMap<>();
    RequestRootSerializer requestSerializer = consumerOperationProtobuf.findRequestSerializer();
    user.friends = friends;
    args.put("users", userMap);
    values = requestSerializer.serialize(args);

    RequestRootDeserializer<Object> requestDeserializer = providerOperationProtobuf.findRequestDesirializer();
    Map<String, Object> decodedUserArgs = requestDeserializer.deserialize(values);
    Assert.assertEquals(user.name, ((Map<String, User>) decodedUserArgs.get("users")).get("test").name);
    Assert.assertEquals(user.friends.get(0).name,
        ((Map<String, User>) decodedUserArgs.get("users")).get("test").friends.get(0).name);

    // response message
    RootSerializer responseSerializer = providerOperationProtobuf.findResponseSerializer(200);
    values = responseSerializer.serialize(userMap);
    ResponseRootDeserializer<Object> responseDeserializer = consumerOperationProtobuf.findResponseDesirialize(200);
    Map<String, User> decodedUser = (Map<String, User>) responseDeserializer.deserialize(values);
    Assert.assertEquals(user.name, decodedUser.get("test").name);
    Assert.assertEquals(user.friends.get(0).name, decodedUser.get("test").friends.get(0).name);

    user.friends = new ArrayList<>();
    values = responseSerializer.serialize(userMap);
    decodedUser = (Map<String, User>) responseDeserializer.deserialize(values);
    Assert.assertEquals(user.name, decodedUser.get("test").name);
    // proto buffer encode and decode empty list to be null
    Assert.assertEquals(null, decodedUser.get("test").friends);
  }

  @Test
  public void testProtoSchemaOperationBaseSpringMVC() throws Exception {
    mockSchemaMeta(new SpringmvcSwaggerGenerator(ProtoSchema.class), ProtoSchema.class);
    testProtoSchemaOperationBaseImpl();
  }

  @Test
  public void testProtoSchemaOperationBasePOJO() throws Exception {
    mockSchemaMeta(new PojoSwaggerGenerator(ProtoSchemaPojo.class), ProtoSchemaPojo.class);
    testProtoSchemaOperationBaseImpl();
  }

  private void testProtoSchemaOperationBaseImpl() throws IOException {
    // TODO : WEAK fix this line "java.lang.NoClassDefFoundError: org/apache/servicecomb/foundation/common/utils/bean/Getter"
    OperationProtobuf providerOperationProtobuf = ProtobufManager
        .getOrCreateOperation(providerSchemaMeta.getOperations().get("base"));
    OperationProtobuf consumerOperationProtobuf = ProtobufManager
        .getOrCreateOperation(consumerSchemaMeta.getOperations().get("base"));
    byte[] values;

    // request message
    RequestRootSerializer requestSerializer = consumerOperationProtobuf.findRequestSerializer();
    boolean boolValue = true;
    int iValue = 20;
    long lValue = 30L;
    float fValue = 40f;
    double dValue = 50D;
    String sValue = "hello";
    int[] iArray = new int[] {60, 70};
    Color color = Color.BLUE;
    LocalDate localDate = LocalDate.of(2019, 10, 1);
    Date date = new Date();
    Empty empty = new Empty();
    Map<String, Object> args = new HashMap<>();
    args.put("boolValue", boolValue);
    args.put("iValue", iValue);
    args.put("lValue", lValue);
    args.put("fValue", fValue);
    args.put("dValue", dValue);
    args.put("sValue", sValue);
    args.put("iArray", iArray);
    args.put("color", color);
    args.put("localDate", localDate);
    args.put("date", date);
    args.put("empty", empty);
    values = requestSerializer.serialize(args);
    RequestRootDeserializer<Object> requestDeserializer = providerOperationProtobuf.findRequestDesirializer();
    Map<String, Object> decodedArgs = requestDeserializer.deserialize(values);
    Assert.assertEquals(boolValue, decodedArgs.get("boolValue"));
    Assert.assertEquals(iValue, decodedArgs.get("iValue"));
    Assert.assertEquals(lValue, decodedArgs.get("lValue"));
    Assert.assertEquals(fValue, decodedArgs.get("fValue"));
    Assert.assertEquals(dValue, decodedArgs.get("dValue"));
    Assert.assertArrayEquals(iArray, (int[]) decodedArgs.get("iArray"));
    Assert.assertEquals(color, decodedArgs.get("color"));
    Assert.assertEquals(date, decodedArgs.get("date"));
    Assert.assertTrue(decodedArgs.get("localDate") instanceof LocalDate);
    Assert.assertEquals(localDate, decodedArgs.get("localDate"));
    Assert.assertTrue(decodedArgs.get("empty") instanceof Empty);

    // default value testing
    args.put("boolValue", false);
    args.put("iValue", 0);
    args.put("lValue", 0L);
    args.put("fValue", 0F);
    args.put("dValue", 0D);
    args.put("sValue", null);
    args.put("iArray", new int[0]);
    args.put("color", null);
    args.put("localDate", null);
    args.put("date", null);
    args.put("empty", null);
    values = requestSerializer.serialize(args);
    decodedArgs = requestDeserializer.deserialize(values);
    Assert.assertEquals(null, decodedArgs.get("boolValue"));
    Assert.assertEquals(null, decodedArgs.get("iValue"));
    Assert.assertEquals(null, decodedArgs.get("lValue"));
    Assert.assertEquals(null, decodedArgs.get("fValue"));
    Assert.assertEquals(null, decodedArgs.get("dValue"));
    Assert.assertEquals(null, decodedArgs.get("iArray"));
    Assert.assertEquals(null, decodedArgs.get("color"));
    Assert.assertEquals(null, decodedArgs.get("localDate"));
    Assert.assertEquals(null, decodedArgs.get("date"));
    Assert.assertEquals(null, decodedArgs.get("empty"));

    // response message
    RootSerializer responseSerializer = providerOperationProtobuf.findResponseSerializer(200);
    values = responseSerializer.serialize(30);
    ResponseRootDeserializer<Object> responseDeserializer = consumerOperationProtobuf.findResponseDesirialize(200);
    Object decodedValue = responseDeserializer.deserialize(values);
    Assert.assertEquals(30, (int) decodedValue);
  }
}
