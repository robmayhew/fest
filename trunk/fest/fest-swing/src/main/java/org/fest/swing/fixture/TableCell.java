/*
 * Created on Sep 12, 2007
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
package org.fest.swing.fixture;

import javax.swing.JTable;

import static java.lang.String.valueOf;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import org.fest.swing.exception.ActionFailedException;

/**
 * Understands a cell in a <code>{@link JTable}</code>. Intended for creation of <code>{@link JTableCellFixture}</code>s 
 * only.
 * <p>
 * Example:
 * <pre>
 * // import static org.fest.swing.fixture.TableCellBuilder.row;
 * {@link JTableCellFixture} cell = dialog.{@link JTableFixture table}("records").cell({@link TableCell.TableCellBuilder#row(int) row}(3).column(0));
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 */
public class TableCell {

  /**
   * Understands creation of <code>{@link TableCell}</code>s.
   *
   * @author Alex Ruiz
   */
  public static class TableCellBuilder {
    private final int row;
    
    /**
     * Starting point for the creation of a <code>{@link TableCell}</code>.
     * <p>
     * Example:
     * <pre>
     * // import static org.fest.swing.fixture.TableCell.TableCellBuilder.row;
     * TableCell cell = row(5).column(3);
     * </pre>
     * </p>
     * @param row the row index of the table cell to create.
     * @return the created builder.
     */
    public static TableCellBuilder row(int row) { return new TableCellBuilder(row); }
   
    private TableCellBuilder(int row) { this.row = row; }
    
    /**
     * Creates a new table cell using the row index specified in <code>{@link TableCellBuilder#row(int)}</code> and the 
     * column index specified as the argument in this method. 
     * @param column the column index of the table cell to create.
     * @return the created table cell.
     */
    public TableCell column(int column) { return new TableCell(row, column); }
  }
  
  /** The row of the cell. */
  public final int row;
  
  /** The column of the cell. */
  public final int column;

  private TableCell(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Validates the indices of this cell regarding the given table.
   * @param table the table to use to validate this cell's indices.
   * @throw ActionFailedException if any of the indices is out of bounds. 
   */
  void validateBoundsIn(JTable table) {
    int rowCount = table.getRowCount();
    if (rowCount == 0) throw new IndexOutOfBoundsException("Table does not contain any rows");
    validateIndex(row, rowCount, "row");
    validateIndex(column, table.getColumnCount(), "column");
  }
  
  private void validateIndex(int index, int itemCount, String indexName) {
    if (index < 0 || index >= itemCount) 
      throw new ActionFailedException(concat(
          indexName, " ", quote(valueOf(index)), " should be between ", quote(valueOf(0)), " and ", quote(valueOf(itemCount))));
  }
}