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
package org.fest.swing.driver;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.fest.swing.cell.JTableCellReader;

import static org.fest.swing.driver.JTableCellBackgroundQuery.cellBackground;
import static org.fest.swing.driver.JTableCellFontQuery.cellFont;
import static org.fest.swing.driver.JTableCellForegroundQuery.cellForeground;
import static org.fest.swing.driver.JTableCellValueAsTextQuery.cellValue;

/**
 * Understands the default implementation of <code>{@link JTableCellReader}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTableCellReader extends BaseValueReader implements JTableCellReader {

  /**
   * Creates a new </code>{@link BasicJTableCellReader}</code> that uses a 
   * <code>{@link BasicCellRendererComponentReader}</code> to read the value from the cell renderer component in a 
   * <code>JTable</code>.
   */
  public BasicJTableCellReader() {}

  /**
   * Creates a new </code>{@link BasicJTableCellReader}</code>.
   * @param cellRendererComponentReader knows how to read values from the cell renderer component in a 
   * <code>JTable</code>.
   */
  public BasicJTableCellReader(CellRendererComponentReader cellRendererComponentReader) {
    super(cellRendererComponentReader);
  }

  /**
   * Returns the internal value of a cell in a <code>{@link JTable}</code> as expected in a test. This method first
   * tries to return the value displayed in the <code>JTable</code>'s cell renderer.
   * <ul>
   * <li>if the renderer is a <code>{@link JLabel}</code>, this method returns its text</li>
   * <li>if the renderer is a <code>{@link JComboBox}</code>, this method returns the value of its selection as a 
   * <code>String</code></li>
   * <li>if the renderer is a <code>{@link JCheckBox}</code>, this method returns whether it is selected or not</li>
   * </ul>
   * If it fails reading the cell renderer, this method will get the value from the <code>toString</code> implementation
   * of the object stored in the <code>JTable</code>'s model at the specified indices.
   * @param table the given <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the internal value of a cell in a <code>JTable</code> as expected in a test.
   */
  public String valueAt(JTable table, int row, int column) {
    return cellValue(table, row, column, cellRendererComponentReader());
  }

  /** {@inheritDoc} */
  public Font fontAt(JTable table, int row, int column) {
    return cellFont(table, row, column);
  }

  /** {@inheritDoc} */
  public Color backgroundAt(JTable table, int row, int column) {
    return cellBackground(table, row, column);
  }

  /** {@inheritDoc} */
  public Color foregroundAt(JTable table, int row, int column) {
    return cellForeground(table, row, column);
  }
}
