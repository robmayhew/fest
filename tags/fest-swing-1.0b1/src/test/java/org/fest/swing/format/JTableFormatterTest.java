/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JTableFormatter}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableFormatterTest {

  private JTable table;
  private JTableFormatter formatter;

  @BeforeClass public void setUp() {
    table = new JTable(8, 6);
    table.setName("table");
    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    formatter = new JTableFormatter();
  }
  
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJTable() {
    formatter.format(new JTextField());
  }

  @Test public void shouldFormatJTable() {
    String formatted = formatter.format(table);
    assertThat(formatted).contains(table.getClass().getName())
                         .contains("name='table'")
                         .contains("rowCount=8")
                         .contains("columnCount=6")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
