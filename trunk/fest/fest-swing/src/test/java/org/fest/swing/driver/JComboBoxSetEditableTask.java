package org.fest.swing.driver;

import javax.swing.JComboBox;

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiTask;

import static java.lang.String.valueOf;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Strings.concat;

/**
 * Understands a task that sets a <code>{@link JComboBox}</code> editable. This task is executed in the event dispatch
 * thread.
 *
 * @author Alex Ruiz
 */
final class JComboBoxSetEditableTask {

  static void setEditable(final JComboBox comboBox, final boolean editable) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        comboBox.setEditable(editable);
      }
    }, new Condition(concat("JComboBox 'editable' property is ", valueOf(editable))) {
      public boolean test() {
        return comboBox.isEditable() == editable;
      }
    });
  }
  
  private JComboBoxSetEditableTask() {}
}