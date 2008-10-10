package org.fest.swing.driver;

import java.awt.Container;

import javax.swing.JToolBar;

import org.fest.swing.core.GuiQuery;

import static org.fest.reflect.core.Reflection.field;
import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the docking source of a
 * <code>{@link JToolBar}</code>.
 * 
 * @author Alex Ruiz
 */
final class JToolBarDockingSourceQuery {
  
  static Container dockingSourceOf(final JToolBar toolBar) {
    return execute(new GuiQuery<Container>() {
      protected Container executeInEDT() {
        try {
          return field("dockingSource").ofType(Container.class).in(toolBar.getUI()).get();
        } catch (RuntimeException e) {
          return null;
        }
      }
    });
  }

  private JToolBarDockingSourceQuery() {}
}