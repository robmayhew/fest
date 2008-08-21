package org.fest.swing.driver;

import java.awt.Point;

import javax.swing.JTree;

import org.fest.swing.core.GuiActionRunner;
import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the row in a <code>{@link JTree}</code>
 * for the specified location.
 *
 * @author Yvonne Wang
 */
class JTreeRowAtPointQuery extends GuiQuery<Integer> {

  private final Point location;
  private final JTree tree;

  static int rowAtPoint(JTree tree, Point location) {
    return GuiActionRunner.execute(new JTreeRowAtPointQuery(location, tree));
  }

  private JTreeRowAtPointQuery(Point location, JTree tree) {
    this.location = location;
    this.tree = tree;
  }

  protected Integer executeInEDT() {
    return tree.getRowForLocation(location.x, location.y);
  }
}