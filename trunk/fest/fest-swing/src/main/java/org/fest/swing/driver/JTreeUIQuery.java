package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.plaf.TreeUI;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns a <code>{@link JTree}</code>'s current
 * UI.
 * @see JTree#getUI()
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTreeUIQuery {

  static TreeUI uiOf(final JTree tree) {
    return execute(new GuiQuery<TreeUI>() {
      protected TreeUI executeInEDT() {
        return tree.getUI();
      }
    });
  }

  JTreeUIQuery(final JTree tree) {}
}