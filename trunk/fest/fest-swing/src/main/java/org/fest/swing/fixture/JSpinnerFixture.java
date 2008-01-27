/*
 * Created on Jul 1, 2007
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

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JSpinnerDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link JSpinner}</code> and verification of the state of such
 * <code>{@link JSpinner}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSpinnerFixture extends ComponentFixture<JSpinner> {

  private final JSpinnerDriver driver;

  /**
   * Creates a new <code>{@link JSpinnerFixture}</code>.
   * @param robot performs simulation of user events on a <code>JSpinner</code>.
   * @param spinnerName the name of the <code>JSpinner</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JSpinner</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JSpinner</code> is found.
   */
  public JSpinnerFixture(RobotFixture robot, String spinnerName) {
    super(robot, spinnerName, JSpinner.class);
    driver = newSpinnerDriver();
  }

  /**
   * Creates a new <code>{@link JSpinnerFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JSpinner</code>.
   * @param target the <code>JSpinner</code> to be managed by this fixture.
   */
  public JSpinnerFixture(RobotFixture robot, JSpinner target) {
    super(robot, target);
    driver = newSpinnerDriver();
  }

  private JSpinnerDriver newSpinnerDriver() {
    return new JSpinnerDriver(robot, target);
  }

  /**
   * Verifies that the value of this fixture's <code>{@link JSpinner}</code> is equal to the given one.
   * @param value the expected value of this fixture's <code>JSpinner</code>.
   * @return this fixture.
   * @throws AssertionError if the value of this fixture's <code>JSpinner</code> is not equal to the given one.
   */
  public final JSpinnerFixture requireValue(Object value) {
    assertThat(target.getValue()).as(formattedPropertyName("value")).isEqualTo(value);
    return this;
  }

  /**
   * Simulates a user incrementing the value of this fixture's <code>{@link JSpinner}</code> the given number of times.
   * @param times how many times the value of this fixture's <code>JSpinner</code> should be incremented.
   * @return this fixture.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public final JSpinnerFixture increment(int times) {
    driver.increment(times);
    return this;
  }

  /**
   * Simulates a user incrementing the value of this fixture's <code>{@link JSpinner}</code> one time.
   * @return this fixture.
   */
  public final JSpinnerFixture increment() {
    driver.increment();
    return this;
  }

  /**
   * Simulates a user decrementing the value of this fixture's <code>{@link JSpinner}</code> the given number of times.
   * @param times how many times the value of this fixture's <code>JSpinner</code> should be decremented.
   * @return this fixture.
   * @throws ActionFailedException if <code>times</code> is less than or equal to zero.
   */
  public final JSpinnerFixture decrement(int times) {
    driver.decrement(times);
    return this;
  }

  /**
   * Simulates a user decrementing the value of this fixture's <code>{@link JSpinner}</code> one time.
   * @return this fixture.
   */
  public final JSpinnerFixture decrement() {
    driver.decrement();
    return this;
  }

  /**
   * Simulates a user entering the given text in this fixture's <code>{@link JSpinner}</code>, assuming its editor has a
   * <code>{@link JTextComponent}</code> under it.
   * @param text the text to enter.
   * @return this fixture.
   * @throws ActionFailedException if the editor of the <code>JSpinner</code> is not a <code>JTextComponent</code> or
   *          cannot be found.
   */
  public final JSpinnerFixture enterText(String text) {
    driver.enterText(text);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSpinner}</code>.
   * @return this fixture.
   */
  public final JSpinnerFixture click() {
    return (JSpinnerFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSpinner}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JSpinnerFixture click(MouseButton button) {
    return (JSpinnerFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSpinner}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JSpinnerFixture click(MouseClickInfo mouseClickInfo) {
    return (JSpinnerFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JSpinner}</code>.
   * @return this fixture.
   */
  public final JSpinnerFixture rightClick() {
    return (JSpinnerFixture)doRightClick();
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JSpinner}</code>.
   * @return this fixture.
   */
  public final JSpinnerFixture doubleClick() {
    return (JSpinnerFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JSpinner}</code>.
   * @return this fixture.
   */
  public final JSpinnerFixture focus() {
    return (JSpinnerFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JSpinner}</code>. This method
   * does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSpinnerFixture pressAndReleaseKeys(int... keyCodes) {
    return (JSpinnerFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JSpinner}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSpinnerFixture pressKey(int keyCode) {
    return (JSpinnerFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JSpinner}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSpinnerFixture releaseKey(int keyCode) {
    return (JSpinnerFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link JSpinner}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSpinner</code> is not visible.
   */
  public final JSpinnerFixture requireVisible() {
    return (JSpinnerFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JSpinner}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSpinner</code> is visible.
   */
  public final JSpinnerFixture requireNotVisible() {
    return (JSpinnerFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JSpinner}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSpinner</code> is disabled.
   */
  public final JSpinnerFixture requireEnabled() {
    return (JSpinnerFixture)assertEnabled();
  }

  /**
   * Asserts that this fixture's <code>{@link JSpinner}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JSpinner</code> is never enabled.
   */
  public final JSpinnerFixture requireEnabled(Timeout timeout) {
    return (JSpinnerFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JSpinner}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSpinner</code> is enabled.
   */
  public final JSpinnerFixture requireDisabled() {
    return (JSpinnerFixture)assertDisabled();
  }
}
