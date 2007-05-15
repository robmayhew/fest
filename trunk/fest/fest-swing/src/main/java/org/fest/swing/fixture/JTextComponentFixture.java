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

import javax.swing.text.JTextComponent;

import abbot.tester.JTextComponentTester;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DELETE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.isEmpty;

import org.fest.swing.RobotFixture;



/**
 * Understands simulation of user events and state verification of a <code>{@link JTextComponent}</code>.
 *
 * @author Alex Ruiz
 */
public class JTextComponentFixture extends AbstractComponentFixture<JTextComponent> implements TextInputFixture<JTextComponent> {

  /**
   * Creates a new </code>{@link JTextComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTextComponent</code>.
   * @param textComponentName the name of the text component to find using the given <code>RobotFixture</code>.
   * @see org.fest.swing.ComponentFinder#findByName(String, Class)
   */
  public JTextComponentFixture(RobotFixture robot, String textComponentName) {
    super(robot, textComponentName, JTextComponent.class);
  }
  
  /**
   * Creates a new </code>{@link JTextComponentFixture}</code>.
   * @param robot performs simulation of user events on the given text component.
   * @param target the target text component.
   */
  public JTextComponentFixture(RobotFixture robot, JTextComponent target) {
    super(robot, target);
  }

  /** {@inheritDoc} */
  public final JTextComponentFixture click() { 
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public final JTextComponentFixture focus() { 
    doFocus();
    return this;
  }

  /** {@inheritDoc} */
  public final JTextComponentFixture requireText(String expected) {
    assertThat(text()).isEqualTo(expected);
    return this;
  }
  
  /** {@inheritDoc} */
  public final JTextComponentFixture enterText(String text) {
    doFocus();
    tester().actionKeyString(text);
    return this;
  }
  
  /** {@inheritDoc} */
  public final JTextComponentFixture deleteText() {
    return selectAll().pressKeys(VK_BACK_SPACE, VK_DELETE);
  }
  
  /** {@inheritDoc} */
  public final JTextComponentFixture pressKeys(int...keyCodes) {
    doPressKeys(keyCodes);
    return this;
  }

  /** {@inheritDoc} */
  public final String text() {
    return target.getText();
  }
  
  /** {@inheritDoc} */
  public final JTextComponentFixture selectAll() {
    return selectText(0, target.getDocument().getLength());
  }

  /** {@inheritDoc} */
  public final JTextComponentFixture selectText(int start, int end) {
    if (isEmpty(text())) return this;
    textComponentTester().actionSelectText(target, start, end);
    return this;
  }
  
  private JTextComponentTester textComponentTester() {
    return testerCastedTo(JTextComponentTester.class);
  }

  /** {@inheritDoc} */
  public final JTextComponentFixture requireVisible() { 
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final JTextComponentFixture requireNotVisible() { 
    assertIsNotVisible();
    return this;
  }

  /**
   * Asserts that the target text component does not contain any text.
   * @return this fixture.
   * @throws AssertionError if the target text component is not empty.
   */
  public final JTextComponentFixture requireEmpty() {
    assertThat(text()).isEmpty();
    return this;
  }
}
