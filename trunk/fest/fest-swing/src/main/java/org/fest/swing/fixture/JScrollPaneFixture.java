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

import java.awt.Component;
import java.awt.Point;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.driver.JOptionPaneDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JScrollPane}</code> and verification of the state of such
 * <code>{@link JScrollPane}</code>.
 *
 * @author Yvonne Wang
 */
public class JScrollPaneFixture extends ComponentFixture<JScrollPane> implements JPopupMenuInvokerFixture {

  private JComponentDriver driver;
  
  /**
   * Creates a new <code>{@link JScrollPaneFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JScrollPane</code>.
   * @param target the <code>JScrollPane</code> to be managed by this fixture.
   */
  public JScrollPaneFixture(Robot robot, JScrollPane target) {
    super(robot, target);
    updateDriver(newComponentDriver());
  }

  final void updateDriver(JComponentDriver driver) {
    this.driver = driver;
  }

  /**
   * Creates a new <code>{@link JScrollPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JScrollPane</code>.
   * @param panelName the name of the <code>JScrollPane</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JScrollPane</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JScrollPane</code> is found.
   */
  public JScrollPaneFixture(Robot robot, String panelName) {
    super(robot, panelName, JScrollPane.class);
    driver = newComponentDriver();
  }

  private JComponentDriver newComponentDriver() {
    return new JOptionPaneDriver(robot);
  }
  
  /**
   * Returns a <code>{@link JScrollBarFixture}</code> managing the horizontal <code>{@link JScrollBar}</code> of this
   * target's <code>{@link JScrollPane}</code>.
   * @return a fixture managing the horizontal <code>JScrollBar</code> of this target's <code>JScrollPane</code>.
   */
  public JScrollBarFixture horizontalScrollBar() {
    return scrollBarFixture(target.getHorizontalScrollBar());
  }

  /**
   * Returns a <code>{@link JScrollBarFixture}</code> managing the vertical <code>{@link JScrollBar}</code> of this
   * target's <code>{@link JScrollPane}</code>.
   * @return a fixture managing the vertical <code>JScrollBar</code> of this target's <code>JScrollPane</code>.
   */
  public JScrollBarFixture verticalScrollBar() {
    return scrollBarFixture(target.getVerticalScrollBar());
  }

  private JScrollBarFixture scrollBarFixture(JScrollBar scrollBar) {
    return new JScrollBarFixture(robot, scrollBar);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public JScrollPaneFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JScrollPaneFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollPane}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JScrollPaneFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public JScrollPaneFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public JScrollPaneFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JScrollPane}</code>.
   * @return this fixture.
   */
  public JScrollPaneFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JScrollPane}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JScrollPaneFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JScrollPane}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JScrollPaneFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JScrollPane}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JScrollPaneFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is enabled.
   */
  public JScrollPaneFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is disabled.
   */
  public JScrollPaneFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JScrollPane</code> is never enabled.
   */
  public JScrollPaneFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is visible.
   */
  public JScrollPaneFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollPane}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollPane</code> is not visible.
   */
  public JScrollPaneFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Shows a pop-up menu using this fixture's <code>{@link Component}</code> as the invoker of the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenu() {
    return new JPopupMenuFixture(robot, driver.showPopupMenu(target));
  }

  /**
   * Shows a pop-up menu at the given point using this fixture's <code>{@link Component}</code> as the invoker of the
   * pop-up menu.
   * @param p the given point where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(Point p) {
    return new JPopupMenuFixture(robot, driver.showPopupMenu(target, p));
  }
}
