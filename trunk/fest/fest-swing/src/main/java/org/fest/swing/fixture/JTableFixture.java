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

import javax.swing.JTable;

import abbot.tester.ComponentLocation;
import abbot.tester.JTableLocation;
import abbot.tester.JTableTester;

import static org.fest.swing.util.Platform.controlOrCommandKey;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JTable}</code> and verification of the state of such
 * <code>{@link JTable}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JTableFixture extends ComponentFixture<JTable> {

  /**
   * Creates a new </code>{@link JTableFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTable</code>.
   * @param tableName the name of the <code>JTable</code> to find using the given 
   * <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTable</code> could not be found.
   */
  public JTableFixture(RobotFixture robot, String tableName) {
    super(robot, tableName, JTable.class);
  }
  
  /**
   * Creates a new </code>{@link JTableFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTable</code>.
   * @param target the <code>JTable</code> to be managed by this fixture.
   */
  public JTableFixture(RobotFixture robot, JTable target) {
    super(robot, target);
  }

  /**
   * Returns a fixture that manages the table cell specified by the given row and column.
   * @param cell the cell of interest.
   * @return  a fixture that manages the table cell specified by the given row and column.
   * @throws IllegalArgumentException if <code>cell</code> is <code>null</code>.
   * @throws IllegalStateException if the <code>JTable</code> managed by this fixture does not contain any cells (is
   * empty).
   * @throws IndexOutOfBoundsException if any of the indices of the <code>cell</code> are either negative or are out of
   * bounds.
   */
  public final JTableCellFixture cell(TableCell cell) {
    if (cell == null) throw new IllegalArgumentException("Cell cannot be null");
    cell.beValidatedIn(target);
    return new JTableCellFixture(this, cell.row, cell.column);
  }

  /**
   * Simulates a user selecting the given cell (row and column) of the <code>{@link JTable}</code> managed by this 
   * fixture.
   * @param row the row to select.
   * @param column the column to select.
   * @return this fixture.
   */
  public final JTableFixture selectCell(int row, int column) {
    tableTester().actionSelectCell(target, row, column);
    return this;
  }

  /**
   * Simulates a user selecting the given cells of the <code>{@link JTable}</code> managed by this fixture.
   * @param cells the cells to select.
   * @return this fixture.
   */
  public final JTableFixture selectCells(TableCell... cells) {
    int multipleSelectionKey = controlOrCommandKey();
    tableTester().actionKeyPress(multipleSelectionKey);
    for (TableCell c : cells) selectCell(c.row, c.column);
    tableTester().actionKeyRelease(multipleSelectionKey);
    return this;
  }

  /**
   * Returns the value of the selected cell in the <code>{@link JTable}</code> managed by this fixture into a reasonable 
   * <code>String</code> representation. Returns <code>null</code> if one can not be obtained or if the
   * <code>{@link JTable}</code> does not have any selected cell.
   * @return the value of the selected cell.
   */
  public final String selectionContents() {
    if (target.getSelectedRowCount() == 0) return null;
    return contentsAt(target.getSelectedRow(), target.getSelectedColumn());
  }

  /**
   * Returns the value of the given cell in the <code>{@link JTable}</code> managed by this fixture into a reasonable 
   * <code>String</code> representation, or <code>null</code> if one can not be obtained.
   * @param row the row of the given cell.
   * @param column the column of the given cell.
   * @return the value of the given cell.
   */
  public final String contentsAt(int row, int column) {
    return JTableTester.valueToString(target, row, column);
  }

  /**
   * Simulates a user dragging an item from the <code>{@link JTable}</code> managed by this fixture.
   * @param row the row of the item to drag.
   * @param col the column of the item to drag.
   * @return this fixture.
   */
  public final JTableFixture drag(int row, int col) {
    tester().actionDrag(target, cellLocation(row, col));
    return this;
  }

  /**
   * Simulates a user dropping an item to the <code>{@link JTable}</code> managed by this fixture.
   * @param row the row of the item to drop.
   * @param col the column of the item to drop.
   * @return this fixture.
   */
  public final JTableFixture drop(int row, int col) {
    tester().actionDrop(target, cellLocation(row, col));
    return this;
  }  

  private ComponentLocation cellLocation(int row, int col) {
    return new ComponentLocation(new JTableLocation(row, col).getPoint(target));
  }
  
  protected final JTableTester tableTester() {
    return (JTableTester)tester();
  }

  /**
   * Simulates a user clicking the <code>{@link JTable}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JTableFixture click() {
    return (JTableFixture)doClick();
  }
  
  /**
   * Simulates a user clicking the <code>{@link JTable}</code> managed by this fixture.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JTableFixture click(MouseClickInfo mouseClickInfo) {
    return (JTableFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking the <code>{@link JTable}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JTableFixture rightClick() {
    return (JTableFixture)doRightClick();
  }

  /**
   * Simulates a user doble-clicking the <code>{@link JTable}</code> managed by this fixture.
   * <p>
   * <b>Note:</b> This method will not be successful if the double-clicking occurs on an editable table cell. For this 
   * particular case, this method will start edition of the table cell located under the mouse pointer.
   * </p>
   * @return this fixture.
   */
  public final JTableFixture doubleClick() {    
    return (JTableFixture)doDoubleClick();
  }

  /**
   * Gives input focus to the <code>{@link JTable}</code> managed by this fixture.
   * @return this fixture.
   */
  public final JTableFixture focus() {
    return (JTableFixture)doFocus();
  }

  /**
   * Simulates a user pressing the given keys on the <code>{@link JTable}</code> managed by this fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableFixture pressKeys(int... keyCodes) {
    return (JTableFixture)doPressKeys(keyCodes);
  }

  /**
   * Asserts that the <code>{@link JTable}</code> managed by this fixture is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTable</code> is not visible.
   */
  public final JTableFixture requireVisible() {
    return (JTableFixture)assertVisible();
  }

  /**
   * Asserts that the <code>{@link JTable}</code> managed by this fixture is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTable</code> is visible.
   */
  public final JTableFixture requireNotVisible() {
    return (JTableFixture)assertNotVisible();
  }

  /**
   * Asserts that the <code>{@link JTable}</code> managed by this fixture is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTable</code> is disabled.
   */
  public final JTableFixture requireEnabled() {
    return (JTableFixture)assertEnabled();
  }
  
  /**
   * Asserts that the <code>{@link JTable}</code> managed by this fixture is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTable</code> is enabled.
   */
  public final JTableFixture requireDisabled() {
    return (JTableFixture)assertDisabled();
  }
}
