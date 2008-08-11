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

import static org.fest.swing.driver.JTableCell.cell;

/**
 * Understands an action, executed in the event dispatch thread, that returns the indices of the selected row and column 
 * in a <code>{@link JTable}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JTableSelectedCellQuery extends GuiQuery<JTableCell> {

  private final JTable table;

  static JTableCell selectedCellOf(JTable table) {
    return new JTableSelectedCellQuery(table).run();
  }
  
  private JTableSelectedCellQuery(JTable table) {
    this.table = table;
  }
  
  protected JTableCell executeInEDT() throws Throwable {
    return cell(table.getSelectedRow(), table.getSelectedColumn());
  }
}
