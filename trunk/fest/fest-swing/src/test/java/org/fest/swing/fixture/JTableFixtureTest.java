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

import javax.swing.JScrollPane;
import javax.swing.JTable;

import static org.fest.util.Strings.concat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.*;

/**
 * Tests for <code>{@link JTableFixture}</code>.
 *
 * @author Alex Ruiz 
 */
public class JTableFixtureTest extends ComponentFixtureTestCase<JTable> {

  private static final int COLUMN_COUNT = 6;
  private static final int ROW_COUNT = 10;
  
  private JTableFixture fixture;
  
  @Test(dataProvider = "cellsToSelect") 
  public void shouldSelectCell(int row, int column) {
    fixture.selectCell(row, column);
    assertThat(fixture.target.getSelectedRow()).isEqualTo(row);
    assertThat(fixture.target.getSelectedColumn()).isEqualTo(column);
  }
  
  @DataProvider(name = "cellsToSelect")
  public Object[][] cellsToSelect() {
    return new Object[][] {
        { 6, 5 },  
        { 0, 0 },
        { 8, 3 },
        { 5, 2 }
    };
  }
  
  protected ComponentFixture<JTable> createFixture() {
    fixture = new JTableFixture(robot(), "target");
    return fixture;
  }

  protected JTable createTarget() {
    JTable target = new JTable(rowData(), columnNames());
    target.setName("target");
    return target;
  }
  
  private Object[] columnNames() {
    Object[] columnNames = new Object[COLUMN_COUNT];
    for (int i = 0; i < COLUMN_COUNT; i++) 
      columnNames[i] = String.valueOf(i);
    return columnNames;
  }
  
  private Object[][] rowData() {
    Object[][] data = new Object[ROW_COUNT][COLUMN_COUNT];
    for (int i = 0; i < ROW_COUNT; i++)
      for (int j = 0; j < COLUMN_COUNT; j++)
        data[i][j] = concat(String.valueOf(i), "-", String.valueOf(j)); 
    return data;
  }

  @Override protected Component decorateBeforeAddingToWindow(JTable target) {
    target.setFillsViewportHeight(true);
    return new JScrollPane(target);
  }

}
