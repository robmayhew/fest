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

import java.awt.Point;

import javax.swing.JScrollBar;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.util.Pair;

import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JScrollBar}</code>. Unlike <code>JScrollBarFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JScrollBar}</code>s. This class is intended for
 * internal use only.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JScrollBarDriver extends JComponentDriver {

  private static final String VALUE_PROPERTY = "value";
  
  private final JScrollBarLocation location = new JScrollBarLocation();

  /**
   * Creates a new </code>{@link JScrollBarDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JScrollBarDriver(Robot robot) {
    super(robot);
  }

  /**
   * Scrolls up (or right) one unit (usually a line).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public void scrollUnitUp(JScrollBar scrollBar) {
    scrollUnitUp(scrollBar, 1);
  }

  /**
   * Scrolls up (or right) one unit (usually a line,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll up one unit.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public void scrollUnitUp(JScrollBar scrollBar, int times) {
    validateTimes(times, "scroll up one unit");
    Point where = location.unitLocationToScrollUp(scrollBar);
    scroll(scrollBar, where, times * unitIncrementOf(scrollBar));
  }

  /**
   * Scroll down (or left) one unit (usually a line).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public void scrollUnitDown(JScrollBar scrollBar) {
    scrollUnitDown(scrollBar, 1);
  }

  /**
   * Scrolls down one unit (usually a line,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll down one unit.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public void scrollUnitDown(JScrollBar scrollBar, int times) {
    validateTimes(times, "scroll down one unit");
    Point where = location.unitLocationToScrollDown(scrollBar);
    scroll(scrollBar, where, times * unitIncrementOf(scrollBar) * -1);
  }

  private int unitIncrementOf(JScrollBar scrollBar) {
    return new GetUnitIncrementTask(scrollBar).run();
  }

  private static class GetUnitIncrementTask extends GuiQuery<Integer> {
    private final JScrollBar scrollBar;

    GetUnitIncrementTask(JScrollBar scrollBar) {
      this.scrollBar = scrollBar;
    }

    protected Integer executeInEDT() {
      return scrollBar.getUnitIncrement();
    }
  }

  /**
   * Scrolls up (or right) one block (usually a page).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public void scrollBlockUp(JScrollBar scrollBar) {
    scrollBlockUp(scrollBar, 1);
  }

  /**
   * Scrolls up (or right) one block (usually a page,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll up one block.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public void scrollBlockUp(JScrollBar scrollBar, int times) {
    validateTimes(times, "scroll up one block");
    Point where = location.blockLocationToScrollUp(scrollBar);
    scroll(scrollBar, where, times * blockIncrementOf(scrollBar));
  }

  /**
   * Scrolls down (or left) one block (usually a page).
   * @param scrollBar the target <code>JScrollBar</code>.
   */
  public void scrollBlockDown(JScrollBar scrollBar) {
    scrollBlockDown(scrollBar, 1);
  }

  /**
   * Scrolls down (or left) one block (usually a page,) the given number of times.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param times the number of times to scroll down one block.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public void scrollBlockDown(JScrollBar scrollBar, int times) {
    validateTimes(times, "scroll down one block");
    Point where = location.blockLocationToScrollDown(scrollBar);
    scroll(scrollBar, where, times * blockIncrementOf(scrollBar) * -1);
  }

  private int blockIncrementOf(JScrollBar scrollBar) {
    return new GetBlockIncrementTask(scrollBar).run();
  }

  private static class GetBlockIncrementTask extends GuiQuery<Integer> {
    private final JScrollBar scrollBar;

    GetBlockIncrementTask(JScrollBar scrollBar) {
      this.scrollBar = scrollBar;
    }

    protected Integer executeInEDT() {
      return scrollBar.getBlockIncrement();
    }
  }

  private void validateTimes(int times, String action) {
    if (times > 0) return;
    String message = concat(
        "The number of times to ", action, " should be greater than zero, but was <", valueOf(times), ">");
    throw actionFailure(message);
  }

  private void scroll(JScrollBar scrollBar, Point where, int count) {
    if (!isEnabled(scrollBar)) return;
    // For now, do it programmatically, faking the mouse movement and clicking
    robot.moveMouse(scrollBar, where.x, where.y);
    int value = JScrollBarValueQuery.valueOf(scrollBar) + count;
    setValueProperty(scrollBar, value);
  }

  /**
   * Scrolls to the given position.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param position the position to scroll to.
   * @throws ActionFailedException if the given position is not within the <code>JScrollBar</code> bounds.
   */
  public void scrollTo(JScrollBar scrollBar, final int position) {
    validatePosition(scrollBar, position);
    if (!isEnabled(scrollBar)) return;
    Point thumb = location.thumbLocation(scrollBar, JScrollBarValueQuery.valueOf(scrollBar));
    robot.moveMouse(scrollBar, thumb.x, thumb.y);
    thumb = location.thumbLocation(scrollBar, position);
    robot.moveMouse(scrollBar, thumb.x, thumb.y);
    setValueProperty(scrollBar, position);
  }

  private void validatePosition(JScrollBar scrollBar, int position) {
    Pair<Integer, Integer> minAndMax = new GetMinimumAndMaximumTask(scrollBar).run();
    int min = minAndMax.one;
    int max = minAndMax.two;
    if (position >= min && position <= max) return;
    throw actionFailure(concat(
        "Position <", valueOf(position), "> is not within the JScrollBar bounds of <",
        valueOf(min), "> and <", valueOf(max), ">"));
  }
  
  private static class GetMinimumAndMaximumTask extends GuiQuery<Pair<Integer, Integer>> {
    private final JScrollBar scrollBar;

    GetMinimumAndMaximumTask(JScrollBar scrollBar) {
      this.scrollBar = scrollBar;
    }
    
    protected Pair<Integer, Integer> executeInEDT() throws Throwable {
      int min = scrollBar.getMinimum();
      int max = scrollBar.getMaximum();
      return new Pair<Integer, Integer>(min, max);
    }
  }

  private void setValueProperty(JScrollBar scrollBar, int value) {
    robot.invokeLater(scrollBar, new SetValueTask(scrollBar, value));
    robot.waitForIdle();
  }

  private static class SetValueTask implements Runnable {
    private final JScrollBar scrollBar;
    private final int value;

    SetValueTask(JScrollBar scrollBar, int value) {
      this.scrollBar = scrollBar;
      this.value = value;
    }

    public void run() {
      scrollBar.setValue(value);
    }
  }

  /**
   * Asserts that the value of the <code>{@link JScrollBar}</code> is equal to the given one.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param value the expected value.
   * @throws AssertionError if the value of the <code>JScrollBar</code> is not equal to the given one.
   */
  public void requireValue(JScrollBar scrollBar, int value) {
    assertThat(JScrollBarValueQuery.valueOf(scrollBar)).as(propertyName(scrollBar, VALUE_PROPERTY)).isEqualTo(value);
  }
}
