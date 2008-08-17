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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.BooleanProvider;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JTableCell.cell;
import static org.fest.swing.testing.TestGroups.EDT_QUERY;

/**
 * Tests for <code>{@link JTableCellEditableQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_QUERY)
public class JTableCellEditableQueryTest {

  private JTable table;
  private JTableCell cell;

  @BeforeMethod public void setUp() {
    table = createMock(JTable.class);
    cell = cell(6, 8);
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class, groups = EDT_QUERY)
  public void shouldIndicateWhetherCellIsEditableOrNot(final boolean editable) {
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.isCellEditable(6, 8)).andReturn(editable);
      }

      protected void codeToTest() {
        assertThat(JTableCellEditableQuery.isCellEditable(table, cell)).isEqualTo(editable);
      }
    }.run();
  }
}
