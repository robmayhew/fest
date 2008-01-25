/*
 * Created on Dec 27, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Objects.namesOf;
import static org.fest.util.Strings.concat;

import java.util.Arrays;

/**
 * Understands assertion methods for objects. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(Object)}</code>.
 *
 * @author Yvonne Wang
 */
public final class ObjectAssert extends GenericAssert<Object> {

  ObjectAssert(Object actual) {
    super(actual);
  }

  /**
   * Verifies that the actual <code>Object</code> is an instance of the given type.
   * @param type the type to check the actual <code>Object</code> against.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> is not an instance of the given type.
   */
  public ObjectAssert isInstanceOf(Class<?> type) {
    isNotNull();
    Class<?> current = actual.getClass();
    if (!type.isAssignableFrom(current))
      fail(concat("expected instance of:", inBrackets(type.getName()),
          " but was instance of:", inBrackets(current.getName())));
    return this;
  }

  /**
   * Verifies that the actual <code>Object</code> is an instance of any of the given types.
   * @param types the types to check the actual <code>Object</code> against.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> is not an instance of any of the given types.
   */
  public ObjectAssert isInstanceOfAny(Class<?>...types) {
    isNotNull();
    Class<?> current = actual.getClass();
    for (Class<?> type : types) if (type.isAssignableFrom(current)) return this;
    fail(concat("expected instance of any:",
        inBrackets(Arrays.toString(namesOf(types))), " but was instance of:", inBrackets(current.getName())));
    return this;
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectAssert as(String description) {
    return (ObjectAssert)description(description);
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>.
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public ObjectAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>Object</code> satisfies the given condition.
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> does not satisfy the given condition.
   */
  public ObjectAssert satisfies(Condition<Object> condition) {
    return (ObjectAssert)verify(condition);
  }

  /**
   * Verifies that the actual <code>Object</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> is <code>null</code>.
   */
  public ObjectAssert isNotNull() {
    return (ObjectAssert)assertNotNull();
  }

  /**
   * Verifies that the actual <code>Object</code> is the same as the given one.
   * @param expected the given <code>Object</code> to compare the actual <code>Object</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> is not the same as the given one.
   */
  public ObjectAssert isSameAs(Object expected) {
    return (ObjectAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual <code>Object</code> is not the same as the given one.
   * @param other the given <code>Object</code> to compare the actual <code>Object</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> is the same as the given one.
   */
  public ObjectAssert isNotSameAs(Object other) {
    return (ObjectAssert)assertNotSameAs(other);
  }

  /**
   * Verifies that the actual <code>Object</code> is equal to the given one.
   * @param expected the given <code>Object</code> to compare the actual <code>Object</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> is not equal to the given one.
   */
  public ObjectAssert isEqualTo(Object expected) {
    return (ObjectAssert)assertEqualTo(expected);
  }

  /**
   * Verifies that the actual <code>Object</code> is not equal to the given one.
   * @param other the given <code>Object</code> to compare the actual <code>Object</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>Object</code> is equal to the given one.
   */
  public ObjectAssert isNotEqualTo(Object other) {
    return (ObjectAssert)assertNotEqualTo(other);
  }
}
