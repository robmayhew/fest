/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTreeFormatter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeFormatterTest {

  private JTree tree;
  private JTreeFormatter formatter;
  
  @BeforeMethod public void setUp() {
    tree = new JTree(array("One", "Two", "Three"));
    tree.setName("tree");
    formatter = new JTreeFormatter();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJTree() {
    formatter.format(new JTextField());
  }
  
  @Test public void shouldFormatJTree() {
    DefaultTreeSelectionModel model = new DefaultTreeSelectionModel();
    model.setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
    tree.setSelectionModel(model);
    tree.setSelectionRow(1);
    String formatted = formatter.format(tree);
    assertThat(formatted).contains(tree.getClass().getName())
                         .contains("name='tree'")
                         .contains("selectionCount=1")
                         .contains("selectionPaths=['[root, Two]']")
                         .contains("selectionMode=CONTIGUOUS_TREE_SELECTION")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }

  @Test public void shouldFormatJTreeWithoutSelectionModel() {
    tree.setSelectionModel(null);
    String formatted = formatter.format(tree);
    assertThat(formatted).contains(tree.getClass().getName())
                         .contains("name='tree'")
                         .contains("selectionCount=0")
                         .contains("selectionPaths=[]")
                         .contains("selectionMode=DISCONTIGUOUS_TREE_SELECTION")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
