/*
 * Created on Oct 20, 2006
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

import javax.swing.Action;
import javax.swing.JMenuItem;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JMenuItem}</code> and verification of the state of such
 * <code>{@link JMenuItem}</code>.
 *
 * @author Alex Ruiz
 */
public class JMenuItemFixture extends ComponentFixture<JMenuItem> {

  /**
   * Creates a new </code>{@link JMenuItemFixture}</code>.
   * @param robot performs simulation of user events on a <code>JMenuItem</code>.
   * @param menuItemName the name of the <code>JMenuItem</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JMenuItem</code> could not be found.
   */
  public JMenuItemFixture(RobotFixture robot, String menuItemName) {
    super(robot, menuItemName, JMenuItem.class);
  }
  
  /**
   * Creates a new </code>{@link JMenuItemFixture}</code>. It uses the given <code>{@link Action}</code> to create a new 
   * <code>{@link JMenuItem}</code> as the target menu item.
   * @param robot performs simulation of user events on a <code>JMenuItem</code>.
   * @param action the <code>Action</code> to assign to the created <code>JMenuItem</code>.
   */
  public JMenuItemFixture(RobotFixture robot, Action action) {
    this(robot, new JMenuItem(action));
  }
  
  /**
   * Creates a new </code>{@link JMenuItemFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JMenuItem</code>.
   * @param target the <code>JMenuItem</code> to be managed by this fixture.
   */
  public JMenuItemFixture(RobotFixture robot, JMenuItem target) {
    super(robot, target);
  }
  
  /**
   * Simulates a user selecting the <code>{@link JMenuItem}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JMenuItemFixture select() {
    robot.selectMenuItem(target);
    return this;
  }

  /**
   * Simulates a user clicking the <code>{@link JMenuItem}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JMenuItemFixture click() {
    return (JMenuItemFixture)doClick(); 
  }

  /**
   * Simulates a user clicking the <code>{@link JMenuItem}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JMenuItemFixture click(MouseButton button) {
    return (JMenuItemFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JMenuItem}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JMenuItemFixture click(MouseClickInfo mouseClickInfo) {
    return (JMenuItemFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JMenuItem}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JMenuItemFixture rightClick() {
    return (JMenuItemFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JMenuItem}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JMenuItemFixture doubleClick() {
    return (JMenuItemFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JMenuItem}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JMenuItemFixture focus() {
    return (JMenuItemFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JMenuItem}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JMenuItemFixture pressAndReleaseKeys(int... keyCodes) {
    return (JMenuItemFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on the <code>{@link JMenuItem}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JMenuItemFixture pressKey(int keyCode) {
    return (JMenuItemFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JMenuItem}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JMenuItemFixture releaseKey(int keyCode) {
    return (JMenuItemFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JMenuItem}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JMenuItem</code> is not visible.
   */
  public final JMenuItemFixture requireVisible() {
    return (JMenuItemFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JMenuItem}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JMenuItem</code> is visible.
   */
  public final JMenuItemFixture requireNotVisible() {
    return (JMenuItemFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JMenuItem}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JMenuItem</code> is disabled.
   */
  public final JMenuItemFixture requireEnabled() {
    return (JMenuItemFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JMenuItem}</code> managed by this fixture is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if the managed <code>JMenuItem</code> is never enabled.
   */
  public final JMenuItemFixture requireEnabled(Timeout timeout) {
    return (JMenuItemFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that the <code>{@link JMenuItem}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JMenuItem</code> is enabled.
   */
  public final JMenuItemFixture requireDisabled() {
    return (JMenuItemFixture)assertDisabled();
  }
}
