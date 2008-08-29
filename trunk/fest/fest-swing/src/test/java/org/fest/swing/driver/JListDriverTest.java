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

import org.fest.swing.core.*;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestList;
import org.fest.swing.testing.TestWindow;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JListSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.factory.JMenuItems.menuItem;
import static org.fest.swing.factory.JPopupMenus.popupMenu;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.task.ComponentSetPopupMenuTask.setPopupMenu;
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
    MyWindow window = MyWindow.newWindow();
    dragList = window.dragList;
    dropList = window.dropList;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnLocationOfValue() {
    Point p = driver.pointAt(dragList, "two");
    int index = locationToIndex(dragList, p);
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
    final TestList list = dragList;
    final int[] indices = new int[] { 0, 2 };
    select(list, indices);
    String[] selection = driver.selectionOf(dragList);
    assertThat(selection).containsOnly("one", "three");
    assertCellReaderWasCalled();
  }

  private static void select(final JList list, final int[] indices) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        list.setSelectedIndices(indices);
      }
    });
  }

  public void shouldReturnListContents() {
    Object[] contents = driver.contentsOf(dragList);
    assertThat(contents).isEqualTo(array("one", "two", "three"));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldClickItemWithGivenText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectIndex(dragList, -1);
    ClickRecorder recorder = ClickRecorder.attachTo(dragList);
    driver.clickItem(dragList, "two", RIGHT_BUTTON, 2);
    assertThat(recorder).clicked(RIGHT_BUTTON)
                        .timesClicked(2);
    Point pointClicked = recorder.pointClicked();
    assertThat(locationToIndex(dragList, pointClicked)).isEqualTo(1);
  }

  private static void selectIndex(final JList list, final int index) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setSelectedIndex(index);
      }
    });
  }

  private static int locationToIndex(final JList list, final Point p) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() throws Throwable {
        return list.locationToIndex(p);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemAtGivenIndex(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItem(dragList, 2);
    assertThat(selectedValueOf(dragList)).isEqualTo("three");
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemIfAlreadySelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    selectIndex(dragList, 1);
    driver.selectItem(dragList, 1);
    assertThat(selectedIndexOf(dragList)).isEqualTo(1);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemAtGivenIndexIfListIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragList();
    driver.selectItem(dragList, 2);
    assertDragListHasNoSelection();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemWithGivenText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItem(dragList, "two");
    assertThat(selectedValueOf(dragList)).isEqualTo("two");
    assertCellReaderWasCalled();
  }

  private static Object selectedValueOf(final JList list) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() throws Throwable {
        return list.getSelectedValue();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemWithGivenTextIfListIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemsWithGivenText(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItems(dragList, array("two", "three"));
    assertThat(selectedItemsOf(dragList)).isEqualTo(array("two", "three"));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemsWithGivenTextIsListIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragList();
    driver.selectItems(dragList, array("two", "three"));
    assertDragListHasNoSelection();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemsWithGivenIndices(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItems(dragList, new int[] { 1, 2 });
    assertThat(selectedItemsOf(dragList)).isEqualTo(array("two", "three"));
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

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemsWithGivenIndicesEvenIfIndexArrayHasOneElement(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItems(dragList, new int[] { 1 });
    assertThat(selectedItemsOf(dragList)).isEqualTo(array("two"));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemsWithGivenIndicesIfListIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragList();
    driver.selectItems(dragList, new int[] { 1, 2 });
    assertDragListHasNoSelection();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemsInFluentRange(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItems(dragList, from(0), to(1));
    assertThat(selectedItemsOf(dragList)).isEqualTo(array("one", "two"));
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemsInFluentRangeIfListIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragList();
    driver.selectItems(dragList, from(0), to(1));
    assertDragListHasNoSelection();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemsInGivenRange(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectItems(dragList, 0, 1);
    assertThat(selectedItemsOf(dragList)).isEqualTo(array("one", "two"));
  }

  private static Object[] selectedItemsOf(final JList list) {
    return execute(new GuiQuery<Object[]>() {
      protected Object[] executeInEDT() throws Throwable {
        return list.getSelectedValues();
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectItemsInGivenRangeIfListIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
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
    selectIndex(dragList, 0);
    driver.requireSelection(dragList, "one");
    assertCellReaderWasCalled();
  }

  public void shouldFailIfExpectingSelectionButThereIsNone() {
    selectIndex(dragList, -1);
    try {
      driver.requireSelection(dragList, "one");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("No selection");
    }
  }

  public void shouldFailIfSelectionIsNotEqualToExpectedOne() {
    selectIndex(dragList, 1);
    try {
      driver.requireSelection(dragList, "one");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("expected:<'one'> but was:<'two'>");
    }
  }

  public void shouldPassIfSelectedItemsIsEqualToExpectedOnes() {
    select(dragList, new int[] { 0, 1 });
    driver.requireSelectedItems(dragList, "one", "two");
    assertCellReaderWasCalled();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfExpectedSelectedItemsIsNull() {
    driver.requireSelectedItems(dragList, (String[])null);
  }

  public void shouldFailIfExpectingSelectedItemsButThereIsNone() {
    selectIndex(dragList, -1);
    try {
      driver.requireSelectedItems(dragList, "one", "two");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("No selection");
    }
  }

  public void shouldFailIfSelectedItemCountIsNotEqualToExpectedOnes() {
    selectIndex(dragList, 2);
    try {
      driver.requireSelectedItems(dragList, "one", "two");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndices#length'] expected:<2> but was:<1>");
    }
  }

  public void shouldFailIfSelectedItemsIsNotEqualToExpectedOnes() {
    selectIndex(dragList, 2);
    try {
      driver.requireSelectedItems(dragList, "one");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("expected:<'one'> but was:<'three'>");
    }
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    selectIndex(dragList, -1);
    driver.requireNoSelection(dragList);
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectIndex(dragList, 0);
    try {
      driver.requireNoSelection(dragList);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<-1> but was:<0>");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowPopupMenuAtItemWithValue(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    JPopupMenu popupMenu = popupMenuFor(dragList);
    ClickRecorder recorder = attachTo(dragList);
    driver.showPopupMenu(dragList, "one");
    recorder.clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowPopupMenuAtItemWithIndex(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    JPopupMenu popupMenu = popupMenuFor(dragList);
    ClickRecorder recorder = attachTo(dragList);
    driver.showPopupMenu(dragList, 0);
    recorder.clicked(RIGHT_BUTTON);
    assertThat(isVisible(popupMenu)).isTrue();
  }

  private JPopupMenu popupMenuFor(JList list) {
    JMenuItem menuItem = menuItem().withText("Frodo").createInEDT();
    JPopupMenu popupMenu = popupMenu().withMenuItems(menuItem).createInEDT();
    setPopupMenu(list, popupMenu);
    return popupMenu;
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDragAndDropValueUsingGivenValues(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.drag(dragList, "two");
    driver.drop(dropList, "six");
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "two"));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDrop(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.drag(dragList, "two");
    driver.drop(dropList);
    assertThat(dragList.elements()).isEqualTo(array("one", "three"));
    assertThat(dropList.elements()).hasSize(4);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDragAndDropValueUsingGivenIndices(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.drag(dragList, 2);
    driver.drop(dropList, 2);
    assertThat(dragList.elements()).isEqualTo(array("one", "two"));
    assertThat(dropList.elements()).isEqualTo(array("four", "five", "six", "three"));
  }

  private void clearAndDisableDragList() {
    clearAndDisableDragList(dragList);
  }

  private static void clearAndDisableDragList(final JList list) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setSelectedIndex(-1);
        list.setEnabled(false);
      }
    });
  }

  private void assertDragListHasNoSelection() {
    assertThat(selectedIndexOf(dragList)).isEqualTo(-1);
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
    private static final Dimension LIST_SIZE = new Dimension(80, 40);

    final TestList dragList = new TestList("one", "two", "three");
    final TestList dropList = new TestList("four", "five", "six");

    static MyWindow newWindow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
      });
    }

    MyWindow() {
      super(JListDriverTest.class);
      dragList.setName("dragList");
      dropList.setName("dropList");
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
