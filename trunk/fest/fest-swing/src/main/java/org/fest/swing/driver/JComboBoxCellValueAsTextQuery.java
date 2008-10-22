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

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import static org.fest.swing.driver.ModelValueToString.asText;

/**
 * Understands an action that returns the value of the <code>{@link Component}</code> used by the cell renderer for a
 * particular item in a <code>{@link JComboBox}</code>. <b>Note:</b> this action is <b>not</b> executed in the event
 * dispatch thread. Callers are responsible for calling this query in the event dispatch thread.
 * @see JComboBox#getRenderer()
 * @see ListCellRenderer#getListCellRendererComponent(JList, Object, int, boolean, boolean)
 * @see CellRendererComponentReader#valueFrom(Component)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JComboBoxCellValueAsTextQuery {

  static final JList REFERENCE_JLIST = new JList();

  static String valueAtIndex(final JComboBox comboBox, final int index, final CellRendererComponentReader reader) {
    Object item = comboBox.getItemAt(index);
    ListCellRenderer renderer = comboBox.getRenderer();
    Component c = renderer.getListCellRendererComponent(REFERENCE_JLIST, item, index, true, true);
    String value = (c != null) ? reader.valueFrom(c) : null;
    if (value != null) return value;
    return asText(comboBox.getItemAt(index));
  }

  private JComboBoxCellValueAsTextQuery() {}
}
