package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Rectangle;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the bounds of a
 * <code>{@link Component}</code>.
 * @see Component#getBounds()
 *
 * @author Yvonne Wang
 */
final class ComponentBoundsQuery {

  static Rectangle boundsOf(final Component component) {
    return execute(new GuiQuery<Rectangle>() {
      protected Rectangle executeInEDT() {
        return component.getBounds();
      }
    });
  }

  private ComponentBoundsQuery() {}
}