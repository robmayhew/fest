/*
 * Created on Jan 23, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Arrays.format;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Understands assertions for <code>{@link Map}</code>. To create a new instance of this class use the method
 * <code>{@link Assertions#assertThat(Map)}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class MapAssert extends GroupAssert<Map<?, ?>> {

  MapAssert(Map<?, ?> actual) {
    super(actual);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public MapAssert as(String description) {
    return (MapAssert)description(description);
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> contains the given value associated with the given key.
   * <p>
   * Example:
   * <pre>
   * // static import org.fest.assertions.Assertions.*;
   * // static import org.fest.assertions.MapAssert.*;
   *
   * assertThat(myMap).contains(key(&quot;name&quot;), value(&quot;Frodo&quot;));
   * </pre>
   * </p>
   * @param key specifies the given key.
   * @param value specifies the value expected to be associated with the given key.
   * @return this assertion error.
   * @throws AssertionError if the actual <code>Map</code> does not contain the given key, or if the actual
   *          <code>Map</code> does not contain the given value under the given key.
   */
  public MapAssert contains(Key key, Value value) {
    isNotNull();
    keySetIncludes(key.value);
    Object actualValue = actual.get(key.value);
    if (!areEqual(actualValue, value.value)) {
      fail(concat(
          "expecting value:", inBrackets(value.value), " under key:", inBrackets(key.value),
          " but was:", inBrackets(actualValue)));
    }
    return this;
  }

  /**
   * Creates a new <code>{@link Key}</code>.
   * @param value the value of the key.
   * @return the created <code>Key</code>.
   */
  public static Key key(Object value) {
    return new Key(value);
  }

  /**
   * Understands a value expected to be used as a key in a <code>{@link Map}</code>.
   *
   * @author Yvonne Wang
   */
  public static class Key {
    final Object value;

    Key(Object value) {
      this.value = value;
    }
  }

  /**
   * Creates a new <code>{@link Value}</code>.
   * @param value the value of the entry value.
   * @return the created <code>Value</code>.
   */
  public static Value value(Object value) {
    return new Value(value);
  }

  /**
   * Understands a value expected to be the stored as a <code>{@link Map}</code> entry.
   *
   * @author Yvonne Wang
   */
  public static class Value {
    final Object value;

    Value(Object value) {
      this.value = value;
    }
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> contains the given keys.
   * @param keys the keys to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> does not contain all the given keys.
   */
  public MapAssert keySetIncludes(Object... keys) {
    isNotNull();
    Set<?> keySet = actual.keySet();
    List<Object> notFound = new ArrayList<Object>();
    for (Object key : keys) if (!keySet.contains(key)) notFound.add(key);
    if (!notFound.isEmpty())
      fail(concat("the map ", actual, " does not contain the key(s) ", format(notFound.toArray())));
    return this;
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> contains the given values.
   * @param values the values to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> does not contain all the given values.
   */
  public MapAssert valuesInclude(Object... values) {
    isNotNull();
    List<Object> notFound = new ArrayList<Object>();
    List<Object> actualValues = actualValues();
    for (Object expected : values) if (!actualValues.contains(expected)) notFound.add(expected);
    if (!notFound.isEmpty())
      fail(concat("the map ", actual, " does not contain the value(s) ", format(notFound.toArray())));
    return this;
  }

  private List<Object> actualValues() {
    List<Object> actualValues = new ArrayList<Object>();
    for (Map.Entry<?, ?> entry : actual.entrySet())
      actualValues.add(entry.getValue());
    return actualValues;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public MapAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the number of elements in the actual <code>{@link Map}</code> is equal to the given one.
   * @param expected the expected number of elements in the actual <code>Map</code>.
   * @return this assertion object.
   * @throws AssertionError if the number of elements of the actual <code>Map</code> is not equal to the given one.
   */
  public MapAssert hasSize(int expected) {
    return (MapAssert)assertEqualSize(expected);
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> is empty.
   * @throws AssertionError if the actual <code>Map</code> is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if ((actual != null) && !actual.isEmpty()) {
      fail(concat(format(description()), "expecting empty map, but was ", actual));
    }
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> is equal to the given one.
   *
   * @param expected the given map to compare the actual <code>Map</code> to.
   *
   * @return this assertion object.
   *
   * @throws AssertionError if the actual <code>Map</code> is not equal to the given one.
   */
  public MapAssert isEqualTo(Map<?, ?> expected) {
    return (MapAssert)assertEqualTo(expected);
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> is empty.
   */
  public MapAssert isNotEmpty() {
    isNotNull();
    if (actual.isEmpty()) fail(concat(format(description()), "expecting non-empty map"));
    return this;
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> is not equal to the given one.
   * @param other the given map to compare the actual <code>Map</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> is equal to the given one.
   */
  public MapAssert isNotEqualTo(Map<?, ?> other) {
    return (MapAssert)assertNotEqualTo(other);
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> is <code>null</code>.
   */
  public MapAssert isNotNull() {
    if (actual == null) {
      fail(concat(format(description()), "the map is null"));
    }

    return this;
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> is not the same as the given one.
   * @param other the given map to compare the actual <code>Map</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> is the same as the given one.
   */
  public MapAssert isNotSameAs(Map<?, ?> other) {
    return (MapAssert)assertNotSameAs(other);
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> is the same as the given one.
   * @param expected the given map to compare the actual <code>Map</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> is not the same as the given one.
   */
  public MapAssert isSameAs(Map<?, ?> expected) {
    return (MapAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Map</code> does not satisfy the given condition.
   */
  public MapAssert satisfies(Condition<Map<?, ?>> condition) {
    return (MapAssert)verify(condition);
  }

  protected int actualGroupSize() {
    isNotNull();
    return actual.size();
  }
}