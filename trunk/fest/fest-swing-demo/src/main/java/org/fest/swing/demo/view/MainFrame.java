/*
 * Created on Mar 3, 2008
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;

import static java.awt.BorderLayout.*;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

/**
 * Understands the main window of the application.
 *
 * @author Alex Ruiz
 */
public class MainFrame extends JFrame {

  private static final long serialVersionUID = 1L;
  
  private static final String FRAME_TITLE_KEY = "frame.title";

  private final I18n i18n;
  
  /**
   * Creates a new </code>{@link MainFrame}</code>.
   */
  public MainFrame() {
    setLayout(new BorderLayout());
    i18n = new I18n(this);
    add(splitPane(), CENTER);
    add(new MainActionPanel(this), SOUTH);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle(i18n.message(FRAME_TITLE_KEY));
    setPreferredSize(new Dimension(600, 400));
    pack();
    center();
  }
  
  private void center() {
    Dimension screenSize = getToolkit().getScreenSize();
    Rectangle bounds = getBounds();
    setLocation((screenSize.width - bounds.width) / 2, (screenSize.height - bounds.height) / 2);    
  }
  
  private JSplitPane splitPane() {
    JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
    splitPane.setLeftComponent(new JTree());
    splitPane.setRightComponent(new JTable(10, 4));
    return splitPane;
  }
}
