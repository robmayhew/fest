/*
 * Created on Jan 25, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static java.lang.String.valueOf;
import static org.fest.assertions.Fail.*;
import static org.fest.assertions.Formatting.*;
import static org.fest.util.Strings.concat;

/**
 * Understands failure methods for primitive types. 
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author David DIDIER
 */
public final class PrimitiveFail {

  /**
   * Fails if the given $<code>boolean</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>boolean</code>s are equal.
   */
  static void failIfEqual(String message, boolean first, boolean second) {
    if (first == second) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second))); 
  }  
  /**
   * Fails if the given $<code>char</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>char</code>s are equal.
   */
  static void failIfEqual(String message, char first, char second) {
    if (first == second) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second))); 
  }  
  /**
   * Fails if the given $<code>byte</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>byte</code>s are equal.
   */
  static void failIfEqual(String message, byte first, byte second) {
    if (first == second) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second))); 
  }  
  /**
   * Fails if the given $<code>short</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>short</code>s are equal.
   */
  static void failIfEqual(String message, short first, short second) {
    if (first == second) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second))); 
  }  
  /**
   * Fails if the given $<code>int</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>int</code>s are equal.
   */
  static void failIfEqual(String message, int first, int second) {
    if (first == second) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second))); 
  }  
  /**
   * Fails if the given $<code>long</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>long</code>s are equal.
   */
  static void failIfEqual(String message, long first, long second) {
    if (first == second) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second))); 
  }  
  /**
   * Fails if the given $<code>float</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>float</code>s are equal.
   */
  static void failIfEqual(String message, float first, float second) {
    if (Float.compare(first, second) == 0) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second)));
  }  
  /**
   * Fails if the given $<code>double</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>double</code>s are equal.
   */
  static void failIfEqual(String message, double first, double second) {
    if (Double.compare(first, second) == 0) fail(errorMessageIfEqual(message, valueOf(first), valueOf(second)));
  }  
  
  /**
   * Fails if the given <code>boolean</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>boolean</code>s are not equal.
   */
  static void failIfNotEqual(String message, boolean actual, boolean expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }
  /**
   * Fails if the given <code>char</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>char</code>s are not equal.
   */
  static void failIfNotEqual(String message, char actual, char expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }
  /**
   * Fails if the given <code>byte</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>byte</code>s are not equal.
   */
  static void failIfNotEqual(String message, byte actual, byte expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }
  /**
   * Fails if the given <code>short</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>short</code>s are not equal.
   */
  static void failIfNotEqual(String message, short actual, short expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }
  /**
   * Fails if the given <code>int</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>int</code>s are not equal.
   */
  static void failIfNotEqual(String message, int actual, int expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }
  /**
   * Fails if the given <code>long</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>long</code>s are not equal.
   */
  static void failIfNotEqual(String message, long actual, long expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }
  /**
   * Fails if the given <code>float</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>float</code>s are not equal.
   */
  static void failIfNotEqual(String message, float actual, float expected) {
    if (Float.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }
  /**
   * Fails if the given <code>double</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>double</code>s are not equal.
   */
  static void failIfNotEqual(String message, double actual, double expected) {
    if (Double.compare(actual, expected) != 0) 
      fail(errorMessageIfNotEqual(message, valueOf(actual), valueOf(expected)));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, char first, char second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, byte first, byte second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, short first, short second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, int first, int second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, long first, long second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, float first, float second) {
    if (Float.compare(first, second) >= 0) fail(errorMessageIfNotLessThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, double first, double second) {
    if (Double.compare(first, second) >= 0) fail(errorMessageIfNotLessThan(message, valueOf(first), valueOf(second)));
  }

  private static String errorMessageIfNotLessThan(String message, String first, String second) {
    return concat(format(message), inBrackets(first), " should be less than ", inBrackets(second));
  }
  
  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, char first, char second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, byte first, byte second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, short first, short second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, int first, int second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, long first, long second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, float first, float second) {
    if (Float.compare(first, second) <= 0) fail(errorMessageIfNotGreaterThan(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, double first, double second) {
    if (Double.compare(first, second) <= 0) 
      fail(errorMessageIfNotGreaterThan(message, valueOf(first), valueOf(second)));
  }

  private static String errorMessageIfNotGreaterThan(String message, String first, String second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }

  /**
   * Fails if the first value is not less or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less or equal to the second value.
   */
  static void failIfNotLessOrEqualTo(String message, char first, char second) {
    if (first > second) fail(errorMessageIfNotLessOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less or equal to the second value.
   */
  static void failIfNotLessOrEqualTo(String message, byte first, byte second) {
    if (first > second) fail(errorMessageIfNotLessOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less or equal to the second value.
   */
  static void failIfNotLessOrEqualTo(String message, short first, short second) {
    if (first > second) fail(errorMessageIfNotLessOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less or equal to the second value.
   */
  static void failIfNotLessOrEqualTo(String message, int first, int second) {
    if (first > second) fail(errorMessageIfNotLessOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less or equal to the second value.
   */
  static void failIfNotLessOrEqualTo(String message, long first, long second) {
    if (first > second) fail(errorMessageIfNotLessOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less or equal to the second value.
   */
  static void failIfNotLessOrEqualTo(String message, float first, float second) {
    if (Float.compare(first, second) > 0) 
      fail(errorMessageIfNotLessOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not less or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less or equal to the second value.
   */
  static void failIfNotLessOrEqualTo(String message, double first, double second) {
    if (Double.compare(first, second) > 0) 
      fail(errorMessageIfNotLessOrEqualTo(message, valueOf(first), valueOf(second)));
  }

  private static String errorMessageIfNotLessOrEqualTo(String message, String first, String second) {
    return concat(format(message), inBrackets(first), " should be less or equal to ", inBrackets(second));
  }

  /**
   * Fails if the first value is not greater or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater or equal to the second value.
   */
  static void failIfNotGreaterOrEqualTo(String message, char first, char second) {
    if (first < second) fail(errorMessageIfNotGreaterOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater or equal to the second value.
   */
  static void failIfNotGreaterOrEqualTo(String message, byte first, byte second) {
    if (first < second) fail(errorMessageIfNotGreaterOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater or equal to the second value.
   */
  static void failIfNotGreaterOrEqualTo(String message, short first, short second) {
    if (first < second) fail(errorMessageIfNotGreaterOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater or equal to the second value.
   */
  static void failIfNotGreaterOrEqualTo(String message, int first, int second) {
    if (first < second) fail(errorMessageIfNotGreaterOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater or equal to the second value.
   */
  static void failIfNotGreaterOrEqualTo(String message, long first, long second) {
    if (first < second) fail(errorMessageIfNotGreaterOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater or equal to the second value.
   */
  static void failIfNotGreaterOrEqualTo(String message, float first, float second) {
    if (Float.compare(first, second) < 0) 
      fail(errorMessageIfNotGreaterOrEqualTo(message, valueOf(first), valueOf(second)));
  }
  /**
   * Fails if the first value is not greater or equal to the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater or equal to the second value.
   */
  static void failIfNotGreaterOrEqualTo(String message, double first, double second) {
    if (Double.compare(first, second) < 0) 
      fail(errorMessageIfNotGreaterOrEqualTo(message, valueOf(first), valueOf(second)));
  }

  private static String errorMessageIfNotGreaterOrEqualTo(String message, String first, String second) {
    return concat(format(message), inBrackets(first), " should be greater or equal to ", inBrackets(second));
  }

  private PrimitiveFail() {}
}
