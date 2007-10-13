/*
 * Created on Oct 12, 2007
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

import java.awt.Container;
import java.awt.Insets;

/**
 * Understands AWT-related utility methods.
 * <p>
 * Adapted from <code>abbot.util.AWT</code> from <a href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 */
public final class AWT {

  /**
   * Returns the insets of the given container, or an empty one if no insets can be found.
   * @param c the given container.
   * @return the insets of the given container, or an empty one if no insets can be found.
   */
  public static Insets insetsFrom(Container c) {
    try {
      Insets insets = c.getInsets();
      if (insets != null) return insets;
    } catch (Exception e) {}
    return new Insets(0, 0, 0, 0);
  }

  private AWT() {}
}
