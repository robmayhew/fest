/*
 * Created on Jan 17, 2008
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTreeLocation}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JTreeLocationTest {

  private MyFrame frame;
  private JTreeLocation location;
  private List<TreePath[]> paths;

  @BeforeMethod public void setUp() {
    frame = new MyFrame(getClass());
    frame.display(new Dimension(200, 200));
    location = new JTreeLocation();
    populatePaths();
  }

  private void populatePaths() {
    paths = new ArrayList<TreePath[]>();
    paths.add(array(path("root"), rootPath()));
    paths.add(array(path("root", "node1"), node1Path()));
    paths.add(array(path("root", "node1", "node11"), node11Path()));
    paths.add(array(path("root", "node1", "node11", "node111"), childOf(node11Path(), 0)));
    paths.add(array(path("root", "node1", "node11", "node112"), childOf(node11Path(), 1)));
    paths.add(array(path("root", "node1", "node12"), childOf(node1Path(), 1)));
    paths.add(array(path("root", "node2"), childOf(rootPath(), 1)));
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test(dataProvider = "pathPairIndices")
  public void shouldFindLocationOfTreePath(int pathPairIndex) {
    TreePath[] pathPair = paths.get(pathPairIndex);
    Point actual = location.pointAt(frame.tree, pathPair[0]);
    Rectangle pathBounds = frame.tree.getPathBounds(pathPair[1]);
    Point expected = new Point(pathBounds.x + pathBounds.width / 2, pathBounds.y + pathBounds.height / 2);
    assertThat(actual).isEqualTo(expected);
  }

  @DataProvider(name = "pathPairIndices")
  public Object[][] pathPairIndices() {
    return new Object[][] {
        { 0 }, { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }
    };
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
    TreePath childPath = parent.pathByAddingChild(child);
    frame.tree.expandPath(childPath);
    return childPath;
  }

  private TreeNode childOf(Object parent, int index) {
    return ((DefaultMutableTreeNode)parent).getChildAt(index);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTree tree = new JTree();

    MyFrame(Class<?> testClass) {
      super(testClass);
      populateTree();
      tree.setPreferredSize(new Dimension(200, 200));
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
                ),
            node("node2")
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
