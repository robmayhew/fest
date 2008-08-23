package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the paths of all selected values in a
 * <code>{@link JTree}</code>.
 * 
 * @author Yvonne Wang
 */
class JTreeSelectionPathsQuery extends GuiQuery<TreePath[]> {
  
  private final JTree tree;

  static TreePath[] selectionPathsOf(JTree tree) {
    return execute(new JTreeSelectionPathsQuery(tree));
  }
  
  JTreeSelectionPathsQuery(JTree tree) {
    this.tree = tree;
  }

  protected TreePath[] executeInEDT() {
    return tree.getSelectionPaths();
  }
}