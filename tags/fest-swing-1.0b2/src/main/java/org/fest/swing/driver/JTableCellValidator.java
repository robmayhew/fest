/*
 * Created on Oct 13, 2008
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

import javax.swing.JTable;

import org.fest.swing.data.TableCell;

import static java.lang.String.valueOf;

import static org.fest.swing.driver.JTableColumnCountQuery.columnCountOf;
import static org.fest.swing.driver.JTableRowCountQuery.rowCountOf;
import static org.fest.util.Strings.*;

/**
 * Understands validation of <code>{@link JTable}</code>-related information.
 *
 * @author Alex Ruiz
 */
public final class JTableCellValidator {

  /**
   * Validates that the given table cell is non <code>null</code> and its indices are not out of bounds.
   * @param table the target <code>JTable</code>.
   * @param cell to validate.
   * @throws NullPointerException if the cell is <code>null</code>.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  public static void validateCell(JTable table, TableCell cell) {
    if (cell == null) throw new NullPointerException("Table cell cannot be null");
    validateBounds(table, cell.row, cell.column);
  }

  /**
   * Validates the given indices regarding the given table.
   * @param table the <code>JTable</code> to use to validate the given indices.
   * @param row the row index to validate.
   * @param column the column index to validate.
   * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the <code>JTable</code> does not
   * have any rows.
   */
  public static void validateBounds(JTable table, int row, int column) {
    if (rowCountOf(table) == 0) throw new IndexOutOfBoundsException("Table does not contain any rows");
    validateRow(table, row);
    validateColumn(table, column);
  }

  /**
   * Validates that the given row index exists in the given table.
   * @param table the table the given table.
   * @param row the row to validate.
   * @throws IndexOutOfBoundsException if the row index is out of bounds.
   */
  public static void validateRow(JTable table, int row) {
    validateIndex(row, rowCountOf(table), "row");
  }

  /**
   * Validates that the given column index exists in the given table.
   * @param table the table the given table.
   * @param column the column to validate.
   * @throws IndexOutOfBoundsException if the column index is out of bounds.
   */
  public static void validateColumn(JTable table, int column) {
    validateIndex(column, columnCountOf(table), "column");
  }

  private static void validateIndex(int index, int itemCount, String indexName) {
    if (index >= 0 && index < itemCount) return;
    throw new IndexOutOfBoundsException(concat(
        indexName, " ", quote(valueOf(index)), " should be between ", quote("0"), " and ",
        quote(valueOf(itemCount))));
  }

  private JTableCellValidator() {}
}
