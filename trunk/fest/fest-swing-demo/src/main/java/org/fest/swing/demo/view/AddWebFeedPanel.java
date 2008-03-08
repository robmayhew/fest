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

  private static final String LABEL_ADDRESS_KEY = "label.address";
  private static final String LABEL_NAME_KEY = "label.name";
  private static final String FOLDER_NAME_KEY = "label.folder";

  /**
   * Creates a new </code>{@link AddWebFeedPanel}</code>.
   */
  public AddWebFeedPanel() {
    super();
  }

  void addInputFields(GridBagConstraints c) {
    addAddressField(c);
    addSpaceBetweenLines(c);
    addNameField(c);
    addSpaceBetweenLines(c);
    addfolderField(c);
  }
  
  private void addAddressField(GridBagConstraints c) {
    addInputField(addressLabel(), addressField(), c);
  }

  private JLabel addressLabel() {
    return JComponentFactory.instance().labelWithMnemonic(i18n, LABEL_ADDRESS_KEY);
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
    return JComponentFactory.instance().labelWithMnemonic(i18n, LABEL_NAME_KEY);
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
    return JComponentFactory.instance().labelWithMnemonic(i18n, FOLDER_NAME_KEY);
  }
  
  private JComboBox folderComboBox() {
    JComboBox comboBox = new JComboBox();
    comboBox.setName("folders");
    comboBox.setEditable(true);
    return comboBox;
  }
}
