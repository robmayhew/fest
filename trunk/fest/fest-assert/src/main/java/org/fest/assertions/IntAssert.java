/*
 * Created on Jun 14, 2007
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

import static org.fest.assertions.PrimitiveFail.failIfEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotGreaterThan;
import static org.fest.assertions.PrimitiveFail.failIfNotLessThan;

/**
 * Understands assert method for <code>int</code>s.
 *
 * @author Yvonne Wang
 */
public final class IntAssert{
  private int actual;

  IntAssert(int actual) {
    this.actual = actual;
  }
  
  public IntAssert isEqualTo(int expected) {
    failIfNotEqual(actual, expected);
    return this;
  }
  
  public IntAssert isNotEqualTo(int other) {
    failIfEqual(actual, other);
    return this;
  }
  
  public IntAssert isGreaterThan(int smaller) {
    failIfNotGreaterThan(actual, smaller);
    return this;
  }
  
  public IntAssert isLessThan(int bigger) {
    failIfNotLessThan(actual, bigger);
    return this;
  }
  
  public IntAssert isPositive() { return isGreaterThan(0); }

  public IntAssert isNegative() { return isLessThan(0); }
  
  public IntAssert isZero() { return isEqualTo(0); }
}
