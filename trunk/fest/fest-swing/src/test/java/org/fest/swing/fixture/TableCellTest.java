/*
 * Created on Sep 22, 2007
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

import javax.swing.JTable;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link TableCell}</code>.
 *
 * @author Alex Ruiz
 */
public class TableCellTest {

  @Test public void shouldCreateTableCellWithGivenRowAndColumn() {
    int row = 6;
    int column = 8;
    TableCell cell = TableCell.TableCellBuilder.row(row).column(column);
    assertThat(cell.row).isEqualTo(row);
    assertThat(cell.column).isEqualTo(column);
  }
  
  @Test(expectedExceptions = IllegalStateException.class)
  public void shouldThrowErrorIfTableIsEmpty() {
    TableCell cell = TableCell.TableCellBuilder.row(2).column(3);
    cell.validateBoundsIn(new JTable());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfRowIndexIsNegative() {
    TableCell cell = TableCell.TableCellBuilder.row(-2).column(3);
    cell.validateBoundsIn(new JTable(4, 3));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfColumnIndexIsNegative() {
    TableCell cell = TableCell.TableCellBuilder.row(2).column(-3);
    cell.validateBoundsIn(new JTable(4, 3));
  }
  
  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfRowIsOutOfBounds() {
    TableCell cell = TableCell.TableCellBuilder.row(4).column(2);
    cell.validateBoundsIn(new JTable(4, 3));
  }  
  
  @Test(expectedExceptions = IndexOutOfBoundsException.class)
  public void shouldThrowErrorIfColumnIsOutOfBounds() {
    TableCell cell = TableCell.TableCellBuilder.row(0).column(3);
    cell.validateBoundsIn(new JTable(4, 3));
  }  
}
