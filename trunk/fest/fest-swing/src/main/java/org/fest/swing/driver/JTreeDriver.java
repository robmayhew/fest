/*
 * Created on Jan 12, 2008
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

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.*;
import org.fest.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.driver.CommonValidations.validateCellReader;
import static org.fest.swing.driver.JTreeChildrenShowUpCondition.untilChildrenShowUp;
import static org.fest.swing.driver.JTreeEditableQuery.isEditable;
import static org.fest.swing.driver.JTreeExpandPathTask.expandPathTask;
import static org.fest.swing.driver.JTreeExpandedPathQuery.isExpanded;
import static org.fest.swing.driver.JTreeMatchingPathQuery.matchingPathFor;
import static org.fest.swing.driver.JTreePathBoundsQuery.pathBoundsOf;
import static org.fest.swing.driver.JTreePathsForRowsQuery.pathsForRows;
import static org.fest.swing.driver.JTreeRowAtPointQuery.rowAtPoint;
import static org.fest.swing.driver.JTreeRowBoundsQuery.rowBoundsOf;
import static org.fest.swing.driver.JTreeSelectionCountQuery.selectionCountOf;
import static org.fest.swing.driver.JTreeSelectionPathsQuery.selectionPathsOf;
import static org.fest.swing.driver.JTreeSingleRowSelectedQuery.isSingleRowSelected;
import static org.fest.swing.driver.JTreeToggleClickCountQuery.toggleClickCountOf;
import static org.fest.swing.driver.JTreeToggleExpandStateTask.toggleExpandStateTask;
import static org.fest.swing.driver.JTreeUIQuery.uiOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JTree}</code>. Unlike <code>JTreeFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JTree}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 */
public class JTreeDriver extends JComponentDriver {

  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTION_PROPERTY = "selection";

  private final JTreeLocation location;
  private final JTreePathFinder pathFinder;
  
  /**
   * Creates a new </code>{@link JTreeDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTreeDriver(Robot robot) {
    super(robot);
    location = new JTreeLocation();
    pathFinder = new JTreePathFinder();
  }

  /**
   * Change the open/closed state of the given row, if possible.
   * <p>
   * NOTE: a reasonable assumption is that the toggle control is just to the left of the row bounds and is roughly a
   * square the dimensions of the row height. Clicking in the center of that square should work.
   * </p>
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if is not possible to toggle row for the <code>JTree</code>'s <code>TreeUI</code>.
   */
  public void toggleRow(JTree tree, int row) {
    if (!isEnabled(tree)) return;
    // Alternatively, we can reflect into the UI and do a single click on the appropriate expand location, but this is
    // safer.
    Point p = location.pointAt(tree, row);
    int toggleClickCount = toggleClickCountOf(tree);
    if (toggleClickCount != 0) {
      robot.click(tree, p, LEFT_BUTTON, toggleClickCount);
      return;
    }
    TreeUI treeUI = uiOf(tree);
    if (!(treeUI instanceof BasicTreeUI)) throw actionFailure(concat("Can't toggle row for ", treeUI));
    toggleExpandedState(tree, p);
  }

  private void toggleExpandedState(JTree tree, Point pathLocation) {
    robot.invokeAndWait(toggleExpandStateTask(tree, pathLocation));
  }

  /**
   * Selects the given rows.
   * @param tree the target <code>JTree</code>.
   * @param rows the rows to select.
   * @throws NullPointerException if the array of rows is <code>null</code>.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   * visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for any of the given rows cannot be found.
   */
  public void selectRows(final JTree tree, final int[] rows) {
    if (rows == null) throw new NullPointerException("The array of rows should not be null");
    if (isEmptyArray(rows)) throw new IllegalArgumentException("The array of rows should not be empty");
    if (!isEnabled(tree)) return;
    new MultipleSelectionTemplate(robot) {
      @Override int elementCount() {
        return rows.length;
      }

      @Override void selectElement(int index) {
        selectRow(tree, rows[index]);
      }
    }.multiSelect();
  }

  private boolean isEmptyArray(int[] array) { return array == null || array.length == 0; }

