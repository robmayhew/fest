/*
 * Created on Aug 21, 2008
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
package org.fest.swing.driver;

import static org.fest.swing.core.GuiActionRunner.execute;

import javax.swing.JOptionPane;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the type of message of a
 * <code>{@link JOptionPane}</code>.
 * 
 * @author Alex Ruiz
 */
class JOptionPaneMessageTypeQuery extends GuiQuery<Integer> {

  private final JOptionPane optionPane;

  static int messageTypeOf(JOptionPane optionPane) {
    return execute(new JOptionPaneMessageTypeQuery(optionPane));
  }
  
  JOptionPaneMessageTypeQuery(JOptionPane optionPane) {
    this.optionPane = optionPane;
  }

  protected Integer executeInEDT() {
    return optionPane.getMessageType();
  }
}