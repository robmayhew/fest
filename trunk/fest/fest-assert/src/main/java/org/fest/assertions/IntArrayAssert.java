/*
 * Created on Dec 22, 2007
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Fail.errorMessageIfEqual;
import static org.fest.assertions.Fail.errorMessageIfNotEqual;
import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Formatting.bracketAround;
import static org.fest.assertions.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for <code>int</code> arrays. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(int[])}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class IntArrayAssert extends GroupAssert<int[]> {

  IntArrayAssert(int... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public IntArrayAssert as(String description) {
    return (IntArrayAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in 
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public IntArrayAssert describedAs(String description) {
    return as(description);
  }
  
  /**
   * Verifies that the actual <code>int</code> array contains the given values.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array does not contain the given values.
   */
  public IntArrayAssert contains(int...values) {
    List<Object> notFound = new ArrayList<Object>();
    for (int value : values) if (!hasElement(value)) notFound.add(value);
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);      
    return this;
  }
  
  /**
   * Verifies that the actual <code>int</code> array contains the given values <strong>only</strong>.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array does not contain the given objects, or if the
   *           actual <code>int</code> array contains elements other than the ones specified.
   */
  public IntArrayAssert containsOnly(int...values) {
    List<Object> notFound = new ArrayList<Object>();
    List<Object> copy = list(actual);
    for (Object value : list(values)) {
      if (!copy.contains(value)) {
        notFound.add(value);
        continue;
      }
      copy.remove(value);
    }
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);
    if (!copy.isEmpty()) 
      fail(concat("unexpected element(s) ", bracketAround(copy.toArray()), " in array ", bracketAround(actual)));
    return this;
  }

	private List<Object> list(int[] values) {
	  List<Object> list = new ArrayList<Object>();
	  for (int value : values) list.add(value);
	  return list;
	}
  
  private void failIfElementsNotFound(List<Object> notFound) {
    fail(concat("array ", bracketAround(actual), " does not contain element(s) ", bracketAround(notFound.toArray())));
  }

  /**
   * Verifies that the actual <code>int</code> array does not contain the given values.
   * @param values the values the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array contains any of the given values.
   */
  public IntArrayAssert excludes(int...values) {
    List<Object> found = new ArrayList<Object>();
    for (int value : values) if (hasElement(value)) found.add(value);
    if (!found.isEmpty())
      fail(concat("array ", bracketAround(actual), " does not exclude element(s) ", bracketAround(found.toArray())));      
    return this;
  }

  private boolean hasElement(int value) {
    for (int actualElement : actual)
      if (value == actualElement) return true;
    return false;
  }
  
  /**
   * Verifies that the actual <code>int</code> array satisfies the given condition. 
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array does not satisfy the given condition.
   */
  public IntArrayAssert satisfies(Condition<int[]> condition) {
    return (IntArrayAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>int</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array is <code>null</code>.
   */
  public IntArrayAssert isNotNull() {
    return (IntArrayAssert)assertNotNull();
  }
  
  /**
   * Verifies that the actual <code>int</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>int</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actual.length > 0) 
      fail(concat(format(description()), "expecting empty array, but was ", bracketAround(actual)));
  }

  /**
   * Verifies that the actual <code>int</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array is empty.
   */
  public IntArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail(concat(format(description()), "expecting a non-empty array"));
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> array is equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(int[], int[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array is not equal to the given one.
   */
  public IntArrayAssert isEqualTo(int[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(description(), actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> array is not equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(int[], int[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array is equal to the given one.
   */
  public IntArrayAssert isNotEqualTo(int[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(description(), actual, array));
    return this;
  }

  protected int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>int</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>int</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>int</code> array is not equal to the given 
   * one.
   */
  public IntArrayAssert hasSize(int expected) {
    return (IntArrayAssert)assertEqualSize(expected);
  }
  
  /**
   * Verifies that the actual <code>int</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array is not the same as the given one.
   */
  public IntArrayAssert isSameAs(int[] expected) {
    return (IntArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>int</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> array is the same as the given one.
   */
  public IntArrayAssert isNotSameAs(int[] expected) {
    return (IntArrayAssert)assertNotSameAs(expected);
  }
}
