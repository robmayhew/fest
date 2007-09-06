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

import javax.swing.JPopupMenu;

import abbot.tester.JPopupMenuTester;

import org.fest.swing.RobotFixture;

/**
 * Understands lookup of <code>{@link JPopupMenu}</code>.
 *
 * @author Yvonne Wang
 */
public class JPopupMenuFixture extends JMenuItemContainerFixture<JPopupMenu> {
  
  /**
   * Creates a new </code>{@link JPopupMenuFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JPopupMenu</code>.
   * @param target the <code>JPopupMenu</code> to be managed by this fixture.
   */
  public JPopupMenuFixture(RobotFixture robot, JPopupMenu target) {
    super(robot, target);
  }

  /**
   * Returns the contents of the <code>{@link JPopupMenu}</code> managed by this fixture.
   * @return a <code>String</code> array representing the contents of the <code>JPopupMenu</code> managed by this 
   *         fixture. 
   */
  public final String[] menuLabels() {
    return popupMenuTester().getMenuLabels(target);
  }
  
  protected final JPopupMenuTester popupMenuTester() {
    return (JPopupMenuTester)tester();
  }
  
  /**
   * Simulates a user clicking the <code>{@link JPopupMenu}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JPopupMenuFixture click() {
    return (JPopupMenuFixture)super.click();
  }

  /**
   * Gives input focus to the <code>{@link JPopupMenu}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JPopupMenuFixture focus() {
    return (JPopupMenuFixture)super.focus();
  }
  
  /**
   * Asserts that the <code>{@link JPopupMenu}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JPopupMenu</code> is disabled.
   */
  @Override public final JPopupMenuFixture requireEnabled() {
    return (JPopupMenuFixture)super.requireEnabled();
  }

  /**
   * Asserts that the <code>{@link JPopupMenu}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JPopupMenu</code> is enabled.
   */
  @Override public final JPopupMenuFixture requireDisabled() {
    return (JPopupMenuFixture)super.requireDisabled();
  }

  /**
   * Asserts that the <code>{@link JPopupMenu}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JPopupMenu</code> is not visible.
   */
  @Override public final JPopupMenuFixture requireVisible() {
    return (JPopupMenuFixture)super.requireVisible();
  }
  
  /**
   * Asserts that the <code>{@link JPopupMenu}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JPopupMenu</code> is visible.
   */
  @Override public final JPopupMenuFixture requireNotVisible() {
    return (JPopupMenuFixture)super.requireNotVisible();
  }
}
