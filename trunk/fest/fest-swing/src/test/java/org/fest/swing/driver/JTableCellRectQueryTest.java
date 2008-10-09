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

import java.awt.Rectangle;

import javax.swing.JTable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.fest.swing.testing.MethodInvocations.Args;

import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.MethodInvocations.Args.args;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTableCellRectQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTableCellRectQueryTest {

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

  public void shouldReturnCellRectInJTable() {
    JTableCellRectQuery.cellRectOf(table, 0, 2);
    table.requireInvoked("getCellRect", args(0, 2, false));
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyTable table = new MyTable();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTableCellRectQueryTest.class);
      addComponents(table);
    }
  }

  private static class MyTable extends JTable {
    private static final long serialVersionUID = 1L;

    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTable() {
      super(2, 6);
    }

    @Override public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
      methodInvocations.invoked("getCellRect", args(row, column, includeSpacing));
      return super.getCellRect(row, column, includeSpacing);
    }

    MethodInvocations requireInvoked(String methodName, Args args) {
      return methodInvocations.requireInvoked(methodName, args);
    }
  }
}
