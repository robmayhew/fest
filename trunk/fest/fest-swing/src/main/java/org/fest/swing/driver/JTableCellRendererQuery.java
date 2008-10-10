/*
 * Created on Aug 7, 2008
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

import static org.fest.swing.core.GuiActionRunner.execute;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Component}</code> used as
 * list renderer for a particular cell in a <code>{@link JTable}</code>.
 * @see JTable#getCellRenderer(int, int)
 * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTableCellRendererQuery {

  static Component cellRendererIn(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        Object value = table.getValueAt(row, column);
        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
        boolean cellSelected = table.isCellSelected(row, column);
        return cellRenderer.getTableCellRendererComponent(table, value, cellSelected, false, row, column);
      }
    });
  }

  private JTableCellRendererQuery() {}
}