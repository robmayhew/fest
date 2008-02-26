/*
 * Created on Sep 10, 2007
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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.assertions.AssertExtension;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestTable;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.fixture.TableCell.TableCellBuilder.row;
import static org.fest.swing.testing.TestTable.createCellTextUsing;

/**
 * Tests for <code>{@link JTableCellFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JTableCellFixtureTest {

  private static final int COLUMN = 4;
  private static final int ROW = 2;
  
  private RobotFixture robot;
  private JTableCellFixture cell;
  private MainWindow window;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    window = new MainWindow(getClass());
    robot.showWindow(window);
    cell = new JTableCellFixture(tableFixture(), row(ROW).column(COLUMN));
    assertThat(cell()).isNotSelected();
  }

  private JTableFixture tableFixture() {
    return new JTableFixture(robot, window.table);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldSelectCell() {
    cell.select();
    assertThat(cell()).isSelected();
  }

  @Test public void shouldClickCell() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.table);
    cell.click();
    assertThat(recorder).wasClicked();
    assertThat(cell()).isAt(recorder.pointClicked());
  }
  
  @Test public void shouldDoubleClickCell() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.table);
    cell.doubleClick();
    assertThat(recorder).wasDoubleClicked();
    assertThat(cell()).isAt(recorder.pointClicked());
  }
  
  @Test public void shouldRightClickCell() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.table);
    cell.rightClick();
    assertThat(recorder).wasRightClicked();
    assertThat(cell()).isAt(recorder.pointClicked());
  }

  @Test public void shouldReturnCellContent() {
    assertThat(cell.contents()).isEqualTo(createCellTextUsing(ROW, COLUMN));
  }
  
  @Test public void shouldShowPopupMenuFromCell() {
    window.table.addMouseListener(new MouseAdapter() {
      @Override public void mouseReleased(MouseEvent e) {
        assertThat(e.isPopupTrigger()).isTrue();
        assertThat(cell()).isAt(e.getPoint());
      }      
    });
    cell.showPopupMenu();
    assertThat(window.popupMenu.isVisible()).isTrue();
  }
  
  private CellAssert cell() {
    return new CellAssert(window.table, cell);
  }
  
  private static class CellAssert implements AssertExtension {
    private final JTable table;
    private final JTableCellFixture cell;

    CellAssert(JTable table, JTableCellFixture cell) {
      this.table = table;
      this.cell = cell;
    }
    
    CellAssert isAt(Point p) {
      assertThat(cell.row()).isEqualTo(table.rowAtPoint(p));
      assertThat(cell.column()).isEqualTo(table.columnAtPoint(p));
      return this;
    }
    
    CellAssert isNotSelected() {
      assertThat(rowSelected()).isFalse();
      assertThat(columnSelected()).isFalse();
      return this;
    }

    CellAssert isSelected() {
      assertThat(rowSelected()).isTrue();
      assertThat(columnSelected()).isTrue();
      return this;
    }

    private boolean rowSelected() { return table.isRowSelected(cell.row()); }
    private boolean columnSelected() { return table.isColumnSelected(cell.column()); }
  }
  
  private static class MainWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    private static final int COLUMN_COUNT = 6;
    private static final int ROW_COUNT = 5;

    final TestTable table = new TestTable("table", ROW_COUNT, COLUMN_COUNT);
    final JPopupMenu popupMenu = new JPopupMenu();
    
    MainWindow(Class<?> testClass) {
      super(testClass);
      addTable(table);
      table.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("First"));
    }
    
    private void addTable(TestTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(new Dimension(400, 200));
      add(scrollPane);
    }
  }
}
