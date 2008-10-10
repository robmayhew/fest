package org.fest.swing.driver;

import static org.fest.swing.core.GuiActionRunner.execute;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link JTableHeader}</code> in
 * a <code>{@link JTable}</code>.
 * @see JTable#getTableHeader()
 *
 * @author Alex Ruiz
 */
final class JTableHeaderQuery {

  static JTableHeader tableHeader(final JTable table) {
    return execute(new GuiQuery<JTableHeader>() {
      protected JTableHeader executeInEDT() {
        return table.getTableHeader();
      }
    });
  }

  private JTableHeaderQuery() {}
}