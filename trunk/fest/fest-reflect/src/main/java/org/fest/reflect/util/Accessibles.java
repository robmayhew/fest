/*
 * Created on Jan 1, 2008
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
package org.fest.reflect.util;

import java.lang.reflect.AccessibleObject;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Understands utility methods related to <code>{@link AccessibleObject}</code>s.
 *
 * @author Alex Ruiz
 */
public final class Accessibles {

  /**
   * Sets the <code>accessible</code> flag of the given <code>{@link AccessibleObject}</code> to the given 
   * <code>boolean</code> value, ignoring any thrown exception.
   * @param o the given <code>AccessibleObject</code>,
   * @param accessible the value to set the <code>accessible</code> flag to.
   */
  public static void setAccessibleIgnoringExceptions(final AccessibleObject o, final boolean accessible) {
    try {
      setAccessible(o, accessible);
    } catch (RuntimeException ignored) {}    
  }

  /**
   * Sets the <code>accessible</code> flag of the given <code>{@link AccessibleObject}</code> to the given 
   * <code>boolean</code> value.
   * @param o the given <code>AccessibleObject</code>,
   * @param accessible the value to set the <code>accessible</code> flag to.
   * @throws SecurityException if the request is denied.
   */
  public static void setAccessible(final AccessibleObject o, final boolean accessible) {
    AccessController.doPrivileged(new PrivilegedAction<Void>() {
      public Void run() {
        o.setAccessible(true);
        return null;
      }
    });    
  }
  
  private Accessibles() {}
}
