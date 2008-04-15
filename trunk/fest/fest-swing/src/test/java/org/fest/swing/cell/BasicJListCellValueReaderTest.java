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
package org.fest.swing.cell;


import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link BasicJListCellValueReader}</code>.
 *
 * @author Alex Ruiz
 */
public class BasicJListCellValueReaderTest {

  private JList list;
  private BasicJListCellValueReader reader;
  
  @BeforeMethod public void setUp() {
    list = new JList();
    reader = new BasicJListCellValueReader();
  }

  @Test public void shouldReturnModelValueToString() {
    DefaultListModel model = new DefaultListModel();
    model.addElement(new Jedi("Yoda"));
    list.setModel(model);
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  @Test public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    DefaultListModel model = new DefaultListModel();
    model.addElement(null);
    list.setModel(model);
    list.setCellRenderer(new CustomCellRenderer(new JToolBar()));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isNull();
  }
  
  @Test public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    DefaultListModel model = new DefaultListModel();
    model.addElement(new Jedi(null));
    list.setModel(model);
    JLabel label = new JLabel("First");
    list.setCellRenderer(new CustomCellRenderer(label));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo(label.getText());
  }
}
