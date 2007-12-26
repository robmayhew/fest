/*
 * Created on Sep 16, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.format;

import java.awt.Adjustable;
import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

import org.fest.swing.testing.TestFrame;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Formatting}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FormattingTest {

  private static Logger logger = Logger.getAnonymousLogger();

  @Test public void shouldFormatDialog() {
    JDialog dialog = new JDialog();
    dialog.setTitle("A dialog");
    dialog.setName("dialog");
    String formatted = formatted(dialog);
    assertThat(formatted).isEqualTo(expected(dialog, "[name='dialog', title='A dialog', enabled=true, modal=false, showing=false]"));
  }

  @Test public void shouldFormatFrame() {
    TestFrame frame = TestFrame.showInTest(getClass());
    frame.setName("frame");
    String formatted = formatted(frame);
    assertThat(formatted).isEqualTo(
        expected(frame, "[name='frame', title='FormattingTest', enabled=true, showing=true]"));
    frame.destroy();
  }

  @SuppressWarnings("unchecked")
  @Test public void shouldFormatJComboBox() {
    JComboBox comboBox = new JComboBox(array("One", 2, "Three", 4));
    comboBox.setName("comboBox");
    comboBox.setSelectedIndex(1);
    comboBox.setEditable(true);
    String formatted = formatted(comboBox);
    assertThat(formatted).isEqualTo(
        expected(comboBox, "[name='comboBox', selectedItem=2, contents=['One', 2, 'Three', 4], enabled=true, editable=true]"));
  }

  @Test public void shouldFormatJButton() {
    JButton button = new JButton("A button");
    button.setName("button");
    button.setEnabled(false);
    String formatted = formatted(button);
    assertThat(formatted).isEqualTo(expected(button, "[name='button', text='A button', enabled=false]"));
  }

  @Test public void shouldFormatJFileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("A file chooser");
    fileChooser.setName("fileChooser");
    fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    String formatted = formatted(fileChooser);
    assertThat(formatted).isEqualTo(
        expected(fileChooser,
            concat("[name='fileChooser', dialogTitle='A file chooser', dialogType=OPEN_DIALOG, currentDirectory=",
                fileChooser.getCurrentDirectory(), ", enabled=true]")));
  }

  @Test public void shouldFormatJLabel() {
    JLabel label = new JLabel("A label");
    label.setName("label");
    String formatted = formatted(label);
    assertThat(formatted).isEqualTo(expected(label, "[name='label', text='A label', enabled=true]"));
  }

  @Test public void shouldFormatJLayeredPane() {
    JLayeredPane pane = new JLayeredPane();
    String formatted = formatted(pane);
    assertThat(formatted).isEqualTo(expected(pane, "[]"));
  }

  @SuppressWarnings("unchecked")
  @Test public void shouldFormatJList() {
    JList list = new JList(array("One", 2, "Three", 4));
    list.setName("list");
    list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    list.setSelectedIndices(new int[] { 0, 1 });
    String formatted = formatted(list);
    assertThat(formatted).isEqualTo(
        expected(list,
            concat("[name='list', selectedValues=['One', 2], contents=['One', 2, 'Three', 4], ",
                   "selectionMode=MULTIPLE_INTERVAL_SELECTION, enabled=true]")));
  }

  @Test public void shouldFormatJMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    String formatted = formatted(menuBar);
    assertThat(formatted).isEqualTo(expected(menuBar, "[]"));
  }

  @Test public void shouldFormatJMenuItem() {
    JMenuItem menuItem = new JMenuItem();
    menuItem.setText("a Menu Item");
    menuItem.setName("menuItem");
    menuItem.setSelected(true);
    String formatted = formatted(menuItem);
    assertThat(formatted).isEqualTo(expected(menuItem, "[name='menuItem', text='a Menu Item', selected=true, enabled=true]"));
  }

  @Test public void shouldFormatJOptionPane() {
    JOptionPane optionPane = new JOptionPane("A message", JOptionPane.ERROR_MESSAGE);
    optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
    String formatted = formatted(optionPane);
    assertThat(formatted).isEqualTo(
        expected(optionPane,
            "[message='A message', messageType=ERROR_MESSAGE, optionType=DEFAULT_OPTION, enabled=true, showing=false]"));
  }

  @Test public void shouldFormatJPanel() {
    JPanel panel = new JPanel();
    panel.setName("panel");
    String formatted = formatted(panel);
    assertThat(formatted).isEqualTo(expected(panel, "[name='panel']"));
  }

  @Test public void shouldFormatJPopupMenu() {
    JPopupMenu popupMenu = new JPopupMenu("Menu");
    popupMenu.setName("popupMenu");
    String formatted = formatted(popupMenu);
    assertThat(formatted).isEqualTo(expected(popupMenu, "[name='popupMenu', label='Menu', enabled=true]"));
  }

  @Test public void shouldFormatJRootPane() {
    JRootPane pane = new JRootPane();
    String formatted = formatted(pane);
    assertThat(formatted).isEqualTo(expected(pane, "[]"));
  }

  @Test public void shouldFormatJScrollBar() {
    JScrollBar scrollBar = new JScrollBar(Adjustable.VERTICAL, 20, 10, 0, 60);
    scrollBar.setName("scrollBar");
    String formatted = formatted(scrollBar);
    assertThat(formatted).isEqualTo(expected(scrollBar,
        "[name='scrollBar', value=20, blockIncrement=10, minimum=0, maximum=60, enabled=true]"
    ));
  }

  @Test public void shouldFormatJScrollPane() {
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setName("scrollPane");
    String formatted = formatted(scrollPane);
    assertThat(formatted).isEqualTo(expected(scrollPane, "[name='scrollPane', enabled=true]"));
  }

  @Test public void shouldFormatJSlider() {
    JSlider slider = new JSlider(2, 8, 6);
    slider.setName("slider");
    String formatted = formatted(slider);
    assertThat(formatted).isEqualTo(expected(slider, "[name='slider', value=6, minimum=2, maximum=8, enabled=true]"));
  }

  @Test public void shouldFormatJSpinner() {
    JSpinner spinner = new JSpinner(new SpinnerNumberModel(6, 2, 8, 1));
    spinner.setName("spinner");
    String formatted = formatted(spinner);
    assertThat(formatted).isEqualTo(expected(spinner, "[name='spinner', value=6, enabled=true]"));
  }

  @Test public void shouldFormatJTabbedPane() {
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("First", new JPanel());
    tabbedPane.addTab("Second", new JPanel());
    tabbedPane.addTab("Third", new JPanel());
    tabbedPane.setName("tabbedPane");
    tabbedPane.setSelectedIndex(1);
    String formatted = formatted(tabbedPane);
    assertThat(formatted).isEqualTo(expected(tabbedPane, concat(
        "[name='tabbedPane', selectedTabIndex=1, selectedTabTitle='Second', tabCount=3, ",
        "tabTitles=['First', 'Second', 'Third'], enabled=true]"
    )));
  }

  @Test public void shouldFormatJTable() {
    JTable table = new JTable(8, 6);
    table.setName("table");
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    String formatted = formatted(table);
    assertThat(formatted).isEqualTo(expected(table, concat(
        "[name='table', rowCount=8, columnCount=6, selectedRows=[], selectedColumns=[], ",
        "rowSelectionMode=MULTIPLE_INTERVAL_SELECTION, columnSelectionMode=MULTIPLE_INTERVAL_SELECTION, enabled=true]"
    )));
  }

  @Test public void shouldFormatJTextComponent() {
    JTextField textField = new JTextField("Hello");
    textField.setName("textField");
    String formatted = formatted(textField);
    assertThat(formatted).isEqualTo(expected(textField, "[name='textField', text='Hello', enabled=true]"));
  }

  @Test public void shouldFormatJToggleButton() {
    JRadioButton radio = new JRadioButton();
    radio.setText("a Radio");
    radio.setName("radio");
    radio.setSelected(true);
    String formatted = formatted(radio);
    assertThat(formatted).isEqualTo(expected(radio, "[name='radio', text='a Radio', selected=true, enabled=true]"));
  }

  @Test public void shouldFormatJTree() {
    JTree tree = new JTree(array("One", "Two", "Three"));
    tree.setName("tree");
    DefaultTreeSelectionModel model = new DefaultTreeSelectionModel();
    model.setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
    tree.setSelectionModel(model);
    tree.setSelectionRow(1);
    String formatted = formatted(tree);
    assertThat(formatted).isEqualTo(expected(tree, concat(
        "[name='tree', selectionCount=1, selectionPaths=['[root, Two]'], selectionMode=CONTIGUOUS_TREE_SELECTION, enabled=true]"
    )));
  }

  private String formatted(Component c) {
    String formatted = Formatting.format(c);
    logger.info(concat("formatted: ", formatted));
    return formatted;
  }

  private String expected(Component c, String properties) {
    return concat(c.getClass().getName(), properties);
  }

  @Test public void shouldReturnComponentIsNullIfComponentIsNull() {
    assertThat(Formatting.format(null)).isEqualTo("Null Component");
  }
}
