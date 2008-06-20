/*
 * Created on Mar 16, 2008
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

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.ClickRecorder;
import org.fest.swing.testing.TestFrame;
import org.fest.swing.testing.TestTable;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.ClickRecorder.attachTo;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JTableHeaderDriver}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JTableHeaderDriverTest {

  private Robot robot;
  private JTableHeader tableHeader;
  private JTableHeaderDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JTableHeaderDriver(robot);
    MyFrame frame = new MyFrame();
    tableHeader = frame.table.getTableHeader();
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "indicesOutOfBound", expectedExceptions = LocationUnavailableException.class)
  public void shouldThrowErrorIfColumnIndexOutOfBounds(int columnIndex) {
    driver.clickColumn(tableHeader, columnIndex);
  }

  @DataProvider(name = "indicesOutOfBound") public Object[][] indicesOutOfBound() {
    return new Object[][] { { -1 }, { 2 } };
  }

  @Test(groups = GUI, expectedExceptions = LocationUnavailableException.class)
  public void shouldThrowErrorIfColumnNameNotMatching() {
    driver.clickColumn(tableHeader, "Hello");
  }

  @Test(groups = GUI, dataProvider = "columnIndices")
  public void shouldClickColumnUnderGivenIndex(int columnIndex) {
    ClickRecorder recorder = attachTo(tableHeader);
    driver.clickColumn(tableHeader, columnIndex);
    recorder.wasClicked();
    assertColumnClicked(recorder, columnIndex);
  }

  @DataProvider(name = "columnIndices") public Object[][] columnIndices() {
    return new Object[][] { { 0 }, { 1 } };
  }

  @Test(groups = GUI, dataProvider = "columnNames")
  public void shouldClickColumnWithName(String columnName, int columnIndex) {
    ClickRecorder recorder = attachTo(tableHeader);
    driver.clickColumn(tableHeader, columnName);
    recorder.wasClicked();
    assertColumnClicked(recorder, columnIndex);
  }

  @DataProvider(name = "columnNames") public Object[][] columnNames() {
    return new Object[][] { { "0", 0 }, { "1", 1 } };
  }

  public void shouldShowPopupMenuAtItemWithIndex() {
    JPopupMenu popupMenu = popupMenuForHeader();
    ClickRecorder recorder = attachTo(tableHeader);
    driver.showPopupMenu(tableHeader, 1);
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    assertColumnClicked(recorder, 1);
    assertThat(popupMenu.isVisible()).isTrue();
  }

  public void shouldShowPopupMenuAtItemWithName() {
    JPopupMenu popupMenu = popupMenuForHeader();
    ClickRecorder recorder = attachTo(tableHeader);
    driver.showPopupMenu(tableHeader, "1");
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    assertColumnClicked(recorder, 1);
    assertThat(popupMenu.isVisible()).isTrue();
  }

  private JPopupMenu popupMenuForHeader() {
    JPopupMenu popupMenu = new JPopupMenu();
    popupMenu.add(new JMenuItem("Frodo"));
    tableHeader.setComponentPopupMenu(popupMenu);
    return popupMenu;
  }
  
  private void assertColumnClicked(ClickRecorder recorder, int columnIndex) {
    Point pointClicked = recorder.pointClicked();
    int columnAtPoint = tableHeader.getTable().columnAtPoint(pointClicked);
    assertThat(columnAtPoint).isEqualTo(columnIndex);
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    private static final Dimension TABLE_SIZE = new Dimension(400, 200);

    final TestTable table;

    MyFrame() {
      super(JTableHeaderDriverTest.class);
      table = new TestTable(6, 2);
      add(decorate(table));
      setPreferredSize(new Dimension(600, 400));
    }

    private Component decorate(JTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(TABLE_SIZE);
      return scrollPane;
    }
  }

}
