package org.fest.swing.driver;

import java.awt.Frame;
import java.awt.Window;

import javax.swing.JToolBar;
import javax.swing.plaf.ToolBarUI;
import javax.swing.plaf.basic.BasicToolBarUI;

import org.fest.swing.edt.GuiQuery;

import static javax.swing.SwingUtilities.getWindowAncestor;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a 
 * <code>{@link JToolBar}</code> is floating or not.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JToolBarIsFloatingQuery {
  
  static boolean isJToolBarFloating(final JToolBar toolBar) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        ToolBarUI ui = toolBar.getUI();
        if (ui instanceof BasicToolBarUI) return ((BasicToolBarUI)ui).isFloating();
        // Have to guess; probably ought to check for sibling components
        Window w = getWindowAncestor(toolBar);
        return !(w instanceof Frame) && toolBar.getParent().getComponentCount() == 1;
      }
    });
  }
  
  private JToolBarIsFloatingQuery() {}
}