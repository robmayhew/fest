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

import abbot.tester.ComponentLocation;
import abbot.tester.JTableLocation;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;

import javax.swing.*;

/**
 * Understands simulation of user events on a cell of a <code>{@link JTable}</code> and verification of the state of
 * such table cell.
 * 
 * @author Alex Ruiz
 */
public class JTableCellFixture {

  private final JTableFixture table;
  private final TableCell cell;

  /**
   * Creates a new </code>{@link JTableCellFixture}</code>.
   * @param table contains the <code>JTable</code> containing the cell to be managed by this fixture.
   * @param cell contains the row and column indices of the cell to be managed by this fixture.
   */
  protected JTableCellFixture(JTableFixture table, TableCell cell) {
    this.table = table;
    this.cell = cell;
  }

  /**
   * Simulates a user selecting the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture select() {
    table.selectCell(cell);
    return this;
  }

  /**
   * Simulates a user clicking the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture click() {
    return click(LEFT_BUTTON);
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
    return click(RIGHT_BUTTON);
  }

  /**
   * Simulates a user double-clicking the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture doubleClick() {
    return click(LEFT_BUTTON, 2);
  }
  
  private JTableCellFixture click(MouseButton button) {
    table.tester().actionClick(table.target, cellLocation(), button.mask, 1);
    return this;
  }
  
  private JTableCellFixture click(MouseButton button, int times) {
    table.tester().actionClick(table.target, cellLocation(), button.mask, times);
    return this;
  }

  private ComponentLocation cellLocation() {
    return new ComponentLocation(new JTableLocation(row(), column()).getPoint(table.target));
  }

  /**
   * Returns the value of table cell managed by this fixture into a reasonable <code>String</code> representation, or
   * <code>null</code> if one can not be obtained.
   * @return the value of the given cell.
   */  
  public final String contents() {
    return table.contentsAt(cell);
  }

  /**
   * Simulates a user dragging the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture drag() {
    table.drag(cell);
    return this;
  }

  /**
   * Simulates a user dropping into the table cell managed by this fixture.
   * @return this fixture.
   */
  public final JTableCellFixture drop() {
    table.drop(cell);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on the table cell managed by this fixture. This method does
   * not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableCellFixture pressAndReleaseKeys(int... keyCodes) {
    table.pressAndReleaseKeys(keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on the table cell managed by this fixture.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableCellFixture pressKey(int keyCode) {
    table.pressKey(keyCode);
    return this;
  }
  
  /**
   * Simulates a user releasing the given key on the table cell managed by this fixture.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableCellFixture releaseKey(int keyCode) {
    table.releaseKey(keyCode);
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
    JPopupMenu popupMenu = robot.showPopupMenu(target, new JTableLocation(row(), column()).getPoint(table.target));
    return new JPopupMenuFixture(robot, popupMenu);
  }
  
  /**
   * Returns the row index of the table cell managed by this fixture.
   * @return the row index of the table cell managed by this fixture.
   */
  public final int row() { return cell.row; }

  /**
   * Returns the column index of the table cell managed by this fixture.
   * @return the column index of the table cell managed by this fixture.
   */
  public final int column() { return cell.column; }
}
