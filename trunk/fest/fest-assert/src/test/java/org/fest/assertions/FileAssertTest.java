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
import java.util.logging.Logger;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.FileAssertTest.ErrorMessage.error;
import static org.fest.util.Strings.*;

import static org.testng.Assert.*;

/**
 * Tests for <code>{@link FileAssert}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FileAssertTest {

  private static final File DIRECTORY = new File("src/test/resources/org/fest/assertions");
  private static final File FILE = file("fileAssertTest1.txt");

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

  @Test(dataProvider = "differentFilesToCompare")
  public void shouldFailIfFilesHaveNotSameContent(String actualFile, String expectedFile, ErrorMessage expectedError) {
    try {
      new FileAssert(file(actualFile)).as("Some file").hasSameContentAs(file(expectedFile));
      fail("Must have raised an AssertionError!");
    } catch (AssertionError e) {
      expectedError.verify(e);
    }
  }

  @DataProvider(name="differentFilesToCompare")
  public Object[][] differentFilesToCompare() {
    return new Object[][] {
        { "fileAssertTest2.txt", "fileAssertTest3.txt", error(1, "abcde fghij z", "abcde fghij") },
        { "fileAssertTest2.txt", "fileAssertTest4.txt", error(2, "abcde fghij abcde fghij z", "abcde fghij abcde fghij")},
        { "fileAssertTest2.txt", "fileAssertTest5.txt", error(3, "abcde z", "abcde")},
        { "fileAssertTest3.txt", "fileAssertTest4.txt", error(1, "abcde fghij", "abcde fghij z") },
        { "fileAssertTest3.txt", "fileAssertTest5.txt", error(1, "abcde fghij", "abcde fghij z") },
        { "fileAssertTest4.txt", "fileAssertTest5.txt", error(2, "abcde fghij abcde fghij", "abcde fghij abcde fghij z") },
    };
  }

  static class ErrorMessage {
    private static Logger logger = Logger.getAnonymousLogger();

    private final int lineNumber;
    private final String actualLine;
    private final String expectedLine;

    static ErrorMessage error(int lineNumber, String actualLine, String expectedLine) {
      return new ErrorMessage(lineNumber, actualLine, expectedLine);
    }

    ErrorMessage(int lineNumber, String actualLine, String expectedLine) {
      this.lineNumber = lineNumber;
      this.actualLine = actualLine;
      this.expectedLine = expectedLine;
    }

    void verify(AssertionError e) {
      String message = e.getMessage();
      logger.info(message);
      assertTrue(message.contains(concat("line [", lineNumber, "]")));
      assertTrue(message.contains(concat("expected:<", quote(expectedLine), ">")));
      assertTrue(message.contains(concat("but was:<", quote(actualLine), ">")));
    }
  }

  @Test(dataProvider="files")
  public void shouldSucceedIfFilesHaveSameContent(String fileName) {
    new FileAssert(file(fileName)).hasSameContentAs(file(fileName));
  }

  @DataProvider(name="files")
  public Object[][] files() {
    return new Object[][] {
        { "fileAssertTest2.txt" },
        { "fileAssertTest3.txt" },
        { "fileAssertTest4.txt" },
        { "fileAssertTest5.txt" },
    };
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfFileIsNotAbsolute() {
    new FileAssert(FILE).isAbsolute();
  }

  @Test public void shouldSucceedIfFileIsAbsolute() {
      new FileAssert(FILE.getAbsoluteFile()).isAbsolute();
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfFileIsNotRelative() {
    new FileAssert(FILE.getAbsoluteFile()).isRelative();
  }

  @Test public void shouldSucceedIfFileIsRelative() {
      new FileAssert(FILE).isRelative();
  }

  private static File file(String name) {
    return new File(DIRECTORY, name);
  }
}
