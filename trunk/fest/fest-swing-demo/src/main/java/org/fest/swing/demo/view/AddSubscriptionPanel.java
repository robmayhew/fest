/*
 * Created on Mar 5, 2008
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

import javax.swing.*;

import static java.awt.GridBagConstraints.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.*;

/**
 * Understands the panel where users can add a new subscription.
 *
 * @author Alex Ruiz
 */
public class AddSubscriptionPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new </code>{@link AddSubscriptionPanel}</code>.
   */
  public AddSubscriptionPanel() {
    super(new GridBagLayout());
    setBorder(createEmptyBorder(20, 20, 20, 20));
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = NORTHWEST;
    c.gridx = c.gridy = 0;
    c.fill = NONE;
    addAddressField(c);
    addNameField(c);
    addfolderField(c);
  }

  private void addAddressField(GridBagConstraints c) {
    addInputField(addressLabel(), addressField(), c);
  }

  private JLabel addressLabel() {
    JLabel label = new JLabel("Address:");
    label.setDisplayedMnemonic('A');
    return label;
  }
  
  private JTextField addressField() {
    JTextField field = new JTextField();
    field.setName("address");
    return field;
  }

  private void addNameField(GridBagConstraints c) {
    addInputField(nameLabel(), nameField(), c);
  }

  private JLabel nameLabel() {
    JLabel label = new JLabel("Name:");
    label.setDisplayedMnemonic('N');
    return label;
  }
  
  private JTextField nameField() {
    JTextField field = new JTextField();
    field.setName("name");
    return field;
  }

  private void addfolderField(GridBagConstraints c) {
    addInputField(folderLabel(), folderComboBox(), c);
  }

  private JLabel folderLabel() {
    JLabel label = new JLabel("Folder:");
    label.setDisplayedMnemonic('d');
    return label;
  }
  
  private JComboBox folderComboBox() {
    JComboBox comboBox = new JComboBox();
    comboBox.setName("folders");
    comboBox.setEditable(true);
    return comboBox;
  }

  private void addInputField(JLabel label, JComponent inputField, GridBagConstraints c) {
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
