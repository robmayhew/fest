/*
 * Created on Oct 18, 2007
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

/**
 * Understands utility methods related to arrays.
 * 
 * @author Alex Ruiz
 */
public final class Arrays {

  public static int objectsOfType(Object[] objects, Class<?> type) {
    int count = 0;
    for (Object o : objects) {
      if (o == null) continue;
      if (type.isAssignableFrom(o.getClass())) count++;
    }
    return count;
  }

  private Arrays() {}
}
