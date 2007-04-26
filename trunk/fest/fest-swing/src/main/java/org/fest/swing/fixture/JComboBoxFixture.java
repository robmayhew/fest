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

import org.fest.swing.RobotFixture;


import abbot.tester.JComboBoxTester;


/**
 * Understands simulation of user events on a <code>{@link JComboBox}</code> and output verification.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxFixture extends AbstractComponentFixture<JComboBox> {
  
  /**
   * Creates a new </code>{@link JComboBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JComboBox</code>.
   * @param comboBoxName the name of the combo box to find using the given <code>RobotFixture</code>.
   * @see RobotFixture#findByName(String, Class)
   */
  public JComboBoxFixture(RobotFixture robot, String comboBoxName) {
    super(robot, comboBoxName, JComboBox.class);
  }
  
  /**
   * Creates a new </code>{@link JComboBoxFixture}</code>.
   * @param robot performs simulation of user events on the given combobox.
   * @param target the target combo box.
   */
  public JComboBoxFixture(RobotFixture robot, JComboBox target) {
    super(robot, target);
  }
  
  /**
   * @return the elements in the managed combobox.
   */
  public final String[] contents() {
    return comboBoxTester().getContents(target);
  }
  
  /**
   * Simulates a user selecting the item located at the given index. 
   * @param index the given index to match.
   * @return this fixture.
   */
  public final JComboBoxFixture selectItemAt(int index) {
    comboBoxTester().actionSelectIndex(target, index);
    return this;
  }

  /**
   * Simulates a user selecting the item that contains the given text. 
   * @param text the given text to match.
   * @return this fixture.
   */
  public final JComboBoxFixture selectItemWithText(String text) {
    comboBoxTester().actionSelectItem(target, text);
    return this;
  }

  /**
   * Enters the specified text in the managed combobox.
   * @param text the text to enter.
   * @return this fixture.
   */
  public final JComboBoxFixture enterText(String text) {
    focus();
    tester().actionKeyString(text);
    return this;
  }

  /** {@inheritDoc} */
  public final JComboBoxFixture focus() {
    super.doFocus();
    return this;
  }

  private JComboBoxTester comboBoxTester() {
    return testerCastedTo(JComboBoxTester.class);
  }

  /** {@inheritDoc} */
  public final JComboBoxFixture click() {
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public final JComboBoxFixture shouldBeVisible() {
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final JComboBoxFixture shouldNotBeVisible() {
    assertIsNotVisible();
    return this;
  }
}
