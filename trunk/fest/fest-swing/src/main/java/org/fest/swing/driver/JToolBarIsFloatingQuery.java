package org.fest.swing.driver;

import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicToolBarUI;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a 
 * <code>{@link JToolBar}</code> is floating or not.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JToolBarIsFloatingQuery extends GuiQuery<Boolean> {
  
  private final BasicToolBarUI ui;

  static boolean isJToolBarFloating(BasicToolBarUI ui) {
    return execute(new JToolBarIsFloatingQuery(ui));
  }
  
  JToolBarIsFloatingQuery(BasicToolBarUI ui) {
    this.ui = ui;
  }

  protected Boolean executeInEDT() {
    return ui.isFloating();
  }
}