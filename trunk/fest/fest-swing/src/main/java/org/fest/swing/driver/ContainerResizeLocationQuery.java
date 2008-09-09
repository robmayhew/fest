/*
 * Created on Aug 7, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the point where the mouse usually grabs to
 * resize a <code>{@link Container}</code>
 * 
 * @author Alex Ruiz
 */
class ContainerResizeLocationQuery extends GuiQuery<Point> {

  private final Container container;

  static Point resizeLocationOf(Container container) {
    return execute(new ContainerResizeLocationQuery(container));
  }
  
  ContainerResizeLocationQuery(Container container) {
    this.container = container;
  }
  
  protected Point executeInEDT() {
    return resizeLocation(container.getSize(), container.getInsets());
  }

  private Point resizeLocation(Dimension size, Insets insets) {
    return new Point(size.width - insets.right / 2, size.height - insets.bottom / 2);
  }
}
