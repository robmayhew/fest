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

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.GetJComboBoxListCellRendererComponentTask.REFERENCE_JLIST;

/**
 * Tests for <code>{@link GetJComboBoxListCellRendererComponentTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class GetJComboBoxListCellRendererComponentTaskTest {

  public void shouldReturnCellRendererComponentOfJComboBoxItem() {
    final JComboBox comboBox = createMock(JComboBox.class);
    final ListCellRenderer cellRenderer = createMock(ListCellRenderer.class);
    final Component renderer = createMock(Component.class);
    final int index = 8;
    final Object value = "Hello";
    new EasyMockTemplate(comboBox, cellRenderer) {
      protected void expectations() {
        expect(comboBox.getRenderer()).andReturn(cellRenderer);
        expect(comboBox.getItemAt(index)).andReturn(value);
        expect(cellRenderer.getListCellRendererComponent(REFERENCE_JLIST, value, index, true, true)).andReturn(renderer);
      }

      protected void codeToTest() {
        assertThat(GetJComboBoxListCellRendererComponentTask.listCellRendererComponentOf(comboBox, index)).isSameAs(renderer);
      }
    }.run();
  }
}
