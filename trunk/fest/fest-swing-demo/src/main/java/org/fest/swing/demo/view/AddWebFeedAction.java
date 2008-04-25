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

import static org.fest.swing.demo.view.AddDialog.addWebFeedDialog;
import static org.fest.swing.demo.view.Icons.ADD_ICON;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.fest.swing.demo.model.Folder;

/**
 * Understands an action that displays an instance of <code>{@link AddDialog}</code>.
 *
 * @author Alex Ruiz
 */
class AddWebFeedAction extends AbstractAction {

  private static final long serialVersionUID = 1L;

  private static final String ACTION_ADD_FEED_KEY = "action.add.feed";

  private final MainFrame mainFrame;
  private final Folder selectedFolder;

  /**
   * Creates a new </code>{@link AddWebFeedAction}</code>.
   * @param mainFrame the main frame of the application.
   * @param selectedFolder the folder to contain the web feed to create.
   */
  AddWebFeedAction(MainFrame mainFrame, Folder selectedFolder) {
    this.mainFrame = mainFrame;
    this.selectedFolder = selectedFolder;
    String name = i18n().message(ACTION_ADD_FEED_KEY);
    int mnemonic = i18n().mnemonic(ACTION_ADD_FEED_KEY);
    putValue(NAME, name);
    putValue(MNEMONIC_KEY, mnemonic);
    putValue(SMALL_ICON, ADD_ICON);
  }

  private static I18n i18n() {
    return I18nSingletonHolder.INSTANCE;
  }

  /**
   * Shows an instance of <code>{@link AddDialog}</code>.
   * @param e the action event.
   */
  public void actionPerformed(ActionEvent e) {
    mainFrame.lock();
    AddDialog addDialog = addWebFeedDialog(mainFrame, selectedFolder);
    addDialog.setVisible(true);
  }

  private static class I18nSingletonHolder {
    static final I18n INSTANCE = new I18n(AddWebFeedAction.class);
  }
}
