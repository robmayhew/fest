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

import java.awt.Component;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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
    list = new JList(new Object[] { "First" });
    reader = new BasicJListCellValueReader();
  }
  
  @Test public void shouldReturnTextIfCellRendererIsJLabel() {
    list.setCellRenderer(new DefaultListCellRenderer());
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo("First");
  }

  @Test(dataProvider = "toggleButtons")
  public void shouldReturnIsSelectedIfCellRendererIsJToggleButton(JToggleButton toggleButton) {
    toggleButton.setSelected(true);
    list.setCellRenderer(new CustomCellRenderer(toggleButton));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo(toggleButton.isSelected());
  }

  @DataProvider(name = "toggleButtons") public Object[][] toggleButtons() {
    return new Object[][] { { new JToggleButton() }, { new JCheckBox() }, { new JRadioButton() } };
  }
  
  @Test(dataProvider = "textComponents")
  public void shouldReturnTextIfCellRendererIsJTextComponent(JTextComponent textComponent) {
    textComponent.setText("Hello");
    list.setCellRenderer(new CustomCellRenderer(textComponent));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo(textComponent.getText());
  }
  
  @DataProvider(name = "textComponents") public Object[][] textComponents() {
    return new Object[][] { { new JTextArea() }, { new JTextField() } };
  }

  @Test public void shouldReturnTextIfCellRendererIsAbstractButton() {
    JButton button = new JButton("Hello");
    list.setCellRenderer(new CustomCellRenderer(button));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo(button.getText());
  }
  
  @Test public void shouldReturnModelValueToStringIfRendererNotRecognized() {
    class Jedi {
      private final String name;

      public Jedi(String name) {
        this.name = name;
      }
      
      @Override public String toString() {
        return name;
      }
    }
    DefaultListModel model = new DefaultListModel();
    model.addElement(new Jedi("Yoda"));
    list.setModel(model);
    list.setCellRenderer(new CustomCellRenderer(new JToolBar()));
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
  
  private static class CustomCellRenderer implements ListCellRenderer {
    private static final long serialVersionUID = 1L;
    private final Component rendererComponent;
    
    public CustomCellRenderer(Component rendererComponent) {
      this.rendererComponent = rendererComponent;
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
      return rendererComponent;
    }
  }
}
