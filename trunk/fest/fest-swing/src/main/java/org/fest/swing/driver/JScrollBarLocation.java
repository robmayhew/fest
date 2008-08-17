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

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollBar;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiActionRunner;

import static java.awt.Adjustable.*;

import static org.fest.swing.driver.JScrollBarMinAndMaxQuery.minAndMaxOf;

/**
 * Understands encapsulation of a location in a <code>{@link JScrollBar}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class JScrollBarLocation {
  // TODO Test horizontal scroll bar
  
  private static final int BLOCK_OFFSET = 4;

  private final Map<Integer, JScrollBarLocationStrategy> strategyMap = new HashMap<Integer, JScrollBarLocationStrategy>();
  
  /**
   * Creates a new </code>{@link JScrollBarLocation}</code>.
   */
  public JScrollBarLocation() {
    strategyMap.put(HORIZONTAL, new HorizontalJScrollBarLocationStrategy());
    strategyMap.put(VERTICAL, new VerticalJScrollBarLocationStrategy());
  }
  
  /**
   * Returns the location where to move the mouse pointer to scroll to the given position.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param position the position to scroll to.
   * @return the location where to move the mouse pointer to scroll to the given position.
   */
  public Point thumbLocation(JScrollBar scrollBar, int position) {
    double fraction = (double) position / maximumMinusMinimum(scrollBar);
    return locationStrategyFor(scrollBar).thumbLocation(scrollBar, fraction);
  }

  private int maximumMinusMinimum(JScrollBar scrollBar) {
    MinimumAndMaximum minAndMax = minAndMaxOf(scrollBar);
    return minAndMax.maximum - minAndMax.minimum;
  }

  /**
   * Returns the location where to move the mouse pointer to scroll one block up (or right.)
   * @param scrollBar the target <code>JScrollBar</code>.
   * @return the location where to move the mouse pointer to scroll one block up (or right.)
   */
  public Point blockLocationToScrollUp(JScrollBar scrollBar) {
    Point p = unitLocationToScrollUp(scrollBar);
    int offset = BLOCK_OFFSET;
    return blockLocation(scrollBar, p, offset);
  }

  /**
   * Returns the location where to move the mouse pointer to scroll one block down (or left.)
   * @param scrollBar the target <code>JScrollBar</code>.
   * @return the location where to move the mouse pointer to scroll one block down (or left.)
   */
  public Point blockLocationToScrollDown(JScrollBar scrollBar) {
    Point p = unitLocationToScrollDown(scrollBar);
    int offset = -BLOCK_OFFSET;
    return blockLocation(scrollBar, p, offset);
  }

  private Point blockLocation(JScrollBar scrollBar, Point unitLocation, int offset) {
    return locationStrategyFor(scrollBar).blockLocation(scrollBar, unitLocation, offset);
  }

  /**
   * Returns the location where to move the mouse pointer to scroll one unit up (or right.)
   * @param scrollBar the target <code>JScrollBar</code>.
   * @return the location where to move the mouse pointer to scroll one unit up (or right.)
   */
  public Point unitLocationToScrollUp(JScrollBar scrollBar) {
    return locationStrategyFor(scrollBar).unitLocationToScrollUp(scrollBar);
  }

  /**
   * Returns the location where to move the mouse pointer to scroll one unit down (or left.)
   * @param scrollBar the target <code>JScrollBar</code>.
   * @return the location where to move the mouse pointer to scroll one unit down (or left.)
   */
  public Point unitLocationToScrollDown(JScrollBar scrollBar) {
    int arrow = locationStrategyFor(scrollBar).arrow(scrollBar);
    return new Point(arrow / 2, arrow / 2);
  }

  private JScrollBarLocationStrategy locationStrategyFor(JScrollBar scrollBar) {
    return strategyMap.get(JScrollBarOrientationQuery.orientationOf(scrollBar));
  }

  private static class JScrollBarOrientationQuery extends GuiQuery<Integer> {
    
    // TODO make top-level
    private final JScrollBar scrollBar;

    static Integer orientationOf(JScrollBar scrollBar) {
      return GuiActionRunner.execute(new JScrollBarOrientationQuery(scrollBar));
    }
    
    private JScrollBarOrientationQuery(JScrollBar scrollBar) {
      this.scrollBar = scrollBar;
    }

    protected Integer executeInEDT() throws Throwable {
      return scrollBar.getOrientation();
    }
  }
}
