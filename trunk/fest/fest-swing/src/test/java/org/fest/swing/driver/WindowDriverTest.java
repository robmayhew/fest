/*
 * Created on Apr 5, 2008
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
import java.awt.Frame;
import java.awt.Point;

import javax.swing.JFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.FluentDimension;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link WindowDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class WindowDriverTest {

  private Robot robot;
  private Frame frame;
  private WindowDriver driver;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = new JFrame(getClass().getName());
    driver = new WindowDriver(robot);
    robot.showWindow(frame, new Dimension(100, 100));
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldResizeWindow() {
    Dimension newSize = new FluentDimension(frame.getSize()).addToHeight(100).addToWidth(200);
    driver.resize(frame, newSize.width, newSize.height);
    assertThat(frame.getSize()).isEqualTo(newSize);
  }
  
  public void shouldResizeWidthOnly() {
    Dimension newSize = new FluentDimension(frame.getSize()).addToWidth(200);
    driver.resizeWidthTo(frame, newSize.width);
    assertThat(frame.getSize()).isEqualTo(newSize);
  }

  public void shouldResizeHeightOnly() {
    Dimension newSize = new FluentDimension(frame.getSize()).addToHeight(100);
    driver.resizeHeightTo(frame, newSize.height);
    assertThat(frame.getSize()).isEqualTo(newSize);
  }
  
  public void shouldMoveWindow() {
    Point newPosition = new Point(200, 200);
    driver.moveTo(frame, newPosition);
    assertThat(frame.getLocationOnScreen()).isEqualTo(newPosition);
  }
  
  public void shouldCloseWindow() {
    driver.close(frame);
    pause(200);
    assertThat(frame.isVisible()).isFalse();
  }
  
  public void shouldShowWindow() {
    frame.setVisible(false);
    driver.show(frame);
    assertThat(frame.isVisible()).isTrue();
  }

  public void shouldShowWindowUsingGivenSize() {
    frame.setVisible(false);
    final Dimension newSize = new Dimension(600, 300);
    driver.show(frame, newSize);
    assertThat(frame.isVisible()).isTrue();
    assertThat(frame.getSize()).isEqualTo(newSize);
  }
}
