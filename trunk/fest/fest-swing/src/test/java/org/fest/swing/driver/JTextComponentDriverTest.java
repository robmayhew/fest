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

import java.awt.Dimension;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

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

  public void shouldDeleteText() {
    driver.deleteText(textField);
    assertThat(textField.getText()).isNullOrEmpty();
  }
  
  public void shouldNotDeleteTextIfTextComponentIsNotEnabled() {
    setTextAndDisableTextField("Hello");
    driver.deleteText(textField);
    assertThat(textField.getText()).isEqualTo("Hello");
  }
  
  public void shouldDeleteTextInEmptyTextComponent() {
    textField.setText("");
    driver.deleteText(textField);
    assertThat(textField.getText()).isNullOrEmpty();
  }

  public void shouldReplaceText() {
    textField.setText("Hi");
    driver.replaceText(textField, "Bye");
    assertThat(textField.getText()).isEqualTo("Bye");
  }

  public void shouldSelectAllText() {
    textField.setText("Hello");
    driver.selectAll(textField);
    assertThat(textField.getSelectedText()).isEqualTo(textField.getText());
  }

  public void shouldNotSelectAllTextIfTextComponentIsNotEnabled() {
    setTextAndDisableTextField("Hello");
    driver.selectAll(textField);
    assertThat(textField.getSelectedText()).isNullOrEmpty();
  }
  
  public void shouldEnterText() {
    textField.setText("");
    String textToEnter = "Entering text";
    driver.enterText(textField, textToEnter);
    assertThat(textField.getText()).isEqualTo(textToEnter);
  }

  public void shouldNotEnterTextIfTextComponentIsNotEnabled() {
    clearAndDisableTextField();
    String textToEnter = "Entering text";
    driver.enterText(textField, textToEnter);
    assertThat(textField.getText()).isNullOrEmpty();
  }

  public void shouldSelectTextRange() {
    driver.selectText(textField, 8, 14);
    assertThat(textField.getSelectedText()).isEqualTo("a test");
  }

  public void shouldNotSelectTextRangeIfTextComponentIsNotEnabled() {
    setTextAndDisableTextField("This is a test");
    driver.selectText(textField, 8, 14);
    assertThat(textField.getSelectedText()).isNullOrEmpty();
  }
  
  public void shouldThrowErrorIfIndicesAreOutOfBoundsWhenSelectingText() {
    try {
      driver.selectText(textField, 20, 22);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().contains("Unable to get location for index '20' in javax.swing.JTextField");
    }
  }

  public void shouldSelectGivenTextOnly() {
    textField.setText("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(textField.getSelectedText()).isEqualTo("llo W");
  }
  
  public void shouldNotSelectGivenTextIfTextComponentIsNotEnabled() {
    setTextAndDisableTextField("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(textField.getSelectedText()).isNullOrEmpty();
  }

  public void shouldPassIfTextComponentIsEditable() {
    textField.setEditable(true);
    driver.requireEditable(textField);
  }

  public void shouldFailIfTextComponentIsNotEditableAndExpectingEditable() {
    textField.setEditable(false);
    try {
      driver.requireEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfTextComponentIsNotEditable() {
    textField.setEditable(false);
    driver.requireNotEditable(textField);
  }

  public void shouldFailIfTextComponentIsEditableAndExpectingNotEditable() {
    textField.setEditable(true);
    try {
      driver.requireNotEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  public void shouldPassIfHasExpectedText() {
    textField.setText("Hi");
    driver.requireText(textField, "Hi");
  }

  public void shouldFailIfDoesNotHaveExpectedText() {
    textField.setText("Hi");
    try {
      driver.requireText(textField, "Bye");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expected:<'Bye'> but was:<'Hi'>");
    }
  }

  public void shouldPassIfEmpty() {
    textField.setText("");
    driver.requireEmpty(textField);
  }

  public void shouldPassIfTextIsNull() {
    textField.setText(null);
    driver.requireEmpty(textField);
  }

  public void shouldFailIfNotEmpty() {
    textField.setText("Hi");
    try {
      driver.requireEmpty(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expecting empty String but was:<'Hi'>");
    }
  }

  private void clearAndDisableTextField() {
    setTextAndDisableTextField("");
  }
  
  private void setTextAndDisableTextField(final String text) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        textField.setText(text);
        textField.setEnabled(false);
      }
    });
    robot.waitForIdle();
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField("This is a test");

    MyFrame() {
      super(JTextComponentDriverTest.class);
      add(textField);
      setPreferredSize(new Dimension(200, 200));
    }
  }
  
}
