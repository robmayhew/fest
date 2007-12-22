/*
 * Created on Apr 3, 2007
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

import javax.swing.JTabbedPane;

import abbot.tester.JTabbedPaneLocation;
import abbot.tester.JTabbedPaneTester;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link JTabbedPane}</code> and verification of the state of such
 * <code>{@link JTabbedPane}</code>.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
public class JTabbedPaneFixture extends ComponentFixture<JTabbedPane> {

  /**
   * Creates a new <code>{@link JTabbedPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTabbedPane</code>.
   * @param tabbedPaneName the name of the <code>JTabbedPane</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTabbedPane</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JTabbedPane</code> is found.
   */
  public JTabbedPaneFixture(RobotFixture robot, String tabbedPaneName) {
    super(robot, tabbedPaneName, JTabbedPane.class);
  }
  
  /**
   * Creates a new <code>{@link JTabbedPaneFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTabbedPane</code>.
   * @param target the <code>JTabbedPane</code> to be managed by this fixture.
   */
  public JTabbedPaneFixture(RobotFixture robot, JTabbedPane target) {
    super(robot, target);
  }

  /**
   * Simulates a user selecting the tab located at the given index.
   * @param tabIndex the index of the tab to select.
   * @return this fixture.
   */
  public final JTabbedPaneFixture selectTab(int tabIndex) {
    selectTab(new JTabbedPaneLocation(tabIndex));
    return this;
  }
  
  /**
   * Simulates a user selecting the tab containing the given text.
   * @param tabText the given text to match.
   * @return this fixture.
   */
  public final JTabbedPaneFixture selectTab(String tabText) {
    selectTab(new JTabbedPaneLocation(tabText));
    return this;
  }

  private final JTabbedPaneFixture selectTab(JTabbedPaneLocation tabLocation) {
    tabbedPaneTester().actionSelectTab(target, tabLocation);
    return this;
  }
  
  /** 
   * Returns the titles of all the tabs in this fixture's <code>{@link JTabbedPane}</code>.
   * @return the titles of all the tabs. 
   */
  public final String[] tabTitles() {
    return tabbedPaneTester().getTabs(target);
  }
  
  private JTabbedPaneTester tabbedPaneTester() {
    return (JTabbedPaneTester)tester();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTabbedPane}</code>.
   * @return this fixture.
   */
  public final JTabbedPaneFixture click() {
    return (JTabbedPaneFixture)doClick(); 
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTabbedPane}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JTabbedPaneFixture click(MouseButton button) {
    return (JTabbedPaneFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTabbedPane}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JTabbedPaneFixture click(MouseClickInfo mouseClickInfo) {
    return (JTabbedPaneFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JTabbedPane}</code>.
   * @return this fixture.
   */
  public final JTabbedPaneFixture rightClick() {
    return (JTabbedPaneFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JTabbedPane}</code>.
   * @return this fixture.
   */
  public final JTabbedPaneFixture doubleClick() {
    return (JTabbedPaneFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JTabbedPane}</code>.
   * @return this fixture.
   */
  public final JTabbedPaneFixture focus() {
    return (JTabbedPaneFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JTabbedPane}</code>. This 
   * method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTabbedPaneFixture pressAndReleaseKeys(int... keyCodes) {
    return (JTabbedPaneFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JTabbedPane}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTabbedPaneFixture pressKey(int keyCode) {
    return (JTabbedPaneFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JTabbedPane}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTabbedPaneFixture releaseKey(int keyCode) {
    return (JTabbedPaneFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTabbedPane}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTabbedPane</code> is not visible.
   */
  public final JTabbedPaneFixture requireVisible() {
    return (JTabbedPaneFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JTabbedPane}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTabbedPane</code> is visible.
   */
  public final JTabbedPaneFixture requireNotVisible() {
    return (JTabbedPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JTabbedPane}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTabbedPane</code> is disabled.
   */
  public final JTabbedPaneFixture requireEnabled() {
    return (JTabbedPaneFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTabbedPane}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JTabbedPane</code> is never enabled.
   */
  public final JTabbedPaneFixture requireEnabled(Timeout timeout) {
    return (JTabbedPaneFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTabbedPane}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTabbedPane</code> is enabled.
   */
  public final JTabbedPaneFixture requireDisabled() {
    return (JTabbedPaneFixture)assertDisabled();
  }
}
