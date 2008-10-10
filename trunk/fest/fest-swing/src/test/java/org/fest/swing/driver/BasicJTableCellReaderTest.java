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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.BooleanProvider;
import org.fest.swing.testing.CustomCellRenderer;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.factory.JToolBars.toolBar;
import static org.fest.swing.query.ComponentBackgroundQuery.backgroundOf;
import static org.fest.swing.query.ComponentFontQuery.fontOf;
import static org.fest.swing.query.ComponentForegroundQuery.foregroundOf;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link BasicJTableCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJTableCellReaderTest {

  private Robot robot;
  private JTable table;
  private BasicJTableCellReader reader;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    table = window.table;
    reader = new BasicJTableCellReader();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnModelValueToStringIfRendererNotRecognized() {
    setModelData(table, new Object[][] { array(new Jedi("Yoda")) }, array("Names"));
    setCellRendererComponent(table, unrecognizedRenderer());
    robot.waitForIdle();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  private static void setModelData(final JTable table, final Object[][] data, final Object[] columnNames) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
      }
    });
  }

  private static Component unrecognizedRenderer() {
    return toolBar().createNew();
  }

  public void shouldReturnFontFromRenderer() {
    JLabel label = setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    Font font = reader.fontAt(table, 0, 0);
    assertThat(font).isEqualTo(fontOf(label));
  }

  public void shouldReturnBackgroundColorFromRenderer() {
    JLabel label = setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    Color background = reader.backgroundAt(table, 0, 0);
    assertThat(background).isEqualTo(backgroundOf(label));
  }

  public void shouldReturnForegroundColorFromRenderer() {
    JLabel label = setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    Color foreground = reader.foregroundAt(table, 0, 0);
    assertThat(foreground).isEqualTo(foregroundOf(label));
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    setJLabelAsCellRenderer(table);
    robot.waitForIdle();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Hello");
  }

  private static JLabel setJLabelAsCellRenderer(final JTable table) {
    return execute(new GuiQuery<JLabel>() {
      protected JLabel executeInEDT() {
        JLabel label = new JLabel("Hello");
        setCellRendererComponent(table, label);
        return label;
      }
    });
  }

  public void shouldReturnSelectionFromCellRendererIfRendererIsJComboBox() {
    setJComboBoxAsCellRenderer(table, 1);
    robot.waitForIdle();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo("Two");
  }

  public void shouldReturnNullIfRendererIsJComboBoxWithoutSelection() {
    setJComboBoxAsCellRenderer(table, -1);
    robot.waitForIdle();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isNull();
  }

  private static void setJComboBoxAsCellRenderer(final JTable table, final int comboBoxSelectedIndex) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        JComboBox comboBox = new JComboBox(array("One", "Two"));
        comboBox.setSelectedIndex(comboBoxSelectedIndex);
        setCellRendererComponent(table, comboBox);
      }
    });
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldReturnIsSelectedIfRendererIsJCheckBox(boolean selected) {
    setJComboBoxAsCellRenderer(table, "Hello", selected);
    robot.waitForIdle();
    Object value = reader.valueAt(table, 0, 0);
    assertThat(value).isEqualTo(String.valueOf(selected));
  }

  private static void setJComboBoxAsCellRenderer(final JTable table, final String text, final boolean selected) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        JCheckBox checkBox = new JCheckBox(text, selected);
        setCellRendererComponent(table, checkBox);
      }
    });
  }

  // invoked in the EDT
  static void setCellRendererComponent(JTable table, Component renderer) {
    CustomCellRenderer cellRenderer = new CustomCellRenderer(renderer);
    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JTable table = new JTable(1, 1);

    private MyWindow() {
      super(BasicJTableCellReaderTest.class);
      addComponents(table);
    }
  }
}
