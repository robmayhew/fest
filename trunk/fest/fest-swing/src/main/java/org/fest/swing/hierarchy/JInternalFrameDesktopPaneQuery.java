package org.fest.swing.hierarchy;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the desktop the given 
 * <code>{@link JInternalFrame}</code> belongs to when iconified.
 *
 * @author Alex Ruiz 
 */
class JInternalFrameDesktopPaneQuery extends GuiQuery<JDesktopPane> {
  
  private final JInternalFrame internalFrame;

  static JDesktopPane desktopPaneOf(JInternalFrame internalFrame) {
    return execute(new JInternalFrameDesktopPaneQuery(internalFrame));
  }
  
  JInternalFrameDesktopPaneQuery(JInternalFrame internalFrame) {
    this.internalFrame = internalFrame;
  }

  protected JDesktopPane executeInEDT() {
    JDesktopIcon icon = internalFrame.getDesktopIcon();
    if (icon != null) return icon.getDesktopPane();
    return null;
  }
}