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

import javax.swing.JTree;

import org.fest.swing.cell.JTreeCellReader;

import static org.fest.swing.driver.JTreeCellValueAsTextQuery.nodeValue;

/**
 * Understands the default implementation of <code>{@link JTreeCellReader}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTreeCellReader extends BaseValueReader implements JTreeCellReader {

  /**
   * Creates a new </code>{@link BasicJTreeCellReader}</code> that uses a 
   * <code>{@link BasicCellRendererComponentReader}</code> to read the value from the cell renderer component in a 
   * <code>JTree</code>.
   */
  public BasicJTreeCellReader() {}

  /**
   * Creates a new </code>{@link BasicJTreeCellReader}</code>.
   * @param cellRendererComponentReader knows how to read values from the cell renderer component in a 
   * <code>JTree</code>.
   */
  public BasicJTreeCellReader(CellRendererComponentReader cellRendererComponentReader) {
    super(cellRendererComponentReader);
  }

  /**
   * Returns the internal value of a cell in a <code>{@link JTree}</code> as expected in a test.
   * @param tree the given <code>JTree</code>.
   * @param modelValue the value of a cell, retrieved from the model.
   * @return the internal value of a cell in a <code>JTree</code> as expected in a test.
   */
  public String valueAt(JTree tree, Object modelValue) {
    return nodeValue(tree, modelValue, cellRendererComponentReader());
  }
}
