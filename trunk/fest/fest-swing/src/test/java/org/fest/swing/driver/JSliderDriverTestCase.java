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

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.EventMode.*;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JSliderValueQuery.valueOf;
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
    MyFrame frame = new GuiQuery<MyFrame>() {
      protected MyFrame executeInEDT() throws Throwable {
        return new MyFrame(getClass(), orientation());
      }
    }.run();
    slider = frame.slider;
    robot.showWindow(frame);
  }

  abstract int orientation();

  JSlider slider() { return slider; }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "valueProvider")
  public void shouldSlideToValue(int value, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.slide(slider, value);
    assertThatSliderValueIsEqualTo(value);
  }

  @DataProvider(name = "valueProvider")
  public Object[][] valueProvider() {
    return new Object[][] { 
        {  5, AWT }, {  5, ROBOT }, 
        { 10, AWT }, { 10, ROBOT }, 
        { 28, AWT }, { 28, ROBOT }, 
        { 20, AWT }, { 20, ROBOT } 
    };
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSlideToValueIfSliderIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableSlider();
    int value = 10;
    setJSliderValue(value);
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSlideToMaximum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(sliderMaximum());
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSlideToMaximumIfSliderIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableSlider();
    int value = valueOf(slider);
    driver.slideToMaximum(slider);
    assertThatSliderValueIsEqualTo(value);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSlideToMinimum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.slideToMinimum(slider);
    assertThatSliderValueIsEqualTo(slider.getMinimum());
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSlideToMinimumIfSliderIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableSlider();
    int value = sliderMaximum();
    setJSliderValue(value);
    driver.slideToMinimum(slider);
    assertThatSliderValueIsEqualTo(value);
  }

  private int sliderMaximum() {
    return new GuiQuery<Integer>() {
      protected Integer executeInEDT() throws Throwable {
        return slider.getMaximum();
      }
    }.run();
  }

  private void setJSliderValue(final int value) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        slider.setValue(value);        
      }
    });
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
  
  protected int sliderOrientation() {
    return new GuiQuery<Integer>() {
      protected Integer executeInEDT() throws Throwable {
        return slider.getOrientation();
      }
    }.run();
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
