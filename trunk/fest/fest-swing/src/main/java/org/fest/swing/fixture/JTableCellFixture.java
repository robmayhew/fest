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

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import abbot.tester.ComponentLocation;
import abbot.tester.JTableLocation;

import static org.fest.swing.MouseButtons.BUTTON1;
import static org.fest.swing.MouseButtons.BUTTON3;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.MouseButtons;
import org.fest.swing.RobotFixture;

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
   * @param row the row index of the cell to be managed by this fixture.
   * @param column the column index of the cell to be managed by this fixture.
   */
  protected JTableCellFixture(JTableFixture table, int row, int column) {
    this.table = table;
    this.row = row;
    this.column = column;
  }

  /**
   * Simulates a user selecting the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture select() {
    table.selectCell(row, column);
    return this;
  }

  /**
   * Simulates a user clicking the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture click() {
    return click(BUTTON1);
  }
  
  /**
   * Simulates a user clicking the table cell managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JTableCellFixture click(MouseClickInfo mouseClickInfo) {
    return click(mouseClickInfo.button(), mouseClickInfo.times());
  }
  
  /**
   * Simulates a user right-clicking the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture rightClick() {
    return click(BUTTON3);
  }

  /**
   * Simulates a user double-clicking the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture doubleClick() {
    return click(BUTTON1, 2);
  }
  
  private JTableCellFixture click(MouseButtons button) {
    table.tester().actionClick(table.target, cellLocation(), button.mask, 1);
    return this;
  }
  
  private JTableCellFixture click(MouseButtons button, int times) {
    table.tester().actionClick(table.target, cellLocation(), button.mask, times);
    return this;
  }

  private ComponentLocation cellLocation() {
    return new ComponentLocation(new JTableLocation(row, column).getPoint(table.target));
  }

  /**
   * Returns the value of table cell managed by this fixture into a reasonable <code>String</code> representation, or
   * <code>null</code> if one can not be obtained.
   * @return the value of the given cell.
   */  
  public final String contents() {
    return table.contentsAt(row, column);
  }

  /**
   * Simulates a user dragging the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture drag() {
    table.drag(row, column);
    return this;
  }

  /**
   * Simulates a user dropping into the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture drop() {
    table.drop(row, column);
    return this;
  }

  /**
   * Simulates a user pressing the given keys on the table cell managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableCellFixture pressKeys(int... keyCodes) {
    table.pressKeys(keyCodes);
    return this;
  }

  /**
   * Shows a popup menu using the table cell managed by this fixture as the invoker of the popup menu.
   * @return a fixture that manages the displayed popup menu.
   * @throws ComponentLookupException if a popup menu cannot be found.
   */
  public final JPopupMenuFixture showPopupMenu() {
    RobotFixture robot = table.robot;
    JTable target = table.target;
    JPopupMenu popupMenu = robot.showPopupMenu(target, new JTableLocation(row, column).getPoint(table.target));
    return new JPopupMenuFixture(robot, popupMenu);
  }
  
  /**
   * Returns the row index of the table cell managed by this fixture.
   * @return the row index of the table cell managed by this fixture.
   */
  public final int row() { return row; }

  /**
   * Returns the column index of the table cell managed by this fixture.
   * @return the column index of the table cell managed by this fixture.
   */
  public final int column() { return column; }
}
