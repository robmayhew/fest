package org.fest.swing.driver;

import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Rectangle}</code> that
 * the node at the specified row (in a <code>{@link JTree}</code>) is drawn.
 * @see JTree#getPathBounds(TreePath)
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTreePathBoundsQuery {

  static Rectangle pathBoundsOf(final JTree tree, final TreePath path) {
    return execute(new GuiQuery<Rectangle>() {
      protected Rectangle executeInEDT() {
        return tree.getPathBounds(path);
      }
    });
  }

  private JTreePathBoundsQuery() {}
}