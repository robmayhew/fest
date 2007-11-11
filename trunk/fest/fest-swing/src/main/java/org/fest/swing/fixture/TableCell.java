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

import static org.fest.util.Strings.concat;

import javax.swing.*;

/**
 * Understands a cell in a <code>{@link JTable}</code>.
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
    
    /**
     * Starting point for the creation of a <code>{@link TableCell}</code>.
     * <p>
     * Example:
     * <pre>
     * // import static org.fest.swing.fixture.TableCell.TableCellBuilder.*;
     * 
     * TableCell cell = row(5).column(3);
     * </pre>
     * </p>
     * @param row the row index of the table cell to create.
     * @return the created builder.
     */
    public static TableCellBuilder row(int row) {
      return new TableCellBuilder(row);
    }

    private final int row;
    
    private TableCellBuilder(int row) {
      this.row = row;
    }
    
    /**
     * Creates a new table cell using the row index specified in <code>{@link TableCellBuilder#row(int)}</code> and the 
     * column index specified as the argument in this method. 
     * @param column the column index of the table cell to create.
     * @return the created table cell.
     */
    public TableCell column(int column) {
      return new TableCell(row, column);
    }
  }
  
  /** The row of the cell. */
  public final int row;
  
  /** The column of the cell. */
  public final int column;

  private TableCell(int row, int column) {
    this.row = row;
    this.column = column;
  }

  void validateBoundsIn(JTable table) {
    int rowCount = table.getRowCount();
    if (rowCount == 0) throw new IllegalStateException("Table does not contain any rows");
    validateIndex(row, rowCount, "row");
    validateIndex(column, table.getColumnCount(), "column");
  }
  
  private void validateIndex(int index, int elementCount, String indexName) {
    if (index < 0) throw negativeIndex(indexName);
    if (index >= elementCount) throw indexExceedsMaximum(indexName, elementCount);
  }
  
  private IllegalArgumentException negativeIndex(String indexName) {
    throw new IllegalArgumentException(concat(indexName, " index cannot be a negative number"));
  }
  
  private IndexOutOfBoundsException indexExceedsMaximum(String indexName, int max) {
    throw new IndexOutOfBoundsException(concat(indexName, " index should be less than ", String.valueOf(max)));
  }
}