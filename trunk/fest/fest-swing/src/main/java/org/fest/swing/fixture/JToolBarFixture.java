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
  @Override public final JToolBarFixture click() {
    return (JToolBarFixture)doClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JToolBar}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JToolBarFixture doubleClick() {
    return (JToolBarFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JToolBar}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JToolBarFixture focus() {
    return (JToolBarFixture)doFocus();
  }
  
  /**
   * Simulates a user pressing the given keys in the <code>{@link JToolBar}</code> managed by this fixture.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JToolBarFixture pressKeys(int...keyCodes) {
    return (JToolBarFixture)doPressKeys(keyCodes);
  }

  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JToolBar</code> is not visible.
   */
  @Override public final JToolBarFixture requireVisible() {
    return (JToolBarFixture)assertVisible();
  }
  
  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JToolBar</code> is visible.
   */
  @Override public final JToolBarFixture requireNotVisible() {
    return (JToolBarFixture)assertNotVisible();
  }
  
  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JToolBar</code> is disabled.
   */
  @Override public final JToolBarFixture requireEnabled() {
    return (JToolBarFixture)assertEnabled();
  }

  /**
   * Asserts that the <code>{@link JToolBar}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JToolBar</code> is enabled.
   */
  @Override public final JToolBarFixture requireDisabled() {
    return (JToolBarFixture)assertDisabled();
  }
}
