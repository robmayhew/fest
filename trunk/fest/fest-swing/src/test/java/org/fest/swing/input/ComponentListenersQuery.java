package org.fest.swing.input;

import java.awt.Component;
import java.awt.event.ComponentListener;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns all the
 * <code>{@link ComponentListener}</code>s in a given <code>{@link Component}</code>.
 * @see Component#getComponentListeners()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class ComponentListenersQuery {

  static ComponentListener[] componentListenersIn(final Component component) {
    return execute(new GuiQuery<ComponentListener[]>() {
      protected ComponentListener[] executeInEDT() {
        return component.getComponentListeners();
      }
    });
  }

  private ComponentListenersQuery() {}
}