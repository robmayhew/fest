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

import javax.swing.JList;

import org.fest.swing.cell.JListCellReader;

/**
 * Understands the default implementation of <code>{@link JListCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJListCellReader extends BaseValueReader implements JListCellReader {

  /**
   * Creates a new </code>{@link BasicJListCellReader}</code> that uses a 
   * <code>{@link BasicCellRendererComponentReader}</code> to read the value from the cell renderer component in a 
   * <code>JList</code>.
   */
  public BasicJListCellReader() {}

  /**
   * Creates a new </code>{@link BasicJListCellReader}</code>.
   * @param cellRendererComponentReader knows how to read values from the cell renderer component in a 
   * <code>JList</code>.
   */
  public BasicJListCellReader(CellRendererComponentReader cellRendererComponentReader) {
    super(cellRendererComponentReader);
  }

  /**
   * Returns the internal value of a cell in a <code>{@link JList}</code> as expected in a test. This method first
   * tries to get the value from the <code>toString</code> implementation of the object stored in the
   * <code>JList</code>'s model at the specified index. If it fails, it returns the value displayed in the
   * <code>JList</code>'s cell renderer.
   * @param list the given <code>JList</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>JList</code> as expected in a test.
   * @see CellRendererComponentReader#valueFrom(Component)
   */
  public String valueAt(JList list, int index) {
    return JListCellValueAsTextQuery.valueAtIndex(list, index, cellRendererComponentReader());
  }
}
