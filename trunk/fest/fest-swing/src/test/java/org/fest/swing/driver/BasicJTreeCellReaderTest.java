/*
 * Created on Apr 12, 2008
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

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Condition;
import org.fest.swing.factory.JLabels;
import org.fest.swing.factory.JTrees;
import org.fest.swing.testing.CustomCellRenderer;

import static javax.swing.SwingUtilities.invokeLater;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.factory.JToolBars.toolBar;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJTreeCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJTreeCellReaderTest {

  private JTree tree;
  private BasicJTreeCellReader reader;
  private DefaultMutableTreeNode root;

  @BeforeMethod public void setUp() {
    root = newRoot();
    tree = JTrees.tree().withRoot(root).createNew();
    reader = new BasicJTreeCellReader();
  }

  private static DefaultMutableTreeNode newRoot() {
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
    rootNode.add(new DefaultMutableTreeNode("Node1"));
    return rootNode;
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    JLabel label = JLabels.label().withText("First").createNew();
    setCellRendererComponent(tree, label);
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo("First");
  }

  public void shouldReturnTextFromTreeIfRendererIsNotJLabel() {
    setCellRendererComponent(tree, unrecognizedRenderer());
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(root.getUserObject());
  }

  public void shouldReturnNullIfTextOfModelValueIsDefaultToString() {
    class Person {}
    root = new DefaultMutableTreeNode(new Person());
    setRootInTree(tree, root);
    setCellRendererComponent(tree, unrecognizedRenderer());
    Object value = reader.valueAt(tree, root);
    assertThat(value).isNull();
  }

  private static void setRootInTree(final JTree tree, final DefaultMutableTreeNode root) {
    invokeLater(new Runnable() {
      public void run() {
        ((DefaultTreeModel)tree.getModel()).setRoot(root);
      }
    });
    pause(new Condition("JTree's root is set") {
      public boolean test() {
        return tree.getModel().getRoot() == root;
      }
    });
  }

  private static void setCellRendererComponent(final JTree tree, final Component renderer) {
    tree.setCellRenderer(new CustomCellRenderer(renderer));
  }
  
  private static Component unrecognizedRenderer() {
    return toolBar().createNew();
  }
}
