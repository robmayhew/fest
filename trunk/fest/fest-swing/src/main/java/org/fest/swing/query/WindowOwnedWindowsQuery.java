package org.fest.swing.query;

import java.awt.Window;
import java.util.List;

import static org.fest.util.Collections.list;

/**
 * Understands an action that returns all the windows that a given <code>{@link Window}</code> currently owns. This
 * query is <strong>not</strong> executed in the event dispatch thread.
 * @see Window#getOwnedWindows()
 *
 * @author Alex Ruiz 
 */
public final class WindowOwnedWindowsQuery {

  /**
   * Returns all the windows that the given <code>{@link Window}</code> currently owns. This action is
   * <strong>not</strong> executed in the event dispatch thread.
   * @param w the given <code>Window</code>.
   * @return all the windows that the given <code>Window</code> currently owns.
   * @see Window#getOwnedWindows()
   */
  public static List<Window> ownedWindowsOf(Window w) {
    return list(w.getOwnedWindows());
  }
  
  private WindowOwnedWindowsQuery() {}
}