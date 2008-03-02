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
import javax.swing.JPopupMenu;
import javax.swing.ListModel;

import org.fest.swing.core.Robot;
import org.fest.swing.core.MouseButton;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Range.From;
import org.fest.swing.util.Range.To;

import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.util.Platform.controlOrCommandKey;
import static org.fest.swing.util.AWT.centerOf;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user input on a <code>{@link JList}</code>. Unlike <code>JListFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JList}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver extends JComponentDriver {

  private final JListLocation location;

  /**
   * Creates a new </code>{@link JListDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JListDriver(Robot robot) {
    super(robot);
    location = new JListLocation();
  }

  /**
   * Returns an array of <code>String</code>s that represents the list's contents.
   * @param list the target <code>JList</code>.
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
   * Selects the items matching the given values.
   * @param list the target <code>JList</code>.
   * @param values the values to match.
   * @throws LocationUnavailableException if an element matching the any of the given values cannot be found.
   */
  public void selectItems(JList list, Object[] values) {
    int controlOrCommand = controlOrCommandKey();
    robot.pressKey(controlOrCommand);
    for (Object value : values) selectItem(list, value);
    robot.releaseKey(controlOrCommand);
  }

  /**
   * Clicks the item matching the given value using left mouse button once.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void selectItem(JList list, Object value) {
    clickItem(list, value, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the first item matching the given value, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void clickItem(JList list, Object value, MouseButton button, int times) {
    robot.click(list, pointAt(list, value), button, times);
  }

  /**
   * Selects the items under the given indices.
   * @param list the target <code>JList</code>.
   * @param indices the indices of the items to select.
   * @throws LocationUnavailableException if any of the indices is negative or greater than the index of the last item
   *         in the <code>JList</code>.
   */
  public void selectItems(JList list, int[] indices) {
    int controlOrCommand = controlOrCommandKey();
    robot.pressKey(controlOrCommand);
    for (int index : indices) selectItem(list, index);
    robot.releaseKey(controlOrCommand);
  }

  /**
   * Selects the items in the specified range.
   * @param list the target <code>JList</code>.
   * @param from the starting point of the selection.
   * @param to the last item to select.
   * @throws LocationUnavailableException if the any index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void selectItems(JList list, From from, To to) {
    selectItems(list, from.value, to.value);
  }

  /**
   * Selects the items in the specified range.
   * @param list the target <code>JList</code>.
   * @param start the starting point of the selection.
   * @param end the last item to select (inclusive.)
   * @throws LocationUnavailableException if the any index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void selectItems(JList list, int start, int end) {
    int shift = VK_SHIFT;
    robot.pressKey(shift);
    for (int i = start; i <= end; i++) selectItem(list, i);
    robot.releaseKey(shift);
  }

  /**
   * Clicks the item under the given index using left mouse button once.
   * @param list the target <code>JList</code>.
   * @param index the index of the item to click.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void selectItem(JList list, int index) {
    clickItem(list, index, LEFT_BUTTON, 1);
  }

  /**
   * Clicks the item under the given index, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>.
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
   * @param list the target <code>JList</code>.
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
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @return the index of the first item matching the given value.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public int indexOf(JList list, Object value) {
    return location.indexOf(list, value);
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in the <code>{@link JList}</code> matches 
   * the given text.
   * @param list the target <code>JList</code>.
   * @param text the text to match.
   * @throws AssertionError if the selected item does not match the given text.
   */
  public void requireSelection(JList list, String text) {
    int selectedIndex = list.getSelectedIndex();
    if (selectedIndex == -1) failNoSelection(list);
    assertThat(text(list, selectedIndex)).as(selectedIndexProperty(list)).isEqualTo(text);
  }

  /**
   * Verifies that the <code>String</code> representations of the selected items in the <code>{@link JList}</code> match 
   * the given text items.
   * @param list the target <code>JList</code>.
   * @param items text items to match.
   * @throws AssertionError if the selected items do not match the given text items.
   */
  public void requireSelectedItems(JList list, String... items) {
    int[] selectedIndices = list.getSelectedIndices();
    int currentSelectionCount = selectedIndices.length;
    if (currentSelectionCount == 0) failNoSelection(list);
    assertThat(currentSelectionCount).as(propertyName(list, "selectedIndices#length")).isEqualTo(items.length);
    for (int i = 0; i < currentSelectionCount; i++) {
      String description = propertyName(list, concat("selectedIndices[", valueOf(i), "]"));
      assertThat(text(list, selectedIndices[i])).as(description).isEqualTo(items[i]);
    }
  }

  private void failNoSelection(JList list) { 
    fail(concat("[", selectedIndexProperty(list), "] No selection")); 
  }

  private String selectedIndexProperty(JList list) { 
    return propertyName(list, "selectedIndex"); 
  }
  
  /**
   * Starts a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void drag(JList list, Object value) {
    super.drag(list, pointAt(list, value));
  }

  /**
   * Ends a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(JList list, Object value) {
    super.drop(list, pointAt(list, value));
  }

  /**
   * Starts a drag operation at the location of the given index.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void drag(JList list, int index) {
    super.drag(list, pointAt(list, index));
  }

  /**
   * Ends a drag operation at the location of the given index.
   * @param list the target <code>JList</code>.
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
   * @param list the target <code>JList</code>.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(JList list) {
    super.drop(list, centerOf(list));
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the <code>{@link JList}</code>.
   * @param list the target <code>JList</code>.
   * @param index the index of the item.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public JPopupMenu showPopupMenuAt(JList list, int index) {
    return showPopupMenu(list, pointAt(list, index));
  }

  private Point pointAt(JList list, int index) {
    return location.pointAt(list, index);
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the <code>{@link JList}</code>.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public JPopupMenu showPopupMenuAt(JList list, Object value) {
    return showPopupMenu(list, pointAt(list, value));
  }

  private Point pointAt(JList list, Object value) {
    return location.pointAt(list, value);
  }
}
