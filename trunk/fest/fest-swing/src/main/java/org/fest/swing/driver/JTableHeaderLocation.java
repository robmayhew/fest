/*
 * Created on Mar 13, 2008
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

import javax.swing.table.JTableHeader;

import org.fest.swing.exception.LocationUnavailableException;

import static java.lang.String.valueOf;

import static org.fest.swing.util.Strings.match;
import static org.fest.util.Strings.*;

/**
 * Understands encapsulation of the location of a <code>{@link JTableHeader}</code> (a coordinate, column index or
 * value.)
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableHeaderLocation {

  /**
   * Returns the coordinates of the column which name matches the given one.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnName the column name to match
   * @return the coordinates of the column under the given index.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public Point pointAt(JTableHeader tableHeader, String columnName) {
    int validatedIndex = -1;
    try {
      validatedIndex = validatedIndex(tableHeader, indexOf(tableHeader, columnName));
    } catch (IndexOutOfBoundsException e) {
      throw new LocationUnavailableException(concat(
          "Unable to find column with name ", quote(columnName)));
    }
    return point(tableHeader, validatedIndex);
  }

  /**
   * Returns the coordinates of the column under the given index.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param index the given index.
   * @return the coordinates of the column under the given index.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public Point pointAt(JTableHeader tableHeader, int index) {
    return point(tableHeader, validatedIndex(tableHeader, index));
  }

  private Point point(JTableHeader tableHeader, int index) {
    Rectangle r = tableHeader.getHeaderRect(index);
    return new Point(r.x + r.width / 2, r.y + r.height / 2);
  }

  private int validatedIndex(JTableHeader tableHeader, int index) {
    int itemCount = columnCount(tableHeader);
    if (index >= 0 && index < itemCount) return index;
    throw new IndexOutOfBoundsException(concat(
        "Item index (", valueOf(index), ") should be between [", valueOf(0), "] and [",  valueOf(itemCount - 1),
        "] (inclusive)"));
  }

  /**
   * Returns the index of the column which name matches the given value or -1 if a matching column was not found.
   * @param tableHeader the target <code>JTableHeader</code>.
   * @param columnName the column name to match.
   * @return the index of the column which name matches the given value or -1 if a matching column was not found.
   */
  public int indexOf(JTableHeader tableHeader, String columnName) {
    int size = columnCount(tableHeader);
    for (int i = 0; i < size; i++)
      if (match(columnName, columnName(tableHeader, i))) return i;
    return -1;
  }

  private int columnCount(JTableHeader header) {
    return header.getColumnModel().getColumnCount();
  }

  private String columnName(JTableHeader tableHeader, int index) {
    return tableHeader.getTable().getModel().getColumnName(index);
  }
}
