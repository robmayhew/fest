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

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeSelectionModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTreeSingleRowSelectedQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTreeSingleRowSelectedQueryTest {

  private MyWindow window;
  private JTree tree;

  @BeforeMethod public void setUp() {
    window = MyWindow.newWindow();
    tree = window.tree;
    window.display();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  public void shouldReturnTrueIfSingleRowSelected() {
    int row = 0;
    selectRow(tree, row);
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, row)).isTrue();
  }

  public void shouldReturnFalseIfMultipleRowSelected() {
    int row = 0;
    selectRows(tree, row, 1);
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, row)).isFalse();
  }

  private static void selectRows(final JTree tree, final int... rows) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionRows(rows);
      }
    });
  }

  public void shouldReturnFalseIfThereIsNoSelection() {
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, 0)).isFalse();
  }

  public void shouldReturnFalseIfOtherRowIsSelected() {
    selectRow(tree, 0);
    assertThat(JTreeSingleRowSelectedQuery.isSingleRowSelected(tree, 1)).isFalse();
  }

  private static void selectRow(final JTree tree, final int row) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionRow(row);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow newWindow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTree tree = new JTree(array("One", "Two"));

    MyWindow() {
      super(JTreeSingleRowSelectedQueryTest.class);
      tree.setSelectionModel(new DefaultTreeSelectionModel());
      addComponents(new JScrollPane(tree));
    }
  }

}
