/*
 * Created on Dec 16, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JButton;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JButton}</code> and verification of the state of such 
 * <code>{@link JButton}</code>.
 * 
 * @author Yvonne Wang
 */
public class JButtonFixture extends ComponentFixture<JButton> implements TextDisplayFixture<JButton> {

  /**
   * Creates a new </code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JButton</code>.
   * @param buttonName the name of the <code>JButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JButton</code> could not be found.
   */
  public JButtonFixture(RobotFixture robot, String buttonName) {
    super(robot, buttonName, JButton.class);
  }
  
  /**
   * Creates a new </code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JButton</code>.
   * @param target the <code>JButton</code> to be managed by this fixture.
   */
  public JButtonFixture(RobotFixture robot, JButton target) {
    super(robot, target);
  }

  /**
   * Simulates a user clicking the <code>{@link JButton}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JButtonFixture click() {
    return (JButtonFixture)doClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JButton}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JButtonFixture doubleClick() {
    return (JButtonFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JButton}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JButtonFixture focus() {
    return (JButtonFixture)doFocus();
  }

  /**
   * Asserts that the text of the <code>{@link JButton}</code> managed by this fixture is equal to the specified 
   * <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JButton is not equal to the given one.
   */
  public final JButtonFixture requireText(String expected) {
    assertThat(text()).isEqualTo(expected);
    return this;
  }

  /**
   * Returns the text of the <code>{@link JButton}</code> managed by this fixture. 
   * @return the text of the managed <code>JButton</code>. 
   */
  public final String text() {
    return target.getText();
  }

  /**
   * Simulates a user pressing the given keys on the <code>{@link JButton}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  @Override public final JButtonFixture pressKeys(int... keyCodes) {
    return (JButtonFixture)doPressKeys(keyCodes);
  }
  
  /**
   * Asserts that the <code>{@link JButton}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JButton</code> is not visible.
   */
  @Override public final JButtonFixture requireVisible() {
    return (JButtonFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JButton}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JButton</code> is visible.
   */
  @Override public final JButtonFixture requireNotVisible() {
    return (JButtonFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JButton}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JButton</code> is disabled.
   */
  @Override public final JButtonFixture requireEnabled() {
    return (JButtonFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JButton}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JButton</code> is enabled.
   */
  @Override public final JButtonFixture requireDisabled() {
    return (JButtonFixture)assertDisabled();
  }
}
