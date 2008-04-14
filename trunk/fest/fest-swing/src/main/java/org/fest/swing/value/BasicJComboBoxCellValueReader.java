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
package org.fest.swing.value;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;

/**
 * Understands the default implementation of <code>{@link JListCellValueReader}</code>.
 *
 * @author Alex Ruiz
 */
public class BasicJComboBoxCellValueReader extends BaseValueReader implements JComboBoxCellValueReader {

  private static final JList REFERENCE_JLIST = new JList();

  /**
   * Returns the internal value of a cell in a <code>{@link JComboBox}</code> as expected in a test. This method first
   * tries to get the value from the <code>toString</code> implementation of the object stored in the
   * <code>JComboBox</code>'s model at the specified index. If it fails, it returns the value displayed in the
   * <code>JComboBox</code>'s cell renderer.
   * @param comboBox the given <code>JComboBox</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>JComboBox</code> as expected in a test.
   * @see BaseValueReader#valueFrom(Object)
   * @see BaseValueReader#valueFrom(Component)
   */
  public Object valueAt(JComboBox comboBox, int index) {
    Object item = comboBox.getItemAt(index);
    Object value = valueFrom(item);
    if (value != null) return value;
    return valueFrom(cellRendererComponent(comboBox, index, item));
  }

  private Component cellRendererComponent(JComboBox comboBox, int index, Object item) {
    return comboBox.getRenderer().getListCellRendererComponent(REFERENCE_JLIST, item, index, true, true);
  }
}
