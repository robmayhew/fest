package org.fest.swing.driver;

import javax.swing.JComboBox;

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiTask;

import static javax.swing.SwingUtilities.invokeLater;

import static org.fest.swing.core.Pause.pause;
import static org.fest.util.Strings.concat;

/**
 * Understands a task that sets a <code>{@link JComboBox}</code> editable.
 *
 * @author Alex Ruiz
 */
final class JComboBoxSetEditableTask {

  static void setEditable(JComboBox comboBox, boolean editable) {
    invokeLater(new JComboBoxSetEditableGuiTask(comboBox, editable));
    pause(new JComboBoxIsEditableCondition(comboBox, editable));
  }

  private static class JComboBoxSetEditableGuiTask extends GuiTask {
    private final JComboBox comboBox;
    private final boolean editable;
    
    JComboBoxSetEditableGuiTask(JComboBox comboBox, boolean editable) {
      this.comboBox = comboBox;
      this.editable = editable;
    }

    protected void executeInEDT() {
      comboBox.setEditable(editable);
    }
  }
  
  private static class JComboBoxIsEditableCondition extends Condition {
    private final JComboBox comboBox;
    private final boolean editable;

    JComboBoxIsEditableCondition(JComboBox comboBox, boolean editable) {
      super(concat("JComboBox 'editable' property is ", editable));
      this.comboBox = comboBox;
      this.editable = editable;
    }
    
    public boolean test() {
      return comboBox.isEditable() == editable;
    }
  }
}