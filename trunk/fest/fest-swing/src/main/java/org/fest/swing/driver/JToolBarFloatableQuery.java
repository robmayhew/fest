package org.fest.swing.driver;

import javax.swing.JToolBar;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

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
  
  JToolBarFloatableQuery(JToolBar toolBar) {
    this.toolBar = toolBar;
  }

  protected Boolean executeInEDT() {
    return toolBar.isFloatable();
  }
}