/*
 * Created on Jun 8, 2008
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

import java.awt.Component;

import javax.swing.JTable;

import org.fest.swing.exception.ActionFailedException;

/**
 * Understands how to edit the value of a cell in a <code>{@link JTable}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public interface JTableCellWriter {

  /**
   * Enters the given value at the given cell of the <code>JTable</code>.
   * @param table the target <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @param value the value to enter.
   * @throws ActionFailedException if this writer is unable to enter the given value.
   */
  void enterValue(JTable table, int row, int column, String value);

  /**
   * Returns the <code>{@link Component}</code> used as editor of the given cell.
   * @param table the target <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the <code>Component</code> used as editor of the given cell.
   */
  Component editorForCell(JTable table, int row, int column);
}
