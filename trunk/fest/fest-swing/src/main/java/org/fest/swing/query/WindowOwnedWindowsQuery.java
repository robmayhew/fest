package org.fest.swing.query;

import java.awt.Window;
import java.util.List;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Collections.list;

/**
 * Understands an action, executed in the event dispatch thread, that returns all the windows that a given 
 * <code>{@link Window}</code> currently owns.
 *
 * @author Alex Ruiz 
 */
public class WindowOwnedWindowsQuery extends GuiQuery<List<Window>> {

  private final Window w;

  /**
   * Returns all the windows that the given <code>{@link Window}</code> currently owns. This action is executed in
   * the event dispatch thread.
   * @param w the given <code>Window</code>.
   * @return all the windows that the given <code>Window</code> currently owns.
   */
  public static List<Window> ownedWindowsOf(Window w) {
    return execute(new WindowOwnedWindowsQuery(w));
  }
  
  WindowOwnedWindowsQuery(Window w) {
    this.w = w;
  }

  /**
   * Returns all the windows that this query's <code>{@link Window}</code> currently owns. This action is executed in
   * the event dispatch thread.
   * @return all the windows that this query's <code>Window</code> currently owns.
   */
  protected List<Window> executeInEDT() {
    return list(w.getOwnedWindows());
  }
}