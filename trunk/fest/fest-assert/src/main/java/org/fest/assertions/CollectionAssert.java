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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fest.util.Collections;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Formatting.bracketAround;
import static org.fest.assertions.Formatting.format;
import static org.fest.util.Collections.duplicatesFrom;
import static org.fest.util.Strings.concat;

/**
 * Understands assertions for collections. To create a new instance of this class use the 
 * method <code>{@link Assertions#assertThat(Collection)}</code>.
 *
 * @author Yvonne Wang
 */
public final class CollectionAssert extends GroupAssert<Collection<?>> {

  CollectionAssert(Collection<?> actual) {
    super(actual);
  }

  /**
   * Verifies that the actual collection contains the given objects.
   * @param objects the objects to look for.
   * @return this assertion object.
   * @throws AssertionError if the actual collection does not contain the given objects.
   */
  public CollectionAssert contains(Object...objects) {
    List<Object> notFound = new ArrayList<Object>();
    for (Object o : objects) if (!actual.contains(o)) notFound.add(o);
    if (!notFound.isEmpty()) 
      fail(concat("collection ", actual, " does not contain element(s) ", bracketAround(notFound.toArray())));
    return this;
  }
  
  /**
   * Verifies that the actual collection does not have duplicates.
   * @return this assertion object.
   * @throws AssertionError if the actual collection has duplicates.
   */
  public CollectionAssert doesNotHaveDuplicates() {
    Collection<?> duplicates = duplicatesFrom(actual);
    if (!duplicates.isEmpty()) 
      fail(concat(format(description()), "collection ", actual, " contains duplicates (", duplicates, ")"));
    return this;
  }

  /** {@inheritDoc} */
  public CollectionAssert as(String description) {
    return (CollectionAssert)description(description);
  }

  /** {@inheritDoc} */
  public CollectionAssert describedAs(String description) {
    return as(description);
  }
  
  /**
   * Verifies that the actual collection satisfies the given condition. 
   * @param condition the condition to satisfy.
   * @return this assertion object.
   * @throws AssertionError if the actual collection does not satisfy the given condition.
   */
  public CollectionAssert satisfies(Condition<Collection<?>> condition) {
    return (CollectionAssert)verify(condition);
  }

  /**
   * Verifies that the actual collection is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   */
  public CollectionAssert isNotNull() {
    if (actual == null) fail(concat(format(description()), "the collection is null"));
    return this;
  }
  
  /**
   * Verifies that the actual collection is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual collection is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (!Collections.isEmpty(actual)) 
      fail(concat(format(description()), "expecting empty collection, but was ", actual));
  }
  
  /**
   * Verifies that the actual collection contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is empty.
   */
  public CollectionAssert isNotEmpty() {
    if (actual == null) fail(concat(format(description()), "expecting non-null collection"));
    if (actual.isEmpty()) fail(concat(format(description()), "expecting non-empty collection"));
    return this;
  }
  
  /**
   * Verifies that the number of elements in the actual collection is equal to the given one.
   * @param expected the expected number of elements in the actual collection.
   * @return this assertion object.
   * @throws AssertionError if the number of elements of the actual collection is not equal to the given one.
   */
  public CollectionAssert hasSize(int expected) {
    return (CollectionAssert)assertEqualSize(expected);
  }

  protected int actualGroupSize() {
    return actual.size();
  }

  /**
   * Verifies that the actual collection is equal to the given one.
   * @param expected the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is not equal to the given one.
   */
  public CollectionAssert isEqualTo(Collection<?> expected) {
    return (CollectionAssert)assertEqualTo(expected);
  }

  /**
   * Verifies that the actual collection is not equal to the given one.
   * @param other the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is equal to the given one.
   */
  public CollectionAssert isNotEqualTo(Collection<?> other) {
    return (CollectionAssert)assertNotEqualTo(other);
  }
  
  /**
   * Verifies that the actual collection is the same as the given one.
   * @param expected the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is not the same as the given one.
   */
  public CollectionAssert isSameAs(Collection<?> expected) {
    return (CollectionAssert)assertSameAs(expected);
  }

  /**
   * Verifies that the actual collection is not the same as the given one.
   * @param other the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is the same as the given one.
   */
  public CollectionAssert isNotSameAs(Collection<?> other) {
    return (CollectionAssert)assertNotSameAs(other);
  }
}
