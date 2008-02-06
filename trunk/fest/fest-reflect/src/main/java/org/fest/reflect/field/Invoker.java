/*
 * Created on Oct 31, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.reflect.field;

import java.lang.reflect.Field;

import org.fest.reflect.exception.ReflectionError;

import static org.fest.reflect.util.Accessibles.*;
import static org.fest.util.Strings.*;

/**
 * Understands the use of reflection to access a field from an object.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Retrieves the value of the field "name"
 *   String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link Name#ofType(Class) ofType}(String.class).{@link Type#in(Object) in}(person).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the field "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link Name#ofType(Class) ofType}(String.class).{@link Type#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 *   
 *   // Retrieves the value of the static field "count"
 *   int count = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticName#ofType(Class) ofType}(int.class).{@link StaticType#in(Class) in}(Person.class).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the static field "count" to 3
 *   {@link org.fest.reflect.core.Reflection#staticField(String) field}("count").{@link StaticName#ofType(Class) ofType}(int.class).{@link StaticType#in(Class) in}(Person.class).{@link Invoker#set(Object) set}(3);
 * </pre>
 * </p>
 *
 * @param <T> the declared type for the field to access.
 * 
 * @author Alex Ruiz
 */
public final class Invoker<T> {

  private final Field field;
  private final Object target;

  Invoker(Field field, Object target) {
    this.field = field;
    this.target = target;
  }

  /**
   * Sets a value in the field managed by this class.
   * @param value the value to set.
   * @throws ReflectionError if the given value cannot be set.
   */
  public void set(T value) {
    boolean accessible = field.isAccessible();
    try {
      makeAccessible(field);
      field.set(target, value);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to update the value in field ", quote(field.getName())), e);
    } finally {
      setAccessibleIgnoringExceptions(field, accessible);
    }
  }

  /**
   * Returns the value of the field managed by this class.
   * @return the value of the field managed by this class.
   * @throws ReflectionError if the value of the field cannot be retrieved.
   */
  @SuppressWarnings("unchecked") public T get() {
    boolean accessible = field.isAccessible();
    try {
      makeAccessible(field);
      return (T) field.get(target);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to obtain the value in field " + quote(field.getName())), e);
    } finally {
      setAccessibleIgnoringExceptions(field, accessible);
    }
  }
 
  /**
   * Returns the "real" field managed by this class.
   * @return the "real" field managed by this class.
   */
  public java.lang.reflect.Field info() {
    return field;
  }
}
