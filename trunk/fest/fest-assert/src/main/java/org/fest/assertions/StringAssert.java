/*
 * Created on Dec 26, 2006
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2006 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.util.Strings.*;

import org.fest.util.Strings;

/**
 * Understands assertion methods for <code>String</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(String)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public final class StringAssert extends GroupAssert<String> {

  StringAssert(String actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public StringAssert as(String description) {
    return (StringAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public StringAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>String</code> satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> does not satisfy the given condition.
   */
  public StringAssert satisfies(Condition<String> condition) {
    return (StringAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>String</code> is empty (not <code>null</code> with zero characters.)
   * @throws AssertionError if the actual <code>String</code> is not <code>null</code> or not empty.
   */
  public void isEmpty() {
    isNotNull();
    if (!Strings.isEmpty(actual))
      fail(concat(actual(), " should be empty or null"));
  }

  /**
   * Verifies that the actual <code>String</code> contains at least on character.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> is <code>null</code> or empty.
   */
  public StringAssert isNotEmpty() {
    if (Strings.isEmpty(actual))
      fail(concat(actual(), " should not be empty"));
    return this;
  }

  /**
   * Verifies that the actual <code>String</code> is equal to the given one.
   * @param expected the given <code>String</code> to compare the actual <code>String</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> is not equal to the given one.
   */
  public StringAssert isEqualTo(String expected) {
    return (StringAssert)assertEqualTo(expected);
  }

  /**
   * Verifies that the actual <code>String</code> is not equal to the given one.
   * @param other the given <code>String</code> to compare the actual <code>String</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> is equal to the given one.
   */
  public StringAssert isNotEqualTo(String other) {
    return (StringAssert)assertNotEqualTo(other);
  }

  /**
   * Verifies that the actual <code>String</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> is <code>null</code>.
   */
  public StringAssert isNotNull() {
    return (StringAssert)assertNotNull();
  }

  /**
   * Verifies that the actual <code>String</code> is not the same as the given one.
   * @param other the given <code>String</code> to compare the actual <code>String</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> is the same as the given one.
   */
  public StringAssert isNotSameAs(String other) {
    return (StringAssert)assertNotSameAs(other);
  }

  /**
   * Verifies that the actual <code>String</code> is the same as the given one.
   * @param expected the given <code>String</code> to compare the actual <code>String</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> is not the same as the given one.
   */
  public StringAssert isSameAs(String expected) {
    return (StringAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the number of characters in the actual <code>String</code> is equal to the given one.
   * @param expected the expected number of characters in the actual <code>String</code>.
   * @return this assertion object.
   * @throws AssertionError if the number of characters of the actual <code>String</code> is not equal to the given
   * one.
   */
  public StringAssert hasSize(int expected) {
    return (StringAssert)assertEqualSize(expected);
  }

  protected int actualGroupSize() {
    isNotNull();
    return actual.length();
  }

  /**
   * Verifies that the actual <code>String</code> contains the given one.
   * @param expected the given <code>String</code> expected to be contained in the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> does not contain the given one.
   */
  public StringAssert contains(String expected) {
    isNotNull();
    if (actual.indexOf(expected) == -1)
      fail(concat(actual(), " should contain the String ", quote(expected)));
    return this;
  }

  /**
   * Verifies that the actual <code>String</code> ends with the given one.
   * @param expected the given <code>String</code> expected to be at the end of the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> does not end with the given one.
   */
  public StringAssert endsWith(String expected) {
    isNotNull();
    if (!actual.endsWith(expected))
      fail(concat(actual(), " should end with ", quote(expected)));
    return this;
  }

  /**
   * Verifies that the actual <code>String</code> starts with the given one.
   * @param expected the given <code>String</code> expected to be at the beginning of the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> does not start with the given one.
   */
  public StringAssert startsWith(String expected) {
    isNotNull();
    if (!actual.startsWith(expected))
      fail(concat(actual(), " should start with ", quote(expected)));
    return this;
  }

  /**
   * Verifies that the actual <code>String</code> does not contains the given one.
   * @param s the given <code>String</code> expected not to be contained in the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> does contain the given one.
   */
  public StringAssert excludes(String s) {
    isNotNull();
    if (actual.indexOf(s) != -1)
      fail(concat(actual(), " should not contain the String ", quote(s)));
    return this;
  }

  /**
   * Verifies that the actual <code>String</code> matches the given one.
   * @param regex the given regular expression expected to be matched by the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> does not match the given regular expression.
   */
  public StringAssert matches(String regex) {
    isNotNull();
    if (!actual.matches(regex))
      fail(concat(actual(), " should match the regular expression ", quote(regex)));
    return this;
  }

  /**
   * Verifies that the actual <code>String</code> does not match the given one.
   * @param regex the given regular expression expected not to be matched by the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>String</code> matches the given regular expression.
   */
  public StringAssert doesNotMatch(String regex) {
    isNotNull();
    if (actual.matches(regex))
      fail(concat(actual(), " should not match the regular expression ", quote(regex)));
    return this;
  }

  private String actual() {
    return concat("the String", quote(actual));
  }
}
