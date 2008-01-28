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

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.util.AWT.locationOnScreenOf;
import static org.fest.util.Strings.concat;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;

/**
 * Understands simulation of user input on a <code>{@link Container}</code>. This class is intended for internal use
 * only.
 *
 * <p>
 * Adapted from <code>abbot.tester.Robot</code> from <a href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ContainerDriver extends ComponentDriver {

  /**
   * Creates a new </code>{@link ContainerDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public ContainerDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Resizes the <code>{@link Container}</code> to the given size.
   * @param container the target <code>Container</code>.
   * @param width the width to resize the <code>Container</code> to.
   * @param height the height to resize the <code>Container</code> to.
   */
  protected final void resize(Container container, int width, int height) {
    Dimension size = container.getSize();
    resizeBy(container, width - size.width, height - size.height);
  }

  /**
   * Resizes the <code>{@link Container}</code> by the given amounts.
   * @param container the target <code>Container</code>.
   * @param horizontally the horizontal amount to resize the <code>Container</code> by.
   * @param vertically the vertical amount to resize the <code>Container</code> by.
   */
  protected final void resizeBy(final Container container, final int horizontally, final int vertically) {
    simulateResizeStarted(container, horizontally, vertically);
    robot.invokeAndWait(container, new Runnable() {
      public void run() {
        container.setSize(container.getWidth() + horizontally, container.getHeight() + vertically);
      }
    });
    simulateResizeComplete(container);
    robot.waitForIdle();
  }

  private void simulateResizeStarted(Container container, int horizontally, int vertically) {
    if (!isUserResizable(container)) return;
    Point p = resizeLocationOf(container);
    robot.mouseMove(container, p.x, p.y);
    robot.mouseMove(container, p.x + horizontally, p.y + vertically);
  }

  private void simulateResizeComplete(final Container container) {
    if (!isUserResizable(container)) return;
    Point p = resizeLocationOf(container);
    robot.mouseMove(container, p.x, p.y);
  }

  /**
   * Returns where the mouse usually grabs to resize a window. The lower right corner of the window is usually a good
   * choice.
   * @param c the target <code>Container</code>.
   * @return where the mouse usually grabs to resize a window.
   */
  protected final Point resizeLocationOf(Container c) {
    Dimension size = c.getSize();
    Insets insets = c.getInsets();
    return new Point(size.width - insets.right / 2, size.height - insets.bottom / 2);
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
  public void moveBy(final Container c, final int horizontally, final int vertically) {
    final Point onScreen = locationOnScreenOf(c);
    if (onScreen == null) throw componentNotShowingOnScreen(c);
    simulateMoveStarted(c, horizontally, vertically);
    robot.invokeAndWait(c, new Runnable() {
      public void run() {
        c.setLocation(new Point(onScreen.x + horizontally, onScreen.y + vertically));
      }
    });
    simulateMoveComplete(c);
    robot.waitForIdle();
  }

  private ActionFailedException componentNotShowingOnScreen(final Container c) {
    throw actionFailure(concat("The component ", format(c), " is not showing on the screen"));
  }

  private void simulateMoveStarted(Container c, int horizontally, int vertically) {
    if (!isUserMovable(c)) return;
    Point p = moveLocation(c);
    robot.mouseMove(c, p.x, p.y);
    robot.mouseMove(c, p.x + horizontally, p.y + vertically);
  }

  private void simulateMoveComplete(Container c) {
    if (!isUserMovable(c)) return;
    Point p = moveLocation(c);
    robot.mouseMove(c, p.x, p.y);
  }

  /**
   * Returns where the mouse usually grabs to move a container (or window.) Center of the top of the frame is usually a
   * good choice.
   * @param c the given <code>Container</code>.
   * @return where the mouse usually grabs to move a container (or window.)
   */
  protected Point moveLocation(Container c) {
    Dimension size = c.getSize();
    Insets insets = c.getInsets();
    return new Point(size.width / 2, insets.top / 2);
  }
}
