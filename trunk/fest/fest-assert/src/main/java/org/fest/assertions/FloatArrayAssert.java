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

import java.util.Arrays;

import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Formatting.bracketAround;
import static org.fest.assertions.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>float</code> arrays. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(float[])}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FloatArrayAssert extends GroupAssert<float[]> {

  FloatArrayAssert(float... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual <code>float</code> array, to be used in as message of any
   * <code>{@link AssertionError}</code> thrown when an assertion fails.
   * @param description the description of the actual <code>float</code> array.
   * @return this assertion object.
   */
  public FloatArrayAssert as(String description) {
    return (FloatArrayAssert)description(description);
  }

  /**
   * Verifies that the actual <code>float</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code>.
   */
  public FloatArrayAssert isNotNull() {
    return (FloatArrayAssert)assertNotNull();
  }
  
  /**
   * Verifies that the actual <code>float</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>float</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actual.length > 0) 
      fail(concat(format(description()), "expecting empty array, but was ", bracketAround(actual)));
  }

  /**
   * Verifies that the actual <code>float</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is empty.
   */
  public FloatArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail(concat(format(description()), "expecting a non-empty array"));
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array is equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(float[], float[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is not equal to the given one.
   */
  public FloatArrayAssert isEqualTo(float[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(description(), expected, actual));
    return this;
  }

  /**
   * Verifies that the actual <code>float</code> array is not equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(float[], float[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is equal to the given one.
   */
  public FloatArrayAssert isNotEqualTo(float[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(description(), actual, array));
    return this;
  }

  protected int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>float</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>float</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>float</code> array is not equal to the given 
   * one.
   */
  public FloatArrayAssert hasSize(int expected) {
    return (FloatArrayAssert)assertEqualSize(expected);
  }
  
  /**
   * Verifies that the actual <code>float</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is not the same as the given one.
   */
  public FloatArrayAssert isSameAs(float[] expected) {
    return (FloatArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>float</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>float</code> array is the same as the given one.
   */
  public FloatArrayAssert isNotSameAs(float[] expected) {
    return (FloatArrayAssert)assertNotSameAs(expected);
  }
}
