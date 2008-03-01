/*
 * Created on Dec 16, 2006
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

import java.awt.Component;
import java.awt.Point;

import javax.swing.JButton;

import org.fest.swing.core.Robot;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JButton}</code> and verification of the state of such 
 * <code>{@link JButton}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JButtonFixture extends ComponentFixture<JButton> implements JPopupMenuInvokerFixture, TextDisplayFixture {

  private AbstractButtonDriver driver;
  
  /**
   * Creates a new <code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JButton</code>.
   * @param target the <code>JButton</code> to be managed by this fixture.
   */
  public JButtonFixture(Robot robot, JButton target) {
    super(robot, target);
    createDriver();
  }
  
  /**
   * Creates a new <code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JButton</code>.
   * @param buttonName the name of the <code>JButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JButton</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JButton</code> is found.
   */
  public JButtonFixture(Robot robot, String buttonName) {
    super(robot, buttonName, JButton.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new AbstractButtonDriver(robot));
  }
  
  final void updateDriver(AbstractButtonDriver driver) {
    this.driver = driver;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JButton}</code>.
   * @return this fixture.
   */
  public JButtonFixture click() {
    driver.click(target);
    return this;
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JButton}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JButtonFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JButton}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JButtonFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JButton}</code>.
   * @return this fixture.
   */
  public JButtonFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JButton}</code>.
   * @return this fixture.
   */
  public JButtonFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JButton}</code>.
   * @return this fixture.
   */
  public JButtonFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JButton}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JButtonFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JButton}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JButtonFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JButton}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JButtonFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JButton}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JButton</code> is enabled.
   */
  public JButtonFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JButton}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JButton</code> is disabled.
   */
  public JButtonFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JButton}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JButton</code> is never enabled.
   */
  public JButtonFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JButton}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JButton</code> is visible.
   */
  public JButtonFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JButton}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JButton</code> is not visible.
   */
  public JButtonFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }
  
  /**
   * Asserts that the text of this fixture's <code>{@link JButton}</code> is equal to the specified <code>String</code>.
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JButton is not equal to the given one.
   */
  public JButtonFixture requireText(String expected) {
    driver.requireText(target, expected);
    return this;
  }

  /**
   * Returns the text of this fixture's <code>{@link JButton}</code>. 
   * @return the text of this fixture's <code>JButton</code>. 
   */
  public String text() {
    return target.getText();
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

