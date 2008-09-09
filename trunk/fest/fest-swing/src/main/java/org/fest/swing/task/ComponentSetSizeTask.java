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

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands a task that sets the size of a <code>{@link Component}</code>. This task is executed in the event 
 * dispatch thread.
 *
 * @author Alex Ruiz
 */
public class ComponentSetSizeTask {

  /**
   * Sets the size of a <code>{@link Component}</code>.
   * @param c the <code>Component</code> to set the size to.
   * @param size the new size for the given <code>Component</code>.
   */
  public static void setSize(final Component c, final Dimension size) {
    execute(new GuiQuery<Void>() {
      // we cannot use a GuiTask because we cannot depend on the size of the component after being resized will be the
      // same to the given one (e.g. when resizing a frame, we need to count the size of the title bar.
      // TODO find a way to use a GuiTask
      protected Void executeInEDT() {
        c.setSize(size);
        return null;
      }
    });
  }
}
