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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestTree;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;

/**
 * Test for <code>{@link JTreeDriver}</code>.
 *
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeDriverTest {

  private Robot robot;
  private JTree dragTree;
  private JTree dropTree;
  private JTreeDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTreeDriver(robot);
    MyFrame frame = new MyFrame();
    dragTree = frame.dragTree;
    dropTree = frame.dropTree;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldSelectNodeByRow() {
    dragTree.clearSelection();
    assertThat(dragTree.getSelectionRows()).isNull();
    driver.selectRow(dragTree, 0);
    assertThat(dragTree.getSelectionRows()).isEqualTo(new int[] { 0 });
    driver.selectRow(dragTree, 1);
    assertThat(dragTree.getSelectionRows()).isEqualTo(new int[] { 1 });
    driver.selectRow(dragTree, 0);
    assertThat(dragTree.getSelectionRows()).isEqualTo(new int[] { 0 });
  }

  @Test public void shouldToggleNodeByRow() {
    assertThat(dragTree.isExpanded(1)).isFalse();
    driver.toggleRow(dragTree, 1);
    assertThat(dragTree.isExpanded(1)).isTrue();
    driver.toggleRow(dragTree, 1);
    assertThat(dragTree.isExpanded(1)).isFalse();
  }

  @Test(dataProvider = "selectionPath") 
  public void shouldSelectNodeByPath(TreePath treePath) {
    dragTree.clearSelection();
    assertThat(dragTree.getSelectionRows()).isEqualTo(null);
    driver.selectPath(dragTree, treePath);
    assertThat(dragTree.getSelectionPath().toString()).isEqualTo(treePath.toString());
  }

  @DataProvider(name = "selectionPath") 
  public Object[][] selectionPath() {
    return new Object[][] { 
        { path("root", "branch1") },
        { path("root", "branch1", "branch1.2") },
        { path("root") } 
    };
  }

  @Test public void shouldDragAndDropUsingGivenTreePaths() {
    driver.drag(dragTree, path("root", "branch1", "branch1.1"));
    driver.drop(dropTree, path("root"));
    TreeModel dragModel = dragTree.getModel();
    DefaultMutableTreeNode branch1 = firstChildOf(rootOf(dragModel));
    assertThat(branch1.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(branch1))).isEqualTo("branch1.2");
    TreeModel dropModel = dropTree.getModel();
    DefaultMutableTreeNode root = rootOf(dropModel);
    assertThat(root.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(root))).isEqualTo("branch1.1");    
  }

  @Test public void shouldDragAndDropUsingGivenRows() {
    driver.drag(dragTree, 2);
    driver.drop(dropTree, 0);
    TreeModel dragModel = dragTree.getModel();
    DefaultMutableTreeNode sourceRoot = rootOf(dragModel);
    assertThat(sourceRoot.getChildCount()).isEqualTo(1);
    TreeModel dropModel = dropTree.getModel();
    DefaultMutableTreeNode destinationRoot = rootOf(dropModel);
    assertThat(destinationRoot.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(destinationRoot))).isEqualTo("branch2"); 
  }

  private DefaultMutableTreeNode rootOf(TreeModel model) {
    return (DefaultMutableTreeNode)model.getRoot();
  }

  private String textOf(DefaultMutableTreeNode node) {
    return (String)node.getUserObject();
  }
  
  private DefaultMutableTreeNode firstChildOf(DefaultMutableTreeNode node) {
    return (DefaultMutableTreeNode)node.getChildAt(0);
  }
  
  private TreePath path(String... path) {
    return new TreePath(path);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private static final Dimension TREE_SIZE = new Dimension(200, 100);

    final TestTree dragTree = new TestTree(nodes());
    final TestTree dropTree = new TestTree(rootOnly());
    
    private static TreeModel nodes() {
      MutableTreeNode root =
        node("root",
            node("branch1",
                node("branch1.1",
                    node("branch1.1.1"),
                    node("branch1.1.2")
                ),
                node("branch1.2")
            ),
            node("branch2")
        );
      return new DefaultTreeModel(root);
    }
    
    private static TreeModel rootOnly() {
      return new DefaultTreeModel(node("root"));
    }
    
    private static MutableTreeNode node(String text, MutableTreeNode...children) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
      for (MutableTreeNode child : children) node.add(child);
      return node;
    }

    MyFrame() {
      super(JTreeDriverTest.class);
      add(decorate(dragTree));
      add(decorate(dropTree));
      setPreferredSize(new Dimension(600, 400));
    }

    private Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(TREE_SIZE);
      return scrollPane;
    }
}
}
