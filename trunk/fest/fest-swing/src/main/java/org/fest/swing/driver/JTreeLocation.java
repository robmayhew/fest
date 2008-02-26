/*
 * Created on Jan 12, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;

import static java.lang.String.valueOf;

import static org.fest.swing.driver.TreeCell.lastInPath;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.Strings.match;
import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.concat;

/**
 * Understands a visible location on a <code>{@link JTree}</code>. A row index or a <code>{@link String}</code>ified
 * <code>{@link TreePath}</code> (i.e. each <code>{@link TreePath}</code> component is a <code>String</code>) or
 * a <code>{@link TreePath}</code> of <code>Object</code> may be used to indicate the location. Note that if a
 * <code>{@link TreePath}</code> is used, the entire path leading up to the designated node must be viewable at the
 * time the location is used.
 *
 * @author Alex Ruiz
 */
public final class JTreeLocation {

  /**
   * Converts the given row to an x, y coordinate.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @return the coordinates of the given row.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of 
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public Point pointAt(JTree tree, int row) {
    return pointAt(tree, pathFor(tree, row));
  }

  /**
   * Converts the given path to an x, y coordinate.
   * @param tree the target <code>JTree</code>.
   * @param path the given path.
   * @return the coordinates of the given path.
   * @throws LocationUnavailableException if any part of the path is not visible.
   */
  public Point pointAt(JTree tree, TreePath path) {
    TreePath realPath = findMatchingPath(tree, path);
    Rectangle pathBounds = tree.getPathBounds(realPath);
    if (pathBounds != null)
        return new Point(pathBounds.x + pathBounds.width / 2, pathBounds.y + pathBounds.height / 2);
    throw new LocationUnavailableException(concat("The tree path ", format(path.getPath()), " is not visible"));
  }

  public TreePath findMatchingPath(JTree tree, TreePath path) {
    Object[] pathValues = path.getPath();
    TreeCell rootPath = lastInPath(tree, pathToRoot(tree));
    if (!tree.isRootVisible() && !matches(pathValues[0], rootPath))
      pathValues = startWithInvisibleRoot(pathValues);
    TreePath treeNodePath = findMatchingTreeNodePath(pathValues, rootPath);
    if (treeNodePath != null) return treeNodePath;
    throw new LocationUnavailableException(concat("Unable to find tree path ", format(pathValues)));
  }

  private Object[] startWithInvisibleRoot(Object[] path) {
    Object[] tmp = new Object[path.length + 1];
    System.arraycopy(path, 0, tmp, 1, path.length);
    tmp[0] = null; // shouldn't be null by default?
    return tmp;
  }

  private TreePath pathToRoot(JTree tree) {
    return new TreePath(tree.getModel().getRoot());
  }

  private boolean matches(Object o, TreeCell cell) {
    if (o == null || o.equals(cell.value())) return true;
    return match(o.toString(), cell.textWithIndexIfDuplicated());
  }

  private TreePath findMatchingTreeNodePath(Object[] inputPath, TreeCell cell) {
    if (!matches(inputPath[0], cell)) return null; // no match
    if (inputPath.length == 1) return cell.path();
    Object[] subInputPath = removeFirstFrom(inputPath);
    for (Object child : cell) {
      TreePath newPath = findMatchingTreeNodePath(subInputPath, cell.cellFrom(child));
      if (newPath != null) return newPath;
    }
    return null;
  }

  private Object[] removeFirstFrom(Object[] array) {
    Object[] subArray = new Object[array.length - 1];
    System.arraycopy(array, 1, subArray, 0, subArray.length);
    return subArray;
  }

  /**
   * Returns the path for the given row.
   * @param tree the target <code>JTree</code>.
   * @param row the given row.
   * @return the path for the given row.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public TreePath pathFor(JTree tree, int row) {
    TreePath path = tree.getPathForRow(validated(tree, row));
    if (path != null) return path;
    throw new LocationUnavailableException(concat("Unable to find tree path for row [", valueOf(row), "]"));
  }

  private int validated(JTree tree, int row) {
    int rowCount = tree.getRowCount();
    if (row >= 0 && row < rowCount) return row;
    throw actionFailure(concat(
        "The given row (", valueOf(row), ") should be greater than or equal to 0 and less than ", valueOf(rowCount)));
  }
}
