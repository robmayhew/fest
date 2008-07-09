/*
 * Created on Jul 9, 2008
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
package org.fest.swing.fixture;

import java.awt.Component;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;

/**
 * Understands common behavior in component fixtures.
 *
 * @author Alex Ruiz 
 */
public final class CommonComponentFixtureBehavior {

  // TODO test
  private final ComponentDriver driver;
  private final Component target;

  /**
   * Creates a new </code>{@link CommonComponentFixtureBehavior}</code>.
   * @param robot the robot to delegate work to.
   * @param target the target <code>Component</code>.
   */
  public CommonComponentFixtureBehavior(Robot robot, Component target) {
    this(new ComponentDriver(robot), target);
  }

  CommonComponentFixtureBehavior(ComponentDriver driver, Component target) {
    this.driver = driver;
    this.target = target;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @throws IllegalArgumentException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  public void click(MouseClickInfo mouseClickInfo) {
    if (mouseClickInfo == null) throw new IllegalArgumentException("The given MouseClickInfo should not be null");
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
  }
  
  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link Component}</code>.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @throws IllegalArgumentException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  public void pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    if (keyPressInfo == null) throw new IllegalArgumentException("The given KeyPressInfo should not be null");
    driver.pressAndReleaseKey(target, keyPressInfo.keyCode(), keyPressInfo.modifiers());
  }
}
