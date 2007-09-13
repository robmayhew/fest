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

/**
 * Understands a cell in a <code>{@link JTable}</code>.
 * 
 * @author Alex Ruiz
 */
public class TableCell {
  /**
   * Creates a new representation of a <code>{@link JTable}</code> cell.
   * @param row the row of the cell.
   * @param column the column of the cell.
   * @return the created cell.
   */
  public static TableCell cell(int row, int column) {
    return new TableCell(row, column);
  }
  
  public final int row;
  public final int column;

  /**
   * Creates a new </code>{@link TableCell}</code>.
   * @param row the row of the cell.
   * @param column the column of the cell.
   */
  private TableCell(int row, int column) {
    this.row = row;
    this.column = column;
  }
}