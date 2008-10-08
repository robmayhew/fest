/*
 * Created on Feb 25, 2008
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

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JSpinnerValueQuery.valueOf;
import static org.fest.swing.query.JTextComponentTextQuery.textOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JSpinnerDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JSpinnerDriverTest {

  private Robot robot;
  private JSpinner spinner;
  private JSpinnerDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JSpinnerDriver(robot);
    MyWindow window = MyWindow.createNew();
    spinner = window.spinner;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldIncrementValue(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    assertFirstValueIsSelected();
    driver.increment(spinner);
    assertThatSpinnerValueIsEqualTo("Sam");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotIncrementValueIfSpinnerIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisable(spinner);
    robot.waitForIdle();
    driver.increment(spinner);
    assertFirstValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldIncrementValueTheGivenTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    assertFirstValueIsSelected();
    driver.increment(spinner, 2);
    assertLastValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotIncrementValueTheGivenTimesIfSpinnerIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisable(spinner);
    robot.waitForIdle();
    driver.increment(spinner, 2);
    assertFirstValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToIncrementIsZeroOrNegative(int times) {
    try {
      driver.increment(spinner, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to increment the value should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDecrementValue(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.increment(spinner);
    driver.decrement(spinner);
    assertFirstValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotDecrementValueIfSpinnerIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisable(spinner);
    selectLastValue();
    robot.waitForIdle();
    driver.decrement(spinner);
    assertLastValueIsSelected();
  }

  private void assertFirstValueIsSelected() {
    assertThatSpinnerValueIsEqualTo("Frodo");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDecrementValueTheGivenTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectLastValue();
    robot.waitForIdle();
    driver.decrement(spinner, 2);
    assertFirstValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotDecrementValueTheGivenTimesIfSpinnerIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisable(spinner);
    selectLastValue();
    robot.waitForIdle();
    driver.decrement(spinner, 2);
    assertLastValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToDecrementIsZeroOrNegative(int times) {
    try {
      driver.decrement(spinner, times);
      fail();
    } catch (ActionFailedException expected) {
      String message = concat(
          "The number of times to decrement the value should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, expectedExceptions=ActionFailedException.class)
  public void shouldThrowErrorIfTextComponentEditorNotFoundWhenEnteringText() {
    setJLabelAsEditorIn(spinner);
    robot.waitForIdle();
    driver.enterText(spinner, "hello");
  }

  @Test(groups = GUI, expectedExceptions=ComponentLookupException.class)
  public void shouldThrowErrorIfTextComponentEditorNotFound() {
    setJLabelAsEditorIn(spinner);
    robot.waitForIdle();
    driver.editor(spinner);
  }

  private static void setJLabelAsEditorIn(final JSpinner spinner) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        spinner.setEditor(new JLabel());
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldEnterTextAndCommit(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.enterTextAndCommit(spinner, "Gandalf");
    assertLastValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextAndCommitIfSpinnerIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisable(spinner);
    robot.waitForIdle();
    driver.enterTextAndCommit(spinner, "Gandalf");
    assertFirstValueIsSelected();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldEnterText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setValue(spinner, "Frodo");
    robot.waitForIdle();
    driver.enterText(spinner, "Gandalf");
    JTextComponent editor = driver.editor(spinner);
    assertThat(textOf(editor)).isEqualTo("Gandalf");
    assertThatSpinnerValueIsEqualTo("Frodo");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextIfSpinnerIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisable(spinner);
    robot.waitForIdle();
    driver.enterText(spinner, "Gandalf");
    assertFirstValueIsSelected();
  }

  private void assertLastValueIsSelected() {
    assertThatSpinnerValueIsEqualTo("Gandalf");
  }

  public void shouldPassIfValueIsEqualToExpected() {
    selectLastValue();
    driver.requireValue(spinner, "Gandalf");
  }

  public void shouldFailIfValueIsNotEqualToExpected() {
    selectLastValue();
    robot.waitForIdle();
    try {
      driver.requireValue(spinner, "Frodo");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'value'")
                             .contains("expected:<'Frodo'> but was:<'Gandalf'>");
    }
  }

  private void selectLastValue() {
    setValue(spinner, "Gandalf");
  }

  private void assertThatSpinnerValueIsEqualTo(Object expected) {
    assertThat(valueOf(spinner)).isEqualTo(expected);
  }

  private static void clearAndDisable(final JSpinner spinner) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        spinner.setValue("Frodo");
        spinner.setEnabled(false);
      }
    });
  }

  private static void setValue(final JSpinner spinner, final String value) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        spinner.setValue(value);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JSpinner spinner = new JSpinner(new SpinnerListModel(array("Frodo", "Sam", "Gandalf")));

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JSpinnerDriverTest.class);
      add(spinner);
      setPreferredSize(new Dimension(160, 80));
    }
  }
}
