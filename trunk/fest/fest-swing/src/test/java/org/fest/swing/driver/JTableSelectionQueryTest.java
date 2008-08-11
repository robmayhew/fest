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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JTableSelectionQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JTableSelectionQueryTest {

  private JTable table;

  @BeforeMethod public void setUp() {
    table = createMock(JTable.class);
  }
  
  public void shouldIndicateNoSelectionIfSelectedRowAndColumnCountsIsZero() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.getSelectedRowCount()).andReturn(0);
        expect(table.getSelectedColumnCount()).andReturn(0);
      }

      protected void codeToTest() {
        assertThat(JTableSelectionQuery.hasSelection(table)).isFalse();
      }
    }.run();
  }

  public void shouldIndicateSelectionIfSelectedRowCountIsNotZero() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.getSelectedRowCount()).andReturn(1);
      }

      protected void codeToTest() {
        assertThat(JTableSelectionQuery.hasSelection(table)).isTrue();
      }
    }.run();
  }

  public void shouldIndicateSelectionIfSelectedRowCountIsZeroAndSelectedColumnCountIsNotZero() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.getSelectedRowCount()).andReturn(0);
        expect(table.getSelectedColumnCount()).andReturn(1);
      }

      protected void codeToTest() {
        assertThat(JTableSelectionQuery.hasSelection(table)).isTrue();
      }
    }.run();
  }
}
