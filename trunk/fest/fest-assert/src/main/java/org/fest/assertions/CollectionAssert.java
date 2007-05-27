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

import static org.fest.assertions.Fail.*;
import static org.fest.util.Collections.duplicatesFrom;
import static org.fest.util.Strings.concat;

/**
 * Understands assertions for collections.
 *
 * @author Yvonne Wang
 * @param <T> the parameterized type of the collection.
 */
public final class CollectionAssert extends GroupAssert<Collection<?>> {

  CollectionAssert(Collection<?> actual) {
    super(actual);
  }

  public CollectionAssert doesNotHaveDuplicates() {
    Collection<?> duplicates = duplicatesFrom(actual);
    if (!duplicates.isEmpty()) fail(concat("the collection ", actual, " contains duplicates (", duplicates, ")"));
    return this;
  }

  @Override public CollectionAssert isNotNull() {
    if (actual == null) fail ("the collection is null");
    return this;
  }
  
  public void isEmpty() {
    if (!Collections.isEmpty(actual)) fail(concat("the collection ", actual, " is not empty"));
  }
  
  public CollectionAssert isNotEmpty() {
    if (Collections.isEmpty(actual)) fail("the collection is null or empty");
    return this;
  }
  
  @Override public CollectionAssert hasSize(int expected) {
    return (CollectionAssert)super.hasSize(expected);
  }

  int actualGroupSize() {
    return actual.size();
  }

  @Override public CollectionAssert isEqualTo(Collection<?> expected) {
    return (CollectionAssert)super.isEqualTo(expected);
  }

  @Override public CollectionAssert isNotEqualTo(Collection<?> obj) {
    return (CollectionAssert)super.isNotEqualTo(obj);
  }
  
  @Override public CollectionAssert isSameAs(Collection<?> expected) {
    return (CollectionAssert)super.isSameAs(expected);
  }

  @Override public CollectionAssert isNotSameAs(Collection<?> expected) {
    return (CollectionAssert)super.isNotSameAs(expected);
  }
}
