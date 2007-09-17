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

import abbot.tester.ComponentLocation;
import abbot.tester.ComponentTester;
import static java.awt.event.InputEvent.BUTTON1_MASK;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.util.Formatting.format;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link Component}</code> and verification of the state of such
 * <code>{@link Component}</code>.
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
  
  private final ComponentTester tester;
  
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
    tester = ComponentTester.getTester(target);
  }
  
  /**
   * Returns the <code>{@link Component}</code> managed by this fixture casted to the given subtype.
   * @param <C> enforces that the given type is a subsubtype of the managed <code>Component</code>.
   * @param type the type that the managed <code>Component</code> will be casted to.
   * @return the <code>Component</code> managed by this fixture casted to the given subtype.
   * @throws AssertionError if the <code>Component</code> managed by this fixture is not an instance of the given type.
   */
  public final <C extends T> C targetCastedTo(Class<C> type) {
    assertThat(target).as(formattedTarget()).isInstanceOf(type);
    return type.cast(target);
  }

  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  public abstract ComponentFixture<T> click();

  /**
   * Simulates a user doble-clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  public abstract ComponentFixture<T> doubleClick();

  /**
   * Gives input focus to the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  public abstract ComponentFixture<T> focus();

  /**
   * Simulates a user pressing the given keys on the <code>{@link Component}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public abstract ComponentFixture<T> pressKeys(int...keyCodes);

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is not visible.
   */
  public abstract ComponentFixture<T> requireVisible();

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is visible.
   */
  public abstract ComponentFixture<T> requireNotVisible();

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is disabled.
   */
  public abstract ComponentFixture<T> requireEnabled();

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is enabled.
   */
  public abstract ComponentFixture<T> requireDisabled();

  /**
   * Shows a popup menu using the <code>{@link Component}</code> managed by this fixture as the invoker of the popup 
   * menu.
   * @return a fixture that manages the displayed popup menu.
   * @throws ComponentLookupException if a popup menu cannot be found.
   */
  public final JPopupMenuFixture showPopupMenu() {
    return new JPopupMenuFixture(robot, robot.showPopupMenu(target));
  }
  
  /**
   * Simulates a user clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doClick() {
    doFocus();
    tester().actionClick(target);
    return this;
  }

  /**
   * Simulates a user doble-clicking the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doDoubleClick() {
    doFocus();
    tester().actionClick(target, new ComponentLocation(), BUTTON1_MASK, 2);
    return this;
  }

  /**
   * Simulates a user pressing the given keys on the <code>{@link Component}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  protected final ComponentFixture<T> doPressKeys(int... keyCodes) {
    doFocus();
    ComponentTester tester = tester();
    for (int keyCode : keyCodes) tester.actionKeyPress(keyCode);
    return this;
  }

  /**
   * Gives input focus to the <code>{@link Component}</code> managed by this fixture.
   * @return this fixture.
   */
  protected final ComponentFixture<T> doFocus() {
    robot.focus(target);
    return this;
  }
  
  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is not visible.
   */
  protected final ComponentFixture<T> assertVisible() {
    assertThat(target.isVisible()).as(visibleProperty()).isTrue();
    return this;
  }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is visible.
   */
  protected final ComponentFixture<T> assertNotVisible() {
    assertThat(target.isVisible()).as(visibleProperty()).isFalse();
    return this;
  }

  private String visibleProperty() { return formattedPropertyName("visible"); }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is disabled.
   */
  protected final ComponentFixture<T> assertEnabled() {
    assertThat(target.isEnabled()).as(enabledProperty()).isTrue();
    return this;
  }

  /**
   * Asserts that the <code>{@link Component}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is enabled.
   */
  protected final ComponentFixture<T> assertDisabled() {
    assertThat(target.isEnabled()).as(enabledProperty()).isFalse();
    return this;
  }

  private String enabledProperty() { return formattedPropertyName("enabled"); }

  protected final String formattedPropertyName(String propertyName) {
    return concat(formattedTarget(), " - property:", quote(propertyName));
  }

  protected String formattedTarget() {
    return format(target);
  }

  /** @return a tester for the target component */
  protected final ComponentTester tester() { return tester; }
}
