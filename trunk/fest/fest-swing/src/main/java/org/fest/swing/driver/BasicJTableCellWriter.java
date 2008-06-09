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

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static java.awt.event.KeyEvent.VK_ENTER;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.concat;

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

  /**
   * Enters the given value at the given cell of the <code>JTable</code>. This method only supports the following GUI
   * components as cell editors:
   * <ul>
   * <li><code>{@link JCheckBox}</code>: valid values for the property "selected" (a boolean) are "true" and "yes",
   * other values are considered <code>false</code>.</li>
   * <li><code>{@link JComboBox}</code>: this writer will select the element which <code>String</code> representation
   * matches the given value.</li>
   * <li><code>{@link JTextComponent}</code>: any value will be entered in the cell.</li>
   * </ul>
   * @param table the target <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @param value the value to enter.
   * @throws ActionFailedException if this writer is unable to enter the given value.
   */
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
    throw actionFailure(concat("Unable to handle editor component of type ", editorTypeName(editor)));
  }

  private String editorTypeName(Component editor) {
    if (editor == null) return "<null>";
    return editor.getClass().getName();
  }

  private void check(JTable table, JCheckBox editor, int row, int column, String value) {
    boolean realValue = Boolean.parseBoolean(value);
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
