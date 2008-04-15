/*
 * Created on Apr 9, 2007
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

import javax.swing.JComboBox;
import javax.swing.JList;

import org.fest.swing.cell.BasicJComboBoxCellReader;
import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JComboBoxDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Understands simulation of user events on a <code>{@link JComboBox}</code> and verification of the state of such
 * <code>{@link JComboBox}</code>.
 * <p>
 * The conversion between the values given in tests and the values being displayed by a <code>{@link JComboBox}</code>
 * renderer is performed by a <code>{@link JComboBoxCellReader}</code>. This fixture uses a 
 * <code>{@link JComboBoxCellReader}</code> by default.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxFixture extends JPopupMenuInvokerFixture<JComboBox> implements ItemGroupFixture,
    EditableComponentFixture {

  private JComboBoxDriver driver;

  /**
   * Creates a new <code>{@link JComboBoxFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JComboBox</code>.
   * @param target the <code>JComboBox</code> to be managed by this fixture.
   */
  public JComboBoxFixture(Robot robot, JComboBox target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JComboBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JComboBox</code>.
   * @param comboBoxName the name of the <code>JComboBox</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JComboBox</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JComboBox</code> is found.
   */
  public JComboBoxFixture(Robot robot, String comboBoxName) {
    super(robot, comboBoxName, JComboBox.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JComboBoxDriver(robot));
  }

  final void updateDriver(JComboBoxDriver driver) {
    this.driver = driver;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JComboBox}</code>.
   * @return this fixture.
   */
  public JComboBoxFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JComboBox}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JComboBoxFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JComboBox}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JComboBoxFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JComboBox}</code>.
   * @return this fixture.
   */
  public JComboBoxFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JComboBox}</code>.
   * @return this fixture.
   */
  public JComboBoxFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Returns the elements in this fixture's <code>{@link JComboBox}</code> as <code>String</code>s.
   * @return the elements in this fixture's <code>JComboBox</code>.
   */
  public String[] contents() {
    return driver.contentsOf(target);
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link JComboBox}</code>, replacing any text. This action
   * is executed only if the <code>{@link JComboBox}</code> is editable.
   * @param text the text to enter.
   * @return this fixture.
   */
  public JComboBoxFixture replaceText(String text) {
    driver.replaceText(target, text);
    return this;
  }

  /**
   * Simulates a user selecting the text in the <code>{@link JComboBox}</code>. This action is executed only if the
   * <code>{@link JComboBox}</code> is editable.
   * @return this fixture.
   */
  public JComboBoxFixture selectAllText() {
    driver.selectAllText(target);
    return this;
  }

  /**
   * Simulates a user entering the specified text in this fixture's <code>{@link JComboBox}</code>. This action is
   * executed only if the <code>{@link JComboBox}</code> is editable.
   * @param text the text to enter.
   * @return this fixture.
   */
  public JComboBoxFixture enterText(String text) {
    driver.enterText(target, text);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JComboBox}</code>.
   * @return this fixture.
   */
  public JComboBoxFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Finds and returns the {@link JList} in the pop-up raised by this fixture's <code>{@link JComboBox}</code>.
   * @return the <code>JList</code> in the pop-up raised by this fixture's <code>JComboBox</code>.
   */
  public JList list() {
    return driver.dropDownList();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JComboBox}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JComboBoxFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JComboBox}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JComboBoxFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JComboBox}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JComboBoxFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is enabled.
   */
  public JComboBoxFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is disabled.
   */
  public JComboBoxFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JComboBox</code> is never enabled.
   */
  public JComboBoxFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is visible.
   */
  public JComboBoxFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is not visible.
   */
  public JComboBoxFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JComboBox}</code>.
   * @param index the index of the item to select.
   * @return this fixture.
   * @throws LocationUnavailableException if the given index is negative or greater than the index of the last item in
   *         the <code>JComboBox</code>.
   */
  public JComboBoxFixture selectItem(int index) {
    driver.selectItem(target, index);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JComboBox}</code>.
   * @param text the text of the item to select.
   * @return this fixture.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   */
  public JComboBoxFixture selectItem(String text) {
    driver.selectItem(target, text);
    return this;
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in this fixture's
   * <code>{@link JComboBox}</code> matches the given text.
   * @param text the text to match.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given text.
   */
  public JComboBoxFixture requireSelection(String text) {
    driver.requireSelection(target, text);
    return this;
  }

  /**
   * Returns the <code>String</code> representation of an item in this fixture's <code>{@link JComboBox}</code>. If such
   * <code>String</code> representation is not meaningful, this method will return <code>null</code>.
   * @param index the index of the item to return.
   * @return the String representation of the item under the given index, or <code>null</code> if nothing meaningful.
   */
  public String valueAt(int index) {
    return driver.value(target, index);
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is editable.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is not editable.
   * @return this fixture.
   */
  public JComboBoxFixture requireEditable() {
    driver.requireEditable(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is not editable.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is editable.
   * @return this fixture.
   */
  public JComboBoxFixture requireNotEditable() {
    driver.requireNotEditable(target);
    return this;
  }

  /**
   * Updates the implementation of <code>{@link JComboBoxCellReader}</code> to use when comparing internal values
   * of this fixture's <code>{@link JComboBox}</code> and the values expected in a test. The default implementation to
   * use is <code>{@link BasicJComboBoxCellReader}</code>.
   * @param cellReader the new <code>JComboBoxCellValueReader</code> to use.
   */
  public void cellReader(JComboBoxCellReader cellReader) {
    driver.cellReader(cellReader);
  }
}
