/*
 * Created on Mar 16, 2008
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

import javax.swing.table.JTableHeader;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTableHeaderDriver;
import org.fest.swing.exception.LocationUnavailableException;

import static org.fest.swing.fixture.ComponentFixture.*;

/**
 * Understands simulation of user events on a <code>{@link JTableHeader}</code>.
 *
 * @author Yvonne Wang
 */
public class JTableHeaderFixture {

  final JTableHeader target;

  private JTableHeaderDriver driver;

  /**
   * Creates a new </code>{@link JTableHeaderFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTableHeader</code>.
   * @param target the <code>JTableHeader</code> to be managed by this fixture.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>target</code> is <code>null</code>.
   */
  JTableHeaderFixture(Robot robot, JTableHeader target) {
    validate(robot);
    validateTarget(target);
    this.target = target;
    updateDriver(new JTableHeaderDriver(robot));
  }

  final void updateDriver(JTableHeaderDriver newDriver) {
    driver = newDriver;
  }

  /**
   * Simulates a user clicking the column under the given index, in this fixture's <code>{@link JTableHeader}</code>.
   * @param index the index of the column to click.
   * @return this fixture.
   * @throws LocationUnavailableException if the index is out of bounds.
   */
  public JTableHeaderFixture clickColumn(int index) {
    driver.clickColumn(target, index);
    return this;
  }

  /**
   * Simulates a user clicking the column under the given index, in this fixture's <code>{@link JTableHeader}</code>,
   * using the given mouse button, the given number of times.
   * @param index the index of the column to click.
   * @param button the mouse button to use.
   * @param times the number of times to click.
   * @return this fixture.
   * @throws LocationUnavailableException if the index is out of bounds.
   */
  public JTableHeaderFixture clickColumn(int index, MouseButton button, int times) {
    driver.clickColumn(target, index, button, times);
    return this;
  }


  /**
   * Simulates a user clicking the column which name matches the given one, in this fixture's
   * <code>{@link JTableHeader}</code>.
   * @param columnName the column name to match.
   * @return this fixture.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public JTableHeaderFixture clickColumn(String columnName) {
    driver.clickColumn(target, columnName);
    return this;
  }

  /**
   * Simulates a user clicking the column which name matches the given one, in this fixture's
   * <code>{@link JTableHeader}</code>, using the given mouse button, the given number of times.
   * @param columnName the column name to match
   * @param button the mouse button to use.
   * @param times the number of times to click.
   * @return this fixture.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public JTableHeaderFixture clickColumn(String columnName, MouseButton button, int times) {
    driver.clickColumn(target, columnName, button, times);
    return this;
  }
}
