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

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JTabbedPane}</code> and verification of the state of such
 * <code>{@link JTabbedPane}</code>.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
public class JTabbedPaneFixture extends ComponentFixture<JTabbedPane> {

  /**
   * Creates a new </code>{@link JTabbedPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTabbedPane</code>.
   * @param tabbedPaneName the name of the <code>JTabbedPane</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTabbedPane</code> could not be found.
   */
  public JTabbedPaneFixture(RobotFixture robot, String tabbedPaneName) {
    super(robot, tabbedPaneName, JTabbedPane.class);
  }
  
  /**
   * Creates a new </code>{@link JTabbedPaneFixture}</code>.
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
   * Returns the titles of all the tabs in the <code>{@link JTabbedPane}</code> managed by this fixture.
   * @return the titles of all the tabs. 
   */
  public final String[] tabTitles() {
    return tabbedPaneTester().getTabs(target);
  }
  
  private JTabbedPaneTester tabbedPaneTester() {
    return (JTabbedPaneTester)tester();
  }

  /**
   * Simulates a user clicking the <code>{@link JTabbedPane}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JTabbedPaneFixture click() {
    return (JTabbedPaneFixture)doClick(); 
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JTabbedPane}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JTabbedPaneFixture doubleClick() {
    return (JTabbedPaneFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JTabbedPane}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JTabbedPaneFixture focus() {
    return (JTabbedPaneFixture)doFocus();
  }

  /**
   * Simulates a user pressing the given keys on the <code>{@link JTabbedPane}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  @Override public final JTabbedPaneFixture pressKeys(int... keyCodes) {
    return (JTabbedPaneFixture)doPressKeys(keyCodes);
  }
  
  /**
   * Asserts that the <code>{@link JTabbedPane}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTabbedPane</code> is not visible.
   */
  @Override public final JTabbedPaneFixture requireVisible() {
    return (JTabbedPaneFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JTabbedPane}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTabbedPane</code> is visible.
   */
  @Override public final JTabbedPaneFixture requireNotVisible() {
    return (JTabbedPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JTabbedPane}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTabbedPane</code> is disabled.
   */
  @Override public final JTabbedPaneFixture requireEnabled() {
    return (JTabbedPaneFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JTabbedPane}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTabbedPane</code> is enabled.
   */
  @Override public final JTabbedPaneFixture requireDisabled() {
    return (JTabbedPaneFixture)assertDisabled();
  }
}
