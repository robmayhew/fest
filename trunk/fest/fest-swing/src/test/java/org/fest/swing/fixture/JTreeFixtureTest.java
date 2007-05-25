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

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.RobotFixture.robotWithNewAwtHierarchy;

import org.fest.swing.GUITest;
import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTreeFixture}</code>.
 * 
 * @author Keith Coughtrey
 */
@GUITest public class JTreeFixtureTest {
  
  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    DefaultTreeModel treeModel;
    final JTree tree = new JTree();

    public MainWindow() {
      setLayout(new FlowLayout());
      setUpComponents();
      addComponents();
    }

    private void setUpComponents() {
      tree.setName("tree");
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("branch1");
      root.add(branch1);
      branch1.add(new DefaultMutableTreeNode("branch1.1"));
      branch1.add(new DefaultMutableTreeNode("branch1.2"));
      root.add(new DefaultMutableTreeNode("branch2"));
      treeModel = new DefaultTreeModel(root);
      tree.setModel(treeModel);
    }

    private void addComponents() {
      getContentPane().add(new JScrollPane(tree));
    }
  }

  private MainWindow window;
  private RobotFixture robot;
  private JTreeFixture fixture;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = new MainWindow();
    robot.showWindow(window);
    fixture = new JTreeFixture(robot, "tree");
  }

  @Test public void shouldHaveFoundTree() {
    assertThat(fixture.target).isSameAs(window.tree);
  }

  @Test(dependsOnMethods = "shouldHaveFoundTree") public void shouldSelectNodeByRow() {
    fixture.target.clearSelection();
    assertThat(fixture.target.getSelectionRows()).isEqualTo(null);
    fixture.selectRow(0);
    assertThat(fixture.target.getSelectionRows()).isEqualTo(new int[] { 0 });
    fixture.selectRow(1);
    assertThat(fixture.target.getSelectionRows()).isEqualTo(new int[] { 1 });
    fixture.selectRow(0);
    assertThat(fixture.target.getSelectionRows()).isEqualTo(new int[] { 0 });
  }

  @Test(dependsOnMethods = "shouldHaveFoundTree") public void shouldToggleNodeByRow() {
    assertThat(fixture.target.isExpanded(1)).isFalse();
    fixture.toggleRow(1);
    assertThat(fixture.target.isExpanded(1)).isTrue();
    fixture.toggleRow(1);
    assertThat(fixture.target.isExpanded(1)).isFalse();
  }

  @Test(dependsOnMethods = "shouldHaveFoundTree", dataProvider = "selectionPathProvider") 
  public void shouldSelectNodeByPath(TreePath treePath) {
    fixture.target.clearSelection();
    assertThat(fixture.target.getSelectionRows()).isEqualTo(null);
    fixture.selectPath(treePath);
    assertThat(fixture.target.getSelectionPath().toString()).isEqualTo(treePath.toString());
  }

  @DataProvider(name = "selectionPathProvider") 
  public Object[][] selectionPathProvider() {
    return new Object[][] { 
        { new TreePath(new String[] { "root", "branch1" }) },
        { new TreePath(new String[] { "root", "branch1", "branch1.2" }) },
        { new TreePath(new String[] { "root" }) } 
    };
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
}