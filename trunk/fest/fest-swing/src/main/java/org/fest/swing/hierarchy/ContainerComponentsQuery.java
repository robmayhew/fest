package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Collections.list;

/**
 * Understands an action, executed in the event dispatch thread, that returns all the components in a given
 * <code>{@link Container}</code>.
 *
 * @author Alex Ruiz
 */
class ContainerComponentsQuery extends GuiQuery<List<Component>> {
  
  private final Container container;

  static List<Component> componentsOf(Container container) {
    return execute(new ContainerComponentsQuery(container));
  }

  ContainerComponentsQuery(Container container) {
    this.container = container;
  }

  protected List<Component> executeInEDT() {
    return list(container.getComponents());
  }
}