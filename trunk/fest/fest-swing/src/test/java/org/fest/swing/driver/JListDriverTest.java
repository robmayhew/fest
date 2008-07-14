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

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestList;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.ClickRecorder.attachTo;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.util.Range.*;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JListDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JListDriverTest {

  private Robot robot;
  private JListCellReaderStub cellReader;
  private TestList dragList;
  private TestList dropList;
  private JListDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    cellReader = new JListCellReaderStub();
    driver = new JListDriver(robot);
    driver.cellReader(cellReader);
    MyFrame frame = new MyFrame();
    dragList = frame.dragList;
    dropList = frame.dropList;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnLocationOfValue() {
    Point p = driver.pointAt(dragList, "two");
    int index = dragList.locationToIndex(p);
    assertThat(index).isEqualTo(1);
    assertCellReaderWasCalled();
  }

  public void shouldReturnIndexForValue() {
    int index = driver.indexOf(dragList, "three");
    assertThat(index).isEqualTo(2);
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorIfIndexForValueNotFound() {
    try {
      driver.indexOf(dragList, "four");
      fail();
    } catch (LocationUnavailableException expected) {
      assertThat(expected).message().isEqualTo("Unable to find an element matching the value 'four'");
    }
  }

  public void shouldReturnTextOfElement() {
    Object text = driver.value(dragList, 0);
    assertThat(text).isEqualTo("one");
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorIfIndexOutOfBoundsWhenLookingForText() {
    try {
      driver.value(dragList, 6);
      fail();
    } catch (IndexOutOfBoundsException expected) {
      assertThat(expected).message().isEqualTo("Item index (6) should be between [0] and [2] (inclusive)");
    }
  }

  public void shouldReturnSelection() {
    robot.invokeAndWait(new Runnable() {
      public void run() {
        dragList.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        dragList.setSelectedIndices(new int[] { 0, 2 });
      }
    });
    String[] selection = driver.selectionOf(dragList);
    assertThat(selection).containsOnly("one", "three");
    assertCellReaderWasCalled();
  }

  public void shouldReturnListContents() {
    Object[] contents = driver.contentsOf(dragList);
    assertThat(contents).isEqualTo(array("one", "two", "three"));
    assertCellReaderWasCalled();
  }

  public void shouldSelectItemAtGivenIndex() {
    driver.selectItem(dragList, 2);
    assertThat(dragList.getSelectedValue()).isEqualTo("three");
  }

  public void shouldNotSelectItemIfAlreadySelected() {
    dragList.setSelectedIndex(1);
    driver.selectItem(dragList, 1);
    assertThat(dragList.getSelectedIndex()).isEqualTo(1);
  }
  
  public void shouldNotSelectItemAtGivenIndexIfListIsNotEnabled() {
    clearAndDisableDragList();
    driver.selectItem(dragList, 2);
    assertDragListHasNoSelection();
  }

  public void shouldSelectItemWithGivenText() {
    driver.selectItem(dragList, "two");
    assertThat(dragList.getSelectedValue()).isEqualTo("two");
    assertCellReaderWasCalled();
  }

  public void shouldNotSelectItemWithGivenTextIfListIsNotEnabled() {
    clearAndDisableDragList();
    driver.selectItem(dragList, "two");
    assertDragListHasNoSelection();
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfValuesToSelectIsNull() {
    String[] values = null;
    driver.selectItems(dragList, values);
  }
  
  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfValuesToSelectIsEmpty() {
    String[] values = new String[0];
    driver.selectItems(dragList, values);
  }

  public void shouldSelectItemsWithGivenText() {
    driver.selectItems(dragList, array("two", "three"));
    assertThat(dragList.getSelectedValues()).isEqualTo(array("two", "three"));
    assertCellReaderWasCalled();
  }

  public void shouldNotSelectItemsWithGivenTextIsListIsNotEnabled() {
    clearAndDisableDragList();
    driver.selectItems(dragList, array("two", "three"));
    assertDragListHasNoSelection();
  }

  public void shouldSelectItemsWithGivenIndices() {
    driver.selectItems(dragList, new int[] { 1, 2 });
    assertThat(dragList.getSelectedValues()).isEqualTo(array("two", "three"));
  }
  
  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfIndicesToSelectIsNull() {
    int[] indices = null;
    driver.selectItems(dragList, indices);
  }
  
  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfIndicesToSelectIsEmpty() {
    int[] indices = new int[0];
    driver.selectItems(dragList, indices);
  }

  public void shouldSelectItemsWithGivenIndicesEvenIfIndexArrayHasOneElement() {
    driver.selectItems(dragList, new int[] { 1 });
    assertThat(dragList.getSelectedValues()).isEqualTo(array("two"));
  }

  public void shouldNotSelectItemsWithGivenIndicesIfListIsNotEnabled() {
    clearAndDisableDragList();
    driver.selectItems(dragList, new int[] { 1, 2 });
    assertDragListHasNoSelection();
  }

  public void shouldSelectItemsInFluentRange() {
    driver.selectItems(dragList, from(0), to(1));
    assertThat(dragList.getSelectedValues()).isEqualTo(array("one", "two"));
  }

  public void shouldNotSelectItemsInFluentRangeIfListIsNotEnabled() {
    clearAndDisableDragList();
    driver.selectItems(dragList, from(0), to(1));
    assertDragListHasNoSelection();
  }

  public void shouldSelectItemsInGivenRange() {
    driver.selectItems(dragList, 0, 1);
    assertThat(dragList.getSelectedValues()).isEqualTo(array("one", "two"));
  }

  public void shouldNotSelectItemsInGivenRangeIfListIsNotEnabled() {
    clearAndDisableDragList();
    driver.selectItems(dragList, 0, 1);
    assertDragListHasNoSelection();
  }

  public void shouldReturnValueAtGivenIndex() {
    Object text = driver.value(dragList, 2);
    assertThat(text).isEqualTo("three");
  }

  public void shouldReturnIndexOfValue() {
    int index = driver.indexOf(dragList, "three");
    assertThat(index).isEqualTo(2);
    assertCellReaderWasCalled();
  }

  public void shouldPassIfSelectionIsEqualToExpectedOne() {
    dragList.setSelectedIndex(0);
    driver.requireSelection(dragList, "one");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfExpectingSelectionButThereIsNone() {
    dragList.setSelectedIndex(-1);
    try {
      driver.requireSelection(dragList, "one");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("No selection");
    }
  }

  public void shouldFailIfSelectionIsNotEqualToExpectedOne() {
    dragList.setSelectedIndex(1);
    try {
      driver.requireSelection(dragList, "one");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("expected:<'one'> but was:<'two'>");
    }
  }

  public void shouldPassIfSelectedItemsIsEqualToExpectedOnes() {
    dragList.setSelectedIndices(new int[] { 0, 1 });
    driver.requireSelectedItems(dragList, "one", "two");
    assertCellReaderWasCalled();
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedSelectedItemsIsNull() {
    driver.requireSelectedItems(dragList, (String[])null);
  }

  public void shouldFailIfExpectingSelectedItemsButThereIsNone() {
    dragList.setSelectedIndex(-1);
    try {
      driver.requireSelectedItems(dragList, "one", "two");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("No selection");
    }
  }

  public void shouldFailIfSelectedItemCountIsNotEqualToExpectedOnes() {
    dragList.setSelectedIndex(2);
    try {
      driver.requireSelectedItems(dragList, "one", "two");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndices#length'] expected:<2> but was:<1>");
    }
  }

  public void shouldFailIfSelectedItemsIsNotEqualToExpectedOnes() {
    dragList.setSelectedIndex(2);
    try {
      driver.requireSelectedItems(dragList, "one");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("expected:<'one'> but was:<'three'>");
    }
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    dragList.setSelectedIndex(-1);
    driver.requireNoSelection(dragList);
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    dragList.setSelectedIndex(0);
    try {
      driver.requireNoSelection(dragList);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<-1> but was:<0>");
    }
  }

  public void shouldShowPopupMenuAtItemWithValue() {
    JPopupMenu popupMenu = popupMenuFor(dragList);
    ClickRecorder recorder = attachTo(dragList);
    driver.showPopupMenu(dragList, "one");
    recorder.clicked(RIGHT_BUTTON);
    assertThat(popupMenu.isVisible()).isTrue();
    assertCellReaderWasCalled();
  }

  public void shouldShowPopupMenuAtItemWithIndex() {
    JPopupMenu popupMenu = popupMenuFor(dragList);
    ClickRecorder recorder = attachTo(dragList);
    driver.showPopupMenu(dragList, 0);
    recorder.clicked(RIGHT_BUTTON);
    assertThat(popupMenu.isVisible()).isTrue();
  }

  private JPopupMenu popupMenuFor(JList list) {
    JPopupMenu popupMenu = new JPopupMenu();
    popupMenu.add(new JMenuItem("Frodo"));
    list.setComponentPopupMenu(popupMenu);
    return popupMenu;
  }

  public void shouldDragAndDropValueUsingGivenValues() {
    driver.drag(dragList, "two");
    driver.drop(dropList, "six");
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "two"));
    assertCellReaderWasCalled();
  }

  public void shouldDrop() {
    driver.drag(dragList, "two");
    driver.drop(dropList);
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).hasSize(4);
  }

  public void shouldDragAndDropValueUsingGivenIndices() {
    driver.drag(dragList, 2);
    driver.drop(dropList, 2);
    assertThat(dragList.elements()).isEqualTo(array("one", "two"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "three"));
  }

  private void clearAndDisableDragList() {
    dragList.setSelectedIndex(-1);
    dragList.setEnabled(false);
  }

  private void assertDragListHasNoSelection() {
    assertThat(dragList.getSelectedIndex()).isEqualTo(-1);
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
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final TestList dragList = new TestList("one", "two", "three");
    final TestList dropList = new TestList("four", "five", "six");

    MyFrame() {
      super(JListDriverTest.class);
      add(decorate(dragList));
      add(decorate(dropList));
      setPreferredSize(new Dimension(300, 100));
    }

    private JScrollPane decorate(JList list) {
      JScrollPane scrollPane = new JScrollPane(list);
      scrollPane.setPreferredSize(LIST_SIZE);
      return scrollPane;
    }
  }

  private static class JListCellReaderStub extends BasicJListCellReader {
    private boolean called;

    JListCellReaderStub() {}

    @Override public String valueAt(JList list, int index) {
      called = true;
      return super.valueAt(list, index);
    }

    boolean called() { return called; }
  }
}
