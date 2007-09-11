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
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.RobotFixture;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link JTableCellFixture}</code>.
 *
 * @author Alex Ruiz
 */
public class JTableCellFixtureTest {

  private static final int COLUMN_COUNT = 6;
  private static final int ROW_COUNT = 5;

  private static final int COLUMN = 4;
  private static final int ROW = 2;
  
  private TestTable table;
  private RobotFixture robot;
  private JTableCellFixture fixture;
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    table = new TestTable("table", ROW_COUNT, COLUMN_COUNT);
    MainWindow window = new MainWindow(getClass().getSimpleName(), table);
    robot.showWindow(window);
    fixture = new JTableCellFixture(tableFixture(), ROW, COLUMN);
  }

  private JTableFixture tableFixture() {
    return new JTableFixture(robot, table);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  @Test public void shouldSelectCell() {
    assertThat(table.isRowSelected(ROW)).isFalse();
    assertThat(table.isColumnSelected(COLUMN)).isFalse();
    fixture.select();
    assertThat(table.isRowSelected(ROW)).isTrue();
    assertThat(table.isColumnSelected(COLUMN)).isTrue();
  }
  
  private static class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    MainWindow(String title, TestTable table) {
      setLayout(new FlowLayout());
      setTitle(title);
      addTable(table);
      lookNative();
    }
    
    private void lookNative() {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ignored) {}
    }
    
    private void addTable(TestTable table) {
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(new Dimension(400, 200));
      add(scrollPane);
    }
  }
}
