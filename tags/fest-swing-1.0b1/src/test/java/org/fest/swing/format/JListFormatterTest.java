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

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JListFormatter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JListFormatterTest {

  private JList list;
  private JListFormatter formatter;
  
  @SuppressWarnings("unchecked") 
  @BeforeClass public void setUp() {
    list = new JList(array("One", 2, "Three", 4));
    list.setName("list");
    list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    list.setSelectedIndices(new int[] { 0, 1 });
    formatter = new JListFormatter();
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void shouldThrowErrorIfComponentIsNotJList() {
    formatter.format(new JTextField());
  }

  @Test public void shouldFormatJList() {
    String formatted = formatter.format(list);
    assertThat(formatted).contains(list.getClass().getName())
                         .contains("name='list'")
                         .contains("selectedValues=['One', 2]")
                         .contains("contents=['One', 2, 'Three', 4]")
                         .contains("selectionMode=MULTIPLE_INTERVAL_SELECTION")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
