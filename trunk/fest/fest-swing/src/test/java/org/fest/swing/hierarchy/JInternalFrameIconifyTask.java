package org.fest.swing.hierarchy;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that iconifies a given <code>{@link JInternalFrame}</code>.
 *
 * @author Alex Ruiz 
 */
class JInternalFrameIconifyTask {
  
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
}