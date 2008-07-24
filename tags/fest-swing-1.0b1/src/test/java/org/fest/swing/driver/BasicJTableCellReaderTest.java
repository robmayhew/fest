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
package org.fest.swing.driver;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.driver.BasicJTableCellReader;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link BasicJTableCellReader}</code>.
 *
 * @author Alex Ruiz
 */
public class BasicJTableCellReaderTest {

  private JTable table;
  private BasicJTableCellReader reader;
  
  @BeforeMethod public void setUp() {
    table = new JTable(1, 1);
    reader = new BasicJTableCellReader();
  }

  @Test public void shouldReturnModelValueToStringIfRendererNotRecognized() {
    DefaultTableModel model = new DefaultTableModel(new Object[][] { { new Jedi("Yoda") } }, new Object[] { "Names" });
    table.setModel(model);
    updateRendererComponent(0, new JToolBar());
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  @Test public void shouldReturnFontFromRenderer() {
    JLabel label = new JLabel("Hello");
    updateRendererComponent(0, label);
    Font font = reader.fontAt(table, 0, 0);
    assertThat(font).isEqualTo(label.getFont());
  }

  @Test public void shouldReturnBackgroundColorFromRenderer() {
    JLabel label = new JLabel("Hello");
    updateRendererComponent(0, label);
    Color background = reader.backgroundAt(table, 0, 0);
    assertThat(background).isEqualTo(label.getBackground());
  }

  @Test public void shouldReturnForegroundColorFromRenderer() {
    JLabel label = new JLabel("Hello");
    updateRendererComponent(0, label);
    Color foreground = reader.foregroundAt(table, 0, 0);
    assertThat(foreground).isEqualTo(label.getForeground());
  }

  @Test public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    JLabel label = new JLabel("Hello");
    updateRendererComponent(0, label);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo(label.getText());
  }
  
  @Test public void shouldReturnSelectionFromCellRendererIfRendererIsJComboBox() {
    JComboBox comboBox = new JComboBox(new Object[] { "One", "Two" });
    comboBox.setSelectedIndex(1);
    updateRendererComponent(0, comboBox);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Two");
  }
  
  @Test public void shouldReturnNullIfRendererIsJComboBoxWithoutSelection() {
    JComboBox comboBox = new JComboBox(new Object[] { "One", "Two" });
    comboBox.setSelectedIndex(-1);
    updateRendererComponent(0, comboBox);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isNull();
  }

  @Test(dataProvider = "booleans") 
  public void shouldReturnIsSelectedIfRendererIsJCheckBox(boolean selected) {
    JCheckBox checkBox = new JCheckBox("Hello", selected);
    updateRendererComponent(0, checkBox);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo(String.valueOf(selected));
  }
  
  @DataProvider(name = "booleans") public Object[][] booleans() {
    return new Object[][] { { true }, { false } };
  }

  private void updateRendererComponent(int column, Component c) {
    table.getColumnModel().getColumn(column).setCellRenderer(new CustomCellRenderer(c));
  }  
}
