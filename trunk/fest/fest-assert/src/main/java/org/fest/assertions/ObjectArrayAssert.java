/*
 * Created on Mar 3, 2007
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

import static org.fest.assertions.Fail.*;
import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Collections.list;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Understands assertions for <code>Object</code> arrays.  To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(Object[])}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ObjectArrayAssert extends GroupAssert<Object[]> {

  ObjectArrayAssert(Object... actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert as(String description) {
    return (ObjectArrayAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>Object</code> array contains the given objects.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array does not contain the given objects.
   */
  public ObjectArrayAssert contains(Object...objects) {
    List<Object> notFound = new ArrayList<Object>();
    for (Object o : objects) if (!hasElement(o)) notFound.add(o);
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array contains the given objects <strong>only</strong>.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array does not contain the given objects, or if the
   *           actual <code>Object</code> array contains elements other than the ones specified.
   */
  public ObjectArrayAssert containsOnly(Object...objects) {
    List<Object> notFound = new ArrayList<Object>();
    List<Object> copy = new ArrayList<Object>(list(actual));
    for (Object o : objects) {
      if (!copy.contains(o)) {
        notFound.add(o);
        continue;
      }
      copy.remove(o);
    }
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);
    if (!copy.isEmpty())
      fail(concat("unexpected element(s) ", inBrackets(copy.toArray()), " in array ", inBrackets(actual)));
    return this;
  }

  private void failIfElementsNotFound(List<Object> notFound) {
    fail(concat("array ", inBrackets(actual), " does not contain element(s) ", inBrackets(notFound.toArray())));
  }

  /**
   * Verifies that the actual <code>Object</code> array does not contain the given objects.
   * @param objects the objects the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array contains any of the given objects.
   */
  public ObjectArrayAssert excludes(Object...objects) {
    List<Object> found = new ArrayList<Object>();
    for (Object o : objects) if (hasElement(o)) found.add(o);
    if (!found.isEmpty())
      fail(concat("array ", inBrackets(actual), " does not exclude element(s) ", inBrackets(found.toArray())));
    return this;
  }

  private boolean hasElement(Object o) {
    for (Object actualElement : actual)
      if (areEqual(o, actualElement)) return true;
    return false;
  }

  /**
   * Verifies that the actual <code>Object</code> array satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array does not satisfy the given condition.
   */
  public ObjectArrayAssert satisfies(Condition<Object[]> condition) {
    return (ObjectArrayAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>Object</code> array is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   */
  public ObjectArrayAssert isNotNull() {
    return (ObjectArrayAssert)assertNotNull();
  }

  /**
   * Verifies that the actual <code>Object</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actualGroupSize() > 0)
      fail(concat("expecting empty array, but was ", inBrackets(actual)));
  }

  /**
   * Verifies that the actual <code>Object</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is empty.
   */
  public ObjectArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail("expecting a non-empty array");
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array is equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(Object[], Object[])}</code>.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is not equal to the given one.
   */
  public ObjectArrayAssert isEqualTo(Object[] expected) {
    if (!Arrays.equals(actual, expected))
      fail(errorMessageIfNotEqual(description(), actual, expected));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array is not equal to the given array. Array equality is checked by
   * <code>{@link Arrays#equals(Object[], Object[])}</code>.
   * @param array the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is equal to the given one.
   */
  public ObjectArrayAssert isNotEqualTo(Object[] array) {
    if (Arrays.equals(actual, array))
      fail(errorMessageIfEqual(description(), actual, array));
    return this;
  }

  int actualGroupSize() {
    return actual.length;
  }

  /**
   * Verifies that the number of elements in the actual <code>Object</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>Object</code> array.
   * @return this assertion object.
   * @throws AssertionError if the number of elements in the actual <code>Object</code> array is not equal to the given
   * one.
   */
  public ObjectArrayAssert hasSize(int expected) {
    return (ObjectArrayAssert)assertEqualSize(expected);
  }

  /**
   * Verifies that the actual <code>Object</code> array is the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is not the same as the given one.
   */
  public ObjectArrayAssert isSameAs(Object[] expected) {
    return (ObjectArrayAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>Object</code> array is not the same as the given array.
   * @param expected the given array to compare the actual array to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is the same as the given one.
   */
  public ObjectArrayAssert isNotSameAs(Object[] expected) {
    return (ObjectArrayAssert)assertNotSameAs(expected);
  }
}
