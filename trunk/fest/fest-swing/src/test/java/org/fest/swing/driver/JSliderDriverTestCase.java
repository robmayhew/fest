/*
 * Created on Feb 25, 2008
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

import javax.swing.JSlider;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.task.GetJSliderValueTask.valueOf;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JSliderDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public abstract class JSliderDriverTestCase {

  private Robot robot;
  private JSlider slider;
  private JSliderDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JSliderDriver(robot);
    MyFrame frame = new MyFrame(getClass(), orientation());
    slider = frame.slider;
    robot.showWindow(frame);
  }

  abstract int orientation();

  JSlider slider() { return slider; }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "valueProvider")
  public void shouldSlideToValue(int value) {
    driver.slide(slider, value);
    assertThatSliderValueIsEqualTo(value);
  }

  @DataProvider(name = "valueProvider")
  public Object[][] valueProvider() {
    return new Object[][] { { 5 }, { 10 }, { 28 }, { 20 } };
  }

  public void shouldNotSlideToValueIfSliderIsNotEnabled() {
    clearAndDisableSlider();
    int value = 10;
    slider.setValue(value);
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(value);
  }

  public void shouldSlideToMaximum() {
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(slider.getMaximum());
  }

  public void shouldNotSlideToMaximumIfSliderIsNotEnabled() {
    clearAndDisableSlider();
    int value = valueOf(slider);
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(value);
  }

  public void shouldSlideToMinimum() {
    driver.slideToMinimum(slider);
    assertThatSliderValueIsEqualTo(slider.getMinimum());
  }

  public void shouldNotSlideToMinimumIfSliderIsNotEnabled() {
    clearAndDisableSlider();
    int value = slider.getMaximum();
    slider.setValue(value);
    driver.slideToMinimum(slider);
    assertThatSliderValueIsEqualTo(value);
  }

  private void assertThatSliderValueIsEqualTo(int expected) {
    assertThat(valueOf(slider)).isEqualTo(expected);
  }

  public void shouldThrowErrorIfValueIsLessThanMinimum() {
    try {
      driver.slide(slider, -1);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo("Value <-1> is not within the JSlider bounds of <0> and <30>");
    }
  }

  public void shouldThrowErrorIfValueIsGreaterThanMaximum() {
    try {
      driver.slide(slider, 31);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo("Value <31> is not within the JSlider bounds of <0> and <30>");
    }
  }

  private void clearAndDisableSlider() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        slider.setValue(0);
        slider.setEnabled(false);
      }
    });
    robot.waitForIdle();
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSlider slider = new JSlider();

    MyFrame(Class<?> testClass, int orientation) {
      super(testClass);
      add(slider);
      slider.setOrientation(orientation);
      slider.setMinimum(0);
      slider.setMaximum(30);
      slider.setValue(15);
      setPreferredSize(new Dimension(300, 300));
    }
  }
}
