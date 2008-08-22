package org.fest.swing.driver;

import javax.swing.JComboBox;

import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that sets a <code>{@link JComboBox}</code> editable. This task should be executed in the event
 * dispatch thread.
 *
 * @author Alex Ruiz
 */
final class JComboBoxSetEditableTask extends GuiTask {
  
  private final boolean editable;
  private final JComboBox comboBox;

  static void setEditable(JComboBox comboBox, boolean editable) {
    execute(new JComboBoxSetEditableTask(editable, comboBox));
  }

  private JComboBoxSetEditableTask(boolean editable, JComboBox comboBox) {
    this.editable = editable;
    this.comboBox = comboBox;
  }

  protected void executeInEDT() {
    comboBox.setEditable(editable);
  }
}