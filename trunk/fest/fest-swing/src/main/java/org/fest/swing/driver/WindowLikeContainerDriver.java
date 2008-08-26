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

import org.fest.swing.core.Robot;

import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.util.Platform.*;

/**
 * Understands simulation of user input on a <code>{@link Container}</code> that looks/behaves like a window. This class 
 * is intended for internal use only.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class WindowLikeContainerDriver extends ContainerDriver {

  private static final int MAXIMIZE_BUTTON_OFFSET = isOSX() ? 25 : isWindows() ? -20 : 0;

  /**
   * Creates a new </code>{@link WindowLikeContainerDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public WindowLikeContainerDriver(Robot robot) {
    super(robot);
  }

  /**
   * Identifies the coordinates of the 'close' button.
   * @param c the target window-like <code>Container</code>.
   * @return the coordinates of the 'close' button.
   */
  protected final Point closeLocation(Container c) {
    Dimension size = sizeOf(c);
    Insets insets = insetsOf(c);
    if (isOSX()) return new Point(insets.left + 15, insets.top / 2);
    return new Point(size.width - insets.right - 10, insets.top / 2);
  }
  
  /**
   * Identifies the coordinates of the 'iconify' button, returning (0, 0) if not found.
   * @param c the target window-like <code>Container</code>.
   * @return the coordinates of the 'iconify' button, returning (0, 0) if not found.
   */
  protected final Point iconifyLocation(Container c) {
    Insets insets = insetsOf(c);
    // From Abbot: We know the exact layout of the window manager frames for w32 and  OSX. Currently no way of detecting
    // the WM under X11. Maybe we could send a WM message (WM_ICONIFY)?
    Point p = new Point();
    p.y = insets.top / 2;
    if (isOSX()) p.x = 35;
    if (isWindows()) {
      int offset = isWindowsXP() ? 64 : 45;
      p.x = sizeOf(c).width - insets.right - offset;
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
