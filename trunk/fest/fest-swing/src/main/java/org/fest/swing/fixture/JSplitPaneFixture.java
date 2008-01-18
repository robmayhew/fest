/*
 * Created on Sep 4, 2007
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

import javax.swing.JSplitPane;

import abbot.tester.JSplitPaneTester;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JSplitPane}</code> and verification of the state of such
 * <code>{@link JSplitPane}</code>.
 *
 * @author Yvonne Wang 
 */
public class JSplitPaneFixture extends ComponentFixture<JSplitPane> {

  /**
   * Creates a new <code>{@link JSplitPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JSplitPane</code>.
   * @param spinnerName the name of the <code>JSplitPane</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JSplitPane</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JSplitPane</code> is found.
   */
  public JSplitPaneFixture(RobotFixture robot, String spinnerName) {
    super(robot, spinnerName, JSplitPane.class);
  }

  /**
   * Creates a new <code>{@link JSplitPaneFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JSplitPane</code>.
   * @param target the <code>JSplitPane</code> to be managed by this fixture.
   */
  public JSplitPaneFixture(RobotFixture robot, JSplitPane target) {
    super(robot, target);
  }

  /**
   * Simulates a user moving the divider of this fixture's <code>{@link JSplitPane}</code>.
   * @param location the location to move the divider to.
   * @return this fixture.
   */
  public final JSplitPaneFixture moveDividerTo(int location) {
    splitPaneTester().actionMoveDividerAbsolute(target, location);
    return this;
  }
  
  protected final JSplitPaneTester splitPaneTester() {
    return (JSplitPaneTester)tester();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSplitPane}</code>.
   * @return this fixture.
   */
  public final JSplitPaneFixture click() {
    return (JSplitPaneFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSplitPane}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JSplitPaneFixture click(MouseButton button) {
    return (JSplitPaneFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JSplitPane}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JSplitPaneFixture click(MouseClickInfo mouseClickInfo) {
    return (JSplitPaneFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JSplitPane}</code>.
   * @return this fixture.
   */
  public final JSplitPaneFixture rightClick() {
    return (JSplitPaneFixture)doRightClick();
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JSplitPane}</code>.
   * @return this fixture.
   */
  public final JSplitPaneFixture doubleClick() {
    return (JSplitPaneFixture)doDoubleClick();
  }
  
  /**
   * Gives input focus to this fixture's <code>{@link JSplitPane}</code>.
   * @return this fixture.
   */
  public final JSplitPaneFixture focus() {
    return (JSplitPaneFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JSplitPane}</code>. This 
   * method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSplitPaneFixture pressAndReleaseKeys(int... keyCodes) {
    return (JSplitPaneFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JSplitPane}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSplitPaneFixture pressKey(int keyCode) {
    return (JSplitPaneFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JSplitPane}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSplitPaneFixture releaseKey(int keyCode) {
    return (JSplitPaneFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JSplitPane}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSplitPane</code> is not visible.
   */
  public final JSplitPaneFixture requireVisible() {
    return (JSplitPaneFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JSplitPane}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSplitPane</code> is visible.
   */
  public final JSplitPaneFixture requireNotVisible() {
    return (JSplitPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JSplitPane}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSplitPane</code> is disabled.
   */
  public final JSplitPaneFixture requireEnabled() {
    return (JSplitPaneFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JSplitPane}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JSplitPane</code> is never enabled.
   */
  public final JSplitPaneFixture requireEnabled(Timeout timeout) {
    return (JSplitPaneFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JSplitPane}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JSplitPane</code> is enabled.
   */
  public final JSplitPaneFixture requireDisabled() {
    return (JSplitPaneFixture)assertDisabled();
  }
}
