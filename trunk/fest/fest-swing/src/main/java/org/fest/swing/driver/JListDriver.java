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

import java.awt.Point;

import javax.swing.JList;
import javax.swing.ListModel;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Swing;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;

/**
 * Understands simulation of user input on a <code>{@link JList}</code>. Unlike <code>JListFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JList}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JListDriver {

  private final RobotFixture robot;
  private final JList list;
  private final JListLocation location;
  private final DragAndDropDriver dragAndDrop;

  /**
   * Creates a new </code>{@link JListDriver}</code>.
   * @param robot the robot to use to simulate user input.
   * @param list the target <code>JList</code>.
   */
  public JListDriver(RobotFixture robot, JList list) {
    this.robot = robot;
    this.list = list;
    location = new JListLocation(list);
    dragAndDrop = new DragAndDropDriver(robot, list);
  }

  /** 
   * Returns an array of <code>String</code>s that represents the list's contents. 
   * @return an array of <code>String</code>s that represents the list's contents.
   */
  public String[] contents() {
    String[] values = new String[size()];
    for (int i = 0; i < values.length; i++)
      values[i] = model().getElementAt(i).toString();
    return values;
  }
  
  private int size() { return model().getSize(); }
  private ListModel model() { return list.getModel(); }
  
  /**
   * Clicks the first item matching the given value, using the specified mouse button, the given number times.
   * @param value the value to match.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void clickItem(Object value, MouseButton button, int times) {
    robot.click(list, location.pointAt(value), button, times);
  }

  /**
   * Clicks the item under the given index using left mouse button once.
   * @param index the index of the item to click.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void clickItem(int index) {
    clickItem(index, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the item under the given index, using the specified mouse button, the given number times.
   * @param index the index of the item to click.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void clickItem(int index, MouseButton button, int times) {
    robot.click(list, location.pointAt(index), button, times);
  }

  /**
   * Returns the text of the element under the given index.
   * @param index the given index.
   * @return the text of the element under the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public String text(int index) {
    return location.text(index);
  }
  
  /**
   * Returns the index of the first item matching the given value.
   * @param value the value to match.
   * @return the index of the first item matching the given value.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public int indexOf(Object value) {
    return location.indexOf(value);
  }

  /**
   * Starts a drag operation at the location of the first item matching the given value.
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void drag(Object value) {
    dragAndDrop.drag(location.pointAt(value));
  }

  /**
   * Ends a drag operation at the location of the first item matching the given value.
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(Object value) {
    dragAndDrop.drop(location.pointAt(value));
  }

  /**
   * Starts a drag operation at the location of the given index.
   * @param index the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void drag(int index) {
    dragAndDrop.drag(pointAt(index));
  }

  /**
   * Ends a drag operation at the location of the given index.
   * @param index the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(int index) {
    dragAndDrop.drop(pointAt(index));
  }
  
  /**
   * Ends a drag operation at the center of the <code>{@link JList}</code>.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop() {
    dragAndDrop.drop(Swing.pointAt(list));
  }

  /**
   * Returns the coordinates of the item at the given index.
   * @param index the given index.
   * @return the coordinates of the item at the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public Point pointAt(int index) {
    return location.pointAt(index);
  }
}
