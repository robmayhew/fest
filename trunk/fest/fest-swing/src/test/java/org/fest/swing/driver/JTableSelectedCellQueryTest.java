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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestTable;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTableCell.cell;
import static org.fest.swing.driver.JTableSelectCellsTask.selectCells;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTableSelectedCellQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTableSelectedCellQueryTest {

  private Robot robot;
  private MyTable table;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    table = window.table;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnSelectedCellOfJTable() {
    selectInTable(cell(0, 2));
    table.startRecording();
    JTableCell selectedCell = JTableSelectedCellQuery.selectedCellOf(table);
    assertThat(selectedCell.row).isEqualTo(0);
    assertThat(selectedCell.column).isEqualTo(2);
    table.requireInvoked("getSelectedColumn")
         .requireInvoked("getSelectedRow");
  }

  private void selectInTable(final JTableCell cell) {
    selectCells(table, cell, cell);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyTable table = new MyTable();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTableSelectedCellQueryTest.class);
      addComponents(table);
    }
  }

  private static class MyTable extends TestTable {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTable() {
      super(2, 6);
    }

    @Override public int getSelectedColumn() {
      if (recording) methodInvocations.invoked("getSelectedColumn");
      return super.getSelectedColumn();
    }

    @Override public int getSelectedRow() {
      if (recording) methodInvocations.invoked("getSelectedRow");
      return super.getSelectedRow();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
