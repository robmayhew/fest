/*
 * Created on Jan 21, 2008
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
import java.awt.Container;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.core.TypeMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.TimeoutWatch;

import static java.lang.String.valueOf;
import static javax.swing.text.DefaultEditorKit.selectAllAction;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.driver.CommonValidations.validateCellReader;
import static org.fest.swing.driver.GetJComboBoxEditorTask.editorOf;
import static org.fest.swing.driver.GetJComboBoxItemCountTask.itemCountOf;
import static org.fest.swing.driver.IsJComboBoxDropDownVisibleTask.isDropDownVisible;
import static org.fest.swing.driver.IsJComboBoxEditableTask.isEditable;
import static org.fest.swing.driver.IsJComboBoxEditorAccessibleTask.isEditorAccessible;
import static org.fest.swing.task.GetJComboBoxSelectedIndexTask.selectedIndexOf;
import static org.fest.swing.task.IsComponentEnabledTask.isEnabled;
import static org.fest.swing.util.TimeoutWatch.startWatchWithTimeoutOf;
import static org.fest.util.Arrays.format;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.*;

/**
 * Understands simulation of user input on a <code>{@link JComboBox}</code>. Unlike <code>JComboBoxFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JComboBox}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxDriver extends JComponentDriver {

  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";

  private static final ComponentMatcher LIST_MATCHER = new TypeMatcher(JList.class);

  private final JListDriver listDriver;

  private JComboBoxCellReader cellReader;

  /**
   * Creates a new </code>{@link JComboBoxDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JComboBoxDriver(Robot robot) {
    super(robot);
    listDriver = new JListDriver(robot);
    cellReader(new BasicJComboBoxCellReader());
  }

  /**
   * Returns an array of <code>String</code>s that represents the contents of the given <code>{@link JComboBox}</code>
   * list. The <code>String</code> representation of each element is performed using this driver's
   * <code>{@link JComboBoxCellReader}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @return an array of <code>String</code>s that represent the contents of the given <code>JComboBox</code> list.
   * @see #value(JComboBox, int)
   * @see #cellReader(JComboBoxCellReader)
   */
  public String[] contentsOf(JComboBox comboBox) {
    int itemCount = itemCountOf(comboBox);
    String[] items = new String[itemCount];
    for (int i = 0; i < itemCount; i++)
      items[i] = value(comboBox, i);
    return items;
  }

  /**
   * Selects the first item matching the given text in the <code>{@link JComboBox}</code>. Value matching is performed
   * using this fixture's <code>{@link JComboBoxCellReader}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param value the value to match
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @see #cellReader(JComboBoxCellReader)
   */
  public void selectItem(JComboBox comboBox, String value) {
    int itemCount = itemCountOf(comboBox);
    for (int i = 0; i < itemCount; i++) {
      if (areEqual(value(comboBox, i), value)) {
        selectItem(comboBox, i);
        return;
      }
    }
    // While actions are supposed to represent real user actions, it's possible that the current environment does not
    // match sufficiently, so we need to throw an appropriate exception that can be used to diagnose the problem.
    throw new LocationUnavailableException(
        concat("Unable to find item ", quote(value), " among the JComboBox contents (",
            format(contentsOf(comboBox)), ")"));
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in the <code>{@link JComboBox}</code>
   * matches the given text.
   * @param comboBox the target <code>JComboBox</code>.
   * @param value the text to match.
   * @throws AssertionError if the selected item does not match the given value.
   */
  public void requireSelection(JComboBox comboBox, String value) {
    int selectedIndex = selectedIndexOf(comboBox);
    if (selectedIndex == -1)
      fail(concat("[", selectedIndexProperty(comboBox), "] No selection"));
    assertThat(value(comboBox, selectedIndex)).as(selectedIndexProperty(comboBox)).isEqualTo(value);
  }

  /**
   * Verifies that the <code>{@link JComboBox}</code> does not have any selection.
   * @param comboBox the target <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> has a selection.
   */
  public void requireNoSelection(JComboBox comboBox) {
    assertThat(selectedIndexOf(comboBox)).as(selectedIndexProperty(comboBox)).isEqualTo(-1);
  }

  /**
   * Returns the <code>String</code> representation of the element under the given index, using this driver's
   * <code>{@link JComboBoxCellReader}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param index the given index.
   * @return the value of the element under the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JComboBox</code>.
   * @see #cellReader(JComboBoxCellReader)
   */
  public String value(JComboBox comboBox, int index) {
    validateIndex(comboBox, index);
    return cellReader.valueAt(comboBox, index);
  }

  private String selectedIndexProperty(JComboBox comboBox) {
    return propertyName(comboBox, SELECTED_INDEX_PROPERTY);
  }

  /**
   * Selects the item under the given index in the <code>{@link JComboBox}</code>.
   * @param comboBox the target <code>JComboBox</code>.
   * @param index the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JComboBox</code>.
   */
  public void selectItem(final JComboBox comboBox, int index) {
    final int validatedIndex = validateIndex(comboBox, index);
    if (!isEnabled(comboBox)) return;
    showDropDownList(comboBox);
    try {
      listDriver.selectItem(dropDownList(), validatedIndex);
    } catch (ComponentLookupException e) {
      robot.invokeAndWait(new SetSelectedIndexTask(comboBox, validatedIndex));
    } finally {
      hideDropDownListIfVisible(comboBox);
    }
  }

  int validateIndex(JComboBox comboBox, int index) {
    int itemCount = itemCountOf(comboBox);
    if (index >= 0 && index < itemCount) return index;
    throw new IndexOutOfBoundsException(concat(
        "Item index (", valueOf(index), ") should be between [0] and [",
        valueOf(itemCount - 1), "] (inclusive)"));
  }

  void showDropDownList(JComboBox comboBox) {
    if (isDropDownVisible(comboBox) || !isEnabled(comboBox)) return;
    // Location of pop-up button activator is LAF-dependent
    dropDownVisibleThroughUIDelegate(comboBox, true);
  }

  private void hideDropDownListIfVisible(JComboBox comboBox) {
    if (!isDropDownVisible(comboBox)) return;
    dropDownVisibleThroughUIDelegate(comboBox, false);
  }

  private void dropDownVisibleThroughUIDelegate(JComboBox comboBox, final boolean visible) {
    robot.invokeAndWait(new SetJComboBoxDropDownVisibleTask(comboBox, visible));
    robot.waitForIdle();
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link JComboBox}</code>, replacing any text. This action
   * is executed only if the <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @param text the text to enter.
   */
  public void replaceText(JComboBox comboBox, String text) {
    if (!isEditorAccessible(comboBox)) return;
    selectAllText(comboBox);
    enterText(comboBox, text);
  }

  /**
   * Simulates a user selecting the text in the <code>{@link JComboBox}</code>. This action is executed only if the
   * <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   */
  public void selectAllText(JComboBox comboBox) {
    if (!isEditorAccessible(comboBox)) return;
    Component editor = editorOf(comboBox);
    if (!(editor instanceof JComponent)) return;
    focus(editor);
    invokeAction((JComponent) editor, selectAllAction);
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link JComboBox}</code>. This action is executed only
   * if the <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @param text the text to enter.
   */
  public void enterText(JComboBox comboBox, String text) {
    if (!isEditorAccessible(comboBox)) return;
    focus(comboBox);
    robot.enterText(text);
  }

  /**
   * Find the <code>{@link JList}</code> in the pop-up raised by the <code>{@link JComboBox}</code>, if the LAF actually
   * uses one.
   * @return the found <code>JList</code>.
   * @throws ComponentLookupException if the <code>JList</code> in the pop-up could not be found.
   */
  public JList dropDownList() {
    JPopupMenu popup = robot.findActivePopupMenu();
    if (popup == null) {
      TimeoutWatch watch = startWatchWithTimeoutOf(robot.settings().timeoutToFindPopup());
      while ((popup = robot.findActivePopupMenu()) == null) {
        if (watch.isTimeOut()) throw listNotFound();
        pause();
      }
    }
    JList list = findListIn(popup);
    if (list == null) throw listNotFound();
    return list;
  }

  private ComponentLookupException listNotFound() {
    throw new ComponentLookupException("Unable to find the pop-up list for the JComboBox");
  }

  private JList findListIn(Container parent) {
    try {
      return (JList)robot.finder().find(LIST_MATCHER);
    } catch (ComponentLookupException ignored) {
      return null;
    }
  }

  /**
   * Asserts that the given <code>{@link JComboBox}</code> is editable.
   * @param comboBox the target <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> is not editable.
   */
  public void requireEditable(final JComboBox comboBox) {
    assertEditable(comboBox, true);
  }

  /**
   * Asserts that the given <code>{@link JComboBox}</code> is not editable.
   * @param comboBox the given <code>JComboBox</code>.
   * @throws AssertionError if the <code>JComboBox</code> is editable.
   */
  public void requireNotEditable(JComboBox comboBox) {
    assertEditable(comboBox, false);
  }
  
  private void assertEditable(JComboBox comboBox, boolean expected) {
    assertThat(isEditable(comboBox)).as(editableProperty(comboBox)).isEqualTo(expected);
  }

  private static String editableProperty(JComboBox comboBox) {
    return propertyName(comboBox, EDITABLE_PROPERTY);
  }

  /**
   * Updates the implementation of <code>{@link JComboBoxCellReader}</code> to use when comparing internal values
   * of a <code>{@link JComboBox}</code> and the values expected in a test.
   * @param newCellReader the new <code>JComboBoxCellValueReader</code> to use.
   * @throws NullPointerException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(JComboBoxCellReader newCellReader) {
    validateCellReader(newCellReader);
    cellReader = newCellReader;
  }
}
