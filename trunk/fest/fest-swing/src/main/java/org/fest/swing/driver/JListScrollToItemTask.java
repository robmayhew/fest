/*
 * Created on Nov 4, 2008
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

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.awt.AWT.centerOf;
import static org.fest.swing.driver.ComponentStateValidator.validateIsEnabled;
import static org.fest.swing.driver.JListCellBoundsQuery.cellBounds;
import static org.fest.swing.driver.JListMatchingItemQuery.matchingItemIndex;
import static org.fest.swing.driver.JListItemIndexValidator.validateIndex;
import static org.fest.swing.driver.JListScrollActionResult.ITEM_NOT_FOUND;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands SOMETHING DUMMY.
 *
 * @author 
 */
final class JListScrollToItemTask {

  @RunsInEDT
  static Point scrollToItem(final JList list, final int index) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabled(list);
        validateIndex(list, index);
        return scrollToItemWithIndex(list, index);
      }
    });
  }

  @RunsInEDT
  static JListScrollActionResult scrollToItem(final JList list, final String value, final JListCellReader cellReader) {
    return execute(new GuiQuery<JListScrollActionResult>() {
      protected JListScrollActionResult executeInEDT() {
        validateIsEnabled(list);
        int index = matchingItemIndex(list, value, cellReader);
        if (index < 0) return ITEM_NOT_FOUND;
        return new JListScrollActionResult(index, scrollToItemWithIndex(list, index));
      }
    });
  }
  
  @RunsInEDT
  static JListScrollActionResult scrollToNotSelectedItem(final JList list, final String value, final JListCellReader cellReader) {
    return execute(new GuiQuery<JListScrollActionResult>() {
      protected JListScrollActionResult executeInEDT() {
        validateIsEnabled(list);
        int index = matchingItemIndex(list, value, cellReader);
        if (index < 0) return ITEM_NOT_FOUND;
        return new JListScrollActionResult(index, scrollToNotSelectedItemWithIndex(list, index));
      }
    });
  }

  @RunsInEDT
  static Point scrollToNotSelectedItem(final JList list, final int index) {
    return execute(new GuiQuery<Point>() {
      protected Point executeInEDT() {
        validateIsEnabled(list);
        validateIndex(list, index);
        return scrollToNotSelectedItemWithIndex(list, index);
      }
    });
  }
  
  @RunsInCurrentThread
  private static Point scrollToNotSelectedItemWithIndex(final JList list, final int index) {
    if (list.getSelectedIndex() == index) return null;
    return scrollToItemWithIndex(list, index);
  }
  
  @RunsInCurrentThread
  private static Point scrollToItemWithIndex(JList list, int index) {
    Rectangle cellBounds = cellBounds(list, index);
    list.scrollRectToVisible(cellBounds);
    return centerOf(cellBounds);
  }
  
  private JListScrollToItemTask() {}
}
