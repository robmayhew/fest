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
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.fest.swing.cell.JComboBoxCellReader;

import static org.fest.swing.driver.ModelValueToString.asText;

/**
 * Understands the default implementation of <code>{@link JComboBoxCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJComboBoxCellReader implements JComboBoxCellReader {

  private static final JList REFERENCE_JLIST = new JList();
  
  private final CellRendererComponentReader cellRendererComponentReader;

  /**
   * Creates a new </code>{@link BasicJComboBoxCellReader}</code> that uses a 
   * <code>{@link BasicCellRendererComponentReader}</code> to read the value from the cell renderer component in a 
   * <code>JComboBox</code>.
   */
  public BasicJComboBoxCellReader() {
    this(new BasicCellRendererComponentReader());
  }

  /**
   * Creates a new </code>{@link BasicJComboBoxCellReader}</code>.
   * @param cellRendererComponentReader knows how to read values from the cell renderer component in a
   * <code>JComboBox</code>.
   * @throws NullPointerException if <code>cellRendererComponentReader</code> is <code>null</code>.
   */
  public BasicJComboBoxCellReader(CellRendererComponentReader cellRendererComponentReader) {
    if (cellRendererComponentReader == null)
      throw new NullPointerException("CellRendererComponentReader should not be null");
    this.cellRendererComponentReader = cellRendererComponentReader;
  }

  /**
   * Returns the internal value of a cell in a <code>{@link JComboBox}</code> as expected in a test. <b>Note:</b> this
   * method is <b>not</b> executed in the event dispatch thread. Callers are responsible for calling this method in the
   * event dispatch thread.
   * @param comboBox the given <code>JComboBox</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>JComboBox</code> as expected in a test.
   * @see CellRendererComponentReader#valueFrom(Component)
   */
  public String valueAt(JComboBox comboBox, int index) {
    Component c = cellRendererComponent(comboBox, index);
    String value = (c != null) ? cellRendererComponentReader.valueFrom(c) : null;
    if (value != null) return value;
    return asText(comboBox.getItemAt(index));
  }

  private Component cellRendererComponent(JComboBox comboBox, int index) {
    Object item = comboBox.getItemAt(index);
    ListCellRenderer renderer = comboBox.getRenderer();
    return renderer.getListCellRendererComponent(REFERENCE_JLIST, item, index, true, true);
  }
}
