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
package org.fest.swing.core;

import java.awt.Component;
import java.awt.event.FocusListener;

import org.fest.swing.edt.GuiTask;

/**
 * Understands a task that removes a <code>{@link FocusListener}</code> from a <code>{@link Component}</code>. This task
 * should be called from the event dispatch thread.
 *
 * @author Alex Ruiz 
 */
class ComponentRemoveFocusListenerTask extends GuiTask {

  private final Component c;
  private final FocusListener l;

  static ComponentRemoveFocusListenerTask removeFocusListenerTask(Component c, FocusListener l) {
    return new ComponentRemoveFocusListenerTask(c, l);
  }
  
  ComponentRemoveFocusListenerTask(Component c, FocusListener l) {
    this.c = c;
    this.l = l;
  }
  
  protected void executeInEDT() {
    c.removeFocusListener(l);
  }
}
