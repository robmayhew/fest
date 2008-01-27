/*
 * Created on Jan 18, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.util.Arrays.array;

import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JTree;
import javax.swing.tree.*;

import org.fest.assertions.AssertExtension;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import abbot.tester.JTreeTester;

/**
 * Test for <code>{@link JTreeTester}</code>.
 *
 * @author Alex Ruiz
 */
public class JTreeDriverTest {

  private RobotFixture robot;
  private MyFrame frame;
  private JTreeDriver driver;

  private static Logger logger = Logger.getAnonymousLogger();

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new MyFrame(getClass());
    robot.showWindow(frame, new Dimension(200, 200));
    driver = new JTreeDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldToggleRow() {
    TreePathAssert node1 = new TreePathAssert(frame.tree, node1Path());
    driver.toggleRow(frame.tree, 1);
    assertThat(node1).isExpanded();
    driver.toggleRow(frame.tree, 1);
    assertThat(node1).isNotExpanded();
    driver.toggleRow(frame.tree, 1);
    assertThat(node1).isExpanded();
  }

  @Test public void shouldToggleRowUsingTreeUI() {
    frame.tree.setToggleClickCount(0);
    TreePathAssert node1 = new TreePathAssert(frame.tree, node1Path());
    driver.toggleRow(frame.tree, 1);
    assertThat(node1).isExpanded();
    driver.toggleRow(frame.tree, 1);
    assertThat(node1).isNotExpanded();
    driver.toggleRow(frame.tree, 1);
    assertThat(node1).isExpanded();
  }

  @Test (dataProvider = "invalidIndices")
  public void shouldThrowErrorIfRowIndexIsInvalid(int invalidIndex) {
    try {
      driver.selectRow(frame.tree, invalidIndex);
      fail();
    } catch (LocationUnavailableException expected) {
      logger.info(expected.getMessage());
    }
  }

  @DataProvider(name = "invalidIndices")
  public Object[][] invalidIndices() {
    return new Object[][] {
        { -1 },
        { 2 },
    };
  }

  @Test public void shouldSelectRow() {
    driver.selectRow(frame.tree, 1);
    TreeSelectionAssert selection = new TreeSelectionAssert(frame.tree.getSelectionPath().getPath());
    assertThat(selection).isEqualTo("root", "node1");
  }

  @Test public void shouldSelectTreePathAndExpandParentNodes() {
    TreePathAssert node11 = new TreePathAssert(frame.tree, node11Path());
    assertThat(node11).isNotExpanded();
    driver.selectPath(frame.tree, path("root", "node1", "node11", "node111"));
    TreeSelectionAssert selection = new TreeSelectionAssert(frame.tree.getSelectionPath().getPath());
    assertThat(selection).isEqualTo("root", "node1", "node11", "node111");
    assertThat(node11).isExpanded();
  }

  private TreePath path(String...path) {
    return new TreePath(path);
  }

  private TreePath node11Path() {
    return childOf(node1Path(), 0);
  }

  private TreePath node1Path() {
    return childOf(rootPath(), 0);
  }

  private TreePath rootPath() {
    DefaultTreeModel model = (DefaultTreeModel)frame.tree.getModel();
    return new TreePath(array(model.getRoot()));
  }

  private TreePath childOf(TreePath parent, int index) {
    TreeNode child = childOf(parent.getLastPathComponent(), index);
    return parent.pathByAddingChild(child);
  }

  private TreeNode childOf(Object parent, int index) {
    return ((DefaultMutableTreeNode)parent).getChildAt(index);
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

    private boolean expanded() {
      return tree.isExpanded(path);
    }
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
