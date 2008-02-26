/*
 * Created on Feb 2, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.driver.CellRendererComponents.textFrom;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JTable;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;

/**
 * Understands simulation of user input on a <code>{@link JTable}</code>. Unlike <code>JTableFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JTable}</code>s. This class is intended for
 * internal use only.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableDriver extends JComponentDriver {

  private final JTableLocation location = new JTableLocation();

  /**
   * Creates a new </code>{@link JTableDriver}</code>.
   * @param robot the robot to use to simulate user events.
   */
  public JTableDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Returns the value of the selected cell into a reasonable <code>String</code> representation. Returns
   * <code>null</code> if one can not be obtained or if the <code>{@link JTable}</code> does not have any selected
   * cell.
   * @param table the target <code>JTable</code>.
   * @return the value of the selected cell.
   */
  public String selectionText(JTable table) {
    if (table.getSelectedRowCount() == 0) return null;
    return text(table, table.getSelectedRow(), table.getSelectedColumn());
  }

  /**
   * Convert the value at the given row and row into a reasonable <code>String</code> representation, or
   * <code>null</code> if one cannot be obtained.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @return a <code>String</code> representation of the the value at the given row and column, or <code>null</code> if
   * one cannot be obtained.
   */
  public String text(JTable table, int row, int column) {
    return textFrom(cellRendererComponent(table, row, column));
  }

  private static Component cellRendererComponent(JTable table, int row, int col) {
    Object value = table.getValueAt(row, col);
    return table.getCellRenderer(row, col).getTableCellRendererComponent(table, value, false, false, row, col);
  }

  /**
   * Selects the given cell, if it is not selected already.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   */
  public void selectCell(JTable table, int row, int column) {
    if (isCellSelected(table, row, column)) return;
    robot.click(table, pointAt(table, row, column));
  }

  private boolean isCellSelected(JTable table, int row, int column) {
    return table.isRowSelected(row) && table.isColumnSelected(column) && table.getSelectedRowCount() == 1;
  }

  /**
   * Clicks the given cell, using the specified mouse button, the given number of times.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @param mouseButton the mouse button to use.
   * @param times the number of times to click the cell.
   */
  public void click(JTable table, int row, int column, MouseButton mouseButton, int times) {
    robot.click(table, pointAt(table, row, column), mouseButton, times);
  }

  /**
   * Starts a drag operation at the location of the given row and column.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   */
  public void drag(JTable table, int row, int column) {
    drag(table, pointAt(table, row, column));
  }

  /**
   * Ends a drag operation at the location of the given row and column.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   */
  public void drop(JTable table, int row, int column) {
    drop(table, pointAt(table, row, column));
  }

  /**
   * Converts the given row and column into a coordinate pair. It is assumed that the row and column indices are
   * in the <code>{@link JTable}</code>'s bounds.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @return the coordinates of the given row and column.
   */
  public Point pointAt(JTable table, int row, int column) {
    return location.pointAt(table, row, column);
  }
}
