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

import java.util.Collection;

import org.fest.util.Collections;

import static org.fest.assertions.Fail.fail;
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
   * Verifies that the actual collection does not have duplicates.
   * @return this assertion object.
   * @throws AssertionError if the actual collection has duplicates.
   */
  public CollectionAssert doesNotHaveDuplicates() {
    Collection<?> duplicates = duplicatesFrom(actual);
    if (!duplicates.isEmpty()) fail(concat("the collection ", actual, " contains duplicates (", duplicates, ")"));
    return this;
  }

  /**
   * Verifies that the actual collection is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is <code>null</code>.
   */
  @Override public CollectionAssert isNotNull() {
    if (actual == null) fail ("the collection is null");
    return this;
  }
  
  /**
   * Verifies that the actual collection is empty (not <code>null</code> with zero elements.)
   * @throws AssertionError if the actual collection is <code>null</code> or not empty.
   */
  public void isEmpty() {
    if (!Collections.isEmpty(actual)) fail(concat("the collection ", actual, " is not empty"));
  }
  
  /**
   * Verifies that the actual collection contains at least on element.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is empty.
   */
  public CollectionAssert isNotEmpty() {
    if (Collections.isEmpty(actual)) fail("the collection is null or empty");
    return this;
  }
  
  /**
   * Verifies that the number of elements in the actual collection is equal to the given one.
   * @param expected the expected number of elements in the actual collection.
   * @return this assertion object.
   * @throws AssertionError if the number of elements of the actual collection is not equal to the given one.
   */
  @Override public CollectionAssert hasSize(int expected) {
    return (CollectionAssert)super.hasSize(expected);
  }

  int actualGroupSize() {
    return actual.size();
  }

  /**
   * Verifies that the actual collection is equal to the given one.
   * @param expected the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is not equal to the given one.
   */
  @Override public CollectionAssert isEqualTo(Collection<?> expected) {
    return (CollectionAssert)super.isEqualTo(expected);
  }

  /**
   * Verifies that the actual collection is not equal to the given one.
   * @param other the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is equal to the given one.
   */
  @Override public CollectionAssert isNotEqualTo(Collection<?> other) {
    return (CollectionAssert)super.isNotEqualTo(other);
  }
  
  /**
   * Verifies that the actual collection is the same as the given one.
   * @param expected the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is not the same as the given one.
   */
  @Override public CollectionAssert isSameAs(Collection<?> expected) {
    return (CollectionAssert)super.isSameAs(expected);
  }

  /**
   * Verifies that the actual collection is not the same as the given one.
   * @param other the given collection to compare the actual collection to.
   * @return this assertion object.
   * @throws AssertionError if the actual collection is the same as the given one.
   */
  @Override public CollectionAssert isNotSameAs(Collection<?> other) {
    return (CollectionAssert)super.isNotSameAs(other);
  }
}
