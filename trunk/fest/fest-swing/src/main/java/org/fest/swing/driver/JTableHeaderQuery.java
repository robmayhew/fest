package org.fest.swing.driver;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link JTableHeader}</code> in
 * a <code>{@link JTable}</code>.
 *
 * @author Alex Ruiz
 */
class JTableHeaderQuery extends GuiQuery<JTableHeader> {
  
  private final JTable table;

  static JTableHeader tableHeader(JTable table) {
    return execute(new JTableHeaderQuery(table));
  }
  
  JTableHeaderQuery(JTable table) {
    this.table = table;
  }

  protected JTableHeader executeInEDT() {
    return table.getTableHeader();
  }
}