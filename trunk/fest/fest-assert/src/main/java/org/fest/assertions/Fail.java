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

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

/**
 * Understands failure methods.
 *
 * @author Alex Ruiz
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
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given objects are equal.
   */
  static void failIfEqual(Object first, Object second) {
    if (areEqual(first, second)) fail(errorMessageIfEqual(first, second));
  }
  
  /**
   * Fails if the given objects are not equal.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given objects are not equal.
   */
  static void failIfNotEqual(Object actual, Object expected) {
    if (!areEqual(actual, expected)) fail(errorMessageIfNotEqual(actual, expected));
  }
  
  /**
   * Fails if the given object is <code>null</code>.
   * @param o the object to check.
   * @throws AssertionError if the given object is <code>null</code>.
   */
  static void failIfNull(Object o) {
    if (o == null) fail("the given object should not be null");
  }
  
  /**
   * Fails if the given object is not <code>null</code>.
   * @param o the object to check.
   * @throws AssertionError if the given object is not <code>null</code>.
   */
  static void failIfNotNull(Object o) {
    if (o != null) fail(concat("<", o, "> should not be null"));
  }

  /**
   * Fails if the given objects are the same instance.
   * @param first the value to check against <code>second</code>.
   * @param second second value.
   * @throws AssertionError if the given objects are the same instance.
   */
  static void failIfSame(Object first, Object second) {
    if (first == second) fail(concat("same objects:<", first, ">"));
  }
  
  /**
   * Fails if the given objects are not the same instance.
   * @param actual the value to check against <code>expected</code>.
   * @param expected expected value.
   * @throws AssertionError if the given objects are not the same instance.
   */
  static void failIfNotSame(Object actual, Object expected) {
    if (actual != expected) fail(concat("expected same with:<", expected, "> but was:<", actual, ">"));
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
   * @param actual the value checked against <code>expected</code>.
   * @param expected expected value.
   * @return an error message to be used when two objects that are expected to be equal aren't. 
   */
  static String errorMessageIfNotEqual(Object actual, Object expected) {
    return concat("expected:<", expected, "> but was:<", actual, ">");
  }
  
  /**
   * Returns an error message to be used when two objects that are not expected to be equal are. 
   * @param first the value checked against <code>second</code>.
   * @param second second value.
   * @return an error message to be used when two objects that are not expected to be equal are. 
   */
  static String errorMessageIfEqual(Object first, Object second) {
    return concat("<", first, "> should not be equal to <", second, ">");
  }
  
  private Fail() {}
}
