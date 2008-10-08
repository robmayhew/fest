/*
 * Created on Jan 19, 2008
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

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JList;

import static java.lang.String.valueOf;

import static org.fest.swing.driver.JListCellBoundsQuery.cellBoundsOf;
import static org.fest.swing.driver.JListElementCountQuery.elementCountOf;
import static org.fest.util.Strings.concat;

/**
 * Understands the location of a row on a <code>{@link JList}</code> (a coordinate, item index or
 * value.)
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListLocation {

  /**
   * Returns the coordinates of the item at the given index.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @return the coordinates of the item at the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  public Point pointAt(JList list, int index) {
    validate(list, index);
    Rectangle cellBounds = cellBoundsOf(list, index);
    return new Point(cellBounds.x + cellBounds.width / 2, cellBounds.y + cellBounds.height / 2);
  }

  /**
   * Verifies that the given index is valid.
   * @param list the target <code>JList</code>.
   * @param index the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   * <code>JList</code>.
   */
  public void validate(JList list, int index) {
    int itemCount = elementCountOf(list);
    if (index >= 0 && index < itemCount) return;
    throw new IndexOutOfBoundsException(concat(
        "Item index (", valueOf(index), ") should be between [", valueOf(0), "] and [",  valueOf(itemCount - 1),
        "] (inclusive)"));
  }
}
