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

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JSplitPane}</code> and verification of the state of such
 * <code>{@link JSplitPane}</code>.
 *
 * @author Yvonne Wang 
 */
public class JSplitPaneFixture extends ComponentFixture<JSplitPane> {

  /**
   * Creates a new </code>{@link JSplitPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JSplitPane</code>.
   * @param spinnerName the name of the <code>JSplitPane</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JSplitPane</code> could not be found.
   */
  public JSplitPaneFixture(RobotFixture robot, String spinnerName) {
    super(robot, spinnerName, JSplitPane.class);
  }

  /**
   * Creates a new </code>{@link JSplitPaneFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JSplitPane</code>.
   * @param target the <code>JSplitPane</code> to be managed by this fixture.
   */
  public JSplitPaneFixture(RobotFixture robot, JSplitPane target) {
    super(robot, target);
  }

  /**
   * Simulates a user moving the divider of the <code>{@link JSplitPane}</code> managed by this fixture.
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
   * Simulates a user clicking the <code>{@link JSplitPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSplitPaneFixture click() {
    return (JSplitPaneFixture)doClick();
  }

  /**
   * Simulates a user right-clicking the <code>{@link JSplitPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSplitPaneFixture rightClick() {
    return (JSplitPaneFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JSplitPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSplitPaneFixture doubleClick() {
    return (JSplitPaneFixture)doDoubleClick();
  }
  
  /**
   * Gives input focus to the <code>{@link JSplitPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSplitPaneFixture focus() {
    return (JSplitPaneFixture)doFocus();
  }

  /**
   * Simulates a user pressing the given keys on the <code>{@link JSplitPane}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSplitPaneFixture pressKeys(int... keyCodes) {
    return (JSplitPaneFixture)doPressKeys(keyCodes);
  }
  
  /**
   * Asserts that the <code>{@link JSplitPane}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSplitPane</code> is not visible.
   */
  public final JSplitPaneFixture requireVisible() {
    return (JSplitPaneFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JSplitPane}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSplitPane</code> is visible.
   */
  public final JSplitPaneFixture requireNotVisible() {
    return (JSplitPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JSplitPane}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSplitPane</code> is disabled.
   */
  public final JSplitPaneFixture requireEnabled() {
    return (JSplitPaneFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JSplitPane}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSplitPane</code> is enabled.
   */
  public final JSplitPaneFixture requireDisabled() {
    return (JSplitPaneFixture)assertDisabled();
  }
}
