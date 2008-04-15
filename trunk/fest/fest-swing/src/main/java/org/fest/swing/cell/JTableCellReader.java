/*
 * Created on Apr 14, 2008
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
package org.fest.swing.cell;

import javax.swing.JTable;

/**
 * Understands reading the internal value of a cell in a <code>{@link JTable}</code> as expected in a test.
 *
 * @author Alex Ruiz
 */
public interface JTableCellReader {

  /**
   * Returns the internal value of a cell in a <code>{@link JTable}</code> as expected in a test.
   * @param table the given <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the internal value of a cell in a <code>JTable</code> as expected in a test.
   */
  String valueAt(JTable table, int row, int column);
}
