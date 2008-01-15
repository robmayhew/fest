/*
 * Created on Jan 13, 2008
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
package org.fest.swing.location;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.testing.TestTree.node;

import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

import org.fest.swing.testing.TestTree;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TreeCell}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TreeCellTest {

  private TestTree tree;
  
  @BeforeClass public void setUp() {
    tree = new TestTree("tree");
    populateTree();
  }

  private void populateTree() {
    MutableTreeNode root = 
      node("root", 
          node("node1", 
              node("node11",
                  node("node111"),
                  node("node111"),
                  node("node111")
                  ), 
              node("node12")
              )
          );
    tree.setModel(new DefaultTreeModel(root));
  }

  @Test public void shouldCreateStringFromLastComponentInTreePath() {
    TreePath node11Path = node11Path();
    TreeNode node111 = childOf(node11Path.getLastPathComponent(), 0);
    TreePath node111Path = node11Path.pathByAddingChild(node111);
    TreeCell cell = TreeCell.lastInPath(tree, node111Path);
    assertThat(cell.text()).isEqualTo("node111");
  }

  @Test(dataProvider = "node111Indices") 
  public void shouldCreateStringFromLastComponentInTreePathAndAddIndexIfTextAlreadyPresentInHierarchy(int index) {
    TreePath node11Path = node11Path();
    TreeNode node111 = childOf(node11Path.getLastPathComponent(), index);
    TreePath node111Path = node11Path.pathByAddingChild(node111);
    TreeCell cell = TreeCell.lastInPath(tree, node111Path);
    assertThat(cell.textWithIndexIfDuplicated()).isEqualTo(concat("node111[", index, "]"));
  }

  @DataProvider(name="node111Indices")
  public Object[][] node111Indices() {
    return new Object[][] {
        { 1 },
        { 2 }
    };
  }
  
  private TreePath node11Path() {
    DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
    Object root = model.getRoot();
    TreeNode node1 = childOf(root, 0);
    TreeNode node11 = childOf(node1, 0);
    return new TreePath(array(root, node1, node11));
  }

  private TreeNode childOf(Object parent, int index) {
    return ((DefaultMutableTreeNode)parent).getChildAt(index);
  }
}
