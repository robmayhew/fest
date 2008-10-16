package org.fest.swing.driver;

import javax.swing.JTree;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns number of mouse clicks needed to expand or
 * close a node in a <code>{@link JTree}</code>.
 * @see JTree#getToggleClickCount()
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTreeToggleClickCountQuery {

  static int toggleClickCountOf(final JTree tree) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return tree.getToggleClickCount();
      }
    });
  }

  private JTreeToggleClickCountQuery() {}
}