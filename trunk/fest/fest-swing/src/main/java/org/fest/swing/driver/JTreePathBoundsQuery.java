package org.fest.swing.driver;

import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Rectangle}</code> that
 * the node at the specified row (in a <code>{@link JTree}</code>) is drawn.
 * 
 * @author Yvonne Wang
 */
class JTreePathBoundsQuery extends GuiQuery<Rectangle> {

  private final TreePath path;
  private final JTree tree;

  static Rectangle pathBoundsOf(JTree tree, TreePath path) {
    return execute(new JTreePathBoundsQuery(tree, path));
  }

  JTreePathBoundsQuery(JTree tree, TreePath path) {
    this.path = path;
    this.tree = tree;
  }

  protected Rectangle executeInEDT() {
    return tree.getPathBounds(path);
  }
}