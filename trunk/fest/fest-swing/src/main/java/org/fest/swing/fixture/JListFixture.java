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

import javax.swing.JList;

import abbot.tester.JListTester;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JList}</code> and verification of the state of such
 * <code>{@link JList}</code>.
 *
 * @author Alex Ruiz
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
  public final JListFixture selectItemAt(int index) {
    listTester().actionSelectIndex(target, index);
    return this;
  }

  /**
   * Simulates a user selecting an item in the <code>{@link JList}</code> managed by this fixture. 
   * @param text the text of the item to select.
   * @return this fixture.
   */
  public final JListFixture selectItemWithText(String text) {
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
    return testerCastedTo(JListTester.class);
  }

  /**
   * Simulates a user clicking the <code>{@link JList}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JListFixture click() {
    return (JListFixture)super.click();
  }

  /**
   * Gives input focus to the <code>{@link JList}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JListFixture focus() {
    return (JListFixture)super.focus();
  }
  
  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JList</code> is not visible.
   */
  @Override public final JListFixture requireVisible() {
    return (JListFixture)super.requireVisible();
  }
  
  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JList</code> is visible.
   */
  @Override public final JListFixture requireNotVisible() {
    return (JListFixture)super.requireNotVisible();
  }
  
  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JList</code> is disabled.
   */
  @Override public final JListFixture requireEnabled() {
    return (JListFixture)super.requireEnabled();
  }

  /**
   * Asserts that the <code>{@link JList}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JList</code> is enabled.
   */
  @Override public final JListFixture requireDisabled() {
    return (JListFixture)super.requireDisabled();
  }
}
