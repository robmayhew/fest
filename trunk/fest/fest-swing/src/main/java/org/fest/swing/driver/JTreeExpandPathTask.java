package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that ensures that the node identified by the specified path is expanded and viewable. This task
 * should be executed in the event dispatch thread.
 * 
 * @author Yvonne Wang
 */
class JTreeExpandPathTask extends GuiTask {

  private final JTree tree;
  private final TreePath path;

  static JTreeExpandPathTask expandPathTask(JTree tree, TreePath path) {
    return new JTreeExpandPathTask(tree, path);
  }

  private JTreeExpandPathTask(JTree tree, TreePath path) {
    this.tree = tree;
    this.path = path;
  }

  protected void executeInEDT() {
    tree.expandPath(path);
  }
}