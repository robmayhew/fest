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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.FluentDimension;
import org.fest.swing.testing.FluentPoint;
import org.fest.swing.testing.TestWindow;

import static java.awt.Frame.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link FrameDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class FrameDriverTest {

  private Robot robot;
  private TestWindow frame;
  private FrameDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    frame = TestWindow.createAndShowNewWindow(getClass());
    driver = new FrameDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldIconifyAndDeiconifyFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.iconify(frame);
    assertThat(frameState()).isEqualTo(ICONIFIED);
    driver.deiconify(frame);
    assertThat(frameState()).isEqualTo(NORMAL);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldMaximizeFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.maximize(frame);
    int frameState = frameState() & MAXIMIZED_BOTH;
    assertThat(frameState).isEqualTo(MAXIMIZED_BOTH);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNormalizeFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.maximize(frame);
    driver.normalize(frame);
    assertThat(frameState()).isEqualTo(NORMAL);
  }

  private int frameState() {
    return frame.getExtendedState();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public final void shouldResizeFrameToGivenSize(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    FluentDimension newSize = frameSize().addToWidth(20).addToHeight(40);
    driver.resize(frame, newSize.width, newSize.height);
    assertThat(sizeOf(frame)).isEqualTo(newSize);
  }

  private final FluentDimension frameSize() {
    return new FluentDimension(sizeOf(frame));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public final void shouldMoveFrame(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    Point p = frameLocationOnScreen().addToX(10).addToY(10);
    driver.move(frame, p.x, p.y);
    assertThat(frameLocationOnScreen()).isEqualTo(p);
  }

  private FluentPoint frameLocationOnScreen() {
    return new FluentPoint(locationOnScreen(frame));
  }
}
