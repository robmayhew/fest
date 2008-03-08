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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.FocusManager;

import static java.awt.GridBagConstraints.*;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.*;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

import static org.fest.swing.demo.view.Icons.*;
import static org.fest.swing.demo.view.Swing.center;

/**
 * Understands the dialog where users can create new web feeds and/or folders.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class AddDialog extends JDialog {
  
  private static final long serialVersionUID = 1L;

  private static final String WEB_FEED_CARD = "WebFeed";
  private static final String FOLDER_CARD = "Folder";

  private static final String BUTTON_WEB_FEED_KEY = "button.webfeed";
  private static final String BUTTON_FOLDER_KEY = "button.folder";
  
  private final CardLayout cardLayout = new CardLayout();
  private final JPanel inputFormCardPanel = new JPanel(cardLayout);

  private final I18n i18n;
  
  /**
   * Creates a new </code>{@link AddDialog}</code>.
   * @param owner the owner of this dialog.
   */
  public AddDialog(MainFrame owner) {
    super(owner, "Add New", DEFAULT_MODALITY_TYPE);
    i18n = new I18n(this);
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout());
    addContent();
    setPreferredSize(new Dimension(320, 260));
    setResizable(false);
    pack();
    center(this);
  }

  private void addContent() {
    add(optionPanel(), BorderLayout.NORTH);
    addPanelsToInputFormCardPanel();
    add(inputFormCardPanel, BorderLayout.CENTER);
    add(actionPanel(), BorderLayout.SOUTH);
  }

  private void addPanelsToInputFormCardPanel() {
    inputFormCardPanel.add(new AddWebFeedPanel(), WEB_FEED_CARD);
    inputFormCardPanel.add(new AddFolderPanel(), FOLDER_CARD);
    cardLayout.show(inputFormCardPanel, WEB_FEED_CARD);
  }

  private JPanel optionPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = NORTHWEST;
    c.gridx = c.gridy = 0;
    AbstractButton addWebFeedButton = addWebFeedButton();
    AbstractButton addFolderButton = addFolderButton();
    addToButtonGroup(addWebFeedButton, addFolderButton);
    panel.add(addWebFeedButton, c);
    c.gridx++;
    panel.add(addFolderButton, c);
    c.fill = BOTH;
    c.weightx = 1.0;
    panel.add(createHorizontalGlue(), c);
    c.gridy++;
    c.gridx = 0;
    c.gridwidth = 3;
    panel.add(new JSeparator(), c);
    return panel;
  }

  private JToggleButton addWebFeedButton() {
    JToggleButton button = optionButton(BUTTON_WEB_FEED_KEY, INTERNET_FEEDS_ICON, WEB_FEED_CARD);
    button.setName("addWebFeed");
    button.setSelected(true);
    return button;
  }

  private JToggleButton addFolderButton() {
    JToggleButton button = optionButton(BUTTON_FOLDER_KEY, FOLDER_ICON, FOLDER_CARD);
    button.setName("addFolder");
    return button;
  }

  private JToggleButton optionButton(String i18nKey, Icon icon, final String cardName) {
    final JToggleButton button = JComponentFactory.instance().toggleButtonWithMnemonic(i18n, i18nKey);
    button.setIcon(icon);
    button.setBorderPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    button.setFocusable(false);
    button.setFocusPainted(false);
    button.setVerticalTextPosition(AbstractButton.BOTTOM);
    button.setHorizontalTextPosition(AbstractButton.CENTER);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(inputFormCardPanel, cardName);
        FocusManager.getCurrentManager().focusNextComponent(button);
      }
    });
    return button;
  }
  
  private void addToButtonGroup(AbstractButton...buttons) {
    ButtonGroup group = new ButtonGroup();
    for (AbstractButton button : buttons) group.add(button);
  }
  
  private JPanel actionPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(createEmptyBorder(10, 10, 10, 10));
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = NORTHWEST;
    c.gridx = c.gridy = 0;
    c.weightx = 1.0;
    panel.add(createHorizontalGlue(), c);
    c.gridx++;
    c.weightx = 0.0;
    panel.add(cancelButton(), c);
    c.gridx++;
    panel.add(createHorizontalStrut(10), c);
    c.gridx++;
    panel.add(okButton(), c);
    return panel;
  }
  
  private JButton cancelButton() {
    JButton button = new JButton("Cancel");
    button.setMnemonic('C');
    button.setName("cancel");
    button.addActionListener(new CloseWindowActionListener(this));
    return button;
  }

  private JButton okButton() {
    JButton button = new JButton("OK");
    button.setDefaultCapable(true);
    button.setMnemonic('O');
    button.setName("ok");
    return button;
  }

  @Override public JRootPane getRootPane() {
    ActionListener closeAction = new CloseWindowActionListener(this);
    JRootPane rootPane = super.getRootPane();
    KeyStroke stroke = KeyStroke.getKeyStroke(VK_ESCAPE, 0);
    rootPane.registerKeyboardAction(closeAction, stroke, WHEN_IN_FOCUSED_WINDOW);
    return rootPane;
  }
}
