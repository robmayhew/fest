package org.fest.swing.input;

import java.awt.Component;
import java.awt.event.ComponentListener;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns all the
 * <code>{@link ComponentListener}</code>s in a given <code>{@link Component}</code>. 
 *
 * @author Alex Ruiz
 */
class ComponentListenersQuery extends GuiQuery<ComponentListener[]> {
  private final Component component;

  static ComponentListener[] componentListenersIn(Component component) {
    return execute(new ComponentListenersQuery(component));
  }
  
  ComponentListenersQuery(Component component) {
    this.component = component;
  }

  protected ComponentListener[] executeInEDT() {
    return component.getComponentListeners();
  }
}