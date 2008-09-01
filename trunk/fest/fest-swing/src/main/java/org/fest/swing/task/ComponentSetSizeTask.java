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
package org.fest.swing.task;

import java.awt.Component;
import java.awt.Dimension;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that sets the size of a <code>{@link Component}</code>. This task should be executed in the
 * event dispatch thread.
 *
 * @author Alex Ruiz
 */
public class ComponentSetSizeTask extends GuiTask {

  private final Component c;
  private final Dimension size;

  /**
   * Creates a new <code>{@link ComponentSetSizeTask}</code>
   * @param c the <code>Component</code> to resize.
   * @param size the new size for the given <code>Component</code>.
   * @return the created task.
   */
  public static ComponentSetSizeTask setSizeTask(Component c, Dimension size) {
    return new ComponentSetSizeTask(c, size);
  }

  private ComponentSetSizeTask(Component c, Dimension size) {
    this.c = c;
    this.size = size;
  }

  /**
   * Sets this task's <code>{@link Dimension}</code> as the new size of this task's <code>{@link Component}</code>. This
   * action is executed in the event dispatch thread.
   */
  protected void executeInEDT() {
    c.setSize(size);
  }
}
