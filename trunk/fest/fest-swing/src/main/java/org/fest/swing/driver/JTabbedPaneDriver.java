/*
 * Created on Jan 27, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Point;

import javax.swing.JTabbedPane;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;

import static org.fest.swing.task.GetJTabbedPaneTabCountTask.tabCountOf;
import static org.fest.swing.task.IsComponentEnabledTask.isEnabled;

/**
 * Understands simulation of user input on a <code>{@link JTabbedPane}</code>. Unlike <code>JTabbedPaneFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JTabbedPane}</code>s. This class is intended for
 * internal use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneDriver extends JComponentDriver {

  private final JTabbedPaneLocation location;

  /**
   * Creates a new </code>{@link JTabbedPaneDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JTabbedPaneDriver(Robot robot) {
    this(robot, new JTabbedPaneLocation());
  }

  /**
   * Creates a new </code>{@link JTabbedPaneDriver}</code>.
   * @param robot the robot to use to simulate user input.
   * @param location knows how to find the location of a tab.
   */
  JTabbedPaneDriver(Robot robot, JTabbedPaneLocation location) {
    super(robot);
    this.location = location;
  }

  /**
   * Returns the titles of all the tabs.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @return the titles of all the tabs.
   */
  public String[] tabTitles(JTabbedPane tabbedPane) {
    int count = tabCountOf(tabbedPane);
    String[] titles = new String[count];
    for (int i = 0; i < count; i++) titles[i] = tabbedPane.getTitleAt(i);
    return titles;
  }

  /**
   * Simulates a user selecting the tab containing the given title.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param title the given text to match.
   * @throws LocationUnavailableException if a tab matching the given title could not be found.
   */
  public void selectTab(JTabbedPane tabbedPane, String title) {
    if (!isEnabled(tabbedPane)) return;
    int index = location.indexOf(tabbedPane, title);
    selectTab(tabbedPane, index);
  }

  /**
   * Simulates a user selecting the tab located at the given index.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param index the index of the tab to select.
   * @throws ActionFailedException if the given index is not within the <code>JTabbedPane</code> bounds.
   */
  public void selectTab(JTabbedPane tabbedPane, int index) {
    if (!isEnabled(tabbedPane)) return;
    try {
      Point p = location.pointAt(tabbedPane, index);
      click(tabbedPane, p);
    } catch (LocationUnavailableException e) {
      // Set the tab directly
      setTabDirectly(tabbedPane, index);
    }
  }

  void setTabDirectly(JTabbedPane tabbedPane, int index) {
    robot.invokeAndWait(new SetSelectedIndexTask(tabbedPane, index));
    robot.waitForIdle();
  }  

  private static class SetSelectedIndexTask implements Runnable {
    private final JTabbedPane target;
    private final int index;

    SetSelectedIndexTask(JTabbedPane target, int index) {
      this.target = target;
      this.index = index;
    }

    public void run() {
      target.setSelectedIndex(index);
    }
  }
}
