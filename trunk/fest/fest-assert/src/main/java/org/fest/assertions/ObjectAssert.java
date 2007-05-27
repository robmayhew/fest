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
import static org.fest.util.Objects.namesOf;
import static org.fest.util.Strings.concat;

/**
 * Understands assertion methods for objects.
 *
 * @author Yvonne Wang
 */
public final class ObjectAssert extends Assert<Object> {

  ObjectAssert(Object actual) {
    super(actual);
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
  
  @Override public ObjectAssert isNotNull() {
    return (ObjectAssert)super.isNotNull();
  }
  
  @Override public ObjectAssert isSameAs(Object expected) {
    return (ObjectAssert)super.isSameAs(expected);
  }
  
  @Override public ObjectAssert isNotSameAs(Object other) {
    return (ObjectAssert)super.isNotSameAs(other);
  }

  @Override public ObjectAssert isEqualTo(Object expected) {
    return (ObjectAssert)super.isEqualTo(expected);
  }

  @Override public ObjectAssert isNotEqualTo(Object other) {
    return (ObjectAssert)super.isNotEqualTo(other);
  }
}
