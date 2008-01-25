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
 * Understands assertion methods for <code>byte</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(byte)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public final class ByteAssert extends PrimitiveAssert {

  private final byte actual;

  private static final byte ZERO = (byte)0;

  ByteAssert(byte actual) {
    this.actual = actual;
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ByteAssert as(String description) {
    return (ByteAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ByteAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>byte</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not equal to the given one.
   */
  public ByteAssert isEqualTo(byte expected) {
    failIfNotEqual(description(), actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> value is not equal to the given one.
   * @param other the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is equal to the given one.
   */
  public ByteAssert isNotEqualTo(byte other) {
    failIfEqual(description(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> value is greater than the given one.
   * @param smaller the value expected to be smaller than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is less than or equal to the given one.
   */
  public ByteAssert isGreaterThan(byte smaller) {
    failIfNotGreaterThan(description(), actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> value is less than the given one.
   * @param bigger the value expected to be bigger than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is greater than or equal to the given one.
   */
  public ByteAssert isLessThan(byte bigger) {
    failIfNotLessThan(description(), actual, bigger);
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> value is greater or equal to the given one.
   * @param smaller the value expected to be smaller or equal to the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is strictly less than or equal to the given one.
   */
  public ByteAssert isGreaterOrEqualTo(byte smaller) {
    failIfNotGreaterOrEqualTo(description(), actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> value is less or equal to the given one.
   * @param bigger the value expected to be bigger or equal to the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is strictly greater than or equal to the given one.
   */
  public ByteAssert isLessOrEqualTo(byte bigger) {
    failIfNotLessOrEqualTo(description(), actual, bigger);
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not positive.
   */
  public ByteAssert isPositive() { return isGreaterThan(ZERO); }

  /**
   * Verifies that the actual <code>byte</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not negative.
   */
  public ByteAssert isNegative() { return isLessThan(ZERO); }

  /**
   * Verifies that the actual <code>byte</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> value is not equal to zero.
   */
  public ByteAssert isZero() { return isEqualTo(ZERO); }
}
