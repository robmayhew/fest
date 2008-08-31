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
import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static javax.swing.JSplitPane.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.ComponentSetEnableTask.disable;
import static org.fest.swing.core.EventMode.*;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
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
    MyWindow window = MyWindow.createAndShowInEDT(orientation);
    splitPane = window.splitPane;
    int newLocation = splitPane.getDividerLocation() + 100;
    driver.moveDividerTo(splitPane, newLocation);
    assertThat(splitPane.getDividerLocation()).isEqualTo(newLocation);
  }

  @Test(groups = GUI, dataProvider = "orientations")
  public void shouldNotMoveDividerToGivenLocationIfSplitPaneIsNotEnabled(int orientation, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    MyWindow window = MyWindow.createAndShowInEDT(orientation);
    splitPane = window.splitPane;
    disable(splitPane);
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

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSplitPane splitPane;

    static MyWindow createAndShowInEDT(final int orientation) {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(orientation); }
      });
      window.display();
      return window;
    }
    
    MyWindow(int orientation) {
      super(JSplitPaneDriverTest.class);
      splitPane = new JSplitPane(orientation, new JList(), new JList());
      splitPane.setDividerLocation(150);
      splitPane.setPreferredSize(new Dimension(300, 300));
      add(splitPane);
    }
  }
}
