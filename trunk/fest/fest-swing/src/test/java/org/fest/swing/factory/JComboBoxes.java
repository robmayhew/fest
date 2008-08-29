/*
 * Created on Aug 28, 2008
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
package org.fest.swing.factory;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.util.Arrays.isEmpty;

/**
 * Understands creation of <code>{@link JComboBox}</code>s in the event dispatch thread.
 *
 * @author Alex Ruiz
 */
public final class JComboBoxes {

  private JComboBoxes() {}

  public static JComboBoxFactory comboBox() {
    return new JComboBoxFactory();
  }
  
  public static class JComboBoxFactory {
    boolean editable;
    Object items[];
    String name;
    int selectedIndex;

    public JComboBoxFactory editable(boolean isEditable) {
      editable = isEditable;
      return this;
    }

    public JComboBoxFactory withItems(Object... newItems) {
      items = newItems;
      return this;
    }
    
    public JComboBoxFactory withName(String newName) {
      name = newName;
      return this;
    }
    
    public JComboBoxFactory withSelectedIndex(int newSelectedIndex) {
      selectedIndex = newSelectedIndex;
      return this;
    }
    
    public JComboBox createInEDT() {
      return execute(new GuiQuery<JComboBox>() {
        protected JComboBox executeInEDT()  {
          JComboBox comboBox = new JComboBox();
          comboBox.setEditable(editable);
          if (!isEmpty(items)) comboBox.setModel(new DefaultComboBoxModel(items));
          comboBox.setName(name);
          comboBox.setSelectedIndex(selectedIndex);
          return comboBox;
        }
      });
    }
  }
}