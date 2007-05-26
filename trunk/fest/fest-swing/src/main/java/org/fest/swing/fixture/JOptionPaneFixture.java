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

import java.awt.Component;
import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import abbot.finder.Matcher;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events and state verification of a <code>{@link JOptionPane}</code>.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneFixture extends AbstractComponentFixture<JOptionPane> {

  /**
   * Creates a new </code>{@link JOptionPaneFixture}</code>.
   * @param robot performs simulation of user events on the target <code>JOptionPane</code>.
   */
  public JOptionPaneFixture(RobotFixture robot) {
    super(robot, JOptionPane.class);
  }

  /** {@inheritDoc} */
  public final JOptionPaneFixture click() {
    doClick();
    return this;
  }

  /** {@inheritDoc} */
  public final JOptionPaneFixture focus() {
    doFocus();
    return this;
  }

  /**
   * Asserts that the target dialog has the given title.
   * @param title the title to match.
   * @return this fixture.
   * @throws AssertionError if the target dialog does not have the given title.
   */
  public final JOptionPaneFixture requireTitle(String title) {
    String actualTitle = ((Dialog)target.getRootPane().getParent()).getTitle();
    assertThat(actualTitle).isEqualTo(title);
    return this;
  }

  /**
   * Asserts that the target dialog shows the given message.
   * @param message the message to verify.
   * @return this fixture.
   * @throws AssertionError if the target dialog does not show the given message.
   */
  public final JOptionPaneFixture requireMessage(Object message) {
    assertThat(target.getMessage()).isEqualTo(message);
    return this;
  }
  
  /**
   * Asserts that the target dialog has the given options.
   * @param options the options to verify.
   * @return this fixture.
   * @throws AssertionError if the target dialog does not have the given options.
   */
  public final JOptionPaneFixture requireOptions(Object[] options) {
    assertThat(target.getOptions()).isEqualTo(options);
    return this;
  }
  
  /**
   * Finds and returns a fixture wrapping a button containing the given text.
   * @param text the text of the button to find and return.
   * @return a fixture wrapping a button containing the given text, or <code>null</code> if none if found.
   */
  public final JButtonFixture findButtonWithText(final String text) {
    Component component = robot.finder().find(target, new Matcher() {
      public boolean matches(Component c) {
        if (!(c instanceof JButton)) return false;
        return areEqual(text, ((JButton)c).getText());
      }
    });
    if (component == null) return null;
    return new JButtonFixture(robot, (JButton)component);
  }
  
  /** @return a fixture wrapping a button contained in the given dialog. */
  public final JButtonFixture findButton() {
    return new JButtonFixture(robot, robot.finder().findByType(target, JButton.class));
  }
  
  /**
   * Returns the <code>{@link JTextComponent}</code> in the given message only if the message is of type input.
   * @return the text component in the given message.
   * @throws ComponentLookupException if the message type is not input and hence does not contain a text component.
   */
  public final JTextComponentFixture findTextComponent() throws ComponentLookupException {
    JTextComponent textComponent = robot.finder().findByType(target, JTextComponent.class);
    return new JTextComponentFixture(robot, textComponent);
  }
  
  /**
   * Asserts that the target dialog is displaying an error message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireErrorMessage() {
    return assertEqualMessageType(ERROR_MESSAGE);
  }
  
  /**
   * Asserts that the target dialog is displaying an information message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireInformationMessage() {
    return assertEqualMessageType(INFORMATION_MESSAGE);
  }

  /**
   * Asserts that the target dialog is displaying a warning message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireWarningMessage() {
    return assertEqualMessageType(WARNING_MESSAGE);
  }
  
  /**
   * Asserts that the target dialog is displaying a question.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireQuestionMessage() {
    return assertEqualMessageType(QUESTION_MESSAGE);
  }
  
  /**
   * Asserts that the target dialog is displaying a plain message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requirePlainMessage() {
    return assertEqualMessageType(PLAIN_MESSAGE);
  }

  private JOptionPaneFixture assertEqualMessageType(int expected) {
    assertThat(messageTypeAsText(target.getMessageType())).isEqualTo(messageTypeAsText(expected));
    return this;
  }
  
  private String messageTypeAsText(int messageType) {
    if (messageType == ERROR_MESSAGE) return "Error Message";
    if (messageType == INFORMATION_MESSAGE) return "Information Message";
    if (messageType == WARNING_MESSAGE) return "Warning Message";
    if (messageType == QUESTION_MESSAGE) return "Question Message";
    if (messageType == PLAIN_MESSAGE) return "Plain Message";
    throw new IllegalArgumentException(concat("The message type <", messageType, "> is not valid"));
  }

  /** {@inheritDoc} */
  public final JOptionPaneFixture requireVisible() {
    assertIsVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final JOptionPaneFixture requireNotVisible() {
    assertIsNotVisible();
    return this;
  }

  /** {@inheritDoc} */
  public final JOptionPaneFixture requireEnabled() {
    assertIsEnabled();
    return this;
  }
  
  /** {@inheritDoc} */  
  public final JOptionPaneFixture requireDisabled() {
    assertIsDisabled();
    return this;
  }
}