  /**
   * Selects the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the row to select.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public void selectRow(JTree tree, int row) {
    if (!isEnabled(tree)) return;
    selectPath(tree, location.pathFor(tree, row));
  }

  /**
   * Selects the given paths, expanding parent nodes if necessary.
   * @param tree the target <code>JTree</code>.
   * @param paths the paths to select.
   * @throws ActionFailedException if the array of paths is <code>null</code> or empty.
   * @throws LocationUnavailableException if any the given path cannot be found.
   */
  public void selectPaths(final JTree tree, final String[] paths) {
    if (Arrays.isEmpty(paths)) throw actionFailure("The array of paths should not be null or empty");
    if (!isEnabled(tree)) return;
    new MultipleSelectionTemplate(robot) {
      int elementCount() {
        return paths.length;
      }

      void selectElement(int index) {
        selectPath(tree, paths[index]);
      }
    }.multiSelect();
  }

  /**
   * Selects the given path, expanding parent nodes if necessary.
   * @param tree the target <code>JTree</code>.
   * @param path the path to select.
   * @throws LocationUnavailableException if the given path cannot be found.
   */
  public void selectPath(JTree tree, String path) {
    if (!isEnabled(tree)) return;
    TreePath treePath = findMatchingPath(tree, path);
    selectPath(tree, treePath);
  }

  private void selectPath(JTree tree, TreePath path) {
    makeVisible(tree, path, false);
    int row = rowAtPoint(tree, location.pointAt(tree, path));
    if (isSingleRowSelected(tree, row)) return;
    // NOTE: the row bounds *do not* include the expansion handle
    Rectangle rowBounds = rowBoundsOf(tree, row);
    scrollToVisible(tree, rowBounds);
    click(tree, new Point(rowBounds.x + 1, rowBounds.y + rowBounds.height / 2));
  }

  /**
   * Matches, makes visible, and expands the path one component at a time, from uppermost ancestor on down, since
   * children may be lazily loaded/created.
   * @param tree the target <code>JTree</code>.
   * @param path the tree path to make visible.
   * @param expandWhenFound indicates if nodes should be expanded or not when found.
   * @return if it was necessary to make visible and/or expand a node in the path.
   */
  private boolean makeVisible(JTree tree, TreePath path, boolean expandWhenFound) {
    boolean changed = false;
    if (path.getPathCount() > 1) changed = makeParentVisible(tree, path);
    if (!expandWhenFound) return changed;
    expand(tree, path);
    waitForChildrenToShowUp(tree, path);
    return true;
  }

  private boolean makeParentVisible(JTree tree, TreePath path) {
    boolean changed = makeVisible(tree, path.getParentPath(), true);
    if (changed) robot.waitForIdle();
    return changed;
  }

  private void expand(JTree tree, TreePath path) {
    if (isExpanded(tree, path)) return;
    // Use this method instead of a toggle action to avoid any component visibility requirements
    robot.invokeAndWait(expandPathTask(tree, path));
  }

  private void waitForChildrenToShowUp(JTree tree, TreePath path) {
    int timeout = robot.settings().timeoutToBeVisible();
    try {
      pause(untilChildrenShowUp(tree, path), timeout);
    } catch (WaitTimedOutError e) {
      throw new LocationUnavailableException(e.getMessage());
    }
  }

  /**
   * Shows a pop-up menu at the position of the node in the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @return a driver that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public JPopupMenu showPopupMenu(JTree tree, int row) {
    return robot.showPopupMenu(tree, location.pointAt(tree, row));
  }

  /**
   * Shows a pop-up menu at the position of the last node in the given path.
   * @param tree the target <code>JTree</code>.
   * @param path the given path.
   * @return a driver that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @see #separator(String)
   */
  public JPopupMenu showPopupMenu(JTree tree, String path) {
    return robot.showPopupMenu(tree, location.pointAt(tree, findMatchingPath(tree, path)));
  }

  /**
   * Starts a drag operation at the location of the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public void drag(JTree tree, int row) {
    drag(tree, location.pathFor(tree, row));
  }

  /**
   * Ends a drag operation at the location of the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(JTree tree, int row) {
    drop(tree, location.pathFor(tree, row));
  }

  /**
   * Starts a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param tree the target <code>JTree</code>.
   * @param path the given path.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @see #separator(String)
   */
  public void drag(JTree tree, String path) {
    drag(tree, findMatchingPath(tree, path));
  }

  /**
   * Ends a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param tree the target <code>JTree</code>.
   * @param path the given path.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   * @see #separator(String)
   */
  public void drop(JTree tree, String path) {
    drop(tree, findMatchingPath(tree, path));
  }

