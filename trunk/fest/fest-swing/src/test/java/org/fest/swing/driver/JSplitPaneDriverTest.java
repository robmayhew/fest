/*
 * Created on Feb 25, 2008
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

import static javax.swing.JSplitPane.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JSplitPane;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JSplitPaneDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JSplitPaneDriverTest {

  private Robot robot;
  private JSplitPane splitPane;
  private JSplitPaneDriver driver;

  @Test(dataProvider = "orientations")
  public void shouldMoveDividerToGivenLocation(int orientation) {
    robot = robotWithCurrentAwtHierarchy();
    driver = new JSplitPaneDriver(robot);
    MyFrame frame = new MyFrame(orientation);
    splitPane = frame.splitPane;
    robot.showWindow(frame);
    try {
      int delta = 100;
      int newLocation = splitPane.getDividerLocation() + delta;
      driver.moveDividerTo(splitPane, newLocation);
      assertThat(splitPane.getDividerLocation()).isEqualTo(newLocation);
    } finally {
      robot.cleanUp();
    }
  }

  @DataProvider(name = "orientations") public Object[][] orientations() {
    return new Object[][] { { VERTICAL_SPLIT }, { HORIZONTAL_SPLIT } };
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JSplitPane splitPane;

    MyFrame(int orientation) {
      super(JSplitPaneDriverTest.class);
      splitPane = new JSplitPane(orientation, new JList(), new JList());
      splitPane.setDividerLocation(150);
      splitPane.setPreferredSize(new Dimension(300, 300));
      add(splitPane);
    }
  }
}
