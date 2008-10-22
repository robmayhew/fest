package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Collections.list;

/**
 * Understands an action, executed in the event dispatch thread, that returns all the components in a given
 * <code>{@link Container}</code>.
 * @see Container#getComponents()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class ContainerComponentsQuery {

  static List<Component> componentsOf(final Container container) {
    return execute(new GuiQuery<List<Component>>() {
      protected List<Component> executeInEDT() throws Throwable {
        return list(container.getComponents());
      }
    });
  }

  private ContainerComponentsQuery() {}
}