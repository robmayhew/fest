/*
 * Created on Dec 1, 2007
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
package org.fest.swing.remote.util;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * Understands utility methods related to casting.
 *
 * @author Yvonne Wang
 */
public final class Castings {

  /**
   * Casts the given object to the given type, throwing a <code>{@link ClassCastException}</code> specifying why the 
   * casting could not be done.
   * @param <T> the generic type of the class to cast the given object to.
   * @param o the object to cast.
   * @param type the class to cast the given object to.
   * @return the given object casted to the given type.
   * @throws ClassCastException if the object is not <code>null</code> and is not assignable to the type <code>T</code>.
   */
  public static <T> T cast(Object o, Class<T> type) {
    try {
      return type.cast(o);
    } catch (ClassCastException e) {
      String message = concat("Cannot cast object of type ", quote(o.getClass().getName()), " to ", quote(type.getName()));
      throw new ClassCastException(message);
    }
  }
  
  private Castings() {}
}
