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
import org.fest.swing.core.GuiQuery;

import static org.fest.swing.driver.JTreeCellRendererQuery.cellRendererIn;
import static org.fest.swing.util.Strings.isDefaultToString;

/**
 * Understands the default implementation of <code>{@link JTreeCellReader}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicJTreeCellReader extends BaseValueReader implements JTreeCellReader {

  /**
   * Returns the internal value of a cell in a <code>{@link JTree}</code> as expected in a test.
   * @param tree the given <code>JTree</code>.
   * @param modelValue the value of a cell, retrieved from the model.
   * @return the internal value of a cell in a <code>JTable</code> as expected in a test.
   * @see BaseValueReader#valueFrom(java.awt.Component)
   */
  public String valueAt(JTree tree, Object modelValue) {
    String value = valueFrom(cellRendererIn(tree, modelValue));
    if (value != null) return value;
    return valueToText(tree, modelValue);
  }

  private String valueToText(final JTree tree, final Object modelValue) {
    String text = new JTreeConvertValueToTextTask(tree, modelValue).run();
    if (isDefaultToString(text)) return null;
    return text;
  }

  private static class JTreeConvertValueToTextTask extends GuiQuery<String> {

    private final JTree tree;
    private final Object modelValue;

    JTreeConvertValueToTextTask(JTree tree, Object modelValue) {
      this.tree = tree;
      this.modelValue = modelValue;
    }

    protected String executeInEDT() {
      return tree.convertValueToText(modelValue, false, false, false, 0, false);
    }
  }
}
