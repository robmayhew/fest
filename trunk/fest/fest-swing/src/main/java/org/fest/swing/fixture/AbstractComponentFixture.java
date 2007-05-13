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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;

import abbot.tester.ComponentTester;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.RobotFixture;


/**
 * Template for implementations of <code>{@link ComponentFixture}</code>
 * @param <T> the type of component handled by this fixture. 
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class AbstractComponentFixture<T extends Component> implements ComponentFixture<T> {

  /** Performs simulation of user events on <code>{@link #target}</code> */
  public final RobotFixture robot;
  
  /** The target component. */
  public final T target;
  
  /**
   * Creates a new </code>{@link AbstractComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param type the type of <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @see RobotFixture#findByType(Class)
   */
  public AbstractComponentFixture(RobotFixture robot, Class<? extends T> type) {
    this(robot, robot.findByType(type));
  }

  /**
   * Creates a new </code>{@link AbstractComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param name the name of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @see RobotFixture#findByName(String, Class)
   */
  public AbstractComponentFixture(RobotFixture robot, String name, Class<? extends T> type) {
    this(robot, robot.findByName(name, type));
  }
  
  /**
   * Creates a new </code>{@link AbstractComponentFixture}</code>.
   * @param robot performs simulation of user events on the given component.
   * @param target the component under test.
   */
  public AbstractComponentFixture(RobotFixture robot, T target) {
    this.robot = robot;
    this.target = target;
  }
  
  /**
   * Returns the target component of this fixture casted to the given type.
   * @param <C> enforces the given type to be a subsubtype of the target component.
   * @param type the type the target component will be casted to.
   * @return the target component of this fixture casted to the given type.
   */
  public final <C extends T> C targetCastedTo(Class<C> type) {
    assertThat(target).isInstanceOf(type);
    return type.cast(target);
  }
  
  /**
   * Simulates a user clicking the target component.
   * @throws AssertionError if the given fixture is not <code>this</code>.
   */
  protected final void doClick() {
    doFocus();
    tester().actionClick(target);
  }

  /**
   * Returns a <code>{@link ComponentTester}</code> casted to the given type.
   * @param <C> indicates that the <code>ComponentTester</code> to return should be casted to one of its subclasses.
   * @param type the type that the <code>ComponentTester</code> should be casted to.
   * @return a <code>ComponentTester</code> casted to the given type.
   */
  protected final <C extends ComponentTester> C testerCastedTo(Class<C> type) {
    ComponentTester tester = tester();
    assertThat(tester).isInstanceOf(type);
    return type.cast(tester);
  }
  
  /**
   * Simulates a user pressing the given keys on the managed text component.
   * @param keyCodes one or more codes of the keys to press.
   */
  protected final void doPressKeys(int...keyCodes) {
    doFocus();
    ComponentTester tester = tester();
    for (int keyCode : keyCodes) tester.actionKeyPress(keyCode);
  }
  
  /**
   * Gives the keyboard focus to the target component.
   */
  protected final void doFocus() {
    robot.focus(target);
  }
  
  /**
   * Asserts that the target component is visible.
   * @throws AssertionError if the target component is not visible.
   */
  protected final void assertIsVisible() {
    assertThat(target.isVisible()).isTrue();
  }

  /**
   * Asserts that the target component is not visible.
   * @throws AssertionError if the target component is visible.
   */
  protected final void assertIsNotVisible() {
    assertThat(target.isVisible()).isFalse();
  }

  /** @return a tester for the target component */
  protected final ComponentTester tester() { return ComponentTester.getTester(target); }
}
