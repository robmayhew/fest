/*
 * Created on Aug 9, 2008
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

import javax.swing.JInternalFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MDITestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JInternalFrameAction.ICONIFY;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JInternalFrameIconQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JInternalFrameIconQueryTest {

  private Robot robot;
  private JInternalFrame internalFrame;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MDITestWindow window = MDITestWindow.createNew(getClass());
    internalFrame = window.internalFrame();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTrueIfJInternalFrameIsIconified() {
    JInternalFrameSetIconTask.setIcon(internalFrame, ICONIFY);
    robot.waitForIdle();
    assertThat(JInternalFrameIconQuery.isIconified(internalFrame)).isTrue();
  }

  public void shouldReturnFalseIfJInternalFrameIsNotIconified() {
    assertThat(JInternalFrameIconQuery.isIconified(internalFrame)).isFalse();
  }

}
