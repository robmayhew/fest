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

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Understands simulation of user events on a <code>{@link JLabel}</code> and verification of the state of such
 * <code>{@link JLabel}</code>.
 *
 * @author Alex Ruiz
 */
public class JLabelFixture extends ComponentFixture<JLabel> implements TextDisplayFixture {
  
  /**
   * Creates a new <code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on a <code>JLabel</code>.
   * @param labelName the name of the <code>JLabel</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JLabel</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JLabel</code> is found.
   */
  public JLabelFixture(RobotFixture robot, String labelName) {
    super(robot, labelName, JLabel.class);
  }
  
  /**
   * Creates a new <code>{@link JLabelFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JLabel</code>.
   * @param target the <code>JLabel</code> to be managed by this fixture.
   */
  public JLabelFixture(RobotFixture robot, JLabel target) {
    super(robot, target);
  }
  
  /**
   * Asserts that the text of this fixture's <code>{@link JLabel}</code> is equal to the specified <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target component is not equal to the given one.
   */
  public final JLabelFixture requireText(String expected) {
    assertThat(text()).as(formattedPropertyName("text")).isEqualTo(expected);
    return this;
  }
  
  /**
   * Returns the text of this fixture's <code>{@link JLabel}</code>. 
   * @return the text of this fixture's <code>JLabel</code>. 
   */
  public final String text() { return target.getText(); }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   */
  public final JLabelFixture click() {
    return (JLabelFixture)doClick(); 
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JLabel}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JLabelFixture click(MouseButton button) {
    return (JLabelFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JLabel}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JLabelFixture click(MouseClickInfo mouseClickInfo) {
    return (JLabelFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   */
  public final JLabelFixture rightClick() {
    return (JLabelFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   */
  public final JLabelFixture doubleClick() {
    return (JLabelFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JLabel}</code>.
   * @return this fixture.
   */
  public final JLabelFixture focus() {
    return (JLabelFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JLabel}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JLabelFixture pressAndReleaseKeys(int... keyCodes) {
    return (JLabelFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JLabel}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JLabelFixture pressKey(int keyCode) {
    return (JLabelFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JLabel}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JLabelFixture releaseKey(int keyCode) {
    return (JLabelFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is not visible.
   */
  public final JLabelFixture requireVisible() {
    return (JLabelFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is visible.
   */
  public final JLabelFixture requireNotVisible() {
    return (JLabelFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is disabled.
   */
  public final JLabelFixture requireEnabled() {
    return (JLabelFixture)assertEnabled();
  }
  
  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JLabel</code> is never enabled.
   */
  public final JLabelFixture requireEnabled(Timeout timeout) {
    return (JLabelFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that this fixture's <code>{@link JLabel}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JLabel</code> is enabled.
   */
  public final JLabelFixture requireDisabled() {
    return (JLabelFixture)assertDisabled();
  }
}
