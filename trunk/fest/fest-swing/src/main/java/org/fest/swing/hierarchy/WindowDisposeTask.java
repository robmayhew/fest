package org.fest.swing.hierarchy;

import java.awt.Window;

import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that disposes a <code>{@link Window}</code>. This task is executed in the event dispatch thread.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class WindowDisposeTask {

  static void dispose(final Window w) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        w.dispose();
      }
    });
  }

  private WindowDisposeTask() {}
}