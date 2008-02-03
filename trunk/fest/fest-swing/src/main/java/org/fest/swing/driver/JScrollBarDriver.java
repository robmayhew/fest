/*
 * Created on Feb 2, 2008
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

import static java.lang.String.valueOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.concat;

import java.awt.Point;

import javax.swing.JScrollBar;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;

/**
 * Understands simulation of user input on a <code>{@link JScrollBar}</code>. Unlike <code>JScrollBarFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JScrollBar}</code>s. This class is intended for
 * internal use only.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JScrollBarDriver extends JComponentDriver {

  private final JScrollBarLocation location = new JScrollBarLocation();

  /**
   * Creates a new </code>{@link JScrollBarDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JScrollBarDriver(RobotFixture robot) {
    super(robot);
  }

  /**
   * Scrolls up (or right) one unit (usually a line).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public final void scrollUnitUp(JScrollBar scrollBar) {
    scrollUnitUp(scrollBar, 1);
  }

  /**
   * Scrolls up (or right) one unit (usually a line,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll up one unit.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public final void scrollUnitUp(JScrollBar scrollBar, int times) {
    if (times <= 0)
      throw actionFailure("The number of times to scroll up one unit should be greater than zero");
    Point where = location.unitLocationToScrollUp(scrollBar);
    scroll(scrollBar, where, times * scrollBar.getUnitIncrement());
  }

  /**
   * Scroll down (or left) one unit (usually a line).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public final void scrollUnitDown(JScrollBar scrollBar) {
    scrollUnitDown(scrollBar, 1);
  }

  /**
   * Scrolls down one unit (usually a line,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll down one unit.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public final void scrollUnitDown(JScrollBar scrollBar, int times) {
    if (times <= 0)
      throw actionFailure("The number of times to scroll down one unit should be greater than zero");
    Point where = location.unitLocationToScrollDown(scrollBar);
    scroll(scrollBar, where, times * scrollBar.getUnitIncrement() * -1);
  }

  /**
   * Scrolls up (or right) one block (usually a page).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public final void scrollBlockUp(JScrollBar scrollBar) {
    scrollBlockUp(scrollBar, 1);
  }

  /**
   * Scrolls up (or right) one block (usually a page,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll up one block.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public final void scrollBlockUp(JScrollBar scrollBar, int times) {
    if (times <= 0)
      throw actionFailure("The number of times to scroll up one block should be greater than zero");
    Point where = location.blockLocationToScrollUp(scrollBar);
    scroll(scrollBar, where, times * scrollBar.getBlockIncrement());
  }

  /**
   * Scrolls down (or left) one block (usually a page).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public final void scrollBlockDown(JScrollBar scrollBar) {
    scrollBlockDown(scrollBar, 1);
  }

  /**
   * Scrolls down (or left) one block (usually a page,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll down one block.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public final void scrollBlockDown(JScrollBar scrollBar, int times) {
    if (times <= 0)
      throw actionFailure("The number of times to scroll down one block should be greater than zero");
    Point where = location.blockLocationToScrollDown(scrollBar);
    scroll(scrollBar, where, times * scrollBar.getBlockIncrement() * -1);
  }

  private void scroll(JScrollBar scrollBar, Point where, int count) {
    // For now, do it programmatically, faking the mouse movement and clicking
    robot.mouseMove(scrollBar, where.x, where.y);
    int value = scrollBar.getValue() + count;
    setValueProperty(scrollBar, value);
  }

  /**
   * Scrolls to the given position.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param position the position to scroll to.
   * @throws ActionFailedException if the given position is not within the <code>JScrollBar</code> bounds.
   */
  public final void scrollTo(JScrollBar scrollBar, final int position) {
    validatePosition(scrollBar, position);
    Point thumb = location.thumbLocation(scrollBar, scrollBar.getValue());
    robot.mouseMove(scrollBar, thumb.x, thumb.y);
    thumb = location.thumbLocation(scrollBar, position);
    robot.mouseMove(scrollBar, thumb.x, thumb.y);
    setValueProperty(scrollBar, position);
  }

  private void validatePosition(JScrollBar scrollBar, final int position) {
    int min = scrollBar.getMinimum();
    int max = scrollBar.getMaximum();
    if (position < min || position > max)
      throw actionFailure(concat("Position '", valueOf(position), "' is not within the JScrollBar bounds of '",
          valueOf(min), "' and '", valueOf(max), "'"));
  }

  private void setValueProperty(final JScrollBar bar, final int value) {
    robot.invokeLater(bar, new Runnable() {
      public void run() {
        bar.setValue(value);
      }
    });
    robot.waitForIdle();
  }
}
