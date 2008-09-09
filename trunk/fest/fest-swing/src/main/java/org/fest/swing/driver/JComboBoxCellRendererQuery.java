/*
 * Created on Aug 6, 2008
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

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the <code>{@link Component}</code> used as
 * list renderer for a particular item in a <code>{@link JComboBox}</code>.
 * 
 * @author Alex Ruiz
 */
class JComboBoxCellRendererQuery extends GuiQuery<Component> {
  
  static final JList REFERENCE_JLIST = new JList();

  private final JComboBox comboBox;
  private final int index;

  static Component cellRendererIn(JComboBox comboBox, int index) {
    return execute(new JComboBoxCellRendererQuery(comboBox, index));
  }
  
  JComboBoxCellRendererQuery(JComboBox comboBox, int index) {
    this.index = index;
    this.comboBox = comboBox;
  }

  protected Component executeInEDT() {
    Object item = comboBox.getItemAt(index);
    ListCellRenderer renderer = comboBox.getRenderer();
    return renderer.getListCellRendererComponent(REFERENCE_JLIST, item, index, true, true);
  }
}