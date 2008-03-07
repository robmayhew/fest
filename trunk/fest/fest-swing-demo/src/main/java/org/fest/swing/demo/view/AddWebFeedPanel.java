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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Understands the panel where users can add a new web feed.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class AddWebFeedPanel extends InputFormPanel {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new </code>{@link AddWebFeedPanel}</code>.
   */
  public AddWebFeedPanel() {
    super();
  }

  void addInputFields(GridBagConstraints c) {
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
    field.setName("feedName");
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
}
