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

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that request input focus for a <code>{@link Component}</code>. This task is executed in the event
 * dispatch thread.
 * 
 * @author Alex Ruiz
 */
public final class ComponentRequestFocusTask {
  
  /**
   * Requests that the given <code>{@link Component}</code> get the input focus. This action is executed in the event
   * dispatch thread.
   * @param c the given <code>Component</code>.
   */
  public static void giveFocusTo(final Component c) {
    execute(new GuiQuery<Void>() {
      protected Void executeInEDT() {
        c.requestFocusInWindow();
        return null;
      }
    });
  }
  
  private ComponentRequestFocusTask() {}
}
