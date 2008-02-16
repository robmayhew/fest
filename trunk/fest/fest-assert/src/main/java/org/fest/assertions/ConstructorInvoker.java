/*
 * Created on Feb 15, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.assertions;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Understands access to constructors using Java reflection.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class ConstructorInvoker {

  Object newInstance(String className, Class<?>[] parameterTypes, Object[] parameterValues) throws Exception {
    Class<?> targetType = Class.forName(className);
    Constructor<?> constructor = targetType.getConstructor(parameterTypes);
    boolean accessible = constructor.isAccessible();
    try {
      setAccessible(constructor, true);
      return constructor.newInstance(parameterValues);
    } finally {
      try {
        setAccessible(constructor, accessible);
      } catch (RuntimeException e) {}
    }
  }

  private void setAccessible(final AccessibleObject accessible, final boolean value) {
    AccessController.doPrivileged(new PrivilegedAction<Void>() {
      public Void run() {
        accessible.setAccessible(value);
        return null;
      }
    });
  }
}
