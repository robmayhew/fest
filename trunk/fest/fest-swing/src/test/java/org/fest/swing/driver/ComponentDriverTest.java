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

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link ComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI) 
public class ComponentDriverTest {

  private Robot robot;
  private ComponentDriver driver;
  private JButton button;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    button = window.button;
    driver = new ComponentDriver(robot);
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldCenterOfComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    driver.click(button);
    assertThat(clickRecorder).wasClicked()
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }
  
  public void shouldThrowErrorWhenClickingDisabledComponent() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    disable(button);
    robot.waitForIdle();
    try {
      driver.click(button);
      fail("Expecting exception");
    } catch (ActionFailedException e) {}
    assertThat(clickRecorder).wasNotClicked();
  }
  
  public void shouldClickComponentWithGivenMouseButtonOnce() {
    ClickRecorder clickRecorder = ClickRecorder.attachTo(button);
    MouseButton mouseButton = RIGHT_BUTTON;
    driver.click(button, mouseButton);
    assertThat(clickRecorder).wasRightClicked()
                             .clickedAt(centerOf(button))
                             .timesClicked(1);
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JButton button = new JButton("Click Me");
    
    private MyWindow() {
      super(ComponentDriverTest.class);
      addComponents(button);
    }
  }
}
