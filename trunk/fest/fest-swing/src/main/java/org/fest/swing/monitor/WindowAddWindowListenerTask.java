/*
 * Created on Sep 2, 2008
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
package org.fest.swing.monitor;

import java.awt.Window;
import java.awt.event.WindowListener;

import org.fest.swing.edt.GuiTask;

/**
 * Understands a task that adds a <code>{@link WindowListener}</code> to a given <code>{@link Window}</code>.
 * This task should be executed in the event dispatch thread.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class WindowAddWindowListenerTask extends GuiTask {

  private final Window w;
  private final WindowListener l;

  static WindowAddWindowListenerTask addWindowListenerTask(Window w, WindowListener l) {
    return new WindowAddWindowListenerTask(w, l);
  }

  WindowAddWindowListenerTask(Window w, WindowListener l) {
    this.w = w;
    this.l = l;
  }

  protected void executeInEDT() {
    w.addWindowListener(l);
  }
}
