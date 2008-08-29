/*
 * Created on Aug 29, 2008
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

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that sets the <code>{@link JPopupMenu}</code> for a <code>{@link JComponent}</code>.
 *
 * @author Alex Ruiz 
 */
public class ComponentSetPopupMenuTask extends GuiTask {
  
  private final JComponent component;
  private final JPopupMenu popupMenu;

  public static void setPopupMenu(JComponent component, JPopupMenu popupMenu) {
    execute(new ComponentSetPopupMenuTask(component, popupMenu));
  }
  
  private ComponentSetPopupMenuTask(JComponent component, JPopupMenu popupMenu) {
    this.component = component;
    this.popupMenu = popupMenu;
  }

  protected void executeInEDT() {
    component.setComponentPopupMenu(popupMenu);
  }
}