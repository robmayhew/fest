/*
 * Created on Aug 6, 2008
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
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a cell in a
 * <code>{@link JTable}</code> is editable or not.
 * @see JTable#isCellEditable(int, int)
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTableCellEditableQuery {

  static boolean isCellEditable(final JTable table, final TableCell cell) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return table.isCellEditable(cell.row, cell.column);
      }
    });
  }

  private JTableCellEditableQuery() {}
}