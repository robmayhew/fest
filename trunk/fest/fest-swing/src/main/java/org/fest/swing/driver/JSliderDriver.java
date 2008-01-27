/*
 * Created on Jan 27, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JSlider;

import org.fest.swing.core.RobotFixture;

/**
 * Understands simulation of user input on a <code>{@link JSlider}</code>. Unlike <code>JSliderFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JSlider}</code>s. This class is intended for internal
 * use only.
 *
 * <p>
 * Adapted from <code>abbot.tester.JSliderTester</code> from <a href="http://abbot.sourceforge.net"
 * target="_blank">Abbot</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public class JSliderDriver extends JComponentDriver {

  private final JSliderLocation location;

  /**
   * Creates a new </code>{@link JSliderDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JSliderDriver(RobotFixture robot) {
    super(robot);
    location = new JSliderLocation();
  }

  /**
   * Clicks at the maximum end of the <code>{@link JSlider}</code>.
   * @param slider the target <code>JSlider</code>.
   */
  public final void increment(JSlider slider) {
    robot.click(slider, location.pointAt(slider, slider.getMaximum()));
  }

  /**
   * Clicks at the minimum end of the <code>{@link JSlider}</code>.
   * @param slider the target <code>JSlider</code>.
   */
  public final void decrement(JSlider slider) {
    robot.click(slider, location.pointAt(slider, slider.getMinimum()));
  }

  /**
   * Slides the knob to its maximum.
   * @param slider the target <code>JSlider</code>.
   */
  public final void slideToMax(JSlider slider) {
    slide(slider, slider.getMaximum());
  }

  /**
   * Slides the knob to its minimum.
   * @param slider the target <code>JSlider</code>.
   */
  public final void slideToMin(JSlider slider) {
    slide(slider, slider.getMinimum());
  }

  /**
   * Slides the knob to the requested value.
   * @param slider the target <code>JSlider</code>.
   * @param value the requested value.
   */
  public final void slide(final JSlider slider, final int value) {
    drag(slider, location.pointAt(slider, slider.getValue()));
    drop(slider, location.pointAt(slider, value));
    // the drag is only approximate, so set the value directly
    robot.invokeAndWait(new Runnable() {
      public void run() {
        slider.setValue(value);
      }
    });
  }
}
