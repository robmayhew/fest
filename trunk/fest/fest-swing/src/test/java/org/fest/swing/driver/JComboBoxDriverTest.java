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
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.task.GetJComboBoxSelectedIndexTask.selectedIndexOf;
import static org.fest.swing.task.GetJLabelTextTask.textOf;
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
    MyFrame frame = new GuiTask<MyFrame>() {
      protected MyFrame executeInEDT() {
        return new MyFrame();
      }
    }.run();
    comboBox = frame.comboBox;
    robot.showWindow(frame);
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
    disableComboBox();
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
    disableComboBox();
    driver.selectItem(comboBox, "first");
    assertComboBoxHasNoSelection();
  }

  private void assertComboBoxHasNoSelection() {
    assertThatSelectedIndexIsEqualTo(-1);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemWithGivenTextIfAlreadySelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectIndexInComboBox(1);
    driver.selectItem(comboBox, "second");
    assertThatSelectedItemIsEqualTo("second");
  }

  private void assertThatSelectedItemIsEqualTo(String expected) {
    Object selectedItem = new GuiTask<Object>() {
      protected Object executeInEDT() {
        return comboBox.getSelectedItem();
      }
    }.run();
    assertThat(selectedItem).isEqualTo(expected);
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

  private void assertThatListContains(final JList list, final String...expected) {
    final int expectedSize = expected.length;
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        ListModel model = list.getModel();
        assertThat(model.getSize()).isEqualTo(expectedSize);
        for (int i = 0; i < expectedSize; i++)
          assertThat(model.getElementAt(i)).isEqualTo(expected[i]);
        return null;
      }
    }.run();
  }

  public void shouldPassIfHasExpectedSelection() {
    selectFirstItemInComboBox();
    driver.requireSelection(comboBox, "first");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfDoesNotHaveExpectedSelection() {
    selectFirstItemInComboBox();
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<'second'> but was:<'first'>");
    }
  }

  public void shouldFailIfDoesNotHaveAnySelectionAndExpectingSelection() {
    clearSelectionInComboBox();
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
    driver.requireNoSelection(comboBox);
  }

  private void clearSelectionInComboBox() {
    selectIndexInComboBox(-1);
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectFirstItemInComboBox();
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
    driver.requireEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsNotEditableAndExpectingEditable() {
    makeComboBoxNotEditable();
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
    driver.selectAllText(comboBox);
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectAllTextIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disableComboBox();
    driver.selectAllText(comboBox);
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectAllText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    driver.selectAllText(comboBox);
    assertSelectedTextIsEqualTo("first");
  }

  private void assertSelectedTextIsEqualTo(String expected) {
    Component editor = comboBoxEditor();
    assertThat(editor).isInstanceOf(JTextComponent.class);
    final JTextComponent textBox = (JTextComponent)editor;
    String selectedText = new GuiTask<String>() {
      protected String executeInEDT() {
        return textBox.getSelectedText();
      }
    }.run();
    assertThat(selectedText).isEqualTo(expected);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextIfComboBoxIsNotEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    driver.enterText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotEnterTextIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disableComboBox();
    driver.enterText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldEnterText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    makeComboBoxEditable();
    driver.enterText(comboBox, "Hello");
    assertThat(textInComboBox()).contains("Hello");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotReplaceTextIfComboBoxIsNotEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    driver.replaceText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotReplaceTextIfComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disableComboBox();
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
    driver.replaceText(comboBox, "Hello");
    assertThat(textInComboBox()).isEqualTo("Hello");
  }

  private void selectFirstItemInComboBox() {
    selectIndexInComboBox(0);
  }

  private void selectIndexInComboBox(final int index) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        comboBox.setSelectedIndex(index);
      }
    });
  }

  private void assertThatSelectedIndexIsEqualTo(int expected) {
    int selectedIndex = selectedIndexOf(comboBox);
    assertThat(selectedIndex).isEqualTo(expected);
  }

  private void makeComboBoxEditable() {
    setComboBoxEditable(true);
  }

  private void makeComboBoxNotEditable() {
    setComboBoxEditable(false);
  }

  private void disableComboBox() {
    setComboBoxEnabled(false);
  }

  private String textInComboBox() {
    final Component editor = comboBoxEditor();
    if (editor instanceof JLabel) return textOf((JLabel)editor);
    if (editor instanceof JTextComponent) return new GuiTask<String>() {
      protected String executeInEDT() {
        return ((JTextComponent) editor).getText();
      }
    }.run();
    return null;
  }

  private Component comboBoxEditor() {
    return new GuiTask<Component>() {
      protected Component executeInEDT() {
        return comboBox.getEditor().getEditorComponent();
      }
    }.run();
  }

  public void shouldPassIfComboBoxIsNotEditable() {
    setComboBoxEditable(false);
    driver.requireNotEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsEditableAndExpectingNotEditable() {
    setComboBoxEditable(true);
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
    setComboBoxEditable(false);
    driver.showDropDownList(comboBox);
    pause(200);
    assertDropDownVisible();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowDropDownListWhenComboBoxIsEditable(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setComboBoxEditable(true);
    driver.showDropDownList(comboBox);
    pause(200);
    assertDropDownVisible();
  }

  private void setComboBoxEditable(final boolean editable) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        comboBox.setEditable(editable);
      }
    });
  }

  private void assertDropDownVisible() {
    assertThat(isDropDownListVisible()).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotShowDropDownListWhenComboBoxIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setComboBoxEnabled(false);
    driver.showDropDownList(comboBox);
    pause(200);
    assertThat(isDropDownListVisible()).isFalse();
  }

  private void setComboBoxEnabled(final boolean enabled) {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        comboBox.setEnabled(enabled);
      }
    });
  }

  private boolean isDropDownListVisible() {
    return new GuiTask<Boolean>() {
      protected Boolean executeInEDT() {
        return comboBox.getUI().isPopupVisible(comboBox);
      }
    }.run();
  }

  private void assertCellReaderWasCalled() {
    assertThat(cellReader.called()).isTrue();
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

    public MyFrame() {
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
