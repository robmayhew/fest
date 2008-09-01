/*
 * Created on Aug 14, 2008
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
package org.fest.swing.task;


import java.awt.Component;

import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that makes a <code>{@link Component}</code> visible or invisible.
 *
 * @author Alex Ruiz
 */
public class ComponentSetVisibleTask extends GuiTask {

  private final Component c;
  private final boolean visible;

  public static void setVisible(Component c, boolean visible) {
    execute(new ComponentSetVisibleTask(c, visible));
  }

  private ComponentSetVisibleTask(Component c, boolean visible) {
    this.c = c;
    this.visible = visible;
  }

  protected void executeInEDT() {
    c.setVisible(visible);
  }
}