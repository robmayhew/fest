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
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;

import org.fest.swing.core.Condition;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;

import static java.awt.Frame.*;

import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.Platform.*;

/**
 * Understands simulation of user input on a <code>{@link Frame}</code>. Unlike <code>FrameFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link Frame}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 */
public class FrameDriver extends WindowDriver {

  private static final int MAXIMIZE_BUTTON_OFFSET = IS_OS_X ? 25 : IS_WINDOWS ? -20 : 0;

  /**
   * Creates a new </code>{@link FrameDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public FrameDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Iconifies the given <code>{@link Frame}</code>.
   * @param frame the given <code>Frame</code>.
   */
  public final void iconify(final Frame frame) {
    Point p = iconifyLocation(frame);
    if (p != null) robot.mouseMove(frame, p.x, p.y);
    updateFrameExtendedState(frame, ICONIFIED);
    pause(new Condition("frame being iconified") {
      public boolean test() {
        return frame.getExtendedState() == ICONIFIED;
      }
    });
  }

  /**
   * Deiconifies the given <code>{@link Frame}</code>.
   * @param frame the given <code>Frame</code>.
   */
  public final void deiconify(final Frame frame) {
    updateFrameExtendedState(frame, NORMAL);
    pause(new Condition("frame being deiconified") {
      public boolean test() {
        return frame.getExtendedState() != ICONIFIED;
      }
    });
  }

  /**
   * Normalizes the given <code>{@link Frame}</code>.
   * @param frame the given <code>Frame</code>.
   */
  public final void normalize(final Frame frame) {
    updateFrameExtendedState(frame, NORMAL);
    pause(new Condition("frame being normalized") {
      public boolean test() {
        return frame.getExtendedState() == NORMAL;
      }
    });
  }

  /**
   * Makes the <code>{@link Frame}</code> full size.
   * @param frame the target <code>Frame</code>.
   * @throws ActionFailedException if the operating system does not support maximizing frames.
   */
  public final void maximize(final Frame frame) {
    Point p = maximizeLocation(frame);
    if (p != null) robot.mouseMove(frame, p.x, p.y);
    if (!supportsMaximize()) throw actionFailure("Platform does not support maximizing frames");
    updateFrameExtendedState(frame, MAXIMIZED_BOTH);
    pause(new Condition("frame being maximized") {
      public boolean test() {
        return (frame.getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH;
      }
    });
  }

  private void updateFrameExtendedState(final Frame frame, final int state) {
    robot.invokeLater(frame, new Runnable() {
      public void run() {
        frame.setExtendedState(state);
      }
    });
  }

  private boolean supportsMaximize() {
    return Toolkit.getDefaultToolkit().isFrameStateSupported(MAXIMIZED_BOTH);
  }

  /**
   * Identifies the coordinates of the 'maximize' button where possible, returning <code>null</code> if not.
   * @param frame the target <code>Frame</code>.
   * @return the coordinates of the 'maximize' button where possible, returning <code>null</code> if not.
   */
  protected final Point maximizeLocation(Frame frame) {
      Point loc = iconifyLocation(frame);
      loc.x += MAXIMIZE_BUTTON_OFFSET;
      return loc;
  }

  /**
   * Identifies the coordinates of the 'iconify' button, returning (0, 0) if not found.
   * @param frame the target <code>Frame</code>.
   * @return the coordinates of the 'iconify' button, returning (0, 0) if not found.
   */
  protected final Point iconifyLocation(Frame frame) {
    Insets insets = frame.getInsets();
    // From Abbot: We know the exact layout of the window manager frames for w32 and  OSX. Currently no way of detecting
    // the WM under X11. Maybe we could send a WM message (WM_ICONIFY)?
    Point loc = new Point();
    loc.y = insets.top / 2;
    if (IS_OS_X) loc.x = 35;
    if (IS_WINDOWS) {
      int offset = IS_WINDOWS_XP ? 64 : 45;
      loc.x = frame.getSize().width - insets.right - offset;
    }
    return loc;
  }
}
