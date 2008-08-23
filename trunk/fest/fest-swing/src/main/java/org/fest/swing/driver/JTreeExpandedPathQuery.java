package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether the node (in a
 * <code>{@link JTree}</code>) identified by the given path is expanded or not.
 *
 * @author Yvonne Wang
 */
class JTreeExpandedPathQuery extends GuiQuery<Boolean> {

  private final JTree tree;
  private final TreePath path;

  static boolean isExpanded(JTree tree, TreePath path) {
    return execute(new JTreeExpandedPathQuery(tree, path));
  }

  JTreeExpandedPathQuery(JTree tree, TreePath path) {
    this.tree = tree;
    this.path = path;
  }

  protected Boolean executeInEDT() {
    return tree.isExpanded(path);
  }
}