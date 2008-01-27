/*
 * Created on Jun 12, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.lang.String.valueOf;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.util.Platform.controlOrCommandKey;
import static org.fest.util.Strings.concat;

import javax.swing.JList;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JListDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.util.Range;

/**
 * Understands simulation of user events on a <code>{@link JList}</code> and verification of the state of such
 * <code>{@link JList}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JListFixture extends ComponentFixture<JList> implements ItemGroupFixture {

  private final JListDriver driver;

  /**
   * Creates a new <code>{@link JListFixture}</code>.
   * @param robot performs simulation of user events on a <code>JList</code>.
   * @param listName the name of the <code>JList</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JList</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JList</code> is found.
   */
  public JListFixture(RobotFixture robot, String listName) {
    super(robot, listName, JList.class);
    driver = newListDriver();
  }

  /**
   * Creates a new <code>{@link JListFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JList</code>.
   * @param target the <code>JList</code> to be managed by this fixture.
   */
  public JListFixture(RobotFixture robot, JList target) {
    super(robot, target);
    driver = newListDriver();
  }

  private JListDriver newListDriver() {
    return new JListDriver(robot);
  }

  /**
   * Returns the elements in this fixture's <code>{@link JList}</code> as <code>String</code>s.
   * @return the elements in this fixture's <code>JList</code>.
   */
  public String[] contents() {
    return driver.contentsOf(target);
  }

  /**
   * Simulates a user selecting the items (in the specified range) in this fixture's <code>{@link JList}</code>. The
   * simulated user actions are:
   * <ul>
   * <li>pressing the "Shift" key</li>
   * <li>selecting the items (in the specified range) in this fixture's <code>{@link JList}</code></li>
   * <li>releasing the "Shift"</li>
   * </ul>
   * @param from the starting point of the selection.
   * @param to the last item to select (inclusive.)
   * @return this fixture.
   */
  public final JListFixture selectItems(Range.From from, Range.To to) {
    int shift = VK_SHIFT;
    doPressKey(shift);
    for (int i = from.value; i <= to.value; i++) selectItem(i);
    doReleaseKey(shift);
    return this;
  }

  /**
   * Simulates a user selecting the specified items in this fixture's <code>{@link JList}</code>. The simulated user
   * actions are:
   * <ul>
   * <li>pressing the "Control" or "Command" key (depending on the OS)</li>
   * <li>selecting the specified items in this fixture's <code>{@link JList}</code></li>
   * <li>releasing the "Control" or "Command" key</li>
   * </ul>
   * @param indices the indices of the items to select.
   * @return this fixture.
   * @throws LocationUnavailableException if any of the indices is negative or greater than the index of the last item
   *         in the <code>JList</code>.
   */
  public final JListFixture selectItems(int...indices) {
    int controlOrCommand = controlOrCommandKey();
    doPressKey(controlOrCommand);
    for (int index : indices) selectItem(index);
    doReleaseKey(controlOrCommand);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JList}</code>.
   * @param index the index of the item to select.
   * @return this fixture.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public final JListFixture selectItem(int index) {
    driver.clickItem(target, index);
    return this;
  }

  /**
   * Simulates a user selecting the specified items in this fixture's <code>{@link JList}</code>. The simulated user
   * actions are:
   * <ul>
   * <li>pressing the "Control" or "Command" key (depending on the OS)</li>
   * <li>selecting the specified items in this fixture's <code>{@link JList}</code></li>
   * <li>releasing the "Control" or "Command" key</li>
   * </ul>
   * @param items the text of the items to select.
   * @return this fixture.
   * @throws LocationUnavailableException if an element matching the any of the given <code>String</code>s cannot be
   *         found.
   */
  public final JListFixture selectItems(String...items) {
    int controlOrCommand = controlOrCommandKey();
    doPressKey(controlOrCommand);
    for (String item : items) selectItem(item);
    doReleaseKey(controlOrCommand);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JList}</code>.
   * @param text the text of the item to select.
   * @return this fixture.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   */
  public final JListFixture selectItem(String text) {
    driver.clickItem(target, text, LEFT_BUTTON, 1);
    return this;
  }

  /**
   * Simulates a user double-clicking an item in this fixture's <code>{@link JList}</code>.
   * @param index the index of the item to double-click.
   * @return this fixture.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public final JListFixture doubleClickItem(int index) {
    clickItem(index, LEFT_BUTTON, 2);
    return this;
  }

  /**
   * Simulates a user double-clicking an item in this fixture's <code>{@link JList}</code>.
   * @param text the text of the item to double-click.
   * @return this fixture.
   */
  public final JListFixture doubleClickItem(String text) {
    driver.clickItem(target, text, LEFT_BUTTON, 2);
    return this;
  }

  void clickItem(int index, MouseButton button, int times) {
    driver.clickItem(target, index, button, times);
  }

  /**
   * Shows a pop-up menu at the location of the specified item in this fixture's <code>{@link JList}</code>.
   * @param index the index of the item.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public final JPopupMenuFixture showPopupMenuAt(int index) {
    return showPopupMenuAt(driver.pointAt(target, index));
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in this fixture's
   * <code>{@link JList}</code> matches the given text.
   * @param text the text to match.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given text.
   */
  public final JListFixture requireSelection(String text) {
    int selectedIndex = target.getSelectedIndex();
    if (selectedIndex == -1) failNoSelection();
    assertThat(valueAt(selectedIndex)).as(selectedIndexProperty()).isEqualTo(text);
    return this;
  }

  /**
   * Verifies that the <code>String</code> representations of the selected items in this fixture's
   * <code>{@link JList}</code> match the given text items.
   * @param items text items to match.
   * @return this fixture.
   * @throws AssertionError if the selected items do not match the given text items.
   */
  public final JListFixture requireSelectedItems(String... items) {
    int[] selectedIndices = target.getSelectedIndices();
    int currentSelectionCount = selectedIndices.length;
    if (currentSelectionCount == 0) failNoSelection();
    assertThat(currentSelectionCount).as(formattedPropertyName("selectedIndices#length")).isEqualTo(items.length);
    for (int i = 0; i < currentSelectionCount; i++) {
      String description = formattedPropertyName(concat("selectedIndices[", valueOf(i), "]"));
      assertThat(valueAt(selectedIndices[i])).as(description).isEqualTo(items[i]);
    }
    return this;
  }

  private void failNoSelection() { fail(concat("[", selectedIndexProperty(), "] No selection")); }

  private String selectedIndexProperty() { return formattedPropertyName("selectedIndex"); }

  /**
   * Returns the <code>String</code> representation of an item in this fixture's <code>{@link JList}</code> . If such
   * <code>String</code> representation is not meaningful, this method will return <code>null</code>.
   * @param index the index of the item to return.
   * @return the String representation of the item under the given index, or <code>null</code> if nothing meaningful.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public String valueAt(int index) {
    return driver.text(target, index);
  }

  /**
   * Returns a fixture that manages the list item specified by the given index.
   * @param index of the item.
   * @return a fixture that manages the list item specified by the given index.
   * @throws ActionFailedException if the index is out of bounds.
   */
  public final JListItemFixture item(int index) {
    return new JListItemFixture(this, index);
  }

  /**
   * Returns a fixture that manages the list item specified by the given text.
   * @param text the text of the item.
   * @return a fixture that manages the list item specified by the given text.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   */
  public final JListItemFixture item(String text) {
    return new JListItemFixture(this, driver.indexOf(target, text));
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JList}</code>.
   * @return this fixture.
   */
  public final JListFixture click() {
    return (JListFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JList}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JListFixture click(MouseButton button) {
    return (JListFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JList}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JListFixture click(MouseClickInfo mouseClickInfo) {
    return (JListFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JList}</code>.
   * @return this fixture.
   */
  public final JListFixture rightClick() {
    return (JListFixture)doRightClick();
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JList}</code>.
   * @return this fixture.
   */
  public final JListFixture doubleClick() {
    return (JListFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JList}</code>.
   * @return this fixture.
   */
  public final JListFixture focus() {
    return (JListFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JList}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListFixture pressAndReleaseKeys(int... keyCodes) {
    return (JListFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JList}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListFixture pressKey(int keyCode) {
    return (JListFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JList}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListFixture releaseKey(int keyCode) {
    return (JListFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link JList}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JList</code> is not visible.
   */
  public final JListFixture requireVisible() {
    return (JListFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JList}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JList</code> is visible.
   */
  public final JListFixture requireNotVisible() {
    return (JListFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JList}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JList</code> is disabled.
   */
  public final JListFixture requireEnabled() {
    return (JListFixture)assertEnabled();
  }

  /**
   * Asserts that this fixture's <code>{@link JList}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JList</code> is never enabled.
   */
  public final JListFixture requireEnabled(Timeout timeout) {
    return (JListFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JList}</code> is not enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JList</code> is enabled.
   */
  public final JListFixture requireDisabled() {
    return (JListFixture)assertDisabled();
  }

  /**
   * Simulates a user dragging an item from this fixture's <code>{@link JList}</code>.
   * @param text the text of the item to drag.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @return this fixture.
   */
  public final JListFixture drag(String text) {
    driver.drag(target, text);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's <code>{@link JList}</code>.
   * @param text the text of the item to drop.
   * @return this fixture.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public final JListFixture drop(String text) {
    driver.drop(target, text);
    return this;
  }

  /**
   * Simulates a user dropping an item at the center of this fixture's <code>{@link JList}</code>.
   * @return this fixture.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public final JListFixture drop() {
    driver.drop(target);
    return this;
  }

  /**
   * Simulates a user dragging an item from this fixture's <code>{@link JList}</code>.
   * @param index the index of the item to drag.
   * @return this fixture.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   */
  public final JListFixture drag(int index) {
    driver.drag(target, index);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's <code>{@link JList}</code>.
   * @param index the index of the item to drop.
   * @return this fixture.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JList</code>.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public final JListFixture drop(int index) {
    driver.drop(target, index);
    return this;
  }
}
