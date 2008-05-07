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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.fest.swing.cell.BasicJTreeCellReader;
import org.fest.swing.cell.JTreeCellReader;
import org.fest.swing.core.Condition;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.exception.WaitTimedOutError;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.*;

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
  private static final String SELECTION_PATH_PROPERTY = "selectionPath";

  private static final String SEPARATOR = "/";

  private final JTreeLocation location;

  private JTreeCellReader cellReader;
  private String separator;

  /**
   * Creates a new </code>{@link JTreeDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTreeDriver(Robot robot) {
    super(robot);
    location = new JTreeLocation();
    cellReader(new BasicJTreeCellReader());
    separator(SEPARATOR);
  }

  /**
   * Change the open/closed state of the given row, if possible.
   * <p>
   * NOTE: a reasonable assumption is that the toggle control is just to the left of the row bounds and is roughly a
   * square the dimensions of the row height. Clicking in the center of that square should work.
   * </p>
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if is not possible to toggle row for the <code>JTree</code>'s <code>TreeUI</code>.
   */
  public void toggleRow(JTree tree, int row) {
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
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public void selectRow(JTree tree, int row) {
    selectPath(tree, location.pathFor(tree, row));
  }

  /**
   * Selects the given path, expanding parent nodes if necessary.
   * @param tree the target <code>JTree</code>.
   * @param path the path to select.
   * @throws LocationUnavailableException if the given path cannot be found.
   */
  public void selectPath(JTree tree, String path) {
    TreePath treePath = findMatchingPath(tree, path);
    selectPath(tree, treePath);
  }
  
  private void selectPath(JTree tree, TreePath path) {
    makeVisible(tree, path, false);
    Point p = location.pointAt(tree, path);
    int row = tree.getRowForLocation(p.x, p.y);
    if (alreadySelected(tree, row)) return;
    // NOTE: the row bounds *do not* include the expansion handle
    Rectangle rowBounds = tree.getRowBounds(row);
    scrollToVisible(tree, rowBounds);
    click(tree, new Point(rowBounds.x + 1, rowBounds.y + rowBounds.height / 2));
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
    if (tree.isExpanded(path)) return;
    // Use this method instead of a toggle action to avoid any component visibility requirements
    robot.invokeAndWait(new ExpandPathTask(tree, path));
  }

  private static class ExpandPathTask implements Runnable {
    private final JTree target;
    private final TreePath path;

    private ExpandPathTask(JTree target, TreePath path) {
      this.target = target;
      this.path = path;
    }

    public void run() {
      target.expandPath(path);
    }
  }

  private boolean waitForChildrenToShowUp(JTree tree, TreePath path) {
    try {
      pause(new UntilChildrenShowUp(tree, path, path.toString()), robot.settings().timeoutToBeVisible());
    } catch (WaitTimedOutError e) {
      throw new LocationUnavailableException(e.getMessage());
    }
    return true;
  }

  private static class UntilChildrenShowUp extends Condition {
    private final JTree tree;
    private final Object lastInPath;

    UntilChildrenShowUp(JTree tree, TreePath path, String pathDescription) {
      super(concat(pathDescription, " to show"));
      this.tree = tree;
      this.lastInPath = path.getLastPathComponent();
    }

    public boolean test() {
      return tree.getModel().getChildCount(lastInPath) != 0;
    }
  }

  /**
   * Shows a pop-up menu at the position of the node in the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @return a driver that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
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
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
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
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
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
    scrollToVisible(tree, tree.getPathBounds(path));
    drop(tree, location.pointAt(tree, path));
  }

  /**
   * Asserts that the given <code>{@link JTree}</code>'s selected row is equal to the given one.
   * @param tree the target <code>JTree</code>.
   * @param row the index of the row, expected to be selected.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given row.
   */
  public void requireSelection(JTree tree, int row) {
    TreePath selectionPath = tree.getPathForRow(row);
    requireSelection(tree, selectionPath);
  }
  
  /**
   * Asserts that the given <code>{@link JTree}</code>'s selected path is equal to the given one.
   * @param tree the target <code>JTree</code>.
   * @param path the given path, expected to be selected.
   * @throws LocationUnavailableException if the given path cannot be found.
   * @throws AssertionError if this fixture's <code>JTree</code> selection is not equal to the given path.
   * @see #separator(String)
   */
  public void requireSelection(JTree tree, String path) {
    TreePath matchingPath = findMatchingPath(tree, path);
    requireSelection(tree, matchingPath);
  }

  private void requireSelection(JTree tree, TreePath path) {
    if (tree.getSelectionCount() == 0) fail(concat("[", propertyName(tree, SELECTION_PROPERTY), "] No selection"));
    assertThat(tree.getSelectionPath()).as(propertyName(tree, SELECTION_PATH_PROPERTY)).isEqualTo(path);
  }
  
  /**
   * Asserts that the given <code>{@link JTree}</code> is editable.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> is not editable.
   */
  public void requireEditable(JTree tree) {
    assertThat(tree.isEditable()).as(editableProperty(tree)).isTrue();
  }

  /**
   * Asserts that the given <code>{@link JTree}</code> is not editable.
   * @param tree the given <code>JTree</code>.
   * @throws AssertionError if the <code>JTree</code> is editable.
   */
  public void requireNotEditable(JTree tree) {
    assertThat(tree.isEditable()).as(editableProperty(tree)).isFalse();
  }

  private static String editableProperty(JTree tree) {
    return propertyName(tree, EDITABLE_PROPERTY);
  }

  private TreePath findMatchingPath(JTree tree, String path) {
    String[] pathStrings = splitPath(path);
    TreeModel model = tree.getModel();
    List<Object> newPathValues = new ArrayList<Object>(pathStrings.length + 1);
    Object node = model.getRoot();
    int pathElementCount = pathStrings.length;
    for (int stringIndex = 0; stringIndex < pathElementCount; stringIndex++) {
      String pathString = pathStrings[stringIndex];
      Object match = null;
      if (stringIndex == 0 && tree.isRootVisible()) {
        if (!pathString.equals(value(tree, node))) throw pathNotFound(path);
        newPathValues.add(node);
        continue;
      }
      int childCount = model.getChildCount(node);
      for (int childIndex = 0; childIndex < childCount; childIndex++) {
        Object child = model.getChild(node, childIndex);
        if (pathString.equals(value(tree, child))) {
          if (match != null) throw multipleMatchingNodes(pathString, value(tree, node));
          match = child;
        }
      }
      if (match == null) throw pathNotFound(path);
      newPathValues.add(match);
      node = match;
    }
    return new TreePath(newPathValues.toArray());
  }
  
  private LocationUnavailableException pathNotFound(String path) {
    throw new LocationUnavailableException(concat("Unable to find path ", quote(path)));
  }

  private String[] splitPath(String path) {
    List<String> result = new ArrayList<String>();
    int separatorSize = separator.length();
    int index = 0;
    int pathSize = path.length();
    while (index < pathSize) {
      int separatorPosition = path.indexOf(separator, index);
      if (separatorPosition == -1) separatorPosition = pathSize;
      result.add(path.substring(index, separatorPosition));
      index = separatorPosition + separatorSize;
    }
    return result.toArray(new String[result.size()]);
  }

  private LocationUnavailableException multipleMatchingNodes(String matchingText, Object parentText) {
    throw new LocationUnavailableException(
        concat("There is more than one node with value ", quote(matchingText), " under ", quote(parentText)));
  }
  
  private Object value(JTree tree, Object modelValue) {
    return cellReader.valueAt(tree, modelValue);
  }
  
  /**
   * Returns the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   * @return the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   */
  public String separator() {
    return separator;
  }
  
  /**
   * Updates the separator to use when converting <code>{@link TreePath}</code>s to <code>String</code>s.
   * @param separator the new separator.
   */
  public void separator(String separator) {
    this.separator = separator;
  }
  
  /**
   * Updates the implementation of <code>{@link JTreeCellReader}</code> to use when comparing internal values of a
   * <code>{@link JTree}</code> and the values expected in a test.
   * @param cellReader the new <code>JTreeCellValueReader</code> to use.
   */
  public void cellReader(JTreeCellReader cellReader) {
    this.cellReader = cellReader;
  }

}
