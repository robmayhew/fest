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

import static java.awt.Adjustable.HORIZONTAL;

import java.awt.Point;

import javax.swing.JScrollBar;

/**
 * Understands encapsulation of a location in a <code>{@link JScrollBar}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class JScrollBarLocation {

  private static final int BLOCK_OFFSET = 4;

  /**
   * Returns the location where to move the mouse pointer to scroll to the given position.
   * @param scrollBar the target <code>JScrollBar</code>.
   * @param position the position to scroll to.
   * @return the location where to move the mouse pointer to scroll to the given position.
   */
  public Point thumbLocation(JScrollBar scrollBar, int position) {
    double fraction = (double) position / (scrollBar.getMaximum() - scrollBar.getMinimum());
    int arrow = arrow(scrollBar);
    if (isHorizontal(scrollBar))
      return new Point(arrow + (int) (fraction * (scrollBar.getWidth() - 2 * arrow)), arrow / 2);
    return new Point(arrow / 2, arrow + (int) (fraction * (scrollBar.getHeight() - 2 * arrow)));
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
    if (isHorizontal(scrollBar)) unitLocation.x += offset;
    else unitLocation.y += offset;
    return unitLocation;
  }

  /**
   * Returns the location where to move the mouse pointer to scroll one unit up (or right.)
   * @param scrollBar the target <code>JScrollBar</code>.
   * @return the location where to move the mouse pointer to scroll one unit up (or right.)
   */
  public Point unitLocationToScrollUp(JScrollBar scrollBar) {
    int arrow = arrow(scrollBar);
    if (isHorizontal(scrollBar))
      return new Point(scrollBar.getWidth() - arrow / 2, arrow / 2);
    return new Point(arrow / 2, scrollBar.getHeight() - arrow / 2);
  }

  /**
   * Returns the location where to move the mouse pointer to scroll one unit down (or left.)
   * @param scrollBar the target <code>JScrollBar</code>.
   * @return the location where to move the mouse pointer to scroll one unit down (or left.)
   */
  public Point unitLocationToScrollDown(JScrollBar scrollBar) {
    int arrow = arrow(scrollBar);
    return new Point(arrow / 2, arrow / 2);
  }

  private int arrow(JScrollBar scrollBar) {
    return isHorizontal(scrollBar) ? scrollBar.getHeight() : scrollBar.getWidth();
  }

  private boolean isHorizontal(JScrollBar scrollBar) {
    return scrollBar.getOrientation() == HORIZONTAL;
  }
}
