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

import static java.lang.String.valueOf;
import static org.fest.assertions.Fail.*;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Closeables.close;
import static org.fest.util.Strings.*;

import java.io.*;

/**
 * Understands assertion methods for <code>File</code>. To create a new instance of this class use the method <code>
 * {@link Assertions#assertThat(File)}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FileAssert extends GenericAssert<File> {

  private static final String EOF = "EOF";

  /**
   * Creates a new <code>FileAssert</code>.
   * @param actual the actual <code>File</code> to test.
   */
  FileAssert(File actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>.
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  @Override public FileAssert as(String description) {
    return (FileAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  @Override public FileAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>File</code> does not exist.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> exists.
   */
  public FileAssert doesNotExist() {
    isNotNull();
    if (actual.exists()) fail(concat("file should not exist:", quotedAbsolutePath()));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> does exist.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> does not exist.
   */
  public FileAssert exists() {
    isNotNull();
    assertExists(actual);
    return this;
  }

  /**
   * Verifies that the size of the actual <code>File</code> is equal to the given one.
   * @param expected the expected size of the actual <code>File</code>.
   * @return this assertion object.
   * @throws AssertionError if the size of the actual <code>File</code> is not equal to the given one.
   */
  public FileAssert hasSize(long expected) {
    isNotNull();
    long size = actual.length();
    if (size != expected)
      fail(concat(
          "expected file size of " + quotedAbsolutePath() + ":", inBrackets(valueOf(expected)),
          " but was:", inBrackets(valueOf(size))
      ));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is a directory.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not a directory.
   */
  public FileAssert isDirectory() {
    isNotNull();
    if (!actual.isDirectory())
      fail(concat("file should be a directory:", quotedAbsolutePath()));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is equal to the given one.
   * @param expected the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not equal to the given one.
   */
  public FileAssert isEqualTo(File expected) {
    return (FileAssert)assertEqualTo(expected);
  }

  /**
   * Verifies that the actual <code>File</code> is a regular file.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not a regular file.
   */
  public FileAssert isFile() {
    isNotNull();
    if (!actual.isFile())
      fail(concat("file should be a regular file:", quotedAbsolutePath()));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is not equal to the given one.
   * @param other the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is equal to the given one.
   */
  public FileAssert isNotEqualTo(File other) {
    return (FileAssert)assertNotEqualTo(other);
  }

  /**
   * Verifies that the actual <code>File</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is <code>null</code>.
   */
  @Override public FileAssert isNotNull() {
    return (FileAssert)assertNotNull();
  }

  /**
   * Verifies that the actual <code>File</code> is not the same as the given one.
   * @param other the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is the same as the given one.
   */
  public FileAssert isNotSameAs(File other) {
    return (FileAssert)assertNotSameAs(other);
  }

  /**
   * Verifies that the actual <code>File</code> is the same as the given one.
   * @param expected the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not the same as the given one.
   */
  public FileAssert isSameAs(File expected) {
    return (FileAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>File</code> satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> does not satisfy the given condition.
   */
  @Override public FileAssert satisfies(Condition<File> condition) {
    return (FileAssert)verify(condition);
  }

  /**
   * Verifies that the content of the actual <code>File</code> is equal to the content of the given one. Adapted from
   * <a href="http://junit-addons.sourceforge.net/junitx/framework/FileAssert.html" target="_blank">FileAssert</a> (from
   * <a href="http://sourceforge.net/projects/junit-addons">JUnit-addons</a>.)
   * @param expected the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the content of the actual <code>File</code> is not equal to the content of the given
   *          one.
   */
  public FileAssert hasSameContentAs(File expected) {
    isNotNull();
    failIfNull("expected file should not be null", expected);
    assertExists(actual).assertExists(expected);
    InputStream expectedInputStream = null;
    InputStream actualInputStream = null;
    try {
      expectedInputStream = new FileInputStream(expected);
      actualInputStream = new FileInputStream(actual);
      failIfNotEqual(reader(actualInputStream), reader(expectedInputStream));
    } catch (Exception e) {
      String message = concat(
                        description(),
                        "unable to compare contents of files:",
                        quotedAbsolutePath(), " and ", quotedAbsolutePath(expected));
      fail(message, e);
    } finally {
      close(expectedInputStream);
      close(actualInputStream);
    }
    return this;
  }

  private LineNumberReader reader(InputStream inputStream) {
    return new LineNumberReader(new BufferedReader(new InputStreamReader(inputStream)));
  }

  private FileAssert assertExists(File file) {
    if (!file.exists()) fail(concat("file should exist:", quotedAbsolutePath(file)));
    return this;
  }

  /**
   * Asserts that two readers have the same content.
   * @param actual the actual reader.
   * @param expected the expected reader.
   * @throws IOException any I/O error thrown.
   * @throws AssertionError if the two readers are not equal.
   */
  private void failIfNotEqual(LineNumberReader actual, LineNumberReader expected) throws IOException {
    String formatted = description();
    formatted = formatted == null ? "" : concat(formatted, " ");
    while (true) {
      if (!expected.ready() && !actual.ready()) return;
      String expectedLine = expected.readLine();
      String actualLine = actual.readLine();
      int lineNumber = expected.getLineNumber();
      if (!actual.ready() && expected.ready())
        fail(errorMessageIfNotEqual(message(formatted, lineNumber), EOF, expectedLine));
      if (actual.ready() && !expected.ready())
        fail(errorMessageIfNotEqual(message(formatted, lineNumber), actualLine, EOF));
      Fail.failIfNotEqual(message(formatted, lineNumber), expectedLine, actualLine);
    }
  }

  private String message(String message, int line) {
    return concat(message, "line [", valueOf(line), "]");
  }

  /**
   * Verifies that the actual <code>File</code> is a relative path.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not a relative path.
   */
  public FileAssert isRelative() {
    isNotNull();
    if (actual.isAbsolute())
      fail(concat("file should be relative but is ", inBrackets(quotedAbsolutePath())));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is an absolute path.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not an absolute path.
   */
  public FileAssert isAbsolute() {
    isNotNull();
    if (!actual.isAbsolute())
      fail(concat("file should be absolute but is ", inBrackets(quotedAbsolutePath())));
    return this;
  }

  private String quotedAbsolutePath() {
    return quotedAbsolutePath(actual);
  }

  private String quotedAbsolutePath(File file) {
    return quote(file.getAbsolutePath());
  }
}
