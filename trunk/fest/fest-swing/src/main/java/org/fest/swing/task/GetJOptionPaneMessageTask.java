/*
 * Created on Jul 27, 2008
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

import javax.swing.JOptionPane;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that returns the message of a <code>{@link JOptionPane}</code>.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
public class GetJOptionPaneMessageTask extends GuiTask<Object> {
  private final JOptionPane optionPane;

  /**
   * Returns the message of the given <code>{@link JOptionPane}</code>. This action is executed in the event dispatch
   * thread.
   * @param optionPane the given <code>JOptionPane</code>.
   * @return the message of the given <code>JOptionPane</code>.
   */
  public static Object messageOf(JOptionPane optionPane) {
    return new GetJOptionPaneMessageTask(optionPane).run();
  }

  private GetJOptionPaneMessageTask(JOptionPane optionPane) {
    this.optionPane = optionPane;
  }

  /**
   * Returns the message in this task's <code>{@link JOptionPane}</code>. This action is executed in the event dispatch
   * thread.
   * @return the message in this task's <code>JOptionPane</code>.
   */
  protected Object executeInEDT() {
    return optionPane.getMessage();
  }
}