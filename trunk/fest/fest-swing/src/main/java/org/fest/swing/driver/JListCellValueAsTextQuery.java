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

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.driver.ModelValueToString.asText;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the value of the
 * <code>{@link Component}</code> used by the cell renderer for a particular item in a <code>{@link JList}</code>.
 * @see JList#getCellRenderer()
 * @see ListCellRenderer#getListCellRendererComponent(JList, Object, int, boolean, boolean)
 * @see CellRendererComponentReader#valueFrom(Component)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JListCellValueAsTextQuery {

  static final JList REFERENCE_JLIST = new JList();

  static String valueAtIndex(final JList list, final int index, final CellRendererComponentReader reader) {
    return execute(new GuiQuery<String>() {
      protected String executeInEDT() {
        Object element = list.getModel().getElementAt(index);
        Component c = list.getCellRenderer().getListCellRendererComponent(list, element, index, true, true);
        String value = (c != null) ? reader.valueFrom(c) : null;
        if (value != null) return value;
        return asText(list.getModel().getElementAt(index));
      }
    });
  }

  private JListCellValueAsTextQuery() {}
}
