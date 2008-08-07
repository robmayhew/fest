/*
 * Created on Jun 10, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JTable;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.GetJTableCellEditorComponentTask.cellEditorComponentOf;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Strings.concat;

/**
 * Understands the base class for implementations of <code>{@link JTableCellWriter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class AbstractJTableCellWriter implements JTableCellWriter {

  protected final Robot robot;
  private final JComponentDriver driver;

  private final JTableLocation tableLocation = new JTableLocation();

  public AbstractJTableCellWriter(Robot robot) {
    this.robot = robot;
    this.driver = new JComponentDriver(robot);
  }

  /**
   * Simulates a user clicking the given table cell.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   */
  protected final void clickCell(JTable table, int row, int column) {
    clickCell(table, row, column, 1);
  }

  /**
   * Simulates a user clicking the given table cell, the given number of times.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @param times how many times the cell should click the cell.
   */
  protected final void clickCell(JTable table, int row, int column, int times) {
    driver.scrollToVisible(table, tableLocation.cellBounds(table, row, column));
    robot.click(table, tableLocation.pointAt(table, row, column), LEFT_BUTTON, times);
  }

  /**
   * Throws a <code>{@link ActionFailedException}</code> if the given editor cannot be handled by this
   * <code>{@link JTableCellWriter}</code>.
   * @param editor the given editor.
   * @return the thrown exception.
   */
  protected final ActionFailedException cannotHandleEditor(Component editor) {
    throw actionFailure(concat("Unable to handle editor component of type ", editorTypeName(editor)));
  }

  private String editorTypeName(Component editor) {
    if (editor == null) return "<null>";
    return editor.getClass().getName();
  }

  /** ${@inheritDoc} */
  public Component editorForCell(final JTable table, final int row, final int column) {
    return cellEditorComponentOf(table, row, column);
  }
}
