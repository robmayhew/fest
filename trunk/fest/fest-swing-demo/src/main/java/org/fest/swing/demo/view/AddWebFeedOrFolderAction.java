/*
 * Created on Apr 23, 2008
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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import static org.fest.swing.demo.view.Icons.ADD_ICON;

/**
 * Understands an action that displays an instance of <code>{@link AddDialog}</code>.
 *
 * @author Alex Ruiz
 */
class AddWebFeedOrFolderAction extends AbstractAction {

  private static final long serialVersionUID = 1L;
  
  private static final String ACTION_ADD_KEY = "action.add";

  private final MainFrame mainFrame;

  /**
   * Creates a new </code>{@link AddWebFeedOrFolderAction}</code>.
   * @param mainFrame the main frame of the application.
   */
  AddWebFeedOrFolderAction(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
    I18n i18n = new I18n(this);
    String name = i18n.message(ACTION_ADD_KEY);
    int mnemonic = i18n.mnemonic(ACTION_ADD_KEY);
    putValue(NAME, name);
    putValue(MNEMONIC_KEY, mnemonic);
    putValue(SMALL_ICON, ADD_ICON);
  }

  /**
   * Shows an instance of <code>{@link AddDialog}</code>.
   * @param e the action event.
   */
  public void actionPerformed(ActionEvent e) {
    mainFrame.lock();
    AddDialog addDialog = new AddDialog(mainFrame);
    addDialog.setVisible(true);
  }
}
