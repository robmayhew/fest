/*
 * Created on Aug 7, 2008
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
import javax.swing.ListCellRenderer;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Component}</code> used as
 * list renderer for a particular item in a <code>{@link JList}</code>.
 * @see JList#getCellRenderer()
 * @see ListCellRenderer#getListCellRendererComponent(JList, Object, int, boolean, boolean)
 *
 * @author Alex Ruiz
 */
final class JListCellRendererQuery {

  static Component cellRendererIn(final JList list, final int index) {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        Object element = list.getModel().getElementAt(index);
        return list.getCellRenderer().getListCellRendererComponent(list, element, index, true, true);
      }
    });
  }

  private JListCellRendererQuery() {}
}