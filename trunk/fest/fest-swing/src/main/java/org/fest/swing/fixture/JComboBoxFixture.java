/*
 * Created on Apr 9, 2007
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

import javax.swing.JComboBox;
import javax.swing.JList;

import abbot.tester.JComboBoxTester;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;

/**
 * Understands simulation of user events on a <code>{@link JComboBox}</code> and verification of the state of such
 * <code>{@link JComboBox}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxFixture extends ComponentFixture<JComboBox> implements ItemGroupFixture<JComboBox> {

  /**
   * Creates a new </code>{@link JComboBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JComboBox</code>.
   * @param comboBoxName the name of the <code>JComboBox</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JComboBox</code> could not be found.
   */
  public JComboBoxFixture(RobotFixture robot, String comboBoxName) {
    super(robot, comboBoxName, JComboBox.class);
  }
  
  /**
   * Creates a new </code>{@link JComboBoxFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JComboBox</code>.
   * @param target the <code>JComboBox</code> to be managed by this fixture.
   */
  public JComboBoxFixture(RobotFixture robot, JComboBox target) {
    super(robot, target);
  }
  
  /**
   * Returns the elements in the <code>{@link JComboBox}</code> managed by this fixture as <code>String</code>s.
   * @return the elements in the managed <code>JComboBox</code>.
   */
  public final String[] contents() {
    return comboBoxTester().getContents(target);
  }
  
  /**
   * Simulates a user selecting an item in the <code>{@link JComboBox}</code> managed by this fixture. 
   * @param index the index of the item to select.
   * @return this fixture.
   */
  public final JComboBoxFixture selectItem(int index) {
    comboBoxTester().actionSelectIndex(target, index);
    return this;
  }

  /**
   * Simulates a user selecting an item in the <code>{@link JComboBox}</code> managed by this fixture. 
   * @param text the text of the item to select.
   * @return this fixture.
   */
  public final JComboBoxFixture selectItem(String text) {
    comboBoxTester().actionSelectItem(target, text);
    return this;
  }

  /**
   * Returns the <code>String</code> representation of an item in the <code>{@link JComboBox}</code> managed by this 
   * fixture. If such <code>String</code> representation is not meaningful, this method will return <code>null</code>.
   * @param index the index of the item to return.
   * @return the String reprentation of the item under the given index, or <code>null</code> if nothing meaningful.
   */
  public String valueAt(int index) {
    Object value = target.getItemAt(index);
    return comboBoxTester().getValueAsString(target, list(), value, index);
  }
  
  /** 
   * Finds and returns the {@link JList} in the popup raised by the <code>{@link JComboBox}</code> managed by this 
   * fixture.
   * @return the <code>JList</code> in the popup raised by the managed <code>JComboBox</code>. 
   */
  public JList list() {
    target.showPopup();
    return comboBoxTester().findComboList(target);
  }
  
  /**
   * Simulates a user entering the specified text in the <code>{@link JComboBox}</code> managed by this fixture only
   * if it is editable.
   * @param text the text to enter.
   * @return this fixture.
   */
  public final JComboBoxFixture enterText(String text) {
    if (!target.isEditable()) return this;
    focus();
    tester().actionKeyString(text);
    return this;
  }

  private JComboBoxTester comboBoxTester() {
    return (JComboBoxTester)tester();
  }

  /**
   * Gives input focus to the <code>{@link JComboBox}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JComboBoxFixture focus() {
    return (JComboBoxFixture)doFocus();
  }

  /**
   * Simulates a user clicking the <code>{@link JComboBox}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JComboBoxFixture click() {
    return (JComboBoxFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JComboBox}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JComboBoxFixture click(MouseClickInfo mouseClickInfo) {
    return (JComboBoxFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user clicking the <code>{@link JComboBox}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JComboBoxFixture click(MouseButton button) {
    return (JComboBoxFixture)doClick(button);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JComboBox}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JComboBoxFixture rightClick() {
    return (JComboBoxFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JComboBox}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JComboBoxFixture doubleClick() {
    return (JComboBoxFixture)doDoubleClick();
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JComboBox}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JComboBoxFixture pressAndReleaseKeys(int... keyCodes) {
    return (JComboBoxFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on the <code>{@link JComboBox}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JComboBoxFixture pressKey(int keyCode) {
    return (JComboBoxFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JComboBox}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JComboBoxFixture releaseKey(int keyCode) {
    return (JComboBoxFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JComboBox}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JComboBox</code> is not visible.
   */
  public final JComboBoxFixture requireVisible() {
    return (JComboBoxFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JComboBox}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JComboBox</code> is visible.
   */
  public final JComboBoxFixture requireNotVisible() {
    return (JComboBoxFixture)assertNotVisible();
  }
  
  /**
   * Asserts that the <code>{@link JComboBox}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JComboBox</code> is disabled.
   */
  public final JComboBoxFixture requireEnabled() {
    return (JComboBoxFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JComboBox}</code> managed by this fixture is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if the managed <code>JComboBox</code> is never enabled.
   */
  public final JComboBoxFixture requireEnabled(Timeout timeout) {
    return (JComboBoxFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that the <code>{@link JComboBox}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JComboBox</code> is enabled.
   */
  public final JComboBoxFixture requireDisabled() {
    return (JComboBoxFixture)assertDisabled();
  }
}
