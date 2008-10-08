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

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TableDialogEditDemoWindow.createNew(getClass());
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldCancelCellEditingIfTableCellHasEditor() {
    int row = 0;
    int column = 3;
    assertThat(startEditingTable(row, column)).isTrue();
    assertThat(editingRow()).isEqualTo(row);
    assertThat(editingColumn()).isEqualTo(column);
    JTableCancelCellEditingTask.cancelEditing(window.table, row, column);
    robot.waitForIdle();
    assertThat(editingRow()).isEqualTo(-1);
    assertThat(editingColumn()).isEqualTo(-1);
  }

  private boolean startEditingTable(final int row, final int column) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return window.table.editCellAt(row, column);
      }
    });
  }

  private int editingRow() {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return window.table.getEditingRow();
      }
    });
  }

  private int editingColumn() {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return window.table.getEditingColumn();
      }
    });
  }
}
