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

import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.task.GetJLabelTextTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.task.GetJComboBoxSelectedIndexTask.selectedIndexOf;
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

  public void shouldSelectItemAtGivenIndex() {
    driver.selectItem(comboBox, 2);
    assertThatSelectedItemIsEqualTo("third");
  }

  public void shouldNotSelectItemWithGivenIndexIfComboBoxIsNotEnabled() {
    clearSelectionInComboBox();
    disableComboBox();
    driver.selectItem(comboBox, 0);
    assertComboBoxHasNoSelection();
  }

  public void shouldSelectItemWithGivenText() {
    driver.selectItem(comboBox, "second");
    assertThatSelectedItemIsEqualTo("second");
    assertCellReaderWasCalled();
  }

  public void shouldNotSelectItemWithGivenTextIfComboBoxIsNotEnabled() {
    clearSelectionInComboBox();
    disableComboBox();
    driver.selectItem(comboBox, "first");
    assertComboBoxHasNoSelection();
  }

  private void assertComboBoxHasNoSelection() {
    assertThatSelectedIndexIsEqualTo(-1);
  }

  public void shouldNotSelectItemWithGivenTextIfAlreadySelected() {
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

  public void shouldReturnDropDownList() {
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

  public void shouldNotSelectAllTextIfComboBoxIsNotEditable() {
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    driver.selectAllText(comboBox);
    assertFirstItemIsSelectedInComboBox();
  }

  public void shouldNotSelectAllTextIfComboBoxIsNotEnabled() {
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disableComboBox();
    driver.selectAllText(comboBox);
    assertFirstItemIsSelectedInComboBox();
  }

  public void shouldSelectAllText() {
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

  public void shouldNotEnterTextIfComboBoxIsNotEditable() {
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    driver.enterText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  public void shouldNotEnterTextIfComboBoxIsNotEnabled() {
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disableComboBox();
    driver.enterText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  public void shouldEnterText() {
    makeComboBoxEditable();
    driver.enterText(comboBox, "Hello");
    assertThat(textInComboBox()).contains("Hello");
  }

  public void shouldNotReplaceTextIfComboBoxIsNotEditable() {
    selectFirstItemInComboBox();
    makeComboBoxNotEditable();
    driver.replaceText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  public void shouldNotReplaceTextIfComboBoxIsNotEnabled() {
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    disableComboBox();
    driver.replaceText(comboBox, "Hello");
    assertFirstItemIsSelectedInComboBox();
  }

  private void assertFirstItemIsSelectedInComboBox() {
    assertThatSelectedIndexIsEqualTo(0);
  }

  public void shouldReplaceText() {
    selectFirstItemInComboBox();
    makeComboBoxEditable();
    driver.replaceText(comboBox, "Hello");
    assertThat(textInComboBox()).isEqualTo("Hello");
  }

  private void selectFirstItemInComboBox() {
    selectIndexInComboBox(0);
  }

  private void selectIndexInComboBox(final int index) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        comboBox.setSelectedIndex(index);
        return null;
      }
    }.run();
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

  private void setComboBoxEditable(final boolean editable) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        comboBox.setEditable(editable);
        return null;
      }
    }.run();
  }

  private void disableComboBox() {
    setComboBoxEnabled(false);
  }

  private void setComboBoxEnabled(final boolean enabled) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        comboBox.setEnabled(enabled);
        return null;
      }
    }.run();
  }

  private String textInComboBox() {
    final Component editor = comboBoxEditor();
    String text = new GuiTask<String>() {
      protected String executeInEDT() {
        if (editor instanceof JLabel) return GetJLabelTextTask.textOf((JLabel)editor);
        if (editor instanceof JTextComponent) return ((JTextComponent)editor).getText();
        return null;
      }
    }.run();
    return text;
  }

  private Component comboBoxEditor() {
    return new GuiTask<Component>() {
      protected Component executeInEDT() {
        return comboBox.getEditor().getEditorComponent();
      }
    }.run();
  }

  public void shouldPassIfComboBoxIsNotEditable() {
    comboBox.setEditable(false);
    driver.requireNotEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsEditableAndExpectingNotEditable() {
    comboBox.setEditable(true);
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

  public void shouldShowDropDownListWhenComboBoxIsNotEditable() {
    comboBox.setEditable(false);
    driver.showDropDownList(comboBox);
    pause(200);
    assertDropDownVisible();
  }

  public void shouldShowDropDownListWhenComboBoxIsEditable() {
    comboBox.setEditable(true);
    driver.showDropDownList(comboBox);
    pause(200);
    assertDropDownVisible();
  }

  private void assertDropDownVisible() {
    assertThat(isDropDownListVisible()).isTrue();
  }

  public void shouldNotShowDropDownListWhenComboBoxIsNotEnabled() {
    comboBox.setEnabled(false);
    driver.showDropDownList(comboBox);
    pause(200);
    assertThat(isDropDownListVisible()).isFalse();
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
