package org.fest.swing.hierarchy;

import java.awt.Window;

import org.fest.swing.edt.GuiTask;

/**
 * Understands a task that disposes a <code>{@link Window}</code>. This task should be executed in the event dispatch
 * thread.
 *
 * @author Alex Ruiz 
 */
public class WindowDisposeTask extends GuiTask {
  
  private final Window w;

  /**
   * Creates and returns a <code>{@link WindowDisposeTask}</code>.
   * @param w the <code>Window</code> to dispose.
   * @return the created task.
   */
  public static WindowDisposeTask disposeTask(Window w) {
    return new WindowDisposeTask(w);
  }
  
  private WindowDisposeTask(Window w) {
    this.w = w;
  }

  /**
   * Disposes this query's <code>{@link Window}</code>. This action is executed in the event dispatch thread.
   */
  protected void executeInEDT() {
    w.dispose();
  }
}