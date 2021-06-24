/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.qa.utils;

import org.junit.ComparisonFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.stratio.qa.assertions.Assertions;

import java.util.concurrent.ExecutionException;

import java.util.Arrays;
import java.util.List;
import io.cucumber.datatable.DataTable;

public class KafkaSecUtilsIT {
    private final Logger logger = LoggerFactory
            .getLogger(KafkaSecUtilsIT.class);

    private KafkaSecUtils kafka_utils;

    @BeforeMethod
    public void setSettingsTest() {
        kafka_utils = new KafkaSecUtils();
        kafka_utils.createConnection(System.getProperty("KAFKA_HOSTS"));
    }

    @Test
    public void checkTopicDoesNotExistTest() {
        String topic = "checktopicdoesnotexist";

        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void checkTopicDoesNotExistWithExistentTopicTest() {
        String topic = "checktopicdoesnotexistwithexistenttopic";

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();

        try {
            kafka_utils.checkTopicDoesNotExist(topic);
        }
        catch (AssertionError|Exception e) {
            Assertions.assertThat(e).isInstanceOf(ComparisonFailure.class);
            Assertions.assertThat(e).hasMessage("[Topic " + topic + " exists.] expected:<[fals]e> but was:<[tru]e>");
        }

        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void createTopicTest() {
        String topic = "createtopictest";

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void createTopicExistentTest() {
        String topic = "createtopicexistent";

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();

        try {
            kafka_utils.createTopic(topic, null);
        }
        catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(ExecutionException.class);
            Assertions.assertThat(e).hasMessage("org.apache.kafka.common.errors.TopicExistsException: Topic '" + topic + "' already exists.");
        }

        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void listTopicsTest() throws Exception {
        String topic = "listtopics";

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.listTopics()).doesNotThrowAnyException();
        Assertions.assertThat(kafka_utils.listTopics()).isEqualTo("[" + topic + ", __confluent.support.metrics]");
        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void deleteTopicTest() {
        String topic = "deletetopic";

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void deleteTopicNonExistentTest() {
        String topic = "deletetopicnonexistent";

        try {
            kafka_utils.deleteTopic(topic);
        }
        catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(ExecutionException.class);
            Assertions.assertThat(e).hasMessage("org.apache.kafka.common.errors.UnknownTopicOrPartitionException: This server does not host this topic-partition.");
        }
    }

    @Test
    public void sendMessageTest() {
        String topic = "sendmessage";
        String message = "sendmessagetest";

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.sendMessage(topic, null, message)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.containsMessage(topic, null, message)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void containsNMessagesInTopicTest() {
        String topic = "containsnmessagesintopic";
        String message = "containsnmessagesintopictest";

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.sendMessage(topic, null, message)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.containsNMessagesInTopic(topic,"1", null, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }

    @Test
    public void containsNMessagesInTopicDataTableTest() {
        String topic = "containsnmessagesintopicdatatable";
        String message = "containsnmessagesintopicdatatabletest";
        List<List<String>> rawData = Arrays.asList(Arrays.asList(message), Arrays.asList(message + "2"));
        DataTable dataTable = DataTable.create(rawData);

        Assertions.assertThatCode(() -> kafka_utils.createTopic(topic, null)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicExists(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.sendMessage(topic, null, message)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.sendMessage(topic, null, message + "2")).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.containsNMessagesInTopic(topic,"2", null, dataTable)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.deleteTopic(topic)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> kafka_utils.checkTopicDoesNotExist(topic)).doesNotThrowAnyException();
    }
}
