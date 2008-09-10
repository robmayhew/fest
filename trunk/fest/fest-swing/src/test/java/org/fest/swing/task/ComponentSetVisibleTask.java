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

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

/**
 * Understands a task that makes a <code>{@link Component}</code> visible or invisible.
 *
 * @author Alex Ruiz
 */
public final class ComponentSetVisibleTask {

  public static void setVisible(final Component c, final boolean visible) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        c.setVisible(visible);
      }
    }, new Condition(concat("Component's 'visible' property is ", visible)) {
      public boolean test() {
        return c.isVisible() == visible;
      }
    });
  }

  private ComponentSetVisibleTask() {}
}