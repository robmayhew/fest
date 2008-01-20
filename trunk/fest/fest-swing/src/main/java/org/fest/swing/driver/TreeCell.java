/*
 * Created on Jan 14, 2008
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

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import static java.lang.String.valueOf;

import static org.fest.swing.util.Objects.toStringOf;
import static org.fest.swing.util.Strings.isDefaultToString;
import static org.fest.util.Strings.*;

/**
 * Understands a cell in a <code>{@link JTree}</code>.
 *
 * @author Alex Ruiz
 */
final class TreeCell implements Iterable<Object> {

  private static final boolean NODE_SELECTED = false;
  private static final boolean NODE_HAS_FOCUS = false;
  
  private final Object value;
  private final int row;
  private final JTree tree;
  private final TreePath path;
  
  /**
   * Creates a new <code>{@link TreeCell}</code> containing the last component in the given <code>{@link TreePath}</code>.
   * @param tree the <code>JTree</code> containing the cell of interest.
   * @param path the <code>TreePath</code> which last component will be the base for the created tree cell.
   * @return the created <code>TreeCell</code>.
   */
  static TreeCell lastInPath(JTree tree, TreePath path) {
    return new TreeCell(tree, path);
  }
  
  private TreeCell(JTree tree, TreePath path) {
    this.value = path.getLastPathComponent();
    this.row = tree.getRowForPath(path);
    this.tree = tree;
    this.path = path;
  }

  /**
   * Returns the <code>String</code> representation of this cell, adding an index at the end if more than one cells
   * in the same hierarchy level have the same <code>String</code> representation. 
   * <p>
   * For example, if a <code>{@link JTree}</code> has two nodes at the same level in the hierarchy with the text "Yoda", 
   * the <code>String</code> representation of the first one will be "Yoda", while the the <code>String</code> of the 
   * second one will be "Yoda[1]".
   * </p>
   * @return the <code>String</code> representation of this cell.
   */
  String textWithIndexIfDuplicated() {
    return addIndexIfDuplicatedTextFound(text());
  }

  /**
   * Returns the <code>String</code> representation of this cell.
   * @return the <code>String</code> representation of this cell.
   */
  String text() {
    String text = textFrom(cellRendererComponent());
    if (text != null) return text;
    text = valueToText();
    if (text != null) return text;
    return toStringOf(value);
  }

  private Component cellRendererComponent() {
    TreeCellRenderer r = tree.getCellRenderer();
    return r.getTreeCellRendererComponent(tree, value, NODE_SELECTED, expanded(), leaf(), row, NODE_HAS_FOCUS);
  }

  private String textFrom(Component cellRendererComponent) {
    if (!(cellRendererComponent instanceof JLabel)) return null;
    String text = ((JLabel)cellRendererComponent).getText();
    if (text != null) text = text.trim();
    if (isEmpty(text) || isDefaultToString(text)) return null;
    return text;
  }

  private String valueToText() {
    String text = tree.convertValueToText(value, NODE_SELECTED, expanded(), leaf(), row, NODE_HAS_FOCUS);
    if (isDefaultToString(text)) return null;
    return text;
  }
  
  private boolean expanded() { return tree.isExpanded(row); }
  
  private boolean leaf() { return model().isLeaf(value); }
  
  private String addIndexIfDuplicatedTextFound(String original) {
    int duplicateCount = duplicateCount(original);
    if (duplicateCount == 0) return original;
    return concat(original, "[", valueOf(duplicateCount), "]");
  }

  private int duplicateCount(String original) {
    TreePath parentPath = path.getParentPath();
    if (parentPath == null) return 0;
    Object parent = parentPath.getLastPathComponent();
    int lastChildIndex = model().getIndexOfChild(parent, value);
    int duplicateCount = 0;
    for (int i = 0; i < lastChildIndex; i++) {
      TreePath childPath = parentPath.pathByAddingChild(childOf(parent, i));
      TreeCell child = lastInPath(tree, childPath);
      if (original.equals(child.text())) duplicateCount++;
    }
    return duplicateCount;
  }
  
  private Object childOf(Object parent, int index) {
    return model().getChild(parent, index);   
  }
  
  private TreeModel model() { return tree.getModel(); }
  
  /**
   * Returns this cell's value.
   * @return this cell's value.
   */
  Object value() { return value; }

  /**
   * Returns this cell's path.
   * @return this cell's path.
   */
  TreePath path() { return path; }
  
  /**
   * Returns an iterator over the children of the value of this cell.
   * @return an Iterator over the children of the value of this cell.
   */
  public TreeNodeChildrenIterator iterator() {
    return new TreeNodeChildrenIterator(model(), value);
  }
  
  /**
   * Returns a new path containing all the elements of this object plus <code>child</code>. <code>child</code> will
   * be the last element of the newly created <code>{@link TreePath}</code>.
   * @param child the child to add to new path.
   * @return the created path.
   * @throws NullPointerException if <code>child</code> is <code>null</code>.
   */
  TreeCell cellFrom(Object child) {
    TreePath childPath = path.pathByAddingChild(child);
    return new TreeCell(tree, childPath);    
  }
}
