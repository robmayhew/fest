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

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestFrame;

import static javax.swing.SwingConstants.HORIZONTAL;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.FUNCTIONAL;

/**
 * Tests for <code>{@link JSliderDriver}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = FUNCTIONAL)
public class JSliderDriverTest {

  private RobotFixture robot;
  private JSlider slider;
  private JSliderDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JSliderDriver(robot);
    MyFrame frame = new MyFrame();
    slider = frame.slider;
    robot.showWindow(frame);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldIncrementValue() {
    driver.increment(slider);
    assertThat(slider.getValue()).isGreaterThan(15);
  }

  @Test public void shouldDecrementValue() {
    driver.decrement(slider);
    assertThat(slider.getValue()).isLessThan(15);
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
  
  @Test public void shouldSlideToMaximum() {
    driver.slideToMax(slider);
    assertThatSliderValueIsEqualTo(slider.getMaximum());
  }
  
  @Test public void shouldSlideToMinimum() {
    driver.slideToMin(slider);
    assertThatSliderValueIsEqualTo(slider.getMinimum());
  }
  
  private void assertThatSliderValueIsEqualTo(int expected) {
    assertThat(slider.getValue()).isEqualTo(expected);
  }

  @Test public void shouldThrowErrorIfValueIsLessThanMinimum() {
    try {
      driver.slide(slider, -1);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo("Value <-1> is not within the JSlider bounds of <0> and <30>");
    }
  }

  @Test public void shouldThrowErrorIfValueIsGreaterThanMaximum() {
    try {
      driver.slide(slider, 31);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().isEqualTo("Value <31> is not within the JSlider bounds of <0> and <30>");
    }
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JSlider slider = new JSlider();
    
    MyFrame() {
      super(JScrollBarDriverTest.class);
      add(slider);
      slider.setOrientation(HORIZONTAL);
      slider.setMinimum(0);
      slider.setMaximum(30);
      slider.setValue(15);
      setPreferredSize(new Dimension(300, 60));
    }
  }
}
