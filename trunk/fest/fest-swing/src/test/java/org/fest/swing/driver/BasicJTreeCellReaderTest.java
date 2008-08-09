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


import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
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
    tree = new GuiQuery<JTree>() {
      protected JTree executeInEDT() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        root.add(new DefaultMutableTreeNode("Node1"));
        return new JTree(new DefaultTreeModel(root));
      }
    }.run();
    root = (DefaultMutableTreeNode)tree.getModel().getRoot();
    reader = new BasicJTreeCellReader();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    String expectedText = new GuiQuery<String>() {
      protected String executeInEDT() {
        JLabel label = new JLabel("First");
        tree.setCellRenderer(new CustomCellRenderer(label));
        return label.getText();
      }
    }.run();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(expectedText);
  }

  public void shouldReturnTextFromTreeIfRendererIsNotJLabel() {
    new GuiQuery<Void>() {
      protected Void executeInEDT() {
        tree.setCellRenderer(new CustomCellRenderer(new JToolBar()));
        return null;
      }
    }.run();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(root.getUserObject());
  }

  public void shouldReturnNullIfTextOfModelValueIsDefaultToString() {
    class Person {}
    root = new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return new DefaultMutableTreeNode(new Person());
      }
    }.run();
    new GuiQuery<Void>() {
      protected Void executeInEDT() {
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        model.setRoot(root);
        tree.setCellRenderer(new CustomCellRenderer(new JToolBar()));
        return null;
      }
    }.run();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isNull();
  }
}
