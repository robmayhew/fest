/*
 * Created on Jan 12, 2008
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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.Point;

/**
 * Understands utility methods related to visible <code>{@link Component}</code>-relative locations.
 * <p>
 * Adapted from <code>abbot.tester.ComponentLocation</code> from 
 * <a href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 * @author Alex Ruiz
 */
public final class Locations {
  
  /**
   * Returns a point relative to the given <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   * @return a point relative to the given <code>Component</code>.
   */
  public static Point pointAt(Component c) {
    return new Point(c.getWidth() / 2, c.getHeight() / 2);
  }

  private Locations() {}
}
