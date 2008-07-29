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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link AbstractJTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class AbstractJTableCellWriterGuiTest {

  private Robot robot;
  private TableDialogEditDemoFrame frame;
  private AbstractJTableCellWriter writer;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    writer = new ConcreteJTableCellWriter(robot);
    frame = new GuiTask<TableDialogEditDemoFrame>() {
      protected TableDialogEditDemoFrame executeInEDT() {
        return new TableDialogEditDemoFrame();
      }
    }.run();
    robot.showWindow(frame, new Dimension(500, 100));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "cellEditors")
  public void shouldReturnEditorForCell(int row, int column, Class<Component> editorType) {
    Component editor = writer.editorForCell(frame.table, row, column);
    assertThat(editor).isNotNull().isInstanceOf(editorType);
  }

  @DataProvider(name = "cellEditors")
  public Object[][] cellEditors() {
    return new Object[][] {
        { 0, 2, JComboBox.class },
        { 0, 3, JTextField.class },
        { 0, 4, JCheckBox.class }
    };
  }
  
  @Test public void shouldThrowErrorIfEditorToHandleIsNull() {
    try {
      writer.cannotHandleEditor(null);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("Unable to handle editor component of type <null>");
    }
  }
  
  @Test public void shouldReturnNullEditorComponentIfCellEditorIsNull() {
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

  private static class ConcreteJTableCellWriter extends AbstractJTableCellWriter {
    ConcreteJTableCellWriter(Robot robot) {
      super(robot);
    }

    public void enterValue(JTable table, int row, int column, String value) {}

    public void startCellEditing(JTable table, int row, int column) {}

    public void stopCellEditing(JTable table, int row, int column) {}

    public void cancelCellEditing(JTable table, int row, int column) {}
  }
}
