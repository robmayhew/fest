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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.task.GetComponentSizeTask.sizeOf;
import static org.fest.swing.util.AWT.locationOnScreenOf;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link Container}</code>. This class is intended for internal use
 * only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ContainerDriver extends ComponentDriver {

  /**
   * Creates a new </code>{@link ContainerDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public ContainerDriver(Robot robot) {
    super(robot);
  }

  /**
   * Resizes the <code>{@link Container}</code> to the given size.
   * @param c the target <code>Container</code>.
   * @param width the width to resize the <code>Container</code> to.
   * @param height the height to resize the <code>Container</code> to.
   */
  public void resize(Container c, int width, int height) {
    Dimension size = sizeOf(c);
    resizeBy(c, width - size.width, height - size.height);
  }

  /**
   * Resizes the <code>{@link Container}</code> by the given amounts.
   * @param c the target <code>Container</code>.
   * @param horizontally the horizontal amount to resize the <code>Container</code> by.
   * @param vertically the vertical amount to resize the <code>Container</code> by.
   */
  protected void resizeBy(Container c, int horizontally, int vertically) {
    simulateResizeStarted(c, horizontally, vertically);
    Dimension size = sizeOf(c);
    Dimension newSize = new Dimension(size.width + horizontally, size.height + vertically);
    robot.invokeAndWait(c, new SetSizeTask(c, newSize));
    simulateResizeComplete(c);
    robot.waitForIdle();
  }

  private void simulateResizeStarted(Container c, int horizontally, int vertically) {
    if (!isUserResizable(c)) return;
    Point p = resizeLocationOf(c);
    robot.moveMouse(c, p.x, p.y);
    robot.moveMouse(c, p.x + horizontally, p.y + vertically);
  }

  private void simulateResizeComplete(Container c) {
    if (!isUserResizable(c)) return;
    Point p = resizeLocationOf(c);
    robot.moveMouse(c, p.x, p.y);
  }

  /**
   * Returns where the mouse usually grabs to resize a window. The lower right corner of the window is usually a good
   * choice.
   * @param c the target <code>Container</code>.
   * @return where the mouse usually grabs to resize a window.
   */
  protected Point resizeLocationOf(Container c) {
    return GetContainerResizeLocationTask.resizeLocationOf(c);
  }

  /**
   * Move the given <code>{@link Container}</code> to the requested location.
   * @param c the target <code>Container</code>.
   * @param x the horizontal coordinate.
   * @param y the vertical coordinate.
   * @throws ActionFailedException if the given container is not showing on the screen.
   */
  public void move(Container c, int x, int y) {
    Point onScreen = locationOnScreenOf(c);
    if (onScreen == null) throw componentNotShowingOnScreen(c);
    moveBy(c, x - onScreen.x, y - onScreen.y);
  }

  /**
   * Move the given <code>{@link Container}</code> by the given amount.
   * @param c the target <code>Container</code>.
   * @param horizontally
   * @param vertically
   * @throws ActionFailedException if the given container is not showing on the screen.
   */
  protected void moveBy(Container c, int horizontally, int vertically) {
    Point onScreen = locationOnScreenOf(c);
    if (onScreen == null) throw componentNotShowingOnScreen(c);
    simulateMoveStarted(c, horizontally, vertically);
    Point location = new Point(onScreen.x + horizontally, onScreen.y + vertically);
    robot.invokeAndWait(c, new SetLocationTask(c, location));
    simulateMoveComplete(c);
    robot.waitForIdle();
  }

  private ActionFailedException componentNotShowingOnScreen(Container c) {
    throw actionFailure(concat("The component ", format(c), " is not showing on the screen"));
  }

  private void simulateMoveStarted(Container c, int horizontally, int vertically) {
    if (!isUserMovable(c)) return;
    Point p = moveLocationOf(c);
    robot.moveMouse(c, p.x, p.y);
    robot.moveMouse(c, p.x + horizontally, p.y + vertically);
  }

  private void simulateMoveComplete(Container c) {
    if (!isUserMovable(c)) return;
    Point p = moveLocationOf(c);
    robot.moveMouse(c, p.x, p.y);
  }

  /**
   * Returns where the mouse usually grabs to move a container (or window.) Center of the top of the frame is usually a
   * good choice.
   * @param c the given <code>Container</code>.
   * @return where the mouse usually grabs to move a container (or window.)
   */
  protected Point moveLocationOf(Container c) {
    return GetContainerMoveLocationTask.moveLocationOf(c);
  }
}
