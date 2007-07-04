/*
 * Created on Jul 1, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JSlider;

import abbot.tester.JSliderTester;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JSlider}</code> and verification of the state of such
 * <code>{@link JSlider}</code>.
 *
 * @author Yvonne Wang
 *
 */
public class JSliderFixture extends ComponentFixture<JSlider> {

  /**
   * Creates a new </code>{@link JSliderFixture}</code>.
   * @param robot performs simulation of user events on a <code>JSlider</code>.
   * @param sliderName the name of the <code>JSlider</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JSlider</code> could not be found.
   */
  public JSliderFixture(RobotFixture robot, String sliderName) {
    super(robot, sliderName, JSlider.class);
  }

  /**
   * Creates a new </code>{@link JSliderFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JSlider</code>.
   * @param target the <code>JSlider</code> to be managed by this fixture.
   */
  public JSliderFixture(RobotFixture robot, JSlider target) {
    super(robot, target);
  }
  
  public JSliderFixture increment() {
    sliderTester().actionIncrement(target);
    return this;
  }
  
  public JSliderFixture decrement() {
    sliderTester().actionDecrement(target);
    return this;
  }
  
  protected final JSliderTester sliderTester() {
    return testerCastedTo(JSliderTester.class);
  }

  /** ${@inheritDoc} */
  @Override public final JSliderFixture click() {
    return (JSliderFixture)super.click();
  }

  /** ${@inheritDoc} */
  @Override public final JSliderFixture focus() {
    return (JSliderFixture)super.focus();
  }

  /** ${@inheritDoc} */
  @Override public final JSliderFixture requireDisabled() {
    return (JSliderFixture)super.requireDisabled();
  }

  /** ${@inheritDoc} */
  @Override public final JSliderFixture requireEnabled() {
    return (JSliderFixture)super.requireEnabled();
  }

  /** ${@inheritDoc} */
  @Override public final JSliderFixture requireNotVisible() {
    return (JSliderFixture)super.requireNotVisible();
  }

  /** ${@inheritDoc} */
  @Override public final JSliderFixture requireVisible() {
    return (JSliderFixture)super.requireVisible();
  }
}
