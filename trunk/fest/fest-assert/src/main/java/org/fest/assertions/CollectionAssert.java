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
import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.util.Collections.duplicatesFrom;
import static org.fest.util.Strings.concat;

/**
 * Understands assertions for collections.
 *
 * @author Yvonne Wang
 * @param <T> the parameterized type of the collection.
 */
public final class CollectionAssert<T> {

  private final Collection<T> actual;

  CollectionAssert(Collection<T> actual) {
    this.actual = actual;
  }

  public CollectionAssert doesNotHaveDuplicates() {
    Collection<T> duplicates = duplicatesFrom(actual);
    if (!duplicates.isEmpty()) fail(concat("the collection ", actual, " contains duplicates (", duplicates, ")"));
    return this;
  }

  public void isNull() {
    if (actual != null) fail("the collection is not null");
  }
  
  public CollectionAssert isNotNull() {
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
  
  public CollectionAssert hasSize(int size) {
    if (actual == null) fail("the collection is null");
    failIfNotEqual(actual.size(), size);
    return this;
  }
}
