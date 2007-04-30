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
import java.util.HashSet;
import java.util.List;

import static org.fest.assertions.Fail.fail;
import static org.fest.util.Strings.*;

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
    if (actual == null || actual.isEmpty()) return this;
    HashSet<T> withoutDuplicates = new HashSet<T>();
    List<T> duplicates = new ArrayList<T>();
    for (T element : actual) if (!withoutDuplicates.add(element)) duplicates.add(element);
    if (!duplicates.isEmpty()) fail(concat("The collection ", actual, " contains duplicates (", duplicates, ")"));
    return this;
  }

  public void isEmpty() {
    if (actual == null || actual.isEmpty()) return;
    fail(concat("The collection ", actual, " is not empty"));
  }
}
