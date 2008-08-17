/*
 * Created on Jul 29, 2008
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

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the value in a <code>{@link JTable}</code> 
 * cell.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JTableCellValueQuery extends GuiQuery<Object> {

  private final JTable table;
  private final int row;
  private final int column;

  static Object cellValueOf(JTable table, int row, int column) {
    return execute(new JTableCellValueQuery(table, row, column));
  }

  private JTableCellValueQuery(JTable table, int row, int column) {
    this.table = table;
    this.row = row;
    this.column = column;
  }

  protected Object executeInEDT() {
    return table.getValueAt(row, column);
  }
}