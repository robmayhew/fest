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

import java.awt.Rectangle;

import javax.swing.JComponent;

import org.fest.swing.core.GuiTask;

/**
 * Understands a request to scroll a <code>{@link JComponent}</code>'s parent to a visible
 * <code>{@link Rectangle}</code>. This task should be executed in the event dispatch thread.
 *
 * @author Alex Ruiz
 */
class JComponentScrollRectToVisibleTask extends GuiTask {

  private final JComponent component;
  private final Rectangle visibleRectangle;

  static JComponentScrollRectToVisibleTask scrollRectToVisibleTask(JComponent component, Rectangle visibleRectangle) {
    return new JComponentScrollRectToVisibleTask(component, visibleRectangle);
  }
  
  private JComponentScrollRectToVisibleTask(JComponent component, Rectangle visibleRectangle) {
    this.component = component;
    this.visibleRectangle = visibleRectangle;
  }

  protected void executeInEDT() {
    component.scrollRectToVisible(visibleRectangle);
  }
}
