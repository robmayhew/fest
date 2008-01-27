/*
 * Created on Jan 19, 2008
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

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.util.Swing.centerOf;

import java.awt.Point;

import javax.swing.JList;
import javax.swing.ListModel;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Understands simulation of user input on a <code>{@link JList}</code>. Unlike <code>JListFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JList}</code>s. This class is intended for internal
 * use only.
 *
 * <p>
 * Adapted from <code>abbot.tester.JListTester</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JListDriver extends JComponentDriver {

  private final JListLocation location;

  /**
   * Creates a new </code>{@link JListDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JListDriver(RobotFixture robot) {
    super(robot);
    location = new JListLocation();
  }

  /**
   * Returns an array of <code>String</code>s that represents the list's contents.
   * @param list the target <code>JList</code>
   * @return an array of <code>String</code>s that represents the list's contents.
   */
  public String[] contentsOf(JList list) {
    String[] values = new String[sizeOf(list)];
    for (int i = 0; i < values.length; i++)
      values[i] = model(list).getElementAt(i).toString();
    return values;
  }

  private int sizeOf(JList list) {
    return model(list).getSize();
  }

  private ListModel model(JList list) {
    return list.getModel();
  }

  /**
   * Clicks the first item matching the given value, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>
   * @param value the value to match.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void clickItem(JList list, Object value, MouseButton button, int times) {
    robot.click(list, location.pointAt(list, value), button, times);
  }

  /**
   * Clicks the item under the given index using left mouse button once.
   * @param list the target <code>JList</code>
   * @param index the index of the item to click.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void clickItem(JList list, int index) {
    clickItem(list, index, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the item under the given index, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>
   * @param index the index of the item to click.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void clickItem(JList list, int index, MouseButton button, int times) {
    robot.click(list, location.pointAt(list, index), button, times);
  }

  /**
   * Returns the text of the element under the given index.
   * @param list the target <code>JList</code>
   * @param index the given index.
   * @return the text of the element under the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public String text(JList list, int index) {
    return location.text(list, index);
  }

  /**
   * Returns the index of the first item matching the given value.
   * @param list the target <code>JList</code>
   * @param value the value to match.
   * @return the index of the first item matching the given value.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public int indexOf(JList list, Object value) {
    return location.indexOf(list, value);
  }

  /**
   * Starts a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void drag(JList list, Object value) {
    super.drag(list, location.pointAt(list, value));
  }

  /**
   * Ends a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(JList list, Object value) {
    super.drop(list, location.pointAt(list, value));
  }

  /**
   * Starts a drag operation at the location of the given index.
   * @param list the target <code>JList</code>
   * @param index the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void drag(JList list, int index) {
    super.drag(list, pointAt(list, index));
  }

  /**
   * Ends a drag operation at the location of the given index.
   * @param list the target <code>JList</code>
   * @param index the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(JList list, int index) {
    super.drop(list, pointAt(list, index));
  }

  /**
   * Ends a drag operation at the center of the <code>{@link JList}</code>.
   * @param list the target <code>JList</code>
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(JList list) {
    super.drop(list, centerOf(list));
  }

  /**
   * Returns the coordinates of the item at the given index.
   * @param list the target <code>JList</code>
   * @param index the given index.
   * @return the coordinates of the item at the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public Point pointAt(JList list, int index) {
    return location.pointAt(list, index);
  }
}
