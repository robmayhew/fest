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

import java.awt.Font;
import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.fest.swing.cell.BasicJTableCellReader;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
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
  private JTableCellReader cellReader;

  /**
   * Creates a new </code>{@link JTableDriver}</code>.
   * @param robot the robot to use to simulate user events.
   */
  public JTableDriver(Robot robot) {
    super(robot);
    cellReader(new BasicJTableCellReader());
  }

  /**
   * Returns the <code>String</code> representation of the value of the selected cell, using this driver's
   * <code>{@link JTableCellReader}</code>.
   * @param table the target <code>JTable</code>.
   * @return the <code>String</code> representation of the value of the selected cell.
   * @see #cellReader(JTableCellReader)
   */
  public String selectionValue(JTable table) {
    if (table.getSelectedRowCount() == 0) return null;
    return value(table, table.getSelectedRow(), table.getSelectedColumn());
  }

  /**
   * Returns the <code>String</code> representation of the value at the given cell, using this driver's
   * <code>{@link JTableCellReader}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the <code>String</code> representation of the value at the given cell.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   * @see #cellReader(JTableCellReader)
   */
  public String value(JTable table, JTableCell cell) {
    validate(table, cell);
    return value(table, cell.row, cell.column);
  }

  /**
   * Returns the <code>String</code> representation of the value at the given row and column, using this driver's
   * <code>{@link JTableCellReader}</code>.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @return the <code>String</code> representation of the value at the given row and column.
   * @see #cellReader(JTableCellReader)
   */
  public String value(JTable table, int row, int column) {
    return cellReader.valueAt(table, row, column);
  }

  /**
   * Returns the font of the given table cell. 
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the font of the given table cell.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public Font font(JTable table, JTableCell cell) {
    validate(table, cell);
    return cellReader.fontAt(table, cell.row, cell.column);
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
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public void selectCell(JTable table, JTableCell cell) {
    validate(table, cell);
    if (isCellSelected(table, cell)) return;
    click(table, cell, LEFT_BUTTON, 1);
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
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public void click(JTable table, JTableCell cell, MouseButton mouseButton, int times) {
    validate(table, cell);
    scrollToVisible(table, location.cellBounds(table, cell));
    robot.click(table, pointAt(table, cell), mouseButton, times);
  }

  /**
   * Starts a drag operation at the location of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public void drag(JTable table, JTableCell cell) {
    validate(table, cell);
    scrollToVisible(table, location.cellBounds(table, cell));
    drag(table, pointAt(table, cell));
  }

  /**
   * Starts a drop operation at the location of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public void drop(JTable table, JTableCell cell) {
    validate(table, cell);
    scrollToVisible(table, location.cellBounds(table, cell));
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
    scrollToVisible(table, location.cellBounds(table, cell));
    return robot.showPopupMenu(table, pointAt(table, cell));
  }

  /**
   * Converts the given table cell into a coordinate pair.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the coordinates of the given row and column.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public Point pointAt(JTable table, JTableCell cell) {
    validate(table, cell);
    return location.pointAt(table, cell.row, cell.column);
  }

  /**
   * Validates that the given table cell is non <code>null</code> and its indices are not out of bounds.
   * @param table the target <code>JTable</code>.
   * @param cell to validate.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public void validate(JTable table, JTableCell cell) {
    if (cell == null) throw actionFailure("Table cell cannot be null");
    cell.validateBoundsIn(table);
  }

  /**
   * Updates the implementation of <code>{@link JTableCellReader}</code> to use when comparing internal values of a
   * <code>{@link JTable}</code> and the values expected in a test.
   * @param cellReader the new <code>JTableCellValueReader</code> to use.
   */
  public void cellReader(JTableCellReader cellReader) {
    this.cellReader = cellReader;
  }
}
