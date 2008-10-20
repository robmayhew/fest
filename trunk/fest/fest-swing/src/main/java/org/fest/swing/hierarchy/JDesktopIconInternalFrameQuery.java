package org.fest.swing.hierarchy;

import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

/**
 * Understands an action that returns the <code>{@link JInternalFrame}</code> associated to the given
 * <code>{@link JDesktopIcon}</code>. This query is <strong>not</strong> executed in the event dispatch thread.
 * @see JDesktopIcon#getInternalFrame()
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JDesktopIconInternalFrameQuery {

  static JInternalFrame internalFrameOf(JDesktopIcon desktopIcon) {
    return desktopIcon.getInternalFrame();
  }

  private JDesktopIconInternalFrameQuery() {}
}