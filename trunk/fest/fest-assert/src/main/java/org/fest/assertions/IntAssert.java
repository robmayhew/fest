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
public class IntAssert{
  private int actual;

  IntAssert(int actual) {
    this.actual = actual;
  }
  
  public void isEqualTo(int expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(toString(actual), toString(expected)));
  }
  
  public void isNotEqualTo(int other) {
    if (actual == other) fail(errorMessageIfEqual(toString(actual), toString(other)));
  }
  
  public void isGreaterThan(int smaller) {
    if (smaller >= actual)
      fail(concat(toString(actual), " should be greater than ", toString(smaller)));
  }
  
  public void isLessThan(int bigger) {
    if (bigger <= actual)
      fail(concat(toString(actual), " should be less than ", toString(bigger)));
  }
  
  public void isPositive() {
    isGreaterThan(0);
  }
  
  public void isNegative() {
    isLessThan(0);
  }
  
  private String toString(int value) {
    return valueOf(value);
  }
}
