/*
 * Created on Dec 25, 2007
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

import javax.swing.JScrollBar;

import abbot.tester.JScrollBarTester;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JScrollBar}</code> and verification of the state of such 
 * <code>{@link JScrollBar}</code>.
 *
 * @author Alex Ruiz
 */
public class JScrollBarFixture extends ComponentFixture<JScrollBar> {

  /**
   * Creates a new <code>{@link JScrollBarFixture}</code>.
   * @param robot performs simulation of user events on a <code>JScrollBar</code>.
   * @param scrollBarName the name of the <code>JScrollBar</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JScrollBar</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JScrollBar</code> is found.
   */
  public JScrollBarFixture(RobotFixture robot, String scrollBarName) {
    super(robot, scrollBarName, JScrollBar.class);
  }

  /**
   * Creates a new <code>{@link JScrollBarFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JScrollBar</code>.
   * @param target the <code>JScrollBar</code> to be managed by this fixture.
   */
  public JScrollBarFixture(RobotFixture robot, JScrollBar target) {
    super(robot, target);
  }

  /**
   * Simulates a user scrolling up one unit (usually a line,) the given number of times.
   * @param times the number of times to scroll up one unit.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   */
  public final JScrollBarFixture scrollUnitUp(int times) {
    if (times <= 0) 
      throw new IllegalArgumentException("The number of times to scroll up one unit should be greater than zero");
    for (int i = 0; i < times; i++) scrollUnitUp();
    return this;
  }
  
  /**
   * Simulates a user scrolling up one unit (usually a line.)
   * @return this fixture.
   */
  public final JScrollBarFixture scrollUnitUp() {
    scrollBarTester().actionScrollUnitUp(target);
    return this;
  }

  /**
   * Simulates a user scrolling down one unit (usually a line,) the given number of times.
   * @param times the number of times to scroll down one unit.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   */
  public final JScrollBarFixture scrollUnitDown(int times) {
    if (times <= 0) 
      throw new IllegalArgumentException("The number of times to scroll down one unit should be greater than zero");
    for (int i = 0; i < times; i++) scrollUnitDown();
    return this;
  }
  
  /**
   * Simulates a user scrolling down one unit (usually a line.)
   * @return this fixture.
   */
  public final JScrollBarFixture scrollUnitDown() {
    scrollBarTester().actionScrollUnitDown(target);
    return this;
  }
  
  /**
   * Simulates a user scrolling up one block (usually a page,) the given number of times.
   * @param times the number of times to scroll up one block.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   */
  public final JScrollBarFixture scrollBlockUp(int times) {
    if (times <= 0) 
      throw new IllegalArgumentException("The number of times to scroll up one block should be greater than zero");
    for (int i = 0; i < times; i++) scrollBlockUp();
    return this;
  }
  
  /**
   * Simulates a user scrolling up one block (usually a page.)
   * @return this fixture.
   */
  public final JScrollBarFixture scrollBlockUp() {
    scrollBarTester().actionScrollBlockUp(target);
    return this;
  }

  /**
   * Simulates a user scrolling down one block (usually a page,) the given number of times.
   * @param times the number of times to scroll down one block.
   * @return this fixture.
   * @throws IllegalArgumentException if <code>times</code> is less than or equal to zero.
   */
  public final JScrollBarFixture scrollBlockDown(int times) {
    if (times <= 0) 
      throw new IllegalArgumentException("The number of times to scroll down one block should be greater than zero");
    for (int i = 0; i < times; i++) scrollBlockDown();
    return this;
  }
  
  /**
   * Simulates a user scrolling down one block (usually a page.)
   * @return this fixture.
   */
  public final JScrollBarFixture scrollBlockDown() {
    scrollBarTester().actionScrollBlockDown(target);
    return this;
  }
  
  protected final JScrollBarTester scrollBarTester() {
    return (JScrollBarTester)tester();
  }
  
  /**
   * Asserts that the value of this fixture's <code>{@link JScrollBar}</code> is equal to the given one. 
   * @param value the expected value.
   * @return this fixture.
   * @throws AssertionError if the value of this fixture's <code>JScrollBar</code> is not equal to the given one.
   */
  public final JScrollBarFixture requireValue(int value) {
    assertThat(target.getValue()).isEqualTo(value);
    return this;
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollBar}</code>.
   * @return this fixture.
   */
  public final JScrollBarFixture click() {
    return (JScrollBarFixture) doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollBar}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JScrollBarFixture click(MouseButton button) {
    return (JScrollBarFixture) doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JScrollBar}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JScrollBarFixture click(MouseClickInfo mouseClickInfo) {
    return (JScrollBarFixture) doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JScrollBar}</code>.
   * @return this fixture.
   */
  public final JScrollBarFixture rightClick() {
    return (JScrollBarFixture) doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JScrollBar}</code>.
   * @return this fixture.
   */
  public final JScrollBarFixture doubleClick() {
    return (JScrollBarFixture) doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JScrollBar}</code>.
   * @return this fixture.
   */
  public final JScrollBarFixture focus() {
    return (JScrollBarFixture) doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JScrollBar}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JScrollBarFixture pressAndReleaseKeys(int... keyCodes) {
    return (JScrollBarFixture) doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JScrollBar}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JScrollBarFixture pressKey(int keyCode) {
    return (JScrollBarFixture) doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JScrollBar}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JScrollBarFixture releaseKey(int keyCode) {
    return (JScrollBarFixture) doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is not visible.
   */
  public final JScrollBarFixture requireVisible() {
    return (JScrollBarFixture) assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is visible.
   */
  public final JScrollBarFixture requireNotVisible() {
    return (JScrollBarFixture) assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is disabled.
   */
  public final JScrollBarFixture requireEnabled() {
    return (JScrollBarFixture) assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JScrollBar</code> is never enabled.
   */
  public final JScrollBarFixture requireEnabled(Timeout timeout) {
    return (JScrollBarFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JScrollBar}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JScrollBar</code> is enabled.
   */
  public final JScrollBarFixture requireDisabled() {
    return (JScrollBarFixture) assertDisabled();
  }
}
