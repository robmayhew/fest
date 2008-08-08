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

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link GetJListCellRendererComponentTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class GetJListCellRendererComponentTaskTest {

  public void shouldReturnCellRendererComponentOfJListItem() {
    final JList list = createMock(JList.class);
    final ListModel model = createMock(ListModel.class);
    final ListCellRenderer cellRenderer = createMock(ListCellRenderer.class);
    final Component renderer = createMock(Component.class);
    final int index = 8;
    final Object value = "Hello";
    new EasyMockTemplate(list, model, cellRenderer) {
      protected void expectations() {
        expect(list.getModel()).andReturn(model);
        expect(model.getElementAt(index)).andReturn(value);
        expect(list.getCellRenderer()).andReturn(cellRenderer);
        expect(cellRenderer.getListCellRendererComponent(list, value, index, false, false)).andReturn(renderer);
      }

      protected void codeToTest() {
        assertThat(GetJListCellRendererComponentTask.cellRendererIn(list, index)).isSameAs(renderer);
      }
    }.run();
  }
}
