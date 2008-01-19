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

import abbot.tester.ComponentLocation;
import abbot.tester.JTreeLocation;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JTreeDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link JTree}</code> and verification of the state of such
 * <code>{@link JTree}</code>.
 * 
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JTreeFixture extends ComponentFixture<JTree> {

  private final JTreeDriver treeDriver;
  
  /**
   * Creates a new <code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTree</code>.
   * @param treeName the name of the <code>JTree</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTree</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JTree</code> is found.
   */
  public JTreeFixture(RobotFixture robot, String treeName) {
    super(robot, treeName, JTree.class);
    treeDriver = treeDriver();
  }

  /**
   * Creates a new <code>{@link JTreeFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTree</code>.
   * @param target the <code>JTree</code> to be managed by this fixture.
   */
  public JTreeFixture(RobotFixture robot, JTree target) {
    super(robot, target);
    treeDriver = treeDriver();
  }

  private JTreeDriver treeDriver() {
    return new JTreeDriver(robot, target);
  }
  
  /**
   * Simulates a user selecting the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   */
  public final JTreeFixture selectRow(int row) {
    treeDriver.selectRow(row);
    return this;
  }

  /**
   * Simulates a user toggling the open/closed state of the tree node at the given row.
   * @param row the index of the row to select.
   * @return this fixture.
   */
  public final JTreeFixture toggleRow(int row) {
    treeDriver.toggleRow(row);
    return this;
  }

  /**
   * Select the given path, expanding parent nodes if necessary. TreePath must consist of usable String representations
   * that can be used in later comparisons. The default &lt;classname&gt;@&lt;hashcode&gt; returned by
   * <code>{@link Object#toString()}</code> is not usable; if that is all that is available, refer to the row number 
   * instead.
   * @param treePath A path comprising an array of Strings that match the toString()'s of the path nodes
   * @return this fixture.
   */
  public final JTreeFixture selectPath(TreePath treePath) {
    treeDriver.selectPath(treePath);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public final JTreeFixture click() {
    return (JTreeFixture)doClick(); 
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JTreeFixture click(MouseButton button) {
    return (JTreeFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTree}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JTreeFixture click(MouseClickInfo mouseClickInfo) {
    return (JTreeFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public final JTreeFixture rightClick() {
    return (JTreeFixture)doRightClick();
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public final JTreeFixture doubleClick() {
    return (JTreeFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JTree}</code>.
   * @return this fixture.
   */
  public final JTreeFixture focus() {
    return (JTreeFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys in this fixture's <code>{@link JTree}</code>.
   * This method does not affect the current focus.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTreeFixture pressAndReleaseKeys(int...keyCodes) {
    return (JTreeFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JTree}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTreeFixture pressKey(int keyCode) {
    return (JTreeFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JTree}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTreeFixture releaseKey(int keyCode) {
    return (JTreeFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is not visible.
   */
  public final JTreeFixture requireVisible() {
    return (JTreeFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is visible.
   */
  public final JTreeFixture requireNotVisible() {
    return (JTreeFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is disabled.
   */
  public final JTreeFixture requireEnabled() {
    return (JTreeFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JTree</code> is never enabled.
   */
  public final JTreeFixture requireEnabled(Timeout timeout) {
    return (JTreeFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JTree}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JTree</code> is enabled.
   */
  public final JTreeFixture requireDisabled() {
    return (JTreeFixture)assertDisabled();
  }

  /**
   * Simulates a user dragging an item from this fixture's <code>{@link JTree}</code>.
   * @param treePath the tree path corresponding to the item to drag.
   * @return this fixture.
   */
  public final JTreeFixture drag(TreePath treePath) {
    selectPath(treePath);
    tester().actionDrag(target, elementLocation(treePath));
    return this;
  }
  
  /**
   * Simulates a user dropping an item to this fixture's <code>{@link JTree}</code>.
   * @param treePath the tree path corresponding to the item to drop.
   * @return this fixture.
   */
  public final JTreeFixture drop(TreePath treePath) {
    tester().actionDrop(target, elementLocation(treePath));
    return this;
  }
  
  private ComponentLocation elementLocation(TreePath treePath) {
    return new ComponentLocation(new JTreeLocation(treePath).getPoint(target));
  }

  /**
   * Simulates a user dragging a row from this fixture's <code>{@link JTree}</code>.
   * @param row the index of the row to drag.
   * @return this fixture.
   */
  public JTreeFixture drag(int row) {
    tester().actionDrag(target, elementLocation(row));
    return this;
  }

  /**
   * Simulates a user dropping an item into this fixture's <code>{@link JTree}</code>.
   * @param row the row to drop the item to.
   * @return this fixture.
   */
  public JTreeFixture drop(int row) {
    tester().actionDrop(target, elementLocation(row));
    return this;
  }
  
  private ComponentLocation elementLocation(int index) {
    return new ComponentLocation(new JTreeLocation(index).getPoint(target));
  }
}