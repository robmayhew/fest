/*
 * Created on Sep 18, 2007
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

import java.util.Arrays;

import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Formatting.bracketAround;
import static org.fest.assertions.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>short</code> arrays. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(short[])}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ShortArrayAssert extends GroupAssert<short[]> {

  ShortArrayAssert(short... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual <code>short</code> array, to be used in as message of any
   * <code>{@link AssertionError}</code> thrown when an assertion fails.
   * @param description the description of the actual <code>short</code> array.
   * @return this assertion object.
   */
  public ShortArrayAssert as(String description) {
    return (ShortArrayAssert)description(description);
  }

  /**
   * Verifies that the actual <code>short</code> array satisfies the given condition. 
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> array does not satisfy the given condition.
   */
  public ShortArrayAssert satisfies(Condition<short[]> condition) {
    return (ShortArrayAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>short</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> array is <code>null</code>.
   */
  public ShortArrayAssert isNotNull() {
    return (ShortArrayAssert)assertNotNull();
  }
  
  /**
   * Verifies that the actual <code>short</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>short</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actual.length > 0) 
      fail(concat(format(description()), "expecting empty array, but was ", bracketAround(actual)));
  }

  /**
   * Verifies that the actual <code>short</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> array is empty.
   */
  public ShortArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail(concat(format(description()), "expecting a non-empty array"));
    return this;
  }

  /**
   * Verifies that the actual <code>short</code> array is equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(short[], short[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> array is not equal to the given one.
   */
  public ShortArrayAssert isEqualTo(short[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(description(), actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>short</code> array is not equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(short[], short[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> array is equal to the given one.
   */
  public ShortArrayAssert isNotEqualTo(short[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(description(), actual, array));
    return this;
  }

  protected int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>short</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>short</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>short</code> array is not equal to the given 
   * one.
   */
  public ShortArrayAssert hasSize(int expected) {
    return (ShortArrayAssert)assertEqualSize(expected);
  }
  
  /**
   * Verifies that the actual <code>short</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> array is not the same as the given one.
   */
  public ShortArrayAssert isSameAs(short[] expected) {
    return (ShortArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>short</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>short</code> array is the same as the given one.
   */
  public ShortArrayAssert isNotSameAs(short[] expected) {
    return (ShortArrayAssert)assertNotSameAs(expected);
  }
}
