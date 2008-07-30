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
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Range.From;
import org.fest.swing.util.Range.To;
import org.fest.util.Arrays;

import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.lang.String.valueOf;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.CommonValidations.validateCellReader;
import static org.fest.swing.task.GetJListSelectedItemTask.selectedIndexOf;
import static org.fest.swing.task.IsComponentEnabledTask.isEnabled;
import static org.fest.swing.util.AWT.centerOf;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JList}</code>. Unlike <code>JListFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JList}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver extends JComponentDriver {

  private static final String SELECTED_INDICES_PROPERTY = "selectedIndices";
  private static final String SELECTED_INDICES_LENGTH_PROPERTY = concat(SELECTED_INDICES_PROPERTY, "#length");
  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";

  private final JListLocation location;

  private JListCellReader cellReader;

  /**
   * Creates a new </code>{@link JListDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JListDriver(Robot robot) {
    super(robot);
    location = new JListLocation();
    cellReader(new BasicJListCellReader());
  }

  /**
   * Returns an array of <code>String</code>s that represents the contents of the given <code>{@link JList}</code>,
   * using this driver's <code>{@link JListCellReader}</code>.
   * @param list the target <code>JList</code>.
   * @return an array of <code>String</code>s that represents the contents of the given <code>JList</code>.
   * @see #cellReader(JListCellReader)
   */
  public String[] contentsOf(JList list) {
    String[] values = new String[sizeOf(list)];
    for (int i = 0; i < values.length; i++)
      values[i] = value(list, i);
    return values;
  }

  /**
   * Returns an array of <code>String</code>s that represents the selection in the given <code>{@link JList}</code>,
   * using this driver's <code>{@link JListCellReader}</code>.
   * @param list the target <code>JList</code>.
   * @return an array of <code>String</code>s that represents the selection in the given <code>JList</code>.
   * @see #cellReader(JListCellReader)
   */
  public String[] selectionOf(JList list) {
    int[] selectedIndices = selectedIndicesOf(list);
    int selectionCount = selectedIndices.length;
    String[] values = new String[selectionCount];
    for (int i = 0; i < selectionCount; i++)
      values[i] = value(list, selectedIndices[i]);
    return values;
  }

  private int sizeOf(final JList list) {
    return new GetSizeTask(list).run();
  }

  private static class GetSizeTask extends GuiTask<Integer> {
    private final JList list;

    GetSizeTask(JList list) {
      this.list = list;
    }

    protected Integer executeInEDT() throws Throwable {
      return list.getModel().getSize();
    }
  }

  /**
   * Selects the items matching the given values.
   * @param list the target <code>JList</code>.
   * @param values the values to match.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws LocationUnavailableException if an element matching the any of the given values cannot be found.
   */
  public void selectItems(final JList list, final String[] values) {
    if (values == null) throw new NullPointerException("Array of values should not be null");
    if (Arrays.isEmpty(values)) throw new IllegalArgumentException("Array of values should not be empty");
    if (!isEnabled(list)) return;
    new MultipleSelectionTemplate(robot) {
      int elementCount() {
        return values.length;
      }

      void selectElement(int index) {
        selectItem(list, values[index]);
      }
    }.multiSelect();
  }

  /**
   * Clicks the item matching the given value using left mouse button once.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void selectItem(JList list, String value) {
    selectItem(list, indexOf(list, value));
  }

  /**
   * Clicks the first item matching the given value, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void clickItem(JList list, String value, MouseButton button, int times) {
    clickItem(list, indexOf(list, value), button, times);
  }

  /**
   * Selects the items under the given indices.
   * @param list the target <code>JList</code>.
   * @param indices the indices of the items to select.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IndexOutOfBoundsException if any of the indices is negative or greater than the index of the last item in
   * the <code>JList</code>.
   */
  public void selectItems(final JList list, final int[] indices) {
    if (indices == null) throw new NullPointerException("The array of indices should not be null");
    if (isEmptyArray(indices)) throw new IllegalArgumentException("The array of indices should not be empty");
    if (!isEnabled(list)) return;
    new MultipleSelectionTemplate(robot) {
      int elementCount() {
        return indices.length;
      }

      void selectElement(int index) {
        selectItem(list, indices[index]);
      }
    }.multiSelect();
  }

  private boolean isEmptyArray(int[] array) { return array == null || array.length == 0; }

  /**
   * Selects the items in the specified range.
   * @param list the target <code>JList</code>.
   * @param from the starting point of the selection.
   * @param to the last item to select.
   * @throws IndexOutOfBoundsException if the any index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  public void selectItems(JList list, From from, To to) {
    selectItems(list, from.value, to.value);
  }

  /**
   * Selects the items in the specified range.
   * @param list the target <code>JList</code>.
   * @param start the starting point of the selection.
   * @param end the last item to select (inclusive.)
   * @throws IndexOutOfBoundsException if the any index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  public void selectItems(JList list, int start, int end) {
    selectItem(list, start);
    robot.pressKey(VK_SHIFT);
    selectItem(list, end);
    robot.releaseKey(VK_SHIFT);
  }

  /**
   * Selects the item under the given index using left mouse button once.
   * @param list the target <code>JList</code>.
   * @param index the index of the item to click.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  public void selectItem(JList list, int index) {
    if (isSelectedIndex(list, index)) return;
    clickItem(list, index, LEFT_BUTTON, 1);
  }

  private boolean isSelectedIndex(final JList list, final int index) {
    return new IsSelectedIndexTask(list, index).run();
  }

  private static class IsSelectedIndexTask extends GuiTask<Boolean> {
    private final JList list;
    private final int index;

    IsSelectedIndexTask(JList list, int index) {
      this.list = list;
      this.index = index;
    }

    protected Boolean executeInEDT() throws Throwable {
      return list.isSelectedIndex(index);
    }
  }

  /**
   * Clicks the item under the given index, using the specified mouse button, the given number times.
   * @param list the target <code>JList</code>.
   * @param index the index of the item to click.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  public void clickItem(JList list, int index, MouseButton button, int times) {
    if (!isEnabled(list)) return;
    scrollToVisible(list, index);
    robot.click(list, location.pointAt(list, index), button, times);
  }

  /**
   * Verifies that the selected item in the <code>{@link JList}</code> matches the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws AssertionError if the selected item does not match the value.
   */
  public void requireSelection(JList list, String value) {
    int selectedIndex = selectedIndexOf(list);
    if (selectedIndex == -1) failNoSelection(list);
    assertThat(value(list, selectedIndex)).as(selectedIndexProperty(list)).isEqualTo(value);
  }

  /**
   * Verifies that the selected items in the <code>{@link JList}</code> match the given values.
   * @param list the target <code>JList</code>.
   * @param items the values to match.
   * @throws NullPointerException if the given array is <code>null</code>.
   * @throws AssertionError if the selected items do not match the given values.
   */
  public void requireSelectedItems(JList list, String... items) {
    if (items == null) throw new NullPointerException("The array of items should not be null");
    int[] selectedIndices = selectedIndicesOf(list);
    int currentSelectionCount = selectedIndices.length;
    if (currentSelectionCount == 0) failNoSelection(list);
    assertThat(currentSelectionCount).as(propertyName(list, SELECTED_INDICES_LENGTH_PROPERTY)).isEqualTo(items.length);
    for (int i = 0; i < currentSelectionCount; i++) {
      String description = propertyName(list, concat(SELECTED_INDICES_PROPERTY, "[", valueOf(i), "]"));
      assertThat(value(list, selectedIndices[i])).as(description).isEqualTo(items[i]);
    }
  }

  private int[] selectedIndicesOf(final JList list) {
    return new GetSelectedIndicesTask(list).run();
  }

  private static class GetSelectedIndicesTask extends GuiTask<int[]> {
    private final JList list;

    GetSelectedIndicesTask(JList list) {
      this.list = list;
    }

    protected int[] executeInEDT() throws Throwable {
      return list.getSelectedIndices();
    }
  }

  /**
   * Verifies that the <code>{@link JList}</code> does not have a selection.
   * @param list the target <code>JList</code>.
   * @throws AssertionError if the <code>JList</code> has a selection.
   */
  public void requireNoSelection(JList list) {
    assertThat(selectedIndexOf(list)).as(selectedIndexProperty(list)).isEqualTo(-1);
  }

  private void failNoSelection(JList list) {
    fail(concat("[", selectedIndexProperty(list), "] No selection"));
  }

  private String selectedIndexProperty(JList list) {
    return propertyName(list, SELECTED_INDEX_PROPERTY);
  }

  /**
   * Starts a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void drag(JList list, String value) {
    drag(list, indexOf(list, value));
  }

  /**
   * Ends a drag operation at the location of the first item matching the given value.
   * @param list the target <code>JList</code>.
   * @param value the value to match.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public void drop(JList list, String value) {
    drop(list, indexOf(list, value));
  }

  /**
   * Starts a drag operation at the location of the given index.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public void drag(JList list, int index) {
    scrollToVisible(list, index);
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
    scrollToVisible(list, index);
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
  public JPopupMenu showPopupMenu(JList list, String value) {
    return showPopupMenu(list, indexOf(list, value));
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the <code>{@link JList}</code>.
   * @param list the target <code>JList</code>.
   * @param index the index of the item.
   * @return a driver that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public JPopupMenu showPopupMenu(JList list, int index) {
    scrollToVisible(list, index);
    return robot.showPopupMenu(list, pointAt(list, index));
  }

  private void scrollToVisible(JList list, int index) {
    super.scrollToVisible(list, itemBounds(list, index));
  }

  private Rectangle itemBounds(final JList list, final int index) {
    return new GetItemBoundsTask(list, index).run();
  }

  private static class GetItemBoundsTask extends GuiTask<Rectangle> {
    private final JList list;
    private final int index;

    GetItemBoundsTask(JList list, int index) {
      this.list = list;
      this.index = index;
    }

    protected Rectangle executeInEDT() {
      return list.getCellBounds(index, index);
    }
  }

  /**
   * Returns the coordinates of the first item matching the given value.
   * @param list the target <code>JList</code>
   * @param value the value to match.
   * @return the coordinates of the item at the given item.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public Point pointAt(JList list, String value) {
    return location.pointAt(list, indexOf(list, value));
  }

  /**
   * Returns the index of the first item matching the given value.
   * @param list the target <code>JList</code>
   * @param value the value to match.
   * @return the index of the first item matching the given value.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public int indexOf(JList list, String value) {
    int size = sizeOf(list);
    for (int i = 0; i < size; i++)
      if (areEqual(value, value(list, i))) return i;
    throw indexNotFoundFor(value);
  }

  /**
   * Returns the <code>String</code> representation of the element under the given index, using this driver's
   * <code>{@link JListCellReader}</code>.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @return the value of the element under the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   * @see #cellReader(JListCellReader)
   */
  public String value(JList list, int index) {
    location.validate(list, index);
    return cellReader.valueAt(list, index);
  }

  private LocationUnavailableException indexNotFoundFor(String value) {
    throw new LocationUnavailableException(concat("Unable to find an element matching the value ", quote(value)));
  }

  /**
   * Updates the implementation of <code>{@link JListCellReader}</code> to use when comparing internal values of a
   * <code>{@link JList}</code> and the values expected in a test.
   * @param newCellReader the new <code>JListCellValueReader</code> to use.
   * @throws NullPointerException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(JListCellReader newCellReader) {
    validateCellReader(newCellReader);
    cellReader = newCellReader;
  }
}
