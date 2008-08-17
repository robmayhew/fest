/*
 * Created on Jul 22, 2008
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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link JTableCancelCellEditingTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class CancelTableCellEditingTaskTest {

  private JTable table;
  private int row;
  private int column;
  private JTableCancelCellEditingTask task;
  
  @BeforeMethod public void setUp() {
    table = createMock(JTable.class);
    row = 6;
    column = 8;
    task = JTableCancelCellEditingTask.cancelCellEditingTask(table, row, column);
  }
  
  public void shouldCancelCellEditingIfTableCellHasEditor() {
    final TableCellEditor editor = createMock(TableCellEditor.class);
    new EasyMockTemplate(table, editor) {
      protected void expectations() {
        expect(table.getCellEditor(row, column)).andReturn(editor);
        editor.cancelCellEditing();
      }

      protected void codeToTest() {
        task.executeInEDT();
      }
    }.run();
  }

  public void shouldNotCancelCellEditingIfTableCellHasNoEditor() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.getCellEditor(row, column)).andReturn(null);
      }

      protected void codeToTest() {
        task.executeInEDT();
      }
    }.run();
  }
}
