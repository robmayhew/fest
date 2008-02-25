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

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;

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
   * @param robot
   */
  public JTabbedPaneDriver(RobotFixture robot) {
    super(robot);
    location = new JTabbedPaneLocation();
  }

  /**
   * Returns the titles of all the tabs.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @return the titles of all the tabs.
   */
  public String[] tabTitles(JTabbedPane tabbedPane) {
      int count = tabbedPane.getTabCount();
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
  public final void selectTab(JTabbedPane tabbedPane, String title) {
    int index = location.titleToIndex(tabbedPane, title);
    selectTab(tabbedPane, index);
  }

  /**
   * Simulates a user selecting the tab located at the given index.
   * @param tabbedPane the target <code>JTabbedPane</code>.
   * @param index the index of the tab to select.
   * @throws ActionFailedException if the given index is not within the <code>JTabbedPane</code> bounds.
   */
  public final void selectTab(JTabbedPane tabbedPane, int index) {
    try {
      Point p = location.pointAt(tabbedPane, index);
      robot.click(tabbedPane, p);
    } catch (LocationUnavailableException e) {
      // Set the tab directly
      robot.invokeAndWait(new SetSelectedIndexTask(tabbedPane, index));
    }
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
