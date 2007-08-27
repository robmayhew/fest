/*
 * Created on May 21, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.GUITest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeFixture}</code>.
 * 
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@GUITest public class JTreeFixtureTest extends ComponentFixtureTestCase<JTree> {
  
  private TestTree target;
  private JTreeFixture targetFixture;

  private TestTree dropTarget;
  private JTreeFixture dropTargetFixture;

  @Test public void shouldSelectNodeByRow() {
    targetFixture.target.clearSelection();
    assertThat(targetFixture.target.getSelectionRows()).isNull();
    targetFixture.selectRow(0);
    assertThat(targetFixture.target.getSelectionRows()).isEqualTo(new int[] { 0 });
    targetFixture.selectRow(1);
    assertThat(targetFixture.target.getSelectionRows()).isEqualTo(new int[] { 1 });
    targetFixture.selectRow(0);
    assertThat(targetFixture.target.getSelectionRows()).isEqualTo(new int[] { 0 });
  }

  @Test public void shouldToggleNodeByRow() {
    assertThat(targetFixture.target.isExpanded(1)).isFalse();
    targetFixture.toggleRow(1);
    assertThat(targetFixture.target.isExpanded(1)).isTrue();
    targetFixture.toggleRow(1);
    assertThat(targetFixture.target.isExpanded(1)).isFalse();
  }

  @Test(dataProvider = "selectionPath") 
  public void shouldSelectNodeByPath(TreePath treePath) {
    targetFixture.target.clearSelection();
    assertThat(targetFixture.target.getSelectionRows()).isEqualTo(null);
    targetFixture.selectPath(treePath);
    assertThat(targetFixture.target.getSelectionPath().toString()).isEqualTo(treePath.toString());
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
    targetFixture.drag(path("root", "branch1", "branch1.1"));
    dropTargetFixture.drop(path("root"));
    TreeModel targetModel = target.getModel();
    DefaultMutableTreeNode branch1 = firstChildOf(rootOf(targetModel));
    assertThat(branch1.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(branch1))).isEqualTo("branch1.2");
    TreeModel dropTargetModel = dropTarget.getModel();
    DefaultMutableTreeNode root = rootOf(dropTargetModel);
    assertThat(root.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(root))).isEqualTo("branch1.1");    
  }

  @Test public void shouldDragAndDropUsingGivenIndices() {
    targetFixture.drag(2);
    dropTargetFixture.drop(0);
    TreeModel targetModel = target.getModel();
    DefaultMutableTreeNode sourceRoot = rootOf(targetModel);
    assertThat(sourceRoot.getChildCount()).isEqualTo(1);
    TreeModel dropTargetModel = dropTarget.getModel();
    DefaultMutableTreeNode destinationRoot = rootOf(dropTargetModel);
    assertThat(destinationRoot.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(destinationRoot))).isEqualTo("branch2");    
  }

  private DefaultMutableTreeNode rootOf(TreeModel model) {
    return (DefaultMutableTreeNode)model.getRoot();
  }

  private String textOf(DefaultMutableTreeNode node) {
    return (String) node.getUserObject();
  }
  
  private DefaultMutableTreeNode firstChildOf(DefaultMutableTreeNode node) {
    return (DefaultMutableTreeNode)node.getChildAt(0);
  }
  
  private TreePath path(String... path) {
    return new TreePath(path);
  }
  
  protected ComponentFixture<JTree> createFixture() {
    targetFixture = new JTreeFixture(robot(), "target");
    return targetFixture;
  }

  protected JTree createTarget() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("branch1");
    root.add(branch1);
    branch1.add(new DefaultMutableTreeNode("branch1.1"));
    branch1.add(new DefaultMutableTreeNode("branch1.2"));
    root.add(new DefaultMutableTreeNode("branch2"));
    target = new TestTree("target", new DefaultTreeModel(root));
    return target;
  }

  @Override protected void afterSetUp() {
    dropTarget = new TestTree("target", new DefaultTreeModel(new DefaultMutableTreeNode("root")));
    dropTargetFixture = new JTreeFixture(robot(), dropTarget);
    window().add(dropTarget);
    window().setSize(new Dimension(600, 400));
  }
}