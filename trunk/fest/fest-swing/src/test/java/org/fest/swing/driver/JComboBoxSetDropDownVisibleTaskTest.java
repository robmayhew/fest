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
import javax.swing.plaf.ComboBoxUI;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.BooleanProvider;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Tests for <code>{@link JComboBoxSetDropDownVisibleTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JComboBoxSetDropDownVisibleTaskTest {

  private JComboBox comboBox;
  private ComboBoxUI ui;

  @BeforeMethod public void setUp() {
    comboBox = createMock(JComboBox.class);
    ui = createMock(ComboBoxUI.class);
  }

  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class)
  public void shouldSetDropDownVisible(final boolean visible) {
    new EasyMockTemplate(comboBox, ui) {
      protected void expectations() {
        expect(comboBox.getUI()).andReturn(ui);
        ui.setPopupVisible(comboBox, visible);
        expectLastCall().once();
      }

      protected void codeToTest() {
        JComboBoxSetDropDownVisibleTask.setDropDownVisibleIn(comboBox, visible).executeInEDT();
      }
    }.run();
  }
}
