/*
 * Created on Aug 16, 2007
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
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>boolean</code> arrays. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(boolean[])}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class BooleanArrayAssert extends GroupAssert<boolean[]> {

  BooleanArrayAssert(boolean... actual) {
    super(actual);
  }

  /**
   * Verifies that the actual <code>boolean</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code>.
   */
  @Override public BooleanArrayAssert isNotNull() {
    return (BooleanArrayAssert)super.isNotNull();
  }
  
  /**
   * Verifies that the actual <code>boolean</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actual.length > 0) fail(concat("expecting empty array, but was <", Arrays.toString(actual), ">"));
  }

  /**
   * Verifies that the actual <code>boolean</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is empty.
   */
  public BooleanArrayAssert isNotEmpty() {
    if (actual.length == 0) fail("expecting non-empty array");
    return this;
  }

  /**
   * Verifies that the actual <code>boolean</code> array is equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(boolean[], boolean[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is not equal to the given one.
   */
  @Override public BooleanArrayAssert isEqualTo(boolean[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(Arrays.toString(expected), Arrays.toString(actual)));
    return this;
  }

  /**
   * Verifies that the actual <code>boolean</code> array is not equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(boolean[], boolean[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is equal to the given one.
   */
  @Override public BooleanArrayAssert isNotEqualTo(boolean[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(Arrays.toString(actual), Arrays.toString(array)));
    return this;
  }

  int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>boolean</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>boolean</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>boolean</code> array is not equal to the given 
   * one.
   */
  @Override public BooleanArrayAssert hasSize(int expected) {
    return (BooleanArrayAssert)super.hasSize(expected);
  }
  
  /**
   * Verifies that the actual <code>boolean</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is not the same as the given one.
   */
  @Override public BooleanArrayAssert isSameAs(boolean[] expected) {
    return (BooleanArrayAssert)super.isSameAs(expected);
  }

  /**
   * Verifies that the actual <code>boolean</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is the same as the given one.
   */
  @Override public BooleanArrayAssert isNotSameAs(boolean[] expected) {
    return (BooleanArrayAssert)super.isNotSameAs(expected);
  }
}
