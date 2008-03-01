/*
 * Created on Feb 24, 2008
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

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.util.Platform.*;

/**
 * Tests for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 */
public class ComponentDriverTest {

  private ComponentDriver driver;
  private Robot robot;
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new ComponentDriver(robot) {};
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldReturnIsResizableIfComponentIsDialog() {
    Component c = new JDialog();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  @Test public void shouldReturnIsResizableIfComponentIsFrame() {
    Component c = new JFrame();
    assertThat(driver.isUserResizable(c)).isTrue();
  }
  
  @Test public void shouldReturnIsResizableIfComponentIsNotDialogOrFrameAndPlatformIsNotWindowsOrMac() {
    if (isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserResizable(c)).isTrue();
  }

  @Test public void shouldReturnIsNotResizableIfComponentIsNotDialogOrFrameAndPlatformIsWindowsOrMac() {
    if (!isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserResizable(c)).isFalse();
    return;
  }
  
  @Test public void shouldReturnIsMovableIfComponentIsDialog() {
    Component c = new JDialog();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  @Test public void shouldReturnIsMovableIfComponentIsFrame() {
    Component c = new JFrame();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  @Test public void shouldReturnIsMovableIfComponentIsNotDialogOrFrameAndPlatformIsNotWindowsOrMac() {
    if (isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserMovable(c)).isTrue();
  }

  @Test public void shouldReturnIsNotMovableIfComponentIsNotDialogOrFrameAndPlatformIsWindowsOrMac() {
    if (!isWindowsOrMac()) return;
    Component c = new JTextField();
    assertThat(driver.isUserMovable(c)).isFalse();
    return;
  }

  private boolean isWindowsOrMac() {
    return IS_WINDOWS || IS_MACINTOSH;
  }
}
