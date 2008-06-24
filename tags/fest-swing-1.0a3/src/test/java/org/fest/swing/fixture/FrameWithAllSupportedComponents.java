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
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.fest.swing.testing.TestFrame;

import static java.awt.Color.RED;
import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.util.Arrays.array;

/**
 * Understands a <code>{@link JFrame}</code> that contains all the components supported by the fixtures.
 *
 * @author Alex Ruiz
 */
class FrameWithAllSupportedComponents extends TestFrame {

  private static final long serialVersionUID = 1L;

  private static final Dimension PREFERRED_DIMENSION = new Dimension(100, 100);

  final JButton button = new JButton("A Button");
  final JCheckBox checkBox = new JCheckBox("A CheckBox");
  final JComboBox comboBox = new JComboBox(array("first", "second", "third"));
  final JDialog dialog = new JDialog(this, "A Dialog");
  final JFileChooser fileChooser = new JFileChooser();
  final JLabel label = new JLabel("A Label");
  final JList list = new JList();
  final JMenu menu = new JMenu("A Menu");
  final JPanel panel = new JPanel();
  final JRadioButton radioButton = new JRadioButton("A Radio Button");
  final JScrollBar scrollBar = new JScrollBar();
  final JScrollPane scrollPane = new JScrollPane();
  final JSlider slider = new JSlider(10, 20, 15);
  final JSpinner spinner = new JSpinner(new SpinnerListModel(array("One", "Two")));
  final JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT, new JList(), new JList());
  final JMenuItem subMenu = new JMenu("A Submenu");
  final JTabbedPane tabbedPane = new JTabbedPane();
  final JTable table = new JTable(6, 8);
  final JTextField textField = new JTextField(10);
  final JToggleButton toggleButton = new JToggleButton("A ToggleButton");
  final JToolBar toolBar = new JToolBar(HORIZONTAL);

  FrameWithAllSupportedComponents(Class<?> classForTitle) {
    super(classForTitle);
    setLayout(new BoxLayout(getContentPane(), Y_AXIS));
    setUpComponents();
    addComponents();
  }

  private void addComponents() {
    setJMenuBar(new JMenuBar());
    getJMenuBar().add(menu);
    add(button, checkBox, comboBox, fileChooser, label, list, panel, radioButton, scrollBar, scrollPane, slider,
        spinner, splitPane, tabbedPane, table, textField, toggleButton, toolBar);
  }

  private void add(Component... components) {
    for (Component c : components) add(c);
  }

  private void setUpComponents() {
    button.setName("button");
    button.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        JOptionPane.showMessageDialog(FrameWithAllSupportedComponents.this, "A Message");
      }
    });
    checkBox.setName("checkBox");
    comboBox.setName("comboBox");
    dialog.setName("dialog");
    fileChooser.setName("fileChooser");
    label.setName("label");
    list.setName("list");
    menu.setName("menu");
    menu.add(subMenu);
    panel.setName("panel");
    panel.setPreferredSize(new Dimension(40, 40));
    panel.setBackground(RED);
    radioButton.setName("radioButton");
    scrollBar.setName("scrollBar");
    scrollBar.setValue(10);
    scrollPane.setName("scrollPane");
    scrollPane.setPreferredSize(PREFERRED_DIMENSION);
    slider.setName("slider");
    spinner.setName("spinner");
    splitPane.setName("splitPane");
    splitPane.setPreferredSize(PREFERRED_DIMENSION);
    tabbedPane.setName("tabbedPane");
    tabbedPane.addTab("A Tab", new JPanel());
    table.setName("table");
    table.setPreferredSize(PREFERRED_DIMENSION);
    textField.setName("textField");
    toggleButton.setName("toggleButton");
    toolBar.setName("toolBar");
  }
}