package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a row in a
 * <code>{@link JTree}</code> is the only one selected.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTreeSingleRowSelectedQuery {

  static boolean isSingleRowSelected(final JTree tree, final int row) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return tree.getLeadSelectionRow() == row && tree.getSelectionCount() == 1;
      }
    });
  }

  private JTreeSingleRowSelectedQuery() {}
}