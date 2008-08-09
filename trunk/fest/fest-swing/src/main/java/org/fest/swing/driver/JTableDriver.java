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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.CommonValidations.*;
import static org.fest.swing.driver.IsJTableCellEditableTask.isCellEditable;
import static org.fest.swing.driver.JTableCell.*;
import static org.fest.swing.query.GetJTableRowCountTask.rowCountOf;
import static org.fest.swing.query.IsComponentEnabledTask.isEnabled;
import static org.fest.swing.util.Arrays.assertEquals;
import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JTable}</code>. Unlike <code>JTableFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JTable}</code>s. This class is intended for internal
 * use only.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableDriver extends JComponentDriver {

  private static final String CONTENTS_PROPERTY = "contents";
  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTION_PROPERTY = "selection";
  private static final String VALUE_PROPERTY = "value";

  private final JTableLocation location = new JTableLocation();
  private JTableCellReader cellReader;
  private JTableCellWriter cellWriter;

  /**
   * Creates a new </code>{@link JTableDriver}</code>.
   * @param robot the robot to use to simulate user events.
   */
  public JTableDriver(Robot robot) {
    super(robot);
    cellReader(new BasicJTableCellReader());
    cellWriter(new BasicJTableCellWriter(robot));
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
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
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
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @see #cellReader(JTableCellReader)
   */
  public String value(JTable table, int row, int column) {
    validateRow(table, row);
    validateColumn(table, column);
    return cellReader.valueAt(table, row, column);
  }

  /**
   * Returns the font of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the font of the given table cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public Font font(JTable table, JTableCell cell) {
    validate(table, cell);
    return cellReader.fontAt(table, cell.row, cell.column);
  }

  /**
   * Returns the background color of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the background color of the given table cell.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public Color background(JTable table, JTableCell cell) {
    validate(table, cell);
    return cellReader.backgroundAt(table, cell.row, cell.column);
  }

  /**
   * Returns the foreground color of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the foreground color of the given table cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public Color foreground(JTable table, JTableCell cell) {
    validate(table, cell);
    return cellReader.foregroundAt(table, cell.row, cell.column);
  }

  /**
   * Selects the given cells of the <code>{@link JTable}</code>.
   * @param table the target <code>JTable</code>.
   * @param cells the cells to select.
   * @throws NullPointerException if <code>cells</code> is <code>null</code> or empty.
   * @throws IllegalArgumentException if <code>cells</code> is <code>null</code> or empty.
   * @throws NullPointerException if any element in <code>cells</code> is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices of any of the <code>cells</code> are out of bounds.
   */
  public void selectCells(final JTable table, final JTableCell[] cells) {
    if (cells == null) throw new NullPointerException("Array of table cells to select should not be null");
    if (Arrays.isEmpty(cells)) throw new IllegalArgumentException("Array of table cells to select should not be empty");
    new MultipleSelectionTemplate(robot) {
      int elementCount() {
        return cells.length;
      }

      void selectElement(int index) {
        selectCell(table, cells[index]);
      }
    }.multiSelect();
  }

  /**
   * Verifies that the <code>{@link JTable}</code> does not have any selection.
   * @param table the target <code>JTable</code>.
   * @throws AssertionError is the <code>JTable</code> has a selection.
   */
  public void requireNoSelection(JTable table) {
    if (table.getSelectedColumnCount() == 0 && table.getSelectedRowCount() == 0) return;
    String message = concat("[", propertyName(table, SELECTION_PROPERTY), "] expected no selection but was:<rows",
        format(table.getSelectedRows()), ", columns", format(table.getSelectedColumns()), ">");
    fail(message);
  }

  /**
   * Selects the given cell, if it is not selected already.
   * @param table the target <code>JTable</code>.
   * @param cell the cell to select.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public void selectCell(JTable table, JTableCell cell) {
    if (!isEnabled(table)) return;
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
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
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
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
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
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
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
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public Point pointAt(JTable table, JTableCell cell) {
    validate(table, cell);
    return location.pointAt(table, cell.row, cell.column);
  }

  /**
   * Asserts that the <code>String</code> representation of the cell values in the <code>{@link JTable}</code> is
   * equal to the given <code>String</code> array. This method uses this driver's
   * <code>{@link JTableCellReader}</code> to read the values of the table cells as <code>String</code>s.
   * @param table the target <code>JTable</code>.
   * @param contents the expected <code>String</code> representation of the cell values in the <code>JTable</code>.
   * @see #cellReader(JTableCellReader)
   */
  public void requireContents(JTable table, String[][] contents) {
    String[][] actual = contents(table);
    assertEquals(actual, contents, propertyName(table, CONTENTS_PROPERTY));
  }

  /**
   * Returns the <code>String</code> representation of the cells in the <code>{@link JTable}</code>, using this
   * driver's <code>{@link JTableCellReader}</code>.
   * @param table the target <code>JTable</code>.
   * @return the <code>String</code> representation of the cells in the <code>JTable</code>.
   * @see #cellReader(JTableCellReader)
   */
  public String[][] contents(JTable table) {
    int rowCount = rowCountOf(table);
    int columnCount = table.getColumnCount();
    String[][] contents = new String[rowCount][columnCount];
    for (int row = 0; row < rowCount; row++)
      for (int col = 0; col < columnCount; col++)
        contents[row][col] = cellReader.valueAt(table, row, col);
    return contents;
  }

  /**
   * Asserts that the value of the given cell is equal to the expected one.
   * @param table the target <code>JTable</code>.
   * @param cell the given table cell.
   * @param value the expected value.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the value of the given cell is not equal to the expected one.
   */
  public void requireCellValue(JTable table, JTableCell cell, String value) {
    validate(table, cell);
    assertThat(value(table, cell)).as(cellProperty(table, cell, VALUE_PROPERTY)).isEqualTo(value);
  }

  /**
   * Formats the name of a table cell property by concatenating the value obtained from
   * <code>{@link ComponentDriver#propertyName(java.awt.Component, String)}</code> with the coordinates of the given
   * cell.
   * @param table the target <code>JTable</code>.
   * @param cell the given table cell.
   * @param propertyName the name of a property.
   * @return the formatted name of a property from the given table cell.
   * @see ComponentDriver#propertyName(java.awt.Component, String)
   */
  public static String cellProperty(JTable table, JTableCell cell, String propertyName) {
    return concat(propertyName(table, propertyName), " - ", cell);
  }

  /**
   * Enters the given value in the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @param value the given value.
   * @throws AssertionError if the given <code>JTable</code> is not enabled.
   * @throws AssertionError if the given table cell is not editable.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this driver's <code>JTableCellValueReader</code> is unable to enter the given 
   * value.
   * @see #cellWriter(JTableCellWriter)
   */
  public void enterValueInCell(JTable table, JTableCell cell, String value) {
    requireEditable(table, cell);
    requireEnabled(table);
    cellWriter.enterValue(table, cell.row, cell.column, value);
  }

  /**
   * Asserts that the given table cell is editable.
   * @param table the target <code>JTable</code>.
   * @param cell the given table cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the given table cell is not editable.
   */
  public void requireEditable(JTable table, JTableCell cell) {
    requireEditableEqualTo(table, cell, true);
  }

  /**
   * Asserts that the given table cell is not editable.
   * @param table the target <code>JTable</code>.
   * @param cell the given table cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws AssertionError if the given table cell is editable.
   */
  public void requireNotEditable(JTable table, JTableCell cell) {
    requireEditableEqualTo(table, cell, false);
  }

  private void requireEditableEqualTo(JTable table, JTableCell cell, boolean editable) {
    validate(table, cell);
    boolean cellEditable = isCellEditable(table, cell);
    assertThat(cellEditable).as(cellProperty(table, cell, EDITABLE_PROPERTY)).isEqualTo(editable);
  }

  /**
   * Returns the editor in the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @return the editor in the given cell of the <code>JTable</code>.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @see #cellWriter(JTableCellWriter)
   */
  public Component cellEditor(JTable table, JTableCell cell) {
    validate(table, cell);
    return cellWriter.editorForCell(table, cell.row, cell.column);
  }

  /**
   * Starts editing the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>. This method should be called before manipulating the
   * <code>{@link Component}</code> returned by <code>{@link #cellEditor(JTable, JTableCell)}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #cellWriter(JTableCellWriter)
   */
  public void startCellEditing(JTable table, JTableCell cell) {
    validate(table, cell);
    cellWriter.startCellEditing(table, cell.row, cell.column);
  }
  
  /**
   * Stops editing the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>. This method should be called after manipulating the
   * <code>{@link Component}</code> returned by <code>{@link #cellEditor(JTable, JTableCell)}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #cellWriter(JTableCellWriter)
   */
  public void stopCellEditing(JTable table, JTableCell cell) {
    validate(table, cell);
    cellWriter.stopCellEditing(table, cell.row, cell.column);
  }
  
  /**
   * Cancels editing the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>. This method should be called after manipulating the
   * <code>{@link Component}</code> returned by <code>{@link #cellEditor(JTable, JTableCell)}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #cellWriter(JTableCellWriter)
   */
  public void cancelCellEditing(JTable table, JTableCell cell) {
    validate(table, cell);
    cellWriter.cancelCellEditing(table, cell.row, cell.column);
  }
  
  /**
   * Validates that the given table cell is non <code>null</code> and its indices are not out of bounds.
   * @param table the target <code>JTable</code>.
   * @param cell to validate.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public void validate(JTable table, JTableCell cell) {
    if (cell == null) throw new NullPointerException("Table cell cannot be null");
    cell.validateBoundsIn(table);
  }

  /**
   * Updates the implementation of <code>{@link JTableCellReader}</code> to use when comparing internal values of a
   * <code>{@link JTable}</code> and the values expected in a test.
   * @param newCellReader the new <code>JTableCellValueReader</code> to use.
   * @throws NullPointerException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(JTableCellReader newCellReader) {
    validateCellReader(newCellReader);
    cellReader = newCellReader;
  }

  /**
   * Updates the implementation of <code>{@link JTableCellWriter}</code> to use to edit cell values in a
   * <code>{@link JTable}</code>.
   * @param newCellWriter the new <code>JTableCellWriter</code> to use.
   * @throws NullPointerException if <code>newCellWriter</code> is <code>null</code>.
   */
  public void cellWriter(JTableCellWriter newCellWriter) {
    validateCellWriter(newCellWriter);
    cellWriter = newCellWriter;
  }
}
