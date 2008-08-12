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

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.query.JTextComponentTextQuery.textOf;
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
    MyFrame frame = new GuiQuery<MyFrame>() {
      protected MyFrame executeInEDT() throws Throwable {
        return new MyFrame();
      }
    }.run();
    textField = frame.textField;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDeleteText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.deleteText(textField);
    assertThat(textOf(textField)).isNullOrEmpty();
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotDeleteTextIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextAndDisableTextField("Hello");
    driver.deleteText(textField);
    assertThat(textOf(textField)).isEqualTo("Hello");
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDeleteTextInEmptyTextComponent(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldTextTo("");
    driver.deleteText(textField);
    assertThat(textOf(textField)).isNullOrEmpty();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldReplaceText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldTextTo("Hi");
    driver.replaceText(textField, "Bye");
    assertThat(textOf(textField)).isEqualTo("Bye");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectAllText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldTextTo("Hello");
    driver.selectAll(textField);
    assertThat(selectedTextOf(textField)).isEqualTo(textOf(textField));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectAllTextIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextAndDisableTextField("Hello");
    driver.selectAll(textField);
    assertThat(selectedTextOf(textField)).isNullOrEmpty();
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldEnterText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldTextTo("");
    String textToEnter = "Entering text";
    driver.enterText(textField, textToEnter);
    assertThat(textOf(textField)).isEqualTo(textToEnter);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableTextField();
    String textToEnter = "Entering text";
    driver.enterText(textField, textToEnter);
    assertThat(textOf(textField)).isNullOrEmpty();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectTextRange(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectText(textField, 8, 14);
    assertThat(selectedTextOf(textField)).isEqualTo("a test");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectTextRangeIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextAndDisableTextField("This is a test");
    driver.selectText(textField, 8, 14);
    assertThat(selectedTextOf(textField)).isNullOrEmpty();
  }
  
  public void shouldThrowErrorIfIndicesAreOutOfBoundsWhenSelectingText() {
    try {
      driver.selectText(textField, 20, 22);
      fail();
    } catch (ActionFailedException expected) {
      assertThat(expected).message().contains("Unable to get location for index '20' in javax.swing.JTextField");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectGivenTextOnly(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldTextTo("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(selectedTextOf(textField)).isEqualTo("llo W");
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectGivenTextIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextAndDisableTextField("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(selectedTextOf(textField)).isNullOrEmpty();
  }

  public void shouldPassIfTextComponentIsEditable() {
    setTextFieldEditable(true);
    driver.requireEditable(textField);
  }

  public void shouldFailIfTextComponentIsNotEditableAndExpectingEditable() {
    setTextFieldEditable(false);
    try {
      driver.requireEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfTextComponentIsNotEditable() {
    setTextFieldEditable(false);
    driver.requireNotEditable(textField);
  }

  public void shouldFailIfTextComponentIsEditableAndExpectingNotEditable() {
    setTextFieldEditable(true);
    try {
      driver.requireNotEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  public void shouldPassIfHasExpectedText() {
    setTextFieldTextTo("Hi");
    driver.requireText(textField, "Hi");
  }

  public void shouldFailIfDoesNotHaveExpectedText() {
    setTextFieldTextTo("Hi");
    try {
      driver.requireText(textField, "Bye");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expected:<'Bye'> but was:<'Hi'>");
    }
  }

  public void shouldPassIfEmpty() {
    setTextFieldTextTo("");
    driver.requireEmpty(textField);
  }

  public void shouldPassIfTextIsNull() {
    setTextFieldTextTo(null);
    driver.requireEmpty(textField);
  }

  public void shouldFailIfNotEmpty() {
    setTextFieldTextTo("Hi");
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

  private void setTextFieldEditable(final boolean editable) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        textField.setEditable(editable);
      }
    });
    robot.waitForIdle();
  }

  private void setTextFieldTextTo(final String text) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        textField.setText(text);
      }
    });
    robot.waitForIdle();
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
