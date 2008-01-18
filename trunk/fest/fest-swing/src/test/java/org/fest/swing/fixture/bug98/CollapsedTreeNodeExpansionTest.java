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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.AssertExtension;
import org.fest.swing.core.Pause;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.JTreeFixture;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;

/**
 * Test for bug <a href="http://code.google.com/p/fest/issues/detail?id=98" target="_blank">98</a>: 
 * JTreeFixture.selectPath does not select collapsed/invisible nodes?.
 *
 * @author Alex Ruiz 
 */
public class CollapsedTreeNodeExpansionTest {

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
    TreePathAssert node1 = new TreePathAssert(frame.tree, path("root", "node1", "node11"));
    assertThat(node1).isNotExpanded();
    tree.selectPath(path("root", "node1", "node11", "node111"));
    TreeSelectionAssert selection = new TreeSelectionAssert(frame.tree.getSelectionPath().getPath());
    assertThat(selection).isEqualTo("root", "node1", "node11", "node111");
    assertThat(node1).isExpanded();
    Pause.pause(10000);
  }

  private TreePath path(String...path) {
    return new TreePath(path);
  }

  private static class TreePathAssert implements AssertExtension {
    private final JTree tree;
    private final TreePath path;

    TreePathAssert(JTree tree, TreePath path) {
      this.tree = tree;
      this.path = path;
    }
    
    void isExpanded() { 
      assertThat(expanded()).isTrue(); 
    }
    
    void isNotExpanded() { assertThat(expanded()).isFalse(); }

    private boolean expanded() { return tree.isExpanded(path); }
  }
  
  private static class TreeSelectionAssert implements AssertExtension {
    private final Object[] selection;

    TreeSelectionAssert(Object[] selection) {
      this.selection = selection;
    }

    void isEqualTo(String...expectedPath) {
      int elementCount = expectedPath.length;
      assertThat(selection).hasSize(elementCount);
      for (int i = 0; i < elementCount; i++)
        nodeHasText(selection[i], expectedPath[i]);
    }

    private void nodeHasText(Object selected, String text) {
      assertThat(selected).isInstanceOf(DefaultMutableTreeNode.class);
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)selected;
      assertThat(node.getUserObject()).isEqualTo(text);
    }
  }
  
  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTree tree = new JTree();
    
    MyFrame(Class<?> testClass) {
      super(testClass);
      populateTree();
      add(tree);
    }
    
    private void populateTree() {
      MutableTreeNode root = 
        node("root", 
            node("node1", 
                node("node11",
                    node("node111"),
                    node("node112")
                    ), 
                node("node12")
                )
            );
      tree.setModel(new DefaultTreeModel(root));
    }
    
    private MutableTreeNode node(String text, MutableTreeNode...children) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
      for (MutableTreeNode child : children) node.add(child);
      return node;
    }
  }
}
