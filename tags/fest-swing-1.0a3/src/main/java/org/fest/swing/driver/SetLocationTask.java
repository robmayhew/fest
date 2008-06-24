/*
 * Created on Feb 23, 2008
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
package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Point;

/**
 * Understands setting the location of a <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 */
class SetLocationTask implements Runnable {

  private final Component component;
  private final Point location;

  /**
   * Creates a new </code>{@link SetLocationTask}</code>.
   * @param component the given <code>Component</code>.
   * @param location the location to set.
   */
  SetLocationTask(Component component, Point location) {
    this.component = component;
    this.location = location;
  }

  /** Sets the location of this task's <code>{@link Component}</code> */
  public void run() {
    component.setLocation(location);
  }
}
