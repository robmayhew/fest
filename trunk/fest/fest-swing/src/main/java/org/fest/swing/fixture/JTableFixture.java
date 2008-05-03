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

import java.awt.Font;
import java.awt.Point;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.fest.swing.cell.BasicJTableCellReader;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.JTableDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentDriver.propertyName;
import static org.fest.util.Strings.concat;

/**
 * Understands simulation of user events on a <code>{@link JTable}</code> and verification of the state of such
 * <code>{@link JTable}</code>.
 * <p>
 * The conversion between the values given in tests and the values being displayed by a <code>{@link JTable}</code>
 * renderer is performed by a <code>{@link JTableCellReader}</code>. This fixture uses a
 * <code>{@link BasicJTableCellReader}</code> by default.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 */
public class JTableFixture extends JPopupMenuInvokerFixture<JTable> {

  private JTableDriver driver;

  /**
   * Creates a new <code>{@link JTableFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JTable</code>.
   * @param target the <code>JTable</code> to be managed by this fixture.
   */
  public JTableFixture(Robot robot, JTable target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JTableFixture}</code>.
   * @param robot performs simulation of user events on a <code>JTable</code>.
   * @param tableName the name of the <code>JTable</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JTable</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JTable</code> is found.
   */
  public JTableFixture(Robot robot, String tableName) {
    super(robot, tableName, JTable.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new JTableDriver(robot));
  }

  final void updateDriver(JTableDriver driver) {
    this.driver = driver;
  }

  /**
   * Returns a fixture for the <code>{@link Font}</code> of the given table cell.
   * @param cell the given table cell.
   * @return a fixture for the <code>Font</code> of the given table cell.
   * @throws ActionFailedException if the cell is <code>null</code>.
   * @throws ActionFailedException if any of the indices (row and column) is out of bounds.
   */
  public FontFixture fontAt(TableCell cell) {
    Font font = driver.font(target, cell);
    String description = concat(propertyName(target, "font"), " - ", cell);
    return new FontFixture(font, description);
  }
  
  /**
   * Returns a fixture that manages the table cell specified by the given row and column.
   * @param cell the cell of interest.
   * @return a fixture that manages the table cell specified by the given row and column.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public JTableCellFixture cell(TableCell cell) {
    driver.validate(target, cell);
    return new JTableCellFixture(this, cell);
  }

  /**
   * Simulates a user selecting the given cell (row and column) of this fixture's <code>{@link JTable}</code>.
   * @param cell the cell to select.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public JTableFixture selectCell(TableCell cell) {
    driver.selectCell(target, cell);
    return this;
  }

  /**
   * Simulates a user selecting the given cells of this fixture's <code>{@link JTable}</code>.
   * @param cells the cells to select.
   * @return this fixture.
   * @throws ActionFailedException if <code>cells</code> is <code>null</code>.
   * @throws ActionFailedException if any element in <code>cells</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of any of the <code>cells</code> are out of bounds.
   */
  public JTableFixture selectCells(TableCell... cells) {
    driver.selectCells(target, cells);
    return this;
  }

  /**
   * Returns the value of the selected cell in this fixture's <code>{@link JTable}</code>. Returns <code>null</code>
   * if one can not be obtained or if the <code>{@link JTable}</code> does not have any selected cell.
   * @return the value of the selected cell.
   */
  public Object selectionContents() {
    return driver.selectionValue(target);
  }

  /**
   * Simulates a user dragging an item from this fixture's <code>{@link JTable}</code>.
   * @param cell the cell to drag.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public JTableFixture drag(TableCell cell) {
    driver.drag(target, cell);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's <code>{@link JTable}</code>.
   * @param cell the cell to drop the dragging item into.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public JTableFixture drop(TableCell cell) {
    driver.drop(target, cell);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JTable}</code>.
   * @return this fixture.
   */
  public JTableFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Converts the given cell into a coordinate pair.
   * @param cell the given cell.
   * @return the coordinates of the given cell.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public Point pointAt(TableCell cell) {
    return driver.pointAt(target, cell);
  }

  /**
   * Returns the value of the given cell in this fixture's <code>{@link JTable}</code>, or <code>null</code> if one
   * can not be obtained.
   * @param cell the given cell.
   * @return the value of the given cell.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public Object contentAt(TableCell cell) {
    return driver.value(target, cell);
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTable}</code>.
   * @return this fixture.
   */
  public JTableFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTable}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JTableFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JTable}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JTableFixture click(MouseClickInfo mouseClickInfo) {
    driver.click(target, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user clicking a cell in this fixture's <code>{@link JTable}</code> once, using the specified mouse
   * button.
   * @param cell the cell to click.
   * @param button the mouse button to use.
   * @return this fixture.
   * @throws ActionFailedException if <code>cell</code> is <code>null</code>.
   * @throws ActionFailedException if any of the indices of the <code>cell</code> are out of bounds.
   */
  public JTableFixture click(TableCell cell, MouseButton button) {
    click(cell, button, 1);
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
  public JTableFixture click(TableCell cell, MouseClickInfo mouseClickInfo) {
    click(cell, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  void click(TableCell cell, MouseButton button, int times) {
    driver.click(target, cell, button, times);
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JTable}</code>.
   * <p>
   * <b>Note:</b> This method will not be successful if the double-clicking occurs on an editable table cell. For this
   * particular case, this method will start edition of the table cell located under the mouse pointer.
   * </p>
   * @return this fixture.
   */
  public JTableFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JTable}</code>.
   * @return this fixture.
   */
  public JTableFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link JTable}</code>. This method
   * does not affect the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTableFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JTable}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTableFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JTable}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTableFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTable</code> is enabled.
   */
  public JTableFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError is the managed <code>JTable</code> is disabled.
   */
  public JTableFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if the managed <code>JTable</code> is never enabled.
   */
  public JTableFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTable</code> is visible.
   */
  public JTableFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JTable}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JTable</code> is not visible.
   */
  public JTableFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  
  
  /**
   * Shows a pop-up menu at the given cell.
   * @param cell the table cell where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenuAt(TableCell cell) {
    return new JPopupMenuFixture(robot, driver.showPopupMenuAt(target, cell));
  }

  /**
   * Returns a <code>{@link JTableHeaderFixture}</code> wrapping the <code>{@link JTableHeader}</code> in this
   * fixture's <code>{@link JTable}</code>.
   * @return a <code>JTableHeaderFixture</code> wrapping the <code>JTableHeader</code> in this fixture's
   *          <code>JTable</code>.
   * @throws AssertionError if the <code>JTableHeader</code> in this fixture's <code>JTable</code> is
   *          <code>null</code>.
   */
  public JTableHeaderFixture tableHeader() {
    JTableHeader tableHeader = target.getTableHeader();
    assertThat(tableHeader).isNotNull();
    return new JTableHeaderFixture(robot, tableHeader);
  }

  /**
   * Updates the implementation of <code>{@link JTableCellReader}</code> to use when comparing internal values of 
   * this fixture's <code>{@link JTable}</code> and the values expected in a test. The default implementation to use
   * is <code>{@link BasicJTableCellReader}</code>.
   * @param cellReader the new <code>JTableCellValueReader</code> to use.
   */
  public void cellReader(JTableCellReader cellReader) {
    driver.cellReader(cellReader);
  }
}
