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
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
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
    root = execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
        rootNode.add(new DefaultMutableTreeNode("Node1"));
        return rootNode;
      }
    });
    tree = newTreeWith(new DefaultTreeModel(root));
    reader = new BasicJTreeCellReader();
  }

  private static JTree newTreeWith(final DefaultTreeModel rootNode) {
    return execute(new GuiQuery<JTree>() {
      protected JTree executeInEDT() {
        return new JTree(rootNode);
      }
    });
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    JLabel label = execute(new GuiQuery<JLabel>() {
      protected JLabel executeInEDT() {
        return new JLabel("First");
      }
    });
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
    root = execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return new DefaultMutableTreeNode(new Person());
      }
    });
    setRootInTree(tree, root);
    setCellRendererComponent(tree, unrecognizedRenderer());
    Object value = reader.valueAt(tree, root);
    assertThat(value).isNull();
  }

  private static void setRootInTree(final JTree tree, final DefaultMutableTreeNode root) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        ((DefaultTreeModel)tree.getModel()).setRoot(root);
      }
    });
  }

  private static void setCellRendererComponent(final JTree tree, final Component renderer) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setCellRenderer(new CustomCellRenderer(renderer));
      }
    });
  }
  
  private Component unrecognizedRenderer() {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return new JToolBar();
      }
    });
  }
}
