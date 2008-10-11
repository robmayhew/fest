package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the number of nodes selected in a
 * <code>{@link JTree}</code>.
 *
 * @author Alex Ruiz 
 */
final class JTreeSelectionCountQuery {
  
  static int selectionCountOf(final JTree tree) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return tree.getSelectionCount();
      }
    });
  }
  
  private JTreeSelectionCountQuery() {}
}