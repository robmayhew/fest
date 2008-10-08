/*
 * Created on Sep 14, 2008
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
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTreePathBoundsQuery.pathBoundsOf;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTreeToggleExpandStateTask}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTreeToggleExpandStateTaskTest {

  private Robot robot;
  private JTree tree;
  private TreeNode treeRoot;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    treeRoot = window.treeRoot;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldToggleExpandState() {
    assertThat(isRootExpanded()).isFalse();
    JTreeToggleExpandStateTask.toggleExpandState(tree, centerOfTreeRoot());
    robot.waitForIdle();
    assertThat(isRootExpanded()).isTrue();
  }

  private boolean isRootExpanded() {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return tree.isExpanded(rootPath());
      }
    });
  }

  private Point centerOfTreeRoot() {
    Rectangle pathBounds = pathBoundsOf(tree, rootPath());
    return new Point(pathBounds.x + pathBounds.width / 2, pathBounds.y + pathBounds.height / 2);
  }

  private TreePath rootPath() {
    return new TreePath(treeRoot);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTree tree;
    final TreeNode treeRoot;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTreeToggleExpandStateTaskTest.class);
      treeRoot = createRoot();
      tree = new JTree(treeRoot);
      tree.setPreferredSize(new Dimension(300, 200));
      addComponents(tree);
      tree.collapsePath(new TreePath(treeRoot));
    }

    private static TreeNode createRoot() {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      DefaultMutableTreeNode child = new DefaultMutableTreeNode("child");
      root.add(child);
      return root;
    }
  }
}
