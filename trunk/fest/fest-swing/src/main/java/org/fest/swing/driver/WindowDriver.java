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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link Window}</code>. Unlike <code>WindowFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link Window}</code>s. This class is intended for internal
 * use only.
 *
 * <p>
 * Adapted from <code>abbot.tester.WindowTester</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public class WindowDriver extends WindowLikeContainerDriver {

  /**
   * Creates a new </code>{@link WindowDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public WindowDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Resizes the <code>{@link Window}</code> horizontally.
   * @param w the target <code>Window</code>.
   * @param width the width that this fixture's <code>Window</code> should have after being resized.
   * @throws ActionFailedException if the <code>Window</code> is not resizable.
   */
  public final void resizeWidthTo(Window w, int width) {
    resizeTo(w, new Dimension(width, w.getHeight()));
  }

  /**
   * Resizes the <code>{@link Window}</code> vertically.
   * @param w the target <code>Window</code>.
   * @param height the height that this fixture's <code>Window</code> should have after being resized.
   * @throws ActionFailedException if the <code>Window</code> is not resizable.
   */
  public final void resizeHeightTo(Window w, int height) {
    resizeTo(w, new Dimension(w.getWidth(), height));
  }

  /**
   * Resizes the <code>{@link Window}</code> to the given size.
   * @param w the target <code>Window</code>.
   * @param size the size to resize the <code>Window</code> to.
   * @throws ActionFailedException if the <code>Window</code> is not resizable.
   */
  public final void resizeTo(Window w, Dimension size) {
    if (!isUserResizable(w))
      throw actionFailure(concat("The window ", format(w), " is not resizable by the user"));
    resize(w, size.width, size.height);
  }

  /**
   * Moves the <code>{@link Window}</code> to the given location.
   * @param w the target <code>Window</code>.
   * @param where the location to move the <code>Window</code> to.
   * @throws ActionFailedException if the <code>Window</code> is not movable.
   * @throws ActionFailedException if the given <code>Window</code> is not showing on the screen.
   */
  public final void moveTo(Window w, Point where) {
    if (!isUserMovable(w))
      throw actionFailure(concat("The window ", format(w), " is not movable by the user"));
    move(w, where.x, where.y);
  }
}
