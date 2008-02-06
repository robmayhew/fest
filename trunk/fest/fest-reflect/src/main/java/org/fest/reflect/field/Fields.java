/*
 * Created on Feb 5, 2008
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

import static org.fest.reflect.util.Accessibles.*;
import static org.fest.util.Strings.*;

import java.lang.reflect.Field;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
final class Fields {

  /**
   * Looks up a field matching the given name, in the class hierarchy of the given type.
   * @param fieldName the name of the field to look for.
   * @param type the type to start the lookup from.
   * @return the found field.
   * @throws ReflectionError if a matching field cannot be found.
   */
  static Field lookupInClassHierarchy(String fieldName, Class<?> type) {
    java.lang.reflect.Field field = null;
    Class<?> target = type;
    while (target != null) {
      field = field(fieldName, target);
      if (field != null) break;
      target = target.getSuperclass();
    }
    if (field == null) 
      throw new ReflectionError(concat("Unable to find field ", quote(fieldName), " in class ", type.getName()));
    return field;
  }

  private static Field field(String fieldName, Class<?> targetType) {
    try {
      return targetType.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      return null;
    }
  }

  /**
   * Verifies that the given field belongs to the given type.
   * @param field the field to verify.
   * @param expected the expected type.
   * @throws ReflectionError if the given field does not belong to the given type.
   */
  static void assertIsInstanceOf(Field field, Class<?> expected) {
    boolean accessible = field.isAccessible();
    try {
      setAccessible(field, true);
      Class<?> fieldType = field.getType();
      if (!expected.isAssignableFrom(fieldType)) {
        throw new ReflectionError(concat(
            "The field ", quote(field.getName()), " should be of type <", expected.getName(), "> but was <",
            fieldType.getName(), ">"));
      }
    } finally {
      setAccessibleIgnoringExceptions(field, accessible);
    }
  }
  
  private Fields() {
  }

}
