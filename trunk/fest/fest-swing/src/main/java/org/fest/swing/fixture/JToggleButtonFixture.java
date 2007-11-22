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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;

import javax.swing.JToggleButton;

/**
 * Understands simulation of user events on a <code>{@link JToggleButton}</code> and verification of the state of such
 * <code>{@link JToggleButton}</code>.
 *
 * @author Alex Ruiz
 */
public class JToggleButtonFixture extends TwoStateButtonFixture<JToggleButton> {

  /**
   * Creates a new <code>{@link org.fest.swing.fixture.JToggleButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param checkBoxName the name of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @throws org.fest.swing.exception.ComponentLookupException if a matching <code>JToggleButton</code> could not be found.
   */
  public JToggleButtonFixture(RobotFixture robot, String checkBoxName) {
    super(robot, checkBoxName, JToggleButton.class);
  }

  /**
   * Creates a new <code>{@link JToggleButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JToggleButton</code>.
   * @param target the <code>JToggleButton</code> to be managed by this fixture.
   */
  public JToggleButtonFixture(RobotFixture robot, JToggleButton target) {
    super(robot, target);
  }

  /**
   * Checks (or selects) the <code>{@link JToggleButton}</code> managed by this fixture only it is not already checked.
   * @return this fixture.
   */
  public final JToggleButtonFixture check() {
    if (target.isSelected()) return this;
    return click();
  }

  /**
   * Unchecks the <code>{@link JToggleButton}</code> managed by this fixture only if it is checked.
   * @return this fixture.
   */
  public final JToggleButtonFixture uncheck() {
    if (!target.isSelected()) return this;
    return click();
  }

  /**
   * Simulates a user clicking the <code>{@link JToggleButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToggleButtonFixture click() {
    return (JToggleButtonFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JToggleButton}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JToggleButtonFixture click(MouseButton button) {
    return (JToggleButtonFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JToggleButton}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JToggleButtonFixture click(MouseClickInfo mouseClickInfo) {
    return (JToggleButtonFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JToggleButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToggleButtonFixture rightClick() {
    return (JToggleButtonFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JToggleButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToggleButtonFixture doubleClick() {
    return (JToggleButtonFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JToggleButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToggleButtonFixture focus() {
    return (JToggleButtonFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JToggleButton}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JToggleButtonFixture pressAndReleaseKeys(int... keyCodes) {
    return (JToggleButtonFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on the <code>{@link JToggleButton}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JToggleButtonFixture pressKey(int keyCode) {
    return (JToggleButtonFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on the <code>{@link JToggleButton}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JToggleButtonFixture releaseKey(int keyCode) {
    return (JToggleButtonFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that the text of the <code>{@link JToggleButton}</code> managed by this fixture is equal to the specified
   * <code>String</code>.
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JToggleButton is not equal to the given one.
   */
  public final JToggleButtonFixture requireText(String expected) {
    assertThat(text()).as(formattedPropertyName("text")).isEqualTo(expected);
    return this;
  }

  /**
   * Returns the text of the <code>{@link JToggleButton}</code> managed by this fixture.
   * @return the text of the managed <code>JToggleButton</code>.
   */
  public final String text() {
    return target.getText();
  }

  /**
   * Asserts that the <code>{@link JToggleButton}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JToggleButton</code> is not visible.
   */
  public final JToggleButtonFixture requireVisible() {
    return (JToggleButtonFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JToggleButton}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JToggleButton</code> is visible.
   */
  public final JToggleButtonFixture requireNotVisible() {
    return (JToggleButtonFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JToggleButton}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JToggleButton</code> is disabled.
   */
  public final JToggleButtonFixture requireEnabled() {
    return (JToggleButtonFixture)assertEnabled();
  }

  /**
   * Asserts that the <code>{@link JToggleButton}</code> managed by this fixture is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if the managed <code>JToggleButton</code> is never enabled.
   */
  public final JToggleButtonFixture requireEnabled(Timeout timeout) {
    return (JToggleButtonFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that the <code>{@link JToggleButton}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JToggleButton</code> is enabled.
   */
  public final JToggleButtonFixture requireDisabled() {
    return (JToggleButtonFixture)assertDisabled();
  }

  /**
   * Verifies that the <code>{@link JToggleButton}</code> managed by this fixture is selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JToggleButton</code> managed by this fixture is not selected.
   */
  public final JToggleButtonFixture requireSelected() {
    return (JToggleButtonFixture)assertSelected();
  }

  /**
   * Verifies that the <code>{@link JToggleButton}</code> managed by this fixture is not selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JToggleButton</code> managed by this fixture is selected.
   */
  public final JToggleButtonFixture requireNotSelected() {
    return (JToggleButtonFixture)assertNotSelected();
  }
}