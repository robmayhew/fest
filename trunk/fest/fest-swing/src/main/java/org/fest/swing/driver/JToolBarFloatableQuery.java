package org.fest.swing.driver;

import javax.swing.JToolBar;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a 
 * <code>{@link JToolBar}</code> is floatable or not.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JToolBarFloatableQuery {
  
  static boolean isFloatable(final JToolBar toolBar) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return toolBar.isFloatable();
      }
    });
  }
  
  private JToolBarFloatableQuery() {}
}