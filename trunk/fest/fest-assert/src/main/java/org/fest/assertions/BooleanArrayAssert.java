/*
 * Created on Feb 14, 2008
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
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Strings.concat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(values).<strong>as</strong>(&quot;Some values&quot;).isNotEmpty();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BooleanArrayAssert as(String description) {
    return (BooleanArrayAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(values).<strong>describedAs</strong>(&quot;Some values&quot;).isNotEmpty();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public BooleanArrayAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>boolean</code> array contains the given values.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array does not contain the given values.
   */
  public BooleanArrayAssert contains(boolean...values) {
    isNotNull();
    List<Object> notFound = new ArrayList<Object>();
    for (boolean value : values) if (!hasElement(value)) notFound.add(value);
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);
    return this;
  }

  /**
   * Verifies that the actual <code>boolean</code> array contains the given values <strong>only</strong>.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>boolean</code> array does not contain the given objects, or if the
   *          actual <code>boolean</code> array contains elements other than the ones specified.
   */
  public BooleanArrayAssert containsOnly(boolean...values) {
    isNotNull();
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
      fail(concat("unexpected element(s):", inBrackets(copy.toArray()), " in array:", actualInBrackets()));
    return this;
  }

	private List<Object> list(boolean[] values) {
	  List<Object> list = new ArrayList<Object>();
	  for (boolean value : values) list.add(value);
	  return list;
	}

  private void failIfElementsNotFound(List<Object> notFound) {
    fail(concat("array:", actualInBrackets(), " does not contain element(s):", inBrackets(notFound.toArray())));
  }

  /**
   * Verifies that the actual <code>boolean</code> array does not contain the given values.
   * @param values the values the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array contains any of the given values.
   */
  public BooleanArrayAssert excludes(boolean...values) {
    isNotNull();
    List<Object> found = new ArrayList<Object>();
    for (boolean value : values) if (hasElement(value)) found.add(value);
    if (!found.isEmpty())
      fail(concat("array:", actualInBrackets(), " does not exclude element(s):", inBrackets(found.toArray())));
    return this;
  }

  private boolean hasElement(boolean value) {
    for (boolean actualElement : actual)
      if (value == actualElement) return true;
    return false;
  }

  /**
   * Verifies that the actual <code>boolean</code> array satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public BooleanArrayAssert satisfies(Condition<boolean[]> condition) {
    return (BooleanArrayAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>boolean</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code>.
   */
  public BooleanArrayAssert isNotNull() {
    if (actual == null) fail("expecting a non-null array, but it was null");
    return this;
  }

  /**
   * Verifies that the actual <code>boolean</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actualGroupSize() > 0)
      fail(concat("expecting empty array, but was:", actualInBrackets()));
  }

  /**
   * Verifies that the actual <code>boolean</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>boolean</code> array is empty.
   */
  public BooleanArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail("expecting a non-empty array, but it was empty");
    return this;
  }

  /**
   * Verifies that the actual <code>boolean</code> array is equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(boolean[], boolean[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is not equal to the given one.
   */
  public BooleanArrayAssert isEqualTo(boolean[] expected) {
    if (!Arrays.equals(actual, expected))
      fail(errorMessageIfNotEqual(actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>boolean</code> array is not equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(boolean[], boolean[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is equal to the given one.
   */
  public BooleanArrayAssert isNotEqualTo(boolean[] array) {
    if (Arrays.equals(actual, array))
      fail(errorMessageIfEqual(actual, array));
    return this;
  }

  /**
   * Verifies that the number of elements in the actual <code>boolean</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>boolean</code> array.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is <code>null</code>.
   * @throws AssertionError if the number of elements in the actual <code>boolean</code> array is not equal to the given
   *          one.
   */
  public BooleanArrayAssert hasSize(int expected) {
    int actualSize = actualGroupSize();
    if (actualSize != expected)
      fail(concat(
          "expected size:", inBrackets(expected)," but was:", inBrackets(actualSize), " for array:", actualInBrackets()));
    return this;
  }

  int actualGroupSize() {
    isNotNull();
    return actual.length;
  }

  private String actualInBrackets() {
    return inBrackets(actual);
  }

  /**
   * Verifies that the actual <code>boolean</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is not the same as the given one.
   */
  public BooleanArrayAssert isSameAs(boolean[] expected) {
    return (BooleanArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>boolean</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>boolean</code> array is the same as the given one.
   */
  public BooleanArrayAssert isNotSameAs(boolean[] expected) {
    return (BooleanArrayAssert)assertNotSameAs(expected);
  }
}
