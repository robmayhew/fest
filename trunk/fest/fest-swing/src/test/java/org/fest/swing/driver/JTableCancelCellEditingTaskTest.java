/*
 * Created on Jul 22, 2008
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTableCancelCellEditingTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTableCancelCellEditingTaskTest {

  private Robot robot;
  private TableDialogEditDemoWindow window;
  private JTable table;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TableDialogEditDemoWindow.createNew(getClass());
    table = window.table;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldCancelCellEditingIfTableCellHasEditor() {
    int row = 0;
    int column = 3;
    assertThat(startEditingTable(table, row, column)).isTrue();
    assertThat(editingRowOf(table)).isEqualTo(row);
    assertThat(editingColumnOf(table)).isEqualTo(column);
    JTableCancelCellEditingTask.cancelEditing(table, row, column);
    robot.waitForIdle();
    assertThat(editingRowOf(table)).isEqualTo(-1);
    assertThat(editingColumnOf(table)).isEqualTo(-1);
  }

  private static boolean startEditingTable(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return table.editCellAt(row, column);
      }
    });
  }

  private static int editingRowOf(final JTable table) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return table.getEditingRow();
      }
    });
  }

  private static int editingColumnOf(final JTable table) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return table.getEditingColumn();
      }
    });
  }
}
