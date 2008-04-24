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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.*;

import static java.awt.GridBagConstraints.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.*;

/**
 * Understands a base panel for input forms.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
abstract class InputFormPanel extends JPanel {

  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
  
  static final String EMPTY_TEXT = "";
  
  /**
   * Creates a new </code>{@link InputFormPanel}</code>.
   */
  InputFormPanel() {
    super(new GridBagLayout());
    setBorder(createEmptyBorder(20, 20, 20, 20));
    addContent();
  }

  private void addContent() {
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

  final void addInputField(JLabel errorLabel, JLabel fieldLabel, JComponent inputField, GridBagConstraints c) {
    c.gridx = 0;
    c.insets = new Insets(0, 0, 6, 0);
    c.gridwidth = 3;
    add(errorLabel, c);
    c.gridwidth = 1;
    c.gridx = 0;
    c.gridy++;
    c.insets = EMPTY_INSETS;
    addInputField(fieldLabel, inputField, c);
  }

  final void addInputField(JLabel fieldLabel, JComponent inputField, GridBagConstraints c) {
    fieldLabel.setLabelFor(inputField);
    add(fieldLabel, c);
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
  }

  void addSpaceBetweenLines(GridBagConstraints c) {
    add(createVerticalStrut(10), c);
    c.gridy++;
  }
  
  static void clear(JComboBox comboBox) {
    comboBox.setSelectedIndex(-1);
  }
  
  static void clear(JTextField textField) {
    textField.setText(EMPTY_TEXT);
  }
  
  abstract void clear();
  
  abstract boolean validInput();
  
  abstract void save(InputForm inputForm, Window progressWindow);
}
