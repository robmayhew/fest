package org.fest.swing.driver;

import java.awt.Rectangle;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Rectangle}</code> that
 * the node at the specified row (in a <code>{@link JTree}</code>) is drawn.
 *
 * @author Yvonne Wang
 */
class JTreeRowBoundsQuery extends GuiQuery<Rectangle> {
  
  private final JTree tree;
  private final int row;

  static Rectangle rowBoundsOf(JTree tree, int row) {
    return execute(new JTreeRowBoundsQuery(tree, row));
  }
  
  private JTreeRowBoundsQuery(JTree tree, int row) {
    this.tree = tree;
    this.row = row;
  }

  protected Rectangle executeInEDT() {
    return tree.getRowBounds(row);
  }
}