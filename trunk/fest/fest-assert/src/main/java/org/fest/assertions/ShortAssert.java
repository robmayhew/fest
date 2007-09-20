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

import static org.fest.assertions.PrimitiveFail.failIfEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotGreaterThan;
import static org.fest.assertions.PrimitiveFail.failIfNotLessThan;

/**
 * Understands assertion methods for <code>short</code>s. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(short)}</code>.
 * 
 * @author Yvonne Wang
 */
public class ShortAssert extends PrimitiveAssert {

  private static final short ZERO = (short)0;

  private final short actual;

  ShortAssert(short actual) {
    this.actual = actual;
  }

  /** {@inheritDoc} */
  public ShortAssert as(String description) {
    return (ShortAssert)description(description);
  }
  
  /** {@inheritDoc} */
  public ShortAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>short</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not equal to the given one.
   */
  public ShortAssert isEqualTo(short expected) {
    failIfNotEqual(description(), actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>short</code> value is not equal to the given one.
   * @param other the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is equal to the given one.
   */
  public ShortAssert isNotEqualTo(short other) {
    failIfEqual(description(), actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>short</code> value is greater than the given one.
   * @param smaller the value expected to be smaller than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is less than or equal to the given one.
   */
  public ShortAssert isGreaterThan(short smaller) {
    failIfNotGreaterThan(description(), actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>short</code> value is less than the given one.
   * @param bigger the value expected to be bigger than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is greater than or equal to the given one.
   */
  public ShortAssert isLessThan(short bigger) {
    failIfNotLessThan(description(), actual, bigger);
    return this;
  }

  /**
   * Verifies that the actual <code>short</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not positive.
   */
  public ShortAssert isPositive() { return isGreaterThan(ZERO); }

  /**
   * Verifies that the actual <code>short</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not negative.
   */
  public ShortAssert isNegative() { return isLessThan(ZERO); }

  /**
   * Verifies that the actual <code>short</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> value is not equal to zero.
   */
  public ShortAssert isZero() { return isEqualTo(ZERO); }
}
