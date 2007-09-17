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
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;
import org.fest.swing.TypeMatcher;

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
   * Creates a new </code>{@link JOptionPaneFixture}</code>.
   * @param robot finds a visible <code>JOptionPane</code>, which will be managed by this fixture.
   * @throws ComponentLookupException if a visible <code>JOptionPane</code> could not be found.
   */
  public JOptionPaneFixture(RobotFixture robot) {
    super(robot, (JOptionPane)robot.finder().find(new TypeMatcher(JOptionPane.class, true)));
  }

  /**
   * Simulates a user clicking the <code>{@link JOptionPane}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JOptionPaneFixture click() {
    return (JOptionPaneFixture)doClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JOptionPane}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JOptionPaneFixture doubleClick() {
    return (JOptionPaneFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JOptionPane}</code> managed by this fixture.
   * @return this fixture.
   */
  @Override public final JOptionPaneFixture focus() {
    return (JOptionPaneFixture)doFocus();
  }

  /**
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture has the given title.
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
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture shows the given message.
   * @param message the message to verify.
   * @return this fixture.
   * @throws AssertionError if the managed </code>JOptionPaneFixture</code> managed by this fixture does not show the given message.
   */
  public final JOptionPaneFixture requireMessage(Object message) {
    assertThat(target.getMessage()).as(formattedPropertyName("message")).isEqualTo(message);
    return this;
  }
  
  /**
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture has the given options.
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
  public final JButtonFixture buttonWithText(final String text) {
    Component component = robot.finder().find(target, new ComponentMatcher() {
      public boolean matches(Component c) {
        if (!(c instanceof JButton)) return false;
        return areEqual(text, ((JButton)c).getText());
      }
    });
    if (component == null) return null;
    return new JButtonFixture(robot, (JButton)component);
  }
  
  /**
   * Finds a <code>{@link JButton}</code> in the </code>{@link JOptionPaneFixture}</code> managed by this fixture. 
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
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying an error message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireErrorMessage() {
    return assertEqualMessageType(ERROR_MESSAGE);
  }
  
  /**
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying an information 
   * message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireInformationMessage() {
    return assertEqualMessageType(INFORMATION_MESSAGE);
  }

  /**
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying a warning message.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireWarningMessage() {
    return assertEqualMessageType(WARNING_MESSAGE);
  }
  
  /**
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying a question.
   * @return this fixture.
   */
  public final JOptionPaneFixture requireQuestionMessage() {
    return assertEqualMessageType(QUESTION_MESSAGE);
  }
  
  /**
   * Asserts that the </code>{@link JOptionPaneFixture}</code> managed by this fixture is displaying a plain message.
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
    Integer key = messageType;
    if (!messageMap.containsKey(key)) 
      throw new IllegalArgumentException(concat("The message type <", key, "> is not valid"));
    return messageMap.get(key);
  }

  /**
   * Simulates a user pressing the given keys on the <code>{@link JOptionPane}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  @Override public final JOptionPaneFixture pressKeys(int... keyCodes) {
    return (JOptionPaneFixture)doPressKeys(keyCodes);
  }
  
  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JOptionPane</code> is not visible.
   */
  @Override public final JOptionPaneFixture requireVisible() {
    return (JOptionPaneFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JOptionPane</code> is visible.
   */
  @Override public final JOptionPaneFixture requireNotVisible() {
    return (JOptionPaneFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JOptionPane</code> is disabled.
   */
  @Override public final JOptionPaneFixture requireEnabled() {
    return (JOptionPaneFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JOptionPane}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JOptionPane</code> is enabled.
   */
  @Override public final JOptionPaneFixture requireDisabled() {
    return (JOptionPaneFixture)assertDisabled();
  }

  @Override protected String formattedTarget() {
    return target.getClass().getName();
  }
}
