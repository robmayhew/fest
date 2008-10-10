package org.fest.swing.hierarchy;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the desktop the given
 * <code>{@link JInternalFrame}</code> belongs to when iconified.
 * @see JInternalFrame#getDesktopIcon()
 * @see JDesktopIcon#getDesktopPane()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JInternalFrameDesktopPaneQuery {

  static JDesktopPane desktopPaneOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<JDesktopPane>() {
      protected JDesktopPane executeInEDT() {
        JDesktopIcon icon = internalFrame.getDesktopIcon();
        if (icon != null) return icon.getDesktopPane();
        return null;
      }
    });
  }

  private JInternalFrameDesktopPaneQuery() {}
}