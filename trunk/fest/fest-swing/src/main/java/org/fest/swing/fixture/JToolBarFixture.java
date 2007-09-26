/*
 * Created on Jul 5, 2007
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

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JToolBar;

import abbot.tester.JToolBarTester;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.MouseButton;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JToolBar}</code> and verification of the state of such
 * <code>{@link JToolBar}</code>.
 * 
 * @author Alex Ruiz
 */
public class JToolBarFixture extends ContainerFixture<JToolBar> {

  /**
   * Understands constraints used to unfloat a floating <code>{@link JToolBar}</code>.
   */
  public enum UnfloatConstraint {
    NORTH(BorderLayout.NORTH), EAST(BorderLayout.EAST), SOUTH(BorderLayout.SOUTH), WEST(BorderLayout.WEST);
    
    public final String value;
    
    UnfloatConstraint(String value) {
      this.value = value;
    }
  }
  
  /**
   * Creates a new </code>{@link JToolBarFixture}</code>.
   * @param robot performs simulation of user events on a <code>JToolBar</code>.
   * @param toolbarName the name of the <code>JToolBar</code> to find using the given 
   * <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JToolBar</code> could not be found.
   */
  public JToolBarFixture(RobotFixture robot, String toolbarName) {
    super(robot, toolbarName, JToolBar.class);
  }
  
  /**
   * Creates a new </code>{@link JToolBarFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JToolBar</code>.
   * @param target the <code>JToolBar</code> to be managed by this fixture.
   */
  public JToolBarFixture(RobotFixture robot, JToolBar target) {
    super(robot, target);
  }
  
  /**
   * Simulates a user floating the <code>{@link JToolBar}</code> managed by this fixture.
   * @param point the point where the <code>JToolBar</code> will be floating to.
   * @return this fixture.
   */
  public final JToolBarFixture floatTo(Point point) {
    toolbarTester().actionFloat(target, point.x, point.y);
    return this;
  }

  /**
   * Simulates a user unfloating the <code>{@link JToolBar}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToolBarFixture unfloat() {
    toolbarTester().actionUnfloat(target);
    return this;
  }
  
  public JToolBarFixture unfloat(UnfloatConstraint constraint) {
    toolbarTester().actionUnfloat(target, constraint.value);
    return this;
  }

  protected final JToolBarTester toolbarTester() {
    return (JToolBarTester)tester();
  }

  /**
   * Simulates a user clicking the <code>{@link JToolBar}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToolBarFixture click() {
    return (JToolBarFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JToolBar}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JToolBarFixture click(MouseButton button) {
    return (JToolBarFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JToolBar}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JToolBarFixture click(MouseClickInfo mouseClickInfo) {
    return (JToolBarFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JToolBar}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToolBarFixture rightClick() {
    return (JToolBarFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JToolBar}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToolBarFixture doubleClick() {
    return (JToolBarFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JToolBar}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JToolBarFixture focus() {
    return (JToolBarFixture)doFocus();
  }
  
  /**
   * Simulates a user pressing and releasing the given keys in the <code>{@link JToolBar}</code> managed by this
   * fixture. This method does not affect the current focus.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JToolBarFixture pressAndReleaseKeys(int...keyCodes) {
    return (JToolBarFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on the <code>{@link JToolBar}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JToolBarFixture pressKey(int keyCode) {
    return (JToolBarFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JToolBar}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JToolBarFixture releaseKey(int keyCode) {
    return (JToolBarFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JToolBar</code> is not visible.
   */
  public final JToolBarFixture requireVisible() {
    return (JToolBarFixture)assertVisible();
  }
  
  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JToolBar</code> is visible.
   */
  public final JToolBarFixture requireNotVisible() {
    return (JToolBarFixture)assertNotVisible();
  }
  
  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JToolBar</code> is disabled.
   */
  public final JToolBarFixture requireEnabled() {
    return (JToolBarFixture)assertEnabled();
  }

  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JToolBar</code> is enabled.
   */
  public final JToolBarFixture requireDisabled() {
    return (JToolBarFixture)assertDisabled();
  }
}
