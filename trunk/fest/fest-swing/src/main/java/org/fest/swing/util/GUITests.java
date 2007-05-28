/*
 * Created on May 27, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.util;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.fest.swing.GUITest;

/**
 * Understands utility methods related to GUI tests. A GUI test is a class or method annotated with
 * <code>{@link GUITest}</code>.
 * 
 * @author Alex Ruiz
 */
public final class GUITests {

  /**
   * Returns <code>true</code> if the given class and/or method are annotated with <code>{@link GUITest}</code>. This 
   * method also searches in superclasses and overriden methods.
   * @param type the class to check.
   * @param method the method to check.
   * @return <code>true</code> if the given class and/or method are annotated with <code>{@link GUITest}</code>.
   */
  public static boolean isGUITest(Class<?> type, Method method) {
    if (isGUITest(type)) return true;
    if (isGUITest(method)) return true;
    return (isSuperclassGUITest(type, method));
  }

  private static boolean isSuperclassGUITest(Class<?> type, Method method) {
    Class<?> superclass = type.getSuperclass();
    while (superclass != null) {
      if (isGUITest(superclass)) return true;
      Method overriden = method(superclass, method.getName(), method.getParameterTypes());
      if (overriden != null && isGUITest(overriden)) return true; 
      superclass = superclass.getSuperclass();
    }
    return false;
  }
  
  private static Method method(Class<?> superclass, String methodName, Class<?>[] parameterTypes) {
    try {
      return superclass.getDeclaredMethod(methodName, parameterTypes);
    } catch (Exception e) {
      return null;
    }
  }
  
  private static boolean isGUITest(AnnotatedElement annotatedElement) {
    GUITest annotation = annotatedElement.getAnnotation(GUITest.class);
    return annotation != null;
  }

  private GUITests() {}
}
