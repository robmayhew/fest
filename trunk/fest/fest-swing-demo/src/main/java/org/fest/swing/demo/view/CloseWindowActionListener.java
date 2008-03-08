/*
 * Created on Mar 7, 2008
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
package org.fest.swing.demo.view;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Closes a <code>{@link Window}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class CloseWindowActionListener implements ActionListener {

  private final Window window;

  /**
   * Creates a new </code>{@link CloseWindowActionListener}</code>.
   * @param w the <code>Window</code> to close.
   */
  CloseWindowActionListener(Window w) {
    this.window = w;
  }

  /** 
   * Closes the <code>{@link Window}</code>
   * @param e the event indicating that a component-defined action occurred.
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent) 
   */
  public void actionPerformed(ActionEvent e) {
    window.setVisible(false);
  }

}
