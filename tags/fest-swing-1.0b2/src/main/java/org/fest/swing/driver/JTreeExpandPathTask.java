package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.edt.GuiTask;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands a task that ensures that the node identified by the specified path is expanded and viewable. This task is
 * executed in the event dispatch thread.
 *
 * @author Yvonne Wang
 */
final class JTreeExpandPathTask {

  static void expandPath(final JTree tree, final TreePath path) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.expandPath(path);
      }
    });
  }

  private JTreeExpandPathTask() {}
}