package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the number of nodes selected in a
 * <code>{@link JTree}</code>.
 *
 * @author Alex Ruiz 
 */
class JTreeSelectionCountQuery extends GuiQuery<Integer> {
  
  private final JTree tree;

  static int selectionCountOf(final JTree tree) {
    return execute(new JTreeSelectionCountQuery(tree));
  }
  
  JTreeSelectionCountQuery(JTree tree) {
    this.tree = tree;
  }

  protected Integer executeInEDT() {
    return tree.getSelectionCount();
  }
}