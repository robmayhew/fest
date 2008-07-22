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


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.CustomCellRenderer;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link BasicJTreeCellReader}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class BasicJTreeCellReaderTest {

  private JTree tree;
  private BasicJTreeCellReader reader;
  private DefaultMutableTreeNode root;
  
  @BeforeMethod public void setUp() {
    root = new DefaultMutableTreeNode("root");
    root.add(new DefaultMutableTreeNode("Node1"));
    tree = new JTree(new DefaultTreeModel(root));
    reader = new BasicJTreeCellReader();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    JLabel label = new JLabel("First");
    tree.setCellRenderer(new CustomCellRenderer(label));
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(label.getText());
  }

  public void shouldReturnTextFromTreeIfRendererIsNotJLabel() {
    tree.setCellRenderer(new CustomCellRenderer(new JToolBar()));
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(root.getUserObject());
  }
  
  public void shouldReturnNullIfTextOfModelValueIsDefaultToString() {
    class Person {}
    tree = createMock(JTree.class);
    final TreeCellRenderer renderer = createMock(TreeCellRenderer.class);
    final Person value = new Person();
    new EasyMockTemplate(tree) {
      protected void expectations() {
        expect(tree.getCellRenderer()).andReturn(renderer);
        expect(renderer.getTreeCellRendererComponent(tree, value, false, false, false, 0, false)).andReturn(new JButton());
        expect(tree.convertValueToText(value, false, false, false, 0, false)).andReturn(value.toString());
      }

      protected void codeToTest() {
        assertThat(reader.valueAt(tree, value)).isNull();
      }
    }.run();
  }
}
