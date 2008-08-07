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
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.ClickRecorder;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link AbstractJTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI) 
public class AbstractJTableCellWriterTest {

  private Robot robot;
  private TableDialogEditDemoFrame frame;
  private AbstractJTableCellWriter writer;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    writer = new AbstractJTableCellWriterStub(robot);
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

  public void shouldThrowErrorIfEditorToHandleIsNull() {
    try {
      writer.cannotHandleEditor(null);
      fail();
    } catch (ActionFailedException e) {
      assertThat(e).message().contains("Unable to handle editor component of type <null>");
    }
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldClickCellOnce(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    ClickRecorder recorder = ClickRecorder.attachTo(frame.table);
    int row = 0;
    int column = 0;
    Point cellCenter = centerOfCell(row, column);
    writer.clickCell(frame.table, row, column);
    assertThat(recorder).clicked(LEFT_BUTTON)
                        .timesClicked(1)
                        .clickedAt(cellCenter);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldClickCell(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    ClickRecorder recorder = ClickRecorder.attachTo(frame.table);
    int row = 0;
    int column = 0;
    Point cellCenter = centerOfCell(row, column);
    writer.clickCell(frame.table, row, column, 2);
    assertThat(recorder).clicked(LEFT_BUTTON)
                        .timesClicked(2)
                        .clickedAt(cellCenter);
  }

  private Point centerOfCell(int row, int column) {
    Rectangle r = frame.table.getCellRect(row, column, false);
    return new Point(r.x + r.width / 2, r.y + r.height / 2);
  }
}
