/*
 * Created on Aug 24, 2008
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
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.*;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JTreeChildOfPathCountQuery}</code>.
 *
 * @author Alex Ruiz 
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTreeChildOfPathCountQueryTest {

  private MyWindow window;
  private int childCount;
  
  @BeforeMethod public void setUp() {
    childCount = 8;
    window = MyWindow.createAndShow(childCount);
  }
  
  public void shouldReturnChildCountOfTreePath() {
    TreePath path = new TreePath(window.tree.getModel().getRoot());
    int childOfPathCount = JTreeChildOfPathCountQuery.childCount(window.tree, path);
    assertThat(childOfPathCount).isEqualTo(window.treeRoot.getChildCount())
                                .isEqualTo(childOfPathCount);
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createAndShow(final int treeRootChildCount) {
      MyWindow window = new MyWindow(treeRootChildCount);
      window.display();
      return window;
    }
    
    final JTree tree;
    final TreeNode treeRoot;
    
    private MyWindow(int treeRootChildCount) {
      super(JTreeChildOfPathCountQueryTest.class);
      treeRoot = root(treeRootChildCount);
      tree = new JTree(treeRoot);
      addComponents(tree);
    }

    private static TreeNode root(int rootChildCount) {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      for (int i = 1; i <= rootChildCount; i++) 
        root.add(new DefaultMutableTreeNode(concat("node", i)));
      return root;
    }
  }
}
