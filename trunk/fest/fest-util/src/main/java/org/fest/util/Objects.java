/*
 * Created on Jun 2, 2006
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
package org.fest.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Understands utility methods.
 * 
 * @author Alex Ruiz
 */
public final class Objects {

  public static <T> boolean isEmpty(T[] array) {
    return array == null || array.length == 0;
  }

  public static <T> List<T> asList(T... elements) {
    return new ArrayList<T>(Arrays.asList(elements));
  }
  
  /**
   * Returns <code>true</code> if the given objects are equal or if both objects are <code>null</code>.
   * 
   * @param o1 one of the objects to compare.
   * @param o2 one of the objects to compare.
   * @return <code>true</code> if the given objects are equal or if both objects are <code>null</code>.
   */
  public static boolean areEqual(Object o1, Object o2) {
    if (o1 == null) return o2 == null;
    return o1.equals(o2);
  }
  
  public static String[] namesOf(Class<?>... types) {
    if (isEmpty(types)) return new String[0];
    String[] names = new String[types.length];
    for (int i = 0; i < types.length; i++) names[i] = types[i].getName();
    return names;
  }
  
  public static <T> T[] array(T... values) { return values; }

  private Objects() {}
}
