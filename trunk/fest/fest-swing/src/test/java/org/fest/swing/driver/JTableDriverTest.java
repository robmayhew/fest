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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestTable;

import static java.awt.Color.BLUE;
import static java.awt.Font.PLAIN;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTableCell.cell;
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

  private Robot robot;
  private JTableCellReaderStub cellReader;
  private TestTable dragTable;
  private TestTable dropTable;
  private JTableDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    cellReader = new JTableCellReaderStub();
    driver = new JTableDriver(robot);
    driver.cellReader(cellReader);
    MyFrame frame = new MyFrame();
    dragTable = frame.dragTable;
    dropTable = frame.dropTable;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(dataProvider = "cells")
  public void shouldSelectCell(int row, int column) {
    driver.selectCell(dragTable, cell(row, column));
    assertThat(dragTable.isCellSelected(row, column)).isTrue();
  }

  @Test public void shouldNotSelectCellIfTableIsNotEnabled() {
    clearAndDisableDragTable();
    driver.selectCell(dragTable, cell(0, 0));
    assertDragTableHasNoSelection();
  }

  @Test public void shouldSelectCells() {
    dragTable.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
    driver.selectCells(dragTable, cell(0, 0), cell(2, 0));
    assertThat(dragTable.isCellSelected(0, 0)).isTrue();
    assertThat(dragTable.isCellSelected(2, 0)).isTrue();
  }

  @Test public void shouldNotSelectCellsIfTableIsNotEnabled() {
    clearAndDisableDragTable();
    driver.selectCells(dragTable, cell(0, 0), cell(2, 0));
    assertDragTableHasNoSelection();
  }

  @Test(dependsOnMethods = "shouldSelectCell")
  public void shouldNotSelectCellIfAlreadySelected() {
    driver.selectCell(dragTable, cell(0, 0));
    assertThat(dragTable.isCellSelected(0, 0)).isTrue();
    driver.selectCell(dragTable, cell(0, 0));
    assertThat(dragTable.isCellSelected(0, 0)).isTrue();
  }

  @Test(dataProvider = "cells")
  public void shouldReturnValueOfGivenRowAndColumn(int row, int column) {
    String value = driver.value(dragTable, row, column);
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(dataProvider = "cells")
  public void shouldReturnValueOfGivenCell(int row, int column) {
    String value = driver.value(dragTable, cell(row, column));
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @Test(dependsOnMethods = "shouldSelectCell", dataProvider = "cells")
  public void shouldReturnValueOfSelectedCell(int row, int column) {
    driver.selectCell(dragTable, cell(row, column));
    String value = driver.selectionValue(dragTable);
    assertThat(value).isEqualTo(createCellTextUsing(row, column));
    assertCellReaderWasCalled();
  }

  @DataProvider(name = "cells") public Object[][] cells() {
    return new Object[][] { { 6, 5 }, { 0, 0 }, { 8, 3 }, { 5, 2 } };
  }

  @Test public void shouldPassIfDoesNotHaveSelectionAsAnticipated() {
    dragTable.clearSelection();
    driver.requireNoSelection(dragTable);
  }

  @Test public void shouldFailIfHasSelectionAndExpectingNoSelection() {
    dragTable.changeSelection(0, 0, false, false);
    try {
      driver.requireNoSelection(dragTable);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selection'")
                             .contains("expected no selection but was:<rows[0], columns[0]>");
    }
  }

  @Test public void shouldReturnNullAsSelectionContentIfNoSelectedCell() {
    assertThat(dragTable.getSelectedRowCount()).isZero();
    assertThat(driver.selectionValue(dragTable)).isNull();
  }

  @Test public void shouldDragAndDrop() throws Exception {
    int dragRowCount = dragTable.getRowCount();
    int dropRowCount = dropTable.getRowCount();
    driver.drag(dragTable, cell(3, 0));
    driver.drop(dropTable, cell(1, 0));
    assertThat(dragTable.getRowCount()).isEqualTo(dragRowCount - 1);
    assertThat(dragTable.getValueAt(3, 0)).isEqualTo(createCellTextUsing(4, 0));
    assertThat(dropTable.getRowCount()).isEqualTo(dropRowCount + 1);
    assertThat(dropTable.getValueAt(2, 0)).isEqualTo(createCellTextUsing(3, 0));
  }

  @Test public void shouldPassIfCellValueIsEqualToExpected() {
    driver.requireCellValue(dragTable, cell(0, 0), "0-0");
  }

  @Test public void shouldFailIfCellValueIsNotEqualToExpected() {
    try {
      driver.requireCellValue(dragTable, cell(0, 0), "0-1");
      fail();
    } catch (AssertionError e) {
      e.printStackTrace();
      assertThat(e).message().contains("[row=0, column=0]")
                             .contains("property:'value'")
                             .contains("expected:<'0-1'> but was:<'0-0'>");
    }
  }

  @Test public void shouldShowPopupMenuAtCell() {
    JPopupMenu popupMenu = new JPopupMenu();
    popupMenu.add(new JMenuItem("Leia"));
    dragTable.setComponentPopupMenu(popupMenu);
    ClickRecorder recorder = attachTo(dragTable);
    driver.showPopupMenuAt(dragTable, cell(0, 1));
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    Point pointClicked = recorder.pointClicked();
    Point pointAtCell = new JTableLocation().pointAt(dragTable, 0, 1);
    assertThat(pointClicked).isEqualTo(pointAtCell);
  }

  @Test public void shouldReturnCellFont() {
    final JTableCellReader cellReader = mockCellReader();
    final Font font = new Font("SansSerif", PLAIN, 8);
    driver.cellReader(cellReader);
    new EasyMockTemplate(cellReader) {
      protected void expectations() {
        expect(cellReader.fontAt(dragTable, 0, 0)).andReturn(font);
      }

      protected void codeToTest() {
        Font result = driver.font(dragTable, cell(0, 0));
        assertThat(result).isSameAs(font);
      }
    }.run();
  }

  @Test public void shouldReturnCellBackgroundColor() {
    final JTableCellReader cellReader = mockCellReader();
    final Color background = BLUE;
    driver.cellReader(cellReader);
    new EasyMockTemplate(cellReader) {
      protected void expectations() {
        expect(cellReader.backgroundAt(dragTable, 0, 0)).andReturn(background);
      }

      protected void codeToTest() {
        Color result = driver.background(dragTable, cell(0, 0));
        assertThat(result).isSameAs(background);
      }
    }.run();
  }

  @Test public void shouldReturnCellForegroundColor() {
    final JTableCellReader cellReader = mockCellReader();
    final Color foreground = BLUE;
    driver.cellReader(cellReader);
    new EasyMockTemplate(cellReader) {
      protected void expectations() {
        expect(cellReader.foregroundAt(dragTable, 0, 0)).andReturn(foreground);
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

  @Test public void shouldPassIfCellIsEditableAsAnticipated() {
    dragTable.cellEditable(0, 0, true);
    driver.requireEditable(dragTable, cell(0, 0));
  }
  
  @Test public void shouldFailIfCellIsNotEditableAndExpectingEditable() {
    dragTable.cellEditable(0, 0, false);
    try {
      driver.requireEditable(dragTable, cell(0, 0));
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("[row=0, column=0]")
                             .contains("property:'editable'")
                             .contains("expected:<true> but was:<false>");
    }
  }
  
  @Test public void shouldPassIfCellIsNotEditableAsAnticipated() {
    dragTable.cellEditable(0, 0, false);
    driver.requireNotEditable(dragTable, cell(0, 0));
  }
  
  @Test public void shouldFailIfCellIsEditableAndExpectingNotEditable() {
    dragTable.cellEditable(0, 0, true);
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
    robot.invokeAndWait(new Runnable() {
      public void run() {
        dragTable.clearSelection();
        dragTable.setEnabled(false);
      }
    });
    assertThat(dragTable.isEnabled()).isFalse();
  }

  private void assertDragTableHasNoSelection() {
    assertThat(dragTable.getSelectedRowCount()).isEqualTo(0);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private static final Dimension TABLE_SIZE = new Dimension(400, 100);

    private static final int COLUMN_COUNT = 6;
    private static final int ROW_COUNT = 10;

    final TestTable dragTable = new TestTable(ROW_COUNT, COLUMN_COUNT);
    final TestTable dropTable = new TestTable(dropTableData(2, COLUMN_COUNT), columnNames(COLUMN_COUNT));

    private static Object[][] dropTableData(int rowCount, int columnCount) {
      Object[][] data = new Object[rowCount][columnCount];
      for (int i = 0; i < rowCount; i++)
        for (int j = 0; j < columnCount; j++)
          data[i][j] = createCellTextUsing(ROW_COUNT + i, j);
      return data;
    }

    MyFrame() {
      super(JTableDriverTest.class);
      add(decorate(dragTable));
      add(decorate(dropTable));
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
