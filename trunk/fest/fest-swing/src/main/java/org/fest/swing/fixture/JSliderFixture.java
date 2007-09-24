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
import org.fest.swing.MouseButtons;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JSlider}</code> and verification of the state of such
 * <code>{@link JSlider}</code>.
 *
 * @author Yvonne Wang
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
  
  /**
   * Simulates a user incrementing the value of the managed <code>{@link JSlider}</code>.
   * @return this fixture.
   */
  public final JSliderFixture increment() {
    sliderTester().actionIncrement(target);
    return this;
  }
  
  /**
   * Simulates a user decrementing the value of the managed <code>{@link JSlider}</code>.
   * @return this fixture.
   */
  public final JSliderFixture decrement() {
    sliderTester().actionDecrement(target);
    return this;
  }
  
  /**
   * Simulates a user sliding the managed <code>{@link JSlider}</code> to the given value.
   * @param value the value to slide the <code>JSlider</code> to.
   * @return this fixture.
   */
  public JSliderFixture slideTo(int value) {
    sliderTester().actionSlide(target, value);
    return this;
  }

  /**
   * Simulates a user sliding the managed <code>{@link JSlider}</code> to its maximum value.
   * @return this fixture.
   */
  public JSliderFixture slideToMax() {
    sliderTester().actionSlideMaximum(target);
    return this;
  }

  /**
   * Simulates a user sliding the managed <code>{@link JSlider}</code> to its minimum value.
   * @return this fixture.
   */
  public JSliderFixture slideToMin() {
    sliderTester().actionSlideMinimum(target);
    return this;
  }

  protected final JSliderTester sliderTester() {
    return (JSliderTester)tester();
  }

  /**
   * Simulates a user clicking the <code>{@link JSlider}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSliderFixture click() {
    return (JSliderFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JSlider}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JSliderFixture click(MouseButtons button) {
    return (JSliderFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JSlider}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JSliderFixture click(MouseClickInfo mouseClickInfo) {
    return (JSliderFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JSlider}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSliderFixture rightClick() {
    return (JSliderFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JSlider}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSliderFixture doubleClick() {
    return (JSliderFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JSlider}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JSliderFixture focus() {
    return (JSliderFixture)doFocus();
  }
  
  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JSlider}</code> managed by this
   * fixture. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSliderFixture pressAndReleaseKeys(int... keyCodes) {
    return (JSliderFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on the <code>{@link JSlider}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSliderFixture pressKey(int keyCode) {
    return (JSliderFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JSlider}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JSliderFixture releaseKey(int keyCode) {
    return (JSliderFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JSlider}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSlider</code> is not visible.
   */
  public final JSliderFixture requireVisible() {
    return (JSliderFixture)assertVisible();
  }
  
  /**
   * Asserts that the <code>{@link JSlider}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JSlider</code> is visible.
   */
  public final JSliderFixture requireNotVisible() {
    return (JSliderFixture)assertNotVisible();
  }
  
  /**
   * Asserts that the <code>{@link JSlider}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSlider</code> is disabled.
   */
  public final JSliderFixture requireEnabled() {
    return (JSliderFixture)assertEnabled();
  }

  /**
   * Asserts that the <code>{@link JSlider}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JSlider</code> is enabled.
   */
  public final JSliderFixture requireDisabled() {
    return (JSliderFixture)assertDisabled();
  }
}
