/*
 * Created on Jan 26, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

import java.awt.Dimension;

import javax.swing.JTextField;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestFrame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTextComponentDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTextComponentDriverTest {

  private Robot robot;
  private JTextField textField;
  private JTextComponentDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTextComponentDriver(robot);
    MyFrame frame = new MyFrame();
    textField = frame.textField;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldDeleteText() {
    driver.deleteText(textField);
    assertThat(textField.getText()).isNullOrEmpty();
  }

  @Test public void shouldReplaceText() {
    textField.setText("Hi");
    driver.replaceText(textField, "Bye");
    assertThat(textField.getText()).isEqualTo("Bye");
  }

  @Test public void shouldSelectAllText() {
    textField.setText("Hello");
    driver.selectAll(textField);
    assertThat(textField.getSelectedText()).isEqualTo(textField.getText());
  }

  @Test public void shouldEnterText() {
    textField.setText("");
    String textToEnter = "Entering text";
    driver.enterText(textField, textToEnter);
    assertThat(textField.getText()).isEqualTo(textToEnter);
  }


  @Test public void shouldSelectTextRange() {
    driver.selectText(textField, 8, 14);
    assertThat(textField.getSelectedText()).isEqualTo("a test");
  }

  @Test public void shouldThrowErrorIfIndicesAreOutOfBoundsWhenSelectingText() {
    try {
      driver.selectText(textField, 20, 22);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().contains("Unable to get location for index '20' in javax.swing.JTextField");
    }
  }

  @Test public void shouldSelectGivenTextOnly() {
    textField.setText("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(textField.getSelectedText()).isEqualTo("llo W");
  }

  @Test public void shouldPassIfTextComponentIsEditable() {
    textField.setEditable(true);
    driver.requireEditable(textField);
  }

  @Test public void shouldFailIfTextComponentIsNotEditableAndExpectingEditable() {
    textField.setEditable(false);
    try {
      driver.requireEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  @Test public void shouldPassIfTextComponentIsNotEditable() {
    textField.setEditable(false);
    driver.requireNotEditable(textField);
  }

  @Test public void shouldFailIfTextComponentIsEditableAndExpectingNotEditable() {
    textField.setEditable(true);
    try {
      driver.requireNotEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  @Test public void shouldPassIfHasExpectedText() {
    textField.setText("Hi");
    driver.requireText(textField, "Hi");
  }

  @Test public void shouldFailIfDoesNotHaveExpectedText() {
    textField.setText("Hi");
    try {
      driver.requireText(textField, "Bye");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expected:<'Bye'> but was:<'Hi'>");
    }
  }

  @Test public void shouldPassIfEmpty() {
    textField.setText("");
    driver.requireEmpty(textField);
  }

  @Test public void shouldPassIfTextIsNull() {
    textField.setText(null);
    driver.requireEmpty(textField);
  }

  @Test public void shouldFailIfNotEmpty() {
    textField.setText("Hi");
    try {
      driver.requireEmpty(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expecting empty String but was:<'Hi'>");
    }
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField("This is a test");

    MyFrame() {
      super(JTextComponentDriverTest.class);
      add(textField);
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
