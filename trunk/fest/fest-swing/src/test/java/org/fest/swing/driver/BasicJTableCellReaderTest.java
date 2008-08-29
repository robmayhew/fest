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
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.BooleanProvider;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.factory.JComboBoxes.comboBox;
import static org.fest.swing.factory.JLabels.label;
import static org.fest.swing.factory.JTables.table;
import static org.fest.swing.query.ComponentBackgroundQuery.backgroundOf;
import static org.fest.swing.query.ComponentFontQuery.fontOf;
import static org.fest.swing.query.ComponentForegroundQuery.foregroundOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link BasicJTableCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Price
 */
@Test(groups = GUI) 
public class BasicJTableCellReaderTest {

  private JTable table;
  private BasicJTableCellReader reader;

  @BeforeMethod public void setUp() {
    table = table().withColumnCount(1)
                   .withRowCount(1)
                   .createInEDT();
    reader = new BasicJTableCellReader();
  }

  public void shouldReturnNullIfRendererNotRecognized() {
    setModelData(table, new Object[][] { array(new Jedi("Yoda")) }, array("Names"));
    setCellRendererComponent(table, unrecognizedRenderer());
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isNull();
  }

  private static void setModelData(final JTable table, final Object[][] data, final Object[] columnNames) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.setModel(new DefaultTableModel(data, columnNames));
      }
    });
  }
  
  private static Component unrecognizedRenderer() {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return new JToolBar();
      }
    });
  }
  
  public void shouldReturnFontFromRenderer() {
    final JLabel label = setJLabelAsCellRenderer(table);
    Font font = reader.fontAt(table, 0, 0);
    assertThat(font).isEqualTo(fontOf(label));
  }

  public void shouldReturnBackgroundColorFromRenderer() {
    final JLabel label = setJLabelAsCellRenderer(table);
    Color background = reader.backgroundAt(table, 0, 0);
    assertThat(background).isEqualTo(backgroundOf(label));
  }

  public void shouldReturnForegroundColorFromRenderer() {
    final JLabel label = setJLabelAsCellRenderer(table);
    Color foreground = reader.foregroundAt(table, 0, 0);
    assertThat(foreground).isEqualTo(foregroundOf(label));
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    setJLabelAsCellRenderer(table);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Hello");
  }

  private static JLabel setJLabelAsCellRenderer(JTable table) {
    JLabel label = label().withText("Hello").createInEDT(); 
    setCellRendererComponent(table, label);
    return label;
  }

  public void shouldReturnSelectionFromCellRendererIfRendererIsJComboBox() {
    setJComboBoxAsCellRenderer(table, 1);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Two");
  }

  public void shouldReturnNullIfRendererIsJComboBoxWithoutSelection() {
    setJComboBoxAsCellRenderer(table, -1);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isNull();
  }

  private static void setJComboBoxAsCellRenderer(JTable table, int comboBoxSelectedIndex) {
    JComboBox renderer = comboBox().withItems("One", "Two")
                                   .withSelectedIndex(comboBoxSelectedIndex)
                                   .createInEDT(); 
    setCellRendererComponent(table, renderer);
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsSelectedIfRendererIsJCheckBox(final boolean selected) {
    JCheckBox checkBox = new JCheckBox("Hello", selected);
    setCellRendererComponent(table, checkBox);
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo(String.valueOf(selected));
  }

  private static void setCellRendererComponent(final JTable table, final Component renderer) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(renderer));
      }
    });
  }
}
