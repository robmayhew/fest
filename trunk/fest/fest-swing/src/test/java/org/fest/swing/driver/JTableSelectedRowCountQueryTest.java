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
import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link JTableSelectedRowCountQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = EDT_QUERY)
public class JTableSelectedRowCountQueryTest {

  private JTable table;
  private int selectedRowCount;
  
  @BeforeMethod public void setUp() {
    table = createMock(JTable.class);
    selectedRowCount = 8;
  }
  
  public void shouldReturnSelectedRowCountOfJTable() {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.getSelectedRowCount()).andReturn(selectedRowCount);
      }

      protected void codeToTest() {
        assertThat(JTableSelectedRowCountQuery.selectedRowCountOf(table)).isEqualTo(selectedRowCount);
      }
    }.run();
  }
}
