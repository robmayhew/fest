package org.fest.swing.query;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the pop-up menu associated with a
 * <code>{@link JMenu}</code>.
 * @see JMenu#getPopupMenu()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JMenuPopupMenuQuery {

  /**
   * Returns the pop-up menu associated with the given <code>{@link JMenu}</code>. This action is executed in the event
   * dispatch thread.
   * @param menu the given <code>JMenu</code>.
   * @return the pop-up menu associated with the given <code>JMenu</code>.
   * @see JMenu#getPopupMenu()
   */
  public static JPopupMenu popupMenuOf(final JMenu menu) {
    return execute(new GuiQuery<JPopupMenu>() {
      protected JPopupMenu executeInEDT() {
        return menu.getPopupMenu();
      }
    });
  }

  private JMenuPopupMenuQuery() {}
}