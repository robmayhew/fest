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

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
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
    MyFrame frame = new MyFrame();
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
    assertThat(comboBox.getSelectedItem()).isEqualTo("third");
  }

  public void shouldNotSelectItemWithGivenIndexIfComboBoxIsNotEnabled() {
    clearAndDisableComboBox();
    driver.selectItem(comboBox, 0);
    assertThat(comboBox.getSelectedIndex()).isEqualTo(-1);
  }

  public void shouldSelectItemWithGivenText() {
    driver.selectItem(comboBox, "second");
    assertThat(comboBox.getSelectedItem()).isEqualTo("second");
    assertCellReaderWasCalled();
  }

  public void shouldNotSelectItemWithGivenTextIfComboBoxIsNotEnabled() {
    clearAndDisableComboBox();
    driver.selectItem(comboBox, "first");
    assertThat(comboBox.getSelectedIndex()).isEqualTo(-1);
  }

  private void clearAndDisableComboBox() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        comboBox.setSelectedIndex(-1);
        comboBox.setEnabled(false);
      }
    });
    assertThat(comboBox.isEnabled()).isFalse();
  }

  public void shouldNotSelectItemWithGivenTextIfAlreadySelected() {
    comboBox.setSelectedIndex(1);
    driver.selectItem(comboBox, "second");
    assertThat(comboBox.getSelectedItem()).isEqualTo("second");
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

  private void assertThatListContains(JList list, String...expected) {
    int expectedSize = expected.length;
    ListModel model = list.getModel();
    assertThat(model.getSize()).isEqualTo(expectedSize);
    for (int i = 0; i < expectedSize; i++)
      assertThat(model.getElementAt(i)).isEqualTo(expected[i]);
  }

  public void shouldPassIfHasExpectedSelection() {
    comboBox.setSelectedIndex(0);
    driver.requireSelection(comboBox, "first");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfDoesNotHaveExpectedSelection() {
    comboBox.setSelectedIndex(0);
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<'second'> but was:<'first'>");
    }
  }

  public void shouldFailIfDoesNotHaveAnySelectionAndExpectingSelection() {
    comboBox.setSelectedIndex(-1);
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("No selection");
    }
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    comboBox.setSelectedIndex(-1);
    driver.requireNoSelection(comboBox);
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    comboBox.setSelectedIndex(0);
    try {
      driver.requireNoSelection(comboBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<-1> but was:<0>");
    }
  }

  public void shouldPassIfComboBoxIsEditable() {
    comboBox.setEditable(true);
    driver.requireEditable(comboBox);
  }

  public void shouldFailIfComboBoxIsNotEditableAndExpectingEditable() {
    comboBox.setEditable(false);
    try {
      driver.requireEditable(comboBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  public void shouldNotSelectAllTextIfComboBoxIsNotEditable() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(false);
    driver.selectAllText(comboBox);
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  public void shouldNotSelectAllTextIfComboBoxIsNotEnabled() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(true);
    comboBox.setEnabled(false);
    driver.selectAllText(comboBox);
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  public void shouldSelectAllText() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(true);
    driver.selectAllText(comboBox);
    Component editor = comboBox.getEditor().getEditorComponent();
    assertThat(editor).isInstanceOf(JTextComponent.class);
    JTextComponent textBox = (JTextComponent)editor;
    assertThat(textBox.getSelectedText()).isEqualTo("first");
  }

  public void shouldNotEnterTextIfComboBoxIsNotEditable() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(false);
    driver.enterText(comboBox, "Hello");
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  public void shouldNotEnterTextIfComboBoxIsNotEnabled() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(true);
    comboBox.setEnabled(false);
    driver.enterText(comboBox, "Hello");
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  public void shouldEnterText() {
    comboBox.setEditable(true);
    driver.enterText(comboBox, "Hello");
    assertThat(textIn(comboBox)).contains("Hello");
  }

  public void shouldNotReplaceTextIfComboBoxIsNotEditable() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(false);
    driver.replaceText(comboBox, "Hello");
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  public void shouldNotReplaceTextIfComboBoxIsNotEnabled() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(true);
    comboBox.setEnabled(false);
    driver.replaceText(comboBox, "Hello");
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  public void shouldReplaceText() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(true);
    driver.replaceText(comboBox, "Hello");
    assertThat(textIn(comboBox)).isEqualTo("Hello");
  }

  private String textIn(JComboBox comboBox) {
    Component editor = comboBox.getEditor().getEditorComponent();
    if (editor instanceof JTextComponent) return ((JTextComponent)editor).getText();
    if (editor instanceof JLabel) return ((JLabel)editor).getText();
    return null;
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
    return comboBox.getUI().isPopupVisible(comboBox);
  }

  private void assertCellReaderWasCalled() {
    assertThat(cellReader.called()).isTrue();
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }
  
  private static class MyFrame extends TestFrame {
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
