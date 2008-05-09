/*
 * Created on Jan 18, 2008
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
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestTree;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Test for <code>{@link JTreeDriver}</code>.
 *
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTreeDriverTest {

  private Robot robot;
  private MyFrame frame;
  private JTree dragTree;
  private JTree dropTree;

  private JTreeDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTreeDriver(robot);
    frame = new MyFrame();
    dragTree = frame.dragTree;
    dropTree = frame.dropTree;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldSelectNodeByRow() {
    clearSelection();
    assertThat(dragTree.getSelectionRows()).isNull();
    driver.selectRow(dragTree, 0);
    assertThat(dragTree.getSelectionRows()).isEqualTo(new int[] { 0 });
    driver.selectRow(dragTree, 1);
    assertThat(dragTree.getSelectionRows()).isEqualTo(new int[] { 1 });
    driver.selectRow(dragTree, 0);
    assertThat(dragTree.getSelectionRows()).isEqualTo(new int[] { 0 });
  }

  @Test public void shouldSelectNodesByRow() {
    clearSelection();
    dragTree.setSelectionModel(new DefaultTreeSelectionModel());
    assertThat(dragTree.getSelectionRows()).isNull();
    driver.selectRows(dragTree, 0, 1, 2);
    assertThat(dragTree.getSelectionRows()).isEqualTo(new int[] { 0, 1, 2 });
  }

  @Test public void shouldToggleNodeByRow() {
    assertThat(dragTree.isExpanded(1)).isFalse();
    driver.toggleRow(dragTree, 1);
    assertThat(dragTree.isExpanded(1)).isTrue();
    driver.toggleRow(dragTree, 1);
    assertThat(dragTree.isExpanded(1)).isFalse();
  }

  @Test public void shouldThrowErrorIfPathNotFound() {
    try {
      driver.selectPath(dragTree, "another");
      fail();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'another'");
    }
  }

  @Test(dataProvider = "selectionPath")
  public void shouldSelectNodeByPath(String treePath) {
    clearSelection();
    driver.selectPath(dragTree, treePath);
    assertThat(textOf(dragTree.getSelectionPath())).isEqualTo(treePath);
  }

  @DataProvider(name = "selectionPath")
  public Object[][] selectionPath() {
    return new Object[][] {
        { "root/branch1" },
        { "root/branch1/branch1.2" },
        { "root" }
    };
  }

  @Test public void shouldSelectNodesByPaths() {
    clearSelection();
    dragTree.setSelectionModel(new DefaultTreeSelectionModel());
    String[] paths = array("root/branch1/branch1.1", "root/branch1/branch1.2");
    driver.selectPaths(dragTree, paths);
    TreePath[] selectionPaths = dragTree.getSelectionPaths();
    assertThat(selectionPaths).hasSize(2);
    assertThat(textOf(selectionPaths[0])).isEqualTo(paths[0]);
    assertThat(textOf(selectionPaths[1])).isEqualTo(paths[1]);
  }

  private void clearSelection() {
    dragTree.clearSelection();
    assertThat(dragTree.getSelectionRows()).isEqualTo(null);
  }

  @Test public void shouldDragAndDropUsingGivenTreePaths() {
    driver.drag(dragTree, "root/branch1/branch1.1");
    driver.drop(dropTree, "root");
    TreeModel dragModel = dragTree.getModel();
    DefaultMutableTreeNode branch1 = firstChildOf(rootOf(dragModel));
    assertThat(branch1.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(branch1))).isEqualTo("branch1.2");
    TreeModel dropModel = dropTree.getModel();
    DefaultMutableTreeNode root = rootOf(dropModel);
    assertThat(root.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(root))).isEqualTo("branch1.1");
  }

  @Test public void shouldDragAndDropUsingGivenRows() {
    driver.drag(dragTree, 2);
    driver.drop(dropTree, 0);
    TreeModel dragModel = dragTree.getModel();
    DefaultMutableTreeNode sourceRoot = rootOf(dragModel);
    assertThat(sourceRoot.getChildCount()).isEqualTo(1);
    TreeModel dropModel = dropTree.getModel();
    DefaultMutableTreeNode destinationRoot = rootOf(dropModel);
    assertThat(destinationRoot.getChildCount()).isEqualTo(1);
    assertThat(textOf(firstChildOf(destinationRoot))).isEqualTo("branch2");
  }

  private String textOf(TreePath path) {
    if (path == null) return null;
    Object[] values = path.getPath();
    String separator = driver.separator();
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i != 0) b.append(separator);
      Object value = values[i];
      if (value instanceof DefaultMutableTreeNode) b.append(textOf((DefaultMutableTreeNode)value));
    }
    return b.toString();
  }

  private String textOf(DefaultMutableTreeNode node) {
    return (String)node.getUserObject();
  }

  private DefaultMutableTreeNode rootOf(TreeModel model) {
    return (DefaultMutableTreeNode)model.getRoot();
  }

  private DefaultMutableTreeNode firstChildOf(DefaultMutableTreeNode node) {
    return (DefaultMutableTreeNode)node.getChildAt(0);
  }

  @Test public void shouldPassIfRowIsSelected() {
    DefaultMutableTreeNode root = rootOf(dragTree.getModel());
    TreePath path = new TreePath(array(root, root.getFirstChild()));
    dragTree.setSelectionPath(path);
    driver.requireSelection(dragTree, 1);
  }

  @Test public void shouldPassIfPathIsSelected() {
    DefaultMutableTreeNode root = rootOf(dragTree.getModel());
    TreePath path = new TreePath(array(root, root.getFirstChild()));
    dragTree.setSelectionPath(path);
    driver.requireSelection(dragTree, "root/branch1");
  }

  @Test public void shouldFailIfExpectingSelectedRowAndTreeHasNoSelection() {
    dragTree.setSelectionPath(null);
    try {
      driver.requireSelection(dragTree, 1);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selection'")
                             .contains("No selection");
    }
  }

  @Test public void shouldFailIfSelectedRowIsNotEqualToExpectedSelection() {
    DefaultMutableTreeNode root = rootOf(dragTree.getModel());
    dragTree.setSelectionPath(new TreePath(array(root)));
    try {
      driver.requireSelection(dragTree, 1);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectionPath'")
                             .contains("expected:<[root, branch1]> but was:<[root]>");
    }
  }

  @Test public void shouldFailIfExpectingSelectedPathAndTreeHasNoSelection() {
    dragTree.setSelectionPath(null);
    try {
      driver.requireSelection(dragTree, "root/branch1");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selection'")
                             .contains("No selection");
    }
  }

  @Test public void shouldFailIfSelectedPathIsNotEqualToExpectedSelection() {
    DefaultMutableTreeNode root = rootOf(dragTree.getModel());
    dragTree.setSelectionPath(new TreePath(array(root)));
    try {
      driver.requireSelection(dragTree, "root/branch1");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectionPath'")
                             .contains("expected:<[root, branch1]> but was:<[root]>");
    }
  }

  @Test public void shouldPassIfTreeIsEditable() {
    dragTree.setEditable(true);
    driver.requireEditable(dragTree);
  }

  @Test public void shouldFailIfTreeIsNotEditableAndExpectingEditable() {
    dragTree.setEditable(false);
    try {
      driver.requireEditable(dragTree);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  @Test public void shouldPassIfTreeIsNotEditable() {
    dragTree.setEditable(false);
    driver.requireNotEditable(dragTree);
  }

  @Test public void shouldFailIfTreeIsEditableAndExpectingNotEditable() {
    dragTree.setEditable(true);
    try {
      driver.requireNotEditable(dragTree);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  @Test public void shouldShowPopupMenuAtRow() {
    JPopupMenu popupMenu = driver.showPopupMenu(dragTree, 0);
    assertThat(popupMenu).isSameAs(frame.popupMenu);
  }

  @Test public void shouldShowPopupMenuAtPath() {
    JPopupMenu popupMenu = driver.showPopupMenu(dragTree, "root");
    assertThat(popupMenu).isSameAs(frame.popupMenu);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private static final Dimension TREE_SIZE = new Dimension(200, 100);

    final TestTree dragTree = new TestTree(nodes());
    final TestTree dropTree = new TestTree(rootOnly());
    final JPopupMenu popupMenu = new JPopupMenu();

    private static TreeModel nodes() {
      MutableTreeNode root =
        node("root",
            node("branch1",
                node("branch1.1",
                    node("branch1.1.1"),
                    node("branch1.1.2")
                ),
                node("branch1.2")
            ),
            node("branch2")
        );
      return new DefaultTreeModel(root);
    }

    private static TreeModel rootOnly() {
      return new DefaultTreeModel(node("root"));
    }

    private static MutableTreeNode node(String text, MutableTreeNode...children) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
      for (MutableTreeNode child : children) node.add(child);
      return node;
    }

    MyFrame() {
      super(JTreeDriverTest.class);
      add(decorate(dragTree));
      dragTree.addMouseListener(new Listener(popupMenu));
      add(decorate(dropTree));
      popupMenu.add(new JMenuItem("Hello"));
      setPreferredSize(new Dimension(600, 400));
    }

    private Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(TREE_SIZE);
      return scrollPane;
    }

    private static class Listener extends MouseAdapter {
      private final JPopupMenu popupMenu;

      Listener(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
      }

      @Override public void mouseReleased(MouseEvent e) {
        if (!e.isPopupTrigger()) return;
        Component c = e.getComponent();
        if (!(c instanceof JTree)) return;
        JTree tree = (JTree)c;
        int x = e.getX();
        int y = e.getY();
        int row = tree.getRowForLocation(x, y);
        if (row == 0) popupMenu.show(tree, x, y);
      }
    }

  }
}
