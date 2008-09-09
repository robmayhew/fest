package org.fest.swing.driver;

import javax.swing.JToolBar;
import javax.swing.plaf.ToolBarUI;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns a <code>{@link JToolBar}</code>'s current 
 * UI.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class JToolBarUIQuery extends GuiQuery<ToolBarUI> {
  
  private final JToolBar toolBar;

  static ToolBarUI uiOf(JToolBar toolBar) {
    return execute(new JToolBarUIQuery(toolBar));
  }
  
  JToolBarUIQuery(JToolBar toolBar) {
    this.toolBar = toolBar;
  }

  protected ToolBarUI executeInEDT() {
    return toolBar.getUI();
  }
}