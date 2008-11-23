/*
 * Created on Aug 19, 2008
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

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeSelectionModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTreeSelectRowsTask.selectRows;
import static org.fest.swing.task.JTreeSelectRowTask.selectRow;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTreeSingleRowSelectedQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JTreeSingleRowSelectedQueryTest {

  private Robot robot;
  private JTree tree;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTrueIfSingleRowSelected() {
    int row = 0;
    selectRow(tree, row);
    robot.waitForIdle();
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, row)).isTrue();
  }

  public void shouldReturnFalseIfMultipleRowSelected() {
    int row = 0;
    int[] rows = { row, 1 };
    selectRows(tree, rows);
    robot.waitForIdle();
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, row)).isFalse();
  }

  public void shouldReturnFalseIfThereIsNoSelection() {
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, 0)).isFalse();
  }

  public void shouldReturnFalseIfOtherRowIsSelected() {
    selectRow(tree, 0);
    robot.waitForIdle();
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, 1)).isFalse();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JTree tree = new JTree(array("One", "Two"));

    private MyWindow() {
      super(JTreeSingleRowSelectedQueryTest.class);
      tree.setSelectionModel(new DefaultTreeSelectionModel());
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(new Dimension(80, 60));
      addComponents(scrollPane);
    }
  }
}
