/*
 * Created on Jun 18, 2007
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

import static org.fest.assertions.PrimitiveFail.*;

/**
 * Understands assertion methods for <code>long</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(long)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public final class LongAssert extends PrimitiveAssert {

  private static final long ZERO = 0L;

  private final long actual;

  LongAssert(long actual) {
    this.actual = actual;
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public LongAssert as(String description) {
    return (LongAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public LongAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>long</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not equal to the given one.
   */
  public LongAssert isEqualTo(long expected) {
    failIfNotEqual(description(), actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is not equal to the given one.
   * @param other the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is equal to the given one.
   */
  public LongAssert isNotEqualTo(long other) {
    failIfEqual(description(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is greater than the given one.
   * @param smaller the value expected to be smaller than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is less than or equal to the given one.
   */
  public LongAssert isGreaterThan(long smaller) {
    failIfNotGreaterThan(description(), actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is less than the given one.
   * @param bigger the value expected to be bigger than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is greater than or equal to the given one.
   */
  public LongAssert isLessThan(long bigger) {
    failIfNotLessThan(description(), actual, bigger);
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is greater or equal to the given one.
   * @param smaller the value expected to be smaller or equal to the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is strictly less than or equal to the given one.
   */
  public LongAssert isGreaterOrEqualTo(long smaller) {
    failIfNotGreaterOrEqualTo(description(), actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is less or equal to the given one.
   * @param bigger the value expected to be bigger or equal to the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is strictly greater than or equal to the given one.
   */
  public LongAssert isLessOrEqualTo(long bigger) {
    failIfNotLessOrEqualTo(description(), actual, bigger);
    return this;
  }

  /**
   * Verifies that the actual <code>long</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not positive.
   */
  public LongAssert isPositive() { return isGreaterThan(ZERO); }

  /**
   * Verifies that the actual <code>long</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not negative.
   */
  public LongAssert isNegative() { return isLessThan(ZERO); }

  /**
   * Verifies that the actual <code>long</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>long</code> value is not equal to zero.
   */
  public LongAssert isZero() { return isEqualTo(ZERO); }

}
