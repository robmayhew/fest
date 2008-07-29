/*
 * Created on Jun 10, 2008
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
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * Tests for <code>{@link AbstractJTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class AbstractJTableCellWriterTest {

  private Robot robot;
  private AbstractJTableCellWriter writer;

  @BeforeMethod public void setUp() {
    robot = createMock(Robot.class);
    writer = new AbstractJTableCellWriterStub(robot);
  }

  public void shouldThrowErrorIfEditorToHandleIsNull() {
    try {
      writer.cannotHandleEditor(null);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("Unable to handle editor component of type <null>");
    }
  }

  public void shouldReturnNullEditorComponentIfCellEditorIsNull() {
    final JTable table = createMock(JTable.class);
    final int row = 6;
    final int column = 8;
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.getCellEditor(row, column)).andReturn(null);
      }

      protected void codeToTest() {
        assertThat(writer.editorForCell(table, row, column)).isNull();
      }
    }.run();
  }
}
