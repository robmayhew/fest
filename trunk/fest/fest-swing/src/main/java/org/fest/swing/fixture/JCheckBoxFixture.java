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

import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JCheckBox}</code> and output verification.
 *
 * @author Alex Ruiz
 */
public class JCheckBoxFixture extends ComponentFixture<JCheckBox> {

  /**
   * Creates a new </code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JCheckBox</code>.
   * @param checkBoxName the name of the combobox to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public JCheckBoxFixture(RobotFixture robot, String checkBoxName) {
    super(robot, checkBoxName, JCheckBox.class);
  }
  
  /**
   * Creates a new </code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on the given checkbox.
   * @param target the target combo box.
   */
  public JCheckBoxFixture(RobotFixture robot, JCheckBox target) {
    super(robot, target);
  }

  /**
   * Checks (or selects) the target checkbox only it is not already checked.
   * @return this fixture.
   */
  public final JCheckBoxFixture check() {
    if (target.isSelected()) return this;
    return click();
  }

  /**
   * Unchecks the target checkbox only if it is checked.
   * @return this fixture.
   */
  public final JCheckBoxFixture uncheck() {
    if (!target.isSelected()) return this;
    return click();
  }

  /** {@inheritDoc} */
  @Override public final JCheckBoxFixture click() {
    return (JCheckBoxFixture)super.click();
  }

  /** {@inheritDoc} */
  @Override public final JCheckBoxFixture focus() {
    return (JCheckBoxFixture)super.focus();
  }

  /** {@inheritDoc} */
  @Override public final JCheckBoxFixture requireDisabled() {
    return (JCheckBoxFixture)super.requireDisabled();
  }

  /** {@inheritDoc} */
  @Override public final JCheckBoxFixture requireEnabled() {
    return (JCheckBoxFixture)super.requireEnabled();
  }

  /** {@inheritDoc} */
  @Override public final JCheckBoxFixture requireNotVisible() {
    return (JCheckBoxFixture)super.requireNotVisible();
  }

  /** {@inheritDoc} */
  @Override public final JCheckBoxFixture requireVisible() {
    return (JCheckBoxFixture)super.requireVisible();
  }
}
