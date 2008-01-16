/*
 * Created on Sep 16, 2007
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

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Formatting.*;
import static org.fest.util.Strings.concat;

/**
 * Understands failure methods for primitive types. 
 * 
 * @author Yvonne Wang
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
    if (first == second) fail(errorMessageIfEqual(message, first, second)); 
  }
  
  /**
   * Fails if the given $<code>char</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>char</code>s are equal.
   */
  static void failIfEqual(String message, char first, char second) {
    if (first == second) fail(errorMessageIfEqual(message, first, second)); 
  }
  
  /**
   * Fails if the given $<code>byte</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>byte</code>s are equal.
   */
  static void failIfEqual(String message, byte first, byte second) {
    if (first == second) fail(errorMessageIfEqual(message, first, second)); 
  }
  
  /**
   * Fails if the given $<code>short</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>short</code>s are equal.
   */
  static void failIfEqual(String message, short first, short second) {
    if (first == second) fail(errorMessageIfEqual(message, first, second)); 
  }
  
  /**
   * Fails if the given $<code>int</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>int</code>s are equal.
   */
  static void failIfEqual(String message, int first, int second) {
    if (first == second) fail(errorMessageIfEqual(message, first, second)); 
  }
  
  /**
   * Fails if the given $<code>long</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>long</code>s are equal.
   */
  static void failIfEqual(String message, long first, long second) {
    if (first == second) fail(errorMessageIfEqual(message, first, second)); 
  }
  
  /**
   * Fails if the given $<code>float</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>float</code>s are equal.
   */
  static void failIfEqual(String message, float first, float second) {
    if (Float.compare(first, second) == 0) fail(errorMessageIfEqual(message, first, second));
  }
  
  /**
   * Fails if the given $<code>double</code>s are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>double</code>s are equal.
   */
  static void failIfEqual(String message, double first, double second) {
    if (Double.compare(first, second) == 0) fail(errorMessageIfEqual(message, first, second));
  }
  
  /**
   * Returns an error message to be used when two <code>boolean</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>boolean</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, boolean first, boolean second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when two <code>char</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>char</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, char first, char second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when two <code>byte</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>byte</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, byte first, byte second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when two <code>short</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>short</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, short first, short second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when two <code>int</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>int</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, int first, int second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when two <code>long</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>long</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, long first, long second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when two <code>float</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>float</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, float first, float second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when two <code>double</code>s that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>double</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, double first, double second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  /**
   * Fails if the given <code>boolean</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>boolean</code>s are not equal.
   */
  static void failIfNotEqual(String message, boolean actual, boolean expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given <code>char</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>char</code>s are not equal.
   */
  static void failIfNotEqual(String message, char actual, char expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given <code>byte</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>byte</code>s are not equal.
   */
  static void failIfNotEqual(String message, byte actual, byte expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given <code>short</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>short</code>s are not equal.
   */
  static void failIfNotEqual(String message, short actual, short expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given <code>int</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>int</code>s are not equal.
   */
  static void failIfNotEqual(String message, int actual, int expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given <code>long</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>long</code>s are not equal.
   */
  static void failIfNotEqual(String message, long actual, long expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given <code>float</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>float</code>s are not equal.
   */
  static void failIfNotEqual(String message, float actual, float expected) {
    if (Float.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given <code>double</code>s are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>double</code>s are not equal.
   */
  static void failIfNotEqual(String message, double actual, double expected) {
    if (Double.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Returns an error message to be used when two <code>boolean</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>boolean</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, boolean actual, boolean expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two <code>char</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>char</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, char actual, char expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two <code>byte</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>byte</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, byte actual, byte expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two <code>short</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>short</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, short actual, short expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two <code>int</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>int</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, int actual, int expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two <code>long</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>long</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, long actual, long expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two <code>float</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>float</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, float actual, float expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two <code>double</code>s that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>double</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, double actual, double expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, char first, char second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, byte first, byte second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, short first, short second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, int first, int second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, long first, long second) {
    if (first >= second) fail(errorMessageIfNotLessThan(message, first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, float first, float second) {
    if (Float.compare(first, second) >= 0) fail(errorMessageIfNotLessThan(message, first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(String message, double first, double second) {
    if (Double.compare(first, second) >= 0) fail(errorMessageIfNotLessThan(message, first, second));
  }

  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(String message, char first, char second) {
    return concat(format(message), inBrackets(first), " should be less than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(String message, byte first, byte second) {
    return concat(format(message), inBrackets(first), " should be less than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(String message, short first, short second) {
    return concat(format(message), inBrackets(first), " should be less than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(String message, int first, int second) {
    return concat(format(message), inBrackets(first), " should be less than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(String message, long first, long second) {
    return concat(format(message), inBrackets(first), " should be less than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(String message, float first, float second) {
    return concat(format(message), inBrackets(first), " should be less than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(String message, double first, double second) {
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
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, byte first, byte second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, short first, short second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, int first, int second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, long first, long second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(message, first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, float first, float second) {
    if (Float.compare(first, second) <= 0) fail(errorMessageIfNotGreaterThan(message, first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(String message, double first, double second) {
    if (Double.compare(first, second) <= 0) fail(errorMessageIfNotGreaterThan(message, first, second));
  }

  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(String message, char first, char second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(String message, byte first, byte second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(String message, short first, short second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(String message, int first, int second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(String message, long first, long second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(String message, float first, float second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(String message, double first, double second) {
    return concat(format(message), inBrackets(first), " should be greater than ", inBrackets(second));
  }
  
  private PrimitiveFail() {}
}
