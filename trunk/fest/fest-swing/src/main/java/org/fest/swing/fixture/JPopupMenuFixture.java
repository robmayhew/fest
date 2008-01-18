/*
 * Created on Sep 5, 2007
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

import java.awt.Component;
import java.awt.Container;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import abbot.tester.JPopupMenuTester;

import org.fest.swing.core.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands lookup of <code>{@link JPopupMenu}</code>.
 *
 * @author Yvonne Wang
 */
public class JPopupMenuFixture extends ComponentFixture<JPopupMenu> {
  
  /**
   * Creates a new <code>{@link JPopupMenuFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JPopupMenu</code>.
   * @param target the <code>JPopupMenu</code> to be managed by this fixture.
   */
  public JPopupMenuFixture(RobotFixture robot, JPopupMenu target) {
    super(robot, target);
  }

  /**
   * Finds a <code>{@link JMenuItem}</code>, contained in this fixture's <code>{@link Container}</code>, 
   * which name matches the specified one.
   * @param name the name to match.
   * @return a fixture that manages the <code>JMenuItem</code> found.
   * @throws ComponentLookupException if a <code>JMenuItem</code> having a matching name could not be found.
   * @throws ComponentLookupException if more than one <code>JMenuItem</code> having a matching name is found.
   */
  public final JMenuItemFixture menuItem(String name) {
    return new JMenuItemFixture(robot, findByName(name, JMenuItem.class));
  }

  /**
   * Finds a <code>{@link JMenuItem}</code>, contained in this fixture's <code>{@link Container}</code>, 
   * that matches the specified search criteria.
   * @param matcher contains the search criteria for finding a <code>JMenuItem</code>.
   * @return a fixture that manages the <code>JMenuItem</code> found.
   * @throws ComponentLookupException if a <code>JMenuItem</code> that matches the given search criteria could not be
   *         found.
   * @throws ComponentLookupException if more than one <code>JMenuItem</code> that matches the given search criteria is
   *         found.
   */
  public final JMenuItemFixture menuItem(GenericTypeMatcher<? extends JMenuItem> matcher) {
    return new JMenuItemFixture(robot, find(matcher));
  }

  private <C extends Component> C findByName(String name, Class<C> type) {
    return finder().findByName(target, name, type);
  }
  
  private <C extends Component> C find(GenericTypeMatcher<? extends C> matcher) {
    return finder().find(target, matcher);
  }

  private ComponentFinder finder() { return robot.finder(); }

  /**
   * Returns the contents of this fixture's <code>{@link JPopupMenu}</code>.
   * @return a <code>String</code> array representing the contents of this fixture's <code>JPopupMenu</code>. 
   */
  public final String[] menuLabels() {
    return popupMenuTester().getMenuLabels(target);
  }
  
  protected final JPopupMenuTester popupMenuTester() {
    return (JPopupMenuTester)tester();
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JPopupMenu}</code>.
   * @return this fixture.
   */
  public final JPopupMenuFixture click() {
    return (JPopupMenuFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JPopupMenu}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JPopupMenuFixture click(MouseButton button) {
    return (JPopupMenuFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JPopupMenu}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JPopupMenuFixture click(MouseClickInfo mouseClickInfo) {
    return (JPopupMenuFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JPopupMenu}</code>.
   * @return this fixture.
   */
  public final JPopupMenuFixture rightClick() {
    return (JPopupMenuFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JPopupMenu}</code>.
   * @return this fixture.
   */
  public final JPopupMenuFixture doubleClick() {
    return (JPopupMenuFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JPopupMenu}</code>.
   * @return this fixture.
   */
  public final JPopupMenuFixture focus() {
    return (JPopupMenuFixture)doFocus();
  }
  
  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JPopupMenu}</code>. This 
   * method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JPopupMenuFixture pressAndReleaseKeys(int... keyCodes) {
    return (JPopupMenuFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JPopupMenu}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JPopupMenuFixture pressKey(int keyCode) {
    return (JPopupMenuFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JPopupMenu}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JPopupMenuFixture releaseKey(int keyCode) {
    return (JPopupMenuFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JPopupMenu}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPopupMenu</code> is disabled.
   */
  public final JPopupMenuFixture requireEnabled() {
    return (JPopupMenuFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JPopupMenu}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's <code>JPopupMenu</code> is never enabled.
   */
  public final JPopupMenuFixture requireEnabled(Timeout timeout) {
    return (JPopupMenuFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JPopupMenu}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPopupMenu</code> is enabled.
   */
  public final JPopupMenuFixture requireDisabled() {
    return (JPopupMenuFixture)assertDisabled();
  }

  /**
   * Asserts that this fixture's <code>{@link JPopupMenu}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPopupMenu</code> is not visible.
   */
  public final JPopupMenuFixture requireVisible() {
    return (JPopupMenuFixture)assertVisible();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JPopupMenu}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JPopupMenu</code> is visible.
   */
  public final JPopupMenuFixture requireNotVisible() {
    return (JPopupMenuFixture)assertNotVisible();
  }
}
