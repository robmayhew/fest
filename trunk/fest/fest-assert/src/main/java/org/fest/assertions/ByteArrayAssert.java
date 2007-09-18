/*
 * Created on Sep 17, 2007
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
 * Understands assertion methods for <code>byte</code> arrays. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(byte[])}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ByteArrayAssert extends GroupAssert<byte[]> {

  ByteArrayAssert(byte... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual <code>byte</code> array, to be used in as message of any
   * <code>{@link AssertionError}</code> thrown when an assertion fails.
   * @param description the description of the actual <code>byte</code> array.
   * @return this assertion object.
   */
  public ByteArrayAssert as(String description) {
    return (ByteArrayAssert)description(description);
  }

  /**
   * Verifies that the actual <code>byte</code> array satisfies the given condition. 
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> array does not satisfy the given condition.
   */
  public ByteArrayAssert satisfies(Condition<byte[]> condition) {
    return (ByteArrayAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>byte</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> array is <code>null</code>.
   */
  public ByteArrayAssert isNotNull() {
    return (ByteArrayAssert)assertNotNull();
  }
  
  /**
   * Verifies that the actual <code>byte</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>byte</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actual.length > 0) 
      fail(concat(format(description()), "expecting empty array, but was ", bracketAround(actual)));
  }

  /**
   * Verifies that the actual <code>byte</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> array is empty.
   */
  public ByteArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail(concat(format(description()), "expecting a non-empty array"));
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> array is equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(byte[], byte[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> array is not equal to the given one.
   */
  public ByteArrayAssert isEqualTo(byte[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(description(), expected, actual));
    return this;
  }

  /**
   * Verifies that the actual <code>byte</code> array is not equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(byte[], byte[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> array is equal to the given one.
   */
  public ByteArrayAssert isNotEqualTo(byte[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(description(), actual, array));
    return this;
  }

  protected int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>byte</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>byte</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>byte</code> array is not equal to the given 
   * one.
   */
  public ByteArrayAssert hasSize(int expected) {
    return (ByteArrayAssert)assertEqualSize(expected);
  }
  
  /**
   * Verifies that the actual <code>byte</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> array is not the same as the given one.
   */
  public ByteArrayAssert isSameAs(byte[] expected) {
    return (ByteArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>byte</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>byte</code> array is the same as the given one.
   */
  public ByteArrayAssert isNotSameAs(byte[] expected) {
    return (ByteArrayAssert)assertNotSameAs(expected);
  }
}
