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

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JCheckBox}</code> and verification of the state of such 
 * <code>{@link JCheckBox}</code>.
 * 
 * @author Alex Ruiz
 */
public class JCheckBoxFixture extends ComponentFixture<JCheckBox> {

  /**
   * Creates a new </code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JCheckBox</code>.
   * @param checkBoxName the name of the <code>JCheckBox</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JCheckBox</code> could not be found.
   */
  public JCheckBoxFixture(RobotFixture robot, String checkBoxName) {
    super(robot, checkBoxName, JCheckBox.class);
  }
  
  /**
   * Creates a new </code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JCheckBox</code>.
   * @param target the <code>JCheckBox</code> to be managed by this fixture.
   */
  public JCheckBoxFixture(RobotFixture robot, JCheckBox target) {
    super(robot, target);
  }

  /**
   * Checks (or selects) the <code>{@link JCheckBox}</code> managed by this fixture only it is not already checked.
   * @return this fixture.
   */
  public final JCheckBoxFixture check() {
    if (target.isSelected()) return this;
    return click();
  }

  /**
   * Unchecks the <code>{@link JCheckBox}</code> managed by this fixture only if it is checked.
   * @return this fixture.
   */
  public final JCheckBoxFixture uncheck() {
    if (!target.isSelected()) return this;
    return click();
  }

  /**
   * Simulates a user clicking the <code>{@link JCheckBox}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JCheckBoxFixture click() {
    return (JCheckBoxFixture)super.click();
  }

  /**
   * Gives input focus to the <code>{@link JCheckBox}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JCheckBoxFixture focus() {
    return (JCheckBoxFixture)super.focus();
  }
  
  /**
   * Simulates a user pressing the given keys on the <code>{@link JCheckBox}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  @Override public final JCheckBoxFixture pressKeys(int... keyCodes) {
    return (JCheckBoxFixture)super.pressKeys(keyCodes);
  }
  
  /**
   * Asserts that the <code>{@link JCheckBox}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JCheckBox</code> is not visible.
   */
  @Override public final JCheckBoxFixture requireVisible() {
    return (JCheckBoxFixture)super.requireVisible();
  }

  /**
   * Asserts that the <code>{@link JCheckBox}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JCheckBox</code> is visible.
   */
  @Override public final JCheckBoxFixture requireNotVisible() {
    return (JCheckBoxFixture)super.requireNotVisible();
  }

  /**
   * Asserts that the <code>{@link JCheckBox}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JCheckBox</code> is disabled.
   */
  @Override public final JCheckBoxFixture requireEnabled() {
    return (JCheckBoxFixture)super.requireEnabled();
  }

  /**
   * Asserts that the <code>{@link JCheckBox}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JCheckBox</code> is enabled.
   */
  @Override public final JCheckBoxFixture requireDisabled() {
    return (JCheckBoxFixture)super.requireDisabled();
  }
}
