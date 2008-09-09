/*
 * Created on Aug 10, 2008
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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Condition;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.TestTable;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JTableClearSelectionTask.clearSelectionOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link JTableSelectionQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class JTableSelectionQueryTest {
  
  private MyWindow window;

  @BeforeMethod public void setUp() {
    window = MyWindow.createAndShow();
  }
  
  @AfterMethod public void tearDown() {
    window.destroy();
  }
  
  public void shouldReturnFalseIfTableHasNoSelection() {
    clearSelectionOf(window.table);
    assertThat(JTableSelectionQuery.hasSelection(window.table)).isFalse();
  }

  public void shouldReturnTrueIfTableHasSelection() {
    selectAllIn(window.table);
    assertThat(JTableSelectionQuery.hasSelection(window.table)).isTrue();
  }

  private static void selectAllIn(final TestTable table) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        table.selectAll();
      }
    }, new Condition("All cells in JTable has been selected") {
      public boolean test() {
        return table.getSelectedRowCount() > 0;
      }
    });
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    
    static MyWindow createAndShow() {
      MyWindow window = new MyWindow();
      window.display();
      return window;
    }
    
    final TestTable table = new TestTable(2, 4);

    private MyWindow() {
      super(JTableSelectionQueryTest.class);
      addComponents(table);
    }
  }
}
