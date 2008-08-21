package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a row in a
 * <code>{@link JTree}</code> is the only one selected.
 *
 * @author Yvonne Wang
 */
class JTreeSingleRowSelectedQuery extends GuiQuery<Boolean> {

  private final JTree tree;
  private final int row;

  static boolean isSingleRowSelected(JTree tree, int row) {
    return execute(new JTreeSingleRowSelectedQuery(tree, row));
  }

  private JTreeSingleRowSelectedQuery(JTree tree, int row) {
    this.tree = tree;
    this.row = row;
  }

  protected Boolean executeInEDT() {
    return tree.getLeadSelectionRow() == row && tree.getSelectionCount() == 1;
  }
}