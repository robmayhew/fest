/*
 * Created on Aug 10, 2008
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

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a cell in a 
 * <code>{@link JTable}</code> is selected or not.
 * 
 * @author Alex Ruiz
 */
class JTableSingleRowCellSelectedQuery extends GuiQuery<Boolean> {

  private final JTable table;
  private final int row;
  private final int column;

  static boolean isCellSelected(JTable table, int row, int column) {
    return new JTableSingleRowCellSelectedQuery(table, row, column).run();
  }
  
  JTableSingleRowCellSelectedQuery(JTable table, int row, int column) {
    this.table = table;
    this.row = row;
    this.column = column;
  }
  
  protected Boolean executeInEDT() throws Throwable {
    return table.isRowSelected(row) && table.isColumnSelected(column) && table.getSelectedRowCount() == 1;
  }
}
