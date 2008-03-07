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

import javax.swing.*;

import static java.awt.GridBagConstraints.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.*;

import static org.fest.swing.demo.view.Icons.*;
import static org.fest.swing.demo.view.Swing.center;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author Alex Ruiz
 */
public class AddDialog extends JDialog {
  
  private static final long serialVersionUID = 1L;

  private static final String WEB_FEED_CARD = "WebFeed";

  private final CardLayout cardLayout = new CardLayout();
  
  /**
   * Creates a new </code>{@link AddDialog}</code>.
   * @param owner
   */
  public AddDialog(MainFrame owner) {
    super(owner, "Add New", DEFAULT_MODALITY_TYPE);
    setLocationRelativeTo(owner);
    setLayout(new BorderLayout());
    add(optionPanel(), BorderLayout.NORTH);
    add(inputPanel(), BorderLayout.CENTER);
    add(actionPanel(), BorderLayout.SOUTH);
    setPreferredSize(new Dimension(320, 250));
    setResizable(false);
    pack();
    center(this);
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

  private AbstractButton addWebFeedButton() {
    AbstractButton button = optionButton("Web Feed", INTERNET_FEEDS_ICON);
    button.setName("addWebFeed");
    button.setMnemonic('W');
    button.setSelected(true);
    return button;
  }

  private AbstractButton addFolderButton() {
    AbstractButton button = optionButton("Folder", FOLDER_ICON);
    button.setName("addFolder");
    button.setMnemonic('F');
    return button;
  }

  private AbstractButton optionButton(String text, Icon icon) {
    JToggleButton button = new JToggleButton(text, icon);
    button.setBorderPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    button.setFocusable(false);
    button.setFocusPainted(false);
    button.setVerticalTextPosition(AbstractButton.BOTTOM);
    button.setHorizontalTextPosition(AbstractButton.CENTER);
    return button;
  }
  
  private void addToButtonGroup(AbstractButton...buttons) {
    ButtonGroup group = new ButtonGroup();
    for (AbstractButton button : buttons) group.add(button);
  }
  
  private JPanel inputPanel() {
    JPanel panel = new JPanel(cardLayout);
    panel.add(new AddSubscriptionPanel(), WEB_FEED_CARD);
    cardLayout.show(panel, WEB_FEED_CARD);
    return panel;
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
    return button;
  }

  private JButton okButton() {
    JButton button = new JButton("OK");
    button.setDefaultCapable(true);
    button.setMnemonic('O');
    button.setName("ok");
    return button;
  }
}
