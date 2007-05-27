/*
 * Created on Dec 16, 2006
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

import javax.swing.JButton;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.RobotFixture;


/**
 * Understands simulation of user events and state verification of a <code>{@link JButton}</code>.
 *
 * @author Yvonne Wang
 */
public class JButtonFixture extends AbstractComponentFixture<JButton> implements TextDisplayFixture<JButton> {

  /**
   * Creates a new </code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on a <code>JButton</code>.
   * @param buttonName the name of the button to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public JButtonFixture(RobotFixture robot, String buttonName) {
    super(robot, buttonName, JButton.class);
  }
  
  /**
   * Creates a new </code>{@link JButtonFixture}</code>.
   * @param robot performs simulation of user events on the given button.
   * @param target the target button.
   */
  public JButtonFixture(RobotFixture robot, JButton target) {
    super(robot, target);
  }

  /** {@inheritDoc} */
  @Override public final JButtonFixture click() {
    return (JButtonFixture)super.click();
  }

  /** {@inheritDoc} */
  @Override public final JButtonFixture focus() {
    return (JButtonFixture)super.focus();
  }

  /** {@inheritDoc} */
  public final JButtonFixture requireText(String expected) {
    assertThat(text()).isEqualTo(expected);
    return this;
  }

  /** {@inheritDoc} */
  public final String text() {
    return target.getText();
  }

  /** {@inheritDoc} */
  @Override public final JButtonFixture requireVisible() {
    return (JButtonFixture)super.requireVisible();
  }

  /** {@inheritDoc} */
  @Override public final JButtonFixture requireNotVisible() {
    return (JButtonFixture)super.requireNotVisible();
  }

  /** {@inheritDoc} */
  @Override public final JButtonFixture requireEnabled() {
    return (JButtonFixture)super.requireEnabled();
  }
  
  /** {@inheritDoc} */  
  @Override public final JButtonFixture requireDisabled() {
    return (JButtonFixture)super.requireDisabled();
  }
}
