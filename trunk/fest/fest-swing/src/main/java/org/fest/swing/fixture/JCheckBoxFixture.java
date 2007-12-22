/*
 * Created on Jun 10, 2007
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

import javax.swing.JCheckBox;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link JCheckBox}</code> and verification of the state of such 
 * <code>{@link JCheckBox}</code>.
 * 
 * @author Alex Ruiz
 */
public class JCheckBoxFixture extends TwoStateButtonFixture<JCheckBox> {

  /**
   * Creates a new <code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JCheckBox</code>.
   * @param checkBoxName the name of the <code>JCheckBox</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JCheckBox</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JCheckBox</code> is found.
   */
  public JCheckBoxFixture(RobotFixture robot, String checkBoxName) {
    super(robot, checkBoxName, JCheckBox.class);
  }
  
  /**
   * Creates a new <code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JCheckBox</code>.
   * @param target the <code>JCheckBox</code> to be managed by this fixture.
   */
  public JCheckBoxFixture(RobotFixture robot, JCheckBox target) {
    super(robot, target);
  }

  /**
   * Checks (or selects) this fixture's <code>{@link JCheckBox}</code> only it is not already checked.
   * @return this fixture.
   */
  public final JCheckBoxFixture check() {
    if (target.isSelected()) return this;
    return click();
  }

  /**
   * Unchecks this fixture's <code>{@link JCheckBox}</code> only if it is checked.
   * @return this fixture.
   */
  public final JCheckBoxFixture uncheck() {
    if (!target.isSelected()) return this;
    return click();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public final JCheckBoxFixture click() {
    return (JCheckBoxFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JCheckBoxFixture click(MouseButton button) {
    return (JCheckBoxFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JCheckBoxFixture click(MouseClickInfo mouseClickInfo) {
    return (JCheckBoxFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public final JCheckBoxFixture rightClick() {
    return (JCheckBoxFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public final JCheckBoxFixture doubleClick() {
    return (JCheckBoxFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public final JCheckBoxFixture focus() {
    return (JCheckBoxFixture)doFocus();
  }
  
  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JCheckBox}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JCheckBoxFixture pressAndReleaseKeys(int... keyCodes) {
    return (JCheckBoxFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JCheckBox}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JCheckBoxFixture pressKey(int keyCode) {
    return (JCheckBoxFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JCheckBox}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JCheckBoxFixture releaseKey(int keyCode) {
    return (JCheckBoxFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the text of this fixture's <code>{@link JCheckBox}</code> is equal to the specified 
   * <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JCheckBox is not equal to the given one.
   */
  public final JCheckBoxFixture requireText(String expected) {
    assertThat(text()).as(formattedPropertyName("text")).isEqualTo(expected);
    return this;
  }

  /**
   * Returns the text of this fixture's <code>{@link JCheckBox}</code>. 
   * @return the text of this fixture's <code>JCheckBox</code>. 
   */
  public final String text() {
    return target.getText();
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is not visible.
   */
  public final JCheckBoxFixture requireVisible() {
    return (JCheckBoxFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is visible.
   */
  public final JCheckBoxFixture requireNotVisible() {
    return (JCheckBoxFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is disabled.
   */
  public final JCheckBoxFixture requireEnabled() {
    return (JCheckBoxFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JCheckBox</code> is never enabled.
   */
  public final JCheckBoxFixture requireEnabled(Timeout timeout) {
    return (JCheckBoxFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is enabled.
   */
  public final JCheckBoxFixture requireDisabled() {
    return (JCheckBoxFixture)assertDisabled();
  }

  /**
   * Verifies that this fixture's <code>{@link JCheckBox}</code> is selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JCheckBox</code> managed by this fixture is not selected.
   */
  public final JCheckBoxFixture requireSelected() {
    return (JCheckBoxFixture)assertSelected();
  }

  /**
   * Verifies that this fixture's <code>{@link JCheckBox}</code> is not selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JCheckBox</code> managed by this fixture is selected.
   */
  public final JCheckBoxFixture requireNotSelected() {
    return (JCheckBoxFixture)assertNotSelected();
  }
}
