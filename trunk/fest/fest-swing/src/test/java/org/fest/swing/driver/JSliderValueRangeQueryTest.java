/*
 * Created on Aug 12, 2008
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

import javax.swing.JSlider;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link JSliderValueRangeQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = EDT_ACTION)
public class JSliderValueRangeQueryTest {

  private static final int MINIMUM = 6;
  private static final int MAXIMUM = 10;

  private Robot robot;
  private MySlider slider;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    slider = window.slider;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnMaximumValueOfJSlider() {
    ValueRange valueRange = JSliderValueRangeQuery.valueRangeOf(slider);
    assertThat(valueRange.minimum).isEqualTo(MINIMUM);
    assertThat(valueRange.maximum).isEqualTo(MAXIMUM);
    assertThat(slider.methodGetMinimumWasInvoked()).isTrue();
    assertThat(slider.methodGetMaximumWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MySlider slider = new MySlider(MINIMUM, MAXIMUM, 8);

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JSliderMaximumQueryTest.class);
      addComponents(slider);
    }
  }

  private static class MySlider extends JSlider {
    private static final long serialVersionUID = 1L;

    private boolean methodGetMaximumInvoked;
    private boolean methodGetMinimumInvoked;

    MySlider(int min, int max, int value) {
      super(min, max, value);
    }

    @Override public int getMaximum() {
      methodGetMaximumInvoked = true;
      return super.getMaximum();
    }

    @Override public int getMinimum() {
      methodGetMinimumInvoked = true;
      return super.getMinimum();
    }

    boolean methodGetMaximumWasInvoked() { return methodGetMaximumInvoked; }
    boolean methodGetMinimumWasInvoked() { return methodGetMinimumInvoked; }
  }
}
