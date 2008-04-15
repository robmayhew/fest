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
package org.fest.swing.cell;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Understands the default implementation of <code>{@link JListCellReader}</code>.
 *
 * @author Alex Ruiz
 */
public class BasicJListCellReader extends BaseValueReader implements JListCellReader {

  /**
   * Returns the internal value of a cell in a <code>{@link JList}</code> as expected in a test. This method first
   * tries to get the value from the <code>toString</code> implementation of the object stored in the
   * <code>JList</code>'s model at the specified index. If it fails, it returns the value displayed in the
   * <code>JList</code>'s cell renderer.
   * @param list the given <code>JList</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>JList</code> as expected in a test.
   * @see BaseValueReader#valueFrom(Object)
   * @see BaseValueReader#valueFrom(Component)
   */
  public String valueAt(JList list, int index) {
    Object element = elementAt(list, index);
    String value = valueFrom(element);
    if (value != null) return value;
    return valueFrom(cellRendererComponent(list, index));
  }

  /**
   * Returns the <code>{@link Component}</code> used by the <code>{@link ListCellRenderer}</code> in the given 
   * <code>{@link JList}</code>.
   * @param list the given <code>JList</code>.
   * @param index the index of the cell.
   * @return the <code>Component</code> used by the <code>ListCellRenderer</code> in the given <code>JList</code>.
   */
  protected final Component cellRendererComponent(JList list, int index) {
    Object element = elementAt(list, index);
    return list.getCellRenderer().getListCellRendererComponent(list, element, index, false, false);
  }
  
  private Object elementAt(JList list, int index) {
    return list.getModel().getElementAt(index);
  }
}
