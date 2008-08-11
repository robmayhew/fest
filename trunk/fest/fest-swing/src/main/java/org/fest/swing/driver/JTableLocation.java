/*
 * Created on Feb 2, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTable;

/**
 * Understands a visible location on a <code>{@link JTable}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class JTableLocation {

  /**
   * Converts the given row and column into a coordinate pair. It is assumed that the row and column indices are
   * in the <code>{@link JTable}</code>'s bounds.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @return the coordinates of the given row and column.
   */
  public Point pointAt(JTable table, int row, int column) {
    Rectangle cellBounds = cellBounds(table, row, column);
    return new Point(cellBounds.x + cellBounds.width / 2, cellBounds.y + cellBounds.height / 2);
  }

  /**
   * Returns the bounds of the given cell.
   * @param table the target <code>JTable</code>.
   * @param cell the given cell.
   * @return the bounds of the given cell.
   */
  public Rectangle cellBounds(JTable table, JTableCell cell) {
    return cellBounds(table, cell.row, cell.column);
  }

  /**
   * Returns the bounds of the given row and column.
   * @param table the target <code>JTable</code>.
   * @param row the given row.
   * @param column the given column.
   * @return the bounds of the given row and column.
   */
  public Rectangle cellBounds(JTable table, int row, int column) {
    return JTableCellRectQuery.cellBoundsOf(table, row, column);
  }

}
