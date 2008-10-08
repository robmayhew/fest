package org.fest.swing.hierarchy;

import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link JInternalFrame}</code>
 * associated to the given <code>{@link JDesktopIcon}</code>.
 * @see JDesktopIcon#getInternalFrame()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JDesktopIconInternalFrameQuery {

  static JInternalFrame internalFrameOf(final JDesktopIcon desktopIcon) {
    return execute(new GuiQuery<JInternalFrame>() {
      protected JInternalFrame executeInEDT() {
        return desktopIcon.getInternalFrame();
      }
    });
  }

  private JDesktopIconInternalFrameQuery() {}
}