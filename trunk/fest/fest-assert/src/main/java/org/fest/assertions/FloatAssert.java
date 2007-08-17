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
 * Understands assertion methods for <code>float</code>s. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(float)}</code>.
 * 
 * @author Yvonne Wang
 */
public class FloatAssert {

  private static final float ZERO = 0f;
  
  private final float actual;

  FloatAssert(float actual) {
    this.actual = actual;
  }

  /**
   * Verifies that the actual <code>float</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not equal to the given one.
   */
  public FloatAssert isEqualTo(float expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is not equal to the given one.
   * @param other the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is equal to the given one.
   */
  public FloatAssert isNotEqualTo(float other) {
    failIfEqual(actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is greater than the given one.
   * @param smaller the value expected to be smaller than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is less than or equal to the given one.
   */
  public FloatAssert isGreaterThan(float smaller) {
    failIfNotGreaterThan(actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is less than the given one.
   * @param bigger the value expected to be bigger than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is greater than or equal to the given one.
   */
  public FloatAssert isLessThan(float bigger) {
    failIfNotLessThan(actual, bigger);
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> value is equal to <code>{@link Float#NaN}</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not equal to <code>NaN</code>.
   */
  public FloatAssert isNaN() { return isEqualTo(Float.NaN); }

  /**
   * Verifies that the actual <code>float</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not positive.
   */
  public FloatAssert isPositive() { return isGreaterThan(ZERO); }

  /**
   * Verifies that the actual <code>float</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not negative.
   */
  public FloatAssert isNegative() { return isLessThan(ZERO); }

  /**
   * Verifies that the actual <code>float</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> value is not equal to zero.
   */
  public FloatAssert isZero() { return isEqualTo(ZERO); }
}

