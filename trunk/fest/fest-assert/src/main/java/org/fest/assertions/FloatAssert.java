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

import static java.lang.String.valueOf;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.PrimitiveFail.failIfEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotEqual;
import static org.fest.util.Strings.concat;

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
    if (Float.compare(actual, smaller) <= 0) failed("should be greater than", smaller);
    return this;
  }

  public FloatAssert isLessThan(float bigger) {
    if (Float.compare(actual, bigger) >= 0) failed("should be less than", bigger);
    return this;
  }

  public void failed(String reason, float expected) {
    fail(concat(valueOf(actual), " ", reason, " ", valueOf(expected)));
  }

  public FloatAssert isNaN() { return isEqualTo(Float.NaN); }

  public FloatAssert isPositive() { return isGreaterThan(0.0F); }

  public FloatAssert isNegative() { return isLessThan(0.0F); }
}
