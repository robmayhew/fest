package org.fest.swing.hierarchy;

import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link JInternalFrame}</code>
 * associated to the given <code>{@link JDesktopIcon}</code>.
 *
 * @author Alex Ruiz 
 */
class JDesktopIconInternalFrameQuery extends GuiQuery<JInternalFrame> {

  private final JDesktopIcon desktopIcon;

  static JInternalFrame internalFrameOf(JDesktopIcon desktopIcon) {
    return execute(new JDesktopIconInternalFrameQuery(desktopIcon));
  }
  
  JDesktopIconInternalFrameQuery(JDesktopIcon desktopIcon) {
    this.desktopIcon = desktopIcon;
  }

  protected JInternalFrame executeInEDT() {
    return desktopIcon.getInternalFrame();
  }
}