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

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.GuiActionRunner.execute;
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
    MyWindow window = MyWindow.createNew();
    textField = window.textField;
    robot.showWindow(window);
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
    setTextAndDisableTextField(textField, "Hello");
    robot.waitForIdle();
    driver.deleteText(textField);
    assertThat(textOf(textField)).isEqualTo("Hello");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDeleteTextInEmptyTextComponent(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldText(textField, "");
    robot.waitForIdle();
    driver.deleteText(textField);
    assertThat(textOf(textField)).isNullOrEmpty();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldReplaceText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldText(textField, "Hi");
    robot.waitForIdle();
    driver.replaceText(textField, "Bye");
    assertThat(textOf(textField)).isEqualTo("Bye");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectAllText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldText(textField, "Hello");
    robot.waitForIdle();
    driver.selectAll(textField);
    assertThat(selectedTextOf(textField)).isEqualTo(textOf(textField));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectAllTextIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextAndDisableTextField(textField, "Hello");
    robot.waitForIdle();
    driver.selectAll(textField);
    assertThat(selectedTextOf(textField)).isNullOrEmpty();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldEnterText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextFieldText(textField, "");
    robot.waitForIdle();
    String textToEnter = "Entering text";
    driver.enterText(textField, textToEnter);
    assertThat(textOf(textField)).isEqualTo(textToEnter);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableTextField();
    robot.waitForIdle();
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
    setTextAndDisableTextField(textField, "This is a test");
    robot.waitForIdle();
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
    setTextFieldText(textField, "Hello World");
    robot.waitForIdle();
    driver.selectText(textField, "llo W");
    assertThat(selectedTextOf(textField)).isEqualTo("llo W");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectGivenTextIfTextComponentIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setTextAndDisableTextField(textField, "Hello World");
    robot.waitForIdle();
    driver.selectText(textField, "llo W");
    assertThat(selectedTextOf(textField)).isNullOrEmpty();
  }

  public void shouldPassIfTextComponentIsEditable() {
    setTextFieldEditable(textField, true);
    robot.waitForIdle();
    driver.requireEditable(textField);
  }

  public void shouldFailIfTextComponentIsNotEditableAndExpectingEditable() {
    setTextFieldEditable(textField, false);
    robot.waitForIdle();
    try {
      driver.requireEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfTextComponentIsNotEditable() {
    setTextFieldEditable(textField, false);
    robot.waitForIdle();
    driver.requireNotEditable(textField);
  }

  public void shouldFailIfTextComponentIsEditableAndExpectingNotEditable() {
    setTextFieldEditable(textField, true);
    robot.waitForIdle();
    try {
      driver.requireNotEditable(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  public void shouldPassIfHasExpectedText() {
    setTextFieldText(textField, "Hi");
    robot.waitForIdle();
    driver.requireText(textField, "Hi");
  }

  public void shouldFailIfDoesNotHaveExpectedText() {
    setTextFieldText(textField, "Hi");
    robot.waitForIdle();
    try {
      driver.requireText(textField, "Bye");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expected:<'Bye'> but was:<'Hi'>");
    }
  }

  public void shouldPassIfEmpty() {
    setTextFieldText(textField, "");
    robot.waitForIdle();
    driver.requireEmpty(textField);
  }

  public void shouldPassIfTextIsNull() {
    setTextFieldText(textField, null);
    robot.waitForIdle();
    driver.requireEmpty(textField);
  }

  public void shouldFailIfNotEmpty() {
    setTextFieldText(textField, "Hi");
    robot.waitForIdle();
    try {
      driver.requireEmpty(textField);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'text'").contains("expecting empty String but was:<'Hi'>");
    }
  }

  private void clearAndDisableTextField() {
    setTextAndDisableTextField(textField, "");
  }

  private static void setTextFieldEditable(final JTextField textField, final boolean editable) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        textField.setEditable(editable);
      }
    });
  }

  private void setTextAndDisableTextField(final JTextField textField, final String text) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        textField.setText(text);
        textField.setEnabled(false);
      }
    });
  }

  private static void setTextFieldText(final JTextField textField, final String text) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        textField.setText(text);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTextField textField = new JTextField("This is a test");

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JTextComponentDriverTest.class);
      add(textField);
    }
  }
}
