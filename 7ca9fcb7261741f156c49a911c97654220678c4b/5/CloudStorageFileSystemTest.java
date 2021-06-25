/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.storage.contrib.nio;

import static com.google.common.truth.Truth.assertThat;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.testing.EqualsTester;
import com.google.common.testing.NullPointerTester;
import com.google.cloud.storage.testing.LocalGcsHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Unit tests for {@link CloudStorageFileSystem}.
 */
@RunWith(JUnit4.class)
public class CloudStorageFileSystemTest {

  private static final String ALONE =
      "To be, or not to be, that is the question—\n"
          + "Whether 'tis Nobler in the mind to suffer\n"
          + "The Slings and Arrows of outrageous Fortune,\n"
          + "Or to take Arms against a Sea of troubles,\n"
          + "And by opposing, end them? To die, to sleep—\n"
          + "No more; and by a sleep, to say we end\n"
          + "The Heart-ache, and the thousand Natural shocks\n"
          + "That Flesh is heir to? 'Tis a consummation\n";

  @Before
  public void before() {
    CloudStorageFileSystemProvider.setGCloudOptions(LocalGcsHelper.options());
  }

  @Test
  public void testGetPath() throws IOException {
    try (FileSystem fs = CloudStorageFileSystem.forBucket("bucket")) {
      assertThat(fs.getPath("/angel").toString()).isEqualTo("/angel");
      assertThat(fs.getPath("/angel").toUri().toString()).isEqualTo("gs://bucket/angel");
    }
  }

  @Test
  public void testWrite() throws IOException {
    try (FileSystem fs = CloudStorageFileSystem.forBucket("bucket")) {
      Files.write(fs.getPath("/angel"), ALONE.getBytes(UTF_8));
    }
    assertThat(new String(Files.readAllBytes(Paths.get(URI.create("gs://bucket/angel"))), UTF_8))
        .isEqualTo(ALONE);
  }

  @Test
  public void testRead() throws IOException {
    Files.write(Paths.get(URI.create("gs://bucket/angel")), ALONE.getBytes(UTF_8));
    try (FileSystem fs = CloudStorageFileSystem.forBucket("bucket")) {
      assertThat(new String(Files.readAllBytes(fs.getPath("/angel")), UTF_8)).isEqualTo(ALONE);
    }
  }

  @Test
  public void testExists_false() throws IOException {
    try (FileSystem fs = FileSystems.getFileSystem(URI.create("gs://bucket"))) {
      assertThat(Files.exists(fs.getPath("/angel"))).isFalse();
    }
  }

  @Test
  public void testExists_true() throws IOException {
    Files.write(Paths.get(URI.create("gs://bucket/angel")), ALONE.getBytes(UTF_8));
    try (FileSystem fs = CloudStorageFileSystem.forBucket("bucket")) {
      assertThat(Files.exists(fs.getPath("/angel"))).isTrue();
    }
  }

  @Test
  public void testGetters() throws IOException {
    try (FileSystem fs = CloudStorageFileSystem.forBucket("bucket")) {
      assertThat(fs.isOpen()).isTrue();
      assertThat(fs.isReadOnly()).isFalse();
      assertThat(fs.getRootDirectories()).containsExactly(fs.getPath("/"));
      assertThat(fs.getFileStores()).isEmpty();
      assertThat(fs.getSeparator()).isEqualTo("/");
      assertThat(fs.supportedFileAttributeViews()).containsExactly("basic", "gcs");
    }
  }

  @Test
  public void testEquals() throws IOException {
    try (FileSystem bucket1 = CloudStorageFileSystem.forBucket("bucket");
        FileSystem bucket2 = FileSystems.getFileSystem(URI.create("gs://bucket"));
        FileSystem doge1 = CloudStorageFileSystem.forBucket("doge");
        FileSystem doge2 = FileSystems.getFileSystem(URI.create("gs://doge"))) {
      new EqualsTester()
          .addEqualityGroup(bucket1, bucket2)
          .addEqualityGroup(doge1, doge2)
          .testEquals();
    }
  }

  @Test
  public void testNullness() throws IOException, NoSuchMethodException, SecurityException {
    try (FileSystem fs = FileSystems.getFileSystem(URI.create("gs://bucket"))) {
      NullPointerTester tester =
          new NullPointerTester()
              .ignore(CloudStorageFileSystem.class.getMethod("equals", Object.class))
              .setDefault(CloudStorageConfiguration.class, CloudStorageConfiguration.DEFAULT);
      tester.testAllPublicStaticMethods(CloudStorageFileSystem.class);
      tester.testAllPublicInstanceMethods(fs);
    }
  }
}
