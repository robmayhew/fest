/*
 * Created on Apr 29, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Understands utility methods for Collections.
 *
 * @author Yvonne Wang
 */
public final class Collections {
  
  public static <T> Collection<T> duplicatesFrom(Collection<T> c) {
    Set<T> duplicates = new HashSet<T>();
    if (isEmpty(c)) return duplicates;
    Set<T> onlyOne = new HashSet<T>();
    for(T e : c) {
      if (onlyOne.contains(e)) {
        duplicates.add(e);
        continue;
      }
      onlyOne.add(e);
    }
    return duplicates;
  }

  public static boolean isEmpty(Collection<?> c) {
    return c == null || c.isEmpty();
  }
  
  private Collections() {}
}
