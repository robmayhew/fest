package org.fest.swing.driver;

import javax.swing.JTable;

import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that clears the selection in a <code>{@link JTable}</code>. This task should be executed in the
 * event dispatch thread.
 *
 * @author Alex Ruiz 
 */
class JTableClearSelectionTask extends GuiTask {
  
  private final JTable table;

  static void clearSelectionOf(JTable table) {
    execute(new JTableClearSelectionTask(table));
  }
  
  JTableClearSelectionTask(JTable table) {
    this.table = table;
  }

  protected void executeInEDT() {
    table.clearSelection();
  }
}