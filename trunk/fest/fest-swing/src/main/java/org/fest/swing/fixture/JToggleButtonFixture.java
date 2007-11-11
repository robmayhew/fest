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

import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

import javax.swing.*;

/**
 * Understands simulation of user events on a <code>{@link JButton}</code> and verification of the state of such 
 * <code>{@link JButton}</code>.
 * @param <T> 
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class JToggleButtonFixture<T extends JToggleButton> extends ComponentFixture<T> implements TextDisplayFixture<T> {

  private static final String SELECTED_PROPERTY = "selected";
  
  /**
   * Creates a new </code>{@link JToggleButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param type the type of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public JToggleButtonFixture(RobotFixture robot, Class<? extends T> type) {
    super(robot, type);
  }

  /**
   * Creates a new </code>{@link JToggleButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToggleButton</code>.
   * @param name the name of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @param type the type of the <code>JToggleButton</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching component could not be found.
   */
  public JToggleButtonFixture(RobotFixture robot, String name, Class<? extends T> type) {
    super(robot, name, type);
  }
  
  /**
   * Creates a new </code>{@link JToggleButtonFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JToggleButton</code>.
   * @param target the <code>JToggleButton</code> to be managed by this fixture.
   */
  public JToggleButtonFixture(RobotFixture robot, T target) {
    super(robot, target);
  }

  /**
   * Verifies that the <code>{@link JToggleButton}</code> managed by this fixture is selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JToggleButton</code> managed by this fixture is not selected.
   */
  protected abstract JToggleButtonFixture<T> requireSelected(); 

  /**
   * Verifies that the <code>{@link JToggleButton}</code> managed by this fixture is not selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JToggleButton</code> managed by this fixture is selected.
   */
  protected abstract JToggleButtonFixture<T> requireNotSelected();
  
  protected final JToggleButtonFixture<T> assertSelected() {
    assertThat(target.isSelected()).as(formattedPropertyName(SELECTED_PROPERTY)).isTrue();
    return this;
  }
  
  protected final JToggleButtonFixture<T> assertNotSelected() {
    assertThat(target.isSelected()).as(formattedPropertyName(SELECTED_PROPERTY)).isFalse();
    return this;
  }
}
