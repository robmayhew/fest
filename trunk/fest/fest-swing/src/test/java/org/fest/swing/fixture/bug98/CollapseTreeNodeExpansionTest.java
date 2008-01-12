/*
 * Created on Jan 11, 2008
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
package org.fest.swing.fixture.bug98;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;

import static org.fest.util.Arrays.array;

import org.fest.swing.core.Pause;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.JTreeFixture;
import org.fest.swing.testing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test for bug <a href="http://code.google.com/p/fest/issues/detail?id=98" target="_blank">98</a>: 
 * JTreeFixture.selectPath does not select collapsed/invisible nodes?.
 *
 * @author Alex Ruiz 
 */
public class CollapseTreeNodeExpansionTest {

  private RobotFixture robot;
  private MyFrame frame;
  private JTreeFixture tree;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new MyFrame(getClass());
    robot.showWindow(frame, new Dimension(200, 200));
    tree = new JTreeFixture(robot, frame.tree);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldExpandCollapsedNode() {
    Pause.pause(10000);
    TreePath path = new TreePath(array("root", "node1", "node11"));
    tree.selectPath(path);
    Pause.pause(10000);
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTree tree = new JTree();
    
    MyFrame(Class<?> testClass) {
      super(testClass);
      tree.setName("tree");
      populateTree();
      add(tree);
    }
    
    private void populateTree() {
      DefaultMutableTreeNode root = node("root", node("node1", node("node11"), node("node12")));
      tree.setModel(new DefaultTreeModel(root));
    }
    
    private DefaultMutableTreeNode node(String text, MutableTreeNode...children) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
      for (MutableTreeNode child : children) node.add(child);
      return node;
    }
  }
}
