/*
 * Created on Jun 12, 2007
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

import abbot.tester.ComponentLocation;
import abbot.tester.JListLocation;
import abbot.tester.JListTester;
import org.fest.swing.ComponentLookupException;
import org.fest.swing.MouseButton;
import org.fest.swing.RobotFixture;

import javax.swing.*;

/**
 * Understands simulation of user events on a <code>{@link JList}</code> and verification of the state of such
 * <code>{@link JList}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JListFixture extends ComponentFixture<JList> implements ItemGroupFixture<JList> {

  /**
   * Creates a new </code>{@link JListFixture}</code>.
   * @param robot performs simulation of user events on a <code>JList</code>.
   * @param listName the name of the <code>JList</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JList</code> could not be found.
   */
  public JListFixture(RobotFixture robot, String listName) {
    super(robot, listName, JList.class);
  }
  
  /**
   * Creates a new </code>{@link JListFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JList</code>.
   * @param target the <code>JList</code> to be managed by this fixture.
   */
  public JListFixture(RobotFixture robot, JList target) {
    super(robot, target);
  }

  /**
   * Returns the elements in the <code>{@link JList}</code> managed by this fixture as <code>String</code>s.
   * @return the elements in the managed <code>JList</code>.
   */
  public String[] contents() {
    return listTester().getContents(target);
  }
  
  /**
   * Simulates a user selecting an item in the <code>{@link JList}</code> managed by this fixture. 
   * @param index the index of the item to select.
   * @return this fixture.
   */
  public final JListFixture selectItem(int index) {
    listTester().actionSelectIndex(target, index);
    return this;
  }

  /**
   * Simulates a user selecting an item in the <code>{@link JList}</code> managed by this fixture. 
   * @param text the text of the item to select.
   * @return this fixture.
   */
  public final JListFixture selectItem(String text) {
    listTester().actionSelectValue(target, text);
    return null;
  }

  /**
   * Returns the <code>String</code> representation of an item in the <code>{@link JList}</code> managed by this 
   * fixture. If such <code>String</code> representation is not meaningful, this method will return <code>null</code>.
   * @param index the index of the item to return.
   * @return the String reprentation of the item under the given index, or <code>null</code> if nothing meaningful.
   */
  public String valueAt(int index) {
    return JListTester.valueToString(target, index);
  }

  private JListTester listTester() {
    return (JListTester)tester();
  }

  /**
   * Simulates a user clicking the <code>{@link JList}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JListFixture click() {
    return (JListFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JList}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JListFixture click(MouseButton button) {
    return (JListFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JList}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JListFixture click(MouseClickInfo mouseClickInfo) {
    return (JListFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JList}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JListFixture rightClick() {
    return (JListFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JList}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JListFixture doubleClick() {
    return (JListFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JList}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JListFixture focus() {
    return (JListFixture)doFocus();
  }
  
  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JList}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListFixture pressAndReleaseKeys(int... keyCodes) {
    return (JListFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on the <code>{@link JList}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListFixture pressKey(int keyCode) {
    return (JListFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JList}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JListFixture releaseKey(int keyCode) {
    return (JListFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JList</code> is not visible.
   */
  public final JListFixture requireVisible() {
    return (JListFixture)assertVisible();
  }
  
  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JList</code> is visible.
   */
  public final JListFixture requireNotVisible() {
    return (JListFixture)assertNotVisible();
  }
  
  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JList</code> is disabled.
   */
  public final JListFixture requireEnabled() {
    return (JListFixture)assertEnabled();
  }

  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JList</code> is enabled.
   */
  public final JListFixture requireDisabled() {
    return (JListFixture)assertDisabled();
  }

  /**
   * Simulates a user dragging an item from the <code>{@link JList}</code> managed by this fixture.
   * @param text the text of the item to drag.
   * @return this fixture.
   */
  public final JListFixture drag(String text) {
    tester().actionDrag(target, elementLocation(text));
    return this;
  }

  /**
   * Simulates a user dropping an item to the <code>{@link JList}</code> managed by this fixture.
   * @param text the text of the item to drop.
   * @return this fixture.
   */
  public final JListFixture drop(String text) {
    tester().actionDrop(target, elementLocation(text));
    return this;
  }  

  private ComponentLocation elementLocation(String text) {
    return new ComponentLocation(new JListLocation(text).getPoint(target));
  }

  public final JListFixture drop() {
    tester().actionDrop(target, new ComponentLocation(new JListLocation().getPoint(target)));
    return this;
  }
  
  /**
   * Simulates a user dragging an item from the <code>{@link JList}</code> managed by this fixture.
   * @param index the index of the item to drag.
   * @return this fixture.
   */
  public final JListFixture drag(int index) {
    tester().actionDrag(target, elementLocation(index));
    return this;
  }

  /**
   * Simulates a user dropping an item to the <code>{@link JList}</code> managed by this fixture.
   * @param index the index of the item to drop.
   * @return this fixture.
   */
  public final JListFixture drop(int index) {
    tester().actionDrop(target, elementLocation(index));
    return this;
  }
  
  private ComponentLocation elementLocation(int index) {
    return new ComponentLocation(new JListLocation(index).getPoint(target));
  }
}
