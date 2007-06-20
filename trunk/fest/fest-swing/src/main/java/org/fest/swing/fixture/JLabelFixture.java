/*
 * Created on Oct 20, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JLabel;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JLabel}</code> and verification of the state of such
 * <code>{@link JLabel}</code>.
 *
 * @author Alex Ruiz
 */
public class JLabelFixture extends ComponentFixture<JLabel> implements TextDisplayFixture<JLabel> {
  
  /**
   * Creates a new </code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on a <code>JLabel</code>.
   * @param labelName the name of the button to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JLabel</code> could not be found.
   */
  public JLabelFixture(RobotFixture robot, String labelName) {
    super(robot, labelName, JLabel.class);
  }
  
  /**
   * Creates a new </code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JLabel</code>.
   * @param target the <code>JLabel</code> to be managed by this fixture.
   */
  public JLabelFixture(RobotFixture robot, JLabel target) {
    super(robot, target);
  }
  
  /**
   * Asserts that the text of the <code>{@link JLabel}</code> managed by this fixture is equal to the specified 
   * <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target component is not equal to the given one.
   */
  public final JLabelFixture requireText(String expected) {
    assertThat(text()).isEqualTo(expected);
    return this;
  }
  
  /**
   * Returns the text of the <code>{@link JLabel}</code> managed by this fixture. 
   * @return the text of the managed <code>JLabel</code>. 
   */
  public final String text() { return target.getText(); }
  
  /**
   * Simulates a user clicking the <code>{@link JLabel}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JLabelFixture click() {
    return (JLabelFixture)super.click(); 
  }

  /**
   * Gives input focus to the <code>{@link JLabel}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JLabelFixture focus() {
    return (JLabelFixture)super.focus();
  }

  /**
   * Asserts that the <code>{@link JLabel}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JLabel</code> is not visible.
   */
  @Override public final JLabelFixture requireVisible() {
    return (JLabelFixture)super.requireVisible();
  }

  /**
   * Asserts that the <code>{@link JLabel}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JLabel</code> is visible.
   */
  @Override public final JLabelFixture requireNotVisible() {
    return (JLabelFixture)super.requireNotVisible();
  }

  /**
   * Asserts that the <code>{@link JLabel}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JLabel</code> is disabled.
   */
  @Override public final JLabelFixture requireEnabled() {
    return (JLabelFixture)super.requireEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JLabel}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JLabel</code> is enabled.
   */
  @Override public final JLabelFixture requireDisabled() {
    return (JLabelFixture)super.requireDisabled();
  }
}
