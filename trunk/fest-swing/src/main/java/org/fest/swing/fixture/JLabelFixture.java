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

import org.fest.swing.RobotFixture;


import static org.fest.swing.assertions.Assertions.assertThat;


/**
 * Understands simulation of user events and state verification of a <code>{@link JLabel}</code>.
 *
 * @author Alex Ruiz
 */
public class JLabelFixture extends AbstractComponentFixture<JLabel> implements TextDisplayFixture<JLabel> {
  
  /**
   * Creates a new </code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on a <code>JLabel</code>.
   * @param labelName the name of the label to find using the given <code>AbbotFixture</code>.
   * @see RobotFixture#findByName(String, Class)
   */
  public JLabelFixture(RobotFixture robot, String labelName) {
    super(robot, labelName, JLabel.class);
  }
  
  /**
   * Creates a new </code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on the given label.
   * @param target the target label.
   */
  public JLabelFixture(RobotFixture robot, JLabel target) {
    super(robot, target);
  }
  
  /** {@inheritDoc} */
  public final JLabelFixture shouldHaveThisText(String expected) {
    assertThat(text()).isEqualTo(expected);
    return this;
  }
  
  /** {@inheritDoc} */
  public final String text() { return target.getText(); }
  
  /** {@inheritDoc} */
  public final JLabelFixture click() {
    doClick();
    return this; 
  }

  /** {@inheritDoc} */
  public final JLabelFixture focus() {
    doFocus();
    return this;
  }

  /** {@inheritDoc} */
  public final JLabelFixture shouldBeVisible() {
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final JLabelFixture shouldNotBeVisible() {
    assertIsNotVisible();
    return this;
  }
}
