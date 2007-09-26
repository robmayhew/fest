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

import org.fest.swing.ComponentLookupException;
import org.fest.swing.MouseButton;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JRadioButton}</code> and verification of the state of such 
 * <code>{@link JRadioButton}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JRadioButtonFixture extends JToggleButtonFixture<JRadioButton> {

  /**
   * Creates a new </code>{@link JRadioButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JRadioButton</code>.
   * @param buttonName the name of the <code>JRadioButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JRadioButton</code> could not be found.
   */
  public JRadioButtonFixture(RobotFixture robot, String buttonName) {
    super(robot, buttonName, JRadioButton.class);
  }
  
  /**
   * Creates a new </code>{@link JRadioButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JRadioButton</code>.
   * @param target the <code>JRadioButton</code> to be managed by this fixture.
   */
  public JRadioButtonFixture(RobotFixture robot, JRadioButton target) {
    super(robot, target);
  }

  /**
   * Simulates a user clicking the <code>{@link JRadioButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JRadioButtonFixture click() {
    return (JRadioButtonFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JRadioButton}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JRadioButtonFixture click(MouseButton button) {
    return (JRadioButtonFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JRadioButton}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JRadioButtonFixture click(MouseClickInfo mouseClickInfo) {
    return (JRadioButtonFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JRadioButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JRadioButtonFixture rightClick() {
    return (JRadioButtonFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JRadioButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JRadioButtonFixture doubleClick() {
    return (JRadioButtonFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JRadioButton}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JRadioButtonFixture focus() {
    return (JRadioButtonFixture)doFocus();
  }

  /**
   * Asserts that the text of the <code>{@link JRadioButton}</code> managed by this fixture is equal to the specified 
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
   * Returns the text of the <code>{@link JRadioButton}</code> managed by this fixture. 
   * @return the text of the managed <code>JRadioButton</code>. 
   */
  public final String text() {
    return target.getText();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JRadioButton}</code> managed by this
   * fixture. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JRadioButtonFixture pressAndReleaseKeys(int... keyCodes) {
    return (JRadioButtonFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on the <code>{@link JRadioButton}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JRadioButtonFixture pressKey(int keyCode) {
    return (JRadioButtonFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JRadioButton}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JRadioButtonFixture releaseKey(int keyCode) {
    return (JRadioButtonFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JRadioButton}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JRadioButton</code> is not visible.
   */
  public final JRadioButtonFixture requireVisible() {
    return (JRadioButtonFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JRadioButton}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JRadioButton</code> is visible.
   */
  public final JRadioButtonFixture requireNotVisible() {
    return (JRadioButtonFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JRadioButton}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JRadioButton</code> is disabled.
   */
  public final JRadioButtonFixture requireEnabled() {
    return (JRadioButtonFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JRadioButton}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JRadioButton</code> is enabled.
   */
  public final JRadioButtonFixture requireDisabled() {
    return (JRadioButtonFixture)assertDisabled();
  }
  
  /**
   * Verifies that the <code>{@link JRadioButton}</code> managed by this fixture is selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JRadioButton</code> managed by this fixture is not selected.
   */
  public final JRadioButtonFixture requireSelected() {
    return (JRadioButtonFixture)assertSelected();
  }

  /**
   * Verifies that the <code>{@link JRadioButton}</code> managed by this fixture is not selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JRadioButton</code> managed by this fixture is selected.
   */
  public final JRadioButtonFixture requireNotSelected() {
    return (JRadioButtonFixture)assertNotSelected();
  }
}
