/*
 * Created on Jul 24, 2008
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
package org.fest.swing.fixture;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.testing.TestTable;
import org.fest.swing.testing.TestWindow;

import static javax.swing.RowFilter.regexFilter;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.fixture.TableCell.row;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=186" target="_blank">issue 186</a>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, BUG })
public class ReadSortedTableTest {

  private FrameFixture frame;
  
  @BeforeMethod public void setUp() {
    frame = new FrameFixture(MyWindow.createNew());
    frame.show();
  }
  
  @AfterMethod public void tearDown() {
    frame.cleanUp();
  }
  
  public void shouldReadFirstCellAfterReading() {
    frame.textBox("textBox").enterText("2");
    assertThat(frame.table("table").valueAt(row(0).column(0))).isEqualTo("2-0");
  }
  
  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;
    
    final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(ReadSortedTableTest.class);
      add(textBox());
      add(table());
      setPreferredSize(new Dimension(200, 200));
    }

    private JTextField textBox() {
      final JTextField textBox = new JTextField(20);
      textBox.getDocument().addDocumentListener(new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { filterTable(); }
        public void removeUpdate(DocumentEvent e) { filterTable(); }
        
        private void filterTable() {
          sorter.setRowFilter(regexFilter(textBox.getText(), 0));
        }
        
        public void changedUpdate(DocumentEvent e) {}
      });
      textBox.setName("textBox");
      return textBox;
    }
    
    private TestTable table() {
      TestTable table = new TestTable("table", 5, 2);
      sorter.setModel(table.getModel());
      table.setRowSorter(sorter);
      return table;
    }
  }
}
