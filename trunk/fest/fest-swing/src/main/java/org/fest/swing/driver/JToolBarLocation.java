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

import java.awt.*;

import javax.swing.JToolBar;

import static java.awt.BorderLayout.*;
import static java.lang.Math.max;
import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.swing.driver.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.driver.JToolBarOrientationQuery.orientationOf;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.*;

/**
 * Understands a visible location on a <code>{@link JToolBar}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class JToolBarLocation {

  private static String[] VALID_CONSTRAINTS = { NORTH, EAST, SOUTH, WEST };

  /**
   * Returns the point where to grab the given <code>{@link JToolBar}</code>.
   * @param toolBar the target <code>JToolBar</code>.
   * @return the point where to grab the given <code>JToolBar</code>.
   */
  public Point pointToGrab(JToolBar toolBar) {
    Insets insets = insetsOf(toolBar);
    Dimension size = sizeOf(toolBar);
    if (max(max(max(insets.left, insets.top), insets.right), insets.bottom) == insets.left)
      return new Point(insets.left / 2, size.height / 2);
    if (max(max(insets.top, insets.right), insets.bottom) == insets.top)
      return new Point(size.width / 2, insets.top / 2);
    if (max(insets.right, insets.bottom) == insets.right)
      return new Point(size.width - insets.right / 2, size.height / 2);
    return new Point(size.width / 2, size.height - insets.bottom / 2);
  }

  /**
   * Returns the location where to dock the given <code>{@link JToolBar}</code>, at the given constraint position.
   * The constraint position must be one of the constants <code>{@link BorderLayout#NORTH NORTH}</code>,
   * <code>{@link BorderLayout#EAST EAST}</code>, <code>{@link BorderLayout#SOUTH SOUTH}</code>, or
   * <code>{@link BorderLayout#WEST WEST}</code>.
   * @param toolBar the target <code>JToolBar</code>.
   * @param dock the container where to dock the <code>JToolBar</code> to.
   * @param constraint the constraint position.
   * @return the location where to dock the given <code>JToolBar</code>.
   * @throws IllegalArgumentException if the constraint has an invalid value.
   */
  public Point dockLocation(JToolBar toolBar, Container dock, String constraint) {
    validate(constraint);
    Insets insets = insetsOf(dock);
    Dimension toolBarSize = sizeOf(toolBar);
    // BasicToolBarUI prioritizes location N/E/W/S by proximity to the respective border. Close to top border is N, even
    // if close to the left or right border.
    int offset = isHorizontal(toolBar) ? toolBarSize.height : toolBarSize.width;
    Dimension dockSize = sizeOf(dock);
    if (NORTH.equals(constraint))
      return new Point(dockSize.width / 2, insets.top);
    if (EAST.equals(constraint))
      return new Point(dockSize.width - insets.right - 1, verticalDockingYCoordinate(dockSize.height, insets, offset));
    if (WEST.equals(constraint))
      return new Point(insets.left, verticalDockingYCoordinate(dockSize.height, insets, offset));
    int x = dockSize.width / 2;
    // Make sure we don't get mistaken for EAST or WEST
    if (x < insets.left + offset)
      x = insets.left + offset;
    else if (x > dockSize.width - insets.right - offset - 1)
      x = dockSize.width - insets.right - offset - 1;
    return new Point(x, dockSize.height - insets.bottom - 1);
  }

  private boolean isHorizontal(JToolBar toolBar) {
    return orientationOf(toolBar) == HORIZONTAL;
  }
  
  private void validate(String constraint) {
    for (String validConstraint : VALID_CONSTRAINTS)
      if (validConstraint.equals(constraint)) return;
    throw new IllegalArgumentException(
        concat(quote(constraint), " is not a valid constraint. Valid constraints are ", format(VALID_CONSTRAINTS)));
  }

  private int verticalDockingYCoordinate(int dockHeight, Insets insets, int offset) {
    int y = dockHeight / 2;
    // Make sure we don't get mistaken for NORTH
    if (y < insets.top + offset) y = insets.top + offset;
    return y;
  }
}
