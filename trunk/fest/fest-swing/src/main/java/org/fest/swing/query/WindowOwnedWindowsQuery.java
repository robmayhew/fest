package org.fest.swing.query;

import java.awt.Window;
import java.util.List;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Collections.list;

/**
 * Understands an action, executed in the event dispatch thread, that returns all the windows that a given 
 * <code>{@link Window}</code> currently owns.
 * @see Window#getOwnedWindows()
 *
 * @author Alex Ruiz 
 */
public final class WindowOwnedWindowsQuery {

  /**
   * Returns all the windows that the given <code>{@link Window}</code> currently owns. This action is executed in
   * the event dispatch thread.
   * @param w the given <code>Window</code>.
   * @return all the windows that the given <code>Window</code> currently owns.
   * @see Window#getOwnedWindows()
   */
  public static List<Window> ownedWindowsOf(final Window w) {
    return execute(new GuiQuery<List<Window>>() {
      protected List<Window> executeInEDT() {
        return list(w.getOwnedWindows());
      }
    });
  }
  
  private WindowOwnedWindowsQuery() {}
}