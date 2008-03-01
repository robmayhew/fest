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
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestFrame;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import static org.fest.swing.testing.TestGroups.FUNCTIONAL;

/**
 * Tests for <code>{@link JSplitPaneDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = FUNCTIONAL)
public class JSplitPaneDriverTest {

  private Robot robot;
  private JSplitPane splitPane;
  private JSplitPaneDriver driver;
  
  @BeforeMethod public void setUp() {
    robot = robotWithCurrentAwtHierarchy();
    driver = new JSplitPaneDriver(robot);
    MyFrame frame = new MyFrame();
    splitPane = frame.splitPane;
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldMoveDividerToGivenLocation() {
    int rightBeforeMoving = rightComponentWidth();
    int leftBeforeMoving = leftComponentWidth();
    int delta = 100;
    int newLocation = splitPane.getDividerLocation() + delta;
    driver.moveDividerTo(splitPane, newLocation);
    assertThat(rightComponentWidth()).isEqualTo(rightBeforeMoving - delta);
    assertThat(leftComponentWidth()).isEqualTo(leftBeforeMoving + delta);
  }
  
  private int rightComponentWidth() {
    return splitPane.getRightComponent().getWidth();
  }  

  private int leftComponentWidth() {
    return splitPane.getLeftComponent().getWidth();
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;
    
    final JSplitPane splitPane = new JSplitPane(HORIZONTAL_SPLIT, new JList(), new JList());

    MyFrame() {
      super(JSpinnerDriverTest.class);
      splitPane.setDividerLocation(200);
      splitPane.setPreferredSize(new Dimension(400, 200));
      add(splitPane);
    }
  }
}
