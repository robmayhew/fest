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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import static java.awt.GridBagConstraints.*;
import static javax.swing.Box.createHorizontalGlue;

/**
 * Understands the panel that contains the actions the user can perform on the main window (<code>{@link MainFrame}</code>).
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class MainActionPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private static final String BUTTON_ADD_KEY = "button.add";
  
  private final MainFrame mainFrame;
  private final I18n i18n;
  
  /**
   * Creates a new </code>{@link MainActionPanel}</code>.
   * @param mainFrame the main application window.
   */
  public MainActionPanel(MainFrame mainFrame) {
    super(new GridBagLayout());
    this.mainFrame = mainFrame;
    i18n = new I18n(this);
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = NORTHWEST;
    c.gridx = c.gridy = 0;
    add(addButton(), c);
    c.gridx++;
    c.fill = BOTH;
    c.weightx = 1.0;
    add(createHorizontalGlue(), c);
  }
  
  private JButton addButton() {
    JButton button = JComponentFactory.instance().buttonWithMnemonic(i18n, BUTTON_ADD_KEY);
    button.setName("add");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AddDialog addDialog = new AddDialog(mainFrame);
        addDialog.setVisible(true);
      }
    });
    return button;
  }
}
