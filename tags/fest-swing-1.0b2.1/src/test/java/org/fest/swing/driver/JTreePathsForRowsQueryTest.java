/*
 * Created on Aug 20, 2008
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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTreePathsForRowsQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTreePathsForRowsQueryTest {

  private Robot robot;
  private MyTree tree;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldReturnPathsForRows() {
    int[] rows = { 0, 1 };
    TreePath[] paths = JTreePathsForRowsQuery.pathsForRows(tree, rows);
    assertThat(paths).size().isEqualTo(2);
    assertThat(paths[0].getPath()).containsOnly(tree.root);
    assertThat(paths[1].getPath()).containsOnly(tree.root, tree.node);
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final MyTree tree = new MyTree();
    
    private MyWindow() {
      super(JTreePathsForRowsQueryTest.class);
      addComponents(tree);
    }
  }

  private static class MyTree extends JTree {
    private static final long serialVersionUID = 1L;
    
    final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    final DefaultMutableTreeNode node = new DefaultMutableTreeNode("node");
    
    MyTree() {
      root.add(node);
      setModel(new DefaultTreeModel(root));
      expandPath(new TreePath(root));
    }
  }
}
