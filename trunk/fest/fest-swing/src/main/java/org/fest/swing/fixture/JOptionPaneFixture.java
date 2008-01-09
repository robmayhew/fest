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

import java.awt.Dialog;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import static javax.swing.JOptionPane.*;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.exception.ActionFailedException.actionFailure;

import static org.fest.util.Strings.concat;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.exception.ComponentLookupException;

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
   * @param robot finds a showing <code>JOptionPane</code>, which will be managed by this fixture.
   * @throws ComponentLookupException if a showing <code>JOptionPane</code> could not be found.
   * @throws ComponentLookupException if more than one showing <code>JOptionPane</code> is found.
   */
  public JOptionPaneFixture(RobotFixture robot) {
    this(robot, robot.finder().findByType(JOptionPane.class, true));
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
   * Simulates a user clicking this fixture's <code>{@link JOptionPane}</code>.
   * @return this fixture.
   */
  public final JOptionPaneFixture click() {
    return (JOptionPaneFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JOptionPane}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JOptionPaneFixture click(MouseButton button) {
    return (JOptionPaneFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JOptionPane}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JOptionPaneFixture click(MouseClickInfo mouseClickInfo) {
    return (JOptionPaneFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JOptionPane}</code>.
   * @return this fixture.
   */
  public final JOptionPaneFixture rightClick() {
    return (JOptionPaneFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking this fixture's <code>{@link JOptionPane}</code>.
   * @return this fixture.
   */
  public final JOptionPaneFixture doubleClick() {
    return (JOptionPaneFixture)doDoubleClick();
  }

  /**
   * Gives input focus to this fixture's <code>{@link JOptionPane}</code>.
   * @return this fixture.
   */
  public final JOptionPaneFixture focus() {
    return (JOptionPaneFixture)doFocus();
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> has the given title.
   * @param title the title to match.
   * @return this fixture.
   * @throws AssertionError if this fixture's </code>JOptionPaneFixture</code> does not have the given title.
   */
  public final JOptionPaneFixture requireTitle(String title) {
    String actualTitle = ((Dialog)target.getRootPane().getParent()).getTitle();
    assertThat(actualTitle).as(formattedPropertyName("title")).isEqualTo(title);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> shows the given message.
   * @param message the message to verify.
   * @return this fixture.
   * @throws AssertionError if this fixture's </code>JOptionPaneFixture</code> does not show the given message.
   */
  public final JOptionPaneFixture requireMessage(Object message) {
    assertThat(target.getMessage()).as(formattedPropertyName("message")).isEqualTo(message);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> has the given options.
   * @param options the options to verify.
   * @return this fixture.
   * @throws AssertionError if this fixture's </code>JOptionPaneFixture</code> does not have the given options.
   */
  public final JOptionPaneFixture requireOptions(Object[] options) {
    assertThat(target.getOptions()).as(formattedPropertyName("options")).isEqualTo(options);
    return this;
  }

  /**
   * Returns a fixture wrapping the "OK" button in this fixture's <code>{@link JOptionPane}</code>. This method is
   * locale-independent and platform-independent.
   * @return a fixture wrapping the "OK" button.
   * @throws ComponentLookupException if the a "OK" button cannot be found.
   */
  public final JButtonFixture okButton() {
    return buttonWithTextFromUIManager("OptionPane.okButtonText");
  }

  /**
   * Returns a fixture wrapping the "Cancel" button in this fixture's <code>{@link JOptionPane}</code>. This method is
   * locale-independent and platform-independent.
   * @return a fixture wrapping the "Cancel" button.
   * @throws ComponentLookupException if the a "Cancel" button cannot be found.
   */
  public final JButtonFixture cancelButton() {
    return buttonWithTextFromUIManager("OptionPane.cancelButtonText");
  }

  /**
   * Returns a fixture wrapping the "Yes" button in this fixture's <code>{@link JOptionPane}</code>. This method is
   * locale-independent and platform-independent.
   * @return a fixture wrapping the "Yes" button.
   * @throws ComponentLookupException if the a "Yes" button cannot be found.
   */
  public final JButtonFixture yesButton() {
    return buttonWithTextFromUIManager("OptionPane.yesButtonText");
  }

  /**
   * Returns a fixture wrapping the "No" button in this fixture's <code>{@link JOptionPane}</code>. This method is
   * locale-independent and platform-independent.
   * @return a fixture wrapping the "No" button.
   * @throws ComponentLookupException if the a "No" button cannot be found.
   */
  public final JButtonFixture noButton() {
    return buttonWithTextFromUIManager("OptionPane.noButtonText");
  }

  private JButtonFixture buttonWithTextFromUIManager(String key) {
    return buttonWithText(UIManager.getString(key));
  }

  /**
   * Finds and returns a fixture wrapping a button (this fixture's <code>{@link JOptionPane}</code>) containing the
   * given text.
   * @param text the text of the button to find and return.
   * @return a fixture wrapping a button containing the given text.
   * @throws ComponentLookupException if the a button with the given text cannot be found.
   */
  public final JButtonFixture buttonWithText(String text) {
    JButton button = robot.finder().find(target, new JButtonMatcher(text));
    return new JButtonFixture(robot, button);
  }

  /**
   * Finds the first <code>{@link JButton}</code> in this fixture's <code>{@link JOptionPane}</code>.
   * @return a fixture wrapping the first <code>JButton</code> contained in this fixture's <code>JOptionPane</code>.
   */
  public final JButtonFixture button() {
    return new JButtonFixture(robot, robot.finder().findByType(target, JButton.class));
  }

  /**
   * Returns the <code>{@link JTextComponent}</code> in the given message only if the message is of type input.
   * @return the text component in the given message.
   * @throws ComponentLookupException if the message type is not input and therefore it does not contain a text component.
   */
  public final JTextComponentFixture textBox() throws ComponentLookupException {
    JTextComponent textComponent = robot.finder().findByType(target, JTextComponent.class);
    return new JTextComponentFixture(robot, textComponent);
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is displaying an error message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireErrorMessage() {
    return assertEqualMessageType(ERROR_MESSAGE);
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is displaying an information
   * message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireInformationMessage() {
    return assertEqualMessageType(INFORMATION_MESSAGE);
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is displaying a warning message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireWarningMessage() {
    return assertEqualMessageType(WARNING_MESSAGE);
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is displaying a question.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireQuestionMessage() {
    return assertEqualMessageType(QUESTION_MESSAGE);
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is displaying a plain message.
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
      throw actionFailure(concat("The message type <", messageType, "> is not valid"));
    return messageMap.get(messageType);
  }

  /**
   * Simulates a user pressing and releasing the given keys this fixture's <code>{@link JOptionPane}</code>. This method
   * does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JOptionPaneFixture pressAndReleaseKeys(int... keyCodes) {
    return (JOptionPaneFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JOptionPane}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JOptionPaneFixture pressKey(int keyCode) {
    return (JOptionPaneFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JOptionPane}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JOptionPaneFixture releaseKey(int keyCode) {
    return (JOptionPaneFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JOptionPane</code> is not visible.
   */
  public final JOptionPaneFixture requireVisible() {
    return (JOptionPaneFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JOptionPane</code> is visible.
   */
  public final JOptionPaneFixture requireNotVisible() {
    return (JOptionPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JOptionPane</code> is disabled.
   */
  public final JOptionPaneFixture requireEnabled() {
    return (JOptionPaneFixture)assertEnabled();
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JOptionPane</code> is never enabled.
   */
  public final JOptionPaneFixture requireEnabled(Timeout timeout) {
    return (JOptionPaneFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JOptionPane}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JOptionPane</code> is enabled.
   */
  public final JOptionPaneFixture requireDisabled() {
    return (JOptionPaneFixture)assertDisabled();
  }
}
