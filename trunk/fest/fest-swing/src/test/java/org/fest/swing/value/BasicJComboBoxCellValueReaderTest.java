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
package org.fest.swing.value;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link BasicJComboBoxCellValueReader}</code>.
 *
 * @author Alex Ruiz
 */
public class BasicJComboBoxCellValueReaderTest {

  private JComboBox comboBox;
  private BasicJComboBoxCellValueReader reader;
  
  @BeforeMethod public void setUp() {
    comboBox = new JComboBox(new Object[] { "First" });
    reader = new BasicJComboBoxCellValueReader();
  }
  
  @Test public void shouldReturnModelValueToString() {
    DefaultComboBoxModel model = new DefaultComboBoxModel();
    model.addElement(new Jedi("Yoda"));
    comboBox.setModel(model);
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  @Test public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    DefaultComboBoxModel model = new DefaultComboBoxModel();
    model.addElement(null);
    comboBox.setModel(model);
    comboBox.setRenderer(new CustomCellRenderer(new JToolBar()));
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isNull();
  }
  
  @Test public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    DefaultComboBoxModel model = new DefaultComboBoxModel();
    model.addElement(new Jedi(null));
    comboBox.setModel(model);
    JLabel label = new JLabel("First");
    comboBox.setRenderer(new CustomCellRenderer(label));
    Object value = reader.valueAt(comboBox, 0);
    assertThat(value).isEqualTo(label.getText());
  }
}
