/*
 * Created on Sep 16, 2007
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
package org.fest.swing.util;

import static org.fest.util.Strings.*;

import java.awt.*;

/**
 * Understands utility methods related to formatting.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Formatting {

  private static final String NULL_COMPONENT_MESSAGE = "Null Component";
  
  /**
   * Returns a <code>String</code> representation of the given <code>{@link Component}</code>. 
   * @param c the given <code>Component</code>.
   * @return a <code>String</code> representation of the given <code>Component</code>.
   */
  public static String format(Component c) {
    if (c == null) return NULL_COMPONENT_MESSAGE;
    String name = c.getName();
    if (isEmpty(name)) return c.toString();
    return concat(c.getClass().getName(), "<", quote(name), ">");
  }
  
  private Formatting() {}
}
