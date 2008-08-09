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

import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.BooleanProvider;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJTableCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Price
 */
@Test(groups = GUI) public class BasicJTableCellReaderTest {

  private JTable table;
  private BasicJTableCellReader reader;

  @BeforeMethod public void setUp() {
    table = new GuiTask<JTable>() {
      protected JTable executeInEDT() {
        return new JTable(1, 1);
      }
    }.run();
    reader = new BasicJTableCellReader();
  }

  public void shouldReturnNullIfRendererNotRecognized() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        DefaultTableModel model = new DefaultTableModel(new Object[][] { { new Jedi("Yoda") } }, new Object[] { "Names" });
        table.setModel(model);
        updateRendererComponent(0, new JToolBar());
        return null;
      }
    }.run();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isNull();
  }

  public void shouldReturnFontFromRenderer() {
    Font expectedFont = new GuiTask<Font>() {
      protected Font executeInEDT() {
        JLabel label = new JLabel("Hello");
        updateRendererComponent(0, label);
        return label.getFont();
      }
    }.run();
    Font font = reader.fontAt(table, 0, 0);
    assertThat(font).isEqualTo(expectedFont);
  }

  public void shouldReturnBackgroundColorFromRenderer() {
    Color expectedBackground = new GuiTask<Color>() {
      protected Color executeInEDT() {
        JLabel label = new JLabel("Hello");
        updateRendererComponent(0, label);
        return label.getBackground();
      }
    }.run();
    Color background = reader.backgroundAt(table, 0, 0);
    assertThat(background).isEqualTo(expectedBackground);
  }

  public void shouldReturnForegroundColorFromRenderer() {
    Color expectedForeground = new GuiTask<Color>() {
      protected Color executeInEDT() {
        JLabel label = new JLabel("Hello");
        updateRendererComponent(0, label);
        return label.getForeground();
      }
    }.run();
    Color foreground = reader.foregroundAt(table, 0, 0);
    assertThat(foreground).isEqualTo(expectedForeground);
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    String expectedText = new GuiTask<String>() {
      protected String executeInEDT() {
        JLabel label = new JLabel("Hello");
        updateRendererComponent(0, label);
        return label.getText();
      }
    }.run();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo(expectedText);
  }

  public void shouldReturnSelectionFromCellRendererIfRendererIsJComboBox() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        JComboBox comboBox = new JComboBox(new Object[] { "One", "Two" });
        comboBox.setSelectedIndex(1);
        updateRendererComponent(0, comboBox);
        return null;
      }
    }.run();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Two");
  }

  public void shouldReturnNullIfRendererIsJComboBoxWithoutSelection() {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        JComboBox comboBox = new JComboBox(new Object[] { "One", "Two" });
        comboBox.setSelectedIndex(-1);
        updateRendererComponent(0, comboBox);
        return null;
      }
    }.run();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isNull();
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsSelectedIfRendererIsJCheckBox(final boolean selected) {
    new GuiTask<Void>() {
      protected Void executeInEDT() {
        JCheckBox checkBox = new JCheckBox("Hello", selected);
        updateRendererComponent(0, checkBox);
        return null;
      }
    }.run();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo(String.valueOf(selected));
  }

  private void updateRendererComponent(int column, Component c) {
    table.getColumnModel().getColumn(column).setCellRenderer(new CustomCellRenderer(c));
  }
}
