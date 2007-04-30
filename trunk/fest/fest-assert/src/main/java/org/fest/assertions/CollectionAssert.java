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
    if (!duplicates.isEmpty()) fail(concat("The collection ", actual, " contains duplicates (", duplicates, ")"));
    return this;
  }

  public CollectionAssert isEmpty() {
    if (!Collections.isEmpty(actual)) fail(concat("The collection ", actual, " is not empty"));
    return this;
  }
}
