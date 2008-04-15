/*
 * Created on May 21, 2007
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

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.cell.JTreeCellValueReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JTreeDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Understands simulation of user events on a <code>{@link JTree}</code> and verification of the state of such
 * <code>{@link JTree}</code>.
 *
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JTreeFixture extends JPopupMenuInvokerFixture<JTree> {

  private JTreeDriver driver;

  /**
   * Creates a new <code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTree</code>.
   * @param target the <code>JTree</code> to be managed by this fixture.
   */
  public JTreeFixture(Robot robot, JTree target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTree</code>.
   * @param treeName the name of the <code>JTree</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTree</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JTree</code> is found.
   */
  public JTreeFixture(Robot robot, String treeName) {
    super(robot, treeName, JTree.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JTreeDriver(robot));
  }

  void updateDriver(JTreeDriver driver) {
    this.driver = driver;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public JTreeFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JTreeFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JTreeFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public JTreeFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public JTreeFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Simulates a user dragging a row from this fixture's <code>{@link JTree}</code>.
   * @param row the index of the row to drag.
   * @return this fixture.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public JTreeFixture drag(int row) {
    driver.drag(target, row);
    return this;
  }

  /**
   * Simulates a user dragging an item from this fixture's <code>{@link JTree}</code>.
   * @param treePath the tree path corresponding to the item to drag.
   * @return this fixture.
   * @throws LocationUnavailableException if any part of the path is not visible.
   */
  public JTreeFixture drag(String treePath) {
    driver.drag(target, treePath);
    return this;
  }

  /**
   * Simulates a user dropping an item into this fixture's <code>{@link JTree}</code>.
   * @param row the row to drop the item to.
   * @return this fixture.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public JTreeFixture drop(int row) {
    driver.drop(target, row);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's <code>{@link JTree}</code>.
   * @param treePath the tree path corresponding to the item to drop.
   * @return this fixture.
   * @throws LocationUnavailableException if any part of the path is not visible.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public JTreeFixture drop(String treePath) {
    driver.drop(target, treePath);
    return this;
  }

  /**
   * Select the given path, expanding parent nodes if necessary. TreePath must consist of usable String representations
   * that can be used in later comparisons. The default &lt;classname&gt;@&lt;hashcode&gt; returned by
   * <code>{@link Object#toString()}</code> is not usable; if that is all that is available, refer to the row number
   * instead.
   * @param treePath A path comprising an array of Strings that match the toString()'s of the path nodes
   * @return this fixture.
   * @throws LocationUnavailableException if any part of the path is not visible.
   */
  public JTreeFixture selectPath(String treePath) {
    driver.selectPath(target, treePath);
    return this;
  }

  /**
   * Simulates a user selecting the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public JTreeFixture selectRow(int row) {
    driver.selectRow(target, row);
    return this;
  }

  /**
   * Simulates a user toggling the open/closed state of the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   * @throws ActionFailedException if the given row is less than zero or equal than or greater than the number of
   *         visible rows in the <code>JTree</code>.
   * @throws LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public JTreeFixture toggleRow(int row) {
    driver.toggleRow(target, row);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public JTreeFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys in this fixture's <code>{@link JTree}</code>.
   * This method does not affect the current focus.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTreeFixture pressAndReleaseKeys(int...keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JTree}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTreeFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JTree}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTreeFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is enabled.
   */
  public JTreeFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is disabled.
   */
  public JTreeFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JTree</code> is never enabled.
   */
  public JTreeFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is visible.
   */
  public JTreeFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is not visible.
   */
  public JTreeFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is editable.
   * @throws AssertionError if this fixture's <code>JTree</code> is not editable.
   * @return this fixture. 
   */
  public JTreeFixture requireEditable() {
    driver.requireEditable(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is not editable.
   * @throws AssertionError if this fixture's <code>JTree</code> is editable.
   * @return this fixture. 
   */
  public JTreeFixture requireNotEditable() {
    driver.requireNotEditable(target);
    return this;
  }

  /**
   * Updates the separator to use when specifying <code>{@link TreePath}</code>s as <code>String</code>s.
   * @param separator the new separator.
   */
  public void separator(String separator) {
    driver.separator(separator);
  }
  
  /**
   * Updates the implementation of <code>{@link JTreeCellValueReader}</code> to use when comparing internal values of a
   * <code>{@link JTree}</code> and the values expected in a test.
   * @param cellReader the new <code>JTreeCellValueReader</code> to use.
   */
  public void cellReader(JTreeCellValueReader cellReader) {
    driver.cellReader(cellReader);
  }
}