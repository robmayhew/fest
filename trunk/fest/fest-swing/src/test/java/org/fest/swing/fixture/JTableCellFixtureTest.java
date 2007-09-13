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

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.fixture.TestTable.cellValue;

import org.fest.swing.RobotFixture;
import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    cell = new JTableCellFixture(tableFixture(), ROW, COLUMN);
    assertCellNotSelected();
  }

  private JTableFixture tableFixture() {
    return new JTableFixture(robot, window.table);
  }
  
  private void assertCellNotSelected() {
    assertThat(window.table.isRowSelected(cell.row())).isFalse();
    assertThat(window.table.isColumnSelected(cell.column())).isFalse();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldSelectCell() {
    cell.select();
    assertCellSelected();
  }

  private void assertCellSelected() {
    assertThat(window.table.isRowSelected(cell.row())).isTrue();
    assertThat(window.table.isColumnSelected(cell.column())).isTrue();
  }

  @Test public void shouldClickCell() {
    cell.click();
    assertCellSelected();
  }
  
  @Test public void shouldDoubleClickCell() {
    ComponentEvents events = ComponentEvents.attachTo(window.table);
    cell.doubleClick();
    assertCellSelected();
    assertThat(events.doubleClicked()).isTrue();
  }
  
  @Test public void shouldReturnCellContent() {
    assertThat(cell.contents()).isEqualTo(cellValue(ROW, COLUMN));
  }
  
  @Test public void shouldShowPopupMenuFromCell() {
    window.table.addMouseListener(new MouseAdapter() {
      @Override public void mouseReleased(MouseEvent e) {
        assertThat(e.isPopupTrigger()).isTrue();
        Point point = e.getPoint();
        assertThat(window.table.rowAtPoint(point)).isEqualTo(cell.row());
        assertThat(window.table.columnAtPoint(point)).isEqualTo(cell.column());
      }      
    });
    cell.showPopupMenu();
    assertThat(window.popupMenu.isVisible()).isTrue();
  }
  
  private static class MainWindow extends TestFrame {
    private static final long serialVersionUID = 1L;

    private static final int COLUMN_COUNT = 6;
    private static final int ROW_COUNT = 5;

    final TestTable table = new TestTable("table", ROW_COUNT, COLUMN_COUNT);
    final JPopupMenu popupMenu = new JPopupMenu();
    
    MainWindow(Class testClass) {
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
