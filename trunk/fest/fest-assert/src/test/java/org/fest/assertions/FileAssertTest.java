/*
 * Created on Dec 23, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import java.io.File;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FileAssert}</code>.
 *
 * @author David DIDIER
 */
public class FileAssertTest {

  private static final File DIRECTORY = new File("src/main/java/org/fest/assertions");
  private static final File FILE = new File(DIRECTORY, "FileAssert.java");

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfFileDoesExist() {
    new FileAssert(DIRECTORY).doesNotExist();
    new FileAssert(FILE).doesNotExist();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfFileDoesNotExist() {
    new FileAssert(new File("invalid")).exists();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfFileIsNotDirectory() {
    new FileAssert(FILE).isDirectory();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfFileIsNotFile() {
    new FileAssert(DIRECTORY).isFile();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfFileSizeIsDifferent() {
    new FileAssert(FILE).hasSize(1);
  }

  @Test public void shouldSucceedIfFileDoesExist() {
    new FileAssert(DIRECTORY).exists();
    new FileAssert(FILE).exists();
  }

  @Test public void shouldSucceedIfFileDoesNotExist() {
    new FileAssert(new File("invalid")).doesNotExist();
  }

  @Test public void shouldSucceedIfFileIsDirectory() {
    new FileAssert(DIRECTORY).isDirectory();
  }

  @Test public void shouldSucceedIfFileIsFile() {
    new FileAssert(FILE).isFile();
  }

  @Test public void shouldSucceedIfFileSizeIsEqual() {
    new FileAssert(FILE).hasSize(FILE.length());
  }
}
