/*
 * Created on Dec 25, 2007
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

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JScrollPane}</code> and verification of the state of such
 * <code>{@link JScrollPane}</code>.
 *
 * @author Yvonne Wang
 */
public class JScrollPaneFixture extends ComponentFixture<JScrollPane> {

  /**
   * Creates a new <code>{@link JScrollPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JScrollPane</code>.
   * @param panelName the name of the <code>JScrollPane</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JScrollPane</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JScrollPane</code> is found.
   */
  public JScrollPaneFixture(RobotFixture robot, String panelName) {
    super(robot, panelName, JScrollPane.class);
  }

  /**
   * Creates a new <code>{@link JScrollPaneFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JScrollPane</code>.
   * @param target the <code>JScrollPane</code> to be managed by this fixture.
   */
  public JScrollPaneFixture(RobotFixture robot, JScrollPane target) {
    super(robot, target);
  }

  /**
   * Returns a <code>{@link JScrollBarFixture}</code> managing the vertical <code>{@link JScrollBar}</code> of this
   * target's <code>{@link JScrollPane}</code>.
   * @return a fixture managing the vertical <code>JScrollBar</code> of this target's <code>JScrollPane</code>.
   */
  public final JScrollBarFixture verticalScrollBar() {
    return scrollBarFixture(target.getVerticalScrollBar());
  }

  /**
   * Returns a <code>{@link JScrollBarFixture}</code> managing the horizontal <code>{@link JScrollBar}</code> of this
   * target's <code>{@link JScrollPane}</code>.
   * @return a fixture managing the horizontal <code>JScrollBar</code> of this target's <code>JScrollPane</code>.
   */
  public final JScrollBarFixture horizontalScrollBar() {
    return scrollBarFixture(target.getHorizontalScrollBar());
  }

  private JScrollBarFixture scrollBarFixture(JScrollBar scrollBar) {
    return new JScrollBarFixture(robot, scrollBar);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public final JScrollPaneFixture click() {
    return (JScrollPaneFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JScrollPaneFixture click(MouseButton button) {
    return (JScrollPaneFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JScrollPaneFixture click(MouseClickInfo mouseClickInfo) {
    return (JScrollPaneFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public final JScrollPaneFixture rightClick() {
    return (JScrollPaneFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public final JScrollPaneFixture doubleClick() {
    return (JScrollPaneFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public final JScrollPaneFixture focus() {
    return (JScrollPaneFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JScrollPane}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JScrollPaneFixture pressAndReleaseKeys(int... keyCodes) {
    return (JScrollPaneFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JScrollPane}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JScrollPaneFixture pressKey(int keyCode) {
    return (JScrollPaneFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JScrollPane}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JScrollPaneFixture releaseKey(int keyCode) {
    return (JScrollPaneFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is not visible.
   */
  public final JScrollPaneFixture requireVisible() {
    return (JScrollPaneFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is visible.
   */
  public final JScrollPaneFixture requireNotVisible() {
    return (JScrollPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is disabled.
   */
  public final JScrollPaneFixture requireEnabled() {
    return (JScrollPaneFixture)assertEnabled();
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JScrollPane</code> is never enabled.
   */
  public final JScrollPaneFixture requireEnabled(Timeout timeout) {
    return (JScrollPaneFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is enabled.
   */
  public final JScrollPaneFixture requireDisabled() {
    return (JScrollPaneFixture)assertDisabled();
  }
}
