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

import java.awt.Point;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.FluentDimension;
import org.fest.swing.testing.FluentPoint;
import org.fest.swing.testing.TestWindow;

import static java.awt.Frame.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.testing.CommonAssertions.*;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link FrameDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class FrameDriverTest {

  private Robot robot;
  private TestWindow window;
  private FrameDriver driver;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = TestWindow.createAndShowNewWindow(getClass());
    driver = new FrameDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldIconifyAndDeiconifyFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.iconify(window);
    assertThat(frameState()).isEqualTo(ICONIFIED);
    driver.deiconify(window);
    assertThat(frameState()).isEqualTo(NORMAL);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMaximizeFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.maximize(window);
    int frameState = frameState() & MAXIMIZED_BOTH;
    assertThat(frameState).isEqualTo(MAXIMIZED_BOTH);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNormalizeFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.maximize(window);
    driver.normalize(window);
    assertThat(frameState()).isEqualTo(NORMAL);
  }

  private int frameState() {
    return window.getExtendedState();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldResizeFrameToGivenSize(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    FluentDimension newSize = frameSize().addToWidth(20).addToHeight(40);
    driver.resizeTo(window, newSize);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  public void shouldThrowErrorWhenResizingDisabledFrame() {
    disable(window);
    robot.waitForIdle();
    try {
      driver.resize(window, 10, 10);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailureDueToDisableComponent(e);
    }
  }
  
  public void shouldThrowErrorWhenResizingNotResizableFrame() {
    makeNotResizable(window);
    robot.waitForIdle();
    try {
      driver.resize(window, 10, 10);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailureDueToNotResizableContainer(e);
    }
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldResizeFrameToGivenWidth(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    FluentDimension newSize = frameSize().addToWidth(20);
    driver.resizeWidthTo(window, newSize.width);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  public void shouldThrowErrorWhenResizingWidthOfDisabledFrame() {
    disable(window);
    robot.waitForIdle();
    try {
      driver.resizeWidthTo(window, 10);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailureDueToDisableComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingWidthOfNotResizableFrame() {
    makeNotResizable(window);
    robot.waitForIdle();
    try {
      driver.resizeWidthTo(window, 10);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailureDueToNotResizableContainer(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldResizeFrameToGivenHeight(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    FluentDimension newSize = frameSize().addToHeight(30);
    driver.resizeHeightTo(window, newSize.height);
    assertThat(sizeOf(window)).isEqualTo(newSize);
  }

  private final FluentDimension frameSize() {
    return new FluentDimension(sizeOf(window));
  }

  public void shouldThrowErrorWhenResizingHeightOfDisabledFrame() {
    disable(window);
    robot.waitForIdle();
    try {
      driver.resizeHeightTo(window, 10);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailureDueToDisableComponent(e);
    }
  }

  public void shouldThrowErrorWhenResizingHeightOfNotResizableFrame() {
    makeNotResizable(window);
    robot.waitForIdle();
    try {
      driver.resizeHeightTo(window, 10);
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailureDueToNotResizableContainer(e);
    }
  }

  private static void makeNotResizable(final TestWindow window) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        window.setResizable(false);
      }
    });
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMoveFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    Point p = locationOnScreenOfWindow().addToX(10).addToY(10);
    driver.move(window, p.x, p.y);
    assertThat(locationOnScreenOfWindow()).isEqualTo(p);
  }

  private FluentPoint locationOnScreenOfWindow() {
    return new FluentPoint(locationOnScreen(window));
  }
}
