/*
 * Created on Feb 5, 2006
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
 * Copyright @2006-2008 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.reflect.field.Fields.*;

import java.lang.reflect.Field;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands the type of a static field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Retrieves the value of the static field "count"
 *   int count = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticName#ofType(Class) ofType}(String.class).{@link StaticType#in(Class) in}(Person.class).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the field "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#staticField(String) field}("count").{@link StaticName#ofType(Class) ofType}(String.class).{@link StaticType#in(Class) in}(Person.class).{@link Invoker#set(Object) set}(3);
 * </pre>
 * </p>
 *
 * @param <T> the generic type of the field. 
 *
 * @author Alex Ruiz
 */
public class StaticType<T> {
  private final Class<T> type;
  private final StaticName fieldName;

  StaticType(Class<T> type, StaticName fieldName) {
    this.type = type;
    this.fieldName = fieldName;
  }

  /**
   * Creates a new field invoker.
   * @param target the type containing the static field of interest.
   * @return the created field invoker.
   * @throws ReflectionError if a static field with a matching name and type cannot be found.
   */
  public Invoker<T> in(Class<?> target) {
    Field field = lookupInClassHierarchy(fieldName.name, target);
    assertIsInstanceOf(field, type);
    return new Invoker<T>(field, target);
  }
}