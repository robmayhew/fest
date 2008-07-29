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
package org.fest.swing.task;

import javax.swing.JTable;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that returns the value in a <code>{@link JTable}</code> cell.
 *
 * @author Yvonne Wang
 */
public final class GetJTableCellValueTask extends GuiTask<Object> {

  private final JTable table;
  private final int row;
  private final int column;

  /**
   * Returns the value in the given <code>{@link JTable}</code> cell. This action is executed in the event dispatch
   * thread.
   * @param table the given <code>JTable</code>.
   * @param row the given row coordinate.
   * @param column the given column coordinate.
   * @return the value in the given <code>JTable</code> cell.
   */
  public static Object cellValueOf(JTable table, int row, int column) {
    return new GetJTableCellValueTask(table, row, column);
  }

  private GetJTableCellValueTask(JTable table, int row, int column) {
    this.row = row;
    this.table = table;
    this.column = column;
  }

  /**
   * Returns the value in this task's <code>{@link JTable}</code>, in this task's cell coordinates. This action is
   * executed in the event dispatch thread.
   */
  protected Object executeInEDT() {
    return table.getValueAt(row, column);
  }
}