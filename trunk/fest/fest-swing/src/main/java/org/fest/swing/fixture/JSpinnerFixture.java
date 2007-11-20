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

import javax.swing.JSpinner;

import abbot.tester.JSpinnerTester;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JSpinner}</code> and verification of the state of such
 * <code>{@link JSpinner}</code>.
 *
 * @author Yvonne Wang
 */
public class JSpinnerFixture extends ComponentFixture<JSpinner> {

  /**
   * Creates a new <code>{@link JSpinnerFixture}</code>.
   * @param robot performs simulation of user events on a <code>JSpinner</code>.
   * @param spinnerName the name of the <code>JSpinner</code> to find using the given <code>RobotFixture</code>.
   * @throws org.fest.swing.exception.ComponentLookupException if a matching <code>JSpinner</code> could not be found.
   */
  public JSpinnerFixture(RobotFixture robot, String spinnerName) {
    super(robot, spinnerName, JSpinner.class);
  }

  /**
   * Creates a new <code>{@link JSpinnerFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JSpinner</code>.
   * @param target the <code>JSpinner</code> to be managed by this fixture.
   */
  public JSpinnerFixture(RobotFixture robot, JSpinner target) {
    super(robot, target);
  }

  /**
   * Simulates a user increasing the value of the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture. 
   */
  public final JSpinnerFixture increment() {
    spinnerTester().actionIncrement(target);
    return this;
  }

  /**
   * Simulates a user decreasing the value of the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture. 
   */
  public final JSpinnerFixture decrement() {
    spinnerTester().actionDecrement(target);
    return this;
  }
  
  /**
   * Simulates a user entering the given text in the <code>{@link JSpinner}</code> managed by this fixture.
   * @param text the text to enter.
   * @return this fixture.
   */
  public final JSpinnerFixture enterText(String text) {
    spinnerTester().actionSetValue(target, text);
    return this;
  }

  protected final JSpinnerTester spinnerTester() {
    return (JSpinnerTester)tester();
  }

  /**
   * Simulates a user clicking the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSpinnerFixture click() {
    return (JSpinnerFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JSpinner}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JSpinnerFixture click(MouseButton button) {
    return (JSpinnerFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JSpinner}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JSpinnerFixture click(MouseClickInfo mouseClickInfo) {
    return (JSpinnerFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSpinnerFixture rightClick() {
    return (JSpinnerFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSpinnerFixture doubleClick() {
    return (JSpinnerFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSpinnerFixture focus() {
    return (JSpinnerFixture)doFocus();
  }
  
  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JSpinner}</code> managed by this
   * fixture. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSpinnerFixture pressAndReleaseKeys(int... keyCodes) {
    return (JSpinnerFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on the <code>{@link JSpinner}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSpinnerFixture pressKey(int keyCode) {
    return (JSpinnerFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JSpinner}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSpinnerFixture releaseKey(int keyCode) {
    return (JSpinnerFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSpinner</code> is not visible.
   */
  public final JSpinnerFixture requireVisible() {
    return (JSpinnerFixture)assertVisible();
  }
  
  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSpinner</code> is visible.
   */
  public final JSpinnerFixture requireNotVisible() {
    return (JSpinnerFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSpinner</code> is disabled.
   */
  public final JSpinnerFixture requireEnabled() {
    return (JSpinnerFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if the managed <code>JSpinner</code> is never enabled.
   */
  public final JSpinnerFixture requireEnabled(Timeout timeout) {
    return (JSpinnerFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSpinner</code> is enabled.
   */
  public final JSpinnerFixture requireDisabled() {
    return (JSpinnerFixture)assertDisabled();
  }
}
