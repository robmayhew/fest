/*
 * Created on Aug 7, 2008
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
import javax.swing.JLabel;
import javax.swing.JTable;

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
 * Tests for <code>{@link JTableCellRendererQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTableCellRendererQueryTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    window = MyWindow.createAndShowInEDT();
  }

  @AfterMethod public void tearDown() {
    window.destroy();
  }

  @Test(dataProvider = "rendererTypes", groups = { GUI, EDT_ACTION })
  public void shouldReturnRendererComponentOfJTableCell(int column, Class<?> rendererType) {
    Component renderer = JTableCellRendererQuery.cellRendererIn(window.table, 0, column);
    assertThat(renderer).isInstanceOf(rendererType);
  }

  @DataProvider(name = "rendererTypes") public Object[][] rendererTypes() {
    return new Object[][] {
        { 2, JLabel.class },
        { 3, JLabel.class },
        { 4, JCheckBox.class },
    };
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JTable table;

    static MyWindow createAndShowInEDT() {
      MyWindow window = execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() { return new MyWindow(); }
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
