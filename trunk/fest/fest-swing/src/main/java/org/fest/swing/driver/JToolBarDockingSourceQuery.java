package org.fest.swing.driver;

import java.awt.Container;

import javax.swing.JToolBar;

import org.fest.swing.edt.GuiQuery;

import static org.fest.reflect.core.Reflection.field;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the docking source of a
 * <code>{@link JToolBar}</code>.
 * 
 * @author Alex Ruiz
 */
class JToolBarDockingSourceQuery extends GuiQuery<Container> {
  
  private final JToolBar toolBar;

  static Container dockingSourceOf(JToolBar toolBar) {
    return execute(new JToolBarDockingSourceQuery(toolBar));
  }

  JToolBarDockingSourceQuery(JToolBar toolBar) {
    this.toolBar = toolBar;
  }

  protected Container executeInEDT() throws Throwable {
    try {
      return field("dockingSource").ofType(Container.class).in(toolBar.getUI()).get();
    } catch (RuntimeException e) {
      return null;
    }
  }
}