/*
 * Created on Oct 11, 2008
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
package org.fest.swing.query;

import javax.swing.JComboBox;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the selected item in a
 * <code>{@link JComboBox}</code>.
 * @see JComboBox#getSelectedItem()
 *
 * @author Alex Ruiz
 */
public final class JComboBoxSelectedItemQuery {
  
  /**
   * Returns the selected item in the given <code>{@link JComboBox}</code>. This action is executed in the event
   * dispatch thread.
   * @param comboBox the given <code>JComboBox</code>.
   * @return the selected item in the given <code>JComboBox</code>.
   * @see JComboBox#getSelectedItem()
   */
  public static Object selectedItemOf(final JComboBox comboBox) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() {
        return comboBox.getSelectedItem();
      }
    });
  }

  private JComboBoxSelectedItemQuery() {}
}
