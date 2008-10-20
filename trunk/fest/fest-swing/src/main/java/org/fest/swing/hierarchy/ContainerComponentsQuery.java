package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import static org.fest.util.Collections.list;

/**
 * Understands an action that returns all the components in a given <code>{@link Container}</code>. This query is 
 * <strong>not</strong> executed in the event dispatch thread.
 * @see Container#getComponents()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class ContainerComponentsQuery {

  static List<Component> componentsOf(Container container) {
    return list(container.getComponents());
  }

  private ContainerComponentsQuery() {}
}