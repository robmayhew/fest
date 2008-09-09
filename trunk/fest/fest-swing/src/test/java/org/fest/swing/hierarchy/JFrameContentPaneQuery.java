package org.fest.swing.hierarchy;

import java.awt.Container;

import javax.swing.JFrame;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the content pane object of a given
 * <code>{@link JFrame}</code>.
 *
 * @author Alex Ruiz
 */
class JFrameContentPaneQuery extends GuiQuery<Container> {

  private final JFrame frame;

  static Container contentPaneOf(final JFrame frame) {
    return execute(new JFrameContentPaneQuery(frame));
  }

  JFrameContentPaneQuery(JFrame frame) {
    this.frame = frame;
  }

  protected Container executeInEDT() {
    return frame.getContentPane();
  }
}