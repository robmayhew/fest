/*
 * Created on Jan 27, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static java.awt.Frame.*;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.driver.FrameDeiconifiedCondition.untilDeiconified;
import static org.fest.swing.driver.FrameIconifiedCondition.untilIconified;
import static org.fest.swing.driver.FrameMaximizedCondition.untilMaximized;
import static org.fest.swing.driver.FrameNormalizedCondition.untilNormalized;
import static org.fest.swing.exception.ActionFailedException.actionFailure;

/**
 * Understands simulation of user input on a <code>{@link Frame}</code>. Unlike <code>FrameFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link Frame}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 */
public class FrameDriver extends WindowDriver {

  /**
   * Creates a new </code>{@link FrameDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public FrameDriver(Robot robot) {
    super(robot);
  }

  /**
   * Iconifies the given <code>{@link Frame}</code>.
   * @param frame the given <code>Frame</code>.
   */
  public void iconify(Frame frame) {
    Point p = iconifyLocation(frame);
    if (p != null) robot.moveMouse(frame, p.x, p.y);
    updateFrameExtendedState(frame, ICONIFIED);
    pause(untilIconified(frame));
  }

  /**
   * Deiconifies the given <code>{@link Frame}</code>.
   * @param frame the given <code>Frame</code>.
   */
  public void deiconify(Frame frame) {
    updateFrameExtendedState(frame, NORMAL);
    pause(untilDeiconified(frame));
  }

  /**
   * Normalizes the given <code>{@link Frame}</code>.
   * @param frame the given <code>Frame</code>.
   */
  public void normalize(Frame frame) {
    updateFrameExtendedState(frame, NORMAL);
    pause(untilNormalized(frame));
  }

  /**
   * Makes the <code>{@link Frame}</code> full size.
   * @param frame the target <code>Frame</code>.
   * @throws ActionFailedException if the operating system does not support maximizing frames.
   */
  public void maximize(Frame frame) {
    Point p = maximizeLocation(frame);
    if (p != null) robot.moveMouse(frame, p.x, p.y);
    if (!supportsMaximize(Toolkit.getDefaultToolkit())) 
      throw actionFailure("Platform does not support maximizing frames");
    updateFrameExtendedState(frame, MAXIMIZED_BOTH);
    pause(untilMaximized(frame));
  }

  private void updateFrameExtendedState(Frame frame, int state) {
    frame.setExtendedState(state);
  }

  static boolean supportsMaximize(Toolkit toolkit) {
    return toolkit.isFrameStateSupported(MAXIMIZED_BOTH);
  }
}
