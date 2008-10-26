/*
 * Created on Aug 8, 2008
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
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.BooleanProvider;
import org.fest.swing.testing.MDITestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JInternalFrameMaximizableQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JInternalFrameMaximizableQueryTest {

  private Robot robot;
  private JInternalFrame internalFrame;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MDITestWindow window = MDITestWindow.createNewWindow(getClass());
    internalFrame = window.internalFrame();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class, groups = EDT_ACTION)
  public void shouldIndicateIfJInternalFrameIsMaximizable(final boolean maximizable) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        internalFrame.setMaximizable(maximizable);
      }
    });
    robot.waitForIdle();
    assertThat(JInternalFrameMaximizableQuery.isMaximizable(internalFrame)).isEqualTo(maximizable);
  }
}
