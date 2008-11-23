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
import java.awt.Point;

import javax.swing.JTable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabledAndShowing;
import static org.fest.swing.driver.JTableCancelCellEditingTask.cancelEditing;
import static org.fest.swing.driver.JTableCellEditorQuery.cellEditorIn;
import static org.fest.swing.driver.JTableCellValidator.validateIndices;
import static org.fest.swing.driver.JTableStopCellEditingTask.stopEditing;
import static org.fest.swing.edt.GuiActionRunner.execute;
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
  protected final JTableLocation location = new JTableLocation();

  public AbstractJTableCellWriter(Robot robot) {
    this.robot = robot;
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public void cancelCellEditing(JTable table, int row, int column) {
    cancelEditing(table, row, column);
    robot.waitForIdle();
  }
  
  /** {@inheritDoc} */
  @RunsInEDT
  public void stopCellEditing(JTable table, int row, int column) {
    stopEditing(table, row, column);
    robot.waitForIdle();
  }


  /**
   * Simulates a user clicking the given table cell.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @throws IllegalStateException if the <code>JTable</code> is disabled.
   * @throws IllegalStateException if the <code>JTable</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  protected final void clickCell(JTable table, int row, int column) {
    clickCell(table, row, column, 1);
  }

  /**
   * Simulates a user clicking the given table cell, the given number of times.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @param times how many times the cell should click the cell.
   * @throws IllegalStateException if the <code>JTable</code> is disabled.
   * @throws IllegalStateException if the <code>JTable</code> is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices (row and column) is out of bounds.
   */
  @RunsInEDT
  protected final void clickCell(JTable table, int row, int column, int times) {
    // TODO validate times
    Point pointAtCell = scrollToPointAtCell(table, row, column, location);
    robot.click(table, pointAtCell, LEFT_BUTTON, times);
  }

  @RunsInEDT
  private static Point scrollToPointAtCell(final JTable table, final int row, final int column, 
      final JTableLocation location) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        scrollToCell(table, row, column, location);
        return location.pointAt(table, row, column);
      }
    });
  }
  
  @RunsInCurrentThread
  protected static void scrollToCell(final JTable table, final int row, final int column, final JTableLocation location) {
    validateIsEnabledAndShowing(table);
    validateIndices(table, row, column);
    table.scrollRectToVisible(location.cellBounds(table, row, column));
  }

  /**
   * Throws a <code>{@link ActionFailedException}</code> if the given editor cannot be handled by this
   * <code>{@link JTableCellWriter}</code>.
   * @param editor the given editor.
   * @return the thrown exception.
   */
  protected static final ActionFailedException cannotHandleEditor(Component editor) {
    throw actionFailure(concat("Unable to handle editor component of type ", editorTypeName(editor)));
  }

  private static String editorTypeName(Component editor) {
    if (editor == null) return "<null>";
    return editor.getClass().getName();
  }

  /** {@inheritDoc} */
  @RunsInEDT
  public Component editorForCell(JTable table, int row, int column) {
    return cellEditor(table, row, column);
  }
  
  @RunsInEDT
  private static Component cellEditor(final JTable table, final int row, final int column) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return cellEditorIn(table, row, column);
      }
    });
  }
}
