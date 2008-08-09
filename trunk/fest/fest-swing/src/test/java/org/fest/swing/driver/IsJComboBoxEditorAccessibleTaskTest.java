/*
 * Created on Aug 8, 2008
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link IsJComboBoxEditorAccessibleTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class IsJComboBoxEditorAccessibleTaskTest {

  private JComboBox comboBox;
  
  @BeforeMethod public void setUp() {
    comboBox = createMock(JComboBox.class);
  }
  
  public void shouldReturnTrueIfJComboBoxIsEnabledAndEditable() {
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        expect(comboBox.isEditable()).andReturn(true);
        expect(comboBox.isEnabled()).andReturn(true);
      }

      protected void codeToTest() {
        assertThat(IsJComboBoxEditorAccessibleTask.isEditorAccessible(comboBox)).isTrue();
      }
    }.run();
  }

  @Test(dataProvider = "notEditableOrDisabled")
  public void shouldReturnFalseIfJComboBoxIsNotEditableOrNotEnabled(final boolean editable, final boolean enabled) {
    new EasyMockTemplate(comboBox) {
      protected void expectations() {
        expect(comboBox.isEditable()).andReturn(editable);
        expect(comboBox.isEnabled()).andReturn(enabled);
      }

      protected void codeToTest() {
        assertThat(IsJComboBoxEditorAccessibleTask.isEditorAccessible(comboBox)).isFalse();
      }
    }.run();
  }
  
  @DataProvider(name = "notEditableOrDisabled") public Object[][] notEditableOrDisabled() {
    return new Object[][] {
        { true , false },
        { false , true },
        { false , false },
    };
  }
}
