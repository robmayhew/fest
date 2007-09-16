/*
 * Created on May 21, 2007
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

import static org.fest.assertions.Fail.failIfEqual;
import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.assertions.Fail.failIfNotNull;
import static org.fest.assertions.Fail.failIfNotSame;
import static org.fest.assertions.Fail.failIfNull;
import static org.fest.assertions.Fail.failIfSame;

/**
 * Understands a template for assertion methods.
 * @param <T> the type of object implementations of this template can verify. 
 *
 * @author Yvonne Wang
 */
abstract class Assert<T> {

  final T actual;
  private String description;

  /**
   * Creates a new </code>{@link Assert}</code>.
   * @param actual the actual target to verify.
   */
  Assert(T actual) {
    this.actual = actual;
  }

  /**
   * Asserts that the actual value (specified in the constructor of this class) is <code>null</code>.
   * @throws AssertionError if the actual value is not <code>null</code>.
   */
  public final void isNull() {
    failIfNotNull(description, actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public abstract Assert<T> as(String description);

  /**
   * Verifies that the actual value is equal to the given one.
   * @param expected the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is not equal to the given one.
   */
  public abstract Assert<T> isEqualTo(T expected);

  /**
   * Verifies that the actual value is not equal to the given one.
   * @param other the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is equal to the given one.
   */
  public abstract Assert<T> isNotEqualTo(T other);
  
  /**
   * Verifies that the actual value is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual value is <code>null</code>.
   */
  public abstract Assert<T> isNotNull();

  /**
   * Verifies that the actual value is the same as the given one.
   * @param expected the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is not the same as the given one.
   */
  public abstract Assert<T> isSameAs(T expected);

  /**
   * Verifies that the actual value is not the same as the given one.
   * @param other the given value to compare the actual value to.
   * @return this assertion object.
   * @throws AssertionError if the actual value is the same as the given one.
   */
  public abstract Assert<T> isNotSameAs(T other);

  /**
   * Returns the description of the actual value in this assertion.
   * @return the description of the actual value in this assertion.
   */
  public final String description() {
    return description;
  }
  
  protected Assert<T> description(String description) {
    this.description = description;
    return this;
  }

  protected final Assert<T> assertEqualTo(T expected) {
    failIfNotEqual(description, actual, expected);
    return this;
  }

  protected final Assert<T> assertNotEqualTo(T obj) {
    failIfEqual(description, actual, obj);
    return this;
  }

  protected final Assert<T> assertNotNull() {
    failIfNull(description, actual);
    return this;
  }

  protected final Assert<T> assertSameAs(T expected) {
    failIfNotSame(description, actual, expected);
    return this;
  }

  protected final Assert<T> assertNotSameAs(T expected) {
    failIfSame(description, actual, expected);
    return this;
  }
}
