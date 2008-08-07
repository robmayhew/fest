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

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link GetJTableCellEditorComponentTask}</code>.
 *
 * @author Alex Ruiz 
 */
@Test public class GetJTableCellEditorComponentTaskTest {

  private static final int ROW = 6;
  private static final int COLUMN = 8;

  public void shouldReturnEditorComponentOfJTableCell() {
    final JTable table = createMockTable();
    final TableCellEditor cellEditor = createMock(TableCellEditor.class);
    final Component editor = createMock(Component.class);
    new EasyMockTemplate(table, cellEditor) {
      protected void expectations() {
        Object cellValue = "Hello";
        expect(table.getCellEditor(ROW, COLUMN)).andReturn(cellEditor);
        expect(table.getValueAt(ROW, COLUMN)).andReturn(cellValue);
        expect(cellEditor.getTableCellEditorComponent(table, cellValue, false, ROW, COLUMN)).andReturn(editor);
      }

      protected void codeToTest() {
        assertThat(GetJTableCellEditorComponentTask.cellEditorComponentOf(table, ROW, COLUMN)).isSameAs(editor);
      }
    }.run();
  }

  public void shouldReturnNullEditorComponentIfCellEditorIsNull() {
    final JTable table = createMockTable();
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.getCellEditor(ROW, COLUMN)).andReturn(null);
      }

      protected void codeToTest() {
        assertThat(GetJTableCellEditorComponentTask.cellEditorComponentOf(table, ROW, COLUMN)).isNull();
      }
    }.run();
  }

  private JTable createMockTable() {
    return createMock(JTable.class);
  }

}
