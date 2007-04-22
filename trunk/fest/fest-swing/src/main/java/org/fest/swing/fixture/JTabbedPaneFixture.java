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

import org.fest.swing.RobotFixture;


import abbot.tester.JTabbedPaneLocation;
import abbot.tester.JTabbedPaneTester;


/**
 * Understands simulation of user events on a <code>{@link JTabbedPane}</code> and output verification.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
public final class JTabbedPaneFixture extends AbstractComponentFixture<JTabbedPane> {

  /**
   * Creates a new </code>{@link JTabbedPaneFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTabbedPane</code>.
   * @param tabbedPaneName the name of the tabbed pane to find using the given <code>AbbotFixture</code>.
   * @see RobotFixture#findByName(String, Class)
   */
  public JTabbedPaneFixture(RobotFixture robot, String tabbedPaneName) {
    super(robot, tabbedPaneName, JTabbedPane.class);
  }
  
  /**
   * Creates a new </code>{@link JTabbedPaneFixture}</code>.
   * @param robot performs simulation of user events on the given tabbed pane.
   * @param target the target tabbed pane.
   */
  public JTabbedPaneFixture(RobotFixture robot, JTabbedPane target) {
    super(robot, target);
  }

  /**
   * Simulates a user selecting the tab located at the given index.
   * @param tabIndex the index of the tab to select.
   * @return this fixture.
   */
  public JTabbedPaneFixture selectTab(int tabIndex) {
    selectTab(new JTabbedPaneLocation(tabIndex));
    return this;
  }
  
  /**
   * Simulates a user selecting the tab containing the given text.
   * @param tabText the given text to match.
   * @return this fixture.
   */
  public JTabbedPaneFixture selectTab(String tabText) {
    selectTab(new JTabbedPaneLocation(tabText));
    return this;
  }

  private JTabbedPaneFixture selectTab(JTabbedPaneLocation tabLocation) {
    tabbedPaneTester().actionSelectTab(target, tabLocation);
    return this;
  }
  
  /** @return the titles of all the tabs. */
  public String[] tabTitles() {
    return tabbedPaneTester().getTabs(target);
  }
  
  private JTabbedPaneTester tabbedPaneTester() {
    return testerCastedTo(JTabbedPaneTester.class);
  }

  /** {@inheritDoc} */
  public final JTabbedPaneFixture click() {
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public final JTabbedPaneFixture focus() {
    doFocus();
    return this;
  }

  /** {@inheritDoc} */
  public final JTabbedPaneFixture shouldBeVisible() {
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final JTabbedPaneFixture shouldNotBeVisible() {
    assertIsNotVisible();
    return this;
  }
}
