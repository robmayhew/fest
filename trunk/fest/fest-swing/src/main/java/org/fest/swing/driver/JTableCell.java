/*
 * Created on Mar 2, 2008
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

import org.fest.swing.core.GuiQuery;

import static java.lang.String.valueOf;

import static org.fest.swing.driver.JTableRowCountQuery.rowCountOf;
import static org.fest.util.Strings.*;

/**
 * Understands a cell in a <code>{@link JTable}</code>..
 *
 * @author Alex Ruiz
 */
public abstract class JTableCell {

  /** The row of the cell. */
  public final int row;

  /** The column of the cell. */
  public final int column;

  // to be used in tests
  static JTableCell cell(int row, int column) {
    return new JTableCell(row, column) {};
  }

  /**
   * Creates a new </code>{@link JTableCell}</code>.
   * @param row the row of the cell.
   * @param column the column of the cell.
   */
  public JTableCell(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Validates the indices of this cell regarding the given table.
   * @param table the table to use to validate this cell's indices.
   * @throws IndexOutOfBoundsException if any of the indices is out of bounds or if the <code>JTable</code> does not
   * have any rows.
   */
  public void validateBoundsIn(JTable table) {
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
  protected static void validateRow(JTable table, int row) {
    validateIndex(row, rowCountOf(table), "row");
  }

  /**
   * Validates that the given column index exists in the given table.
   * @param table the table the given table.
   * @param column the column to validate.
   * @throws IndexOutOfBoundsException if the column index is out of bounds.
   */
  protected static void validateColumn(JTable table, int column) {
    validateIndex(column, columnCountOf(table), "column");
  }

  private static int columnCountOf(JTable table) {
    return new GetColumnCountTask(table).run();
  }

  private static class GetColumnCountTask extends GuiQuery<Integer> {
    private final JTable table;

    GetColumnCountTask(JTable table) {
      this.table = table;
    }

    protected Integer executeInEDT() throws Throwable {
      return table.getColumnCount();
    }
  }

  private static void validateIndex(int index, int itemCount, String indexName) {
    if (index >= 0 && index < itemCount) return;
    throw new IndexOutOfBoundsException(concat(
        indexName, " ", quote(valueOf(index)), " should be between ", quote("0"), " and ",
        quote(valueOf(itemCount))));
  }

  @Override public String toString() {
    return concat("[row=", valueOf(row), ", column=", valueOf(column), "]");
  }
}
