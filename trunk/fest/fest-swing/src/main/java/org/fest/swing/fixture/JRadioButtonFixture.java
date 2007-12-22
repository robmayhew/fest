/*
 * Created on Sep 18, 2007
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

import javax.swing.JRadioButton;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link JRadioButton}</code> and verification of the state of such 
 * <code>{@link JRadioButton}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JRadioButtonFixture extends TwoStateButtonFixture<JRadioButton> {

  /**
   * Creates a new <code>{@link JRadioButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JRadioButton</code>.
   * @param buttonName the name of the <code>JRadioButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JRadioButton</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JRadioButton</code> is found.
   */
  public JRadioButtonFixture(RobotFixture robot, String buttonName) {
    super(robot, buttonName, JRadioButton.class);
  }
  
  /**
   * Creates a new <code>{@link JRadioButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JRadioButton</code>.
   * @param target the <code>JRadioButton</code> to be managed by this fixture.
   */
  public JRadioButtonFixture(RobotFixture robot, JRadioButton target) {
    super(robot, target);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   */
  public final JRadioButtonFixture click() {
    return (JRadioButtonFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JRadioButton}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JRadioButtonFixture click(MouseButton button) {
    return (JRadioButtonFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JRadioButton}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JRadioButtonFixture click(MouseClickInfo mouseClickInfo) {
    return (JRadioButtonFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   */
  public final JRadioButtonFixture rightClick() {
    return (JRadioButtonFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   */
  public final JRadioButtonFixture doubleClick() {
    return (JRadioButtonFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JRadioButton}</code>.
   * @return this fixture.
   */
  public final JRadioButtonFixture focus() {
    return (JRadioButtonFixture)doFocus();
  }

  /**
   * Asserts that the text of this fixture's <code>{@link JRadioButton}</code> is equal to the specified 
   * <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JRadioButton is not equal to the given one.
   */
  public final JRadioButtonFixture requireText(String expected) {
    assertThat(text()).as(formattedPropertyName("text")).isEqualTo(expected);
    return this;
  }

  /**
   * Returns the text of this fixture's <code>{@link JRadioButton}</code>. 
   * @return the text of this fixture's <code>JRadioButton</code>. 
   */
  public final String text() {
    return target.getText();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JRadioButton}</code>. This 
   * method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JRadioButtonFixture pressAndReleaseKeys(int... keyCodes) {
    return (JRadioButtonFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JRadioButton}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JRadioButtonFixture pressKey(int keyCode) {
    return (JRadioButtonFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JRadioButton}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JRadioButtonFixture releaseKey(int keyCode) {
    return (JRadioButtonFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is not visible.
   */
  public final JRadioButtonFixture requireVisible() {
    return (JRadioButtonFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is visible.
   */
  public final JRadioButtonFixture requireNotVisible() {
    return (JRadioButtonFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError is this fixture's <code>JRadioButton</code> is disabled.
   */
  public final JRadioButtonFixture requireEnabled() {
    return (JRadioButtonFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JRadioButton</code> is never enabled.
   */
  public final JRadioButtonFixture requireEnabled(Timeout timeout) {
    return (JRadioButtonFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JRadioButton}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError is this fixture's <code>JRadioButton</code> is enabled.
   */
  public final JRadioButtonFixture requireDisabled() {
    return (JRadioButtonFixture)assertDisabled();
  }
  
  /**
   * Verifies that this fixture's <code>{@link JRadioButton}</code> is selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is not selected.
   */
  public final JRadioButtonFixture requireSelected() {
    return (JRadioButtonFixture)assertSelected();
  }

  /**
   * Verifies that this fixture's <code>{@link JRadioButton}</code> is not selected.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JRadioButton</code> is selected.
   */
  public final JRadioButtonFixture requireNotSelected() {
    return (JRadioButtonFixture)assertNotSelected();
  }
}
