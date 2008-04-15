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

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Understands the default implementation of <code>{@link JTableCellReader}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTableCellReader extends BaseValueReader implements JTableCellReader {

  private final JComboBoxCellReader comboBoxCellValueReader = new BasicJComboBoxCellReader();
  
  /**
   * Returns the internal value of a cell in a <code>{@link JTable}</code> as expected in a test. This method first 
   * tries to return the value displayed in the <code>JTable</code>'s cell renderer.
   * <ul>
   * <li>if the renderer is a <code>{@link JLabel}</code>, this method returns its text</li>
   * <li>if the renderer is a <code>{@link JComboBox}</code>, this method returns the value of its selection</li>
   * <li>if the renderer is a <code>{@link JCheckBox}</code>, this method returns whether it is selected or not</li>
   * </ul>
   * If it fails reading the cell renderer, this method will get the value from the <code>toString</code> implementation 
   * of the object stored in the <code>JTable</code>'s model at the specified indices.
   * @param table the given <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the internal value of a cell in a <code>JTable</code> as expected in a test.
   * @see BaseValueReader#valueFrom(Object)
   * @see BasicJComboBoxCellReader#valueAt(JComboBox, int)
   */
  public String valueAt(JTable table, int row, int column) {
    Component c = cellRendererComponent(table, row, column);
    if (c instanceof JLabel) return ((JLabel)c).getText();
    if (c instanceof JCheckBox) return String.valueOf(((JCheckBox)c).isSelected());
    if (c instanceof JComboBox) return valueAt((JComboBox)c);
    return valueFrom(cellAt(table, row, column));
  }

  private String valueAt(JComboBox comboBox) {
    int selectedIndex = comboBox.getSelectedIndex();
    if (selectedIndex == -1) return null;
    return comboBoxCellValueReader.valueAt(comboBox, selectedIndex);
  }
  
  /**
   * Returns the <code>{@link Component}</code> used by the <code>{@link TableCellRenderer}</code> in the given 
   * <code>{@link JTable}</code>.
   * @param table the given <code>JTable</code>.
   * @param row the row index of the cell.
   * @param column the column index of the cell.
   * @return the <code>Component</code> used by the <code>TableCellRenderer</code> in the given <code>JTable</code>.
   */
  protected final Component cellRendererComponent(JTable table, int row, int column) {
    Object value = cellAt(table, row, column);
    TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
    boolean cellSelected = table.isCellSelected(row, column);
    return cellRenderer.getTableCellRendererComponent(table, value, cellSelected, false, row, column);
  }
  
  private Object cellAt(JTable table, int row, int column) {
    return table.getModel().getValueAt(row, column);
  }
}
