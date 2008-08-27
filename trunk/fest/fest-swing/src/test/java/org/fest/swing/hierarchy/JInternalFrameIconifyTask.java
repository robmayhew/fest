package org.fest.swing.hierarchy;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

import org.fest.swing.core.GuiTask;

/**
 * Understands a task that iconifies a given <code>{@link JInternalFrame}</code>. This task should be executed in the
 * event dispatch thread.
 *
 * @author Alex Ruiz 
 */
class JInternalFrameIconifyTask extends GuiTask {
  
  private final JInternalFrame internalFrame;

  static JInternalFrameIconifyTask iconifyTask(JInternalFrame internalFrame) {
    return new JInternalFrameIconifyTask(internalFrame);
  }
  
  private JInternalFrameIconifyTask(JInternalFrame internalFrame) {
    this.internalFrame = internalFrame;
  }

  protected void executeInEDT() throws PropertyVetoException {
    internalFrame.setIcon(true);
  }
}