/*
 * Created on Sep 5, 2007
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

import java.awt.Component;

import javax.swing.JPopupMenu;

import org.fest.swing.RobotFixture;

/**
 * Understands lookup of <code>{@link JPopupMenu}</code>.
 *
 * @author Yvonne Wang
 */
public class JPopupMenuFixture extends ComponentFixture<JPopupMenu> {
  
  public JPopupMenuFixture(RobotFixture robot, JPopupMenu target) {
    super(robot, target);
  }

  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JPopupMenuFixture click() {
    return (JPopupMenuFixture)super.click();
  }

  /**
   * Gives input focus to the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JPopupMenuFixture focus() {
    return (JPopupMenuFixture)super.focus();
  }
  
  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is disabled.
   */
  @Override public final JPopupMenuFixture requireEnabled() {
    return (JPopupMenuFixture)super.requireEnabled();
  }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is enabled.
   */
  @Override public final JPopupMenuFixture requireDisabled() {
    return (JPopupMenuFixture)super.requireDisabled();
  }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is not visible.
   */
  @Override public final JPopupMenuFixture requireVisible() {
    return (JPopupMenuFixture)super.requireVisible();
  }
  
  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is visible.
   */
  @Override public final JPopupMenuFixture requireNotVisible() {
    return (JPopupMenuFixture)super.requireNotVisible();
  }
}
