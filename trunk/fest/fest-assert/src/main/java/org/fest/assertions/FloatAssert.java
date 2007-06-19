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
 * Understands assertion methods for <code>float</code>s.
 * 
 * @author Yvonne Wang
 */
public class FloatAssert {

  private static final float ZERO = 0f;
  
  private final float actual;

  FloatAssert(float actual) {
    this.actual = actual;
  }

  public FloatAssert isEqualTo(float expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  public FloatAssert isNotEqualTo(float other) {
    failIfEqual(actual, other);
    return this;
  }

  public FloatAssert isZero() {
    return isEqualTo(ZERO);
  }

  public FloatAssert isGreaterThan(float smaller) {
    failIfNotGreaterThan(actual, smaller);
    return this;
  }

  public FloatAssert isLessThan(float bigger) {
    failIfNotLessThan(actual, bigger);
    return this;
  }

  public FloatAssert isNaN() { return isEqualTo(Float.NaN); }

  public FloatAssert isPositive() { return isGreaterThan(ZERO); }

  public FloatAssert isNegative() { return isLessThan(ZERO); }
}
