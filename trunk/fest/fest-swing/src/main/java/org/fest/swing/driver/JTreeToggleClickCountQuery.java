package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns number of mouse clicks needed to expand or
 * close a node in a <code>{@link JTree}</code>
 *
 * @author Yvonne Wang
 */
class JTreeToggleClickCountQuery extends GuiQuery<Integer> {

  private final JTree tree;

  static int toggleClickCountOf(JTree tree) {
    return execute(new JTreeToggleClickCountQuery(tree));
  }

  private JTreeToggleClickCountQuery(JTree tree) {
    this.tree = tree;
  }

  protected Integer executeInEDT() {
    return tree.getToggleClickCount();
  }
}