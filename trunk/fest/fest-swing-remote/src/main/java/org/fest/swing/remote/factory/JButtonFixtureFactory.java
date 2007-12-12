/*
 * Created on Dec 11, 2007
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
package org.fest.swing.remote.factory;

import javax.swing.JButton;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.fixture.JButtonFixture;

/**
 * Understands a factory of <code>{@link JButtonFixture}</code>.
 *
 * @author Alex Ruiz
 */
final class JButtonFixtureFactory extends ComponentFixtureFactoryTemplate<JButton> {

  /**
   * Creates a new <code>{@link JButtonFixture}</code> for the given <code>{@link JButton}</code>.
   * @param robot the robot to use to simulate user input on the given button.
   * @param button the given button.
   * @return the created fixture.
   */
  @Override JButtonFixture fixture(RobotFixture robot, JButton button) {
    return new JButtonFixture(robot, button);
  }

  /**
   * Indicates that this factory supports fixtures for <code>{@link JButton}</code>s.
   * @return <code>JButton.class</code>
   */
  @Override Class<JButton> supported() {
    return JButton.class;
  }

  /** @see java.lang.Object#toString() */
  @Override public String toString() {
    return getClass().getName();
  }
}
