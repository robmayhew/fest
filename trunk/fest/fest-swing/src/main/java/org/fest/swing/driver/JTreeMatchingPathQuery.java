package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that finds a path in a <code>{@link JTree}</code> 
 * that matches a given <code>String</code>.
 *
 * @author Alex Ruiz
 * 
 * @see JTreePathFinder
 */
class JTreeMatchingPathQuery extends GuiQuery<TreePath> {
  
  private final String path;
  private final JTree tree;
  private final JTreePathFinder pathFinder;

  static TreePath matchingPathFor(JTree tree, String path, JTreePathFinder pathFinder) {
    return execute(new JTreeMatchingPathQuery(tree, path, pathFinder));
  }
  
  JTreeMatchingPathQuery(JTree tree, String path, JTreePathFinder pathFinder) {
    this.path = path;
    this.tree = tree;
    this.pathFinder = pathFinder;
  }

  protected TreePath executeInEDT() {
    return pathFinder.findMatchingPath(tree, path);
  }
}