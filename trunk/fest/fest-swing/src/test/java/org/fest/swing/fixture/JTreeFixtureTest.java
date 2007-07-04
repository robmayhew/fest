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

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

import org.fest.swing.GUITest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeFixture}</code>.
 * 
 * @author Keith Coughtrey
 * @author Alex Ruiz
 */
@GUITest public class JTreeFixtureTest extends ComponentFixtureTestCase<JTree> {
  
  private JTreeFixture fixture;

  @Test public void shouldSelectNodeByRow() {
    fixture.target.clearSelection();
    assertThat(fixture.target.getSelectionRows()).isNull();
    fixture.selectRow(0);
    assertThat(fixture.target.getSelectionRows()).isEqualTo(new int[] { 0 });
    fixture.selectRow(1);
    assertThat(fixture.target.getSelectionRows()).isEqualTo(new int[] { 1 });
    fixture.selectRow(0);
    assertThat(fixture.target.getSelectionRows()).isEqualTo(new int[] { 0 });
  }

  @Test public void shouldToggleNodeByRow() {
    assertThat(fixture.target.isExpanded(1)).isFalse();
    fixture.toggleRow(1);
    assertThat(fixture.target.isExpanded(1)).isTrue();
    fixture.toggleRow(1);
    assertThat(fixture.target.isExpanded(1)).isFalse();
  }

  @Test(dataProvider = "selectionPathProvider") 
  public void shouldSelectNodeByPath(TreePath treePath) {
    fixture.target.clearSelection();
    assertThat(fixture.target.getSelectionRows()).isEqualTo(null);
    fixture.selectPath(treePath);
    assertThat(fixture.target.getSelectionPath().toString()).isEqualTo(treePath.toString());
  }

  @DataProvider(name = "selectionPathProvider") 
  public Object[][] selectionPathProvider() {
    return new Object[][] { 
        { new TreePath(array("root", "branch1")) },
        { new TreePath(array("root", "branch1", "branch1.2")) },
        { new TreePath(array("root")) } 
    };
  }

  protected ComponentFixture<JTree> createFixture() {
    fixture = new JTreeFixture(robot(), "target");
    return fixture;
  }

  protected JTree createTarget() {
    JTree target = new JTree();
    target.setName("target");
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
    DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("branch1");
    root.add(branch1);
    branch1.add(new DefaultMutableTreeNode("branch1.1"));
    branch1.add(new DefaultMutableTreeNode("branch1.2"));
    root.add(new DefaultMutableTreeNode("branch2"));
    target.setModel(new DefaultTreeModel(root));
    return target;
  }
}