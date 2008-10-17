package org.fest.swing.hierarchy;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import org.fest.swing.edt.GuiTask;
import org.fest.swing.timing.Condition;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands a task that iconifies a given <code>{@link JInternalFrame}</code>. This task is executed in the event
 * dispatch thread.
 *
 * @author Alex Ruiz 
 */
final class JInternalFrameIconifyTask {
  
  static void iconify(final JInternalFrame internalFrame) {
    execute(new GuiTask() {
      protected void executeInEDT() throws PropertyVetoException {
        internalFrame.setIcon(true);
      }
    }, new Condition("JInternalFrame is icondified") {
      public boolean test() {
        return internalFrame.isIcon();
      }
    });
  }
  
  private JInternalFrameIconifyTask() {}
}