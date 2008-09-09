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

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a <code>{@link JTable}</code> 
 * has any selection or not.
 *
 * @author Alex Ruiz 
 */
class JTableSelectionQuery extends GuiQuery<Boolean> {

  private final JTable table;

  static boolean hasSelection(JTable table) {
    return execute(new JTableSelectionQuery(table));
  }
  
  JTableSelectionQuery(JTable table) {
    this.table = table;
  }
  
  protected Boolean executeInEDT() {
    return table.getSelectedRowCount() > 0 || table.getSelectedColumnCount() > 0;
  }

}
