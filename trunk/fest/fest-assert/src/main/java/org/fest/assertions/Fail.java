/*
 * Created on Mar 19, 2007
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

import static org.fest.assertions.ComparisonFailureFactory.comparisonFailure;
import static org.fest.assertions.Formatting.*;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

/**
 * Understands failure methods.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Fail {

  /**
   * Fails with no message.
   * @throws AssertionError without any message.
   */
  public static void fail() {
    fail(null);
  }

  /**
   * Throws an {@link AssertionError} with the given message an with the <code>{@link Throwable}</code> that caused the 
   * failure.
   * @param message error message.
   * @param realCause cause of the error.
   */
  public static void fail(String message, Throwable realCause) {
    AssertionError error = new AssertionError(message);
    error.initCause(realCause);
    throw error;
  }

  /**
   * Fails if the given objects are equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given objects are equal.
   */
  static void failIfEqual(String message, Object first, Object second) {
    if (areEqual(first, second)) fail(errorMessageIfEqual(message, first, second));
  }
  
  /**
   * Fails if the given objects are not equal.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given objects are not equal.
   */
  static void failIfNotEqual(String message, Object actual, Object expected) {
    if (areEqual(actual, expected)) return;
    AssertionError comparisonFailure = comparisonFailure(message, expected, actual);
    if (comparisonFailure != null) throw comparisonFailure;
    fail(errorMessageIfNotEqual(message, actual, expected));
  }
  
  /**
   * Fails if the given object is <code>null</code>.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param o the object to check.
   * @throws AssertionError if the given object is <code>null</code>.
   */
  static void failIfNull(String message, Object o) {
    if (o == null) fail(concat(format(message), "expecting a non-null object"));
  }
  
  /**
   * Fails if the given object is not <code>null</code>.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param o the object to check.
   * @throws AssertionError if the given object is not <code>null</code>.
   */
  static void failIfNotNull(String message, Object o) {
    if (o != null) fail(concat(format(message), inBrackets(o), " should be null"));
  }

  /**
   * Fails if the given objects are the same instance.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given objects are the same instance.
   */
  static void failIfSame(String message, Object first, Object second) {
    if (first == second) fail(concat(format(message), "given objects are same:", inBrackets(first)));
  }
  
  /**
   * Fails if the given objects are not the same instance.
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value to check against <code>second</code>.
   * @param second the second value.
   * @throws AssertionError if the given objects are not the same instance.
   */
  static void failIfNotSame(String message, Object first, Object second) {
    if (first != second) 
      fail(concat(format(message), "expected same instance but found ", inBrackets(first), " and ", inBrackets(second)));
  }
  
  /**
   * Fails with the given message.
   * @param message error message.
   * @throws AssertionError with the given message.
   */
  public static void fail(String message) {
    throw new AssertionError(message);
  }

  /**
   * Returns an error message to be used when two objects that are expected to be equal aren't. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two objects that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(String message, Object actual, Object expected) {
    return concat(format(message), "expected:", inBrackets(expected), " but was:", inBrackets(actual));
  }
  
  /**
   * Returns an error message to be used when two objects that are not expected to be equal are. 
   * @param message the identifying message or <code>null</code> for the <code>AssertionError</code>.
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two objects that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(String message, Object first, Object second) {
    return concat(format(message), inBrackets(first), " should not be equal to ", inBrackets(second));
  }
  
  private Fail() {}
}
