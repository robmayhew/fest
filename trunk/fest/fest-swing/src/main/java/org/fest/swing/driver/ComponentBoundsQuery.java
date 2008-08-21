package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Rectangle;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the bounds of a
 * <code>{@link Component}</code>.
 *
 * @author Yvonne Wang
 */
class ComponentBoundsQuery extends GuiQuery<Rectangle> {

  private final Component c;

  static Rectangle boundsOf(Component c) {
    return execute(new ComponentBoundsQuery(c));
  }

  ComponentBoundsQuery(Component c) {
    this.c = c;
  }

  protected Rectangle executeInEDT() {
    return c.getBounds();
  }
}