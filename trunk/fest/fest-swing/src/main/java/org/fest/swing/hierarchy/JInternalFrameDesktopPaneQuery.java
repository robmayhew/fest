package org.fest.swing.hierarchy;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

/**
 * Understands an action that returns the desktop the given <code>{@link JInternalFrame}</code> belongs to when
 * iconified. This query is <strong>not</strong> executed in the event dispatch thread.
 * @see JInternalFrame#getDesktopIcon()
 * @see JDesktopIcon#getDesktopPane()
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JInternalFrameDesktopPaneQuery {

  static JDesktopPane desktopPaneOf(JInternalFrame internalFrame) {
    JDesktopIcon icon = internalFrame.getDesktopIcon();
    if (icon != null) return icon.getDesktopPane();
    return null;
  }

  private JInternalFrameDesktopPaneQuery() {}
}