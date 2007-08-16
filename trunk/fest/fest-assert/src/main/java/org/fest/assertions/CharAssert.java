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
import static org.fest.assertions.PrimitiveFail.failIfNotGreaterThan;
import static org.fest.assertions.PrimitiveFail.failIfNotLessThan;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>char</code>s.
 * 
 * @author Yvonne Wang
 */
public class CharAssert {

  private final char actual;

  CharAssert(char actual) {
    this.actual = actual;
  }

  /**
   * Verifies that the actual <code>char</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not equal to the given one.
   */
  public CharAssert isEqualTo(char expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is not equal to the given one.
   * @param other the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is equal to the given one.
   */
  public CharAssert isNotEqualTo(char other) {
    failIfEqual(actual, other);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is greater than the given one.
   * @param smaller the value expected to be smaller than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is less than or equal to the given one.
   */
  public CharAssert isGreaterThan(char smaller) {
    failIfNotGreaterThan(actual, smaller);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is less than the given one.
   * @param bigger the value expected to be bigger than the actual one.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is greater than or equal to the given one.
   */
  public CharAssert isLessThan(char bigger) {
    failIfNotLessThan(actual, bigger);
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is an uppercase value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not an uppercase value.
   */
  public CharAssert isUpperCase() {
    if (!Character.isUpperCase(actual)) fail(concat(valueOf(actual), " should be an uppercase character"));
    return this;
  }

  /**
   * Verifies that the actual <code>char</code> value is an lowercase value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>char</code> value is not an lowercase value.
   */
  public CharAssert isLowerCase() {
    if (!Character.isLowerCase(actual)) fail(concat(valueOf(actual), " should be a lowercase character"));
    return this;
  }
}
