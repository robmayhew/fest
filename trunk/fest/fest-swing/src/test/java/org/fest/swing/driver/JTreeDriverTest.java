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

import org.fest.swing.core.*;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestTree;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.ComponentSetEnableTask.disable;
import static org.fest.swing.core.EventMode.ROBOT;
import static org.fest.swing.core.GuiActionRunner.execute;
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectNodeByRow(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearSelectionOf(dragTree);
    assertThat(selectionRowsOf(dragTree)).isNull();
    driver.selectRow(dragTree, 0);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(new int[] { 0 });
    driver.selectRow(dragTree, 1);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(new int[] { 1 });
    driver.selectRow(dragTree, 0);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(new int[] { 0 });
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectNodeByRowIfTreeIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragTree();
    driver.selectRow(dragTree, 0);
    assertThat(selectionCountOf(dragTree)).isZero();
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRowArrayIsNull() {
    int[] rows = null;
    driver.selectRows(dragTree, rows);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfRowArrayIsEmpty() {
    int[] rows = new int[0];
    driver.selectRows(dragTree, rows);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectNodesByRow(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearSelectionOf(dragTree);
    setDefaultSelectionModelTo(dragTree);
    assertThat(selectionRowsOf(dragTree)).isNull();
    int[] rows = { 0, 1, 2 };
    driver.selectRows(dragTree, rows);
    assertThat(selectionRowsOf(dragTree)).isEqualTo(rows);
  }

  private static int[] selectionRowsOf(final JTree tree) {
    return execute(new GuiQuery<int[]>() {
      protected int[] executeInEDT() {
        return tree.getSelectionRows();
      }
    });
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectNodesByRowIfTreeIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragTree();
    int[] rows = { 0, 1, 2 };
    driver.selectRows(dragTree, rows);
    assertThat(selectionCountOf(dragTree)).isZero();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldToggleNodeByRow(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    assertThat(isRowExpanded(dragTree, 1)).isFalse();
    driver.toggleRow(dragTree, 1);
    assertThat(isRowExpanded(dragTree, 1)).isTrue();
    driver.toggleRow(dragTree, 1);
    assertThat(isRowExpanded(dragTree, 1)).isFalse();
  }
  
  private static boolean isRowExpanded(final JTree tree, final int row) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return tree.isExpanded(row);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfPathNotFound(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      driver.selectPath(dragTree, "another");
      fail();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'another'");
    }
  }

  @Test(groups = GUI, dataProvider = "selectionPath")
  public void shouldSelectNodeByPath(String treePath, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearSelectionOf(dragTree);
    driver.selectPath(dragTree, treePath);
    assertThat(textOf(selectionPathOf(dragTree))).isEqualTo(treePath);
  }
  
  private static TreePath selectionPathOf(final JTree tree) {
    return execute(new GuiQuery<TreePath>() {
      protected TreePath executeInEDT() throws Throwable {
        return tree.getSelectionPath();
      }
    });
  }

  @DataProvider(name = "selectionPath")
  public Object[][] selectionPath() {
    return new Object[][] {
//        { "root/branch1", AWT },
        { "root/branch1", ROBOT },
//        { "root/branch1/branch1.2", AWT },
        { "root/branch1/branch1.2", ROBOT },
//        { "root", AWT },
        { "root", ROBOT }
    };
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectNodeByPathIfTreeIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragTree();
    driver.selectPath(dragTree, "root/branch1");
    assertThat(selectionCountOf(dragTree)).isZero();
  }

  @Test(groups = GUI, expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfPathArrayIsNull() {
    String[] paths = null;
    driver.selectPaths(dragTree, paths);
  }

  @Test(groups = GUI, expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfPathArrayIsEmpty() {
    String[] paths = new String[0];
    driver.selectPaths(dragTree, paths);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectNodesByPaths(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearSelectionOf(dragTree);
    setDefaultSelectionModelTo(dragTree);
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    driver.selectPaths(dragTree, paths);
    TreePath[] selectionPaths = selectionPathsOf(dragTree);
    assertThat(selectionPaths).hasSize(2);
    assertThat(textOf(selectionPaths[0])).isEqualTo(paths[0]);
    assertThat(textOf(selectionPaths[1])).isEqualTo(paths[1]);
  }

  private static void setDefaultSelectionModelTo(final JTree tree) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionModel(new DefaultTreeSelectionModel());
      }
    });
  }

  private static TreePath[] selectionPathsOf(final JTree tree) {
    return execute(new GuiQuery<TreePath[]>() {
      protected TreePath[] executeInEDT()  {
        return tree.getSelectionPaths();
      }
    });
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectNodesByPathsIfTreeIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragTree();
    String[] paths = { "root/branch1/branch1.1", "root/branch1/branch1.2" };
    driver.selectPaths(dragTree, paths);
    assertThat(selectionCountOf(dragTree)).isZero();
  }

  private static int selectionCountOf(final JTree tree) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return tree.getSelectionCount();
      }
    });
  }
  
  private void clearAndDisableDragTree() {
    clearSelectionOf(dragTree);
    disable(dragTree);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDragAndDropUsingGivenTreePaths(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.drag(dragTree, "root/branch1/branch1.1");
    driver.drop(dropTree, "root");
    DefaultMutableTreeNode branch1 = firstChildOfRootOf(dragTree);
    assertThat(childCountOf(branch1)).isEqualTo(1);
    assertThat(textOf(firstChildOf(branch1))).isEqualTo("branch1.2");
    DefaultMutableTreeNode root = rootOf(dropTree);
    assertThat(childCountOf(root)).isEqualTo(1);
    assertThat(textOf(firstChildOf(root))).isEqualTo("branch1.1");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDragAndDropUsingGivenRows(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.drag(dragTree, 2);
    driver.drop(dropTree, 0);
    DefaultMutableTreeNode sourceRoot = rootOf(dragTree);
    assertThat(childCountOf(sourceRoot)).isEqualTo(1);
    DefaultMutableTreeNode destinationRoot = rootOf(dropTree);
    assertThat(childCountOf(destinationRoot)).isEqualTo(1);
    assertThat(textOf(firstChildOf(destinationRoot))).isEqualTo("branch2");
  }

  private String textOf(TreePath path) {
    return textOf(path, driver.separator());
  }

  private static String textOf(final TreePath path, final String separator) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        if (path == null) return null;
        Object[] values = path.getPath();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
          if (i != 0) b.append(separator);
          Object value = values[i];
          if (value instanceof DefaultMutableTreeNode) 
            b.append(((DefaultMutableTreeNode)value).getUserObject());
        }
        return b.toString();
      }
    });
  }

  private static String textOf(final DefaultMutableTreeNode node) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return (String)node.getUserObject();
      }
    });
  }


  private static DefaultMutableTreeNode firstChildOfRootOf(final JTree tree) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        TreeNode root = (TreeNode)tree.getModel().getRoot();
        return (DefaultMutableTreeNode)root.getChildAt(0);
      }
    });
  }
  
  private static int childCountOf(final TreeNode node) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return node.getChildCount();
      }
    });
  }
  
  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRowIndexArrayIsNull() {
    driver.requireSelection(dragTree, (int[])null);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldPassIfRowIsSelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    DefaultMutableTreeNode root = rootOf(dragTree);
    TreePath path = new TreePath(array(root, firstChildOf(root)));
    setSelectionPath(dragTree, path);
    driver.requireSelection(dragTree, intArray(1));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldPassIfPathIsSelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    DefaultMutableTreeNode root = rootOf(dragTree);
    TreePath path = new TreePath(array(root, firstChildOf(root)));
    setSelectionPath(dragTree, path);
    driver.requireSelection(dragTree, array("root/branch1"));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldPassIfPathsAreSelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    DefaultMutableTreeNode root = rootOf(dragTree);
    TreeNode branch1 = firstChildOf(root);
    TreePath rootBranch1 = new TreePath(array(root, branch1));
    TreePath rootBranch1Branch11 = new TreePath(array(root, branch1, firstChildOf(branch1)));
    setSelectionPaths(dragTree, array(rootBranch1, rootBranch1Branch11));
    driver.requireSelection(dragTree, array("root/branch1", "root/branch1/branch1.1"));
  }

  private static DefaultMutableTreeNode firstChildOf(final TreeNode node) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return (DefaultMutableTreeNode)node.getChildAt(0);
      }
    });
  }

 
  private static void setSelectionPaths(final JTree tree, final TreePath[] paths) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionPaths(paths);
      }
    });
  }
  
  private static DefaultMutableTreeNode rootOf(final JTree tree) {
    return execute(new GuiQuery<DefaultMutableTreeNode>() {
      protected DefaultMutableTreeNode executeInEDT() {
        return (DefaultMutableTreeNode)tree.getModel().getRoot();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldFailIfExpectingSelectedRowAndTreeHasNoSelection(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setSelectionPath(dragTree, null);
    try {
      driver.requireSelection(dragTree, intArray(1));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selection'")
                             .contains("No selection");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldFailIfSelectedRowIsNotEqualToExpectedSelection(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    DefaultMutableTreeNode root = rootOf(dragTree);
    setSelectionPath(dragTree, new TreePath(array(root)));
    try {
      driver.requireSelection(dragTree, intArray(1));
      fail();
    } catch (AssertionError e) {
      System.out.println(e.getMessage());
      assertThat(e).message().contains("property:'selection'")
                             .contains("expected:<[[root, branch1]]> but was:<[[root]]>");
    }
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedPathArrayIsNull() {
    driver.requireSelection(dragTree, (String[])null);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldFailIfExpectingSelectedPathAndTreeHasNoSelection(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setSelectionPath(dragTree, null);
    try {
      driver.requireSelection(dragTree, array("root/branch1"));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selection'")
                             .contains("No selection");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldFailIfSelectedPathIsNotEqualToExpectedSelection(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    DefaultMutableTreeNode root = rootOf(dragTree);
    setSelectionPath(dragTree, new TreePath(array(root)));
    try {
      driver.requireSelection(dragTree, array("root/branch1"));
      fail();
    } catch (AssertionError e) {
      System.out.println(e.getMessage());
      assertThat(e).message().contains("property:'selection'")
                             .contains("expected:<[[root, branch1]]> but was:<[[root]]>");
    }
  }

  private static void setSelectionPath(final JTree tree, final TreePath path) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionPath(path);
      }
    });
  }
  
  private int[] intArray(int...values) {
    return values;
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldPassIfDoesNotHaveSelectionAsAnticipated(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearSelectionOf(dragTree);
    driver.requireNoSelection(dragTree);
  }

  private static void clearSelectionOf(final JTree tree) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.clearSelection();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldFailIfHasSelectionAndExpectingNoSelection(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstRowOf(dragTree);
    try {
      driver.requireNoSelection(dragTree);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selection'")
                             .contains("expected no selection but was:<[[root]]>");
    }
  }

  private static void selectFirstRowOf(final JTree tree) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setSelectionRow(0);
      }
    });
  }
  
  public void shouldPassIfTreeIsEditable() {
    setEditable(dragTree, true);
    driver.requireEditable(dragTree);
  }

  public void shouldFailIfTreeIsNotEditableAndExpectingEditable() {
    setEditable(dragTree, false);
    try {
      driver.requireEditable(dragTree);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfTreeIsNotEditable() {
    setEditable(dragTree, false);
    driver.requireNotEditable(dragTree);
  }

  public void shouldFailIfTreeIsEditableAndExpectingNotEditable() {
    setEditable(dragTree, true);
    try {
      driver.requireNotEditable(dragTree);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  private static void setEditable(final JTree tree, final boolean editable) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setEditable(editable);
      }
    });
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowPopupMenuAtRow(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    JPopupMenu popupMenu = driver.showPopupMenu(dragTree, 0);
    assertThat(popupMenu).isSameAs(frame.popupMenu);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowPopupMenuAtPath(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    JPopupMenu popupMenu = driver.showPopupMenu(dragTree, "root");
    assertThat(popupMenu).isSameAs(frame.popupMenu);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfSeparatorIsNull() {
    driver.separator(null);
  }

  public void shouldSetPathSeparator() {
    driver.separator("|");
    assertThat(driver.separator()).isEqualTo("|");
  }

  private static class MyFrame extends TestWindow {
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
