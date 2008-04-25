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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.jdesktop.swinghelper.layer.JXLayer;

import static java.awt.BorderLayout.*;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

import static org.fest.swing.demo.view.Swing.center;

/**
 * Understands the main window of the application.
 *
 * @author Alex Ruiz
 */
public class MainFrame extends JFrame {

  private static final long serialVersionUID = 1L;

  private static final String FRAME_TITLE_KEY = "frame.title";

  private final JXLayer<JPanel> layer;
  
  private final WebFeedTree webFeedTree = new WebFeedTree(this);
  private final WebFeedItemsTable webFeedItemsTable = new WebFeedItemsTable();

  /**
   * Creates a new </code>{@link MainFrame}</code>.
   */
  public MainFrame() {
    setLayout(new BorderLayout());
    layer = JComponentFactory.instance().blurFilteredLayer(content());
    add(layer, CENTER);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle(i18n().message(FRAME_TITLE_KEY));
    setPreferredSize(new Dimension(600, 400));
    pack();
  }

  private JPanel content() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(splitPane(), CENTER);
    panel.add(new MainActionPanel(this), SOUTH);
    return panel;
  }

  private JSplitPane splitPane() {
    JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT);
    splitPane.setLeftComponent(new JScrollPane(webFeedTree));
    final JScrollPane tableScrollPane = new JScrollPane(webFeedItemsTable);
    addComponentListener(new ComponentAdapter() {
      @Override public void componentResized(ComponentEvent e) {
        webFeedItemsTable.resizeColumns(tableScrollPane.getSize());
      }
    });
    splitPane.setRightComponent(tableScrollPane);
    splitPane.setDividerLocation(120);
    return splitPane;
  }

  void lock() {
    layer.setLocked(true);
  }

  void unlock() {
    layer.setLocked(false);
  }

  void addContentToWebFeedTree(Object content) {
    webFeedTree.addContent(content);
  }
  
  /** @see java.awt.Window#setVisible(boolean) */
  @Override public void setVisible(boolean visible) {
    if (visible) center(this);
    super.setVisible(visible);
  }

  void clearWebFeedItems() {
    webFeedItemsTable.removeAllRows();
  }
  
  private static I18n i18n() {
    return I18nSingletonHolder.INSTANCE;
  }
  
  private static class I18nSingletonHolder {
    static final I18n INSTANCE = new I18n(MainFrame.class);
  }
}
