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
package org.fest.reflect;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands the use of reflection to access a field from an object.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   String name = Reflection.field("name").ofType(String.class).in(person).get();
 *   Reflection.field("name").ofType(String.class).in(person).set("Yoda");
 * </pre>
 * </p>
 * @param <T> the declared type for the field represented by this <code>Field</code> object.
 * 
 * @author Alex Ruiz
 */
public final class Field<T> {

  private final Object target;
  private final java.lang.reflect.Field field;

  Field(String fieldName, Object target) {
    this.target = target;
    Class<?> type = target.getClass();
    this.field = lookupInClassHierarchy(fieldName, type);
  }

  private java.lang.reflect.Field lookupInClassHierarchy(String fieldName, Class<?> targetType) {
    java.lang.reflect.Field field = null;
    Class<?> type = targetType;
    while (type != null) {
      field = field(fieldName, type);
      if (field != null) break;
      type = type.getSuperclass();
    }
    if (field == null) throw new ReflectionError(concat("Unable to find field ", quote(fieldName), " in class ", type));
    return field;
  }

  private static java.lang.reflect.Field field(String fieldName, Class<?> targetType) {
    try {
      java.lang.reflect.Field f = targetType.getDeclaredField(fieldName);
      f.setAccessible(true);
      return f;
    } catch (Exception e) {
      return null;
    }
  }

  void assertIsType(Class<T> expected) {
    Class<?> fieldType = field.getType();
    if (!expected.isAssignableFrom(fieldType))
      throw new ReflectionError(concat("The field ", quote(field.getName()), " should be of type <", expected.getName(),
          "> but was <", fieldType.getName(), ">"));
  }

  public void set(T value) {
    try {
      field.set(target, value);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to update the value in field ", quote(field.getName())), e);
    }
  }

  @SuppressWarnings("unchecked") public T get() {
    try {
      return (T) field.get(target);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to obtain the value in field " + quote(field.getName())), e);
    }
  }

  public static class FieldName {
    private final String value;

    FieldName(String name) {
      this.value = name;
    }

    public <T> FieldType<T> ofType(Class<T> type) {
      return new FieldType<T>(type, this);
    }
  }

  public static class FieldType<T> {
    private final Class<T> value;
    private final FieldName fieldName;

    FieldType(Class<T> type, FieldName fieldName) {
      this.value = type;
      this.fieldName = fieldName;
    }

    public Field<T> in(Object target) {
      Field<T> field = new Field<T>(fieldName.value, target);
      field.assertIsType(value);
      return field;
    }
  }
}
