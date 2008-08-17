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

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.driver.JSliderMaximumQuery.maximumOf;
import static org.fest.swing.driver.JSliderMinAndMaxQuery.minAndMaxOf;
import static org.fest.swing.driver.JSliderMinimumQuery.minimumOf;
import static org.fest.swing.driver.JSliderSetValueTask.setValueTask;
import static org.fest.swing.driver.JSliderValueQuery.valueOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.util.Strings.concat;

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
    slide(slider, maximumOf(slider));
  }

  /**
   * Slides the knob to its minimum.
   * @param slider the target <code>JSlider</code>.
   */
  public void slideToMinimum(JSlider slider) {
    slide(slider, minimumOf(slider));
  }

  /**
   * Slides the knob to the requested value.
   * @param slider the target <code>JSlider</code>.
   * @param value the requested value.
   * @throws ActionFailedException if the given position is not within the <code>JSlider</code> bounds.
   */
  public void slide(JSlider slider, int value) {
    validateValue(slider, value);
    if (!isEnabled(slider)) return;
    drag(slider, location.pointAt(slider, valueOf(slider)));
    drop(slider, location.pointAt(slider, value));
    // the drag is only approximate, so set the value directly
    robot.invokeAndWait(setValueTask(slider, value));
  }
  
  private void validateValue(JSlider slider, int value) {
    MinimumAndMaximum minAndMax = minAndMaxOf(slider);
    int min = minAndMax.minimum;
    int max = minAndMax.maximum;
    if (value >= min && value <= max) return;
    throw actionFailure(concat("Value <", value, "> is not within the JSlider bounds of <", min, "> and <", max, ">"));
  }
}
