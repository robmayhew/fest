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
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(names).<strong>as</strong>(&quot;Jedi Knights&quot;).contains(&quot;Yoda&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert as(String description) {
    return (ObjectArrayAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(names).<strong>describedAs</strong>(&quot;Jedi Knights&quot;).contains(&quot;Yoda&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectArrayAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that all the elements in the actual <code>Object</code> array belong to the specified type. Matching
   * includes subclasses of the given type.
   * <p>
   * For example, consider the following code listing:
   * <pre>
   * Number[] numbers = { 2, 6 ,8 };
   * assertThat(numbers).hasComponentType(Integer.class);
   * </pre>
   * The assertion <code>hasAllElementsOfType</code> will be successful.
   * </p>
   * @param type the expected type.
   * @return this assertion object.
   * @throws AssertionError if the component type of the actual <code>Object</code> array is not the same as the
   *          specified one.
   */
  public ObjectArrayAssert hasAllElementsOfType(Class<?> type) {
    isNotNull();
    for (Object o : actual)
      if (!type.isInstance(o))
        fail(concat("not all elements in array:", actualInBrackets(), " belong to the type:", inBrackets(type)));
    return this;
  }

  /**
   * Verifies that at least one element in the actual <code>Object</code> array belong to the specified type. Matching
   * includes subclasses of the given type.
   * @param type the expected type.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> does not have any elements of the given type.
   */
  public ObjectArrayAssert hasAtLeastOneElementOfType(Class<?> type) {
    isNotNull();
    boolean found = false;
    for (Object o : actual) {
      if (!type.isInstance(o)) continue;
      found = true;
      break;
    }
    if (!found) fail(concat("array:", actualInBrackets(), " does not have any elements of type:", inBrackets(type)));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array contains the given objects.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array does not contain the given objects.
   */
  public ObjectArrayAssert contains(Object...objects) {
    isNotNull();
    List<Object> notFound = new ArrayList<Object>();
    for (Object o : objects) if (!hasElement(o)) notFound.add(o);
    if (!notFound.isEmpty()) failIfElementsNotFound(notFound);
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array contains the given objects <strong>only</strong>.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array does not contain the given objects, or if the
   *          actual <code>Object</code> array contains elements other than the ones specified.
   */
  public ObjectArrayAssert containsOnly(Object...objects) {
    isNotNull();
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
      fail(concat("unexpected element(s):", inBrackets(copy.toArray()), " in array:", actualInBrackets()));
    return this;
  }

  private void failIfElementsNotFound(List<Object> notFound) {
    fail(concat("array:", actualInBrackets(), " does not contain element(s):", inBrackets(notFound.toArray())));
  }

  /**
   * Verifies that the actual <code>Object</code> array does not contain the given objects.
   * @param objects the objects the array should exclude.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array contains any of the given objects.
   */
  public ObjectArrayAssert excludes(Object...objects) {
    isNotNull();
    List<Object> found = new ArrayList<Object>();
    for (Object o : objects) if (hasElement(o)) found.add(o);
    if (!found.isEmpty())
      fail(concat("array:", actualInBrackets(), " does not exclude element(s):", inBrackets(found.toArray())));
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
    if (actual == null) fail("expecting a non-null array, but it was null");
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> array is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (actualGroupSize() > 0)
      fail(concat("expecting empty array, but was:", actualInBrackets()));
  }

  /**
   * Verifies that the actual <code>Object</code> array contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the actual <code>Object</code> array is empty.
   */
  public ObjectArrayAssert isNotEmpty() {
    if (actualGroupSize() == 0) fail("expecting a non-empty array, but it was empty");
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
      fail(errorMessageIfNotEqual(actual, expected));
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
      fail(errorMessageIfEqual(actual, array));
    return this;
  }

  /**
   * Verifies that the number of elements in the actual <code>Object</code> array is equal to the given one.
   * @param expected the expected number of elements in the actual <code>Object</code> array.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> array is <code>null</code>.
   * @throws AssertionError if the number of elements in the actual <code>Object</code> array is not equal to the given
   * one.
   */
  public ObjectArrayAssert hasSize(int expected) {
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
