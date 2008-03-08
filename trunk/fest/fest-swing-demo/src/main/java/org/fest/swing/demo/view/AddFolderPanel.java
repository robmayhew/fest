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

import javax.swing.JLabel;
import javax.swing.JTextField;

import static org.fest.util.Strings.isEmpty;

/**
 * Understands the panel where users can add a new folder.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class AddFolderPanel extends InputFormPanel {

  private static final long serialVersionUID = 1L;

  private static final String LABEL_NAME_KEY = "label.name";
  private static final String LABEL_NAME_MISSING_KEY = "label.name.missing";

  private JTextField nameField;
  private ErrorMessageLabel nameMissingLabel;
  
  /**
   * Creates a new </code>{@link AddFolderPanel}</code>.
   */
  AddFolderPanel() {
    super();
  }

  void addInputFields(GridBagConstraints c) {
    nameField = nameField();
    nameMissingLabel = nameMissingLabel();
    addInputField(nameMissingLabel, nameLabel(), nameField, c);
  }
  
  private ErrorMessageLabel nameMissingLabel() {
    ErrorMessageLabel label = new ErrorMessageLabel(nameField);
    label.setName("nameMissing");
    return label;
  }
  
  private JLabel nameLabel() {
    return JComponentFactory.instance().labelWithMnemonic(i18n, LABEL_NAME_KEY);
  }
  
  private JTextField nameField() {
    JTextField field = new JTextField();
    field.setName("folderName");
    return field;
  }
  
  boolean validInput() {
    String name = nameField.getText();
    if (!isEmpty(name)) return true;
    nameMissingLabel.errorMessage(i18n.message(LABEL_NAME_MISSING_KEY));
    return false;
  }
}
