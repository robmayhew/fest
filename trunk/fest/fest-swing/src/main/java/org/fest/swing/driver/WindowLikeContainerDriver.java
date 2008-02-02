/*
 * Created on Feb 2, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import org.fest.swing.core.RobotFixture;

import static org.fest.swing.util.Platform.*;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author 
 *
 */
public abstract class WindowLikeContainerDriver extends ContainerDriver {

  private static final int MAXIMIZE_BUTTON_OFFSET = IS_OS_X ? 25 : IS_WINDOWS ? -20 : 0;

  /**
   * Creates a new </code>{@link WindowLikeContainerDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public WindowLikeContainerDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Identifies the coordinates of the 'close' button.
   * @param c the target window-like <code>Container</code>.
   * @return the coordinates of the 'close' button.
   */
  protected final Point closeLocation(Container c) {
    Dimension size = c.getSize();
    Insets insets = c.getInsets();
    if (IS_OS_X) return new Point(insets.left + 15, insets.top / 2);
    return new Point(size.width - insets.right - 10, insets.top / 2);
  }
  
  /**
   * Identifies the coordinates of the 'iconify' button, returning (0, 0) if not found.
   * @param c the target window-like <code>Container</code>.
   * @return the coordinates of the 'iconify' button, returning (0, 0) if not found.
   */
  protected final Point iconifyLocation(Container c) {
    Insets insets = c.getInsets();
    // From Abbot: We know the exact layout of the window manager frames for w32 and  OSX. Currently no way of detecting
    // the WM under X11. Maybe we could send a WM message (WM_ICONIFY)?
    Point p = new Point();
    p.y = insets.top / 2;
    if (IS_OS_X) p.x = 35;
    if (IS_WINDOWS) {
      int offset = IS_WINDOWS_XP ? 64 : 45;
      p.x = c.getSize().width - insets.right - offset;
    }
    return p;
  }

  /**
   * Identifies the coordinates of the 'maximize' button.
   * @param c the target window-like <code>Container</code>.
   * @return the coordinates of the 'maximize' button.
   */
  protected final Point maximizeLocation(Container c) {
      Point p = iconifyLocation(c);
      p.x += MAXIMIZE_BUTTON_OFFSET;
      return p;
  } 
}
