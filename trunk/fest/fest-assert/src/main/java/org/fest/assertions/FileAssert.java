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

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Formatting.bracketAround;
import static org.fest.assertions.Formatting.format;
import static org.fest.util.Strings.concat;

import java.io.File;

/**
 * Understands assertion methods for <code>File</code>. To create a new instance of this class use the method <code>
 * {@link Assertions#assertThat(File)}</code>.
 *
 * @author David DIDIER
 */
public final class FileAssert extends GenericAssert<File> {

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
    if (actual.exists()) fail(concat(format(description()), "file should not exist:", actual.getAbsolutePath()));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> does exist.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> does not exist.
   */
  public FileAssert exists() {
    isNotNull();
    if (!actual.exists()) fail(concat(format(description()), "file should exist:", actual.getAbsolutePath()));
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
    long actualSize = actual.length();
    if (actualSize != expected)
      fail(concat(
          format(description()),
          "expected file size of '" + actual + "':", bracketAround(expected), " but was:", bracketAround(actualSize)
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
      fail(concat(format(description()), "file should be a directory:", actual.getAbsolutePath()));
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
      fail(concat(format(description()), "file should be a regular file:", actual.getAbsolutePath()));
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
}