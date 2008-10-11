package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the paths of all selected values in a
 * <code>{@link JTree}</code>.
 * @see JTree#getSelectionPaths
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTreeSelectionPathsQuery {
  
  static TreePath[] selectionPathsOf(final JTree tree) {
    return execute(new GuiQuery<TreePath[]>() {
      protected TreePath[] executeInEDT() {
        return tree.getSelectionPaths();
      }
    });
  }
  
  private JTreeSelectionPathsQuery() {}
}