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
import javax.swing.table.JTableHeader;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellByColumnName;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.driver.CommonValidations.*;
import static org.fest.swing.driver.JTableCellEditableQuery.isCellEditable;
import static org.fest.swing.driver.JTableCellValidator.*;
import static org.fest.swing.driver.JTableColumnByIdentifierQuery.columnIndexByIdentifier;
import static org.fest.swing.driver.JTableContentsQuery.tableContents;
import static org.fest.swing.driver.JTableHasSelectionQuery.hasSelection;
import static org.fest.swing.driver.JTableHeaderQuery.tableHeader;
import static org.fest.swing.driver.JTableMatchingCellQuery.cellWithValue;
import static org.fest.swing.driver.JTableSingleRowCellSelectedQuery.isCellSelected;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.swing.util.Arrays.equal;
import static org.fest.util.Arrays.*;
import static org.fest.util.Strings.*;

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
   * Returns the <code>{@link JTableHeader}</code> of the given <code>{@link JTable}</code>.
   * @param table the given <code>JTable</code>.
   * @return the <code>JTableHeader</code> of the given <code>JTable</code>.
   */
  @RunsInEDT
  public JTableHeader tableHeaderOf(JTable table) {
    return tableHeader(table);
  }

  /**
   * Returns the <code>String</code> representation of the value of the selected cell, using this driver's
   * <code>{@link JTableCellReader}</code>.
   * @param table the target <code>JTable</code>.
   * @return the <code>String</code> representation of the value of the selected cell.
   * @see #cellReader(JTableCellReader)
   */
  @RunsInEDT
  public String selectionValue(JTable table) {
    return selectionValue(table, cellReader);
  }
  
  @RunsInEDT
  private static String selectionValue(final JTable table, final JTableCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        if (table.getSelectedRowCount() == 0) return null;
        return cellReader.valueAt(table, table.getSelectedRow(), table.getSelectedColumn());
      }
    });
  }

  /**
   * Returns a cell from the given <code>{@link JTable}</code> whose row index matches the given one and column name
   * matches the given one.
   * @param table the target <code>JTable</code>.
   * @param cell contains the given row index and column name to match.
   * @return a cell from the given <code>JTable</code> whose row index matches the given one and column name
   * matches the given one.
   * @throws NullPointerException if <code>cellByColumnName</code> is <code>null</code>.
   * @throws IndexOutOfBoundsException if the row index in the given cell is out of bounds.
   * @throws ActionFailedException if a column with a matching name could not be found.
   */
  @RunsInEDT
  public TableCell cell(JTable table, TableCellByColumnName cell) {
    if (cell == null)
      throw new NullPointerException("The instance of TableCellByColumnName should not be null");
    return findCell(table, cell.row, cell.columnName);
  }

  @RunsInEDT
  private static TableCell findCell(final JTable table, final int row, final Object columnName) {
    return execute(new GuiQuery<TableCell>() {
      protected TableCell executeInEDT() {
        validateRowIndex(table, row);
        int column = columnIndexByIdentifier(table, columnName);
        if (column < 0) failColumnIndexNotFound(columnName);
        return row(row).column(column);
      }
    });
  }

  /**
   * Returns a cell from the given <code>{@link JTable}</code> whose value matches the given one.
   * @param table the target <code>JTable</code>.
   * @param value the value of the cell to look for.
   * @return a cell from the given <code>JTable</code> whose value matches the given one.
   * @throws ActionFailedException if a cell with a matching value cannot be found.
   */
  @RunsInEDT
  public TableCell cell(JTable table, String value) {
    return cellWithValue(table, value, cellReader);
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
  @RunsInEDT
  public String value(JTable table, TableCell cell) {
    validateNotNull(cell);
    return cellValue(table, cell, cellReader);
  }

  @RunsInEDT
  private static String cellValue(final JTable table, final TableCell cell, final JTableCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        validateCellIndices(table, cell);
        return cellReader.valueAt(table, cell.row, cell.column);
      }
    });
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
  @RunsInEDT
  public String value(JTable table, int row, int column) {
    return cellValue(table, row, column, cellReader);
  }

  @RunsInEDT
  private static String cellValue(final JTable table, final int row, final int column, 
      final JTableCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        validateIndices(table, row, column);
        return cellReader.valueAt(table, row, column);
      }
    });
  }
  
  /**
   * Returns the font of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the font of the given table cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public Font font(JTable table, TableCell cell) {
    validateNotNull(cell);
    return cellFont(table, cell, cellReader);
  }

  @RunsInEDT
  private static Font cellFont(final JTable table, final TableCell cell, final JTableCellReader cellReader) {
    return execute(new GuiQuery<Font>() {
      protected Font executeInEDT() {
        validateCellIndices(table, cell);
        return cellReader.fontAt(table, cell.row, cell.column);
      }
    });
  }
  
  /**
   * Returns the background color of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the background color of the given table cell.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public Color background(JTable table, TableCell cell) {
    validateNotNull(cell);
    return cellBackground(table, cell, cellReader);
  }

  @RunsInEDT
  private static Color cellBackground(final JTable table, final TableCell cell, final JTableCellReader cellReader) {
    return execute(new GuiQuery<Color>() {
      protected Color executeInEDT() {
        validateCellIndices(table, cell);
        return cellReader.backgroundAt(table, cell.row, cell.column);
      }
    });
  }
  
  /**
   * Returns the foreground color of the given table cell.
   * @param table the target <code>JTable</code>.
   * @param cell the table cell.
   * @return the foreground color of the given table cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  public Color foreground(JTable table, TableCell cell) {
    validateNotNull(cell);
    return cellForeground(table, cell, cellReader);
  }

  @RunsInEDT
  private static Color cellForeground(final JTable table, final TableCell cell, final JTableCellReader cellReader) {
    return execute(new GuiQuery<Color>() {
      protected Color executeInEDT() {
        validateCellIndices(table, cell);
        return cellReader.foregroundAt(table, cell.row, cell.column);
      }
    });
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
  public void selectCells(final JTable table, final TableCell[] cells) {
    validateCellsToSelect(cells);
    new MultipleSelectionTemplate(robot) {
      int elementCount() {
        return cells.length;
      }

      void selectElement(int index) {
        selectCell(table, cells[index]);
      }
    }.multiSelect();
  }

  private void validateCellsToSelect(final TableCell[] cells) {
    if (cells == null) throw new NullPointerException("Array of table cells to select should not be null");
    if (isEmpty(cells)) throw new IllegalArgumentException("Array of table cells to select should not be empty");
  }

  /**
   * Verifies that the <code>{@link JTable}</code> does not have any selection.
   * @param table the target <code>JTable</code>.
   * @throws AssertionError is the <code>JTable</code> has a selection.
   */
  @RunsInEDT
  public void requireNoSelection(JTable table) {
    assertNoSelection(table);
  }

  @RunsInEDT
  private void assertNoSelection(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        if (hasSelection(table)) return;
        String message = concat("[", propertyName(table, SELECTION_PROPERTY).value(), 
            "] expected no selection but was:<rows=", format(table.getSelectedRows()), ", columns=", 
            format(table.getSelectedColumns()), ">");
        fail(message);
      }
    });
  }

  /**
   * Selects the given cell, if it is not selected already.
   * @param table the target <code>JTable</code>.
   * @param cell the cell to select.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public void selectCell(JTable table, TableCell cell) {
    if (!isEnabled(table)) return;
    validate(table, cell);
    if (isCellSelected(table, cell.row, cell.column)) return;
    click(table, cell, LEFT_BUTTON, 1);
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
  public void click(JTable table, TableCell cell, MouseButton mouseButton, int times) {
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
  public void drag(JTable table, TableCell cell) {
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
  public void drop(JTable table, TableCell cell) {
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
  public JPopupMenu showPopupMenuAt(JTable table, TableCell cell) {
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
  public Point pointAt(JTable table, TableCell cell) {
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
  @RunsInEDT
  public void requireContents(JTable table, String[][] contents) {
    String[][] actual = contents(table);
    if (!equal(actual, contents))
      failNotEqual(actual, contents, propertyName(table, CONTENTS_PROPERTY));
  }

  private static void failNotEqual(String[][] actual, String[][] expected, Description description) {
    String descriptionValue = description != null ? description.value() : null;
    String message = descriptionValue == null ? "" : concat("[", descriptionValue, "]");
    fail(concat(message, " expected:<", format(expected), "> but was:<", format(actual), ">"));
  }

  /**
   * Returns the <code>String</code> representation of the cells in the <code>{@link JTable}</code>, using this
   * driver's <code>{@link JTableCellReader}</code>.
   * @param table the target <code>JTable</code>.
   * @return the <code>String</code> representation of the cells in the <code>JTable</code>.
   * @see #cellReader(JTableCellReader)
   */
  @RunsInEDT
  public String[][] contents(JTable table) {
    return tableContents(table, cellReader);
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
  public void requireCellValue(JTable table, TableCell cell, String value) {
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
  public static String cellProperty(JTable table, TableCell cell, String propertyName) {
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
  public void enterValueInCell(JTable table, TableCell cell, String value) {
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
  public void requireEditable(JTable table, TableCell cell) {
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
  public void requireNotEditable(JTable table, TableCell cell) {
    requireEditableEqualTo(table, cell, false);
  }

  private void requireEditableEqualTo(JTable table, TableCell cell, boolean editable) {
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
  public Component cellEditor(JTable table, TableCell cell) {
    validate(table, cell);
    return cellWriter.editorForCell(table, cell.row, cell.column);
  }

  /**
   * Starts editing the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>. This method should be called before manipulating the
   * <code>{@link Component}</code> returned by <code>{@link #cellEditor(JTable, TableCell)}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #cellWriter(JTableCellWriter)
   */
  public void startCellEditing(JTable table, TableCell cell) {
    validate(table, cell);
    cellWriter.startCellEditing(table, cell.row, cell.column);
  }

  /**
   * Stops editing the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>. This method should be called after manipulating the
   * <code>{@link Component}</code> returned by <code>{@link #cellEditor(JTable, TableCell)}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #cellWriter(JTableCellWriter)
   */
  public void stopCellEditing(JTable table, TableCell cell) {
    validate(table, cell);
    cellWriter.stopCellEditing(table, cell.row, cell.column);
  }

  /**
   * Cancels editing the given cell of the <code>{@link JTable}</code>, using this driver's
   * <code>{@link JTableCellWriter}</code>. This method should be called after manipulating the
   * <code>{@link Component}</code> returned by <code>{@link #cellEditor(JTable, TableCell)}</code>.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   * @throws ActionFailedException if this writer is unable to handle the underlying cell editor.
   * @see #cellWriter(JTableCellWriter)
   */
  public void cancelCellEditing(JTable table, TableCell cell) {
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
  public void validate(JTable table, TableCell cell) {
    validateNotNull(cell);
    validateIndices(table, cell.row, cell.column);
  }
  
  private static void validateNotNull(TableCell cell) {
    if (cell == null) throw new NullPointerException("Table cell cannot be null");
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

  /**
   * Returns the number of rows that can be shown in the given <code>{@link JTable}</code>, given unlimited space.
   * @param table the target <code>JTable</code>.
   * @return the number of rows shown in the given <code>JTable</code>.
   * @see JTable#getRowCount()
   */
  public int rowCountOf(JTable table) {
    return JTableRowCountQuery.rowCountOf(table);
  }

  /**
   * Returns the index of the column in the given <code>{@link JTable}</code> whose name matches the given one.
   * @param table the target <code>JTable</code>.
   * @param columnName the name of the column to look for.
   * @return the index of the column whose name matches the given one.
   * @throws ActionFailedException if a column with a matching name could not be found.
   */
  @RunsInEDT
  public int columnIndex(JTable table, Object columnName) {
    return findColumnIndex(table, columnName);
  }

  @RunsInEDT
  private static int findColumnIndex(final JTable table, final Object columnName) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        int index = columnIndexByIdentifier(table, columnName);
        if (index < 0) failColumnIndexNotFound(columnName);
        return index;
      }
    });
  }

  private static ActionFailedException failColumnIndexNotFound(Object columnName) {
    throw actionFailure(concat("Unable to find a column with name ", quote(columnName)));
  }
}
