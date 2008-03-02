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

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.swing.driver.CellRendererComponents.textFrom;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.Platform.controlOrCommandKey;

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
  public JTableDriver(Robot robot) {
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
   * Convert the value at the given table cell into a reasonable <code>String</code> representation, or
   * <code>null</code> if one cannot be obtained.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return a <code>String</code> representation of the the value at the given row and column, or <code>null</code>
   *         if one cannot be obtained.
   * @throw ActionFailedException if the cell is <code>null</code>. 
   * @throw ActionFailedException if any of the indices (row and column) is out of bounds. 
   */
  public String text(JTable table, JTableCell cell) {
    validate(table, cell);
    return textFrom(cellRendererComponent(table, cell.row, cell.column));
  }

  /**
   * Convert the value at the given row and column into a reasonable <code>String</code> representation, or
   * <code>null</code> if one cannot be obtained.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @return a <code>String</code> representation of the the value at the given row and column, or <code>null</code>
   *         if one cannot be obtained.
   */
  public String text(JTable table, int row, int column) {
    return textFrom(cellRendererComponent(table, row, column));
  }

  private static Component cellRendererComponent(JTable table, int row, int col) {
    Object value = table.getValueAt(row, col);
    return table.getCellRenderer(row, col).getTableCellRendererComponent(table, value, false, false, row, col);
  }

  /**
   * Selects the given cells of the <code>{@link JTable}</code>.
   * @param table the target <code>JTable</code>.
   * @param cells the cells to select.
   * @throws ActionFailedException if <code>cells</code> is <code>null</code>.
   * @throws ActionFailedException if any element in <code>cells</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of any of the <code>cells</code> are out of bounds.
   */
  public void selectCells(JTable table, JTableCell... cells) {
    if (cells == null) throw actionFailure("Table cells to select cannot be null");
    int controlOrCommandKey = controlOrCommandKey();
    pressKey(table, controlOrCommandKey);
    for (JTableCell c : cells) selectCell(table, c);
    releaseKey(table, controlOrCommandKey);
  }

  /**
   * Selects the given cell, if it is not selected already.
   * @param table the target <code>JTable</code>.
   * @param cell the cell to select.
   * @throw ActionFailedException if the cell is <code>null</code>. 
   * @throw ActionFailedException if any of the indices (row and column) is out of bounds. 
   */
  public void selectCell(JTable table, JTableCell cell) {
    validate(table, cell);
    if (isCellSelected(table, cell)) return;
    click(table, pointAt(table, cell));
  }
  
  private boolean isCellSelected(JTable table, JTableCell cell) {
    return table.isRowSelected(cell.row) && table.isColumnSelected(cell.column) && table.getSelectedRowCount() == 1;
  }

  /**
   * Clicks the given cell, using the specified mouse button, the given number of times.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @param mouseButton the mouse button to use.
   * @param times the number of times to click the cell.
   * @throw ActionFailedException if the cell is <code>null</code>. 
   * @throw ActionFailedException if any of the indices (row and column) is out of bounds. 
   */
  public void click(JTable table, JTableCell cell, MouseButton mouseButton, int times) {
    validate(table, cell);
    robot.click(table, pointAt(table, cell), mouseButton, times);
  }

  /**
   * Starts a drag operation at the location of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @throw ActionFailedException if the cell is <code>null</code>. 
   * @throw ActionFailedException if any of the indices (row and column) is out of bounds. 
   */
  public void drag(JTable table, JTableCell cell) {
    validate(table, cell);
    drag(table, pointAt(table, cell));
  }

  /**
   * Starts a drop operation at the location of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @throw ActionFailedException if the cell is <code>null</code>. 
   * @throw ActionFailedException if any of the indices (row and column) is out of bounds. 
   */
  public void drop(JTable table, JTableCell cell) {
    validate(table, cell);
    drop(table, pointAt(table, cell));
  }

  /**
   * Shows a pop-up menu at the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenu showPopupMenuAt(JTable table, JTableCell cell) {
    return showPopupMenu(table, pointAt(table, cell));
  }

  /**
   * Converts the given table cell into a coordinate pair.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the coordinates of the given row and column.
   * @throw ActionFailedException if the cell is <code>null</code>. 
   * @throw ActionFailedException if any of the indices (row and column) is out of bounds. 
   */
  public Point pointAt(JTable table, JTableCell cell) {
    validate(table, cell);
    return location.pointAt(table, cell.row, cell.column);
  }

  /**
   * Validates that the given table cell is non <code>null</code> and its indices are not out of bounds.
   * @param table the target <code>JTable</code>.
   * @param cell to validate.
   * @throw ActionFailedException if the cell is <code>null</code>. 
   * @throw ActionFailedException if any of the indices (row and column) is out of bounds. 
   */
  public void validate(JTable table, JTableCell cell) {
    if (cell == null) throw actionFailure("Table cell cannot be null");
    cell.validateBoundsIn(table);
  }
}
