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

import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.core.TypeMatcher;
import org.fest.swing.exception.ComponentLookupException;
import static org.fest.util.Strings.concat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.*;
import javax.swing.text.JTextComponent;
import java.awt.Dialog;
import java.util.HashMap;

/**
 * Understands simulation of user events on a <code>{@link JOptionPane}</code> and verification of the state of such
 * <code>{@link JOptionPane}</code>.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneFixture extends ComponentFixture<JOptionPane> {

  private static final HashMap<Integer, String> messageMap = new HashMap<Integer, String>();
  static {
    messageMap.put(ERROR_MESSAGE, "Error Message");
    messageMap.put(INFORMATION_MESSAGE, "Information Message");
    messageMap.put(WARNING_MESSAGE, "Warning Message");
    messageMap.put(QUESTION_MESSAGE, "Question Message");
    messageMap.put(PLAIN_MESSAGE, "Plain Message");
  }
  
  /**
   * Creates a new <code>{@link JOptionPaneFixture}</code>.
   * @param robot finds a visible <code>JOptionPane</code>, which will be managed by this fixture.
   * @throws ComponentLookupException if a visible <code>JOptionPane</code> could not be found.
   */
  public JOptionPaneFixture(RobotFixture robot) {
    super(robot, (JOptionPane)robot.finder().find(new TypeMatcher(JOptionPane.class, true)));
  }

  /**
   * Creates a new <code>{@link JOptionPaneFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JOptionPane</code>.
   * @param target the <code>JOptionPane</code> to be managed by this fixture.
   */
  public JOptionPaneFixture(RobotFixture robot, JOptionPane target) {
    super(robot, target);
  }

  /**
   * Simulates a user clicking the <code>{@link JOptionPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JOptionPaneFixture click() {
    return (JOptionPaneFixture)doClick();
  }

  /**
   * Simulates a user clicking the <code>{@link JOptionPane}</code> managed by this fixture.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JOptionPaneFixture click(MouseButton button) {
    return (JOptionPaneFixture)doClick(button);
  }

  /**
   * Simulates a user clicking the <code>{@link JOptionPane}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JOptionPaneFixture click(MouseClickInfo mouseClickInfo) {
    return (JOptionPaneFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JOptionPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JOptionPaneFixture rightClick() {
    return (JOptionPaneFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JOptionPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JOptionPaneFixture doubleClick() {
    return (JOptionPaneFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JOptionPane}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JOptionPaneFixture focus() {
    return (JOptionPaneFixture)doFocus();
  }

  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture has the given title.
   * @param title the title to match.
   * @return this fixture.
   * @throws AssertionError if the managed </code>JOptionPaneFixture</code> managed by this fixture does not have the given title.
   */
  public final JOptionPaneFixture requireTitle(String title) {
    String actualTitle = ((Dialog)target.getRootPane().getParent()).getTitle();
    assertThat(actualTitle).as(formattedPropertyName("title")).isEqualTo(title);
    return this;
  }

  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture shows the given message.
   * @param message the message to verify.
   * @return this fixture.
   * @throws AssertionError if the managed </code>JOptionPaneFixture</code> managed by this fixture does not show the given message.
   */
  public final JOptionPaneFixture requireMessage(Object message) {
    assertThat(target.getMessage()).as(formattedPropertyName("message")).isEqualTo(message);
    return this;
  }
  
  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture has the given options.
   * @param options the options to verify.
   * @return this fixture.
   * @throws AssertionError if the managed </code>JOptionPaneFixture</code> managed by this fixture does not have the given options.
   */
  public final JOptionPaneFixture requireOptions(Object[] options) {
    assertThat(target.getOptions()).as(formattedPropertyName("options")).isEqualTo(options);
    return this;
  }
  
  /**
   * Finds and returns a fixture wrapping a button containing the given text.
   * @param text the text of the button to find and return.
   * @return a fixture wrapping a button containing the given text, or <code>null</code> if none if found.
   */
  public final JButtonFixture buttonWithText(String text) {
    JButton button = robot.finder().find(target, new JButtonMatcher(text));
    if (button == null) return null;
    return new JButtonFixture(robot, button);
  }
  
  /**
   * Finds a <code>{@link JButton}</code> in the <code>{@link JOptionPaneFixture}</code> managed by this fixture.
   * @return a fixture wrapping a <code>JButton</code> contained in the managed <code>JOptionPane</code>. 
   */
  public final JButtonFixture button() {
    return new JButtonFixture(robot, robot.finder().findByType(target, JButton.class));
  }
  
  /**
   * Returns the <code>{@link JTextComponent}</code> in the given message only if the message is of type input.
   * @return the text component in the given message.
   * @throws ComponentLookupException if the message type is not input and hence does not contain a text component.
   */
  public final JTextComponentFixture textBox() throws ComponentLookupException {
    JTextComponent textComponent = robot.finder().findByType(target, JTextComponent.class);
    return new JTextComponentFixture(robot, textComponent);
  }
  
  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying an error message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireErrorMessage() {
    return assertEqualMessageType(ERROR_MESSAGE);
  }
  
  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying an information
   * message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireInformationMessage() {
    return assertEqualMessageType(INFORMATION_MESSAGE);
  }

  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying a warning message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireWarningMessage() {
    return assertEqualMessageType(WARNING_MESSAGE);
  }
  
  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying a question.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireQuestionMessage() {
    return assertEqualMessageType(QUESTION_MESSAGE);
  }
  
  /**
   * Asserts that the <code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying a plain message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requirePlainMessage() {
    return assertEqualMessageType(PLAIN_MESSAGE);
  }

  private JOptionPaneFixture assertEqualMessageType(int expected) {
    assertThat(actualMessageTypeAsText()).as(formattedPropertyName("messageType")).isEqualTo(messageTypeAsText(expected));
    return this;
  }

  private String actualMessageTypeAsText() {
    return messageTypeAsText(target.getMessageType());
  }
  
  private String messageTypeAsText(int messageType) {
    if (!messageMap.containsKey(messageType))
      throw new IllegalArgumentException(concat("The message type <", messageType, "> is not valid"));
    return messageMap.get(messageType);
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JOptionPane}</code> managed by this
   * fixture. This method does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JOptionPaneFixture pressAndReleaseKeys(int... keyCodes) {
    return (JOptionPaneFixture)doPressAndReleaseKeys(keyCodes);
  }
  
  /**
   * Simulates a user pressing the given key on the <code>{@link JOptionPane}</code> managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JOptionPaneFixture pressKey(int keyCode) {
    return (JOptionPaneFixture)doPressKey(keyCode);
  }
  
  /**
   * Simulates a user releasing the given key on the <code>{@link JOptionPane}</code> managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JOptionPaneFixture releaseKey(int keyCode) {
    return (JOptionPaneFixture)doReleaseKey(keyCode);
  }
  
  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JOptionPane</code> is not visible.
   */
  public final JOptionPaneFixture requireVisible() {
    return (JOptionPaneFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JOptionPane</code> is visible.
   */
  public final JOptionPaneFixture requireNotVisible() {
    return (JOptionPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JOptionPane</code> is disabled.
   */
  public final JOptionPaneFixture requireEnabled() {
    return (JOptionPaneFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if the managed <code>JOptionPane</code> is never enabled.
   */
  public final JOptionPaneFixture requireEnabled(Timeout timeout) {
    return (JOptionPaneFixture)assertEnabled(timeout);
  }
  
  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JOptionPane</code> is enabled.
   */
  public final JOptionPaneFixture requireDisabled() {
    return (JOptionPaneFixture)assertDisabled();
  }

  @Override protected String formattedTarget() {
    return target.getClass().getName();
  }
}
