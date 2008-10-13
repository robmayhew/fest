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

import java.awt.*;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.*;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestTable;
import org.fest.swing.testing.TestWindow;

import static java.awt.Color.BLUE;
import static java.awt.Font.PLAIN;
import static java.lang.Integer.parseInt;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.EventMode.ROBOT;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTableCell.cell;
import static org.fest.swing.driver.JTableCellEditableQuery.isCellEditable;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.driver.JTableClearSelectionTask.clearSelectionOf;
import static org.fest.swing.driver.JTableRowCountQuery.rowCountOf;
import static org.fest.swing.driver.JTableSelectedRowCountQuery.selectedRowCountOf;
import static org.fest.swing.factory.JTextFields.textField;
import static org.fest.swing.task.ComponentSetEnabledTask.disable;
import static org.fest.swing.task.ComponentSetPopupMenuTask.setPopupMenu;
import static org.fest.swing.testing.ClickRecorder.attachTo;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.testing.TestTable.*;

/**
 * Tests for <code>{@link JTableDriver}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTableDriverTest {

  private static final int COLUMN_COUNT = 6;
  private static final int ROW_COUNT = 10;

  private Robot robot;
  private JTableCellReaderStub cellReader;
  private TestTable dragTable;
  private TestTable dropTable;
  private JTableDriver driver;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    cellReader = new JTableCellReaderStub();
    driver = new JTableDriver(robot);
    driver.cellReader(cellReader);
    window = MyWindow.createNew();
    dragTable = window.dragTable;
    dropTable = window.dropTable;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellToValidateIsNull() {
    driver.validate(dragTable, null);
  }

  @Test(groups = GUI, dataProvider = "cellsAndEventModes")
  public void shouldSelectCell(int row, int column, EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectCell(dragTable, cell(row, column));
    assertThat(isCellSelected(dragTable, row, column)).isTrue();
  }

  @DataProvider(name = "cellsAndEventModes") public Object[][] cellsAndEventModes() {
    return new Object[][] {
        // { 6, 5, AWT },
        { 6, 5, ROBOT },
        // { 0, 0, AWT },
        { 0, 0, ROBOT },
        //{ 8, 3, AWT },
        { 8, 3, ROBOT },
        //{ 5, 2, AWT },
        { 5, 2, ROBOT }
    };
  }

  public void shouldReturnRowCount() {
    assertThat(driver.rowCountOf(dragTable)).isEqualTo(ROW_COUNT);
  }
  
  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectCellIfTableIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragTable();
    robot.waitForIdle();
    driver.selectCell(dragTable, cell(0, 0));
    assertDragTableHasNoSelection();
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArrayOfCellsToSelectIsNull() {
    JTableCell[] cells = null;
    driver.selectCells(dragTable, cells);
  }

  @Test(groups = GUI, expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfArrayOfCellsToSelectIsEmpty() {
    JTableCell[] cells = new JTableCell[0];
    driver.selectCells(dragTable, cells);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectCells(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setMultipleIntervalSelectionTo(dragTable);
    robot.waitForIdle();
    driver.selectCells(dragTable, new JTableCell[] { cell(0, 0), cell(2, 0) });
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
    assertThat(isCellSelected(dragTable, 2, 0)).isTrue();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectCellsEvenIfArrayHasOneElement(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    setMultipleIntervalSelectionTo(dragTable);
    robot.waitForIdle();
    driver.selectCells(dragTable, new JTableCell[] { cell(0, 0) });
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
  }

  private static void setMultipleIntervalSelectionTo(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectCellsIfTableIsNotEnabled(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    clearAndDisableDragTable();
    robot.waitForIdle();
    driver.selectCells(dragTable, new JTableCell[] { cell(0, 0), cell(2, 0) });
    assertDragTableHasNoSelection();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldNotSelectCellIfAlreadySelected(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.selectCell(dragTable, cell(0, 0));
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
    driver.selectCell(dragTable, cell(0, 0));
    assertThat(isCellSelected(dragTable, 0, 0)).isTrue();
  }

  private static boolean isCellSelected(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return table.isCellSelected(row, column);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldReturnValueOfGivenRowAndColumn(int row, int column) {
    String value = driver.value(dragTable, row, column);
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldReturnValueOfGivenCell(int row, int column) {
    String value = driver.value(dragTable, cell(row, column));
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldReturnValueOfSelectedCell(int row, int column) {
    driver.selectCell(dragTable, cell(row, column));
    String value = driver.selectionValue(dragTable);
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(groups = GUI, dataProvider = "cells")
  public void shouldFindCellByValue(int row, int column) {
    String value = createCellTextUsing(row, column);
    JTableCell cell = driver.cell(dragTable, value);
    assertThat(cell.row).isEqualTo(row);
    assertThat(cell.column).isEqualTo(column);
    assertCellReaderWasCalled();
  }

  public void shouldThrowErrorIfCellCannotBeFoundWithGivenValue() {
    try {
      driver.cell(dragTable, "Hello World");
      fail("Expecting an exception");
    } catch (ActionFailedException expected) {
      assertThat(expected).message().contains("Unable to find cell with value 'Hello World'");
    }
  }
  
  @DataProvider(name = "cells") public Object[][] cells() {
    return new Object[][] { { 6, 5 }, { 0, 0 }, { 8, 3 }, { 5, 2 } };
  }

  @Test(groups = GUI, dataProvider = "columnNames")
  public void shouldReturnColumnIndexGivenName(String columnName) {
    assertThat(driver.columnIndex(dragTable, columnName)).isEqualTo(parseInt(columnName));
  }

  @DataProvider(name = "columnNames") public Object[][] columnNameArray() {
    return new Object[][] { { "0" }, { "1" }, { "2" }, { "3" } };
  }

  public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    clearSelectionOf(dragTable);
    robot.waitForIdle();
    driver.requireNoSelection(dragTable);
  }

  public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    selectFirstCellOf(dragTable);
    robot.waitForIdle();
    try {
      driver.requireNoSelection(dragTable);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selection'")
                             .contains("expected no selection but was:<rows=[0], columns=[0]>");
    }
  }

  private static void selectFirstCellOf(final JTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.changeSelection(0, 0, false, false);
      }
    });
  }

  public void shouldReturnNullAsSelectionContentIfNoSelectedCell() {
    assertThat(selectedRowCountOf(dragTable)).isZero();
    assertThat(driver.selectionValue(dragTable)).isNull();
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldDragAndDrop(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    int dragRowCount = rowCountOf(dragTable);
    int dropRowCount = rowCountOf(dropTable);
    driver.drag(dragTable, cell(3, 0));
    driver.drop(dropTable, cell(1, 0));
    assertThat(rowCountOf(dragTable)).isEqualTo(dragRowCount - 1);
    assertThat(cellValueOf(dragTable, 3, 0)).isEqualTo(createCellTextUsing(4, 0));
    assertThat(rowCountOf(dropTable)).isEqualTo(dropRowCount + 1);
    assertThat(cellValueOf(dropTable, 2, 0)).isEqualTo(createCellTextUsing(3, 0));
  }

  public void shouldPassIfCellValueIsEqualToExpected() {
    driver.requireCellValue(dragTable, cell(0, 0), "0-0");
  }

  public void shouldFailIfCellValueIsNotEqualToExpected() {
    try {
      driver.requireCellValue(dragTable, cell(0, 0), "0-1");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("[row=0, column=0]")
                             .contains("property:'value'")
                             .contains("expected:<'0-1'> but was:<'0-0'>");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldShowPopupMenuAtCell(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    final JPopupMenu popupMenu = new JPopupMenu();
    popupMenu.add(new JMenuItem("Leia"));
    setPopupMenu(dragTable, popupMenu);
    robot.waitForIdle();
    ClickRecorder recorder = attachTo(dragTable);
    driver.showPopupMenuAt(dragTable, cell(0, 1));
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    Point pointClicked = recorder.pointClicked();
    Point pointAtCell = new JTableLocation().pointAt(dragTable, 0, 1);
    assertThat(pointClicked).isEqualTo(pointAtCell);
  }

  public void shouldReturnCellFont() {
    final JTableCellReader mockCellReader = mockCellReader();
    final Font font = new Font("SansSerif", PLAIN, 8);
    driver.cellReader(mockCellReader);
    new EasyMockTemplate(mockCellReader) {
      protected void expectations() {
        expect(mockCellReader.fontAt(dragTable, 0, 0)).andReturn(font);
      }

      protected void codeToTest() {
        Font result = driver.font(dragTable, cell(0, 0));
        assertThat(result).isSameAs(font);
      }
    }.run();
  }

  public void shouldReturnCellBackgroundColor() {
    final JTableCellReader mockCellReader = mockCellReader();
    final Color background = BLUE;
    driver.cellReader(mockCellReader);
    new EasyMockTemplate(mockCellReader) {
      protected void expectations() {
        expect(mockCellReader.backgroundAt(dragTable, 0, 0)).andReturn(background);
      }

      protected void codeToTest() {
        Color result = driver.background(dragTable, cell(0, 0));
        assertThat(result).isSameAs(background);
      }
    }.run();
  }

  public void shouldReturnCellForegroundColor() {
    final JTableCellReader mockCellReader = mockCellReader();
    final Color foreground = BLUE;
    driver.cellReader(mockCellReader);
    new EasyMockTemplate(mockCellReader) {
      protected void expectations() {
        expect(mockCellReader.foregroundAt(dragTable, 0, 0)).andReturn(foreground);
      }

      protected void codeToTest() {
        Color result = driver.foreground(dragTable, cell(0, 0));
        assertThat(result).isSameAs(foreground);
      }
    }.run();
  }

  private JTableCellReader mockCellReader() {
    return createMock(JTableCellReader.class);
  }

  @Test public void shouldEnterValueInCell() {
    dragTable.cellEditable(0, 0, true);
    robot.waitForIdle();
    final JTableCellWriter cellWriter = mockCellWriter();
    final String value = "Hello";
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.enterValue(dragTable, 0, 0, value);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.enterValueInCell(dragTable, cell(0, 0), value);
      }
    }.run();
  }

  @Test(groups = GUI, expectedExceptions = AssertionError.class)
  public void shouldThrowErrorIfTableIsNotEnabledWhenEditingCell() {
    disable(dragTable);
    robot.waitForIdle();
    driver.enterValueInCell(dragTable, cell(0, 0), "Hello");
  }

  @Test(groups = GUI, expectedExceptions = AssertionError.class)
  public void shouldThrowErrorIfCellToEditIsNotEditable() {
    JTableCell cell = cell(0, 0);
    assertThat(isCellEditable(dragTable, cell)).isFalse();
    driver.enterValueInCell(dragTable, cell, "Hello");
  }

  @Test public void shouldReturnEditorComponentInCell() {
    final JTableCellWriter cellWriter = mockCellWriter();
    final Component editor = textField().withText("Hello").createNew();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        expect(cellWriter.editorForCell(dragTable, 0, 0)).andReturn(editor);
      }

      protected void codeToTest() {
        Component result = driver.cellEditor(dragTable, cell(0, 0));
        assertThat(result).isSameAs(editor);
      }
    }.run();
  }

  @Test public void shouldStartCellEditing() {
    final JTableCellWriter cellWriter = mockCellWriter();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.startCellEditing(dragTable, 0, 0);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.startCellEditing(dragTable, cell(0,0));
      }
    }.run();
  }

  @Test public void shouldStopCellEditing() {
    final JTableCellWriter cellWriter = mockCellWriter();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.stopCellEditing(dragTable, 0, 0);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.stopCellEditing(dragTable, cell(0,0));
      }
    }.run();
  }

  @Test public void shouldCancelCellEditing() {
    final JTableCellWriter cellWriter = mockCellWriter();
    driver.cellWriter(cellWriter);
    new EasyMockTemplate(cellWriter) {
      protected void expectations() {
        cellWriter.cancelCellEditing(dragTable, 0, 0);
        expectLastCall().once();
      }

      protected void codeToTest() {
        driver.cancelCellEditing(dragTable, cell(0,0));
      }
    }.run();
  }

  private JTableCellWriter mockCellWriter() {
    return createMock(JTableCellWriter.class);
  }

  public void shouldPassIfCellIsEditableAsAnticipated() {
    dragTable.cellEditable(0, 0, true);
    robot.waitForIdle();
    driver.requireEditable(dragTable, cell(0, 0));
  }

  public void shouldFailIfCellIsNotEditableAndExpectingEditable() {
    dragTable.cellEditable(0, 0, false);
    robot.waitForIdle();
    try {
      driver.requireEditable(dragTable, cell(0, 0));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("[row=0, column=0]")
                             .contains("property:'editable'")
                             .contains("expected:<true> but was:<false>");
    }
  }

  public void shouldPassIfCellIsNotEditableAsAnticipated() {
    dragTable.cellEditable(0, 0, false);
    robot.waitForIdle();
    driver.requireNotEditable(dragTable, cell(0, 0));
  }

  public void shouldFailIfCellIsEditableAndExpectingNotEditable() {
    dragTable.cellEditable(0, 0, true);
    robot.waitForIdle();
    try {
      driver.requireNotEditable(dragTable, cell(0, 0));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("[row=0, column=0]")
                             .contains("property:'editable'")
                             .contains("expected:<false> but was:<true>");
    }
  }

  private void assertCellReaderWasCalled() {
    assertThat(cellReader.called()).isTrue();
  }

  private void clearAndDisableDragTable() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        dragTable.clearSelection();
        dragTable.setEnabled(false);
      }
    });
  }

  private void assertDragTableHasNoSelection() {
    assertThat(selectedRowCountOf(dragTable)).isEqualTo(0);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellReaderIsNull() {
    driver.cellReader(null);
  }

  @Test(groups = GUI, expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfCellWriterIsNull() {
    driver.cellWriter(null);
  }

  public void shouldReturnJTableHeader() {
    assertThat(driver.tableHeaderOf(dragTable)).isSameAs(window.dragTableHeader);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final Dimension TABLE_SIZE = new Dimension(400, 100);

    final TestTable dragTable = new TestTable(ROW_COUNT, COLUMN_COUNT);
    final TestTable dropTable = new TestTable(dropTableData(2, COLUMN_COUNT), columnNames(COLUMN_COUNT));

    final JTableHeader dragTableHeader;

    static MyWindow createNew() {
      return new MyWindow();
    }

    private static Object[][] dropTableData(int rowCount, int columnCount) {
      Object[][] data = new Object[rowCount][columnCount];
      for (int i = 0; i < rowCount; i++)
        for (int j = 0; j < columnCount; j++)
          data[i][j] = createCellTextUsing(ROW_COUNT + i, j);
      return data;
    }

    private MyWindow() {
      super(JTableDriverTest.class);
      add(decorate(dragTable));
      add(decorate(dropTable));
      dragTableHeader = dragTable.getTableHeader();
      setPreferredSize(new Dimension(600, 400));
    }

    private Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }

  private static class JTableCellReaderStub extends BasicJTableCellReader {
    private boolean called;

    JTableCellReaderStub() {}

    @Override public String valueAt(JTable table, int row, int column) {
      called = true;
      return super.valueAt(table, row, column);
    }

    boolean called() { return called; }
  }
}
