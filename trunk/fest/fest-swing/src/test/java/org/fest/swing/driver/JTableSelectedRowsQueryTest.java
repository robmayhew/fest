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
import org.fest.swing.data.TableCell;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestTable;
import org.fest.swing.testing.TestWindow;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.driver.JTableSelectCellsTask.selectCells;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTableSelectedRowsQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTableSelectedRowsQueryTest {

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

  public void shouldReturnSelectedRowCountOfJTable() {
    selectInTable(row(0).column(3), row(2).column(3));
    table.startRecording();
    int[] selectedRows = JTableSelectedRowsQuery.selectedRowsIn(table);
    assertThat(selectedRows).containsOnly(0, 1, 2);
    table.requireInvoked("getSelectedRows");
  }

  private void selectInTable(final TableCell from, final TableCell to) {
    selectCells(table, from, to);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyTable table = new MyTable();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTableSelectedRowsQueryTest.class);
      addComponents(table);
    }
  }

  private static class MyTable extends TestTable {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTable() {
      super(3, 6);
      setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
    }

    @Override public int[] getSelectedRows() {
      if (recording) methodInvocations.invoked("getSelectedRows");
      return super.getSelectedRows();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
