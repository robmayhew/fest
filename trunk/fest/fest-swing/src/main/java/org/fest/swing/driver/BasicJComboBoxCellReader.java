/*
 * Created on Apr 12, 2008
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

import javax.swing.JComboBox;

import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.driver.JComboBoxCellValueAsTextQuery.valueAtIndex;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands the default implementation of <code>{@link JComboBoxCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJComboBoxCellReader extends BaseValueReader implements JComboBoxCellReader {

  /**
   * Creates a new </code>{@link BasicJComboBoxCellReader}</code> that uses a 
   * <code>{@link BasicCellRendererComponentReader}</code> to read the value from the cell renderer component in a 
   * <code>JComboBox</code>.
   */
  public BasicJComboBoxCellReader() {}

  /**
   * Creates a new </code>{@link BasicJComboBoxCellReader}</code>.
   * @param cellRendererComponentReader knows how to read values from the cell renderer component in a
   * <code>JComboBox</code>.
   */
  public BasicJComboBoxCellReader(CellRendererComponentReader cellRendererComponentReader) {
    super(cellRendererComponentReader);
  }
  
  /**
   * Returns the internal value of a cell in a <code>{@link JComboBox}</code> as expected in a test.
   * @param comboBox the given <code>JComboBox</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>JComboBox</code> as expected in a test.
   * @see CellRendererComponentReader#valueFrom(Component)
   */
  public String valueAt(JComboBox comboBox, int index) {
    return doGetValueAt(comboBox, index, cellRendererComponentReader());
  }

  private static String doGetValueAt(final JComboBox comboBox, final int index, final CellRendererComponentReader reader) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        return valueAtIndex(comboBox, index, reader);
      }
    });
  }  
}
