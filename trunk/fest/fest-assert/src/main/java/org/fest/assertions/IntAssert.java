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

import static java.lang.String.valueOf;
import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.util.Strings.concat;

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
    if (actual != expected) fail(errorMessageIfNotEqual(valueOf(actual), valueOf(expected)));
    return this;
  }
  
  public IntAssert isNotEqualTo(int other) {
    if (actual == other) fail(errorMessageIfEqual(valueOf(actual), valueOf(other)));
    return this;
  }
  
  public IntAssert isGreaterThan(int smaller) {
    if (smaller >= actual) fail("should be greater than", smaller);
    return this;
  }
  
  public IntAssert isLessThan(int bigger) {
    if (bigger <= actual) fail("should be less than", bigger);
    return this;
  }
  
  private void fail(String reason, int expected) {
    fail(concat(valueOf(actual), " ", reason, " ", valueOf(expected)));
  }

  public IntAssert isPositive() { return isGreaterThan(0); }

  public IntAssert isNegative() { return isLessThan(0); }
  
  public IntAssert isZero() { return isEqualTo(0); }
}
