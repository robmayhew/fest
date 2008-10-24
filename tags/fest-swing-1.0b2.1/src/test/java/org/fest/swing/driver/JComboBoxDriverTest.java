/*
 * Created on Feb 24, 2008
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

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.*;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.query.JLabelTextQuery;
import org.fest.swing.query.JTextComponentTextQuery;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.driver.JComboBoxDropDownVisibleQuery.isDropDownVisible;
import static org.fest.swing.driver.JComboBoxSetEditableTask.setEditable;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.JComboBoxSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.task.JComboBoxSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.task.JComboBoxSetSelectedItemTask.setSelectedItem;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JComboBoxDriverTest {

  private Robot robot;
  private JComboBoxCellReaderStub cellReader;
  private JComboBox comboBox;
  private JComboBoxDriver driver;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    cellReader = new JComboBoxCellReaderStub();
    driver = new JComboBoxDriver(robot);
    driver.cellReader(cellReader);
    MyWindow window = MyWindow.createNew();
    comboBox = window.comboBox;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnComboBoxContents() {
    Object[] contents = driver.contentsOf(comboBox);
    assertThat(contents).isEqualTo(array("first", "second", "third"));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemAtGivenIndex(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItem(comboBox, 2);
    assertThatSelectedItemIsEqualTo("third");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemWithGivenIndexIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearSelectionInComboBox();
    disable(comboBox);
    robot.waitForIdle();
    driver.selectItem(comboBox, 0);
    assertComboBoxHasNoSelection();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemWithGivenText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItem(comboBox, "second");
    assertThatSelectedItemIsEqualTo("second");
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemWithGivenTextIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearSelectionInComboBox();
    disable(comboBox);
    robot.waitForIdle();
    driver.selectItem(comboBox, "first");
    assertComboBoxHasNoSelection();
  }

  private void assertComboBoxHasNoSelection() {
    assertThatSelectedIndexIsEqualTo(-1);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemWithGivenTextIfAlreadySelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setSelectedIndex(comboBox, 1);
    robot.waitForIdle();
    driver.selectItem(comboBox, "second");
    assertThatSelectedItemIsEqualTo("second");
  }

  private void assertThatSelectedItemIsEqualTo(String expected) {
    assertThat(selectedItemOf(comboBox)).isEqualTo(expected);
  }

  private static Object selectedItemOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() {
        return comboBox.getSelectedItem();
      }
    });
  }

  @Test(groups = GUI, expectedExceptions = LocationUnavailableException.class)
  public void shouldThrowErrorIfTextOfItemToSelectDoesNotExist() {
    driver.selectItem(comboBox, "hundred");
  }

  public void shouldReturnTextAtGivenIndex() {
    String value = driver.value(comboBox, 2);
    assertThat(value).isEqualTo("third");
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldReturnDropDownList(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.click(comboBox);
    JList dropDownList = driver.dropDownList();
    assertThatListContains(dropDownList, "first", "second", "third");
  }

  private static void assertThatListContains(final JList list, final String...expected) {
    final int expectedSize = expected.length;
    ListModel model = list.getModel();
    assertThat(model.getSize()).isEqualTo(expectedSize);
    for (int i = 0; i < expectedSize; i++)
      assertThat(model.getElementAt(i)).isEqualTo(expected[i]);
  }

  public void shouldPassIfItHasExpectedSelection() {
    selectFirstItemInComboBox();
    robot.waitForIdle();
    driver.requireSelection(comboBox, "first");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfItDoesNotHaveExpectedSelection() {
    selectFirstItemInComboBox();
    robot.waitForIdle();
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<'second'> but was:<'first'>");
    }
  }

  public void shouldFailIfItDoesNotHaveAnySelectionAndExpectingSelection() {
    clearSelectionInComboBox();
    robot.waitForIdle();
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("No selection");
    }
  }

  public void shouldPassIfItIsEditableAndHasExpectedSelection() {
    setEditable(comboBox, true);
    setSelectedItem(comboBox, "Hello World");
    robot.waitForIdle();
    driver.requireSelection(comboBox, "Hello World");
  }

  public void shouldFailIfItIsEditableAndDoesNotHaveExpectedSelection() {
    setEditable(comboBox, true);
    setSelectedItem(comboBox, "Hello World");
    robot.waitForIdle();
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<'second'> but was:<'Hello World'>");
    }
  }

  public void shouldFailIfItEditableAndDoesNotHaveAnySelectionAndExpectingSelection() {
    clearSelectionInComboBox();
    setEditable(comboBox, true);
    robot.waitForIdle();
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("No selection");
    }
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    clearSelectionInComboBox();
    robot.waitForIdle();
    driver.requireNoSelection(comboBox);
  }

  private void clearSelectionInComboBox() {
    setSelectedIndex(comboBox, (-1));
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectFirstItemInComboBox();
    robot.waitForIdle();
    try {
      driver.requireNoSelection(comboBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<-1> but was:<0>");
    }
  }

  public void shouldPassIfComboBoxIsEditable() {
    makeComboBoxEditable();
    robot.waitForIdle();
    driver.requireEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsNotEditableAndExpectingEditable() {
    makeComboBoxNotEditable();
    robot.waitForIdle();
    try {
      driver.requireEditable(comboBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectAllTextIfComboBoxIsNotEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    robot.waitForIdle();
    driver.selectAllText(comboBox);
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectAllTextIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disable(comboBox);
    robot.waitForIdle();
    driver.selectAllText(comboBox);
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectAllText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    robot.waitForIdle();
    driver.selectAllText(comboBox);
    assertSelectedTextIsEqualTo("first");
  }

  private void assertSelectedTextIsEqualTo(String expected) {
    Component editor = editorOf(comboBox);
    assertThat(editor).isInstanceOf(JTextComponent.class);
    JTextComponent textBox = (JTextComponent)editor;
    String selectedText = selectedTextOf(textBox);
    assertThat(selectedText).isEqualTo(expected);
  }

  private static String selectedTextOf(final JTextComponent textBox) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return textBox.getSelectedText();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextIfComboBoxIsNotEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    robot.waitForIdle();
    driver.enterText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disable(comboBox);
    robot.waitForIdle();
    driver.enterText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldEnterText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    makeComboBoxEditable();
    robot.waitForIdle();
    driver.enterText(comboBox, "Hello");
    assertThat(textIn(comboBox)).contains("Hello");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotReplaceTextIfComboBoxIsNotEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    robot.waitForIdle();
    driver.replaceText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotReplaceTextIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disable(comboBox);
    robot.waitForIdle();
    driver.replaceText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  private void assertFirstItemIsSelectedInComboBox() {
    assertThatSelectedIndexIsEqualTo(0);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldReplaceText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    robot.waitForIdle();
    driver.replaceText(comboBox, "Hello");
    assertThat(textIn(comboBox)).isEqualTo("Hello");
  }

  private void selectFirstItemInComboBox() {
    setSelectedIndex(comboBox, 0);
  }

  private void assertThatSelectedIndexIsEqualTo(int expected) {
    int selectedIndex = selectedIndexOf(comboBox);
    assertThat(selectedIndex).isEqualTo(expected);
  }

  private static String textIn(final JComboBox comboBox) {
    Component editor = editorOf(comboBox);
    if (editor instanceof JLabel) return JLabelTextQuery.textOf((JLabel)editor);
    if (editor instanceof JTextComponent) return JTextComponentTextQuery.textOf((JTextComponent)editor);
    return null;
  }

  private static Component editorOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return comboBox.getEditor().getEditorComponent();
      }
    });
  }

  public void shouldPassIfComboBoxIsNotEditable() {
    makeComboBoxNotEditable();
    robot.waitForIdle();
    driver.requireNotEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsEditableAndExpectingNotEditable() {
    makeComboBoxEditable();
    robot.waitForIdle();
    try {
      driver.requireNotEditable(comboBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }

  public void shouldFailIfItemIndexIsNegative() {
    try {
      driver.validateIndex(comboBox, -1);
      fail();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e).message().contains("Item index (-1) should be between [0] and [2]");
    }
  }

  public void shouldFailIfItemIndexIsGreaterThanLastItemIndex() {
    try {
      driver.validateIndex(comboBox, 6);
      fail();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e).message().contains("Item index (6) should be between [0] and [2]");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowDropDownListWhenComboBoxIsNotEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    makeComboBoxNotEditable();
    robot.waitForIdle();
    driver.showDropDownList(comboBox);
    assertDropDownVisible();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowDropDownListWhenComboBoxIsEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    makeComboBoxEditable();
    robot.waitForIdle();
    driver.showDropDownList(comboBox);
    assertDropDownVisible();
  }

  private void makeComboBoxEditable() {
    setEditable(comboBox, true);
  }

  private void makeComboBoxNotEditable() {
    setEditable(comboBox, false);
  }

  private void assertDropDownVisible() {
    assertThat(isDropDownVisible(comboBox)).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotShowDropDownListWhenComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    disable(comboBox);
    robot.waitForIdle();
    driver.showDropDownList(comboBox);
    assertThat(isDropDownVisible(comboBox)).isFalse();
  }

  private void assertCellReaderWasCalled() {
    assertThat(cellReader.called()).isTrue();
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JComboBoxDriverTest.class);
      add(comboBox);
    }
  }

  private static class JComboBoxCellReaderStub extends BasicJComboBoxCellReader {
    private boolean called;

    JComboBoxCellReaderStub() {}

    @Override public String valueAt(JComboBox comboBox, int index) {
      called = true;
      return super.valueAt(comboBox, index);
    }

    boolean called() { return called; }
  }
}
