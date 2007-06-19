/*
 * Created on Jun 18, 2007
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

import static org.fest.assertions.Fail.fail;
import static org.fest.util.Strings.concat;

/**
 * Understands failure methods for primitive types. 
 * 
 * @author Yvonne Wang
 */
public final class PrimitiveFail {

  /**
   * Fails if the given $<code>boolean</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>boolean</code>s are equal.
   */
  static void failIfEqual(boolean first, boolean second) {
    if (first == second) fail(errorMessageIfEqual(first, second)); 
  }
  
  /**
   * Fails if the given $<code>char</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>char</code>s are equal.
   */
  static void failIfEqual(char first, char second) {
    if (first == second) fail(errorMessageIfEqual(first, second)); 
  }
  
  /**
   * Fails if the given $<code>byte</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>byte</code>s are equal.
   */
  static void failIfEqual(byte first, byte second) {
    if (first == second) fail(errorMessageIfEqual(first, second)); 
  }
  
  /**
   * Fails if the given $<code>short</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>short</code>s are equal.
   */
  static void failIfEqual(short first, short second) {
    if (first == second) fail(errorMessageIfEqual(first, second)); 
  }
  
  /**
   * Fails if the given $<code>int</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>int</code>s are equal.
   */
  static void failIfEqual(int first, int second) {
    if (first == second) fail(errorMessageIfEqual(first, second)); 
  }
  
  /**
   * Fails if the given $<code>long</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>long</code>s are equal.
   */
  static void failIfEqual(long first, long second) {
    if (first == second) fail(errorMessageIfEqual(first, second)); 
  }
  
  /**
   * Fails if the given $<code>float</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>float</code>s are equal.
   */
  static void failIfEqual(float first, float second) {
    if (Float.compare(first, second) == 0) fail(errorMessageIfEqual(first, second));
  }
  
  /**
   * Fails if the given $<code>double</code>s are equal.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given <code>double</code>s are equal.
   */
  static void failIfEqual(double first, double second) {
    if (Double.compare(first, second) == 0) fail(errorMessageIfEqual(first, second));
  }
  
  /**
   * Returns an error message to be used when two <code>boolean</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>boolean</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(boolean first, boolean second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>char</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>char</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(char first, char second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>byte</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>byte</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(byte first, byte second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>short</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>short</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(short first, short second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>int</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>int</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(int first, int second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>long</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>long</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(long first, long second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>float</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>float</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(float first, float second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>double</code>s that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two <code>double</code>s that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(double first, double second) {
    return concat("<", valueOf(first), "> should not be equal to <", valueOf(second), ">");
  }
  
  /**
   * Fails if the given <code>boolean</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>boolean</code>s are not equal.
   */
  static void failIfNotEqual(boolean actual, boolean expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given <code>char</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>char</code>s are not equal.
   */
  static void failIfNotEqual(char actual, char expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given <code>byte</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>byte</code>s are not equal.
   */
  static void failIfNotEqual(byte actual, byte expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given <code>short</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>short</code>s are not equal.
   */
  static void failIfNotEqual(short actual, short expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given <code>int</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>int</code>s are not equal.
   */
  static void failIfNotEqual(int actual, int expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given <code>long</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>long</code>s are not equal.
   */
  static void failIfNotEqual(long actual, long expected) {
    if (actual != expected) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given <code>float</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>float</code>s are not equal.
   */
  static void failIfNotEqual(float actual, float expected) {
    if (Float.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given <code>double</code>s are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given <code>double</code>s are not equal.
   */
  static void failIfNotEqual(double actual, double expected) {
    if (Double.compare(actual, expected) != 0) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Returns an error message to be used when two <code>boolean</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>boolean</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(boolean actual, boolean expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>char</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>char</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(char actual, char expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>byte</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>byte</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(byte actual, byte expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>short</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>short</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(short actual, short expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>int</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>int</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(int actual, int expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>long</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>long</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(long actual, long expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>float</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>float</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(float actual, float expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Returns an error message to be used when two <code>double</code>s that are expected to be equal aren't. 
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two <code>double</code>s that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(double actual, double expected) {
    return concat("expected:<", valueOf(expected), "> but was:<", valueOf(actual), ">");
  }
  
  /**
   * Fails if the first value is not less than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(char first, char second) {
    if (first >= second) fail(errorMessageIfNotLessThan(first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(byte first, byte second) {
    if (first >= second) fail(errorMessageIfNotLessThan(first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(short first, short second) {
    if (first >= second) fail(errorMessageIfNotLessThan(first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(int first, int second) {
    if (first >= second) fail(errorMessageIfNotLessThan(first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(long first, long second) {
    if (first >= second) fail(errorMessageIfNotLessThan(first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(float first, float second) {
    if (Float.compare(first, second) >= 0) fail(errorMessageIfNotLessThan(first, second));
  }

  /**
   * Fails if the first value is not less than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not less than the second value.
   */
  static void failIfNotLessThan(double first, double second) {
    if (Double.compare(first, second) >= 0) fail(errorMessageIfNotLessThan(first, second));
  }

  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(char first, char second) {
    return concat(valueOf(first), " should be less than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(byte first, byte second) {
    return concat(valueOf(first), " should be less than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(short first, short second) {
    return concat(valueOf(first), " should be less than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(int first, int second) {
    return concat(valueOf(first), " should be less than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(long first, long second) {
    return concat(valueOf(first), " should be less than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(float first, float second) {
    return concat(valueOf(first), " should be less than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not less than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not less than the second value. 
   */
  static String errorMessageIfNotLessThan(double first, double second) {
    return concat(valueOf(first), " should be less than ", valueOf(second));
  }
  
  /**
   * Fails if the first value is not greater than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(char first, char second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(byte first, byte second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(short first, short second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(int first, int second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(long first, long second) {
    if (first <= second) fail(errorMessageIfNotGreaterThan(first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(float first, float second) {
    if (Float.compare(first, second) <= 0) fail(errorMessageIfNotGreaterThan(first, second));
  }

  /**
   * Fails if the first value is not greater than the second value.
   * @param first the first value.
   * @param second the second value.
   * @throws AssertionError if the first value is not greater than the second value.
   */
  static void failIfNotGreaterThan(double first, double second) {
    if (Double.compare(first, second) <= 0) fail(errorMessageIfNotGreaterThan(first, second));
  }

  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(char first, char second) {
    return concat(valueOf(first), " should be greater than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(byte first, byte second) {
    return concat(valueOf(first), " should be greater than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(short first, short second) {
    return concat(valueOf(first), " should be greater than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(int first, int second) {
    return concat(valueOf(first), " should be greater than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(long first, long second) {
    return concat(valueOf(first), " should be greater than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(float first, float second) {
    return concat(valueOf(first), " should be greater than ", valueOf(second));
  }
  
  /**
   * Returns an error message to be used when the first value is not greater than the second value. 
   * @param first the first value.
   * @param second the second value.
   * @return an error message to be used when the first value is not greater than the second value. 
   */
  static String errorMessageIfNotGreaterThan(double first, double second) {
    return concat(valueOf(first), " should be greater than ", valueOf(second));
  }
  
  private PrimitiveFail() {}
}
