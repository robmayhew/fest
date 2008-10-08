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
import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that uses reflection to toggle the "expand state" of a node in a given
 * <code>{@link JTextComponent}</code>. This task is executed in the event dispatch thread.
 *
 * @author Yvonne Wang
 */
final class JTreeToggleExpandStateTask {

  static void toggleExpandState(final JTree tree, final Point pathLocation) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        TreePath path = tree.getPathForLocation(pathLocation.x, pathLocation.y);
        TreeUI treeUI = tree.getUI();
        assertThat(treeUI).isInstanceOf(BasicTreeUI.class);
        method("toggleExpandState").withParameterTypes(TreePath.class).in(treeUI).invoke(path);
      }
    });
  }

  private JTreeToggleExpandStateTask() {}
}