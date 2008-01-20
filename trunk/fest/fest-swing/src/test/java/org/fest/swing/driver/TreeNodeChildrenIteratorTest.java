/*
 * Created on Jan 17, 2008
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

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.driver.TreeNodeChildrenIterator;
import org.fest.swing.testing.TestTree;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.testing.TestTree.node;

/**
 * Tests for <code>{@link TreeNodeChildrenIterator}</code>.
 *
 * @author Alex Ruiz
 */
public class TreeNodeChildrenIteratorTest {

  private static Logger logger = Logger.getAnonymousLogger();
  
  private TestTree tree;
  private TreeNodeChildrenIterator iterator;
  
  @BeforeMethod public void setUp() {
    tree = new TestTree("tree");
    populateTree();
    TreeModel model = tree.getModel();
    iterator = new TreeNodeChildrenIterator(model, model.getRoot());
  }

  @Test public void shouldReturnHasChildrenUntilLastOneIsRead() {
    assertThat(iterator.hasNext()).isTrue();
    assertThat(textInNext()).isEqualTo("node1");
    assertThat(iterator.hasNext()).isTrue();
    assertThat(textInNext()).isEqualTo("node2");
    assertThat(iterator.hasNext()).isTrue();
    assertThat(textInNext()).isEqualTo("node3");
    assertThat(iterator.hasNext()).isFalse();
    try {
      iterator.next();
      fail();
    } catch (NoSuchElementException expected) {
      logger.info(expected.getMessage());
    }
  }

  private Object textInNext() {
    DefaultMutableTreeNode next = (DefaultMutableTreeNode)iterator.next();
    return (next).getUserObject();
  }
  
  private void populateTree() {
    MutableTreeNode root = 
      node("root", 
          node("node1"), 
          node("node2"),
          node("node3")
          );
    tree.setModel(new DefaultTreeModel(root));
  }
}
