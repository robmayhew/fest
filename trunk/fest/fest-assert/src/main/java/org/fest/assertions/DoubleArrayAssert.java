/*
 * Created on Jan 15, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.Fail.*;
import static org.fest.assertions.Formatting.*;
import static org.fest.util.Strings.concat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Understands assertion methods for <code>double</code> arrays. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(double[])}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class DoubleArrayAssert extends GroupAssert<double[]> {

  DoubleArrayAssert(double... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleArrayAssert as(String description) {
    return (DoubleArrayAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in 
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public DoubleArrayAssert describedAs(String description) {
    return as(description);
  }
  
  /**
   * Verifies that the actual <code>double</code> array contains the given values.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array does not contain the given values.
   */
  public DoubleArrayAssert contains(double...values) {
    List<Object> notFound = new ArrayList<Object>();
    for (double value : values) if (!hasElement(value)) notFound.add(value);
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);      
    return this;
  }
  
  /**
   * Verifies that the actual <code>double</code> array contains the given values <strong>only</strong>.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array does not contain the given objects, or if the
   *           actual <code>double</code> array contains elements other than the ones specified.
   */
  public DoubleArrayAssert containsOnly(double...values) {
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
      fail(concat("unexpected element(s) ", inBrackets(copy.toArray()), " in array ", inBrackets(actual)));
    return this;
  }

	private List<Object> list(double[] values) {
	  List<Object> list = new ArrayList<Object>();
	  for (double value : values) list.add(value);
	  return list;
	}
  
  private void failIfElementsNotFound(List<Object> notFound) {
    fail(concat("array ", inBrackets(actual), " does not contain element(s) ", inBrackets(notFound.toArray())));
  }

  /**
   * Verifies that the actual <code>double</code> array does not contain the given values.
   * @param values the values the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array contains any of the given values.
   */
  public DoubleArrayAssert excludes(double...values) {
    List<Object> found = new ArrayList<Object>();
    for (double value : values) if (hasElement(value)) found.add(value);
    if (!found.isEmpty())
      fail(concat("array ", inBrackets(actual), " does not exclude element(s) ", inBrackets(found.toArray())));      
    return this;
  }

  private boolean hasElement(double value) {
    for (double actualElement : actual)
      if (Double.doubleToLongBits(value) == Double.doubleToLongBits(actualElement)) return true;
    return false;
  }
  
  /**
   * Verifies that the actual <code>double</code> array satisfies the given condition. 
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array does not satisfy the given condition.
   */
  public DoubleArrayAssert satisfies(Condition<double[]> condition) {
    return (DoubleArrayAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>double</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code>.
   */
  public DoubleArrayAssert isNotNull() {
    return (DoubleArrayAssert)assertNotNull();
  }
  
  /**
   * Verifies that the actual <code>double</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>double</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actual.length > 0) 
      fail(concat(format(description()), "expecting empty array, but was ", inBrackets(actual)));
  }

  /**
   * Verifies that the actual <code>double</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is empty.
   */
  public DoubleArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail(concat(format(description()), "expecting a non-empty array"));
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array is equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(double[], double[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is not equal to the given one.
   */
  public DoubleArrayAssert isEqualTo(double[] expected) {
    if (!Arrays.equals(actual, expected)) 
      fail(errorMessageIfNotEqual(description(), actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>double</code> array is not equal to the given array. Array equality is checked by 
   * <code>{@link Arrays#equals(double[], double[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is equal to the given one.
   */
  public DoubleArrayAssert isNotEqualTo(double[] array) {
    if (Arrays.equals(actual, array)) 
      fail(errorMessageIfEqual(description(), actual, array));
    return this;
  }

  protected int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>double</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>double</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>double</code> array is not equal to the given 
   * one.
   */
  public DoubleArrayAssert hasSize(int expected) {
    return (DoubleArrayAssert)assertEqualSize(expected);
  }
  
  /**
   * Verifies that the actual <code>double</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is not the same as the given one.
   */
  public DoubleArrayAssert isSameAs(double[] expected) {
    return (DoubleArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>double</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>double</code> array is the same as the given one.
   */
  public DoubleArrayAssert isNotSameAs(double[] expected) {
    return (DoubleArrayAssert)assertNotSameAs(expected);
  }
}
