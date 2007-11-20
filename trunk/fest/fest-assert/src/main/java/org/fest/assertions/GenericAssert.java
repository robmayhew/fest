/*
 * Created on May 21, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Fail.failIfEqual;
import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.assertions.Fail.failIfNotNull;
import static org.fest.assertions.Fail.failIfNotSame;
import static org.fest.assertions.Fail.failIfNull;
import static org.fest.assertions.Fail.failIfSame;
import static org.fest.assertions.Formatting.bracketAround;
import static org.fest.assertions.Formatting.format;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands a template for assertion methods.
 * @param <T> the type of object implementations of this template can verify. 
 *
 * @author Yvonne Wang
 */
abstract class GenericAssert<T> extends Assert {

  final T actual;
  private String description;

  /**
   * Creates a new <code>{@link GenericAssert}</code>.
   * @param actual the actual target to verify.
   */
  GenericAssert(T actual) {
    this.actual = actual;
  }

  /**
   * Asserts that the actual value (specified in the constructor of this class) is <code>null</code>.
   * @throws AssertionError if the actual value is not <code>null</code>.
   */
  public final void isNull() {
    failIfNotNull(description, actual);
  }

  /**
   * Verifies that the actual value satisfies the given condition. 
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual value does not satisfy the given condition.
   */
  protected abstract GenericAssert<T> satisfies(Condition<T> condition);
  
  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract GenericAssert<T> as(String description);

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in 
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  protected abstract GenericAssert<T> describedAs(String description);

  /**
   * Verifies that the actual value is equal to the given one.
   * @param expected the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is not equal to the given one.
   */
  protected abstract GenericAssert<T> isEqualTo(T expected);

  /**
   * Verifies that the actual value is not equal to the given one.
   * @param other the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is equal to the given one.
   */
  protected abstract GenericAssert<T> isNotEqualTo(T other);
  
  /**
   * Verifies that the actual value is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual value is <code>null</code>.
   */
  protected abstract GenericAssert<T> isNotNull();

  /**
   * Verifies that the actual value is the same as the given one.
   * @param expected the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is not the same as the given one.
   */
  protected abstract GenericAssert<T> isSameAs(T expected);

  /**
   * Verifies that the actual value is not the same as the given one.
   * @param other the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is the same as the given one.
   */
  protected abstract GenericAssert<T> isNotSameAs(T other);

  /**
   * Returns the description of the actual value in this assertion.
   * @return the description of the actual value in this assertion.
   */
  public final String description() {
    return description;
  }

  protected final GenericAssert<T> verify(Condition<T> condition) {
    if (condition == null) throw new IllegalArgumentException("condition cannot be null");
    if (!condition.matches(actual)) fail(conditionFailedMessage(condition));
    return this;
  }
  
  private String conditionFailedMessage(Condition<T> condition) {
    String s = condition.description();
    if (isEmpty(s)) return concat(format(description), "condition failed with: ", bracketAround(actual));
    return concat(format(description), "expected:", s, " but was:", bracketAround(actual));
  }
  
  protected GenericAssert<T> description(String description) {
    this.description = description;
    return this;
  }

  protected final GenericAssert<T> assertEqualTo(T expected) {
    failIfNotEqual(description, actual, expected);
    return this;
  }

  protected final GenericAssert<T> assertNotEqualTo(T obj) {
    failIfEqual(description, actual, obj);
    return this;
  }

  protected final GenericAssert<T> assertNotNull() {
    failIfNull(description, actual);
    return this;
  }

  protected final GenericAssert<T> assertSameAs(T expected) {
    failIfNotSame(description, actual, expected);
    return this;
  }

  protected final GenericAssert<T> assertNotSameAs(T expected) {
    failIfSame(description, actual, expected);
    return this;
  }
}
