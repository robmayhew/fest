/*
 * Created on Aug 8, 2008
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

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.driver.Actions.findActionKey;
import static org.fest.swing.driver.KeyStrokes.findKeyStrokesForAction;

/**
 * Understands an action, executed in the event dispatch thread, that returns the key strokes for an action in a
 * <code>{@link JComponent}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JComponentKeyStrokesForActionQuery extends GuiQuery<KeyStroke[]> {
  
  // TODO: write test case
  
  private final JComponent component;
  private final String actionName;

  static KeyStroke[] keyStrokesForAction(JComponent component, String actionName) {
    return new JComponentKeyStrokesForActionQuery(component, actionName).run();
  }
  
  private JComponentKeyStrokesForActionQuery(JComponent component, String actionName) {
    this.component = component;
    this.actionName = actionName;
  }

  protected KeyStroke[] executeInEDT() {
    Object key = findActionKey(actionName, component.getActionMap());
    return findKeyStrokesForAction(actionName, key, component.getInputMap());
  }
}