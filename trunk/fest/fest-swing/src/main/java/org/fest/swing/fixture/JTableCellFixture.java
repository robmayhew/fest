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

import javax.swing.JTable;

import abbot.tester.ComponentLocation;
import abbot.tester.JTableLocation;

/**
 * Understands simulation of user events on a cell of a <code>{@link JTable}</code> and verification of the state of
 * such table cell.
 * 
 * @author Alex Ruiz
 */
public class JTableCellFixture {

  private final JTableFixture table;
  private final int row;
  private final int column;

  /**
   * Creates a new </code>{@link JTableCellFixture}</code>.
   * @param table contains the <code>JTable</code> containing the cell to be managed by this fixture.
   * @param row the index of the row of the cell to be managed by this fixture.
   * @param column the index of the column of the cell to be managed by this fixture.
   */
  protected JTableCellFixture(JTableFixture table, int row, int column) {
    this.table = table;
    this.row = row;
    this.column = column;
  }

  public final JTableCellFixture select() {
    table.selectCell(row, column);
    return this;
  }
  
  public final JTableCellFixture click() {
    table.tester().actionClick(table.target, cellLocation());
    return this;
  }

  private ComponentLocation cellLocation() {
    return new ComponentLocation(new JTableLocation(row, column).getPoint(table.target));
  }
}
