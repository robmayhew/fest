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

import java.awt.Dimension;

import javax.swing.JTable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Test case for implementations of <code>{@link JTableCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public abstract class JTableCellWriterTestCase {

  private Robot robot;
  private TableDialogEditDemoWindow frame;
  private JTableCellWriter writer;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    writer = createWriter();
    frame = TableDialogEditDemoWindow.createNew(getClass());
    robot.showWindow(frame, new Dimension(500, 100));
  }

  protected abstract JTableCellWriter createWriter();

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfEditorComponentCannotBeHandledWhenEnteringValue(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      writer.enterValue(frame.table, 0, 1, "hello");
      fail();
    } catch (ActionFailedException e) {
      assertMessageIncludesComponentNotHandled(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfEditorComponentCannotBeHandledWhenStartingEditing(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      writer.startCellEditing(frame.table, 0, 1);
      fail();
    } catch (ActionFailedException e) {
      assertMessageIncludesComponentNotHandled(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfEditorComponentCannotBeHandledWhenStopingEditing(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      writer.stopCellEditing(frame.table, 0, 1);
      fail();
    } catch (ActionFailedException e) {
      assertMessageIncludesComponentNotHandled(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfEditorComponentCannotBeHandledWhenCancellingEditing(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      writer.cancelCellEditing(frame.table, 0, 1);
      fail();
    } catch (ActionFailedException e) {
      assertMessageIncludesComponentNotHandled(e);
    }
  }

  private void assertMessageIncludesComponentNotHandled(ActionFailedException e) {
    assertThat(e).message().contains("Unable to handle editor component of type javax.swing.JButton");
  }

  protected final Robot robot() { return robot; }
  protected final JTable table() { return frame.table; }
  protected final JTableCellWriter writer() { return writer; }

  protected final Object valueAt(int row, int column) {
    return cellValueOf(frame.table, row, column);
  }
}
