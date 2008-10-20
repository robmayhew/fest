package org.fest.swing.query;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

/**
 * Understands an action that returns the pop-up menu associated with a <code>{@link JMenu}</code>. This query is 
 * <strong>not</strong> executed in the event dispatch thread.
 * @see JMenu#getPopupMenu()
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JMenuPopupMenuQuery {

  /**
   * Returns the pop-up menu associated with the given <code>{@link JMenu}</code>. This action is <strong>not</strong>
   * executed in the event dispatch thread.
   * @param menu the given <code>JMenu</code>.
   * @return the pop-up menu associated with the given <code>JMenu</code>.
   * @see JMenu#getPopupMenu()
   */
  public static JPopupMenu popupMenuOf(JMenu menu) {
    return menu.getPopupMenu();
  }

  private JMenuPopupMenuQuery() {}
}