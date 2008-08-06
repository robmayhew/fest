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

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JSplitPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static javax.swing.JSplitPane.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.EventMode.*;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import static org.fest.swing.task.IsComponentEnabledTask.isEnabled;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JSplitPaneDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSplitPaneDriverTest {

  private Robot robot;
  private JSplitPane splitPane;
  private JSplitPaneDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithCurrentAwtHierarchy();
    driver = new JSplitPaneDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "orientations")
  public void shouldMoveDividerToGivenLocation(int orientation, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    MyFrame frame = myFrame(orientation);
    splitPane = frame.splitPane;
    robot.showWindow(frame);
    int newLocation = splitPane.getDividerLocation() + 100;
    driver.moveDividerTo(splitPane, newLocation);
    assertThat(splitPane.getDividerLocation()).isEqualTo(newLocation);
  }

  @Test(groups = GUI, dataProvider = "orientations")
  public void shouldNotMoveDividerToGivenLocationIfSplitPaneIsNotEnabled(int orientation, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    MyFrame frame = myFrame(orientation);
    splitPane = frame.splitPane;
    robot.showWindow(frame);
    robot.invokeAndWait(new Runnable() {
      public void run() {
        splitPane.setEnabled(false);
      }
    });
    robot.waitForIdle();
    assertThat(isEnabled(splitPane)).isFalse();
    int originalLocation = splitPane.getDividerLocation();
    driver.moveDividerTo(splitPane, originalLocation + 100);
    assertThat(splitPane.getDividerLocation()).isEqualTo(originalLocation);
  }

  @DataProvider(name = "orientations") public Object[][] orientations() {
    return new Object[][] {
        { VERTICAL_SPLIT, AWT },
        { VERTICAL_SPLIT, ROBOT },
        { HORIZONTAL_SPLIT, AWT },
        { HORIZONTAL_SPLIT, ROBOT }
    };
  }

  private MyFrame myFrame(final int orientation) {
    return new GuiTask<MyFrame>() {
      protected MyFrame executeInEDT() {
        return new MyFrame(orientation);
      }
    }.run();
  }

  private static class MyFrame extends TestWindow {
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
