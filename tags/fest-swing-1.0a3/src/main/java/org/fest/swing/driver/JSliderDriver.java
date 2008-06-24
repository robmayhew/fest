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

import static java.lang.String.valueOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.concat;

import javax.swing.JSlider;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

/**
 * Understands simulation of user input on a <code>{@link JSlider}</code>. Unlike <code>JSliderFixture</code>, this
 * driver only focuses on behavior present only in <code>{@link JSlider}</code>s. This class is intended for internal
 * use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JSliderDriver extends JComponentDriver {

  private final JSliderLocation location;

  /**
   * Creates a new </code>{@link JSliderDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JSliderDriver(Robot robot) {
    super(robot);
    location = new JSliderLocation();
  }

  /**
   * Slides the knob to its maximum.
   * @param slider the target <code>JSlider</code>.
   */
  public void slideToMaximum(JSlider slider) {
    slide(slider, slider.getMaximum());
  }

  /**
   * Slides the knob to its minimum.
   * @param slider the target <code>JSlider</code>.
   */
  public void slideToMinimum(JSlider slider) {
    slide(slider, slider.getMinimum());
  }

  /**
   * Slides the knob to the requested value.
   * @param slider the target <code>JSlider</code>.
   * @param value the requested value.
   * @throws ActionFailedException if the given position is not within the <code>JSlider</code> bounds.
   */
  public void slide(JSlider slider, int value) {
    validateValue(slider, value);
    if (!slider.isEnabled()) return;
    drag(slider, location.pointAt(slider, slider.getValue()));
    drop(slider, location.pointAt(slider, value));
    // the drag is only approximate, so set the value directly
    robot.invokeAndWait(new SetValueTask(slider, value));
  }

  private void validateValue(JSlider slider, int value) {
    int min = slider.getMinimum();
    int max = slider.getMaximum();
    if (value >= min && value <= max) return;
    throw actionFailure(concat(
        "Value <", valueOf(value), "> is not within the JSlider bounds of <", valueOf(min), "> and <", valueOf(max), ">"));
  }

  private static class SetValueTask implements Runnable {
    private final JSlider target;
    private final int value;

    SetValueTask(JSlider target, int value) {
      this.target = target;
      this.value = value;
    }

    public void run() {
      target.setValue(value);
    }
  }
}
