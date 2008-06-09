/*
 * Created on Jun 8, 2008
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
package org.fest.swing.driver;

import static java.awt.event.KeyEvent.VK_ENTER;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;

/**
 * Understands the default implementation of <code>{@link JTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTableCellWriter implements JTableCellWriter {

  private final Robot robot;
  private final JComboBoxDriver comboBoxDriver;
  private final JTextComponentDriver textComponentDriver;

  private final JTableLocation tableLocation = new JTableLocation();

  public BasicJTableCellWriter(Robot robot) {
    this.robot = robot;
    comboBoxDriver = new JComboBoxDriver(robot);
    textComponentDriver = new JTextComponentDriver(robot);
  }

  /** ${@inheritDoc} */
  public void enterValue(JTable table, int row, int column, String value) {
    Component editor = editorForCell(table, row, column);
    if (editor instanceof JCheckBox) {
      check(table, (JCheckBox)editor, row, column, value);
      return;
    }
    if (editor instanceof JComboBox) {
      selectItem(table, (JComboBox)editor, row, column, value);
      return;
    }
    if (editor instanceof JTextComponent) {
      enterValue(table, (JTextComponent)editor, row, column, value);
    }
  }

  private void check(JTable table, final JCheckBox editor, int row, int column, String value) {
    final boolean realValue = Boolean.parseBoolean(value);
    if (editor.isSelected() == realValue) return;
    clickCell(table, row, column);
  }

  private void selectItem(JTable table, JComboBox editor, int row, int column, String value) {
    clickCell(table, row, column);
    comboBoxDriver.selectItem(editor, value);
  }

  private void clickCell(JTable table, int row, int column) {
    clickCell(table, row, column, 1);
  }

  private void enterValue(JTable table, JTextComponent editor, int row, int column, String value) {
    clickCell(table, row, column, 2);
    textComponentDriver.replaceText(editor, value);
    textComponentDriver.pressAndReleaseKeys(editor, VK_ENTER);
  }

  private void clickCell(JTable table, int row, int column, int times) {
    comboBoxDriver.scrollToVisible(table, tableLocation.cellBounds(table, row, column));
    robot.click(table, tableLocation.pointAt(table, row, column), LEFT_BUTTON, times);
  }

  /** ${@inheritDoc} */
  public Component editorForCell(JTable table, int row, int column) {
    TableCellEditor cellEditor = table.getCellEditor(row, column);
    if (cellEditor == null) return null;
    return cellEditor.getTableCellEditorComponent(table, table.getValueAt(row, column), false, row, column);
  }
}
