/*
 * Created on Oct 21, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.util.Strings.isDefaultToString;

/**
 * Understands an action, executed in the event dispatch thread, that returns the value of the
 * <code>{@link Component}</code> used by the cell renderer for a particular node in a <code>{@link JTree}</code>.
 * @see JTree#getCellRenderer()
 * @see TreeCellRenderer#getTreeCellRendererComponent(JTree, Object, boolean, boolean, boolean, int, boolean)
 * @see CellRendererComponentReader#valueFrom(Component)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTreeCellValueAsTextQuery {

  static String nodeValue(final JTree tree, final Object modelValue, final CellRendererComponentReader reader) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        TreeCellRenderer r = tree.getCellRenderer();
        Component c = r.getTreeCellRendererComponent(tree, modelValue, false, false, false, 0, false);
        String value = (c != null) ? reader.valueFrom(c) : null;
        if (value != null) return value;
        value= tree.convertValueToText(modelValue, false, false, false, 0, false);
        if (isDefaultToString(value)) return null;
        return value;
      }
    });
  }

  private JTreeCellValueAsTextQuery() {}
}
