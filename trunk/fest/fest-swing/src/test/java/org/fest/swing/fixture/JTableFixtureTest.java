/*
 * Created on Jul 12, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.fixture.TableCell.TableCellBuilder.row;
import static org.fest.swing.fixture.TestTable.cellValue;
import static org.fest.swing.fixture.TestTable.columnNames;

import static org.fest.util.Arrays.array;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableFixture}</code>.
 *
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
public class JTableFixtureTest extends ComponentFixtureTestCase<JTable> {

  private static final int COLUMN_COUNT = 6;
  private static final int ROW_COUNT = 10;
  
  private TestTable target;
  private JTableFixture targetFixture;
  
  private TestTable dropTarget;
  private JTableFixture dropTargetFixture;
  
  @Test(dataProvider = "cellsToSelect") 
  public void shouldSelectCell(TableCell cell) {
    targetFixture.selectCell(cell);
    assertThatCellIsSelected(cell);
  }

  @Test(dataProvider = "cellsToSelect") 
  public void shouldReturnValueOfGivenCell(TableCell cell) {
    assertThat(targetFixture.contentsAt(cell)).isEqualTo(cellValue(cell));
  }
  
  @Test(dependsOnMethods = "shouldSelectCell", dataProvider = "cellsToSelect")
  public void shouldReturnValueOfSelectedCell(TableCell cell) {
    targetFixture.selectCell(cell);
    assertThat(targetFixture.selectionContents()).isEqualTo(cellValue(cell));
  }
  
  @DataProvider(name = "cellsToSelect")
  public Object[][] cellsToSelect() {
    return new Object[][] {
        { row(6).column(5) },  
        { row(0).column(0) },
        { row(8).column(3) },
        { row(5).column(2) }
    };
  }
  
  @Test public void shouldReturnCellWithGivenRowAndColumn() {
    JTableCellFixture cell = targetFixture.cell(row(1).column(2));
    assertThat(cell.row()).isEqualTo(1);
    assertThat(cell.column()).isEqualTo(2);
  }
  
  @Test public void shouldSelectMultipleRows() {
    target.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
    TableCell[] cells = array(row(6).column(5), row(8).column(3), row(9).column(3));    
    targetFixture.selectCells(cells);
    assertThat(targetFixture.target.getSelectedRowCount()).isEqualTo(cells.length);
    for (TableCell c : cells) assertThatCellIsSelected(c);
  }
  
  private void assertThatCellIsSelected(TableCell cell) {
    assertThat(targetFixture.target.isRowSelected(cell.row)).isTrue();
    assertThat(targetFixture.target.isColumnSelected(cell.column)).isTrue();
  }
  
  @Test public void shouldReturnNullAsSelectionContentIfNoSelectedCell() {
    assertThat(targetFixture.target.getSelectedRowCount()).isZero();
    assertThat(targetFixture.selectionContents()).isNull();
  }

  @Test public void shouldDragAndDrop() throws Exception {
    int sourceRowCount = target.getRowCount();
    int destinationRowCount = dropTarget.getRowCount();
    targetFixture.drag(row(3).column(0));
    dropTargetFixture.drop(row(1).column(0));
    assertThat(target.getRowCount()).isEqualTo(sourceRowCount - 1);
    assertThat(target.getValueAt(3, 0)).isEqualTo(cellValue(4, 0));
    assertThat(dropTarget.getRowCount()).isEqualTo(destinationRowCount + 1);
    assertThat(dropTarget.getValueAt(2, 0)).isEqualTo(cellValue(3, 0));
  }
  
  protected ComponentFixture<JTable> createFixture() {
    targetFixture = new JTableFixture(robot(), "target");
    return targetFixture;
  }

  protected JTable createTarget() {
    target = new TestTable("target", ROW_COUNT, COLUMN_COUNT);
    return target;
  }
  
  @Override protected Component decorateBeforeAddingToWindow(JTable target) {
    return decorate(target);
  }

  @Override protected void afterSetUp() {
    int rowCount = 2;
    Object[][] data = new Object[rowCount][COLUMN_COUNT];
    for (int i = 0; i < rowCount; i++)
      for (int j = 0; j < COLUMN_COUNT; j++)
        data[i][j] = cellValue(i + ROW_COUNT, j); 
    dropTarget = new TestTable("dropTarget", data, columnNames(COLUMN_COUNT));
    dropTargetFixture = new JTableFixture(robot(), dropTarget);
    window().add(decorate(dropTarget));
    window().setSize(new Dimension(600, 400));
  }

  private Component decorate(JTable table) {
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(400, 200));
    return scrollPane;
  }
}
