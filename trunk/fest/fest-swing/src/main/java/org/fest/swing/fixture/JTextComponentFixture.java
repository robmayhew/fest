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
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.isEmpty;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JTextComponent}</code> and verification of the state of such
 * <code>{@link JTextComponent}</code>.
 *
 * @author Alex Ruiz
 */
public class JTextComponentFixture extends ComponentFixture<JTextComponent> implements TextInputFixture<JTextComponent> {

  /**
   * Creates a new </code>{@link JTextComponentFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTextComponent</code>.
   * @param textComponentName the name of the <code>JTextComponent</code> to find using the given 
   * <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTextComponent</code> could not be found.
   */
  public JTextComponentFixture(RobotFixture robot, String textComponentName) {
    super(robot, textComponentName, JTextComponent.class);
  }
  
  /**
   * Creates a new </code>{@link JTextComponentFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTextComponent</code>.
   * @param target the <code>JTextComponent</code> to be managed by this fixture.
   */
  public JTextComponentFixture(RobotFixture robot, JTextComponent target) {
    super(robot, target);
  }

  /**
   * Simulates a user clicking the <code>{@link JTextComponent}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JTextComponentFixture click() { 
    return (JTextComponentFixture)doClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JTextComponent}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JTextComponentFixture doubleClick() {
    return (JTextComponentFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JTextComponent}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JTextComponentFixture focus() { 
    return (JTextComponentFixture)doFocus();
  }

  /**
   * Asserts that the text of the <code>{@link JTextComponent}</code> managed by this fixture is equal to the specified 
   * <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target component is not equal to the given one.
   */
  public final JTextComponentFixture requireText(String expected) {
    assertThat(text()).isEqualTo(expected);
    return this;
  }
  
  /**
   * Simulates a user entering the given text in the <code>{@link JTextComponent}</code> managed by this fixture.
   * @param text the text to enter.
   * @return this fixture.
   */
  public final JTextComponentFixture enterText(String text) {
    focus();
    tester().actionKeyString(text);
    return this;
  }
  
  /**
   * Simulates a user deleting all the text in the <code>{@link JTextComponent}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JTextComponentFixture deleteText() {
    target.setText("");
    return this;
  }
  
  /**
   * Simulates a user pressing the given keys in the <code>{@link JTextComponent}</code> managed by this fixture.
   * @param keyCodes the codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTextComponentFixture pressKeys(int...keyCodes) {
    return (JTextComponentFixture)doPressKeys(keyCodes);
  }

  /**
   * Returns the text of the <code>{@link JTextComponent}</code> managed by this fixture. 
   * @return the text of the managed <code>JTextComponent</code>. 
   */
  public final String text() {
    return target.getText();
  }
  
  /**
   * Simulates a user selecting all the text contained in the <code>{@link JTextComponent}</code> managed by this 
   * fixture. 
   * @return this fixture.
   */
  public final JTextComponentFixture selectAll() {
    return selectText(0, target.getDocument().getLength());
  }
  
  /**
   * Simulates a user selecting the given text contained in the <code>{@link JTextComponent}</code> managed by this 
   * fixture.
   * @param text the text to select.
   * @return this fixture.
   */
  public final JTextComponentFixture select(String text) {
    int indexFound = text().indexOf(text);
    if (indexFound == -1) return this;
    return selectText(indexFound, indexFound + text.length());
  }

  /**
   * Simulates a user selecting a portion of the text contained in the <code>{@link JTextComponent}</code> managed by 
   * this fixture.
   * @param start index where selection should start.
   * @param end index where selection should end.
   * @return this fixture.
   */
  public final JTextComponentFixture selectText(int start, int end) {
    if (isEmpty(text())) return this;
    textComponentTester().actionSelectText(target, start, end);
    return this;
  }

  private JTextComponentTester textComponentTester() {
    return (JTextComponentTester)tester();
  }

  /**
   * Asserts that the <code>{@link JTextComponent}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is not visible.
   */
  @Override public final JTextComponentFixture requireVisible() { 
    return (JTextComponentFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JTextComponent}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>Component</code> is visible.
   */
  @Override public final JTextComponentFixture requireNotVisible() { 
    return (JTextComponentFixture)assertNotVisible();
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

  /**
   * Asserts that the <code>{@link JTextComponent}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is disabled.
   */
  @Override public final JTextComponentFixture requireEnabled() {
    return (JTextComponentFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JTextComponent}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>Component</code> is enabled.
   */
  @Override public final JTextComponentFixture requireDisabled() {
    return (JTextComponentFixture)assertDisabled();
  }
}
