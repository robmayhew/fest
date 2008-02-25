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
import static org.fest.swing.testing.TestGroups.FUNCTIONAL;
import static org.fest.util.Strings.concat;

import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JScrollBarDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = FUNCTIONAL)
public class JScrollBarDriverTest {

  private static final int VALUE = 30;
  private static final int BLOCK_INCREMENT = 10;

  private RobotFixture robot;
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

  @Test(dataProvider = "negativeAndZero")
  public void shouldThrowErrorIfTimesToScrollUnitUpIsNegativeOrZero(int times) {
    try {
      driver.scrollUnitUp(scrollBar, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat("The number of times to scroll up one unit should be greater than zero, but was ", times);
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test public void shouldScrollUnitUpTheGivenNumberOfTimes() {
    driver.scrollUnitUp(scrollBar, 6);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE + 6);
  }

  @Test public void shouldScrollUnitUp() {
    driver.scrollUnitUp(scrollBar);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE + 1);
  }

  @Test(dataProvider = "negativeAndZero")
  public void shouldThrowErrorIfTimesToScrollUnitDownIsNegativeOrZero(int times) {
    try {
      driver.scrollUnitDown(scrollBar, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to scroll down one unit should be greater than zero, but was ", times);
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test public void shouldScrollUnitDownTheGivenNumberOfTimes() {
    driver.scrollUnitDown(scrollBar, 8);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE - 8);
  }

  @Test public void shouldScrollUnitDown() {
    driver.scrollUnitDown(scrollBar);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE - 1);
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfTimesToScrollBlockUpIsNegative() {
    driver.scrollBlockUp(scrollBar, -1);
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfTimesToScrollBlockUpIsZero() {
    driver.scrollBlockUp(scrollBar, 0);
  }

  @Test public void shouldScrollBlockUpTheGivenNumberOfTimes() {
    int times = 2;
    driver.scrollBlockUp(scrollBar, times);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE + (BLOCK_INCREMENT * times));
  }

  @Test public void shouldScrollBlockUp() {
    driver.scrollBlockUp(scrollBar);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE + BLOCK_INCREMENT);
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfTimesToScrollBlockDownIsNegative() {
    driver.scrollBlockDown(scrollBar, -1);
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfTimesToScrollBlockDownIsZero() {
    driver.scrollBlockDown(scrollBar, 0);
  }

  @Test public void shouldScrollBlockUpDownTheGivenNumberOfTimes() {
    int times = 2;
    driver.scrollBlockDown(scrollBar, times);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE - (BLOCK_INCREMENT * times));
  }

  @Test public void shouldScrollBlockDown() {
    driver.scrollBlockDown(scrollBar);
    assertThat(scrollBar.getValue()).isEqualTo(VALUE - BLOCK_INCREMENT);
  }

  @Test public void shouldScrollToGivenPosition() {
    driver.scrollTo(scrollBar, 68);
    assertThat(scrollBar.getValue()).isEqualTo(68);
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfPositionIsLessThanMinimum() {
    driver.scrollTo(scrollBar, scrollBar.getMinimum() - 10);
  }

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldThrowErrorIfPositionIsGreaterThanMaximum() {
    driver.scrollTo(scrollBar, scrollBar.getMaximum() + 10);
  }

  @DataProvider(name = "negativeAndZero")
  public Object[][] negativeAndZero() {
    return new Object[][] { { -1 }, { 0 } };
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JScrollBar scrollBar = new JScrollBar();

    MyFrame() {
      super(JScrollBarDriverTest.class);
      add(scrollBar);
      scrollBar.setPreferredSize(new Dimension(20, 100));
      scrollBar.setBlockIncrement(BLOCK_INCREMENT);
      scrollBar.setValue(VALUE);
      scrollBar.setMinimum(10);
      scrollBar.setMaximum(80);
      setPreferredSize(new Dimension(60, 200));
    }
  }
}
