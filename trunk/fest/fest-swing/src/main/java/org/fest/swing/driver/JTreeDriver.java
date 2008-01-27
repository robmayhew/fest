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

import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.Settings.timeoutToBeVisible;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.concat;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import org.fest.swing.core.Condition;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user input on a <code>{@link JTree}</code>. Unlike <code>JTreeFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JTree}</code>s. This class is intended for internal
 * use only.
 *
 * <p>
 * Adapted from <code>abbot.tester.JTreeTester</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public class JTreeDriver extends JComponentDriver {

  private final JTreeLocation location;

  /**
   * Creates a new </code>{@link JTreeDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTreeDriver(RobotFixture robot) {
    super(robot);
    location = new JTreeLocation();
  }

  /**
   * Change the open/closed state of the given row, if possible.
   * <p>
   * NOTE: a reasonable assumption is that the toggle control is just to the left of the row bounds and is roughly a
   * square the dimensions of the row height. Clicking in the center of that square should work.
   * </p>
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if is not possible to toggle row for the <code>JTree</code>'s <code>TreeUI</code>.
   */
  public final void toggleRow(JTree tree, int row) {
    // Alternatively, we can reflect into the UI and do a single click on the appropriate expand location, but this is
    // safer.
    Point p = location.pointAt(tree, row);
    int toggleClickCount = tree.getToggleClickCount();
    if (toggleClickCount != 0) {
      robot.click(tree, p, LEFT_BUTTON, toggleClickCount);
      return;
    }
    TreeUI treeUI = tree.getUI();
    if (!(treeUI instanceof BasicTreeUI)) throw actionFailure(concat("Can't toggle row for ", treeUI));
    TreePath path = tree.getPathForLocation(p.x, p.y);
    method("toggleExpandState").withParameterTypes(TreePath.class).in(treeUI).invoke(path);
  }

  /**
   * Selects the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the row to select.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public final void selectRow(JTree tree, int row) {
    selectPath(tree, location.pathFor(tree, row));
  }

  /**
   * Selects the given path, expanding parent nodes if necessary.
   * @param tree the target <code>JTree</code>.
   * @param path the path to select.
   * @throws LocationUnavailableException if any part of the path is not visible.
   */
  public final void selectPath(JTree tree, TreePath path) {
    makeVisible(tree, path, false);
    Point p = location.pointAt(tree, path);
    int row = tree.getRowForLocation(p.x, p.y);
    if (alreadySelected(tree, row)) return;
    // NOTE: the row bounds *do not* include the expansion handle
    Rectangle rowBounds = tree.getRowBounds(row);
    robot.click(tree, new Point(rowBounds.x + 1, rowBounds.y + rowBounds.height / 2));
  }

  private boolean alreadySelected(JTree tree, int row) {
    return tree.getLeadSelectionRow() == row && tree.getSelectionCount() == 1;
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
    TreePath realPath = location.findMatchingPath(tree, path);
    expand(tree, realPath);
    waitForChildrenToShowUp(tree, realPath, path.toString());
    return true;
  }

  private boolean makeParentVisible(JTree tree, TreePath path) {
    boolean changed = makeVisible(tree, path.getParentPath(), true);
    if (changed) robot.waitForIdle();
    return changed;
  }

  private void expand(final JTree tree, final TreePath path) {
    if (tree.isExpanded(path)) return;
    // Use this method instead of a toggle action to avoid any component visibility requirements
    robot.invokeAndWait(new Runnable() {
      public void run() { tree.expandPath(path); }
    });
  }

  private boolean waitForChildrenToShowUp(final JTree tree, TreePath path, String pathDescription) {
    final Object lastInPath = path.getLastPathComponent();
    try {
      pause(new Condition(concat(pathDescription, " to show")) {
        public boolean test() {
          return tree.getModel().getChildCount(lastInPath) != 0;
        }
      }, timeoutToBeVisible());
    } catch (WaitTimedOutError e) {
      throw new LocationUnavailableException(e.getMessage());
    }
    return true;
  }

  /**
   * Starts a drag operation at the location of the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public final void drag(JTree tree, int row) {
    drag(tree, location.pathFor(tree, row));
  }

  /**
   * Ends a drag operation at the location of the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public final void drop(JTree tree, int row) {
    drop(tree, location.pathFor(tree, row));
  }

  /**
   * Starts a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param tree the target <code>JTree</code>.
   * @param path the given <code>TreePath</code>.
   * @throws LocationUnavailableException if any part of the path is not visible.
   */
  public final void drag(JTree tree, TreePath path) {
    selectPath(tree, path);
    drag(tree, location.pointAt(tree, path));
  }

  /**
   * Ends a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param tree the target <code>JTree</code>.
   * @param path the given <code>TreePath</code>.
   * @throws LocationUnavailableException if any part of the path is not visible.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public final void drop(JTree tree, TreePath path) {
    drop(tree, location.pointAt(tree, path));
  }
}
