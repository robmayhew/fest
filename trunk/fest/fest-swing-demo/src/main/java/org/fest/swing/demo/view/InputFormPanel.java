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

import static java.awt.GridBagConstraints.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Understands a base panel for input forms.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class InputFormPanel extends JPanel {

  /**
   * Creates a new </code>{@link InputFormPanel}</code>.
   */
  public InputFormPanel() {
    super(new GridBagLayout());
    setBorder(createEmptyBorder(20, 20, 20, 20));
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = NORTHWEST;
    c.gridx = c.gridy = 0;
    c.fill = NONE;
    addInputFields(c);
    c.gridheight = REMAINDER;
    c.weighty = 1.0;
    c.gridwidth = 3; // 3 columns (label, space, input field)
    add(createVerticalGlue(), c);
  }

  abstract void addInputFields(GridBagConstraints c);

  final void addInputField(JLabel label, JComponent inputField, GridBagConstraints c) {
    label.setLabelFor(inputField);
    add(label, c);
    c.gridx++;
    add(createHorizontalStrut(10), c);
    c.gridx++;
    c.fill = HORIZONTAL;
    c.weightx = 1.0;
    add(inputField, c);
    c.weightx = 0.0;
    c.fill = NONE;
    c.gridx = 0;
    c.gridy++;
    add(createVerticalStrut(10), c);
    c.gridy++;
  }
}
