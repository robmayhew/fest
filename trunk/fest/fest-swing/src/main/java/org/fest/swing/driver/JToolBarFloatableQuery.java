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
class JToolBarFloatableQuery extends GuiQuery<Boolean> {
  
  private final JToolBar toolBar;

  static boolean isFloatable(JToolBar toolBar) {
    return execute(new JToolBarFloatableQuery(toolBar));
  }
  
  private JToolBarFloatableQuery(JToolBar toolBar) {
    this.toolBar = toolBar;
  }

  protected Boolean executeInEDT() throws Throwable {
    return toolBar.isFloatable();
  }
}