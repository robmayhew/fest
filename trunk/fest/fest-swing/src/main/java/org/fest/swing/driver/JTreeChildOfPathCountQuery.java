package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns how many children a node in a 
 * <code>{@link JTree}</code> has.
 *
 * @author Alex Ruiz
 */
class JTreeChildOfPathCountQuery extends GuiQuery<Integer> {
  
  private final JTree tree;
  private final TreePath path;

  static int childCount(JTree tree, TreePath path) {
    return execute(new JTreeChildOfPathCountQuery(tree, path));
  }
  
  JTreeChildOfPathCountQuery(JTree tree, TreePath path) {
    this.tree = tree;
    this.path = path;
  }

  protected Integer executeInEDT() {
    Object lastPathComponent = path.getLastPathComponent();
    return tree.getModel().getChildCount(lastPathComponent);
  }
}