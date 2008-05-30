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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    MyFrame frame = new MyFrame();
    scrollBar = frame.scrollBar;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
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

  @Test public void shouldScrollUnitUpTheGivenNumberOfTimes() {
    driver.scrollUnitUp(scrollBar, 6);
    assertThatScrollBarValueIsEqualTo(36);
  }

  @Test public void shouldNotScrollUnitUpTheGivenNumberOfTimesIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollUnitUp(scrollBar, 6);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test public void shouldScrollUnitUp() {
    driver.scrollUnitUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(31);
  }

  @Test public void shouldNotScrollUnitUpIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
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

  @Test public void shouldScrollUnitDownTheGivenNumberOfTimes() {
    driver.scrollUnitDown(scrollBar, 8);
    assertThatScrollBarValueIsEqualTo(22);
  }

  @Test public void shouldNotScrollUnitDownTheGivenNumberOfTimesIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollUnitDown(scrollBar, 8);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test public void shouldScrollUnitDown() {
    driver.scrollUnitDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(29);
  }

  @Test public void shouldNotScrollUnitDownIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollUnitDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
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

  @Test public void shouldScrollBlockUpTheGivenNumberOfTimes() {
    driver.scrollBlockUp(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(50);
  }

  @Test public void shouldNotScrollBlockUpTheGivenNumberOfTimesIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollBlockUp(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test public void shouldScrollBlockUp() {
    driver.scrollBlockUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(40);
  }

  @Test public void shouldNotScrollBlockUpIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollBlockUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
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

  @Test public void shouldScrollBlockUpDownTheGivenNumberOfTimes() {
    driver.scrollBlockDown(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(10);
  }

  @Test public void shouldNotScrollBlockUpDownTheGivenNumberOfTimesIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollBlockDown(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test public void shouldScrollBlockDown() {
    driver.scrollBlockDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(20);
  }

  @Test public void shouldNotScrollBlockDownIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollBlockDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(value);
  }

  @Test public void shouldScrollToGivenPosition() {
    driver.scrollTo(scrollBar, 68);
    assertThatScrollBarValueIsEqualTo(68);
  }

  @Test public void shouldNotScrollToGivenPositionIfScrollBarIsNotEnabled() {
    clearAndDisableScrollBar();
    int value = scrollBar.getValue();
    driver.scrollTo(scrollBar, 68);
    assertThatScrollBarValueIsEqualTo(value);
  }

  private void assertThatScrollBarValueIsEqualTo(int expected) {
    assertThat(scrollBar.getValue()).isEqualTo(expected);
  }

  @Test public void shouldThrowErrorIfPositionIsLessThanMinimum() {
    try {
      driver.scrollTo(scrollBar, 0);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo("Position <0> is not within the JScrollBar bounds of <10> and <80>");
    }
  }

  @Test public void shouldThrowErrorIfPositionIsGreaterThanMaximum() {
    try {
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
    assertThat(scrollBar.isEnabled()).isFalse();
  }

  private static class MyFrame extends TestFrame {
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
