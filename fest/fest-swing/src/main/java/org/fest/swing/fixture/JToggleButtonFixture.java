/*
 * Created on Nov 22, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JToggleButton;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link JToggleButton}</code> and verification of the state of such
 * <code>{@link JToggleButton}</code>.
 *
 * @author Alex Ruiz
 */
public class JToggleButtonFixture extends TwoStateButtonFixture<JToggleButton> {

  private AbstractButtonDriver driver;

  /**
   * Creates a new <code>{@link JToggleButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JToggleButton</code>.
   * @param target the <code>JToggleButton</code> to be managed by this fixture.
   */
  public JToggleButtonFixture(Robot robot, JToggleButton target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link org.fest.swing.fixture.JToggleButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param checkBoxName the name of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JToggleButton</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JToggleButton</code> is found.
   */
  public JToggleButtonFixture(Robot robot, String checkBoxName) {
    super(robot, checkBoxName, JToggleButton.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new AbstractButtonDriver(robot));
  }

  final void updateDriver(AbstractButtonDriver driver) {
    this.driver = driver;
  }

  /**
   * Checks (or selects) this fixture's <code>{@link JToggleButton}</code> only it is not already checked.
   * @return this fixture.
   */
  public JToggleButtonFixture check() {
    driver.select(target);
    return this;
  }

  /**
   * Unchecks this fixture's <code>{@link JToggleButton}</code> only if it is checked.
   * @return this fixture.
   */
  public JToggleButtonFixture uncheck() {
    driver.unselect(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JToggleButton}</code>.
   * @return this fixture.
   */
  public JToggleButtonFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JToggleButton}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JToggleButtonFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JToggleButton}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JToggleButtonFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JToggleButton}</code>.
   * @return this fixture.
   */
  public JToggleButtonFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JToggleButton}</code>.
   * @return this fixture.
   */
  public JToggleButtonFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JToggleButton}</code>.
   * @return this fixture.
   */
  public JToggleButtonFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JToggleButton}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JToggleButtonFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JToggleButton}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JToggleButtonFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JToggleButton}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JToggleButtonFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JToggleButton}</code> is selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is not selected.
   */
  public JToggleButtonFixture requireSelected() {
    driver.requireSelected(target);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JToggleButton}</code> is not selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is selected.
   */
  public JToggleButtonFixture requireNotSelected() {
    driver.requireNotSelected(target);
    return this;
  }

  /**
   * Asserts that the text of this fixture's <code>{@link JToggleButton}</code> is equal to the specified
   * <code>String</code>.
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JToggleButton is not equal to the given one.
   */
  public JToggleButtonFixture requireText(String expected) {
    driver.requireText(target, expected);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToggleButton}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is enabled.
   */
  public JToggleButtonFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToggleButton}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is disabled.
   */
  public JToggleButtonFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToggleButton}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JToggleButton</code> is never enabled.
   */
  public JToggleButtonFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToggleButton}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is visible.
   */
  public JToggleButtonFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JToggleButton}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JToggleButton</code> is not visible.
   */
  public JToggleButtonFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Returns the text of this fixture's <code>{@link JToggleButton}</code>.
   * @return the text of this fixture's <code>JToggleButton</code>.
   */
  public String text() {
    return target.getText();
  }
}