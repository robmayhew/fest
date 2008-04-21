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
import java.awt.Window;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.fest.swing.demo.model.Folder;
import org.fest.swing.demo.model.WebFeed;
import org.fest.swing.demo.service.Services;

import static org.fest.util.Strings.isEmpty;

/**
 * Understands the panel where users can add a new web feed.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class AddWebFeedPanel extends InputFormPanel {

  private static final long serialVersionUID = 1L;

  private static final String LABEL_ADDRESS_KEY = "label.address";
  private static final String LABEL_ADDRESS_MISSING_KEY = "label.address.missing";
  private static final String LABEL_NAME_KEY = "label.name";
  private static final String FOLDER_NAME_KEY = "label.folder";

  private ErrorMessageLabel addressMissingLabel;
  private JTextField addressField;
  private JTextField nameField;
  private JComboBox folderComboBox;

  void addInputFields(GridBagConstraints c) {
    addAddressField(c);
    addSpaceBetweenLines(c);
    addNameField(c);
    addSpaceBetweenLines(c);
    addfolderField(c);
  }

  private void addAddressField(GridBagConstraints c) {
    addressField = addressField();
    addressMissingLabel = addressMissingLabel();
    addInputField(addressMissingLabel, addressLabel(), addressField, c);
  }

  private JTextField addressField() {
    JTextField field = new JTextField();
    field.setName("address");
    return field;
  }

  private ErrorMessageLabel addressMissingLabel() {
    ErrorMessageLabel label = new ErrorMessageLabel(addressField);
    label.setName("addressMissing");
    return label;
  }

  private JLabel addressLabel() {
    return JComponentFactory.instance().labelWithMnemonic(i18n, LABEL_ADDRESS_KEY);
  }

  private void addNameField(GridBagConstraints c) {
    nameField = nameField();
    addInputField(nameLabel(), nameField, c);
  }

  private JLabel nameLabel() {
    return JComponentFactory.instance().labelWithMnemonic(i18n, LABEL_NAME_KEY);
  }

  private JTextField nameField() {
    JTextField field = new JTextField();
    field.setName("name");
    return field;
  }

  private void addfolderField(GridBagConstraints c) {
    folderComboBox = folderComboBox();
    addInputField(folderLabel(), folderComboBox, c);
  }

  private JLabel folderLabel() {
    return JComponentFactory.instance().labelWithMnemonic(i18n, FOLDER_NAME_KEY);
  }

  private JComboBox folderComboBox() {
    String[] folderNames = folderNames();
    JComboBox comboBox = new JComboBox(folderNames);
    comboBox.setName("folder");
    comboBox.setEditable(true);
    comboBox.setSelectedIndex(-1);
    return comboBox;
  }

  private String[] folderNames() {
    Folder[] folders = Services.instance().folderService().allFolders();
    int folderCount = folders.length;
    String[] folderNames = new String[folderCount];
    for (int i = 0; i < folderCount; i++)
      folderNames[i] = folders[i].name();
    return folderNames;
  }

  void clear() {
    clear(addressField);
    clear(nameField);
    clear(folderComboBox);
  }

  boolean validInput() {
    if (!isEmpty(addressField.getText())) return true;
    addressMissingLabel.showErrorMessage(i18n.message(LABEL_ADDRESS_MISSING_KEY));
    return false;
  }

  void save(InputForm inputForm, Window progressWindow) {
    String address = addressField.getText();
    String name = nameField.getText();
    String folder = selectedFolder();
    WebFeed webFeed = new WebFeed(address, name, folder);
    new SaveWebFeedWorker(webFeed, inputForm, progressWindow).execute();
  }

  private String selectedFolder() {
    Object folder = folderComboBox.getSelectedItem();
    return folder != null ? folder.toString() : null;
  }
}
