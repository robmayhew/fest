/*
 * Created on Aug 31, 2008
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
import java.awt.event.ComponentListener;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that adds a <code>{@link ComponentListener}</code> to a given <code>{@link Component}</code>.
 * This task should be executed in the event dispatch thread.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class AddComponentListenerTask extends GuiTask {

  private final Component c;
  private final ComponentListener l;

  /**
   * Creates a new <code>{@link AddComponentListenerTask}</code>.
   * @param c the <code>Component</code> to add a <code>ComponentListener</code> to.
   * @param l the <code>ComponentListener</code> to add.
   * @return the created task.
   */
  public static AddComponentListenerTask addComponentListenerTask(Component c, ComponentListener l) {
    return new AddComponentListenerTask(c, l);
  }

  AddComponentListenerTask(Component c, ComponentListener l) {
    this.c = c;
    this.l = l;
  }

  /**
   * Adds this task's <code>{@link ComponentListener}</code> to this task's <code>{@link Component}</code>. This action
   * is executed in the event dispatch thread.
   */
  protected void executeInEDT() {
    c.addComponentListener(l);
  }
}
