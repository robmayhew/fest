package org.fest.swing.driver;

import java.awt.Point;

import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiTask;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.method;

/**
 * Understands a task that uses reflection to toggle the "expand state" of a node in a given
 * <code>{@link JTextComponent}</code>. This task should be executed in the event dispatch thread.
 *
 * @author Yvonne Wang
 */
class JTreeToggleExpandStateTask extends GuiTask {

  private final JTree tree;
  private final Point pathLocation;

  static JTreeToggleExpandStateTask toggleExpandStateTask(JTree tree, Point pathLocation) {
    return new JTreeToggleExpandStateTask(tree, pathLocation);
  }

  private JTreeToggleExpandStateTask(JTree tree, Point pathLocation) {
    this.pathLocation = pathLocation;
    this.tree = tree;
  }

  protected void executeInEDT() {
    TreePath path = tree.getPathForLocation(pathLocation.x, pathLocation.y);
    TreeUI treeUI = tree.getUI();
    assertThat(treeUI).isInstanceOf(BasicTreeUI.class);
    method("toggleExpandState").withParameterTypes(TreePath.class).in(treeUI).invoke(path);
  }
}