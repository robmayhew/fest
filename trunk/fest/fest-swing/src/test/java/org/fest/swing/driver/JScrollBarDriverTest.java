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

import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JScrollBarValueQuery.valueOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JScrollBarDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JScrollBarDriverTest {

  private Robot robot;
  private JScrollBar scrollBar;
  private JScrollBarDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JScrollBarDriver(robot);
    MyFrame frame = new GuiQuery<MyFrame>() {
      protected MyFrame executeInEDT() {
        return new MyFrame();
      }
    }.run();
    scrollBar = frame.scrollBar;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldPassIfValueIsEqualToExpected() {
    setScrollBarValueTo(30);
    driver.requireValue(scrollBar, 30);
  }
  
  public void shouldFailIfValueIsNotEqualToExpected() {
    setScrollBarValueTo(30);
    try {
      driver.requireValue(scrollBar, 20);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'value'")
                             .contains("expected:<20> but was:<30>");
    }
  }

  private void setScrollBarValueTo(final int value) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        scrollBar.setValue(value);
      }
    });
  }
  
  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollUnitUpIsZeroOrNegative(int times) {
    try {
      driver.scrollUnitUp(scrollBar, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to scroll up one unit should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitUpTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitUp(scrollBar, 6);
    assertThatScrollBarValueIsEqualTo(36);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollUnitUpTheGivenNumberOfTimesIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollUnitUp(scrollBar, 6);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitUp(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(31);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollUnitUpIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollUnitUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollUnitDownIsZeroOrNegative(int times) {
    try {
      driver.scrollUnitDown(scrollBar, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to scroll down one unit should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitDownTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitDown(scrollBar, 8);
    assertThatScrollBarValueIsEqualTo(22);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollUnitDownTheGivenNumberOfTimesIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollUnitDown(scrollBar, 8);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitDown(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(29);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollUnitDownIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollUnitDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollBlockUpIsZeroOrNegative(int times) {
    try {
      driver.scrollBlockUp(scrollBar, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to scroll up one block should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockUpTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockUp(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(50);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollBlockUpTheGivenNumberOfTimesIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollBlockUp(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockUp(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(40);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollBlockUpIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollBlockUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollBlockDownIsZeroOrNegative(int times) {
    try {
      driver.scrollBlockDown(scrollBar, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to scroll down one block should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockUpDownTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockDown(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(10);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollBlockUpDownTheGivenNumberOfTimesIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollBlockDown(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockDown(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(20);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollBlockDownIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollBlockDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollToGivenPosition(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollTo(scrollBar, 68);
    assertThatScrollBarValueIsEqualTo(68);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotScrollToGivenPositionIfScrollBarIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableScrollBar();
    int value = valueOf(scrollBar);
    driver.scrollTo(scrollBar, 68);
    assertThatScrollBarValueIsEqualTo(value);
  }

  private void assertThatScrollBarValueIsEqualTo(int expected) {
    assertThat(valueOf(scrollBar)).isEqualTo(expected);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfPositionIsLessThanMinimum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      driver.scrollTo(scrollBar, 0);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo("Position <0> is not within the JScrollBar bounds of <10> and <80>");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfPositionIsGreaterThanMaximum(EventMode eventMode) {
    try {
      robot.settings().eventMode(eventMode);
      driver.scrollTo(scrollBar, 90);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo("Position <90> is not within the JScrollBar bounds of <10> and <80>");
    }
  }

  private void clearAndDisableScrollBar() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        scrollBar.setValue(0);
        scrollBar.setEnabled(false);
      }
    });
    robot.waitForIdle();
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JScrollBar scrollBar = new JScrollBar();

    MyFrame() {
      super(JScrollBarDriverTest.class);
      add(scrollBar);
      scrollBar.setPreferredSize(new Dimension(20, 100));
      scrollBar.setBlockIncrement(10);
      scrollBar.setValue(30);
      scrollBar.setMinimum(10);
      scrollBar.setMaximum(80);
      setPreferredSize(new Dimension(60, 200));
    }
  }
}
