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

import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.util.Strings.concat;

import org.fest.util.Strings;

/**
 * Understands a template for assertion methods related to arrays or collections.
 * @param <T> the type of object implementations of this template can verify.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class GroupAssert<T> extends GenericAssert<T> {

  private static final String SIZE = "size";

  /**
   * Creates a new <code>{@link GroupAssert}</code>.
   * @param actual the object to verify.
   */
  GroupAssert(T actual) {
    super(actual);
  }

  /**
   * Verifies that the actual group of values is <code>null</code> or empty.
   * @throws AssertionError if the actual group of values is not <code>null</code> or not empty.
   */
  protected abstract void isEmpty();

  /**
   * Verifies that the actual group of values contains at least on value.
   * @return this assertion object.
   * @throws AssertionError if the actual group of values is <code>null</code> or empty.
   */
  protected abstract GroupAssert<T> isNotEmpty();

  /**
   * Verifies that the number of values in the actual group is equal to the given one.
   * @param expected the expected number of values in the actual group.
   * @return this assertion object.
   * @throws AssertionError if the number of values of the actual group is not equal to the given one.
   */
  protected abstract GroupAssert<T> hasSize(int expected);

  /**
   * Returns an <code>{@link IntAssert}</code> for verification of the size of the actual group.
   * @return assertion methods for verification of the size of the actual group.
   */
  public final IntAssert size() {
    return new IntAssert(actualGroupSize());
  }

  protected final GroupAssert<T> assertEqualSize(int expected) {
    if (actual == null) fail("cannot get size of a null object");
    int actualSize = actualGroupSize();
    if (actualSize != expected)
      fail(errorMessageIfNotEqual(descriptionForSize(), String.valueOf(actualSize), String.valueOf(expected)));
    return this;
  }

  private String descriptionForSize() {
    String description = description();
    if (Strings.isEmpty(description)) return SIZE;
    return concat(description, " - ", SIZE);
  }

  protected abstract int actualGroupSize();
}
