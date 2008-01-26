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

import static org.fest.util.Maps.format;
import static org.fest.util.Strings.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fest.util.Collections;

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
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(cache).<strong>as</strong>(&quot;Cached Results&quot;).keySetIncludes(&quot;430-094&quot;, &quot;5094-8&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public MapAssert as(String description) {
    return (MapAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(cache).<strong>describedAs</strong>(&quot;Cached Results&quot;).keySetIncludes(&quot;430-094&quot;, &quot;5094-8&quot;);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public MapAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>{@link Map}</code> contains the given entries.
   * <p>
   * Example:
   * <pre>
   * // static import org.fest.assertions.Assertions.*;
   * // static import org.fest.assertions.MapAssert.*;
   *
   * assertThat(myMap).{@link #contains(org.fest.assertions.MapAssert.Entry...) contains}({@link #entry(Object, Object) entry}(&quot;jedi&quot;, yoda), {@link #entry(Object, Object) entry}(&quot;sith&quot;, anakin));
   * </pre>
   * </p>
   * @param entries
   * @return this assertion error.
   * @throws AssertionError if the actual <code>Map</code> does not contain any of the given entries.
   */
  public MapAssert contains(Entry...entries) {
    isNotNull();
    List<Entry> notFound = new ArrayList<Entry>();
    for (Entry e : entries) if (!containsEntry(e)) notFound.add(e);
    failIfNotFound("entr(y/ies)", notFound);
    return this;
  }

  private boolean containsEntry(Entry e) {
    if (!actual.containsKey(e.key)) return false;
    return actual.containsValue(e.value);
  }

  /**
   * Creates a new map entry.
   * @param key the key of the entry.
   * @param value the value of the entry.
   * @return the created entry.
   * @see #contains(org.fest.assertions.MapAssert.Entry...)
   */
  public static Entry entry(Object key, Object value) {
    return new Entry(key, value);
  }

  /**
   * Understands an entry in a <code>{@link Map}</code>.
   *
   * @author Yvonne Wang
   */
  public static class Entry {
    final Object key;
    final Object value;

    Entry(Object key, Object value) {
      this.key = key;
      this.value = value;
    }

    /** @see java.lang.Object#toString() */
    @Override public String toString() {
      return concat(quote(key), "=", quote(value));
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
    failIfNotFound("keys(s)", notFound);
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
    for (Object expected : values) if (!actual.containsValue(expected)) notFound.add(expected);
    failIfNotFound("value(s)", notFound);
    return this;
  }

  private void failIfNotFound(String description, List<?> notFound) {
    if (notFound.isEmpty()) return;
    fail(concat("the map ", formattedActual(), " does not contain the ", description, " ", Collections.format(notFound)));
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
      fail(concat("expecting empty map, but was ", formattedActual()));
    }
  }

  private String formattedActual() {
    return format(actual);
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
    if (actual.isEmpty()) fail("expecting non-empty map");
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
    if (actual == null) fail("the map is null");
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

  int actualGroupSize() {
    isNotNull();
    return actual.size();
  }
}