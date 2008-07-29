package org.fest.swing.task;

import javax.swing.JComboBox;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that returns the number of items in a <code>{@link JComboBox}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GetJComboBoxItemCountTask extends GuiTask<Integer> {
  private final JComboBox comboBox;

  /**
   * Returns the number of items in the given <code>{@link JComboBox}</code>. This action is executed in the event 
   * dispatch thread.
   * @param comboBox the given <code>JComboBox</code>.
   * @return the number of items in the given <code>JComboBox</code>.
   */
  public static int itemCountOf(JComboBox comboBox) {
    return new GetJComboBoxItemCountTask(comboBox).run();
  }
  
  private GetJComboBoxItemCountTask(JComboBox comboBox) {
    this.comboBox = comboBox;
  }

  /**
   * Returns the number of items in this task's <code>{@link JComboBox}</code>. This action is executed in the event
   * dispatch thread.
   * @return the number of items in this task's <code>JComboBox</code>.
   */
  protected Integer executeInEDT() {
    return comboBox.getItemCount();
  }
}