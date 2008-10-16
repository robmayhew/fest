/*
 * Created on Oct 13, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the index of a column in a
 * <code>{@link JTable}</code> whose identifier matches the given one.
 * @see JTable#getColumn(Object)
 * @see TableColumn#getModelIndex()
 * 
 * @author Alex Ruiz
 */
final class JTableColumnByIdentifierQuery {

  static int columnIndexByIdentifier(final JTable table, final Object identifier) {
    return GuiActionRunner.execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        try {
          TableColumn column = table.getColumn(identifier);
          return column.getModelIndex();
        } catch (IllegalArgumentException e) {
          return -1;
        }
      }
    });
  }
  
  private JTableColumnByIdentifierQuery() {}
}
