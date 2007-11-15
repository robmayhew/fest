/*
 * Created on Nov 1, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JPanel;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.MouseButton;
import org.fest.swing.RobotFixture;
import org.fest.swing.Timeout;
import org.fest.swing.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JPanel}</code> and verification of the state of such 
 * <code>{@link JPanel}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JPanelFixture extends ContainerFixture<JPanel> {

  /**
   * Creates a new </code>{@link JPanelFixture}</code>.
   * @param robot performs simulation of user events on a <code>JPanel</code>.
   * @param panelName the name of the <code>JPanel</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JPanel</code> could not be found.
   */
  public JPanelFixture(RobotFixture robot, String panelName) {
    super(robot, panelName, JPanel.class);
  }

  /**
   * Creates a new </code>{@link JPanelFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JPanel</code>.
   * @param target the <code>JPanel</code> to be managed by this fixture.
   */
  public JPanelFixture(RobotFixture robot, JPanel target) {
    super(robot, target);
  }

  /**
   * Simulates a user clicking the <code>{@link JPanel}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JPanelFixture click() {
    return (JPanelFixture) doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JPanel}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JPanelFixture click(MouseButton button) {
    return (JPanelFixture) doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JPanel}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JPanelFixture click(MouseClickInfo mouseClickInfo) {
    return (JPanelFixture) doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JPanel}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JPanelFixture rightClick() {
    return (JPanelFixture) doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JPanel}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JPanelFixture doubleClick() {
    return (JPanelFixture) doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JPanel}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JPanelFixture focus() {
    return (JPanelFixture) doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JPanel}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JPanelFixture pressAndReleaseKeys(int... keyCodes) {
    return (JPanelFixture) doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on the <code>{@link JPanel}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JPanelFixture pressKey(int keyCode) {
    return (JPanelFixture) doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on the <code>{@link JPanel}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JPanelFixture releaseKey(int keyCode) {
    return (JPanelFixture) doReleaseKey(keyCode);
  }

  /**
   * Asserts that the <code>{@link JPanel}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JPanel</code> is not visible.
   */
  public final JPanelFixture requireVisible() {
    return (JPanelFixture) assertVisible();
  }

  /**
   * Asserts that the <code>{@link JPanel}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JPanel</code> is visible.
   */
  public final JPanelFixture requireNotVisible() {
    return (JPanelFixture) assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JPanel}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JPanel</code> is disabled.
   */
  public final JPanelFixture requireEnabled() {
    return (JPanelFixture) assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JPanel}</code> managed by this fixture is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if the managed <code>JPanel</code> is never enabled.
   */
  public final JPanelFixture requireEnabled(Timeout timeout) {
    return (JPanelFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that the <code>{@link JPanel}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JPanel</code> is enabled.
   */
  public final JPanelFixture requireDisabled() {
    return (JPanelFixture) assertDisabled();
  }
}
