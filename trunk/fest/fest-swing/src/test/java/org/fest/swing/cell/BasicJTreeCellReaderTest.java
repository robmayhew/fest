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
package org.fest.swing.cell;


import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link BasicJTreeCellReader}</code>.
 *
 * @author Alex Ruiz
 */
public class BasicJTreeCellReaderTest {

  private JTree tree;
  private BasicJTreeCellReader reader;
  private DefaultMutableTreeNode root;
  
  @BeforeMethod public void setUp() {
    root = new DefaultMutableTreeNode("root");
    root.add(new DefaultMutableTreeNode("Node1"));
    tree = new JTree(new DefaultTreeModel(root));
    reader = new BasicJTreeCellReader();
  }

  @Test public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    JLabel label = new JLabel("First");
    tree.setCellRenderer(new CustomCellRenderer(label));
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(label.getText());
  }

  @Test public void shouldReturnTextFromTreeIfRendererIsNotJLabel() {
    tree.setCellRenderer(new CustomCellRenderer(new JToolBar()));
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(root.getUserObject());
  }
}
