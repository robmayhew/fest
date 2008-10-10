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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;
import static org.fest.swing.testing.TestGroups.GUI;

import javax.swing.JSlider;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JSliderValueRangeQuery}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
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
    slider.startRecording();
    ValueRange valueRange = JSliderValueRangeQuery.valueRangeOf(slider);
    assertThat(valueRange.minimum).isEqualTo(MINIMUM);
    assertThat(valueRange.maximum).isEqualTo(MAXIMUM);
    slider.requireInvoked("getMaximum")
          .requireInvoked("getMinimum");
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

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MySlider(int min, int max, int value) {
      super(min, max, value);
    }

    @Override public int getMaximum() {
      if (recording) methodInvocations.invoked("getMaximum");
      return super.getMaximum();
    }

    @Override public int getMinimum() {
      if (recording) methodInvocations.invoked("getMinimum");
      return super.getMinimum();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
