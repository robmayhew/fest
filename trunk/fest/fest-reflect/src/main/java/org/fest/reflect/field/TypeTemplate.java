/*
 * Created on Feb 6, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.reflect.field;

import java.lang.reflect.Field;

import org.fest.reflect.exception.ReflectionError;

import static org.fest.reflect.util.Accessibles.*;
import static org.fest.util.Strings.*;

/**
 * Understands a template for the type of a field to access using Java Reflection.
 *
 * @author Alex Ruiz
 */
abstract class TypeTemplate<T> {

  private final Class<T> type;
  private final String name;

  TypeTemplate(Class<T> type, NameTemplate fieldName) {
    name = fieldName.name;
    if (type == null) throw new ReflectionError("The type of the field to access should not be null");
    this.type = type;
  }

  final Invoker<T> fieldInvoker(Object target, Class<?> declaringType) {
    Field field = lookupInClassHierarchy(declaringType);
    verifyCorrectType(field);
    return new Invoker<T>(field, target);
  }

  private Field lookupInClassHierarchy(Class<?> declaringType) {
    java.lang.reflect.Field field = null;
    Class<?> target = declaringType;
    while (target != null) {
      field = field(target);
      if (field != null) break;
      target = target.getSuperclass();
    }
    if (field != null) return field; 
    throw new ReflectionError(concat("Unable to find field ", quote(name), " in class ", declaringType.getName()));
  }

  private Field field(Class<?> declaringType) {
    try {
      return declaringType.getDeclaredField(name);
    } catch (NoSuchFieldException e) {
      return null;
    }
  }

  private void verifyCorrectType(Field field) {
    boolean accessible = field.isAccessible();
    try {
      makeAccessible(field);
      Class<?> fieldType = field.getType();
      if (!type.isAssignableFrom(fieldType)) {
        throw new ReflectionError(concat(
            "The type of the field ", quote(field.getName()), " in class ", field.getDeclaringClass().getName(),
            " should be <", type.getName(), "> but was <",
            fieldType.getName(), ">"));
      }
    } finally {
      setAccessibleIgnoringExceptions(field, accessible);
    }
  }
}
