/*
 * Created on Sep 18, 2007
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

import javax.swing.AbstractButton;
import javax.swing.JToggleButton;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a two-state button and verification of the state of such button.
 * @param <T> the specific type of two-state button this fixture can handle.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class TwoStateButtonFixture<T extends AbstractButton> extends ComponentFixture<T> implements TextDisplayFixture {

  private static final String SELECTED_PROPERTY = "selected";
  
  /**
   * Creates a new <code>{@link TwoStateButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param type the type of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public TwoStateButtonFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, type);
  }

  /**
   * Creates a new <code>{@link TwoStateButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param name the name of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public TwoStateButtonFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, name, type);
  }
  
  /**
   * Creates a new <code>{@link TwoStateButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JToggleButton</code>.
   * @param target the <code>JToggleButton</code> to be managed by this fixture.
   */
  public TwoStateButtonFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Verifies that the <code>{@link JToggleButton}</code> managed by this fixture is selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JToggleButton</code> managed by this fixture is not selected.
   */
  protected abstract TwoStateButtonFixture<T> requireSelected();

  /**
   * Verifies that the <code>{@link JToggleButton}</code> managed by this fixture is not selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JToggleButton</code> managed by this fixture is selected.
   */
  protected abstract TwoStateButtonFixture<T> requireNotSelected();
  
  protected final TwoStateButtonFixture<T> assertSelected() {
    assertThat(target.isSelected()).as(formattedPropertyName(SELECTED_PROPERTY)).isTrue();
    return this;
  }
  
  protected final TwoStateButtonFixture<T> assertNotSelected() {
    assertThat(target.isSelected()).as(formattedPropertyName(SELECTED_PROPERTY)).isFalse();
    return this;
  }
}
