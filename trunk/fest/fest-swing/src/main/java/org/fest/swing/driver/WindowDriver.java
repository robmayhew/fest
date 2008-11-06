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

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link Window}</code>. Unlike <code>WindowFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link Window}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 */
public class WindowDriver extends WindowLikeContainerDriver {

  /**
   * Creates a new </code>{@link WindowDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public WindowDriver(Robot robot) {
    super(robot);
  }

  /**
   * Resizes the <code>{@link Window}</code> horizontally.
   * @param w the target <code>Window</code>.
   * @param width the width that the <code>Window</code> should have after being resized.
   * @throws ActionFailedException if the <code>Window</code> is not enabled.
   * @throws ActionFailedException if the <code>Window</code> is not resizable by the user.
   */
  @RunsInEDT
  public void resizeWidthTo(Window w, int width) {
    resizeTo(w, new Dimension(width, sizeOf(w).height));
  }

  /**
   * Resizes the <code>{@link Window}</code> vertically.
   * @param w the target <code>Window</code>.
   * @param height the height that the <code>Window</code> should have after being resized.
   * @throws ActionFailedException if the <code>Window</code> is not enabled.
   * @throws ActionFailedException if the <code>Window</code> is not resizable by the user.
   */
  @RunsInEDT
  public void resizeHeightTo(Window w, int height) {
    resizeTo(w, new Dimension(sizeOf(w).width, height));
  }

  /**
   * Resizes the <code>{@link Window}</code> to the given size.
   * @param w the target <code>Window</code>.
   * @param size the size to resize the <code>Window</code> to.
   * @throws ActionFailedException if the <code>Window</code> is not enabled.
   * @throws ActionFailedException if the <code>Window</code> is not resizable by the user.
   */
  @RunsInEDT
  public void resizeTo(Window w, Dimension size) {
    resize(w, size.width, size.height);
  }

  /**
   * Moves the <code>{@link Window}</code> to the given location.
   * @param w the target <code>Window</code>.
   * @param where the location to move the <code>Window</code> to.
   * @throws ActionFailedException if the <code>Window</code> is not movable.
   * @throws ActionFailedException if the given <code>Window</code> is not showing on the screen.
   */
  public void moveTo(Window w, Point where) {
    if (!isUserMovable(w))
      throw actionFailure(concat("The window ", format(w), " is not movable by the user"));
    move(w, where.x, where.y);
  }

  /**
   * Closing the <code>{@link Window}</code>.
   * @param w the target <code>Window</code>.
   */
  @RunsInEDT
  public void close(Window w) {
    robot.close(w);
  }

  /**
   * Shows the <code>{@link Window}</code>.
   * @param w the target <code>Window</code>.
   */
  @RunsInEDT
  public void show(Window w) {
    robot.showWindow(w);
    requireVisible(w);
  }

  /**
   * Shows the <code>{@link Window}</code>, resized to the given size.
   * @param w the target <code>Window</code>.
   * @param size the size to resize the <code>Window</code> to.
   */
  @RunsInEDT
  public void show(Window w, Dimension size) {
    robot.showWindow(w, size);
    requireVisible(w);
  }
}
