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

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Simulates user events on a given <code>{@link Component}</code> and verifies the state of 
 * such <code>{@link Component}</code>.
 * @param <T> the type of <code>{@link Component}</code> that this fixture can manage. 
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ComponentFixture<T extends Component> {

  /** Performs simulation of user events on <code>{@link #target}</code> */
  public final RobotFixture robot;
  
  /** The <code>{@link Component}</code> managed by this fixture. */
  public final T target;
  
  /**
   * Creates a new </code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public ComponentFixture(RobotFixture robot, Class<? extends T> type) {
    this(robot, robot.finder().findByType(type));
  }

  /**
   * Creates a new </code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>Component</code>.
   * @param name the name of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>Component</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public ComponentFixture(RobotFixture robot, String name, Class<? extends T> type) {
    this(robot, robot.finder().findByName(name, type));
  }
  
  /**
   * Creates a new </code>{@link ComponentFixture}</code>.
   * @param robot performs simulation of user events on the given <code>Component</code>.
   * @param target the <code>Component</code> to be managed by this fixture.
   */
  public ComponentFixture(RobotFixture robot, T target) {
    this.robot = robot;
    this.target = target;
  }
  
  /**
   * Returns the <code>{@link Component}</code> managed of this fixture casted to the given subtype.
   * @param <C> enforces that the given type is a subsubtype of the managed <code>Component</code>.
   * @param type the type that the managed <code>Component</code> will be casted to.
   * @return the <code>Component</code> managed by this fixture casted to the given subtype.
   * @throws AssertionError if the <code>Component</code> managed by this fixture is not an instance of the given type.
   */
  public final <C extends T> C targetCastedTo(Class<C> type) {
    assertThat(target).isInstanceOf(type);
    return type.cast(target);
  }

  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  public ComponentFixture<T> click() {
    focus();
    tester().actionClick(target);
    return this;
  }
  
  /**
   * Gives input focus to the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  public ComponentFixture<T> focus() {
    robot.focus(target);
    return this;
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
   * Simulates a user pressing the given keys on the <code>{@link Component}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @see java.awt.event.KeyEvent
   */
  protected final void doPressKeys(int...keyCodes) {
    focus();
    ComponentTester tester = tester();
    for (int keyCode : keyCodes) tester.actionKeyPress(keyCode);
  }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is not visible.
   */
  public ComponentFixture<T> requireVisible() {
    assertThat(target.isVisible()).isTrue();
    return this;
  }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is visible.
   */
  public ComponentFixture<T> requireNotVisible() {
    assertThat(target.isVisible()).isFalse();
    return this;
  }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is disabled.
   */
  public ComponentFixture<T> requireEnabled() {
    assertThat(target.isEnabled()).isTrue();
    return this;
  }
  
  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is enabled.
   */
  public ComponentFixture<T> requireDisabled() {
    assertThat(target.isEnabled()).isFalse();
    return this;
  }

  /** @return a tester for the target component */
  protected final ComponentTester tester() { return ComponentTester.getTester(target); }
}
