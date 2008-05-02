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

import org.fest.swing.core.MouseButton;
import org.fest.swing.exception.ComponentLookupException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.*;

/**
 * Understands simulation of user events on a cell in a <code>{@link JTable}</code> and verification of the state of
 * such table cell.
 * <p>
 * Example:
 * <pre>
 * // import static org.fest.swing.fixture.TableCellBuilder.row;
 * {@link JTableCellFixture} cell = dialog.{@link JTableFixture table}("records").cell({@link TableCell.TableCellBuilder#row(int) row}(3).column(0));
 * cell.select().showPopupMenu();
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 *
 * @see TableCell
 */
public class JTableCellFixture implements ItemFixture {

  private final JTableFixture table;
  private final TableCell cell;

  /**
   * Creates a new <code>{@link JTableCellFixture}</code>.
   * @param table manages the <code>JTable</code> containing the table cell to be managed by this fixture.
   * @param cell row and column indices of the table cell to be managed by this fixture.
   */
  protected JTableCellFixture(JTableFixture table, TableCell cell) {
    this.table = table;
    this.cell = cell;
  }

  /**
   * Simulates a user selecting this fixture's table cell.
   * @return this fixture.
   */
  public JTableCellFixture select() {
    return click();
  }

  /**
   * Simulates a user clicking this fixture's table cell.
   * @return this fixture.
   */
  public JTableCellFixture click() {
    table.selectCell(cell);
    return this;
  }
  
  /**
   * Simulates a user clicking this fixture's table cell.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   */
  public JTableCellFixture click(MouseClickInfo mouseClickInfo) {
    table.click(cell, mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's table cell.
   * @return this fixture.
   */
  public JTableCellFixture doubleClick() {
    return click(LEFT_BUTTON, 2);
  }

  /**
   * Simulates a user right-clicking this fixture's table cell.
   * @return this fixture.
   */
  public JTableCellFixture rightClick() {
    return click(RIGHT_BUTTON);
  }

  private JTableCellFixture click(MouseButton button) {
    table.click(cell, button);
    return this;
  }

  private JTableCellFixture click(MouseButton button, int times) {
    table.click(cell, button, times);
    return this;
  }

  /**
   * Asserts that this fixture's table cell contains the given value.
   * @param content the expected content of this fixture's table cell.
   * @return this fixture.
   * @throws AssertionError if the content of this fixture's table cell is not equal to the expected one.
   * TODO: Add property name as description of the assertion.
   */
  public JTableCellFixture requireContent(Object content) {
    assertThat(content()).isEqualTo(content);
    return this;
  }
  
  /**
   * Returns the value of this fixture's table cell, or <code>null</code> if one can not be obtained.
   * @return the value of the given cell.
   */
  public Object content() {
    return table.contentAt(cell);
  }

  /**
   * Simulates a user dragging this fixture's table cell.
   * @return this fixture.
   */
  public JTableCellFixture drag() {
    table.drag(cell);
    return this;
  }

  /**
   * Simulates a user dropping into this fixture's table cell.
   * @return this fixture.
   */
  public JTableCellFixture drop() {
    table.drop(cell);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's table cell. This method does not affect
   * the current focus.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTableCellFixture pressAndReleaseKeys(int... keyCodes) {
    table.pressAndReleaseKeys(keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's table cell.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTableCellFixture pressKey(int keyCode) {
    table.pressKey(keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's table cell.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JTableCellFixture releaseKey(int keyCode) {
    table.releaseKey(keyCode);
    return this;
  }

  /**
   * Shows a pop-up menu using this fixture's table cell as the invoker of the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public JPopupMenuFixture showPopupMenu() {
    return table.showPopupMenuAt(cell);
  }

  /**
   * Returns the row index of this fixture's table cell.
   * @return the row index of this fixture's table cell.
   */
  public int row() { return cell.row; }

  /**
   * Returns the column index of this fixture's table cell.
   * @return the column index of this fixture's table cell.
   */
  public int column() { return cell.column; }
}
