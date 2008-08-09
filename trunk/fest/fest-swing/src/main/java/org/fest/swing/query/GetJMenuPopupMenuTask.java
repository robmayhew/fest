package org.fest.swing.query;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the pop-up menu associated with a 
 * <code>{@link JMenu}</code>.
 *
 * @author Alex Ruiz 
 */
public class GetJMenuPopupMenuTask extends GuiQuery<JPopupMenu> {
  
  private final JMenu menu;

  /**
   * Returns the pop-up menu associated with the given <code>{@link JMenu}</code>. This action is executed in the event
   * dispatch thread.
   * @param menu the given <code>JMenu</code>.
   * @return the pop-up menu associated with the given <code>JMenu</code>.
   */
  public static JPopupMenu popupMenuOf(JMenu menu) {
    return new GetJMenuPopupMenuTask(menu).run();
  }
  
  private GetJMenuPopupMenuTask(JMenu menu) {
    this.menu = menu;
  }

  /**
   * Returns the pop-up menu associated with this task's <code>{@link JMenu}</code>. This action is executed in the
   * event dispatch thread.
   * @return the pop-up menu associated with this task's <code>JMenu</code>.
   */
  protected JPopupMenu executeInEDT() {
    return menu.getPopupMenu();
  }
}