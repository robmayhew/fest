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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.testing.TableRenderDemo;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTableCellEditorQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTableCellEditorQueryTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    window = MyWindow.showNew();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  @Test(dataProvider = "editorTypes", groups = { GUI, EDT_ACTION })
  public void shouldReturnEditorComponentOfJTableCell(int column, Class<?> editorType) {
    Component editor = JTableCellEditorQuery.cellEditorIn(window.table, 0, column);
    assertThat(editor).isInstanceOf(editorType);
  }

  @DataProvider(name = "editorTypes") public Object[][] editorTypes() {
    return new Object[][] {
        { 2, JComboBox.class },
        { 3, JTextComponent.class },
        { 4, JCheckBox.class },
    };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTable table;

    static MyWindow showNew() {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
      window.display();
      return window;
    }
    
    MyWindow() {
      super(JTableCellEditorQueryTest.class);
      TableRenderDemo newContentPane = new TableRenderDemo();
      newContentPane.setOpaque(true); // content panes must be opaque
      setContentPane(newContentPane);
      table = newContentPane.table;
    }
  }
}
