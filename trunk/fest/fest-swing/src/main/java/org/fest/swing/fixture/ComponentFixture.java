/*
 * Created on Oct 20, 2006
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
 * Copyright @2006-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;

import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentDriver.propertyName;
import static org.fest.swing.fixture.ComponentFixtureValidator.*;
import static org.fest.swing.format.Formatting.format;

/**
 * Understands simulation of user events on a <code>{@link Component}</code> and verification of the state of such
 * <code>{@link Component}</code>.
 * @param <T> the type of <code>Component</code> that this fixture can manage.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ComponentFixture<T extends Component> implements MouseInputSimulationFixture,
    KeyboardInputSimulationFixture, StateVerificationFixture {

  /** Name of the property "font". */
  protected static final String FONT_PROPERTY = "font";

  /** Name of the property "background". */
  protected static final String BACKGROUND_PROPERTY = "background";
  
  /** Name of the property "foreground". */
  protected static final String FOREGROUND_PROPERTY = "foreground";

  /** Performs simulation of user events on <code>{@link #target}</code> */
  public final Robot robot;

  /** This fixture's <code>{@link Component}</code>. */
  public final T target;
  
  private final CommonComponentFixtureBehavior commonBehavior;

  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public ComponentFixture(Robot robot, Class<? extends T> type) {
    this(robot, findTarget(robot, type));
  }

  private static <C extends Component> C findTarget(Robot robot, Class<? extends C> type) {
    validate(robot, type);
    return robot.finder().findByType(type, requireShowing(robot));
  }

  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param name the name of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public ComponentFixture(Robot robot, String name, Class<? extends T> type) {
    this(robot, findTarget(robot, name, type));
  }

  private static <C extends Component> C findTarget(Robot robot, String name, Class<? extends C> type) {
    validate(robot, type);
    return robot.finder().findByName(name, type, requireShowing(robot));
  }

  private static void validate(Robot robot, Class<?> type) {
    notNullRobot(robot);
    if (type == null) throw new IllegalArgumentException("The type of component to look for should not be null");
  }

  /**
   * Returns whether showing components are the only ones participating in a component lookup. The returned value is
   * obtained from the <code>{@link Settings#componentLookupScope() component lookup scope}</code> stored in this
   * fixture's <code>{@link Robot}</code>. 
   * @return <code>true</code> if only showing components can participate in a component lookup, <code>false</code>
   * otherwise.
   */
  protected boolean requireShowing() {
    return requireShowing(robot);
  }
  
  private static boolean requireShowing(Robot robot) {
    return robot.settings().componentLookupScope().requireShowing();
  }

  /**
   * Returns the <code>{@link ComponentDriver}</code> used internally to simulate user input and verify the state of
   * this fixture's <code>{@link Component}</code>.
   * @return the internal <code>ComponentDriver</code>.
   */
  protected abstract ComponentDriver driver();
  
  /**
   * Creates a new <code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Component</code>.
   * @param target the <code>Component</code> to be managed by this fixture.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>target</code> is <code>null</code>.
   */
  public ComponentFixture(Robot robot, T target) {
    this.robot = notNullRobot(robot);
    this.target = notNullTarget(target);
    commonBehavior = new CommonComponentFixtureBehavior(driver(), target);
  }

  /**
   * Gives input focus to this fixture's GUI component.
   * @return this fixture.
   */
  public abstract ComponentFixture<T> focus();

  /**
   * Simulates a user clicking this fixture's <code>{@link Component}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @throws IllegalArgumentException if the given <code>MouseClickInfo</code> is <code>null</code>.
   */
  protected final void doClick(MouseClickInfo mouseClickInfo) {
    commonBehavior.click(mouseClickInfo);
  }
  
  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link Component}</code>.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @throws IllegalArgumentException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   */
  protected final void doPressAndReleaseKey(KeyPressInfo keyPressInfo) {
    commonBehavior.pressAndReleaseKey(keyPressInfo);
  }

  /**
   * Returns a fixture that verifies the font of this fixture's <code>{@link Component}</code>.
   * @return a fixture that verifies the font of this fixture's <code>Component</code>.
   */
  public final FontFixture font() {
    return new FontFixture(target.getFont(), propertyName(target, FONT_PROPERTY));
  }

  /**
   * Returns a fixture that verifies the background color of this fixture's <code>{@link Component}</code>.
   * @return a fixture that verifies the background color of this fixture's <code>Component</code>.
   */
  public final ColorFixture background() {
    return new ColorFixture(target.getBackground(), propertyName(target, BACKGROUND_PROPERTY));
  }
  
  /**
   * Returns a fixture that verifies the foreground color of this fixture's <code>{@link Component}</code>.
   * @return a fixture that verifies the foreground color of this fixture's <code>Component</code>.
   */
  public final ColorFixture foreground() {
    return new ColorFixture(target.getForeground(), propertyName(target, FOREGROUND_PROPERTY));
  }

  /**
   * Returns this fixture's <code>{@link Component}</code> casted to the given sub-type.
   * @param <C> enforces that the given type is a sub-type of the managed <code>Component</code>.
   * @param type the type that the managed <code>Component</code> will be casted to.
   * @return this fixture's <code>Component</code> casted to the given sub-type.
   * @throws AssertionError if this fixture's <code>Component</code> is not an instance of the given type.
   */
  public final <C extends T> C targetCastedTo(Class<C> type) {
    assertThat(target).as(format(target)).isInstanceOf(type);
    return type.cast(target);
  }
  
  /**
   * Returns the GUI component in this fixture (same as <code>{@link #target}</code>.)
   * @return the GUI component in this fixture.
   */
  public final T component() {
    return target;
  }
}
