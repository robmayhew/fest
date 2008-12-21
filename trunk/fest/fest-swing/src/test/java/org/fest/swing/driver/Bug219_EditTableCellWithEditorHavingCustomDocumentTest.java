/*
 * Created on Nov 29, 2008
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

import java.awt.Container;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestTable;
import org.fest.swing.test.swing.TestWindow;

import static javax.swing.JOptionPane.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JTableCellValueQuery.cellValueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=219" target="_blank">Bug 219</a>.
 * 
 * @author Alex Ruiz 
 */
@Test(groups = { GUI, BUG })
public class Bug219_EditTableCellWithEditorHavingCustomDocumentTest {

  private static final int COLUMN = 1;
  private static final int ROW = 0;
  private Robot robot;
  private JTableCellWriter cellWriter;
  private TestTable table;
  
  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    cellWriter = new BasicJTableCellWriter(robot);
    MyWindow window = MyWindow.createNew();
    table = window.table;
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldEditCellWithoutActivatingErrorMessage() {
    cellWriter.enterValue(table, ROW, COLUMN, "Hello");
    assertThat(valueAt(ROW, COLUMN)).isEqualTo("Hello");
  }

  @RunsInEDT
  private Object valueAt(int row, int column) {
    return cellValueOf(table, row, column);
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }
    
    final TestTable table = new TestTable(3, 3);
    
    private MyWindow() {
      super(Bug219_EditTableCellWithEditorHavingCustomDocumentTest.class);
      addComponents(table);
      DefaultCellEditor cellEditor = new DefaultCellEditor(new JTextField(new ExampleDocument(this), "", 10));
      table.getColumnModel().getColumn(COLUMN).setCellEditor(cellEditor);
      table.cellEditable(ROW, COLUMN, true);
    }
  }
  
  
  /**
   * @author matthiaso
   */
  private static class ExampleDocument extends PlainDocument {
    private static final long serialVersionUID = 1L;
    
    private final Container errorMessageOwner;

    ExampleDocument(Container errorMessageOwner) {
      this.errorMessageOwner = errorMessageOwner;
    }
    
    /*
     * Inserts a text with a maximum length of 10 characters.
     */
    public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
      // If length is less than 10 characters, insert it
      if (s != null && getLength() + s.length() <= 10) {
        super.insertString(offs, s, a);
        return;
      }
      showMessageDialog(errorMessageOwner, "Maximum length: 10 characters", "Maximum length exceeded", ERROR_MESSAGE);
    }
  }
}
