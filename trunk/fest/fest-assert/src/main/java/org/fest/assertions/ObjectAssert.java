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

import java.util.Arrays;

import static org.fest.assertions.Fail.fail;
import static org.fest.assertions.Fail.failIfEqual;
import static org.fest.assertions.Fail.failIfNotEqual;
import static org.fest.assertions.Fail.failIfNotNull;
import static org.fest.assertions.Fail.failIfNotSame;
import static org.fest.assertions.Fail.failIfNull;
import static org.fest.util.Objects.namesOf;
import static org.fest.util.Strings.concat;

/**
 * Understands constraints on objects.
 *
 * @author Yvonne Wang
 */
public final class ObjectAssert {

  private final Object actual;

  ObjectAssert(Object actual) {
    this.actual = actual;
  }
  
  public ObjectAssert isInstanceOf(Class<?> type) {
    isNotNull();
    Class<? extends Object> current = actual.getClass();
    if (!type.isAssignableFrom(current))
      fail(concat("expected instance of:<", type.getName(), "> but was instance of:<", current.getName(), ">"));
    return this;
  }
  
  public ObjectAssert isInstanceOfAny(Class<?>...types) {
    isNotNull();
    Class<? extends Object> current = actual.getClass();
    for (Class<?> type : types) if (type.isAssignableFrom(current)) return this;
    fail(concat("expected instance of any:<", Arrays.toString(namesOf(types)), "> but was instance of:<", 
        current.getName(), ">"));   
    return this;
  }
  
  public ObjectAssert isNull() {
    failIfNotNull(actual);
    return this;
  }

  public ObjectAssert isNotNull() {
    failIfNull(actual);
    return this;
  }
  
  public ObjectAssert isSameAs(Object expected) {
    failIfNotSame(actual, expected);
    return this;
  }
  
  public ObjectAssert isEqualTo(Object expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  public ObjectAssert isNotEqualTo(Object obj) {
    failIfEqual(actual, obj);
    return this;
  }
}
