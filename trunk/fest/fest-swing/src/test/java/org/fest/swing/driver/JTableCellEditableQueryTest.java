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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TableRenderDemo;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTableCellEditableQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTableCellEditableQueryTest {

  private Robot robot;
  private JTable table;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    table = window.table;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldIndicateWhetherCellIsEditableOrNot() {
    assertThat(JTableCellEditableQuery.isCellEditable(table, row(0).column(0))).isFalse();
    assertThat(JTableCellEditableQuery.isCellEditable(table, row(0).column(1))).isFalse();
    assertThat(JTableCellEditableQuery.isCellEditable(table, row(0).column(2))).isTrue();
    assertThat(JTableCellEditableQuery.isCellEditable(table, row(0).column(3))).isTrue();
    assertThat(JTableCellEditableQuery.isCellEditable(table, row(0).column(4))).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTable table;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTableCellEditableQueryTest.class);
      TableRenderDemo content = new TableRenderDemo();
      table = content.table;
      setContentPane(content);
    }
  }
}
