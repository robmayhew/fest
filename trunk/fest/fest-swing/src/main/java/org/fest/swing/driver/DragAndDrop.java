/*
 * Created on Feb 1, 2008
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

import java.awt.Component;
import java.awt.Point;

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.util.TimeoutWatch;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.Settings.*;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.Platform.*;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;

/**
 * Understands drag and drop.
 *
 * @author Alex Ruiz
 */
final class DragAndDrop {

  private final Robot robot;

  /**
   * Creates a new </code>{@link DragAndDrop}</code>.
   * @param robot the robot to use to simulate user input.
   */
  DragAndDrop(Robot robot) {
    this.robot = robot;
  }

  /** Number of pixels traversed before a drag starts. */
  static final int DRAG_THRESHOLD = IS_WINDOWS || IS_MACINTOSH ? 10 : 16;

  /**
   * Performs a drag action at the given point.
   * @param target the target component.
   * @param where the point where to start the drag action.
   */
  void drag(Component target, Point where) {
    robot.mousePress(target, where, LEFT_BUTTON);
    if (dragDelay() > delayBetweenEvents()) pause(dragDelay());
    mouseMove(target, where.x, where.y);
    robot.waitForIdle();
  }

  private void mouseMove(Component target, int x, int y) {
    if (IS_WINDOWS || IS_MACINTOSH) {
      mouseMoveOnWindowsAndMacintosh(target, x, y);
      return;
    }
    mouseMove(target,
        point(x + DRAG_THRESHOLD / 2, y + DRAG_THRESHOLD / 2),
        point(x + DRAG_THRESHOLD, y + DRAG_THRESHOLD),
        point(x + DRAG_THRESHOLD / 2, y + DRAG_THRESHOLD / 2),
        point(x, y)
    );
  }

  private void mouseMoveOnWindowsAndMacintosh(Component target, int x, int y) {
    int dx = distance(x, target.getWidth());
    int dy = distance(y, target.getHeight());
    if (dx == 0 && dy == 0) dx = DRAG_THRESHOLD;
    mouseMove(target,
        point(x + dx / 4, y + dy / 4),
        point(x + dx / 2, y + dy / 2),
        point(x + dx, y + dy),
        point(x + dx + 1, y + dy)
    );
  }

  private int distance(int coordinate, int dimension) {
    return coordinate + DRAG_THRESHOLD < dimension ? DRAG_THRESHOLD : 0;
  }

  private Point point(int x, int y) { return new Point(x, y); }

  /**
   * Ends a drag operation, releasing the mouse button over the given target location.
   * <p>
   * This method is tuned for native drag/drop operations, so if you get odd behavior, you might try using a simple
   * <code>{@link RobotFixture#mouseMove(Component, int, int)}</code> and
   * <code>{@link RobotFixture#releaseMouseButtons()}</code>.
   * @param target the target component.
   * @param where the point where the drag operation ends.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  void drop(Component target, Point where) {
    dragOver(target, where);
    TimeoutWatch watch = startWatchWithTimeoutOf(eventPostingDelay() * 4);
    while (!robot.isDragging()) {
      if (watch.isTimeOut()) throw actionFailure("There is no drag in effect");
      pause();
    }
    if (dropDelay() > delayBetweenEvents()) pause(dropDelay() - delayBetweenEvents());
    robot.releaseMouseButtons();
    robot.waitForIdle();
  }

  /**
   * Move the mouse appropriately to get from the source to the destination. Enter/exit events will be generated where
   * appropriate.
   * @param target the target component.
   * @param where the point to drag over.
   */
  void dragOver(Component target, Point where) {
    dragOver(target, where.x, where.y);
  }

  private void dragOver(Component target, int x, int y) {
    robot.mouseMove(target, x - 4, y);
    robot.mouseMove(target, x, y);
  }

  private void mouseMove(Component target, Point...points) {
    for (Point p : points) robot.mouseMove(target, p.x, p.y);
  }
}
