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

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JSpinner}</code> and verification of the state of such
 * <code>{@link JSpinner}</code>.
 *
 * @author Yvonne Wang
 */
public class JSpinnerFixture extends ComponentFixture<JSpinner> {

  /**
   * Creates a new </code>{@link JSpinnerFixture}</code>.
   * @param robot performs simulation of user events on a <code>JSpinner</code>.
   * @param spinnerName the name of the <code>JSpinner</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JSpinner</code> could not be found.
   */
  public JSpinnerFixture(RobotFixture robot, String spinnerName) {
    super(robot, spinnerName, JSpinner.class);
  }

  /**
   * Creates a new </code>{@link JSpinnerFixture}</code>.
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
    return testerCastedTo(JSpinnerTester.class);
  }

  /**
   * Simulates a user clicking the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JSpinnerFixture click() {
    return (JSpinnerFixture)super.click();
  }

  /**
   * Gives input focus to the <code>{@link JSpinner}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JSpinnerFixture focus() {
    return (JSpinnerFixture)super.focus();
  }
  
  /**
   * Simulates a user pressing the given keys on the <code>{@link JSpinner}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  @Override public final JSpinnerFixture pressKeys(int... keyCodes) {
    return (JSpinnerFixture)super.pressKeys(keyCodes);
  }
  
  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSpinner</code> is not visible.
   */
  @Override public final JSpinnerFixture requireVisible() {
    return (JSpinnerFixture)super.requireVisible();
  }
  
  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSpinner</code> is visible.
   */
  @Override public final JSpinnerFixture requireNotVisible() {
    return (JSpinnerFixture)super.requireNotVisible();
  }

  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSpinner</code> is disabled.
   */
  @Override public final JSpinnerFixture requireEnabled() {
    return (JSpinnerFixture)super.requireEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JSpinner}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSpinner</code> is enabled.
   */
  @Override public final JSpinnerFixture requireDisabled() {
    return (JSpinnerFixture)super.requireDisabled();
  }
}