  private void drag(JTree tree, TreePath path) {
    selectPath(tree, path);
    drag(tree, location.pointAt(tree, path));
  }

  private void drop(JTree tree, TreePath path) {
    scrollToVisible(tree, pathBoundsOf(tree, path));
    drop(tree, location.pointAt(tree, path));
  }

  /**
   * Asserts that the given <code>{@link JTree}</code>'s selected rows are equal to the given one.
   * @param tree the target <code>JTree</code>.
   * @param rows the indices of the rows, expected to be selected.
   * @throws NullPointerException if the array of row indices is <code>null</code>.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given rows.
   */
  public void requireSelection(JTree tree, int[] rows) {
    if (rows == null) throw new NullPointerException("The array of row indices should not be null");
    requireSelection(tree, pathsForRows(tree, rows));
  }

  /**
   * Asserts that the given <code>{@link JTree}</code>'s selected paths are equal to the given one.
   * @param tree the target <code>JTree</code>.
   * @param paths the given paths, expected to be selected.
   * @throws NullPointerException if the array of paths is <code>null</code>.
   * @throws LocationUnavailableException if any of the given paths cannot be found.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given paths.
   * @see #separator(String)
   */
  public void requireSelection(JTree tree, String[] paths) {
    if (paths == null) throw new NullPointerException("The array of paths should not be null");
    int pathCount = paths.length;
    TreePath[] matchingPaths = new TreePath[pathCount];
    for (int i = 0; i < pathCount; i++)
      matchingPaths[i] = findMatchingPath(tree, paths[i]);
    requireSelection(tree, matchingPaths);
  }

  private void requireSelection(JTree tree, TreePath[] paths) {
    TreePath[] selectionPaths = selectionPathsOf(tree);
    if (Arrays.isEmpty(selectionPaths)) fail(concat("[", selectionProperty(tree), "] No selection"));
    assertThat(selectionPaths).as(selectionProperty(tree)).isEqualTo(paths);
  }
  
  /**
   * Asserts that the given <code>{@link JTree}</code> does not have any selection.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> has a selection.
   */
  public void requireNoSelection(JTree tree) {
    if (selectionCountOf(tree) == 0) return;
    String message = concat(
        "[", selectionProperty(tree), "] expected no selection but was:<", format(selectionPathsOf(tree)), ">");
    fail(message);
  }

  private String selectionProperty(JTree tree) {
    return propertyName(tree, SELECTION_PROPERTY);
  }

  /**
   * Asserts that the given <code>{@link JTree}</code> is editable.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> is not editable.
   */
  public void requireEditable(JTree tree) {
    assertEditable(tree, true);
  }

  /**
   * Asserts that the given <code>{@link JTree}</code> is not editable.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> is editable.
   */
  public void requireNotEditable(JTree tree) {
    assertEditable(tree, false);
  }

  private void assertEditable(JTree tree, boolean editable) {
    assertThat(isEditable(tree)).as(editableProperty(tree)).isEqualTo(editable);
  }
  
  private static String editableProperty(JTree tree) {
    return propertyName(tree, EDITABLE_PROPERTY);
  }

  private TreePath findMatchingPath(JTree tree, String path) {
    try {
      return matchingPathFor(tree, path, pathFinder);
    } catch (UnexpectedException e) {
      Throwable cause = e.getCause();
      if (cause instanceof RuntimeException) throw (RuntimeException)cause;
      throw e;
    }
  }

  /**
   * Returns the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   * @return the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   */
  public String separator() {
    return pathFinder.separator();
  }

  /**
   * Updates the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   * @param newSeparator the new separator.
   * @throws NullPointerException if the given separator is <code>null</code>.
   */
  public void separator(String newSeparator) {
    if (newSeparator == null) throw new NullPointerException("The path separator should not be null");
    pathFinder.separator(newSeparator);
  }

  /**
   * Updates the implementation of <code>{@link JTreeCellReader}</code> to use when comparing internal values of a
   * <code>{@link JTree}</code> and the values expected in a test.
   * @param newCellReader the new <code>JTreeCellValueReader</code> to use.
   * @throws NullPointerException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(JTreeCellReader newCellReader) {
    validateCellReader(newCellReader);
    pathFinder.cellReader(newCellReader);
  }
}
