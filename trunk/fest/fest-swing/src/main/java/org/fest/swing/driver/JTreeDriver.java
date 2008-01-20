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

import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import org.fest.swing.core.Condition;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.exception.WaitTimedOutError;

import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.Settings.timeoutToBeVisible;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JTree}</code>. Unlike <code>JTreeFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JTree}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 */
public final class JTreeDriver {

  private final RobotFixture robot;
  private final JTree tree;
  private final JTreeLocation location;
  private final DragAndDropDriver dragAndDrop;

  /**
   * Creates a new </code>{@link JTreeDriver}</code>.
   * @param robot the robot to use to simulate user input.
   * @param tree the target <code>JTree</code>.
   */
  public JTreeDriver(RobotFixture robot, JTree tree) {
    this.robot = robot;
    this.tree = tree;
    location = new JTreeLocation(tree);
    dragAndDrop = new DragAndDropDriver(robot, tree);
  }

  /**
   * Change the open/closed state of the given row, if possible.
   * <p>
   * NOTE: a reasonable assumption is that the toggle control is just to the left of the row bounds and is roughly a
   * square the dimensions of the row height. Clicking in the center of that square should work.
   * </p>
   * @param row the given row.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if is not possible to toggle row for the <code>JTree</code>'s <code>TreeUI</code>.
   */
  public void toggleRow(int row) {
    // Alternatively, we can reflect into the UI and do a single click on the appropriate expand location, but this is 
    // safer.
    Point p = location.pointAt(row);
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
   * @param row the row to select.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public void selectRow(int row) {
    selectPath(location.pathFor(row));
  }
   
  /**
   * Selects the given path, expanding parent nodes if necessary.
   * @param path the path to select.
   * @throws LocationUnavailableException if any part of the path is not visible.
   */
  public void selectPath(TreePath path) {
    makeVisible(path, false);
    Point p = location.pointAt(path);
    int row = tree.getRowForLocation(p.x, p.y);
    if (alreadySelected(row)) return; 
    // NOTE: the row bounds *do not* include the expansion handle
    Rectangle rowBounds = tree.getRowBounds(row);
    robot.click(tree, new Point(rowBounds.x + 1, rowBounds.y + rowBounds.height / 2));
  }

  private boolean alreadySelected(int row) {
    return tree.getLeadSelectionRow() == row && tree.getSelectionCount() == 1;
  }

  /**
   * Matches, makes visible, and expands the path one component at a time, from uppermost ancestor on down, since
   * children may be lazily loaded/created.
   * @param path the tree path to make visible.
   * @param expandWhenFound indicates if nodes should be expanded or not when found.
   * @return if it was necessary to make visible and/or expand a node in the path.
   */
  private boolean makeVisible(TreePath path, boolean expandWhenFound) {
    boolean changed = false;
    if (path.getPathCount() > 1) changed = makeParentVisible(path);
    if (!expandWhenFound) return changed;
    TreePath realPath = location.findMatchingPath(path);
    expand(realPath);
    waitForChildrenToShowUp(realPath, path.toString());
    return true;
  }

  private boolean makeParentVisible(final TreePath path) {
    boolean changed = makeVisible(path.getParentPath(), true);
    if (changed) robot.waitForIdle();
    return changed;
  }

  private void expand(final TreePath path) {
    if (tree.isExpanded(path)) return;
    // Use this method instead of a toggle action to avoid any component visibility requirements
    robot.invokeAndWait(new Runnable() {
      public void run() { tree.expandPath(path); }
    });
  }

  private boolean waitForChildrenToShowUp(TreePath path, String pathDescription) {
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
   * @param row the given row.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public void drag(int row) {
    drag(location.pathFor(row));
  }

  /**
   * Ends a drag operation at the location of the given row.
   * @param row the given row.
   * @throws LocationUnavailableException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(int row) {
    drop(location.pathFor(row));
  }
  
  /**
   * Starts a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param path the given <code>TreePath</code>.
   * @throws LocationUnavailableException if any part of the path is not visible.
   */
  public void drag(TreePath path) {
    selectPath(path);
    dragAndDrop.drag(location.pointAt(path));
  }

  /**
   * Ends a drag operation at the location of the given <code>{@link TreePath}</code>.
   * @param path the given <code>TreePath</code>.
   * @throws LocationUnavailableException if any part of the path is not visible.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(TreePath path) {
    dragAndDrop.drop(location.pointAt(path));
  }
}
