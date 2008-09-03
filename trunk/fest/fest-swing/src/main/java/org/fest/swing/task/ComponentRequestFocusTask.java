/*
 * Created on Sep 1, 2008
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

/**
 * Understands a task that request input focus for a <code>{@link Component}</code>. This task should be executed in the
 * event dispatch thread.
 *
 * @author Alex Ruiz
 */
public class ComponentRequestFocusTask extends GuiTask {
  
  private final Component c;

  /**
   * Creates a new <code>{@link ComponentRequestFocusTask}</code>
   * @param c the <code>Component</code> to give input focus to.
   * @return the created task.
   */
  public static ComponentRequestFocusTask requestFocusTask(Component c) {
    return new ComponentRequestFocusTask(c);
  }
  
  ComponentRequestFocusTask(Component c) {
    this.c = c;
  }

  /**
   * Requests that this task's <code>{@link Component}</code> get the input focus. This action is executed in the event
   * dispatch thread.
   */
  protected void executeInEDT() {
    c.requestFocusInWindow();
  }
}
