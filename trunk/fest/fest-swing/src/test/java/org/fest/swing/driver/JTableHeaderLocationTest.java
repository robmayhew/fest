/*
 * Created on Aug 11, 2008
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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestTable;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTableHeaderLocation}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTableHeaderLocationTest {

  private MyWindow window;
  private JTableHeaderLocation location;
  private JTableHeader tableHeader;
  
  @BeforeMethod public void setUp() {
    location = new JTableHeaderLocation();
    window = MyWindow.createNew();
    window.display();
    tableHeader = window.tableHeader;
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }
  
  @Test(groups = GUI, dataProvider = "columnIndices")
  public void shouldReturnPointAtHeaderByIndex(int index) {
    Point point = location.pointAt(tableHeader, index);
    assertThat(point).isEqualTo(expectedPoint(index));
  }

  @DataProvider(name = "columnIndices") public Object[][] columnIndices() {
    return new Object[][] { { 0 }, { 1 } };
  }

  @Test(groups = GUI, dataProvider = "columnNames")
  public void shouldClickColumnWithName(String columnName, int columnIndex) {
    Point point = location.pointAt(tableHeader, columnName);
    assertThat(point).isEqualTo(expectedPoint(columnIndex));
  }

  @DataProvider(name = "columnNames") public Object[][] columnNames() {
    return new Object[][] { { "0", 0 }, { "1", 1 } };
  }

  private Point expectedPoint(int index) {
    final JTableHeader header = tableHeader;
    Rectangle r = rectOf(header, index);
    Point expected = new Point(r.x + r.width / 2, r.y + r.height / 2);
    return expected;
  }

  private static Rectangle rectOf(final JTableHeader tableHeader, final int index) {
    return execute(new GuiQuery<Rectangle>() {
      protected Rectangle executeInEDT() {
        return tableHeader.getHeaderRect(index);
      }
    });
  }

  @Test(groups = GUI, dataProvider = "indicesOutOfBound", expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfColumnIndexOutOfBounds(int columnIndex) {
    location.pointAt(tableHeader, columnIndex);
  }

  @DataProvider(name = "indicesOutOfBound") public Object[][] indicesOutOfBound() {
    return new Object[][] { { -1 }, { 2 } };
  }

  @Test(groups = GUI, expectedExceptions = LocationUnavailableException.class)
  public void shouldThrowErrorIfColumnNameNotMatching() {
    location.pointAt(tableHeader, "Hello");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private static final Dimension TABLE_SIZE = new Dimension(400, 200);

    final TestTable table;
    final JTableHeader tableHeader;

    static MyWindow createNew() {
      return new MyWindow(); 
    }

    private MyWindow() {
      super(JTableHeaderLocationTest.class);
      table = new TestTable(6, 2);
      tableHeader = table.getTableHeader();
      add(decorate(table));
      setPreferredSize(new Dimension(600, 400));
    }

    private static Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }

}
