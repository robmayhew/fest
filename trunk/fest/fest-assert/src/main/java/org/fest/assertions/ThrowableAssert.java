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

/**
 * Understands assertion methods for <code>{@link Throwable}</code>. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(Throwable)}</code>.
 * 
 * @author David DIDIER
 */
public final class ThrowableAssert extends GenericAssert<Throwable> {

  /**
   * Creates a new <code>ThrowableAssert</code>.
   * @param actual the actual <code>Throwable</code> to test.
   */
  ThrowableAssert(Throwable actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert as(String description) {
    return (ThrowableAssert) description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ThrowableAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>Throwable</code> has the given class for cause.
   * @param causeClass the cause to check the actual <code>Throwable</code> against.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Throwable</code> has not the given class for cause.
   * @throws IllegalArgumentException if <tt>causeClass</tt> is <code>null</code>.
   */
  public ThrowableAssert hasCause(Class<? extends Throwable> causeClass) {
    isNotNull();
    if (causeClass == null) { throw new IllegalArgumentException("'causeClass' cannot be null"); }
    Throwable actualCause = actual.getCause();
    if (actualCause == null || !actualCause.getClass().equals(causeClass)) 
      notMatchingCauseFailure(causeClass, actualCause);
    return this;
  }

  private void notMatchingCauseFailure(Class<? extends Throwable> causeClass, Throwable actualCause) {
    fail(concat(
        format(description()), "expected cause:", bracketAround(causeClass.getName()), " but was:", bracketAround(actual)));
  }

  /**
   * Verifies that the actual <code>Throwable</code> has the given class in its ancestor tree of causes.
   * 
   * @param causeClass the cause to check the actual <code>Throwable</code> against.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> has not the given class in its ancestor tree of
   *           causes.
   * @throws IllegalArgumentException if <tt>causeClass</tt> is <code>null</code>.
   */
  public ThrowableAssert hasCauseAsAncestor(Class<? extends Throwable> causeClass) {
    isNotNull();

    if (causeClass == null) { throw new IllegalArgumentException("'causeClass' cannot be null!"); }

    Throwable actualCause = actual.getCause();

    while (actualCause != null) {
      if (actualCause.getClass().equals(causeClass)) { return this; }

      actualCause = actualCause.getCause();
    }

    fail(concat(format(description()), "expected cause as ancestor:", bracketAround(causeClass.getName())));

    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> has no cause.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> has a cause.
   */
  public ThrowableAssert hasNoCause() {
    isNotNull();

    Throwable actualCause = actual.getCause();

    if (actualCause != null) {
      fail(concat(format(description()), "expected no cause but was:", bracketAround(actualCause.getClass().getName())));
    }

    return this;
  }

  /**
   * Verifies that the actual <code>Throwable</code> is equal to the given one.
   * 
   * @param expected the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> is not equal to the given one.
   */
  public ThrowableAssert isEqualTo(Throwable expected) {
    return (ThrowableAssert) assertEqualTo(expected);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not equal to the given one.
   * 
   * @param other the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> is equal to the given one.
   */
  public ThrowableAssert isNotEqualTo(Throwable other) {
    return (ThrowableAssert) assertNotEqualTo(other);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not <code>null</code>.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> is <code>null</code>.
   */
  public ThrowableAssert isNotNull() {
    return (ThrowableAssert) assertNotNull();
  }

  /**
   * Verifies that the actual <code>Throwable</code> is not the same as the given one.
   * 
   * @param other the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> is the same as the given one.
   */
  public ThrowableAssert isNotSameAs(Throwable other) {
    return (ThrowableAssert) assertNotSameAs(other);
  }

  /**
   * Verifies that the actual <code>Throwable</code> is the same as the given one.
   * 
   * @param expected the given <code>Throwable</code> to compare the actual <code>Throwable</code> to.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> is not the same as the given one.
   */
  public ThrowableAssert isSameAs(Throwable expected) {
    return (ThrowableAssert) assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>Throwable</code> satisfies the given condition.
   * 
   * @param condition the condition to satisfy.
   * 
   * @return this assertion object.
   * 
   * @throws AssertionError if the actual <code>Throwable</code> does not satisfy the given condition.
   */
  public ThrowableAssert satisfies(Condition<Throwable> condition) {
    return (ThrowableAssert) verify(condition);
  }
}
