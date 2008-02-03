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

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.Platform.controlOrCommandKey;

import java.awt.Point;

import javax.swing.JTable;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JTableDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

/**
 * Understands simulation of user events on a <code>{@link JTable}</code> and verification of the state of such
 * <code>{@link JTable}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JTableFixture extends ComponentFixture<JTable> {

  private final JTableDriver driver;

  /**
   * Creates a new <code>{@link JTableFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTable</code>.
   * @param tableName the name of the <code>JTable</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTable</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JTable</code> is found.
   */
  public JTableFixture(RobotFixture robot, String tableName) {
    super(robot, tableName, JTable.class);
    driver = newTableDriver(robot);
  }

  /**
   * Creates a new <code>{@link JTableFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTable</code>.
   * @param target the <code>JTable</code> to be managed by this fixture.
   */
  public JTableFixture(RobotFixture robot, JTable target) {
    super(robot, target);
    driver = newTableDriver(robot);
  }

  private JTableDriver newTableDriver(RobotFixture robot) {
    return new JTableDriver(robot);
  }

  /**
   * Returns a fixture that manages the table cell specified by the given row and column.
   * @param cell the cell of interest.
   * @return a fixture that manages the table cell specified by the given row and column.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final JTableCellFixture cell(TableCell cell) {
    validate(cell);
    return new JTableCellFixture(this, cell);
  }

  /**
   * Simulates a user selecting the given cells of this fixture's <code>{@link JTable}</code>.
   * @param cells the cells to select.
   * @return this fixture.
   * @throws ActionFailedException if <code>cells</code> is <code>null</code>.
   * @throws ActionFailedException if any element in <code>cells</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of any of the <code>cells</code> are out of bounds.
   */
  public final JTableFixture selectCells(TableCell... cells) {
    if (cells == null) throw actionFailure("Cells to select cannot be null");
    int controlOrCommandKey = controlOrCommandKey();
    doPressKey(controlOrCommandKey);
    for (TableCell c : cells) selectCell(c);
    doReleaseKey(controlOrCommandKey);
    return this;
  }

  /**
   * Simulates a user selecting the given cell (row and column) of this fixture's <code>{@link JTable}</code>.
   * @param cell the cell to select.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final JTableFixture selectCell(TableCell cell) {
    validate(cell);
    driver.selectCell(target, cell.row, cell.column);
    return this;
  }

  /**
   * Returns the value of the selected cell in this fixture's <code>{@link JTable}</code> into a reasonable
   * <code>String</code> representation. Returns <code>null</code> if one can not be obtained or if the
   * <code>{@link JTable}</code> does not have any selected cell.
   * @return the value of the selected cell.
   */
  public final String selectionContents() {
    if (target.getSelectedRowCount() == 0) return null;
    return contentsAt(target.getSelectedRow(), target.getSelectedColumn());
  }

  /**
   * Returns the value of the given cell in this fixture's <code>{@link JTable}</code> into a reasonable
   * <code>String</code> representation, or <code>null</code> if one can not be obtained.
   * @param cell the given cell.
   * @return the value of the given cell.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final String contentsAt(TableCell cell) {
    validate(cell);
    return contentsAt(cell.row, cell.column);
  }

  private String contentsAt(int row, int column) {
    return driver.text(target, row, column);
  }

  /**
   * Simulates a user dragging an item from this fixture's <code>{@link JTable}</code>.
   * @param cell the cell to drag.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final JTableFixture drag(TableCell cell) {
    validate(cell);
    driver.drag(target, cell.row, cell.column);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's <code>{@link JTable}</code>.
   * @param cell the cell to drop the dragging item into.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final JTableFixture drop(TableCell cell) {
    validate(cell);
    driver.drop(target, cell.row, cell.column);
    return this;
  }

  /**
   * Simulates a user clicking a cell in this fixture's <code>{@link JTable}</code> once, using the specified mouse
   * button.
   * @param cell the cell to click.
   * @param mouseButton the mouse button to use.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final JTableFixture click(TableCell cell, MouseButton mouseButton) {
    validate(cell);
    driver.click(target, cell.row, cell.column, mouseButton, 1);
    return this;
  }

  /**
   * Simulates a user clicking a cell in this fixture's <code>{@link JTable}</code>, using the specified mouse button
   * the given number of times.
   * @param cell the cell to click.
   * @param mouseClickInfo specifies the mouse button to use and how many times to click.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final JTableFixture click(TableCell cell, MouseClickInfo mouseClickInfo) {
    validate(cell);
    driver.click(target, cell.row, cell.column, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Converts the given cell into a coordinate pair.
   * @param cell the given cell.
   * @return the coordinates of the given cell.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public final Point pointAt(TableCell cell) {
    validate(cell);
    return driver.pointAt(target, cell.row, cell.column);
  }

  private void validate(TableCell cell) {
    if (cell == null) throw actionFailure("Cell cannot be null");
    cell.validateBoundsIn(target);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTable}</code>.
   * @return this fixture.
   */
  public final JTableFixture click() {
    return (JTableFixture)doClick();
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTable}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public final JTableFixture click(MouseButton button) {
    return (JTableFixture)doClick(button);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTable}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public final JTableFixture click(MouseClickInfo mouseClickInfo) {
    return (JTableFixture)doClick(mouseClickInfo);
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JTable}</code>.
   * @return this fixture.
   */
  public final JTableFixture rightClick() {
    return (JTableFixture)doRightClick();
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JTable}</code>.
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
   * Gives input focus to this fixture's <code>{@link JTable}</code>.
   * @return this fixture.
   */
  public final JTableFixture focus() {
    return (JTableFixture)doFocus();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JTable}</code>. This method
   * does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableFixture pressAndReleaseKeys(int... keyCodes) {
    return (JTableFixture)doPressAndReleaseKeys(keyCodes);
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JTable}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableFixture pressKey(int keyCode) {
    return (JTableFixture)doPressKey(keyCode);
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JTable}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public final JTableFixture releaseKey(int keyCode) {
    return (JTableFixture)doReleaseKey(keyCode);
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTable</code> is not visible.
   */
  public final JTableFixture requireVisible() {
    return (JTableFixture)assertVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTable</code> is visible.
   */
  public final JTableFixture requireNotVisible() {
    return (JTableFixture)assertNotVisible();
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTable</code> is disabled.
   */
  public final JTableFixture requireEnabled() {
    return (JTableFixture)assertEnabled();
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if the managed <code>JTable</code> is never enabled.
   */
  public final JTableFixture requireEnabled(Timeout timeout) {
    return (JTableFixture)assertEnabled(timeout);
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTable</code> is enabled.
   */
  public final JTableFixture requireDisabled() {
    return (JTableFixture)assertDisabled();
  }
}
