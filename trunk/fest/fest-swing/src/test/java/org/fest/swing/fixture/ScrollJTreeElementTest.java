/*
 * Created on Apr 29, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestTree;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=133">Bug 133</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class ScrollJTreeElementTest {

  private FrameFixture fixture;
  private MyFrame frame;

  @BeforeMethod public void setUp() {
    frame = new MyFrame();
    fixture = new FrameFixture(frame);
    fixture.show();
  }

  @AfterMethod public void tearDown() {
    fixture.cleanUp();
  }

  public void shouldScrollToSelectElementByPath() {
    fixture.tree("drag").selectPath("root/100/100.1");
    assertThat(selection()).isEqualTo("100.1");
  }

  public void shouldScrollToSelectElementByIndex() {
    fixture.tree("drag").selectRow(99);
    assertThat(selection()).isEqualTo("99");
  }
  
  public void shouldScrollToDragAndDropByPath() {
    fixture.tree("drag").drag("root/99");
    fixture.tree("drop").drop("root/90");
    assertPathNotFound("root/99");
    fixture.tree("drop").selectPath("root/90/99");
  }

  public void shouldScrollToDragAndDropByIndex() {
    fixture.tree("drag").drag(99);
    fixture.tree("drop").drop(90);
    assertPathNotFound("root/99");
    fixture.tree("drop").selectPath("root/90/99");
  }

  private void assertPathNotFound(String path) {
    try {
      fixture.tree("drag").selectPath(path);
      fail();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).contains(path);
    }
  }

  private Object selection() {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)frame.dragTree.getSelectionPath().getLastPathComponent();
    return node.getUserObject();
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTree dragTree = new TestTree("drag");
    final JTree dropTree = new TestTree("drop");

    public MyFrame() {
      super(ScrollJTreeElementTest.class);
      dragTree.setModel(model());
      add(scrollPaneFor(dragTree));
      dropTree.setModel(model());
      add(scrollPaneFor(dropTree));
      setPreferredSize(new Dimension(300, 150));
    }
    
    private static JScrollPane scrollPaneFor(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(new Dimension(100, 100));
      return scrollPane;
    }

    private static DefaultTreeModel model() {
      return new DefaultTreeModel(rootWith100Nodes());
    }

    private static DefaultMutableTreeNode rootWith100Nodes() {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
      for (int i = 0; i < 100; i++) {
        if (i == 99) {
          DefaultMutableTreeNode node99 = nodeFromIndex(i);
          node99.add(new DefaultMutableTreeNode("100.1"));
          root.add(node99);
          break;
        }
        root.add(nodeFromIndex(i));
      }
      return root;
    }

    private static DefaultMutableTreeNode nodeFromIndex(int i) {
      return new DefaultMutableTreeNode(String.valueOf(i + 1));
    }
  }
}
