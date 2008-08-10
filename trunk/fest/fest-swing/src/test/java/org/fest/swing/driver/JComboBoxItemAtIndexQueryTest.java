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

import javax.swing.JComboBox;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JComboBoxItemAtIndexQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JComboBoxItemAtIndexQueryTest {

  private JComboBox comboBox;
  private int index;
  private String value;

  @BeforeMethod public void setUp() {
    comboBox = createMock(JComboBox.class);
    index = 8;
    value = "Hello";
  }

  public void shouldReturnItemAtIndexInJComboBox() {
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        expect(comboBox.getItemAt(index)).andReturn(value);
      }

      protected void codeToTest() {
        assertThat(JComboBoxItemAtIndexQuery.itemAt(comboBox, index)).isEqualTo(value);
      }
    }.run();
  }
}
