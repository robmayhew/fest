/*
 * Created on Jun 10, 2008
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
import javax.swing.table.TableCellEditor;

/**
 * Understands a task that cancels editing of a cell in a <code>{@link JTable}</code>.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
class CancelTableCellEditingTask implements Runnable {
  
  private final JTable table;
  private final int row;
  private final int column;

  CancelTableCellEditingTask(JTable table, int row, int column) {
    this.table = table;
    this.row = row;
    this.column = column;
  }

  public void run() {
    TableCellEditor cellEditor = table.getCellEditor(row, column);
    if (cellEditor == null) return;
    cellEditor.cancelCellEditing();
  }
}