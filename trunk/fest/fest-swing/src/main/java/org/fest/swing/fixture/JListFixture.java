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

import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JList}</code> and output verification.
 *
 * @author Alex Ruiz
 */
public class JListFixture extends ComponentFixture<JList> implements ItemGroupFixture<JList> {

  /**
   * Creates a new </code>{@link JListFixture}</code>.
   * @param robot performs simulation of user events on a <code>JList</code>.
   * @param listName the name of the list to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public JListFixture(RobotFixture robot, String listName) {
    super(robot, listName, JList.class);
  }
  
  /**
   * Creates a new </code>{@link JListFixture}</code>.
   * @param robot performs simulation of user events on the given list.
   * @param target the target list.
   */
  public JListFixture(RobotFixture robot, JList target) {
    super(robot, target);
  }

  /** ${@inheritDoc} */
  public String[] contents() {
    return listTester().getContents(target);
  }
  
  /** ${@inheritDoc} */
  public final JListFixture selectItemAt(int index) {
    listTester().actionSelectIndex(target, index);
    return this;
  }

  /** ${@inheritDoc} */
  public final JListFixture selectItemWithText(String text) {
    listTester().actionSelectValue(target, text);
    return null;
  }

  /** ${@inheritDoc} */
  public String valueAt(int index) {
    return JListTester.valueToString(target, index);
  }

  private JListTester listTester() {
    return testerCastedTo(JListTester.class);
  }

  /** ${@inheritDoc} */
  @Override public final JListFixture click() {
    return (JListFixture)super.click();
  }

  /** ${@inheritDoc} */
  @Override public final JListFixture focus() {
    return (JListFixture)super.focus();
  }

  /** ${@inheritDoc} */
  @Override public final JListFixture requireDisabled() {
    return (JListFixture)super.requireDisabled();
  }

  /** ${@inheritDoc} */
  @Override public final JListFixture requireEnabled() {
    return (JListFixture)super.requireEnabled();
  }

  /** ${@inheritDoc} */
  @Override public final JListFixture requireNotVisible() {
    return (JListFixture)super.requireNotVisible();
  }

  /** ${@inheritDoc} */
  @Override public final JListFixture requireVisible() {
    return (JListFixture)super.requireVisible();
  }
}
